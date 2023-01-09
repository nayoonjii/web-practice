package net.daum.service;

import java.util.List;

import net.daum.vo.BoardVO;

public interface BoardService {

	void insertBoard(BoardVO b);

	int getRowCount();

	List<BoardVO> getBoardList(BoardVO b);

	BoardVO getBoardCont(int bno);

	BoardVO getBoardCont2(int bno);

	void editBoard(BoardVO eb);

	void delBoard(int bno);
	
	

}
