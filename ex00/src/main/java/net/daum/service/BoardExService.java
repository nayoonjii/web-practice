package net.daum.service;

import java.util.List;

import net.daum.vo.BoardExVO;

public interface BoardExService {

	int getRowCount();

	List<BoardExVO> getBoardList(BoardExVO b);

}
