package net.daum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.daum.dao.BoardDAO;
import net.daum.dao.ReplyDAO;
import net.daum.vo.ReplyVO;

@Service //이 애노테이션을 추가하면 스프링에 서비스 라는것을  인식하게된다.
public class ReplyServiceImpl implements ReplyService {//댓글서비스
	
	@Autowired
	private ReplyDAO replyDao;
	
	@Autowired //
	private BoardDAO boardDao;
	
	@Transactional //스프링aop를 통한 트랜잭션 적용
	@Override
	public void insertReply(ReplyVO vo) {
		this.replyDao.insertReaply(vo);
		this.boardDao.updateReplyCnt(vo.getBno(),1);//게시판 번호를 구해서 댓글 개수 1증가 업
	}//댓글이 추가되면 댓글 카운터 1증가

	@Override
	public List<ReplyVO> listReply(int bno) {
		return this.replyDao.listReply(bno);
	}

	@Override
	public void updateReply(ReplyVO vo) {
		replyDao.updateReply(vo);
	}

	@Transactional//트랜잭션적용
	@Override
	public void deleteReply(int rno) {
		int bno = this.replyDao.getBno(rno);//삭제되기전 댓글 번호에 해당하는 게시판 번호 구하기
		replyDao.deleteReply(rno);//댓글 삭제
		boardDao.updateReplyCnt(bno, -1);//댓글이 삭제되면 댓글수 1감소
	}//댓글이 삭제되면 댓글 수 1감소
	

}
