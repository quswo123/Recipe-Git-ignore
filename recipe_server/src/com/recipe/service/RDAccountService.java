package com.recipe.service;

import java.util.List;

import com.recipe.dao.RDDAO;
import com.recipe.exception.FindException;
import com.recipe.share.RDShare;
import com.recipe.vo.RD;

public class RDAccountService {
	RDDAO rdDAO;
	
	public RDAccountService() {
		rdDAO = new RDDAO();
	}
	
	public void login(String rdId, String rdPwd) throws FindException{
		RD r;
		try {
			r = rdDAO.selectById(rdId);
		} catch (FindException e) {
			throw new FindException("로그인 실패");
		}
		
		if(!r.getRdPwd().equals(rdPwd)) throw new FindException("로그인 실패");
		
		RDShare.addSession(rdId);
	}
	
	public List<RD> findAll() throws FindException {
		return rdDAO.selectAll();
	}
}
