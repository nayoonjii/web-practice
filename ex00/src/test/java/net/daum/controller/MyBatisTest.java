package net.daum.controller;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/*.xml"})

public class MyBatisTest {

	@Autowired //@Ingect와 같이 자동의존성 주입
	private SqlSessionFactory sqlFactory ;
	
	@Test
	public void restFactory() {
		System.out.println(sqlFactory);
	}
	
	@Test
	public void testSession() {
		try(SqlSession sqlSession = sqlFactory.openSession()){
			System.out.println(sqlSession);
			//sqlSession은 mybatis에서 쿼리문 수행해주는 역할을 함
		}catch(Exception e) {e.printStackTrace();}
	}
	
}
