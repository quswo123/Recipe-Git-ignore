package com.recipe.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.share.CustomerShare;
import com.recipe.vo.Favorite;
import com.recipe.vo.Purchase;
import com.recipe.vo.RecipeInfo;
import com.recipe.vo.Review;

public class PurchaseListVIew {
	private DataIO dio;
	
	public PurchaseListVIew(DataIO dio) {
		this.dio = dio;
	}
	
	public void purchaseView() {
		Scanner sc = new Scanner(System.in);
		List<Purchase> list = null;
		List<Review> rlist = null;
		try {
			dio.sendMenu(Menu.PURCHASE_LIST);
			dio.sendId(CustomerShare.loginedId);
			
			list = dio.receivePurchaseList();
			rlist = dio.receiveReviews();
			
			System.out.println(list.size());
			System.out.println(rlist.size());
			
			System.out.println("나의 구매내역");
			System.out.println("["+list.size()+"건의 구매내역이 조회되었습니다 ]");
			System.out.println("레시피상품명/구매일자/후기등록여부");

			for(Purchase p : list) {
				System.out.print(p.getPurchaseDetail().getRecipeInfo().getRecipeName() + "/ ");
				System.out.print(p.getPurchaseDate()+ "/ ");
				for(Review r : rlist) {
					if(p.getPurchaseDate().equals(r.getReviewDate()) && p.getPurchaseDetail().getRecipeInfo().getRecipeCode() == r.getRecipeInfo().getRecipeCode()) {
						System.out.println("No");
					}else {
						System.out.println("Yes");
					}
				}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}	
	}
	
}
