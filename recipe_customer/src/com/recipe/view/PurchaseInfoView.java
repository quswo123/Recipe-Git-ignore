package com.recipe.view;

import java.io.IOException;
import java.util.List;

import com.recipe.io.DataIO;
import com.recipe.share.CustomerShare;
import com.recipe.vo.Purchase;
import com.recipe.vo.Review;

public class PurchaseInfoView {
	private DataIO dio;
	
	public PurchaseInfoView(DataIO dio) {
		this.dio = dio;
	}
	
	public void searchPurchaseInfoView(Purchase p) {
		List<Review> rlist = null;
		
		try {
			dio.send(p);
			dio.sendId(CustomerShare.loginedId);
			
			System.out.println("구매상세내역");
			System.out.println("레시피명 : " + p.getPurchaseDetail().getRecipeInfo().getRecipeName());
			System.out.println("구매일자 : " + p.getPurchaseDate());
			System.out.println("구매수량 : " + p.getPurchaseDetail().getPurchaseDetailQuantity());
			System.out.println("총 결제금액 : " + p.getPurchaseDetail().getPurchaseDetailQuantity()*p.getPurchaseDetail().getRecipeInfo().getRecipePrice());
			System.out.println("1.레시피로가기 | 2.후기등록하기 | 0.목록으로 | *.메인메뉴");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
