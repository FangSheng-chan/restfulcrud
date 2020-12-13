package com.ss.restfulcrud.mapper;

import com.ss.restfulcrud.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Mapper
@Repository
public interface UserMapper {

    User login(String name);

    int insertUser(User user);

    Collection<User> getAll();

    User getUserId(Integer id);

    int updateUser(User user);

    int delUser(Integer id);

    int getTotal();

    List<User> findAllUser();
}
