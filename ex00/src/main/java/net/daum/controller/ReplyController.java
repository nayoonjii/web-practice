package net.daum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.daum.service.ReplyService;
import net.daum.vo.ReplyVO;

@RestController //이 애노테이션을 설정함으로써 뷰페이지를 만들지 않고도 원하는 문자열,xml,json타입 자료를 만들수있다
@RequestMapping("/replies")//컨트롤러 자체에 replies 매핑주소 등록
public class ReplyController {//댓글 REST API 컨트롤러

	@Autowired
	private ReplyService replyService;
	
	//댓글등록
	@RequestMapping("/addreply") //post로 접근하는 매핑주소 처리
	public ResponseEntity<String> addReply(@RequestBody ReplyVO vo){
		//@RequestBody는 전송된 json데이터를 ReplyVO타입으로 변환해준다.
		ResponseEntity<String> entity = null;
		
		try {
			this.replyService.insertReply(vo);//댓글저장
			entity = new ResponseEntity<>("SUCCESS",HttpStatus.OK);//댓글등록 성공시 SUCCESS문자가 반환되고,
			//Http 상태 코드 정상을 뜻하는 200(저장 성공한 경우)을 반환한다.
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			//예외 에러가 발생했을때 에러 메시지와 나쁜 상태 코드 문자열이 반환
		}
		return entity;
	}//addReply()
	
	//게시판 번호에 해당하는 댓글 목록
	@RequestMapping(value="/all/{bno}",produces="application/json")//get방식으로 접근하는 매핑주소를 처리
	public ResponseEntity<List<ReplyVO>> replyList(@PathVariable("bno") int bno){
		// /all/{bno}에서 {bno}에는 게시판 번호가 들어간다. 이번호값은 @PathVariable("bno")로 가져온다.
		ResponseEntity<List<ReplyVO>> entity = null;
		
		try {
			entity = new ResponseEntity<>(this.replyService.listReply(bno),
					HttpStatus.OK);//게시파 번호에 대한 댓글 목록
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}//replyList()
	
	//댓글 수정
	@RequestMapping(value="/{rno}",method={RequestMethod.PUT, RequestMethod.PATCH})
	//PUT은 전체자료 수정, PATCH는 일부자료 수정
	public ResponseEntity<String> editReply(@PathVariable("rno") int rno, @RequestBody ReplyVO vo){
		ResponseEntity<String> entity = null;
		
		//rno는 주소창에서 구해주는 댓글 번호이다. 즉json데이터가 아니다. 그러므로 @RequestBody 에의해서json데이터가 ReplyVO
		//타입으로 변경 안된다. 코드로 따로 저장해야한다.
		
		try {
			vo.setRno(rno);//댓글번호 따로 저장
			this.replyService.updateReply(vo);//댓글을 수정
			entity = new ResponseEntity<>("SUCCESS",HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}//editReply()
	
	//댓글 삭제
	@RequestMapping(value="/{rno}",method=RequestMethod.DELETE)
	//DELETE는 삭제
	public ResponseEntity<String> delReply(@PathVariable("rno") int rno){
		ResponseEntity<String> entity = null;
		
		try {
			replyService.deleteReply(rno);//댓글 번호를 기준으로 삭제
			entity = new ResponseEntity<>("SUCCESS",HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}//delReply()

}
