package com.naver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.dao.MemberDAO;
import com.naver.vo.MemberVO;
import com.naver.vo.ZipcodeVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDao;

	@Override
	public MemberVO idCheck(String id) {
		return this.memberDao.idCheck(id);
	}

	@Override
	public List<ZipcodeVO> zipFind(String dong) {
		return this.memberDao.zipFind(dong);
	}

	@Override
	public void insertMember(MemberVO m) {
		this.memberDao.insertMember(m);
	}

	@Override
	public MemberVO pwdMember(MemberVO m) {
		return this.memberDao.pwdMember(m);
	}

	@Override
	public void updatePwd(MemberVO m) {
		this.memberDao.updatePwd(m);
	}

	@Override
	public MemberVO loginCheck(String login_id) {
		return this.memberDao.loginCheck(login_id);
	}

	@Override
	public MemberVO getMember(String id) {
		return this.memberDao.getMember(id);
	}

	@Override
	public void updateMember(MemberVO em) {
		this.memberDao.updateMember(em);
	}

	@Override
	public void delMem(MemberVO dm) {
		this.memberDao.delMem(dm);
	}
	
	
}
