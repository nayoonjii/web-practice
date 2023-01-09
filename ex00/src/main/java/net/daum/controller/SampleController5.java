package net.daum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.daum.vo.ProductVO;

@Controller

public class SampleController5 {

	@RequestMapping(value="/doJSON",produces = "application/json")
	public @ResponseBody ProductVO doJSON() {
		/* @ResponseBody 애노테이션을 추가하면 jsp파일을 만들지 않고도 브라우저 출력창에 키,값 쌍의 json
		 * 데이터를 쉽게 만들수 있다. 메서드 리턴타입이ProductVO빈클래스 이면 해당 빈클래스의 변수명이
		 * JSON데이터의 키 이름이 된다.브라우저에 실행되는 url 매핑주소는 doJSON이다. 
		 */
		ProductVO p = new ProductVO("포도",12434);
		return p;
	}
	
}
