package com.naver.service;

import java.util.List;

import com.naver.vo.BbsVO;

public interface AdminBbsService {

	int getRowCount(BbsVO b);

	List<BbsVO> getBbsList(BbsVO b);

	void insertBbs(BbsVO b);

	BbsVO getBbsCont(int bbs_no);

	void editBbs(BbsVO b);

	void delBbs(int bbs_no);

}
