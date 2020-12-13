package com.ss.restfulcrud.service;

import com.ss.restfulcrud.entities.Employee;
import com.ss.restfulcrud.entities.User;

import java.util.Collection;
import java.util.List;

public interface UserService {

    User login(String name);

    void addUser(User user);

    Collection<User> getAll();

    User getUserId(Integer id);

    int updateUser(User user);

    int delUser(Integer id);

    List<User> findAllUser();


}
