package com.kmj.apiProject.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kmj.apiProject.login.dto.LoginDto;
import com.kmj.apiProject.login.service.LoginService;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/kmj/user")
public class LoginController {

	
	// SLF4J Logger 생성
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    LoginService loginService;
    
    
    
    @PostMapping("/signUp")
    @ResponseBody
    public Map<Object, Object> signUp(@RequestBody LoginDto loginDto) {
    	
    	logger.info("/kmj/signUp : {}", loginDto);

    	return loginService.signUp(loginDto);
 
        
    }
    
}
