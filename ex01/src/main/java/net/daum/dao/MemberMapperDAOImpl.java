package net.daum.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.daum.vo.MemberVO;

@Repository
public class MemberMapperDAOImpl implements MemberMapperDAO {

	@Autowired//자동의존성 DI 설정
	private SqlSession sqlSession;//mybatis쿼리문 수행 sqlSession을 생성

	@Override
	public MemberVO readMember(String id) {
		
		return this.sqlSession.selectOne("read_memInfo", id);
		//mybatis에서 selectOne()메서드는 단 한개의 레코드 값만 읽어옴. read_memInfo는 매퍼태그
		//에서 설정할 유일한 아이디명.
	}//아이디에 해당하는 회원정보(권한정보) 읽어오기
	
	
}
