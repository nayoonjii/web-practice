package com.naver.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
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

import com.naver.service.MemberService;
import com.naver.vo.MemberVO;
import com.naver.vo.ZipcodeVO;
import com.naver.vo.ZipcodeVO2;

import pwdconv.PwdChange;

@Controller
public class MemberController {//사용자 회원관리

	@Autowired
	private MemberService memberService;
	
	//회원관리 로그인 폼
	@GetMapping("member_login")
	public ModelAndView member_login() {
		ModelAndView m = new ModelAndView();
		m.setViewName("member/member_Login");
		//뷰페이지경로 설정 : /WEB-INF/views/member/member_Login.jsp가된다.
		return m;
	}//member_login()
	
	//회원가입 폼
	@RequestMapping("member_join")
	public String member_Join(Model m) {
		String[] phone = {"010","011","019"};
		String[] email = {"naver.com","daum.net","nate.com","google.com","직접입력"};
		
		m.addAttribute("phone", phone);
		m.addAttribute("email", email);//각 속성 키이름에 값을 저장
		return "member/member_Join";//뷰 리졸브 경로.
	}//member_Join()
	
	@PostMapping("member_idcheck")
	public String member_idcheck(@RequestParam("id") String id,HttpServletResponse
			response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		MemberVO db_id = this.memberService.idCheck(id);//아이디 중복검색
		int re =-1;//중복아이디가 없는 경우 반환값
		
		if(db_id != null) {
			re=1;//중복아이디가 있는경우
		}
		out.println(re);//값반환
		return null;
	}//member_idcheck()
	
	//우편검색폼 공지창
	@RequestMapping(value="/zip_find",method=RequestMethod.GET)
	public ModelAndView zip_find() {		
		return new ModelAndView("member/zip_find");//생성자 인자값으로 뷰페이지 경로 설정
	}//zip_find()
	
	//우편검색 결과
	@RequestMapping(value="/zip_find_ok",method=RequestMethod.POST)
	public ModelAndView zip_find_ok(String dong) {
		List<ZipcodeVO> zlist = this.memberService.zipFind("%"+dong+"%");
		//%는 SQL문에서 검색 와일드 카드문자로서 하나이상의 임의의 모르는 문자와 매핑대응.
		
		List<ZipcodeVO2> zlist2 = new ArrayList<>();
		
		for(ZipcodeVO z:zlist) {
			ZipcodeVO2 z2 = new ZipcodeVO2();
			
			z2.setZipcode(z.getZipcode());
			z2.setAddr(z.getSido()+" "+z.getGugun()+" "+z.getBunji());//시도 구군 동
			
			zlist2.add(z2);//컬렉션 추가
		}
		ModelAndView zm = new ModelAndView();
		zm.addObject("zipcodelist", zlist2);//zipcodelist키이름에 검색된 주소목록을 저장
		zm.addObject("dong", dong);
		zm.setViewName("member/zip_find");//뷰페이지 경로 설정=>뷰리졸브 설정
		return zm;
	}//zip_find_ok()
	
	//회원저장
	@RequestMapping("member_join_ok")//get or post 방식으로 접근하는 매핑주소 처리
	public String member_join_ok(MemberVO m) {
		//뷰페이지의 네임피라미터 이름과 빈클래스 변수명이 같으면 MemberVO m객체에 저장될 회원정보가 담겨있다.
		m.setMem_pwd(PwdChange.getPassWordToXEMD5String(m.getMem_pwd()));//비번 암호화
		this.memberService.insertMember(m);//회원저장
		
		
		return "redirect:/member_login";
	}//zip_join_ok()
	
	//비번 찾기 공지창
	@GetMapping("pwd_find")
	public String pwd_find() {
		return "member/pwd_find"; // /WEB-INF/views/member/pwd_find.jsp
	}//pwd_find()
	
	//비번 찾기 결과
	@PostMapping("/pwd_find_ok")
	public ModelAndView pwd_find_ok(String pwd_id,String pwd_name,HttpServletResponse
			response,MemberVO m) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		m.setMem_id(pwd_id); m.setMem_name(pwd_name);
		MemberVO pm = this.memberService.pwdMember(m); //아이디와 회원이름에 맞는 회원정보를 db로 부터 검색
		
		if(pm == null) {
			out.println("<script>");
			out.println("alert('회원정보를 찾을수 없습니다! \\n 올바른 아이디와 이름을 입력하세요!');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			Random r=new Random();
			int pwd_random = r.nextInt(100000);
			String ran_pwd = Integer.toString(pwd_random);//정수 숫자를 문자열로 변경
			m.setMem_pwd(PwdChange.getPassWordToXEMD5String(ran_pwd));//비번 암호화
			
			this.memberService.updatePwd(m);//암호화 된 비번 수정
			
			ModelAndView fm = new ModelAndView("member/pwd_find_ok");
			fm.addObject("ran_pwd", ran_pwd);
			return fm;
		}
		return null;
		
	}//pwd_find_ok()
	
	//로그인 인증
	@RequestMapping("/member_login_ok")
	public String member_login_ok(String login_id,String login_pwd,HttpSession session,
			HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		MemberVO dm = this.memberService.loginCheck(login_id);//가입회원 1이고, 아이디가 맞다면 
		//로그인 인증.탈퇴 회원 2는 로그인 인증 못함
		
		if(dm == null) {
			out.println("<script>");
			out.println("alert('가입안된 회원입니다!');");
			out.println("history.go(-1);");
			out.println("</script>");
		}else {
			if(!dm.getMem_pwd().equals(PwdChange.getPassWordToXEMD5String(login_pwd))) {
				out.println("<script>");
				out.println("alert('비번이 다릅니다!');");
				out.println("history.back();");
				out.println("</script>");
			}else {
				session.setAttribute("id", login_id);//세션아이디에 아이디 저장
				return "redirect:/index";//메인으로 이동
			}
		}
		return null;
	}//member_login_ok()
	
