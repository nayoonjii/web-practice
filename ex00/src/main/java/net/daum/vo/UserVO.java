package net.daum.vo;

public class UserVO {//중간에 데이터 저장하는 데이터 저장빈 클래스
	
	private String name;//이름
	private String addr;//주소

	public UserVO(String name,String addr) {
		this.name=name;
		this.addr=addr;//멤버변수 초기화
	}//매개변수 개수를 다르게 한 생성자 오버로딩

	public String getName() {
		return name;
	}

	public String getAddr() {
		return addr;
	}//getter() 메서드 정의				
}