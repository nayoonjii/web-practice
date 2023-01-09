package com.naver.dao;

import com.naver.vo.AdminVO;

public interface AdminDAO {

	void insertAdmin(AdminVO ab);

	AdminVO adminLoginCheck(String admin_id);

}
