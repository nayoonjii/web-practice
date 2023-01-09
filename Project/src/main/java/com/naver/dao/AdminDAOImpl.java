package com.naver.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.naver.vo.AdminVO;

@Repository //이 애노테이션을 사용함으로써 스프링에 모델DAO라는걸 인식하게한다.
public class AdminDAOImpl implements AdminDAO {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insertAdmin(AdminVO ab) {
		this.sqlSession.insert("admin_in",ab);
	}//관리자 정보 저장

	@Override
	public AdminVO adminLoginCheck(String admin_id) {
		return sqlSession.selectOne("admin_login",admin_id);
		//mybatis에서selectOne()은 하나의 레코드 값만 반환한다.
	}//관리자 로그인 인증
	
	

}
