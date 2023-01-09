package net.daum.controller;

import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/* root-context.xml에 정의된 jdbc정보를 이용해서 오라클을 연결 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/*.xml"})
public class DataSourceTest {

	@Inject //자동 의존성 주입(DI)
	private DataSource ds;//커넥션 풀을 사용한 ds
	
	@Test
	public void sprintTestCon() throws Exception{
		try(Connection con = ds.getConnection()){
			//커넥션 풀 관리 ds로 디비 연결 con을 생성
			System.out.println(con);
		}catch(Exception e) {e.printStackTrace();}
	}
	
}
