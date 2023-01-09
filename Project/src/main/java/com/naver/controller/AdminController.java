package com.naver.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.naver.service.AdminService;
import com.naver.vo.AdminVO;

import pwdconv.PwdChange;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	//관리자 로그인 폼
	@GetMapping("admin_index")
	public String admin_index() {
		return "admin/admin_Login";// /WEB_INF/views/admin/admin_Login.jsp
	}//admin_index()
	
	//관리자 로그인 비번 암호화와 정보저장, 관리자 로그인 인증
	@PostMapping("admin_login_ok")
	public ModelAndView admin_login_ok(AdminVO ab,HttpServletResponse response,
			HttpServletRequest request,HttpSession session) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		ab.setAdmin_pwd(PwdChange.getPassWordToXEMD5String(ab.getAdmin_pwd()));
		//MD5 비번 암호화=>과거에 네이트온 메신저 비번암호화 스킬로 사용되었던 것이고 한번 해킹 당해서
		//곤욕을 치른적이 있는 부분이 있다. 이를 참조해서 사용하길 바란다. 인코딩 암호화를 한다고 해도
		//디코딩 즉 복호화 된다. 이를 인식하고 사용해야 한다.
		
		//ab.setAdmin_name("관리자");
		//this.adminService.insertAdmin(ab);//관리자 정보 저장
		
		AdminVO admin_pwd=this.adminService.adminLoginCheck(ab.getAdmin_id());//관리자 아이디에
		//해당하는 관리자 정보를 가져옴
		
		if(admin_pwd == null) {
			out.print("<script>");
			out.print("alert('관리자 정보가 없습니다!');");
			out.print("history.back();");
			out.print("</script>");
		}else {
			if(!admin_pwd.getAdmin_pwd().equals(ab.getAdmin_pwd())) {
				out.print("<script>");
				out.print("alert('관리자 비번이 다릅니다!');");
				out.print("history.go(-1);");//.back()이랑 같은 이전주소로 이동
				out.print("</script>");
			}else {
				session.setAttribute("admin_id", ab.getAdmin_id());//세션 admin_id에 
				//관리자 아이디 저장
				session.setAttribute("admin_name", admin_pwd.getAdmin_name());//관리자 이름 저장
				
				ModelAndView loginM = new ModelAndView();
				loginM.setViewName("redirect:/admin_main");
				return loginM;
			}
		}
		
		return null;
	}//admin_login_ok()
	
	//관리자 메인
	@RequestMapping("admin_main")
	public ModelAndView admin_main(HttpServletResponse response,HttpSession session)
	throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String admin_id = (String)session.getAttribute("admin_id");//업캐스팅을 한것에 한해서
		//만 명시적인 다운캐스팅
		
		if(admin_id == null) {
			out.print("<script>");
			out.print("alert('다시 관리자로 로그인 하세요');");
			out.print("location='admin_index';");
			out.print("</script>");
		}else {
			return new ModelAndView("admin/admin_main");//뷰페이지 경로: /WEB-INF/views/admin/admin_main.jsp
		}
		return null;
	}//admin_main()
	
	//관리자 로그아웃
	@RequestMapping("admin_logout")
	public String admin_logout(HttpServletResponse response,HttpServletRequest request,
			HttpSession session) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		session.invalidate();//세션만료
		
		out.print("<script>");
		out.print("alert('관리자 로그아웃 되었습니다!');");
		out.print("location='admin_index';");
		out.print("</script>");
		return null;
	}//admin_logout()	
	
}
