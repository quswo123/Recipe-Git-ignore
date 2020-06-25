package com.recipe.view;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.share.CustomerShare;
import com.recipe.vo.Purchase;
import com.recipe.vo.Review;

public class PurchaseInfoView {
	private DataIO dio;
	private Scanner sc;
	
	public PurchaseInfoView(DataIO dio) {
		sc = new Scanner(System.in);
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
			System.out.println("1.상세레시피로가기 | 2.후기등록하기 | 0.목록으로");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
