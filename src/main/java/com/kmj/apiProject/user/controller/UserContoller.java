package com.kmj.apiProject.user.controller;

import java.util.Map;
import com.kmj.apiProject.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kmj.apiProject.auth.dto.AuthDto;


@Controller
@RestController("/kmj/user")
public class UserContoller {


	
	// SLF4J Logger 생성
	private static final Logger logger = LoggerFactory.getLogger(UserContoller.class);
		
		
	@Autowired
	UserService userService;



//	@PostMapping("")
//	@ResponseBody
//	public Map<Object, Object> signUp(@RequestBody AuthDto loginDto) {

//		logger.info("/kmj/user : {}", loginDto);

//		return userService;

//	}
}
