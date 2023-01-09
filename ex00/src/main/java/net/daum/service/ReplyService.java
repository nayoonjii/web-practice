package net.daum.service;

import java.util.List;

import net.daum.vo.ReplyVO;

public interface ReplyService {

	void insertReply(ReplyVO vo);//추상메서드로 {}가없고, 호출이 불가능. 본문문장이없다. 해당 메서드 리턴타입 앞에 
	//public abstract 가 생략됨.

	List<ReplyVO> listReply(int bno);

	void updateReply(ReplyVO vo);

	void deleteReply(int rno);

}
