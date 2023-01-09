package net.daum.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.daum.service.AopService;
import net.daum.vo.AopVO;
import net.daum.vo.BoardVO;
import net.daum.vo.SampleVO;

@Controller
public class AopController {

	@Autowired//자동의존성 주입(DI)
	private AopService aopService;
	
	@GetMapping("/aop_list")
    public ModelAndView board_list() {
        ModelAndView mv = new ModelAndView("aopList");

        List<AopVO> blist=this.aopService.getBoardList();
        mv.addObject("list", blist);

        return mv;
    }

    @RequestMapping("/aop_cont")
    public ModelAndView aopCont(@RequestParam("bno") int bno){

        ModelAndView mv = new ModelAndView("aopCont");

        AopVO vo = aopService.getBoardCont(bno);
        mv.addObject("cont",vo);

        return mv;
    }
	
}
