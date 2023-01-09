package com.naver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naver.dao.BbsDAO;
import com.naver.vo.BbsVO;

@Service //@Service애노테이션을 추가함으로써 스프링에서 서비스 라는 것을 인식하게함
public class BbsServiceImpl implements BbsService {

	@Autowired
	private BbsDAO bbsDao;

	@Override
	public void insertBbs(BbsVO b) {
		this.bbsDao.insertBbs(b);
	}

	@Override
	public int getListCount(BbsVO b) {
		
		return this.bbsDao.getTotalCount(b);
	}

	@Override
	public List<BbsVO> getBbsList(BbsVO b) {
		return this.bbsDao.getBbsList(b);
	}

	@Transactional//트랜잭션 적용
	@Override
	public BbsVO getBbsCont(int bbs_no) {
		this.bbsDao.updateHit(bbs_no);//조회수 증가
		return this.bbsDao.getBbs_cont(bbs_no);
	}//조회수 증가와 내용보기=>스프링AOP를 통한 트랜잭션 적용 대상

	@Override
	public BbsVO getBbsCont2(int bbs_no) {
		return this.bbsDao.getBbs_cont(bbs_no);
	}//내용보기

	@Transactional //트랜잭션 적용
	@Override
	public void replyBbs(BbsVO rb) {
		this.bbsDao.updateLevel(rb);//답변레벨 증가
		this.bbsDao.replyBbs(rb);//답변저장
	}//답변 레벨 증가+답변 저장=>update문과 insert문이 트랜잭션 단위로 묶어져서 스프링의 aop르 통한 트랜잭션 적용

	@Override
	public void editBbs(BbsVO eb) {
		this.bbsDao.editBbs(eb);
	}

	@Override
	public void delBbs(int bbs_no) {
		this.bbsDao.delBbs(bbs_no);
	}
}
