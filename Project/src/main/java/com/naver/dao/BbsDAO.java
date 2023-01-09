package com.naver.dao;

import java.util.List;

import com.naver.vo.BbsVO;

public interface BbsDAO {

	void insertBbs(BbsVO b);

	int getTotalCount(BbsVO b);

	List<BbsVO> getBbsList(BbsVO b);

	void updateHit(int bbs_no);

	BbsVO getBbs_cont(int bbs_no);

	void updateLevel(BbsVO rb);

	void replyBbs(BbsVO rb);

	void editBbs(BbsVO eb);

	void delBbs(int bbs_no);

}
