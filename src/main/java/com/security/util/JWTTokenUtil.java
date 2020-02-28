package com.security.util;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.security.config.JWTConfig;
import com.security.entity.SelfUserEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTTokenUtil {
	
	public static String createAccessToken(SelfUserEntity selfUserEntity) {
		String token = Jwts.builder()
				//用户ID
				.setId(selfUserEntity.getUserId()+"")
				//主题
				.setSubject(selfUserEntity.getUsername())
				//签发时间
				.setIssuedAt(new Date())
				//签发者
				.setIssuer("lym")
				//自定义属性放入权限
				.claim("authorities",JSON.toJSONString(selfUserEntity.getAuthorities()))
				//失效时间
				.setExpiration(new Date(System.currentTimeMillis() + JWTConfig.expiration))
				//签名算法和密钥
				.signWith(SignatureAlgorithm.HS512, JWTConfig.secret)
			    .compact();
				return token;	
	}
}
