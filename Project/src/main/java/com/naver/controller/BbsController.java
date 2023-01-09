package com.naver.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

//import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.naver.service.BbsService;
import com.naver.vo.BbsVO;
import com.oreilly.servlet.MultipartRequest;

@Controller
public class BbsController {

	@Autowired//자동의존성 주입(DI)
	private BbsService bbsService;
	
	//자료실 글쓰기 폼
	@GetMapping("/bbs_write")//get방식으로 접근하는 매핑주소를 처리,bbs_write라는 매핑주소등록
	//등록
	public ModelAndView bbs_write(HttpServletRequest request) {
		int page=1;
		if(request.getParameter("page")!= null) {
			page = Integer.parseInt(request.getParameter("page"));
			//get으로 전달된 쪽번호를 전달받아서 정수 숫자로 변경해서 저장(책갈피 기능)
		}
		ModelAndView wm = new ModelAndView();
		wm.addObject("page",page);//페이지 키값쌍으로 저장
		wm.setViewName("./bbs/bbs_write");//뷰리졸브 경로 설정. 
		//즉 뷰페이지 경로는 /WEB-INF/views/bbs/bbs_write.jsp
		return wm;
	}//자료실 글쓰기 폼=>bbs_write()메서드

	
	//자료실 저장
	@RequestMapping(value="/bbs_write_ok",method=RequestMethod.POST)
	//POST방식으로 접근하는 매핑주소 처리.bbs_write_ok 매핑주소 등록
	public String bbs_write_ok(BbsVO b,HttpServletRequest request) throws Exception {
		//이진파일 업로드 서버경로지정
		String saveFolder = request.getRealPath("/resources/upload");
		System.out.println(saveFolder);//이진 파일 업로드 되는 서버경로를 출력해본다.
		//C:\\20220607\\20220607Spring_work\\.metadata\\.plugins\\
		//org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\Project\\resources\\upload
		//이클립스 메이븐 폴더 가상경로가 아닌 톰켓 was서버에 의해서 변경된 실제 톰켓 프로젝트 경로가 반환
		
		int fileSize = 5*1024*1024;//이진파일 업로드 최대크기(5M)
		MultipartRequest multi = null;//cos-05Nov2002.jar 외부 라이브러리에 있는 api를 사용함
		//첨부된 이진 파일을 가져오는 api이다.
		
		multi = new MultipartRequest(request, saveFolder,fileSize,"UTF-8");//throws선택
		
		//글쓴이 부터 가져오기
		String bbs_name = multi.getParameter("bbs_name");
		String bbs_title = multi.getParameter("bbs_title");//글제목 가져오기
		String bbs_pwd = multi.getParameter("bbs_pwd");//비번 가져오기
		String bbs_cont = multi.getParameter("bbs_cont");//글내용 가져오기
		
		File upFile = multi.getFile("bbs_file");//첨부된 파일을 가져온다.
		
		if(upFile != null) {//첨부된 파일이 있는 경우
			String fileName = upFile.getName();//첨부된 파일명을 구함
			Calendar cal = Calendar.getInstance();//칼렌더는 추상클래스로 
			//new로 객체생성을 못한다. 하지만 년원일시분초 값을 반환할때 사용
			int year = cal.get(Calendar.YEAR);//년도값
			int month = cal.get(Calendar.MONTH)+1;//월값,+1을한 이유는 1월이 0으로
			//반환 되기 때문이다.
			int date = cal.get(Calendar.DATE);//일값
			
			String homedir = saveFolder+"/"+year+"-"+month+"-"+date;//오늘날짜 폴더
			//경로를 저장
			File path01 = new File(homedir);
			
			if(!(path01.exists())) {//오늘날짜 폴더경로가 없다면
				path01.mkdir();//오늘 날짜 폴더를 생성
			}
			
			Random r = new Random(); //난수(정해지지 않은 임의의 숫자)를 발생 시키는 클래스
			int random = r.nextInt(100000000);//0이상 1억 미만의 임의의 정수 숫자 난수 발생
			
			//첨부파일 확장자를 구함
			int index=fileName.lastIndexOf(".");// .를 맨오른쪽 부터 찾아서 가장 먼저 
			//나오는 .위치 번호를 맨 왼쪽 부터 카운트 해서 구함 첫문자는 0부터 시작
			String fileExtendsion = fileName.substring(index+1);//첨부파일에서
			// .이후부터 마지막 문자까지 구함, 첨부 파일 확장자를구함
			String refileName = "bbs"+year+month+date+random+"."+fileExtendsion;
			//bbs년원일 1억 미만 랜덤 숫자.확장자 즉 새로운 첨부파일명을 구함
			String fileDBName="/"+year+"-"+month+"-"+date+"/"+refileName;
			//데이터베이스에 저장될 레코드값
			
			upFile.renameTo(new File(homedir+"/"+refileName));//변경된 이진파일로
			//생성된 폴더 경로에 실제 업로드한다.
			
			b.setBbs_file(fileDBName);
		}else {//첨부파일이 없는 경우
			String fileDBName="";
			b.setBbs_file(fileDBName);
		}
		
		b.setBbs_name(bbs_name); b.setBbs_title(bbs_title);
		b.setBbs_pwd(bbs_pwd); b.setBbs_cont(bbs_cont);
		
		this.bbsService.insertBbs(b);//자료실 저장
		
		return "redirect:/bbs_list";//새로운 목록보기 매핑주소로 이동		
	}//bbs_write_ok()
	
