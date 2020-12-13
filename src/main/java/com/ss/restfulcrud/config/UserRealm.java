package com.ss.restfulcrud.config;

import com.ss.restfulcrud.entities.User;
import com.ss.restfulcrud.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

//自定义的UserRealm extends AuthorizingRealm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了=>授权=>doGetAuthorizationInfo");
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证=>doGetAuthenticationInfo");

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        //连接真实数据库
        User user = userService.login(userToken.getUsername());
        if (user == null) {
            throw  new UnknownAccountException("账户密码不正确"); //UnknownAccountException
        }
        //密码认证，shiro做
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}

