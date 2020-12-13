package com.ss.restfulcrud.service.impl;

import com.ss.restfulcrud.entities.Employee;
import com.ss.restfulcrud.mapper.EmployeeMapper;
import com.ss.restfulcrud.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EmployeeImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;


    @Override
    public List<Employee> findAllEmp() {
        return employeeMapper.findAllEmp();
    }

    @Override
    public void addEmp(Employee employee) {
        employeeMapper.addEmp(employee);
    }

    @Override
    public List<Employee> findPerformance() {
        return employeeMapper.findPerformance();
    }

    @Override
    public int update(Employee employee) {
        return employeeMapper.update(employee);
    }

    @Override
    public Employee getEmpId(Integer id) {
        return employeeMapper.getEmpId(id);
    }

    @Override
    public int delEmp(Integer id) {
        return employeeMapper.delEmp(id);
    }

    /***
     * 检验员工名是否可用
     * @param name
     * @return
     */
    @Override
    public boolean checkEmp(String name) {
        Employee empName = employeeMapper.findEmpName(name);
        return empName != null;
    }
}
