package com.naver.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.naver.vo.BbsVO;

@Repository
public class AdminBbsDAOImpl implements AdminBbsDAO {

	@Autowired//DI설정
	private SqlSession sqlSession;

	@Override
	public int getTotalCount(BbsVO b) {
		return this.sqlSession.selectOne("abbs_row",b);
	}//관리자 자료실 검색 전후 레코드 개수,관리자라서 admin접두어 a붙여서 abbs_~

	@Override
	public List<BbsVO> getBbsList(BbsVO b) {
		return this.sqlSession.selectList("abbs_list",b);
	}//관리자 자료실 검색 전후 목록

	@Override
	public void insertBbs(BbsVO b) {
		this.sqlSession.insert("abbs_in", b);
	}//관리자자료실 저장

	@Override
	public BbsVO getBbsCont(int bbs_no) {
		return this.sqlSession.selectOne("abbs_cont", bbs_no);
	}//관리자 자료실 내용보기+수정폼

	@Override
	public void editBbs(BbsVO b) {
		this.sqlSession.update("abbs_edit",b);
	}//관리자 자료실 수정

	@Override
	public void delBbs(int bbs_no) {
		this.sqlSession.delete("abbs_del",bbs_no);
	}//관리자 자료실 삭제
	
}
