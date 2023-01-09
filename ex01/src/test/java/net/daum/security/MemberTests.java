package net.daum.security;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"
})
public class MemberTests {

	@Setter(onMethod_ = @Autowired)//setter()를 통한 자동 의존성 주입
	private PasswordEncoder pwencoder;
	
	@Setter(onMethod_ = @Autowired)
	private DataSource ds; //커넥션 풀 관리 ds(JNDI 디렉토리 서비스에 의한 dbcp)
	
	@Test
	public void testInsertMember() {
		String sql="insert into tbl_member (userid,userpw,username) values("
				+"?,?,?)";
		for(int i=0; i<100;i++) {
			Connection con = null;//오라클 연결
			PreparedStatement pstmt = null;//쿼리문 수행
			
			try{
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(2, pwencoder.encode("pw"+i));//pw0~pw99까지 비번 암호화
				
				if(i<80) {//0부터 79까지
					pstmt.setString(1, "user"+i);//user0~user79까지 일반 사용자 회원아이디
					pstmt.setString(3, "일반사용자"+i);//일반사용자0~79 회원이름
				}else if(i<90) {//88~89
					pstmt.setString(1,"manager"+i);//manager80~89
					pstmt.setString(3,"운영자"+i);//운영자80~89
				}else {//90~99
					pstmt.setString(1,"admin"+i);//admin90~99
					pstmt.setString(3,"관리자"+i);//관리자90~99
				}//if else if
				
				pstmt.executeUpdate();//저장쿼리문수행
				
			}catch(Exception e) {e.printStackTrace();}
			finally {
				try {
					if(pstmt != null) pstmt.close();
					if(con != null) con.close();
				}catch(Exception e) {e.printStackTrace();}
			}
		}
	}//testInsert()

	@Test
	public void testInsertAuth() {
		
		String sql="insert into tbl_member_auth (userid,auth) values(?,?)";
		
		for(int i=0;i<100;i++) {
			Connection con = null;//db연결
			PreparedStatement pstmt = null;//쿼리문 수행
			
			try {
				con = ds.getConnection();//커넥션 풀ds로 con생성
				pstmt = con.prepareStatement(sql);
				
				if(i<80) {
					pstmt.setString(1,"user"+i);
					pstmt.setString(2,"ROLE_USER");
				}else if(i<90) {
					pstmt.setString(1,"manager"+i);
					pstmt.setString(2,"ROLE_MEMBER");
				}else {
					pstmt.setString(1,"admin"+i);
					pstmt.setString(2,"ROLE_ADMIN");
				}
				
				pstmt.executeUpdate();
				
			}catch(Exception e) {e.printStackTrace();}
			finally {
				try {
					if(pstmt != null) pstmt.close();
					if(con != null) con.close();
				}catch(Exception e) {e.printStackTrace();}
			}
			
		}//for
	}//testInsertAuth()
	
	
	
}








