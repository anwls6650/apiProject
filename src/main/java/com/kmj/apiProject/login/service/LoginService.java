package com.kmj.apiProject.login.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kmj.apiProject.common.config.ErrorCode;
import com.kmj.apiProject.common.config.UtilsConfig;
import com.kmj.apiProject.common.security.JwtUtil;
import com.kmj.apiProject.login.dao.LoginDao;
import com.kmj.apiProject.login.dto.LoginDto;

@Service
public class LoginService {

	@Autowired
	LoginDao loginDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	public Map<Object, Object> signUp(LoginDto loginDto) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		response.putAll(ErrorCode.FAIl.toMap());

		if (UtilsConfig.isNullOrEmpty(loginDto.getId()) || UtilsConfig.isNullOrEmpty(loginDto.getPhoneNum())
				|| UtilsConfig.isNullOrEmpty(loginDto.getName()) || UtilsConfig.isNullOrEmpty(loginDto.getPassword())) {
			response.putAll(ErrorCode.PARAMETER_FAIL.toMap());
			return response;
		}

			try {
				LoginDto userDetail = loginDao.userDetail(loginDto);

				if (userDetail == null) {
					response.putAll(ErrorCode.DUPLICATE_DATA.toMap());
					return response;
				}

				// 비밀번호 암호화
				String encryptedPassword = passwordEncoder.encode(loginDto.getPassword());

				// 암호화된 비밀번호를 User 객체에 설정
				loginDto.setPassword(encryptedPassword);

				loginDao.signUp(loginDto);

				String token = jwtUtil.createToken(loginDto.getId(), loginDto.getName());

				response.putAll(ErrorCode.SUCCESS.toMap());
				response.put("token", token);
			} catch (Exception e) {
				e.printStackTrace();
			}

		return response;
	}
}
