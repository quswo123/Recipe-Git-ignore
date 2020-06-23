package com.recipe.service;

import com.recipe.dao.CustomerDAO;
import com.recipe.exception.FindException;
import com.recipe.share.CustomerShare;
import com.recipe.vo.Customer;

public class AccountService {
	CustomerDAO customerDAO;
	public AccountService() {
		customerDAO = new CustomerDAO();
	}
	
	public void login(String customerId, String customerPwd) throws FindException{
		Customer c;
		try {
			c = customerDAO.selectById(customerId);
		} catch(FindException e) {
			throw new FindException("로그인 실패");
		}
		if(!c.getCustomerPwd().equals(customerPwd)) throw new FindException("로그인 실패");
		
		CustomerShare.addSession(customerId);
	}
}
