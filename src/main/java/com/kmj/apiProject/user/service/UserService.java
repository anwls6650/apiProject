package com.kmj.apiProject.user.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmj.apiProject.auth.dto.AuthDto;
import com.kmj.apiProject.common.config.ErrorCode;
import com.kmj.apiProject.user.dao.UserDao;
import com.kmj.apiProject.user.dto.UserDeliveryDto;
import com.kmj.apiProject.user.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {

	@Autowired
	UserDao userDao;

	/**
	 * 회원 정보 API
	 * 
	 * @param id
	 */
	public Map<Object, Object> userDetail(int userId) {
		Map<Object, Object> response = new HashMap<>();
		response.putAll(ErrorCode.FAIL.toMap());

		try {

			AuthDto userDetail = userDao.userDetail(userId);

			response.putAll(ErrorCode.SUCCESS.toMap());
			response.put("data", userDetail);
		} catch (Exception e) {
			e.printStackTrace();
			response.putAll(ErrorCode.FAIL.toMap());
		}

		return response;
	}

	/**
	 * 배송지 등록
	 * 
	 * @param userDeliveryDto
	 */
	public Map<Object, Object> delivery(UserDeliveryDto userDeliveryDto) {
		Map<Object, Object> response = new HashMap<>();
		response.putAll(ErrorCode.FAIL.toMap());

		try {

			userDao.delivery(userDeliveryDto);

			response.putAll(ErrorCode.SUCCESS.toMap());

		} catch (Exception e) {
			e.printStackTrace();
			response.putAll(ErrorCode.FAIL.toMap());
		}

		return response;
	}
}
