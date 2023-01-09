package net.daum.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.daum.vo.BoardExVO;

@Repository
public class BoardExDAOImpl implements BoardExDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public int getRowCount() {
		return sqlSession.selectOne("be_count");
	}

	@Override
	public List<BoardExVO> getBoardList(BoardExVO b) {
		return this.sqlSession.selectList("be_list",b);
	}
	
}
