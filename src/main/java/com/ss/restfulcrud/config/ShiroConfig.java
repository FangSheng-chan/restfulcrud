package com.ss.restfulcrud.config;


import com.ss.restfulcrud.filter.AddPrincipalToSessionFilter;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //ShiroFilterFactoryBean ----3
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultSecurityManager defaultSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultSecurityManager);
        //设置登录请求
        bean.setLoginUrl("/toLogin");
        bean.setSuccessUrl("/login");
        //添加Shiro的內置过滤器
        Map<String, String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/login", "anon");
        filterMap.put("/users", "authc");
        filterMap.put("/user/**", "authc");
        filterMap.put("/emps/**", "authc");
//        filterMap.put("/**","user");
//        filterMap.put("/index.html", "user");
        bean.setFilterChainDefinitionMap(filterMap);
        //解决session丢失
        Map<String, Filter> fmap = new HashMap<>();
        fmap.put("addPrincipal", addPrincipalToSessionFilter());
        bean.setFilters(fmap);

        return bean;
    }

    //DefaultSecurityManager ----- 2
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }


    /**
     * cookie管理对象
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey("ZHANGXIAOHEI_CAT".getBytes());
        return cookieRememberMeManager;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    //创建 realm 对象 ，需要自定义类 ---- 1
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * Shiro自定义过滤器（解决session丢失）
     *
     * @return
     */
    @Bean
    public OncePerRequestFilter addPrincipalToSessionFilter() {
        return new AddPrincipalToSessionFilter();
    }
}
