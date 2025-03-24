package com.kmj.apiProject.user.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmj.apiProject.auth.dto.AuthDto;
import com.kmj.apiProject.common.config.ErrorCode;
import com.kmj.apiProject.user.dao.UserDao;
import com.kmj.apiProject.user.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
	
	
	@Autowired
	UserDao userDao;
	

	
	public Map<Object, Object> userDetail(String id) {
		Map<Object, Object> response = new HashMap<>();
		response.putAll(ErrorCode.FAIL.toMap());

		try {
			
			
			AuthDto userDetail = userDao.userDetail(id);

			response.putAll(ErrorCode.SUCCESS.toMap());
			response.put("data", userDetail);
		} catch (Exception e) {
			e.printStackTrace();
			response.putAll(ErrorCode.FAIL.toMap());
		}

		return response;
	}
}
