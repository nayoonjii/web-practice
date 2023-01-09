package com.naver.vo;

import lombok.Getter;
import lombok.Setter;

@Setter//setter()메서드 자동제공
@Getter//getter()메서드 자동제공
public class BbsVO {//자료실 데이터 저장빈 클래스
	//되도록이면 bbs테이블의 컬럼명과 빈클래스 변수명을 같게 한다.
	
	private int bbs_no;
	private String bbs_name;
	private String bbs_title;
	private String bbs_pwd;
	private String bbs_cont;
	private String bbs_file;
	private int bbs_hit;
	private int bbs_ref;//글 그룹 번호=>원본글과 답변글을 묶어주는 기능
	private int bbs_step;//원본글이면 0,첫번째 답변글이면1,두번째답변글이면 2 결국 원본글과
	//답변글을 구분하는 번호값이면서 몇번째 답변글인가를 알려준다.
	private int bbs_level;//답변글 정렬 순서
	private String bbs_date;

	//페이징 관련변수
	private int startrow;//시작행 번호
	private int endrow;//끝행 번호
	
	//검색 필드와 검색어
	private String find_field;//검색필드
	private String find_name;//검색어
	
}
