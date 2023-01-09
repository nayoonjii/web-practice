package net.daum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //@Controller 애노테이션을 사용하면 스프링에서 컨트롤러로 인식해서 브라우저에서 실행한다.
public class SampleController {

	@RequestMapping("doA") //get or post 방식으로 접근하는 doA매핑주소를 처리한다. 매핑주소란 브라우저 
	//주소창에서 실행되는 URL매핑 주소값이다.(=url-pattern)
	public void doA7() {//메서드 리턴타입이 없는void 형이면 자동으로 doA.jsp매핑주소가 뷰페이지 파일명으로 인식한다.
		System.out.println("doA 매핑주소가 실행됨.");//자동으로 파일명이 되었지만
		//doA.jsp뷰페이지를 만들지 않아서 화면에는 파일없음 404에러가 발생한다.
		
	}
	
	@GetMapping("doB") //get으로 접근하는 doB매핑주소가 실행됨
	public void doB() {//리턴타입이 없기 때문에 뷰페이지 파일명은 매핑주소인 doB.jsp 
		System.out.println("doB 매핑주소가 실행");
	}
	
}
