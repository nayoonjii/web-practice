package com.naver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.dao.AdminDAO;
import com.naver.vo.AdminVO;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminDAO adminDao;

	@Override
	public void insertAdmin(AdminVO ab) {
		this.adminDao.insertAdmin(ab);
	}

	@Override
	public AdminVO adminLoginCheck(String admin_id) {
		return adminDao.adminLoginCheck(admin_id);
	}
	

}
