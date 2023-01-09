package com.naver.dao;

import java.util.List;

import com.naver.vo.MemberVO;
import com.naver.vo.ZipcodeVO;

public interface MemberDAO {

	MemberVO idCheck(String id);

	List<ZipcodeVO> zipFind(String dong);

	void insertMember(MemberVO m);

	MemberVO pwdMember(MemberVO m);

	void updatePwd(MemberVO m);

	MemberVO loginCheck(String login_id);

	MemberVO getMember(String id);

	void updateMember(MemberVO em);

	void delMem(MemberVO dm);

}
