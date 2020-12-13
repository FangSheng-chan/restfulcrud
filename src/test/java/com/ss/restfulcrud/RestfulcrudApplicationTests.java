package com.ss.restfulcrud;


import com.ss.restfulcrud.entities.Employee;
import com.ss.restfulcrud.mapper.UserMapper;
import com.ss.restfulcrud.service.EmployeeService;
import com.ss.restfulcrud.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
class RestfulcrudApplicationTests {

    @Autowired
    UserService userService;

    @Resource
    EmployeeService employeeService;

    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {
//        boolean dd = employeeService.checkEmp("qq");
//        System.out.println(dd);
        System.out.println(userService.getUserId(90));
    }

}
