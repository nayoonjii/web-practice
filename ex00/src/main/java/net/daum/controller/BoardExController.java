package net.daum.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.daum.service.BoardExService;
import net.daum.vo.BoardExVO;

@Controller
public class BoardExController {

	@Autowired
	private BoardExService boardExService;
	
	@GetMapping("/boardEx_list") 
	public String boardEx_list(Model m,BoardExVO b,HttpServletRequest request) {
		
		int page=1;
		int limit=10; 
		if(request.getParameter("page")!= null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		b.setStartrow((page-1)*10+1);
		b.setEndrow(b.getStartrow()+limit-1);
		
		
		int totalCount=this.boardExService.getRowCount();
		List<BoardExVO> blist = this.boardExService.getBoardList(b);
		
		
		int maxpage=(int)((double)totalCount/limit+0.95);
		int startpage = (((int)((double)page/10+0.9))-1)*10+1;
		int endpage = maxpage;
		
		if(endpage > startpage +10-1) endpage=startpage+10-1;
		
		m.addAttribute("list",blist);
		m.addAttribute("totalCount",totalCount);
		m.addAttribute("startpage",startpage);
		m.addAttribute("endpage",endpage);
		m.addAttribute("maxpage",maxpage);
		m.addAttribute("page",page);
		return "boardEx_list";
	}
}
