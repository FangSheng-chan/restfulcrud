package com.ss.restfulcrud.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//使用WebMvcConfigureAdapter可以来拓展SpringMvc的功能
//@Configuration
//public class MyMvcConfig implements WebMvcConfigurer {
//
//    //视图跳转
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        WebMvcConfigurer.super.addViewControllers(registry);
////        浏览器发送ss 请求来到success
//        registry.addViewController("/index").setViewName("success");
//    }
//
//    @Bean//将主件注册到容器
//    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
//        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter(){
//            @Override
//            public void addViewControllers(ViewControllerRegistry registry) {
//                registry.addViewController("/").setViewName("login");
//                registry.addViewController("/index.html").setViewName("login");
//                registry.addViewController("/main.html").setViewName("ss");
//            }
//
//            //注册拦截器
//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
//                //super.addInterceptors(registry);
//                //静态资源： *.css ,*.js
//                //SpringBoot已经做好了静态资源映射
//                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
//                        .excludePathPatterns("/index.html","/","/user/login","/asserts/**","/webjars/**");
//            }
//        };
//        return  adapter;
//    }
//}