package com.ss.restfulcrud.controller;

import com.ss.restfulcrud.entities.Department;
import com.ss.restfulcrud.entities.Msg;
import com.ss.restfulcrud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    @RequestMapping("/depts")
    @ResponseBody
    public Msg getDepts(){
        List<Department> list = departmentService.findAllDepartment();
        return Msg.success().add("depts",list);
    }

}
