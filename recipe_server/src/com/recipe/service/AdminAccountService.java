package com.recipe.service;

import com.recipe.dao.AdminDAO;
import com.recipe.exception.FindException;
import com.recipe.share.AdminShare;
import com.recipe.vo.Admin;

public class AdminAccountService {
	AdminDAO adminDAO;
	public AdminAccountService() {
		adminDAO = new AdminDAO();
	}
	
	public void login(String adminId, String adminPwd) throws FindException{
		Admin a;
		try {
			a = adminDAO.selectById(adminId);
		} catch(FindException e) {
			throw new FindException("로그인 실패");
		}
		if(!a.getAdminPwd().equals(adminPwd)) throw new FindException("로그인 실패");
		
		AdminShare.addSession(adminId);
	}
}
