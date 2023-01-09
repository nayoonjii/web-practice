package net.daum.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

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
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model,HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		//브라우저에 출력되는 문자/태그, 언어코딩 타입을 설정
		
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		//serverTime키이름에 날짜 시간정보 저장
		return "home";//뷰페이지 경로->/WEB-INF/views/home.jsp(뷰리졸브 경로)
		//home.jsp에서 EL(표현언어)로 ${serverTime}으로 키이름을 참조해서 날짜 시간정보를 출력한다.
	}
	
}
