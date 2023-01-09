package net.daum.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageVO {//tbl_message 테이블의 컬럼명과 빈클래스 변수명을 같게 한다. 자바 저장빈 클래스

	private int mid;
	private String targetid;
	private String sender;//메시지를 보낸사람
	private String message;
	private String senddate; //메시지를 보낸 날짜
	
}