	//자료실 목록(페이징과 검색기능이 추가됨)
	@RequestMapping(value="/bbs_list",method=RequestMethod.GET) //get으로 접근하는 매핑주소 처리
	public String bbs_list(Model listM,HttpServletRequest request, BbsVO b) {
		int page=1;
	      int limit=10;//한페이지에 보여지는 목록개수
	      if(request.getParameter("page") != null) {
        	page=Integer.parseInt(request.getParameter("page"));         
	      }
	      String find_name = request.getParameter("find_name");//검색어
	      String find_field = request.getParameter("find_field");//검색 필드
	      b.setFind_name("%"+find_name+"%");// %는 데이터 베이스에서 검색 와일드 카드 문자로서 하나이상의 
	      //임의의 모르는 문자와 매핑대응
	      b.setFind_field(find_field);
	      int totalCount=this.bbsService.getListCount(b);
	      //검색 전 총레코드 개수,검색후 레코드 개수
	      
	      b.setStartrow((page-1)*10+1);//시작행번호
	       b.setEndrow(b.getStartrow()+limit-1);//끝행 번호
	      
	      List<BbsVO> blist=this.bbsService.getBbsList(b);
	      //검색 전후 목록
	      
	      //총 페이지수
	      int maxpage=(int)((double)totalCount/limit+0.95);
	      //시작페이지(1,11,21 ..)
	      int startpage=(((int)((double)page/10+0.9))-1)*10+1;
	      //현재 페이지에 보여질 마지막 페이지(10,20 ..)
	      int endpage=maxpage;
	      if(endpage>startpage+10-1) endpage=startpage+10-1;
	      
	      listM.addAttribute("blist",blist);
	      listM.addAttribute("page",page);
	      listM.addAttribute("startpage",startpage);
	      listM.addAttribute("endpage",endpage);
	      listM.addAttribute("maxpage",maxpage);
	      listM.addAttribute("listcount",totalCount);
	      listM.addAttribute("find_field",find_field);//검색 필드
	      listM.addAttribute("find_name",find_name);//검색어
	      
	   
	      return "bbs/bbs_list";//뷰페이지 경로가/WEB-INF/views/bbs/bbs_list.jsp
	}//bbs_list()
	
