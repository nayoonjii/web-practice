package com.naver.service;

import java.util.List;

import com.naver.vo.BbsVO;

public interface BbsService {

	void insertBbs(BbsVO b);

	int getListCount(BbsVO b);

	List<BbsVO> getBbsList(BbsVO b);

	BbsVO getBbsCont(int bbs_no);

	BbsVO getBbsCont2(int bbs_no);

	void replyBbs(BbsVO rb);

	void editBbs(BbsVO eb);

	void delBbs(int bbs_no);

}
