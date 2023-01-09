package com.naver.vo;

import lombok.Data;

@Data //setter() getter() toString()등 기본생성자 등을 자동생성
public class ZipcodeVO {//zipcode테이블의 우편주소 레코드를 저장할 빈클래스
	
	private int no;
	private String zipcode;//우편번호
	private String sido;//시도
	private String gugun;//구군
	private String dong;//동
	private String bunji;;//번지

}
