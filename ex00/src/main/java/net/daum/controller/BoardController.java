package net.daum.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.daum.service.BoardService;
import net.daum.vo.BoardVO;

@Controller//스프링 mvc게시판 컨트롤러 클래스
@RequestMapping("/board/*") //컨트롤러에 매핑주소 등록
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	//스프링 게시판 글쓰기
	@RequestMapping(value="/board_write",method=RequestMethod.GET)// get으로 접근하는 매핑주소 처리.
	//board_write 매핑주소 등록
	public void board_write(HttpServletRequest request) {
		//HttpServletRequest는 서블릿api
		int page=1;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));//페이징에서 쪽번호를
			//정수 숫자로 바꿔서 저장 -> 내가본 쪽번호로 이동하기 위한 책갈피 기능
		}
		request.setAttribute("page", page);//책갈피 기능을 구현하기 위해서 page키이름에 쪽번호 저장
		 /* 기존에 학습했던 서블릿을 스프링 컨트롤러에서 적용가능하다. 리턴타입이 없는 void형이면 매핑주소가
		  * 뷰페이지 파일명이된다. board_write.jsp
		 */
	}//board_write()
	
	//게시판 저장
	@RequestMapping(value="/board_write",method=RequestMethod.POST) //post로 접근하는 매핑주소
	//를 처리. 폼태그에서 액션 속성을 지정하지 않으면 이전 매핑주소가 액션매핑주소가 된다. 같은 매핑주소 구분은 메서드 방식인
	//get or post 로 구분한다.
	public ModelAndView board_write_ok(BoardVO b,RedirectAttributes rttr) {
		/* board_write.jsp의 네임 피라미터 이름과 빈클래스 변수명이 같으면 baordVO b라고 하면 b객체에 저장할 글쓴이,
		 * 글제목,글내용이 저장되어있다. 코드라인을 줄이는 효과가 발생한다. 
		 */
		this.boardService.insertBoard(b);//게시판 저장.this.은 생략가능함
		rttr.addFlashAttribute("msg","SUCCESS");//서버컨트롤로에서 다른 매핑주소로 이동시 값을 전달할 때 
		//사용한다. msg키이름에 SUCCESSS문자를 담아서 전달한다. 주소창에 노출안된다. 보안이 좋다.
		return new ModelAndView("redirect:/board/board_list");//board_list매핑주소로 이동
	}//board_write_ok()
	
	//게시판 목록
	@GetMapping("/board_list") //get으로 접근하는 매핑주소 처리,board_list 매핑주소 등록
	public String board_list(Model m,BoardVO b,HttpServletRequest request) {
		
		int page=1;
		int limit=5; //한페이지에 보여지는 목록 개수 
		if(request.getParameter("page")!= null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		b.setStartrow((page-1)*5+1);//시작행 번호
		b.setEndrow(b.getStartrow()+limit-1);//끝행 번호
		//결국 한페이지에 검색되는 목록개수가 5개로 제한
		
		int totalCount=this.boardService.getRowCount();//총 레코드 개수
		List<BoardVO> blist = this.boardService.getBoardList(b);//개시판 목록
		
		/*페이징 즉 쪽나누기와 관련연산*/
		int maxpage=(int)((double)totalCount/limit+0.95);//총페이지수
		int startpage = (((int)((double)page/10+0.9))-1)*10+1;//시작페이지
		int endpage = maxpage;//현재 페이지에 보여질 마지막 페이지
		
		if(endpage > startpage +10-1) endpage=startpage+10-1;
		
		m.addAttribute("list",blist);//list키이름에 목록저장
		m.addAttribute("totalCount",totalCount);
		m.addAttribute("startpage",startpage);
		m.addAttribute("endpage",endpage);
		m.addAttribute("maxpage",maxpage);
		m.addAttribute("page",page);
		return "board/board_list";//뷰페이지 경로
	}//board_list()
	
	//내용보기와 조회수 전달
	@RequestMapping("board_cont") //get or post 로 접근하는 매핑주소 처리
	public ModelAndView board_cont(@RequestParam("bno")int bno, @RequestParam("page")
	int page) {
		/* @RequestParam("bno")는 서블릿의 request.getParameter("bno")와 같은 기능을 한다.
		 */
		BoardVO bc=boardService.getBoardCont(bno);//번호에 해당하는 레코드를 가져오고, 그전에 조회수 증가
		String cont=bc.getContent().replace("\n", "<br/>");//textarea입력박스에서 엔터키 친부분을
		//다음줄로 개행(줄바꿈)
		 
		ModelAndView cm = new ModelAndView();
		cm.addObject("b",bc);//b키이름에 bc객체를 저장
		cm.addObject("cont", cont);
		cm.addObject("page", page);
		cm.setViewName("board/board_cont2");//뷰페이지 경로설정->/WEB-INF/views/board/board_cont.jsp
		return cm;	
	}
	
	//수정폼
	@GetMapping("board_edit") //get 으로 접근하는 매핑주소 처리, board_edit매핑주소 등록
	public String board_edit(Model em,int bno,int page) {//int bno,int page라고 get으로 전달된 글번호와 쪽번호를 받는다.
		
		BoardVO eb = this.boardService.getBoardCont2(bno);//조회수는 증가되지 않고, 번호에 해당하는 레코드값을 가져온다.
		
		em.addAttribute("b", eb);//문자열 키이름b에 eb객체를 저장
		em.addAttribute("page", page);
		return "board/board_edit";//뷰리졸브 경로는 /WEB-INF/views/board/board_edit.jsp
	}//board_edit()
	
	//게시판 수정완료
	@RequestMapping("board_edit_ok") //get or post 양쪽방식 다 실행. board_edit_ok URL 매핑주소 등록
	public ModelAndView board_edit_ok(int page,BoardVO eb) {
		/* BoardVO eb하면 수정할 글쓴이, 글제목,글내용이 eb객체에 벌써저장되어있다. 이렇게 할수있는 이유가board_edit.jsp의 
		 * 네임 피라미터 이름과 빈클래스 변수명이 같기 때문이다.
		 */
		boardService.editBoard(eb);//번호를 기준으로 글쓴이,글제목, 글내용을 수정한다.
		//문제)번호를 기준으로 글쓴이,글제목 ,글내용을  수정되게 해보자update매퍼태그의 유일아이디명음 b_edit이다.
		
		ModelAndView em = new ModelAndView();
		em.setViewName("redirect:/board/board_cont");
		em.addObject("bno", eb.getBno());
		em.addObject("page", page);
		return em; //board_cont?bno=글번호&page=쪽번호로 get방식으로 내용보기로 이동해서 수정된 값을 확인한다.
	}//board_edit_ok()
	
	//게시글 삭제
	@RequestMapping("/board_del")
	public String board_del(int bno,int page,RedirectAttributes rttr) {
		this.boardService.delBoard(bno);//번호를기준으로 삭제
		/* 문제) 번호를 기준으로 게시물을 삭제되게 만들자.
		 * delete()메서드로 레코드를 삭제한다. 매퍼태그 유일 아이디명은b_del이다. 
		 */
		rttr.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/board/board_list?page="+page;
	}//board_del()
	
}






