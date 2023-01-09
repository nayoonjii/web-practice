package net.daum.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.daum.vo.MemberVO;

@Repository //@Repository 애노테이션은 스프링에 모델 DAO라는 것을 인식하게 한다.
public class MemberDAOImpl implements MemberDAO {

	@Autowired
	private SqlSession sqlSession;//mybatis 쿼리문 수행할sqlSession을 생성
	
	@Override
	public void insertMember(MemberVO m) {
		this.sqlSession.insert("m_in", m);
		/* 1. m_in은 member.xml에서 설정할 유일한insert아이디명
		 * 2. mybatis 쿼리문 수행메서드
		 * 		가. insert() : 레코드 저장
		 * 		나. update() : 레코드 수정
		 * 		다. delete() : 레코드삭제
		 * 		라. selectOne() : 단, 한개의 레코드만 반환
		 * 		마. selectList(): 하나이상의 레코드를 검색해서 컬렉션 List로 주로 반환
		 */
	}

}
