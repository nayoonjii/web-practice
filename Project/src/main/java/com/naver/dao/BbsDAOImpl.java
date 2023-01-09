package com.naver.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.naver.vo.BbsVO;

@Repository //@Repository애노테이션 등록하면 스프링에서 모델DAO로인식
public class BbsDAOImpl implements BbsDAO {

	@Autowired
	private SqlSession sqlSession; //mybatis쿼리문 수행 sqlSession을 생성

	@Override
	public void insertBbs(BbsVO b) {
		this.sqlSession.insert("bbs_in", b);//mybatis에서 bbs_in은 bbs.xml매퍼태그에서
		//설정할 유일한아이디명이다.insert()메서드로 레코드를 저장한다.
	}//자료실 저장

	@Override
	public int getTotalCount(BbsVO b) {
		return this.sqlSession.selectOne("bbs_count",b);//mybatis에서 selectOne()메서드는
		//단한개의 레코드만 반환. bbs_count는 매퍼태그에서 설정할 유일한 아이디명.
	}//검색 전후 레코드 개수

	@Override
	public List<BbsVO> getBbsList(BbsVO b) {
		return this.sqlSession.selectList("bbs_list",b);//selectList()메서드는 하나이상의 복수개
		// 레코드를 검색 bbs_list는 매퍼태그에서 설정할 유일한 아이디명.
	}//검색전 목록

	@Override
	public void updateHit(int bbs_no) {
		this.sqlSession.update("bbs_hit",bbs_no);//mybatis에서update()메서드로 레코드를 수정,
		//bbs_hit는 bbs.xml에서 설정한 유일한 아이디명
	}//조회수 증가

	@Override
	public BbsVO getBbs_cont(int bbs_no) {
		return this.sqlSession.selectOne("bbs_cont",bbs_no);

	}

	@Override
	public void updateLevel(BbsVO rb) {
		this.sqlSession.update("bbs_levelUp",rb);
	}//답변 레벨증가

	@Override
	public void replyBbs(BbsVO rb) {
		this.sqlSession.insert("reply_in", rb);
	}//답변 저장

	@Override
	public void editBbs(BbsVO eb) {
		this.sqlSession.update("bbs_edit",eb);
	}//자료실 수정

	@Override
	public void delBbs(int bbs_no) {
		this.sqlSession.delete("bbs_del",bbs_no);//mybatis에서 delete()메서드는 레코드를 삭제
		//한다 bbs_del은 매퍼태그에서 설정할 유일한 아이디명
	}//자료실 삭제
} 
