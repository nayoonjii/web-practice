package net.daum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.daum.vo.UserVO;
@Controller
public class AddrController {

	@RequestMapping(value="/p_addr",produces = "application/json")
	public @ResponseBody UserVO p_addr() {
		UserVO vo = new UserVO("홍길동", "서울시");
		return vo;
	}
}
