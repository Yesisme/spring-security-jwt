package com.jwt.service;

import com.jwt.entity.Po.UserPO;

public interface UserService {

    UserPO findById(Integer userId);

    UserPO findByName(UserPO user);
}
