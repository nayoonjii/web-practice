package net.daum.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import net.daum.vo.MemberVO;

@Getter
public class CustomUser extends User {
	
	private MemberVO member;
	
	public CustomUser(MemberVO vo) {
		super(vo.getUserid(),vo.getUserpw(),vo.getAuthList().stream()
				.map(auth ->new SimpleGrantedAuthority(auth.getAuth())).
				collect(Collectors.toList()));
		//검색된 아이디,비번, 권한정보를 람다식으로 목록을 수집한 다음 생성자를 호출해서 값을 전달하고
		//객체화 한다. 부모 User의 오버로딩 된 생성자를 호출하여 인자값을 전달한다.
		
		this.member=vo;
	}//생성자 오버로딩

	public CustomUser(String username,String password,
			Collection<? extends GrantedAuthority> authorities) {
		//GrantedAuthority 를 상속받은 자손타입으로만 제네릭 타입 형변환(제네릭 와일드카드 문자)
		//을 허용하면서 권한 목록을 구함.
		super(username,password,authorities);//부모의 오버로딩 된 생성자를 호출하면서 
		//아이디,비번,권한 목록을 전달 
	}
	
}
