package com.kmj.apiProject.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmj.apiProject.user.dao.UserDao;

@Service
public class UserService {
	
	
	@Autowired
	UserDao userDao;
	

}
