package net.daum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import net.daum.dao.BoardDAO;
import net.daum.vo.BoardVO;

@Service//@Service를 추가하므로써 스프링에 서비스라는 것을 인식하게 한다.=>스프링 mvc게시판 서비스
public class BoardServiceImpl implements BoardService{
	
	@Autowired //자동의존성 주입
	private BoardDAO boardDao;

	@Override
	public void insertBoard(BoardVO b) {

		this.boardDao.insertBoard(b);
	}

	@Override
	public int getRowCount() {
		return boardDao.getRowCount();//this. 은 생략 가능함
	}

	@Override
	public List<BoardVO> getBoardList(BoardVO b) {
		return this.boardDao.getBoardList(b);
	}

	//스프링AOP를 통한 트랜잭션 적용대상
	@Transactional(isolation = Isolation.READ_COMMITTED) //트랜잭션 격리(트랜잭션이 처리되는 중간에
	//외부의 간섭을 제거.READ_COMMITTED는 커밋된 데이터에 대해 읽기 허용
	@Override
	public BoardVO getBoardCont(int bno) {
		this.boardDao.updateHit(bno);
		/* 문제)조회수 증가되게만들어보자. 목록에서 증가된 조회수를 확인한다.
		 * 수정쿼리문은 mybatis에서update()메서드를 사용하고, 아이디명은 b_hit로 한다. */
		return boardDao.getBoardCont(bno);
	}//조회수 증가+내용보기

	@Override
	public BoardVO getBoardCont2(int bno) {
		return this.boardDao.getBoardCont(bno);
	}//내용보기

	@Override
	public void editBoard(BoardVO eb) {
		this.boardDao.editBoard(eb);
}

	@Override
	public void delBoard(int bno) {
		this.boardDao.delBoard(bno);
	}
	
	
}