	//내용보기+답변폼+수정폼+삭제폼
	@RequestMapping("/bbs_cont")
	public ModelAndView bbs_cont(@RequestParam("bbs_no")int bbs_no, int page,
			String state,BbsVO b) {
		//@RequestParam("bbs_no")은 서블릿의 request.getParameter("bbs_no")와 같다
		//int page,String state만 사용해서 각각 피라미터 값을 받을수 있다.
		
		if(state.equals("cont")) {//내용보기일때만 조회수 증가
			b=this.bbsService.getBbsCont(bbs_no);
		}else {//답변폼,수정폼,삭제폼일때 실행->조회수 증가 안함
			b=this.bbsService.getBbsCont2(bbs_no);
		}
		
		String bbs_cont=b.getBbs_cont().replace("\n", "<br/>");//textarea 글내용 입력박스에서 
		//엔터키 친부분을 다음줄로 줄바꿈(<br/>)
		
		ModelAndView cm = new ModelAndView();
		cm.addObject("b",b);
		cm.addObject("bbs_cont", bbs_cont);
		cm.addObject("page", page);
		
		if(state.equals("cont")) {//내용보기 일때
			cm.setViewName("bbs/bbs_cont");// /WEB-INF/views/bbs/bbs_cont.jsp
		}else if(state.equals("reply")) {//답변폼
			cm.setViewName("bbs/bbs_reply");
		}else if(state.equals("edit")) {//수정폼
			cm.setViewName("bbs/bbs_edit");
		}else if(state.equals("del")) {//삭제폼
			cm.setViewName("bbs/bbs_del");
		}
		return cm;
	}//bbs_cont()

	//답변 저장
	@PostMapping("/bbs_reply_ok")//post방식으로전달되는 매핑주소 처리
	public ModelAndView bbs_reply_ok(BbsVO rb,int page) {
		//bbs_reply.jsp의 피라미터 이름과 BbsVO빈클래스 변수명이 같으면 BbsVO rb라고 하면 rb객체에 값이 전달되어
		//저장됨. 하지만 page 피라미터 이름 히든값은 해당 비늘래스 변수명에 정의 되어있지 않아서 따로 int page로 해서
		// 값을 가져와야한다.
		this.bbsService.replyBbs(rb);//답변 저장
		return new ModelAndView("redirect:/bbs_list?page="+page);
	}//bbs_reply_ok()
	
	//자료실 수정완료
	@PostMapping("bbs_edit_ok")
	public ModelAndView bbs_edit_ok(HttpServletResponse response,HttpServletRequest
			request, BbsVO eb) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();//출력 스트림 객체 생성
		
		String saveFolder = request.getRealPath("/resources/upload");
		int fileSize=5*1024*1024;
		MultipartRequest multi = null;//이진파일을 받을 참조 변수
		
		multi = new MultipartRequest(request,saveFolder,fileSize,"UTF-8");
		
		int bbs_no = Integer.parseInt(multi.getParameter("bbs_no"));//히든으로 전달된 쪽번호를 받아서
		//정수 숫자로 변경
		int page = 1;
		if(multi.getParameter("page") != null) {
			page=Integer.parseInt(multi.getParameter("page"));
		}
		String bbs_name = multi.getParameter("bbs_name");
		String bbs_title = multi.getParameter("bbs_title");
		String bbs_pwd = multi.getParameter("bbs_pwd");
		String bbs_cont = multi.getParameter("bbs_cont");
		