	//로그인 인증후 메인 화면
	@GetMapping("index")
	public String index(HttpServletResponse response,HttpSession session)
	throws Exception{
		response.setContentType("text/html;charset=utf-8");
		
		if(isLogin(response, session)) {//로그인 성공시
			return "member/member_Login";
		}
		return null;
	}//index()
	
	//로그아웃
	@PostMapping("member_logout")
	public String member_logout(HttpServletResponse response,HttpSession session,
			HttpServletRequest request) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		session.invalidate();//세션 만료 => 로그아웃
		
		out.println("<script>");
        out.println("alert('로그아웃 되었습니다!');");
        out.println("location='member_index';");
        out.println("</script>");
        
		
		return null;
	}
	
	//회원 정보 수정폼
	@RequestMapping(value="/member_edit",method = RequestMethod.GET)
	public ModelAndView member_edit(HttpServletResponse response,HttpSession session)
	throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String id = (String)session.getAttribute("id"); //세션 아이디 값을 구함
		
		if(id==null) {
		   	out.println("<script>");
	         out.println("alert('다시 로그인 하세요!');");
	         out.println("location='member_login';");
	         out.println("</script>");
		}else {
			String[] phone = {"010","011","019"};
			String[] email = {"naver.com","daum.net","nate.com","google.com","직접입력"};
			MemberVO dm = this.memberService.getMember(id);//db로부터 아이디에 해당하는 회원정보를 읽어옴
			
			ModelAndView em = new ModelAndView();
			em.addObject("m", dm);//m키이름에 dm객체를 저장
			em.addObject("phone", phone);
			em.addObject("email", email);
			em.setViewName("member/member_Edit");
			return em;
		}
		return null;
	}//member_edit()

	//정보수정완료
	@RequestMapping(value="/member_edit_ok",method=RequestMethod.POST)
	public String member_edit_ok(MemberVO em,HttpServletResponse response,
			HttpSession session) throws Exception{
		//네임피라미터 이름과 빈클래스 변수명이 같으면MemberVO em만사용해도 수정할 값이 em 에 저장되어있다.
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String id = (String)session.getAttribute("id");
		
		if(id==null) {
			out.println("<script>");
	         out.println("alert('다시 로그인 하세요!');");
	         out.println("location='member_login';");
	         out.println("</script>");
		}else {
			em.setMem_id(id);
			em.setMem_pwd(PwdChange.getPassWordToXEMD5String(em.getMem_pwd()));//MD5비번암호화
			
			this.memberService.updateMember(em);//아이디를 기준으로 회원정보 수정
			
			out.println("<script>");
	         out.println("alert('회원정보가 수정되었습니다!');");
	         out.println("location='member_edit';");
	         out.println("</script>");
		}
		return null;
	}//member_edit_ok()
	
	//회원탈퇴폼
	@RequestMapping("/member_del")
	public ModelAndView member_del(HttpServletResponse response, HttpSession session)
	throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String id = (String)session.getAttribute("id");
		
		if(id==null) {
			out.println("<script>");
	         out.println("alert('다시 로그인 하세요!');");
	         out.println("location='member_login';");
	         out.println("</script>");
		}else {
			MemberVO dm = this.memberService.getMember(id);
			ModelAndView m = new ModelAndView("member/member_del");
			m.addObject("m", dm);
			return m;
		}
		return null;
	}//member_del()
	
	//회원탈퇴 완료
	@PostMapping("member_del_ok")
	public String member_del_ok(HttpServletResponse response,HttpSession session,
			@RequestParam("del_pwd") String del_pwd,String del_cont,MemberVO dm) throws Exception{
		//@RequestParam("del_pwd")애노테이션은 서블릿 자바의 request.getParameter("del_pwd")
		//와 같은 기능을 한다
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String id = (String)session.getAttribute("id");//세션 아이디 값을 반환
		
		if(id == null) {
			out.println("<script>");
	         out.println("alert('다시 로그인 하세요!');");
	         out.println("location='member_login';");
	         out.println("</script>");
		}else {
			del_pwd=PwdChange.getPassWordToXEMD5String(del_pwd);//비번을 암호화
			MemberVO db_pwd = this.memberService.getMember(id);
			
			if(!db_pwd.getMem_pwd().equals(del_pwd)) {//비번이 다른경우
				out.println("<script>");
		         out.println("alert('비번이 다릅니다!');");
		         out.println("history.go(-1);");
		          out.println("</script>");
			}else {//비번이 같은경우
				dm.setMem_id(id); dm.setMem_delcont(del_cont);//탈퇴사유 저장
				this.memberService.delMem(dm);//회원 탈퇴
				
				session.invalidate();//세션 만료
				
				out.println("<script>");
		         out.println("alert('회원 탈퇴 했습니다!');");
		         out.println("location='member_login';");
		         out.println("</script>");
			}//inner if else
		}//outer if else
		
		return null;
	}//member_del_ok()
	
	
	//반복적인 코드 하나로 줄이기
	   public static boolean isLogin(HttpServletResponse response,HttpSession session)
	   throws Exception{
	      PrintWriter out=response.getWriter();
	      String id=(String)session.getAttribute("id");
	      
	      if(id == null) {
	         out.println("<script>");
	         out.println("alert('다시 로그인 하세요!');");
	         out.println("location='member_login';");
	         out.println("</script>");
	         return false;
	      }
	      return true;
	   }
	
}









