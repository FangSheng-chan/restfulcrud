package com.ss.restfulcrud.mapper;

import com.ss.restfulcrud.entities.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartMapper {
   List<Department> findAllDepartment();
}
