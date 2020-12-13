package com.ss.restfulcrud.service.impl;

import com.ss.restfulcrud.entities.Department;
import com.ss.restfulcrud.mapper.DepartMapper;
import com.ss.restfulcrud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentImp implements DepartmentService {

    @Resource
    private DepartMapper departMapper;


    @Override
    public List<Department> findAllDepartment() {
        return departMapper.findAllDepartment();
    }
}
