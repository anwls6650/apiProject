package com.kmj.apiProject.driver.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kmj.apiProject.auth.dto.DriverDto;
import com.kmj.apiProject.common.config.ErrorCode;
import com.kmj.apiProject.common.config.UtilsConfig;
import com.kmj.apiProject.driver.dao.DriverDao;

@Service
public class DriverService {

	@Autowired
	DriverDao driverDao;

	private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public DriverService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper(); // ObjectMapper 생성
    }
	private static final String DRIVER_LOCATION_KEY = "driver:location";

	/**
	 * 기사 위치 저장
	 * 
	 * @param DriverDto
	 */ 
	public Map<Object, Object> location(DriverDto driverDto) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		response.putAll(ErrorCode.FAIL.toMap());

		try {

			// 기존에 있던 키 삭제
	        String driverLocationKey = DRIVER_LOCATION_KEY + driverDto.getDriverId();
	        redisTemplate.delete(driverLocationKey);
			
			 // JSON 형식
            String jsonLocation = objectMapper.writeValueAsString(driverDto);

            // Redis에 저장 
            redisTemplate.opsForValue().set(DRIVER_LOCATION_KEY+driverDto.getDriverId(), jsonLocation);

			response.putAll(ErrorCode.SUCCESS.toMap());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
	
	
	/**
	 * 기사 상태 변경
	 * 
	 * @param DriverDto
	 */ 
	public Map<Object, Object> status(DriverDto driverDto) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		response.putAll(ErrorCode.FAIL.toMap());
		
		if (driverDto.getStatus() == 0) {
			response.putAll(ErrorCode.PARAMETER_FAIL.toMap());
			return response;
		}

		try {

			driverDao.status(driverDto);

			response.putAll(ErrorCode.SUCCESS.toMap());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

}
