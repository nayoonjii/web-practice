package com.naver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.dao.AdminBbsDAO;
import com.naver.vo.BbsVO;

@Service
public class AdminBbsServiceImpl implements AdminBbsService {

	@Autowired
	private AdminBbsDAO adminBbsDao;

	@Override
	public int getRowCount(BbsVO b) {
		return this.adminBbsDao.getTotalCount(b);
}

	@Override
	public List<BbsVO> getBbsList(BbsVO b) {
		return this.adminBbsDao.getBbsList(b);
	}

	@Override
	public void insertBbs(BbsVO b) {
		this.adminBbsDao.insertBbs(b);
	}

	@Override
	public BbsVO getBbsCont(int bbs_no) {
		return this.adminBbsDao.getBbsCont(bbs_no);
	}

	@Override
	public void editBbs(BbsVO b) {
		this.adminBbsDao.editBbs(b);
	}

	@Override
	public void delBbs(int bbs_no) {
		this.adminBbsDao.delBbs(bbs_no);
	}
	
}
