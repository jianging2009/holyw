package com.holyw.holyw.service;

import com.holyw.holyw.mapper.UserMapper;

import com.holyw.holyw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public Integer maxId()  throws Exception{
        return userMapper.maxId();
    }

    @Transactional
    public User get(Integer id)  throws Exception{
        return userMapper.get(id);
    }

    public User findByUserName(String userName)  throws Exception{
        return userMapper.findByUserName(userName);
    }


}
