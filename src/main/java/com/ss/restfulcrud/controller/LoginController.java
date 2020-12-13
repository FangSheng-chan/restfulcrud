package com.ss.restfulcrud.controller;

import com.alibaba.fastjson.JSON;
import com.ss.restfulcrud.entities.User;
import com.ss.restfulcrud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//在template目录下的所有页面，只能通过controller来跳转
@Controller
@Slf4j
public class LoginController {

    @Resource
    private UserService userService;

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping({"/login","/"})
    public String login(boolean rememberMe,Integer id, String username, String password, Model model, HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        //获取当前的用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password,rememberMe);
        try {
            //主体提交登录请求到SecurityManager
            subject.login(token); //执行登录方法，如果没有异常就说明ok
            token.setRememberMe(rememberMe);
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            session.setAttribute("user", user);
            if (username.equals("admin")) {
                return "emp/performance";
            }
            Integer userId = user.getId();
            //获取主页的用户ID
            model.addAttribute("userId", userId);
            model.addAttribute("msg", username);
            return "index";
        } catch (UnknownAccountException e) { //用户名不存在
            model.addAttribute("msg", "请输入用户名");
            return "login";
        } catch (IncorrectCredentialsException e) { //密码错误
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }

    @GetMapping(value = "/register")
    public String register(Model model) {
        return "register";
    }

    @GetMapping(value = "/logout")
    public ModelAndView logout(ModelAndView view, ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("user") != null) {
            session.removeAttribute("user");
        }
        view.setViewName("redirect:login");
        return view;
    }

    @RequestMapping(value = "/addUser")
    public String addUser(HttpServletRequest request, HttpServletResponse response, User user) {
        userService.addUser(user);
        return "login";
    }
}
