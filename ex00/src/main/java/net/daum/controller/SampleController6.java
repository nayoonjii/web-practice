package net.daum.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.daum.vo.ReplyVO;
import net.daum.vo.SampleVO;

@RestController //스프링 4.0이후부터는 이 애노테이션으로 컨트롤러 자체가 REST api프로그램을 만들수 있게 된다.즉 jsp와같은 뷰페이지를
//만들지 않고도 원하는 json,xml,문자열 객체를 만들수 있다.
@RequestMapping("/sample")//컨트롤 자체 매핑주호 sample등록
public class SampleController6 {

	@RequestMapping("hello")//hello매핑주소 등록
	public String hello() {
		return "REST API Begin";
	}//hello()
	
	@RequestMapping(value="/sendVO",produces="application/json")
	public SampleVO sendVO() {
		//리턴타입이 SampleVO이면 빈클래스 변수명이 json 데이터의 키이름이된다.
		SampleVO vo = new SampleVO();
		vo.setMno(10);
		vo.setFirstName("홍");
		vo.setLastName("길동");
		
		return vo;
	}//sendVO()
	
	//컬렉션 List JSON객체 타입이 만들어짐. [{},{},{}]
	@RequestMapping(value="/sendList",produces="application/json") //sendList매핑주소 등록
	public List<SampleVO> sendList(){
		List<SampleVO> list = new ArrayList<>();
		
		for(int i=1;i<=3;i++) {
			SampleVO vo = new SampleVO();
			vo.setMno(i);
			vo.setFirstName("세종");
			vo.setLastName("대왕님");
			
			list.add(vo);//컬렉션에 추가
			
		}
		return list;
	}//sendList()
	
	//영어 사전적인 자료구조인 키,값 쌍의 Map타입 JSON을 반환
	@RequestMapping(value="/sendMap",produces="application/json")
	public Map<Integer,SampleVO> sendMap(){
		Map<Integer,SampleVO> map = new HashMap<>();
		
		for(int i=0;i<3; i++) {
			SampleVO vo = new SampleVO();
			
			vo.setMno(i);
			vo.setFirstName("신");
			vo.setLastName("사임당");
			
			map.put(i, vo);//키,값 쌍으로 저장
			
		}
		return map;
	}//sendMap()
	
	@GetMapping("/sendError") //get방식 매핑주소인 sendError를 처리
	public ResponseEntity<Void> sendError(){
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//400 HTTP나쁜 상태 코드가 문자가브라우저로 전송됨.
		/* 1.@RestController 는 별도의 jsp 뷰페이지를 만들지 않고 백엔드 서버 REST API 서비스를 개발하다보니 글과 데이터에 대한 예외적인 
		 * 		오류가 발생할수있다.그러므로 스프링에서 제공하는 api인 ResponseEntity를 사용하면 좀 더 세밀한 에러 제어를 할수있다.
		 * 		예를 들면 비정상적인 예외오류 404,500같은 나쁜 HTTP 상태코드를 브라우저로 전송하기때문에 
		 * 		이를 참조하여 개발자의 빠른 대처가 가능하다 
		 */
	}//sendError()
	
	//정상적인 json데이터와404(해당 자원 또는 파일등을 경로에서 찾지 못했을때) 나쁜상태http코드가 브라우저로 전송
	@RequestMapping(value="/sendErrorNot",produces="application/json")
	public ResponseEntity<List<SampleVO>> sendErrorNot(){
		List<SampleVO> list = new ArrayList<>();
		
		for(int i=10;i>=6;i--) {
			SampleVO sv = new SampleVO();
			sv.setMno(i);
			sv.setLastName("길동");
			sv.setFirstName("홍");
			
			list.add(sv);
		}
		return new ResponseEntity<List<SampleVO>>(list,HttpStatus.NOT_FOUND);//NOT_FOUND는 나쁜상태코드중 파일없음
	}//sendErrorNot()
	

	
}











