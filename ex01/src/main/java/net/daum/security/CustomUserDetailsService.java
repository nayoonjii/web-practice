package net.daum.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.Setter;
import net.daum.dao.MemberMapperDAO;
import net.daum.security.domain.CustomUser;
import net.daum.vo.MemberVO;

public class CustomUserDetailsService implements UserDetailsService {

//UserDtailsService는 별도의 인증/권한 체크를 하는 이유는 jsp등에서 단순히 사용자 아이디(스프링
//시큐리티에서는 username)가 아닌 이메일 이나 사용자 이름 같은 추가적인 정보를 이용하기 위해서이다.
	
	@Setter(onMethod_ = {@Autowired}) //자동의존성 주입
	public MemberMapperDAO memberMapperDao;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		System.out.println("Load User By UserName : " + username);
		
		MemberVO vo = this.memberMapperDao.readMember(username);//db로 부터 
		//아이디에 해당하는 회원정보를 구함
		System.out.println("queried by member Info : "+vo);
		return (vo==null)?null:new CustomUser(vo);//생성자를 호출해서 검색된 회원정보
		//값을 넘김. 그런다음 생성된 객체를 반환.
	}

}
