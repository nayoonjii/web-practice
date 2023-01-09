package net.daum.vo;

import lombok.Data;

@Data//setter(),getter(),toString()등 제공
public class AuthVO {//권한 정보를 저장하는 데이터 저장빈 클래스

	private String userid;
	private String auth;
	
	
}
