package net.daum.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.daum.vo.ReplyVO;

@Repository //@Repository 애노테이션을 등록 함으로써 스프링에 모델dao라는 것을 인식하게 한다.
public class ReplyDAOImpl implements ReplyDAO {//댓글 DAO
	
	@Autowired
	private SqlSession sqlSession;//sqlSession은 mybatis 쿼리문 수행

	@Override
	public void insertReaply(ReplyVO vo) {
		this.sqlSession.insert("reply_in",vo);
		//insert()메서드는 레코드 저장, reply_in은 reply.xml에서 설정할 유일 아이디명
	}//댓글저장

	@Override
	public List<ReplyVO> listReply(int bno) {
		return sqlSession.selectList("reply_list",bno);//selectList()메서드는 하나이상의 레코드 목록을 반환하고,reply_list
		//는 reply.xml에서 설정한 유일한 아이디 명이다.
	}//댓글 목록

	@Override
	public void updateReply(ReplyVO vo) {
		sqlSession.update("reply_edit",vo);//update()로 레코드 수정,reply_edit는 reply.xml에서 설정할 유일한 아이디
	}//댓글수정

	@Override
	public void deleteReply(int rno) {
		sqlSession.delete("reply_del",rno);//delete()메서드로 레코드를 삭제,reply_del은 유일한 아이디명이다.
	}//댓글 삭제

	@Override
	public int getBno(int rno) {
		return this.sqlSession.selectOne("reply_bno",rno);//selectOne()메서드로 단한개의 레코드 반환
	}//댓글 번호에 해당하는 게시판 번호 구하기
	
	

}
