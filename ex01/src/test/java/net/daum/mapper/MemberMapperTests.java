package net.daum.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.Setter;
import net.daum.dao.MemberMapperDAO;
import net.daum.vo.MemberVO;

@RunWith(SpringRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class MemberMapperTests {
	
	@Setter(onMethod_=@Autowired)//의존성주입
	private MemberMapperDAO mapper;

	@Test
	public void testRead(){
		MemberVO vo = mapper.readMember("admin90");//admin90아이디에 해당하는정보를 읽어옴
		
		System.out.println(vo);
		vo.getAuthList().forEach(authVO -> System.out.println(authVO));
		
	}
	
}
