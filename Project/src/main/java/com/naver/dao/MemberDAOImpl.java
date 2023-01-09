package com.naver.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.naver.vo.MemberVO;
import com.naver.vo.ZipcodeVO;

@Repository
public class MemberDAOImpl implements MemberDAO {

	@Autowired
	private SqlSession sqlSession;//mybatis쿼리문 수행할 sqlSession생성

	@Override
	public MemberVO idCheck(String id) {
		return this.sqlSession.selectOne("id_check",id);
	}//아이디 중복검색

	@Override
	public List<ZipcodeVO> zipFind(String dong) {
		return this.sqlSession.selectList("m_zip",dong);
	}//우편주소검색

	@Override
	public void insertMember(MemberVO m) {
		this.sqlSession.insert("m_in",m);
	}//회원저장

	@Override
	public MemberVO pwdMember(MemberVO m) {
		return this.sqlSession.selectOne("p_find",m);
	}//비번찾기에서 아이디와 회원이름에 맞는 회원 정보 검색

	@Override
	public void updatePwd(MemberVO m) {
		this.sqlSession.update("p_edit",m);
	}//암호화된 임시비번 수정

	@Override
	public MemberVO loginCheck(String login_id) {
		return this.sqlSession.selectOne("login_ck",login_id);
	}//로그인 인증

	@Override
	public MemberVO getMember(String id) {
		return this.sqlSession.selectOne("m_edit",id);
	}//아이디에 해당하는 회원정보 가져오기

	@Override
	public void updateMember(MemberVO em) {
		this.sqlSession.update("edit_ok", em);
	}//회원정보 수정 완료

	@Override
	public void delMem(MemberVO dm) {
		this.sqlSession.update("m_del", dm);
	}//회원 탈퇴
	
	
}
