package com.ss.restfulcrud.service.impl;


import com.github.pagehelper.PageException;
import com.ss.restfulcrud.entities.User;
import com.ss.restfulcrud.mapper.UserMapper;
import com.ss.restfulcrud.service.UserService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User login(String name) {
        return userMapper.login(name);
    }

    @Override
    public void addUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public Collection<User> getAll() {
        return userMapper.getAll();
    }

    @Override
    public User getUserId(Integer id) {
//        User user = new User();
//        Date birth = user.getBirth();
//        try {
//            if (birth != null) {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                sdf.format(birth);
//                user.setBirth(birth);
//            }
//        } catch (PageException e) {
//            birth = null;
//            e.printStackTrace();
//        }

        return userMapper.getUserId(id);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int delUser(Integer id) {
        return userMapper.delUser(id);
    }

    @Override
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }
}
