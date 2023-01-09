package net.daum.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class PointDAOImpl implements PointDAO {

	@Inject //자동의존성 주입
	private SqlSession sqlSession;//mybatis 쿼리문 수행 sqlSession생성

	@Override
	public void updatePoint(String sender, int point) {
		Map<String,Object> pm = new HashMap<>();
		pm.put("sender", sender);//키,값 쌍으로 저장. point.xml에서 키이름 sender를 참조해서 값을 가져온다.
		pm.put("point", point);
		
		this.sqlSession.update("pointUp", pm);//update()메서드로 레코드를 수정, pointUp은 매퍼태그
		//에서 설정한 유일한 아이디명.
	}
	
}
