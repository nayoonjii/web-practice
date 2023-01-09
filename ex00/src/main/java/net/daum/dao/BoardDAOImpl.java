package net.daum.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.daum.vo.BoardVO;

@Repository //스프링에서 dao로 인식하게 한다.->스프링 mvc게시판에서 모델dao
public class BoardDAOImpl implements BoardDAO {

	@Inject //자동 의존성 주입
	private SqlSession sqlSession;//mybatis쿼리문 수행 sqlSession 생성

	@Override
	public void insertBoard(BoardVO b) {
		this.sqlSession.insert("b_in",b);//mybatis ORM 프레임웤에서 insert()메서드는 레코드 저장
		//b_in은 board.xml매퍼태그에서 설정할 유일한 아이디명
	}//게시판저장

	@Override
	public int getRowCount() {
		return sqlSession.selectOne("b_count");//mybatis에서 selectOne()메서드는 단한개의 레코드만 반환
		//b_count는 board.xml에 설정할 중복되지 않은 유일한 아이디 명이다.
	}//총 레코드 개수

	@Override
	public List<BoardVO> getBoardList(BoardVO b) {
		return this.sqlSession.selectList("b_list",b);//mybatis에서 selectList()메서드는 하나이상의
		//레코드를 검색해서 반환. b_list는 board.xml에서 설정하는 유일한 아이디 명이다.
	}//목록을 가져옴

	@Override
	public void updateHit(int bno) {
		sqlSession.update("b_hit",bno);//mybatis에서 update()메서드로 레코드를 수정한다.
		//b_hit는 board.xml에서 설정할 유일한 아이디명(중복되면 안된다)
	}//조회수 증가

	@Override
	public BoardVO getBoardCont(int bno) {
		return sqlSession.selectOne("b_cont",bno);
	}//내용보기

	@Override
	public void editBoard(BoardVO eb) {
		sqlSession.update("b_edit", eb);
	}//수정

	@Override
	public void delBoard(int bno) {
		sqlSession.delete("b_del",bno);//mybatis에서 delete()메서드는 레코드를 삭제한다. b_del은 유일한 매퍼태그 아이디명
	}//삭제

	@Override
	public void updateReplyCnt(int bno, int count) {
		Map<String,Object> cm = new HashMap<>();
		cm.put("bno", bno);
		cm.put("count", count);
		
		this.sqlSession.update("updateReplyCnt",cm);//updateReplyCnt는 매퍼태그에서 설정하는 유일한 아이디명
	}//댓글 개수 추가 업데이트

	
	
}
