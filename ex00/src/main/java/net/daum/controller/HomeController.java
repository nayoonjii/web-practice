package net.daum.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)//GET으로 접근하는 매핑주소를 처리
	//매핑주소는 value에 설정한/가 된다.
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		//서블릿으로 표현하면 request.setAttribute("serverTime",formattedDate);와 같은 기능을 한다.
		//serverTime 키이름에 날짜 값을 저장. 뷰페이지에서 EL즉 표현언어로 ${serverTime}키이름을 참조해서
		//날짜 값을 가져온다.
		return "home";
	}
	
	//비동기식 아작스 댓글 목록
	@RequestMapping("test") //test URL 매핑주소 등록
	public void test() {
		//메서드 리턴 타입이 없으면 매핑주소가 뷰페이지 파일명이 된다. 뷰리졸브(뷰페이지) 경로는 /WEB-INF/views/test.jsp
		
	}//test()
	
	
}










