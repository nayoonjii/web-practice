package net.daum.service;

import java.util.List;

import net.daum.vo.AopVO;


public interface AopService {


	List<AopVO> getBoardList();

	AopVO getBoardCont(int bno);

}
