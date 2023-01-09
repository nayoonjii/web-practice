package net.daum.vo;

import lombok.Data;

@Data //setter(),getter()메서드 자동생성
public class MemberVO {//테이블 컬럼명과 빈클래스 변수명을 같게한다.
	private String userid;
	private String userpw;
	private String username;
	private String email;
	private String regdate;
	private String updatedate;
	
	

}