		BbsVO db_pwd = this.bbsService.getBbsCont2(bbs_no);//조회수가 증가 안되는 내용보기로 
		//데이터베이스로 부터 비번을 가져옴
		if(!db_pwd.getBbs_pwd().equals(bbs_pwd)) {
			out.println("<script>");
			out.println("alert('비번이 다릅니다!');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			File upFile = multi.getFile("bbs_file");//첨부파일을 가져옴
			
			if(upFile != null) {//첨부 파일이 있는 경우는
				String fileName = upFile.getName();//첨부한 파일명
				File delFile = new File(saveFolder+db_pwd.getBbs_file());//기존 첨부된 파일은 
				//삭제하기 위해서 삭제할 파일 객체 생성
				if(delFile.exists()) {//삭제할 파일이 있다면 
					delFile.delete();//기존첨부 파일을 삭제
				}
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);//년도값
				int month = cal.get(Calendar.MONTH)+1;//월값, +1이유는 1월이 0월로 반환되기 때문에
				int date = cal.get(Calendar.DATE);//일값
				
				String homedir = saveFolder+"/"+year+"-"+month+"-"+date;//오늘 날짜 폴더 경로 저장
				File path01 = new File(homedir);
				if(!(path01.exists())) {
					path01.mkdir();
				}
				Random r = new Random();
				int random = r.nextInt(100000000);
				
				//첨부파일 확장자 구함
				int index = fileName.lastIndexOf(".");
				String fileExtendsion = fileName.substring(index+1);//첨부파일 확장자 저장
				String refileName = "bbs"+year+month+date+random+"."+fileExtendsion;
				//새로운 첨부 파일명을 저장
				String fileDBName = "/"+year+"-"+month+"-"+date+"/"+refileName;
				upFile.renameTo(new File(homedir+"/"+refileName));
				eb.setBbs_file(fileDBName);
			}else {//파일을 첨부하지 않았을때
				String fileDBName="";
				if(db_pwd.getBbs_file() != null) {//기존 첨부파일이 있는 경우
					eb.setBbs_file(db_pwd.getBbs_file());
				}else {
					eb.setBbs_file(fileDBName);
				}
			}//파일을 첨부한 경우와 그렇지 않은 경우
			eb.setBbs_name(bbs_name); eb.setBbs_title(bbs_title);
			eb.setBbs_cont(bbs_cont); eb.setBbs_no(bbs_no);
			
			this.bbsService.editBbs(eb);//번호를 기준으로 글쓴이,글제목,글내용,첨부파일을 수정
			
			ModelAndView em = new ModelAndView();
			em.setViewName("redirect:/bbs_cont");
			em.addObject("bbs_no",bbs_no);
			em.addObject("page",page);
			em.addObject("state","cont");
			return em;//주소창에 노출되는 get방식으로 bbs_cont?bbs_no=번호&page=쪽번호&state=cont
			//3개의 피라미터 값이 bbs_cont매핑주소로 전달된다
		}//if else=>비번이 다른 경우와 같은 경우
		
		return null;
	}//bbs_edit_ok()
	
	//자료실 삭제
	@RequestMapping("bbs_del_ok")//get or post 로 접근하는 매핑주소를 도시에 처리
	public String bbs_del_ok(HttpServletResponse response, int bbs_no,int page,
			String del_pwd,HttpServletRequest request) throws Exception {
		response.setContentType("text/html;charset=UTF-8");//웹브라우저에 출력되는 문자,태그,언어
		//코딩 타입을 설정
		PrintWriter out = response.getWriter();
		String up = request.getRealPath("/resources/upload");
		BbsVO db_pwd = this.bbsService.getBbsCont2(bbs_no);
		
		if(!db_pwd.getBbs_pwd().equals(del_pwd)) {
			out.print("<script>");
			out.print("alert('비번이 다릅니다!');");
			out.print("history.back();");
			out.print("</script>");
		}else {
			this.bbsService.delBbs(bbs_no);//번호를 기준으로 자료 삭제
			
			if(db_pwd.getBbs_file() != null) {//첨부파일이 있는 경우
				File delFile = new File(up+db_pwd.getBbs_file());//삭제파일 객체생성
				delFile.delete();//해당 폴더로 부터 기존 파일을 삭제
			}
			return "redirect:/bbs_list?page="+page;
			
		}
		return null;
	}
	
}










