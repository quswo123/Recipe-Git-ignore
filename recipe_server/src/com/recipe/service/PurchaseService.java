package com.recipe.service;

import java.util.ArrayList;
import java.util.List;

import com.recipe.dao.PurchaseDAO;
import com.recipe.dao.ReviewDAO;
import com.recipe.exception.AddException;
import com.recipe.exception.FindException;
import com.recipe.vo.Purchase;
import com.recipe.vo.Review;

public class PurchaseService {
	PurchaseDAO purchasedao = new PurchaseDAO();
	ReviewDAO revidao = new ReviewDAO();
	
	
	public void buy(Purchase p)  throws AddException{
		purchasedao.insert(p);
	}
	
	
	public List<Purchase> findById(String customerId) throws FindException{
		List<Purchase> list = new ArrayList<>();
		list = purchasedao.selectById(customerId);
		List<Review> reviewList = revidao.selectById(customerId);
		if(reviewList.size()!=0) {
			for(Review r : reviewList) {
				r.getReviewComment();
			}
		}
		
		return list;
	}

}
