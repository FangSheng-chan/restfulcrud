package com.ss.restfulcrud.mapper;

import com.ss.restfulcrud.entities.Employee;
import com.ss.restfulcrud.entities.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    List<Employee> findAllEmp();

    int addEmp(Employee employee);

    List<Employee> findPerformance();

    int update(Employee employee);

    Employee getEmpId(Integer id);

    int delEmp(Integer id);

    Employee findEmpName(String name);
}
