package com.jwt.mapper;

import com.jwt.entity.Po.UserPO;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    UserPO findById(@Param("userId") Integer userId);

    UserPO findByName(@Param("userName") String userName);
}
