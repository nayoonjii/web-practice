package net.daum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import net.daum.dao.AopDAO;
import net.daum.vo.AopVO;

@Service
public class AopServiceImpl implements AopService {

	@Autowired //자동의존성 주입
	private AopDAO aopDao;

	@Override
    public List<AopVO> getBoardList() {
        return aopDao.getBoardList();
    }

    @Transactional
    @Override
    public AopVO getBoardCont(int bno) {
        aopDao.updateHit(bno);
        AopVO vo = aopDao.getBoardCont(bno);
        return vo;
    }

}
