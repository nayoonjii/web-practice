package net.daum.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.daum.vo.SampleVO;

@RestController
public class RestTestController {

	@RequestMapping(value="/sendList",produces="application/json")
	public List<SampleVO> sendList(){
		
		List<SampleVO> list = new ArrayList<>();
		
		for(int i=1;i<=2;i++) {
			SampleVO vo = new SampleVO();
			
			vo.setMno(i);
			vo.setFirstName("세종");
			vo.setLastName("대왕님");
			
			list.add(vo);
		}
		return list;
	}
}
