package net.daum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.daum.service.MessageService;
import net.daum.vo.MessageVO;

@RestController //rest api 서비스
@RequestMapping("/message")//컨트롤러 자체 매핑주소인 /message등록
public class MessageController {
	@Autowired
	private MessageService messageService;
	
	//메시지 추가
	@PostMapping("/addM") //post방식으로 접근하는 url매핑주소
	public ResponseEntity<String> addMessage(@RequestBody MessageVO vo){
		//@RequestBody MessageVO vo는 전송된json데이터를 MessageVO 객체 타입으로변경해준다.
		ResponseEntity entity = null;
		
		try {
			this.messageService.addMessage(vo);//메시지 추가
			entity = new ResponseEntity<>("SUCCESS",HttpStatus.OK);//추가 성공시 SUCCESS문자와 200 정상상태코드가 반환
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			//예외에러 메시지를 반환하고, 나쁜상태 문자여을 반환
		}
		return entity;
	}

}
