package com.naver.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.naver.service.AdminBbsService;
import com.naver.vo.BbsVO;
import com.oreilly.servlet.MultipartRequest;

@Controller
public class AdminBbsController {//관리자 자료실 스프링 컨트롤러

	@Autowired
	private AdminBbsService adminBbsService;
	
	//관리자 자료실 목록
	@GetMapping("admin_bbs_list")
	public String admin_bbs_list(Model listM, BbsVO b,HttpServletRequest request,
			HttpSession session,HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String admin_id = (String)session.getAttribute("admin_id");//관리자 세션 아이디를 구함
		
		if(admin_id == null) {
			out.print("<script>");
			out.print("alert('관리자로 다시 로그인 하세요!');");
			out.print("location='admin_index';");
			out.print("</script>");
		}else {
			int page=1;//쪽번호
		      int limit=7;//한페이지에 보여지는 목록개수
		      if(request.getParameter("page") != null) {
		page=Integer.parseInt(request.getParameter("page"));         
		      }
		String find_name=request.getParameter("find_name");//검색어
		String find_field=request.getParameter("find_field");//검색
		//필드
		      b.setFind_field(find_field);
		      b.setFind_name("%"+find_name+"%");
		      //%는 검색에서 와일드 카드 문자로서 하나이상의 임의의 문자와 매핑 대응, 하나의 문자와 매핑대응하는
		      //와일드 카드 문자는 _
		      
		       int listcount=this.adminBbsService.getRowCount(b);
		      //검색전 전체 레코드 개수 또는 검색후 레코드 개수
		      //System.out.println("총 게시물수:"+listcount+"개");
		      
		      b.setStartrow((page-1)*7+1);//시작행번호
		      b.setEndrow(b.getStartrow()+limit-1);//끝행번호
		      
		      List<BbsVO> blist=
		            this.adminBbsService.getBbsList(b);
		      //검색전 총목록, 검색 후 목록
		      
		      //총페이지수
		      int maxpage=(int)((double)listcount/limit+0.95);
		      //현재 페이지에 보여질 시작페이지 수(1,11,21)
		      int startpage=(((int)((double)page/10+0.9))-1)*10+1;
		      //현재 페이지에 보여줄 마지막 페이지 수(10,20,30)
		      int endpage=maxpage;
		      if(endpage > startpage+10-1) endpage=startpage+10-1;
		      
		      listM.addAttribute("blist",blist);
		      //blist 키이름에 값 저장
		      listM.addAttribute("page",page);
		      listM.addAttribute("startpage",startpage);
		      listM.addAttribute("endpage",endpage);
		      listM.addAttribute("maxpage",maxpage);
		        listM.addAttribute("listcount",listcount);   
		        listM.addAttribute("find_field",find_field);
		        listM.addAttribute("find_name", find_name);
		       
		      return "admin/admin_bbs_list";
		      //뷰페이지 폴더경로와 파일명 지정      
		}
		return null;
	}//admin_bbs_list()
	
	
	//관리자 자료실 글쓰기
	@RequestMapping("admin_bbs_write")
	public ModelAndView admin_bbs_write(HttpServletResponse response,HttpSession session
			,HttpServletRequest request) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		String admin_id = (String)session.getAttribute("admin_id");//관리자 세션 아이디를 구함
		if(admin_id == null) {
			out.print("<script>");
			out.print("alert('관리자로 다시 로그인 하세요!');");
			out.print("location='admin_index';");
			out.print("</script>");
		}else {
			int page=1;
			if(request.getParameter("page")!= null) {
				page = Integer.parseInt(request.getParameter("page"));//get으로 전달된 쪽번호를
				//받아서 정수 숫자로 변경
			}
			ModelAndView wm = new ModelAndView("admin/admin_bbs_write");
			wm.addObject("page",page);
			return wm;
		}
		return null;
	}//admin_bbs_write()
	
	//관리자 자료실 저장
	@PostMapping("admin_bbs_write_ok")
	public String admin_bbs_write_ok(BbsVO b,HttpServletRequest request,
			HttpServletResponse response,HttpSession session) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String admin_id = (String)session.getAttribute("admin_id");//관리자 세션 아이디를 구함
		if(admin_id ==null) {
			out.print("<script>");
			out.print("alert('관리자로 다시 로그인 하세요!');");
			out.print("location='admin_index';");
			out.print("</script>");
		}else {
			String saveFolder=request.getRealPath("/resources/upload");
		       //이진파일 업로드 서버경로 =>톰캣 WAS서버에 의해서 실제로 변경된 경로
		       int fileSize=5*1024*1024;//이진파일 업로드 최대크기
		       MultipartRequest multi=null;//이진파일을 받을 참조변수

		               multi=new MultipartRequest(request,saveFolder,
		                     fileSize,"UTF-8");
		               String bbs_name=multi.getParameter("bbs_name");
		               String bbs_title=multi.getParameter("bbs_title");
		               String bbs_pwd=multi.getParameter("bbs_pwd");
		               String bbs_cont=multi.getParameter("bbs_cont");
		               
		               File UpFile=multi.getFile("bbs_file");//첨부한 이진파일
		               //을 받아옴.
		              if(UpFile != null) {//첨부한 이진파일이 있다면
		                 String fileName=UpFile.getName();//첨부한 파일명
		                 Calendar c=Calendar.getInstance();//칼렌더는 추상
		       //클래스로 new로 객체 생성을 못함. 년월일 시분초 값을 반환
		                  int year=c.get(Calendar.YEAR);//년도값
		                  int month=c.get(Calendar.MONTH)+1;//월값. +1을 한
		                  //이유가 1월이 0으로 반환 되기 때문이다.
		                  int date=c.get(Calendar.DATE);//일값
		       String homedir=saveFolder+"/"+year+"-"+month+"-"+date;//오늘
		       //날짜 폴더 경로 저장
		                  File path1=new File(homedir);
		                  if(!(path1.exists())) {
		                     path1.mkdir();//오늘날짜 폴더경로를 생성
		                  }
		                 Random r=new Random();
		                 int random=r.nextInt(100000000);
		                 
		                 /*첨부 파일 확장자 구함*/
		                 int index=fileName.lastIndexOf(".");//마침표 위치
		                 //번호를 구함
		         String fileExtension=fileName.substring(index+1);//마침표
		         //이후부터 마지막 문자까지 구함.첨부파일 확장자를 구함
		         String refileName="bbs"+year+month+date+random+"."+
		         fileExtension;//새로운 이진파일명 저장
		         String fileDBName="/"+year+"-"+month+"-"+date+"/"+
		         refileName;//디비에 저장될 레코드값
		         UpFile.renameTo(new File(homedir+"/"+refileName));
		         //바뀌어진 이진파일로 업로드
		         b.setBbs_file(fileDBName);
		              }else {//mybatis에서는 컬럼에 null을 insert하지 못함.
		         String fileDBName="";
		         b.setBbs_file(fileDBName);//첨부하지 않았을때 빈공백을 저장
		              }
		              b.setBbs_name(bbs_name); b.setBbs_title(bbs_title);
		              b.setBbs_pwd(bbs_pwd); b.setBbs_cont(bbs_cont);
		              
		              this.adminBbsService.insertBbs(b);//자료실 저장
		              
		             return "redirect:/admin_bbs_list"; 
		}
		return null;
	}//admin_bbs_write_ok()
	
	
	 /*//관리자 자료실 내용보기(조회수 증가는 안한다)+수정폼
	@RequestMapping("admin_bbs_cont")
	public ModelAndView admin_bbs_cont(@RequestParam("no") int bbs_no,
			String state,HttpServletRequest request,HttpServletResponse response,
			HttpSession session) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String admin_id = (String)session.getAttribute("admin_id");
		if(admin_id == null) {
			out.print("<script>");
			out.print("alert('관리자로 다시 로그인 하세요!');");
			out.print("location='admin_index';");
			out.print("</script>");
		}else {
			int page = 1;
			if(request.getParameter("page") != null) {
				page=Integer.parseInt(request.getParameter("page"));
			}
			
			BbsVO bc = this.adminBbsService.getBbsCont(bbs_no);//번호에 해당하는 레코드 값을 가져옴
			String bbs_cont = bc.getBbs_cont().replace("\n", "<br/>");//textarea입력박스에서
			//엔터키 친부분을 줄 바꿈 처리
			
			ModelAndView cm = new ModelAndView();
			cm.addObject("b", bc);//b속성 키이름에 b객체 저장
			cm.addObject("bbs_cont", bbs_cont);
			cm.addObject("page", page);
			
			if(state.equals("cont")) {//관리자 자료실 내용보기
				cm.setViewName("admin/admin_bbs_cont");
			}else if(state.equals("edit")){//관리자 자료실 수정폼				
				cm.setViewName("admin/admin_bbs_edit");
			}
			return cm;
		}
		return null;
	}//admin_bbs_cont()
	
	@RequestMapping(value="/admin_bbs_edit_ok",method=RequestMethod.POST)
	public ModelAndView admin_bbs_edit_ok(HttpServletResponse response,
			HttpSession session,HttpServletRequest request,BbsVO b) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out =response.getWriter();
		
		String admin_id = (String)session.getAttribute("admin_id");
		if(admin_id == null) {
			out.print("<script>");
			out.print("alert('관리자로 다시 로그인 하세요!');");
			out.print("location='admin_index';");
			out.print("</script>");
		}else {
			
			String saveFolder=request.getRealPath("/resources/upload");
	        //이진파일 업로드 실제 서버 경로
	        int fileSize=5*1024*1024;//이진파일 최대크기

	        MultipartRequest multi=null;//이진파일 받을 참조변수
	        multi=new MultipartRequest(request,saveFolder,fileSize,
	              "UTF-8");
	        int bbs_no=Integer.parseInt(multi.getParameter("bbs_no"));
	        int page=1;
	        if(multi.getParameter("page") != null) {
	           page=Integer.parseInt(multi.getParameter("page"));
	        }
	        String bbs_name=multi.getParameter("bbs_name");
	        String bbs_title=multi.getParameter("bbs_title");       
	        String bbs_cont=multi.getParameter("bbs_cont");

	       BbsVO db_pwd=this.adminBbsService.getBbsCont(bbs_no);
	//디비로 부터 기존첨부파일명을 구함.       
	       File UpFile=multi.getFile("bbs_file");
	           if(UpFile != null) {//첨부파일이 있는 경우
	        String fileName=UpFile.getName();//첨부한 파일명
	        File DelFile=new File(saveFolder+db_pwd.getBbs_file());
	        //삭제할 파일 객체를 생성
	        if(DelFile.exists()) {
	           DelFile.delete();//기존 이진파일을 삭제
	        }
	        Calendar c=Calendar.getInstance();
	        int year=c.get(Calendar.YEAR);//년도값
	        int month=c.get(Calendar.MONTH)+1;//월값
	        int date=c.get(Calendar.DATE);
	        String homedir=saveFolder+"/"+year+"-"+month+"-"+date;
	        File path1=new File(homedir);
	        if(!(path1.exists())) {
	           path1.mkdir();
	        }
	        Random r=new Random();
	        int random=r.nextInt(100000000);

	       확장자 구함*//*
	        int index=fileName.lastIndexOf(".");
	        String fileExtension=fileName.substring(index+1);//확장자
	        String refileName="bbs"+year+month+date+random+"."+
	        fileExtension;
	        String fileDBName="/"+year+"-"+month+"-"+date+"/"+
	        refileName;
	        UpFile.renameTo(new File(homedir+"/"+refileName));
	        b.setBbs_file(fileDBName);
	           }else {//파일을 첨부하지 않은 경우
	        String fileDBName="";
	        if(db_pwd.getBbs_file() != null) {
	           b.setBbs_file(db_pwd.getBbs_file());
	        }else {
	           b.setBbs_file(fileDBName);
	        }
	       }
	           b.setBbs_no(bbs_no); b.setBbs_name(bbs_name);
	           b.setBbs_title(bbs_title); b.setBbs_cont(bbs_cont);
	           this.adminBbsService.editBbs(b);//자료실 수정
	          문제풀이) 번호를 기준으로 이름,제목,내용,첨부파일을
	         * 수정되게 한다.(테스트 까지 완료) 
	         
	           ModelAndView em=
	          new ModelAndView("redirect:/admin_bbs_list"); 
	           em.addObject("page",page);       
	           return em;//admin_bbs_list?page=쪽번호가 get방식으로 전달한다.
			
		}
		return null;
	}//admin_bbs_edit_ok()*/
	//관리자 자료실 내용보기(조회수 증가는 안한다)+수정폼
	   @RequestMapping("admin_bbs_cont")
	   public ModelAndView admin_bbs_cont(@RequestParam("no") int bbs_no,
	         String state,HttpServletRequest request,HttpServletResponse response,
	         HttpSession session) throws Exception{
	      response.setContentType("text/html;charset=UTF-8");
	      /*PrintWriter out=response.getWriter();

	      String admin_id=(String)session.getAttribute("admin_id");
	      if(admin_id == null) {
	         out.println("<script>");
	         out.println("alert('관리자로 다시 로그인 하세요!');");
	         out.println("location='admin_index';");
	         out.println("</script>");
	      }else {*/
	      if(isAdminLogin(response, session)) {
	         int page=1;
	         if(request.getParameter("page") != null) {
	            page=Integer.parseInt(request.getParameter("page"));
	         }

	         BbsVO bc=this.adminBbsService.getBbsCont(bbs_no);//번호에 해당하는 레코드값을 가져옴.
	         String bbs_cont=bc.getBbs_cont().replace("\n","<br/>");//textarea 입력박스에서 
	         //엔터키 친부분을 줄바꿈 처리

	         ModelAndView cm=new ModelAndView();
	         cm.addObject("b",bc);//b속성 키이름에 b객체 저장
	         cm.addObject("bbs_cont",bbs_cont);
	         cm.addObject("page",page);

	         if(state.equals("cont")) {//관리자 자료실 내용보기
	            cm.setViewName("admin/admin_bbs_cont");
	         }else if(state.equals("edit")) {//관리자 자료실 수정폼
	            cm.setViewName("admin/admin_bbs_edit");
	         }
	         return cm;
	      }
	      return null;
	   }//admin_bbs_cont()

	    //반복적인 코드 하나로 줄이기
	   public static boolean isAdminLogin(HttpServletResponse response,HttpSession session)
	   throws Exception{
	      PrintWriter out=response.getWriter();
	      String admin_id=(String)session.getAttribute("admin_id");
	      
	      if(admin_id == null) {
	         out.println("<script>");
	         out.println("alert('관리자로 다시 로그인 하세요!');");
	         out.println("location='admin_index';");
	         out.println("</script>");
	         return false;
	      }
	      return true;
	   }
	
	//관리자 자료실 삭제
	@RequestMapping(value="/admin_bbs_del",method=RequestMethod.GET)
	public ModelAndView admin_bbs_del(HttpServletResponse response,HttpSession session,
			int page,@RequestParam("no") int bbs_no,HttpServletRequest request) throws Exception  {
		//@RequestParam("no")는 request.getParameter("no")와 같은 기능을한다.
		response.setContentType("text/html;charset=utf-8");
		if(isAdminLogin(response,session)) {//관리자 로그인 성공시 실행
			String up=request.getRealPath("/resources/upload");
			BbsVO db_file = this.adminBbsService.getBbsCont(bbs_no);//번호에 해당하는 기존 첨부파일을
			//db로 부터 구한다.
			this.adminBbsService.delBbs(bbs_no);//번호에 해당하는 자료 삭제
			//문제)번호에 해당하는 레코드를 삭제되게 하고, 실제 폴더로 부터 기존 첨부파일이 삭제되는지 확인
			
			if(db_file.getBbs_file() != null) {//기존첨부 파일이 있다면
				File delFile = new File(up+db_file.getBbs_file());//삭제할 파일 객체를 생성
				delFile.delete();//기존 첨부 파일을 폴더로 부터 삭제
			}
			return new ModelAndView("redirect:/admin_bbs_list?page="+page);
		}
		return null;
	}
}









