package net.daum.dao;

import java.util.List;

import net.daum.vo.BoardVO;

public interface BoardDAO {

	void insertBoard(BoardVO b);

	int getRowCount();

	List<BoardVO> getBoardList(BoardVO b);

	void updateHit(int bno);

	BoardVO getBoardCont(int bno);

	void editBoard(BoardVO eb);

	void delBoard(int bno);

	void updateReplyCnt(int bno, int count);



}
