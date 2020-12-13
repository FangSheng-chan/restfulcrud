package com.ss.restfulcrud.service;


import com.ss.restfulcrud.entities.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAllEmp();

    void addEmp(Employee employee);

    List<Employee> findPerformance();

    int update(Employee employee);

    Employee getEmpId(Integer id);

    int delEmp(Integer id);

    boolean checkEmp(String name);
}
