package net.daum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.daum.dao.BoardExDAO;
import net.daum.vo.BoardExVO;

@Service
public class BoardExServiceImpl implements BoardExService {

	@Autowired//자동 의존성 주입
	private BoardExDAO boardExDao;

	@Override
	public int getRowCount() {
		return this.boardExDao.getRowCount();
	}

	@Override
	public List<BoardExVO> getBoardList(BoardExVO b) {
		return this.boardExDao.getBoardList(b);
	}
}
