package com.jwt.controller;

import com.alibaba.fastjson.JSONObject;
import com.jwt.annotation.LoginToken;
import com.jwt.entity.Po.UserPO;
import com.jwt.service.UserService;
import com.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class LoginApi {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Object login(@RequestBody UserPO user){
        JSONObject json = new JSONObject();
        UserPO userInDB = userService.findByName(user);
        if(userInDB==null){
            json.put("message","登陆失败，用户不存在");
            return json;
        }else {
            if (!userInDB.getPassword().equals(user.getPassword())) {
                json.put("message", "登陆失败,密码错误");
                return json;
            } else {
                String token = JwtUtil.getToken(userInDB);
                json.put("token", token);
                json.put("user", userInDB);
                return json;
            }
        }
    }

    @LoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }
}
