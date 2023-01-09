package net.daum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SampleController4 {

	@RequestMapping("/doE") //doE매핑주소 등록
	public String doE(RedirectAttributes rttr) {
		rttr.addFlashAttribute("cityName","부산시 해운대구");
		/* 백엔드 서버에서 다른 매핑주소로 이동시 cityName 키이름에 값을 담아서 전달한다.
		 * 웹브라우저 주소창에 노출 안됨. 보안상 좋다. 
		 */
		return "redirect:/doF";//doE url 매핑주소가 실행되면  doF매핑주소로 이동한다.
		//redirect로 이동하는 경우도 get 방식이다.
	}
	
	@GetMapping("/doF") //get으로 접근하는 매핑주소를 처리
	public void doF(@ModelAttribute("cityName")String name) {
		//리턴타입이 없는 void 형이면 매핑주소 doF가  뷰페이지파일명 doF.jsp가된다.
		System.out.println("전달된 도시이름 : "+ name);
	}
	
}
