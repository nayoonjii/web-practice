package net.daum.controller;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

/*자바 코드로 해당 ex00프로젝트에서 추가한 오라클 ojdbc8.jar를 통한 데이터 베이스 연결 테스트JUnit연습용 파일*/
public class OracleConTest {

	private static final String driver = "oracle.jdbc.OracleDriver";//oracle.jdbc는 패키지명,
	//OracleDriver는 jdbc드라이버 클래스명
	private static final String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	// 오라클 접속주소, 1521은 포트번호, xe는 데이터 베이스 명
	private static final String user = "night";//오라클 접속 사용자
	private static final String password = "night";//사용자 비번
	
	@Test //JUnit 테스트 연습용 애노테이션
	public void testOracleCon() throws Exception{
		Class.forName(driver);//jdbc드라이버 클래스명
		
		try (Connection con = DriverManager.getConnection(url, user, password)){
			/* 자바7버전에 추가된 AutoCloseable 인터페이스를 구현 상속받은 자손은 try()내에서 인스턴스를
			 * 생성하면 굳이 finally 문에서 close()하지않아도 자동으로 닫힌다
			 */
			System.out.println(con);
		}catch(Exception e) {e.printStackTrace();}
	}
	
}
