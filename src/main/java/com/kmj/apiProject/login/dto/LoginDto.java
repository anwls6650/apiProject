package com.kmj.apiProject.login.dto;

import lombok.Data;

@Data
public class LoginDto {
	
	
	private String username; 
	private String id; 
	private String password; 
	private String email; 
	private String createdAt; 
	private String updatedAt; 
	private String role; 

}
