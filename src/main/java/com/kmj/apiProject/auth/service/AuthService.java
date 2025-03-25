package com.kmj.apiProject.auth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kmj.apiProject.auth.dao.AuthDao;
import com.kmj.apiProject.auth.dto.AuthDto;
import com.kmj.apiProject.auth.dto.DriverDto;
import com.kmj.apiProject.common.config.ErrorCode;
import com.kmj.apiProject.common.config.UtilsConfig;
import com.kmj.apiProject.common.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {

	@Autowired
	AuthDao authDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * 유저 회원가입
	 * 
	 * @param authDto
	 */
	public Map<Object, Object> signUp(AuthDto authDto) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		response.putAll(ErrorCode.FAIL.toMap());

		if (UtilsConfig.isNullOrEmpty(authDto.getId()) || UtilsConfig.isNullOrEmpty(authDto.getPhoneNum())
				|| UtilsConfig.isNullOrEmpty(authDto.getName()) || UtilsConfig.isNullOrEmpty(authDto.getPassword())) {
			response.putAll(ErrorCode.PARAMETER_FAIL.toMap());
			return response;
		}

		try {
			AuthDto userDetail = authDao.userDetail(authDto);

			if (userDetail != null) {
				response.putAll(ErrorCode.USER_ALREADY_EXISTS.toMap());
				return response;
			}

			// 비밀번호 암호화
			String encryptedPassword = passwordEncoder.encode(authDto.getPassword());

			// 암호화된 비밀번호를 User 객체에 설정
			authDto.setPassword(encryptedPassword);

			authDao.signUp(authDto);

			AuthDto userDetailR2 = authDao.userDetail(authDto);

			String token = jwtUtil.createToken(authDto.getId(), authDto.getName(), userDetailR2.getUserId());

			response.putAll(ErrorCode.SUCCESS.toMap());
			response.put("token", token);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 기사 회원가입
	 * 
	 * @param driverDto
	 */
	public Map<Object, Object> driverSignUp(DriverDto driverDto) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		response.putAll(ErrorCode.FAIL.toMap());

		if (UtilsConfig.isNullOrEmpty(driverDto.getId()) || UtilsConfig.isNullOrEmpty(driverDto.getPhoneNum())
				|| UtilsConfig.isNullOrEmpty(driverDto.getName())
				|| UtilsConfig.isNullOrEmpty(driverDto.getPassword())) {
			response.putAll(ErrorCode.PARAMETER_FAIL.toMap());
			return response;
		}

		try {
			DriverDto driverDetail = authDao.driverDetail(driverDto);

			if (driverDetail != null) {
				response.putAll(ErrorCode.USER_ALREADY_EXISTS.toMap());
				return response;
			}

			// 비밀번호 암호화
			String encryptedPassword = passwordEncoder.encode(driverDto.getPassword());

			// 암호화된 비밀번호를 User 객체에 설정
			driverDto.setPassword(encryptedPassword);

			authDao.driverSignUp(driverDto);

			DriverDto driverDetailR2 = authDao.driverDetail(driverDto);

			String token = jwtUtil.createToken(driverDetailR2.getId(), driverDetailR2.getName(),
					driverDetailR2.getDriverId());

			response.putAll(ErrorCode.SUCCESS.toMap());
			response.put("token", token);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 로그인
	 * 
	 * @param authDto
	 */
	public Map<Object, Object> login(AuthDto authDto) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		response.putAll(ErrorCode.FAIL.toMap());

		if (UtilsConfig.isNullOrEmpty(authDto.getId()) || UtilsConfig.isNullOrEmpty(authDto.getPassword())) {
			response.putAll(ErrorCode.PARAMETER_FAIL.toMap());
			return response;
		}

		try {
			AuthDto userDetail = authDao.userDetail(authDto);

			// 사용자 확인
			if (userDetail == null) {
				response.putAll(ErrorCode.USER_NOT_FOUND.toMap());
				return response;
			}

			// 비밀번호 확인
			if (!passwordEncoder.matches(authDto.getPassword(), userDetail.getPassword())) {
				response.putAll(ErrorCode.LOGIN_FAILED.toMap());
				return response;
			}

			AuthDto userDetailR2 = authDao.userDetail(authDto);

			String token = jwtUtil.createToken(authDto.getId(), authDto.getName(), userDetailR2.getUserId());

			response.putAll(ErrorCode.SUCCESS.toMap());
			response.put("token", token);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 로그아웃
	 * 
	 * @param
	 */
	public Map<Object, Object> logout(HttpServletRequest request) {
		Map<Object, Object> response = new HashMap<>();
		response.putAll(ErrorCode.FAIL.toMap());

		try {
			// 토큰 추출
			String token = extractToken(request);

			if (token == null || token.isEmpty()) {
				response.putAll(ErrorCode.PARAMETER_FAIL.toMap());
				return response;
			}

			// 토큰에서 userId 추출
			int userId = jwtUtil.extractUserId(token);

			// 토큰 유효성 검사 (만료된 토큰인지 검증)
			boolean isValidToken = jwtUtil.validateToken(token, userId);

			if (!isValidToken) {
				response.putAll(ErrorCode.INVALID_REQUEST.toMap());
				return response;
			}

			response.putAll(ErrorCode.SUCCESS.toMap());
		} catch (Exception e) {
			e.printStackTrace();
			response.putAll(ErrorCode.FAIL.toMap());
		}

		return response;
	}

	public String extractToken(HttpServletRequest request) {
		// Authorization 헤더에서 "Bearer <token>" 형식으로 토큰을 추출
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			return header.substring(7); // "Bearer "를 제외한 토큰만 반환
		}
		return null;
	}
}
