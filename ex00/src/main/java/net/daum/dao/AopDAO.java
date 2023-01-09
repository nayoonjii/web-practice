package net.daum.dao;

import java.util.List;

import net.daum.vo.AopVO;

public interface AopDAO {



	List<AopVO> getBoardList();

	void updateHit(int bno);

	AopVO getBoardCont(int bno);

}
