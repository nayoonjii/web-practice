package com.naver.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberVO {//member테이블의 컬럼명과 빈클래스 변수명을 같게 한다.회원관리 프로그램 중간에 
	//자료저장하는 데이터 저장빈 클래스
	
	private String mem_id;
	private String mem_pwd;
	private String mem_name;
	private String mem_zip;
	private String mem_zip2;
	private String mem_addr;
	private String mem_addr2;
	private String mem_phone01;
	private String mem_phone02;
	private String mem_phone03;
	private String mail_id;
	private String mail_domain;
	private String mem_date;
	private int mem_state; //가입회원이면 1,탈퇴회원이면2
	private String mem_delcont;
	private String mem_deldate;
	
	//관리자 회원 목록 페이징(쪽나누기)관련변수
	private int startrow;//시작행번호
	private int endrow;//끝행번호
	
	//검색기능 관련변수
	private String find_field;//검색필드=>테이블 해당 컬럼명과 일치
	private String find_name;//검색어
	

}
