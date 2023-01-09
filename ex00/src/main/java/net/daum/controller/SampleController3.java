package net.daum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.daum.vo.ProductVO;

@Controller
public class SampleController3 {

	@RequestMapping("/name_price") // name_price매핑주소 등록
	public String name_price(Model m) {
		ProductVO p = new ProductVO("스탠드에어컨", 3000000);
		m.addAttribute("p", p);//서블릿을 request.setAttribute("p",p); 같은 기능이다.
		//p키이름에 p객체를 저장
		System.out.println(p.toString());
		return "shop/pro_name";//뷰리졸브 경로는 /WEB-INF/views/shop/pro_name.jsp
	}
	
}
