package com.security.util;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletResponse;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ResultUtil {

	private ResultUtil() {}
	
	public static void responseJson(ServletResponse response,Map<String, Object> map) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			out= response.getWriter();
			out.print(JSON.toJSONString(map));
		} catch (Exception e) {
			log.error("【json转换异常】"+e);
		}finally {
			if(out!=null) {
				out.flush();
				out.close();
			}
		}
	}
	
	public static Map<String, Object> resultSuccess(Map<String, Object> map){
		map.put("message", "操作成功");
		map.put("code", 200);
		return map;
	}
	
	public static Map<String, Object> resultFail(Map<String, Object> map){
		map.put("message", "操作失败");
		map.put("code",500);
		return map;
	}
	
	public static Map<String, Object> resultCode(Integer code,String message){
		Map<String, Object> map = new HashMap<>();
		map.put("message", message);
		map.put("code", code);
		return map;
	}
}
