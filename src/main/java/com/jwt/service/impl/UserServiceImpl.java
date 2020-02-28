package com.jwt.service.impl;

import com.jwt.entity.Po.UserPO;
import com.jwt.mapper.UserMapper;
import com.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserPO findById(Integer userId) {
        return userMapper.findById(userId);
    }

    @Override
    public UserPO findByName(UserPO user) {
        return userMapper.findByName(user.getUserName());
    }
}
