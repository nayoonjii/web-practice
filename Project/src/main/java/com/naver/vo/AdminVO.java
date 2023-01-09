package com.naver.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminVO {//admin 관리자 테이블의 컬럼명과 빈클래스 변수명을 되도록이면 같게한다.

	private int admin_no;
	private String admin_id;//관리자 아이디
	private String admin_pwd;//관리자 비번
	private String admin_name;//관리자 이름
	private String admin_date;//등록 날짜
	
	
}
