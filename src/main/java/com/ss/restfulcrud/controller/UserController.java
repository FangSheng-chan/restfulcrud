package com.ss.restfulcrud.controller;

import com.github.pagehelper.PageException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ss.restfulcrud.entities.Msg;
import com.ss.restfulcrud.entities.User;
import com.ss.restfulcrud.mapper.UserMapper;
import com.ss.restfulcrud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    //来到员工添加页面
    @GetMapping("/user")
    public String toAddPage() {
        //来到添加页面
        return "user/add";
    }

    //添加员工
    @PostMapping("/user")
    public String addEmp(User user) {
        //来到员工列表页面
        //redirect:表示重定向一个地址， /代表当前项目路径
        userService.addUser(user);
        return "redirect:/users";
    }

    /***
     * 得到个人用户信息
     * @param id
     * @param session
     * @return
     */
    @GetMapping("/users/{id}")
    @ResponseBody
    public Msg getUser(@PathVariable("id") Integer id, HttpSession session) {
        User user = userService.getUserId(id);
        Date birth = user.getBirth();
        try {
            if (birth != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.format(birth);
                user.setBirth(birth);
            }
        } catch (PageException e) {
            e.printStackTrace();
        }
        System.out.println(user.getBirth());
        return Msg.success().add("user", user);
    }

    /***
     * 修改个人用户信息
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/users/{id}")
    @ResponseBody
    public Msg editEmp(@PathVariable("id") Integer id,User user) {
        System.err.println(user);
        userService.updateUser(user);
        return Msg.success();
    }

    //来到修改页面，查出当前员工，在页面回显
    @GetMapping("/user/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUserId(id);
        System.err.println(user);
        model.addAttribute("user", user);
        //回到修改页面
        return "user/add";
    }

    //用户修改
    @PutMapping("/user")
    public String updateUser(User user) {
        userService.updateUser(user);
        System.err.println(user);
        return "redirect:/users";
    }

    //用户删除
    @GetMapping("/delUser/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        userService.delUser(id);
        return "redirect:/users";
    }

    @RequestMapping("/users")
    public String list(Model model,
                       @RequestParam(required = false, defaultValue = "1", value = "pageNum") Integer pageNum,
                       @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize) {
        //为了程序的严谨性，判断非空：
        //设置默认当前页
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        //设置默认每页显示的数据数
        if (pageSize == null) {
            pageSize = 1;
        }

        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum, pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<User> userList = userService.findAllUser();
            //3.使用PageInfo包装查询后的结果,5是连续显示的条数,结果list类型是Page<E>
            PageInfo<User> pageInfo = new PageInfo<User>(userList, pageSize);
            //4.使用model传参数回前端
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("userList", userList);
        } finally {
            //清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();
        }
        return "user/list";
    }

    @RequestMapping(value = "/search")
    public String search(Model model) {
        return "search";
    }

}
