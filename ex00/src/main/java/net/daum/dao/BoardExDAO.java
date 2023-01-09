package net.daum.dao;

import java.util.List;

import net.daum.vo.BoardExVO;

public interface BoardExDAO {

	int getRowCount();

	List<BoardExVO> getBoardList(BoardExVO b);

}
