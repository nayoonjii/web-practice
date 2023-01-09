package net.daum.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.daum.vo.MessageVO;

@Repository
public class MessageDAOImpl implements MessageDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insertMessage(MessageVO vo) {
		this.sqlSession.insert("message_in", vo);//message_in은 mybatis 매퍼태그에서 설정한 유일한 
		//아이디명, insert()메서드로 레코드를 저장		
	}//메시지 추가
	
	
}
