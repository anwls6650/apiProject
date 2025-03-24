package com.kmj.apiProject.auth.dto;

import lombok.Data;

@Data
public class DriverDto {

	private int driverId; // 배달 기사 고유 ID
	private String name; // 기사 이름
	private String phoneNum; // 전화번호 (유니크)
	private String vehicleInfo; // 차량 정보
	private int status; // 1: 대기 중, 2: 배달 중, 3: 비활성
	private String createdAt; // 등록일
	private String password; // 비밀번호
	private String id; // 로그인 ID

}
