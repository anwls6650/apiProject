package com.kmj.apiProject.login.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmj.apiProject.common.ErrorCode;
import com.kmj.apiProject.common.UtilsConfig;
import com.kmj.apiProject.login.dao.LoginDao;
import com.kmj.apiProject.login.dto.LoginDto;

@Service
public class LoginService {

	
	@Autowired
	LoginDao loginDao;
	
	
	public Map<Object, Object> login(LoginDto loginDto) {
        Map<Object, Object> response = new HashMap<Object, Object>();
        response.put("code", ErrorCode.FAIl) ;
        
        try {
        	LoginDto userDetail = loginDao.userDetail(loginDto);
        	
        	if(userDetail == null) {
        		response.put("code", ErrorCode.DUPLICATE_DATA);
        		return response;
        	}
        	
			
        	response.put("code", ErrorCode.SUCCESS) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
       
        
        return response;
    }
}
