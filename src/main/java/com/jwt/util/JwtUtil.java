package com.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jwt.entity.Po.UserPO;

public class JwtUtil {

    public static String getToken(UserPO user){
        String token="";
        token = JWT.create().withAudience(user.getId().toString()).sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
