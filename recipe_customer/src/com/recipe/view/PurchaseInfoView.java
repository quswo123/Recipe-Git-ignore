package com.recipe.view;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.share.CustomerShare;
import com.recipe.vo.Purchase;
import com.recipe.vo.RecipeInfo;
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
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
		String menu = null;
		
		try {
			dio.send(p.getPurchaseDetail().getRecipeInfo());
			dio.sendId(CustomerShare.loginedId);
			
			System.out.println("");
			System.out.println("레시피명 : " + p.getPurchaseDetail().getRecipeInfo().getRecipeName());
			System.out.println("구매일자 : " + sdf.format(p.getPurchaseDate()));
			System.out.println("구매수량 : " + p.getPurchaseDetail().getPurchaseDetailQuantity());
			System.out.println("총 결제금액 : " + p.getPurchaseDetail().getPurchaseDetailQuantity()*p.getPurchaseDetail().getRecipeInfo().getRecipePrice());

			System.out.println("1.상세레시피로가기 | 2.후기등록하기 | 0.목록으로");
			
			do {
				menu = sc.nextLine();
				if(menu.equals("2")) {
					
				}else if(menu.equals("0")) {
					PurchaseListVIew view = new PurchaseListVIew(dio);
					view.purchaseView();
				}else {
					int n = Integer.parseInt(menu);
					System.out.println(menu);
					RecipeInfo param = p.getPurchaseDetail().getRecipeInfo();		
					RecipeInfoView view = new RecipeInfoView(dio);
					view.showRecipeInfoView(param);
				}
			}while(!menu.equals("3"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
