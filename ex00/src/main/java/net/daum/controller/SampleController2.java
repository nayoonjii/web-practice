package net.daum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SampleController2 {

	@GetMapping("doC")//get으로 접근하는 것을 처리하기위한 doC매핑주소 등록
	public String doC(@ModelAttribute("msg2")String msg) {
		/* @ModelAttribute("msg2")는 msg2피라미터 이름에 값을 담아서 get방식으로 전달한다.
		 * doC?msg2=값 형태의 쿼리스트링 방식으로 url주소창서 실행해야 한다. 
		 */
		System.out.println("전달된 값: "+msg);
		return "result";//뷰리졸브 경로(뷰페이지 경로)는 /WEB-INF/views/result.jsp
	}
}
