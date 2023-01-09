package net.daum.vo;

import lombok.Data;

@Data
public class BoardExVO {

	private int bno;
	private String title;
	private String writer;
	private int viewcnt;//조회수
	private int replycnt;//댓글 카운터
	private String regdate;//등록날짜
	
	//페이징 관련변수(쪽나누기)
	private int startrow;//시작행번호
	private int endrow;//끝행번호
	
}
