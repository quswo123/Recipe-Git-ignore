package com.recipe.dao;

import com.recipe.exception.FindException;
import com.recipe.vo.Customer;

public class CustomerDAO {
	public Customer selectById(String customerId) throws FindException{
		throw new FindException();
	}
}
