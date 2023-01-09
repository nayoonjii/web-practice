package net.daum.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.daum.vo.AopVO;

@Repository
public class AopDAOImpl implements AopDAO {

	@Inject
	private SqlSession sqlSession;
	

	@Override
	public List<AopVO> getBoardList() {
		return this.sqlSession.selectList("blist");
	}

	@Override
	public void updateHit(int bno) {
		sqlSession.update("bhit",bno);
	}

	@Override
	public AopVO getBoardCont(int bno) {
		return sqlSession.selectOne("bcont",bno);
	}

}
