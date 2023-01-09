package net.daum.service;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.daum.dao.MessageDAO;
import net.daum.dao.PointDAO;
import net.daum.vo.MessageVO;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired//자동 의존성 주입
	private MessageDAO messageDao;
	
	@Inject
	private PointDAO pointDao;

	//aop를  통한 트랜잭션 적용(서비스 추가 목적.데이터의 일관성(일치성) 보장. 자료불일치 현상 제거)
	@Transactional //트랜잭션 적용
	@Override
	public void addMessage(MessageVO vo) {
		this.messageDao.insertMessage(vo);//메시지 추가
		this.pointDao.updatePoint(vo.getSender(),10);//메시지를 보낸 사람에게 포인트 점수 10 추가
	}
	
}
