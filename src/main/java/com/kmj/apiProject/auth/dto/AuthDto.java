package com.kmj.apiProject.auth.dto;

import lombok.Data;

@Data
public class AuthDto {
	
	
	private String name; 
	private String id; 
	private String password; 
	private String email; 
	private String createdAt; 
	private String updatedAt; 
	private String role; 
	private String phoneNum;

}
