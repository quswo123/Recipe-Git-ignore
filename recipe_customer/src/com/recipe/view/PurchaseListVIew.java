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
			
			System.out.println("나의 구매내역");
			System.out.println("["+list.size()+"건의 구매내역이 조회되었습니다 ]");
			System.out.println("레시피상품명/구매일자/후기등록여부");
	
			int i =0;
			if (list.size() > 5) {
				viewList(list,i, 0);
			}
			else {
				for(Purchase p : list) {
					System.out.print(i+1 + list.get(i).getPurchaseDetail().getRecipeInfo().getRecipeName() + "/ ");
					System.out.print(p.getPurchaseDate()+ "/ ");
					for(Review r : rlist) {
						String comment = r.getReviewComment();
						if (p.getCustomerId().equals(r.getCustomerId())) {
							if(comment.equals("")) {
								System.out.print("No");
							}else {
								System.out.println("Yes");
							}
						}
					}
				}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}	
	}
	
	private void viewList(List<Purchase> purchaseInfo, int i, int j) {
		Scanner sc = new Scanner(System.in);

		for (; i < j + 5; i++) {
			if (i == purchaseInfo.size()) {
				i = j - 5;
				j -= 5;
				break;
			}
			System.out.println(i+1 + " " + purchaseInfo.get(i).getPurchaseDetail().getRecipeInfo().getRecipeName());

		}
		System.out.println("-:이전페이지 | +:다음페이지 | *:메인메뉴");
		System.out.println("상세구매내역를 보시려면 번호를 입력하세요");
		String value = sc.nextLine();
		if (value.equals("+")) {
			viewList(purchaseInfo, i, j + 5);
		} else if (value.equals("-")) {
			if (i == 5 || i < 10) {
				viewList(purchaseInfo, 0, 0);
			}
			viewList(purchaseInfo, i - 10, j - 5);
		} else if (value.equals("*")) {
			PurchaseInfoView pview = new PurchaseInfoView(dio);
		}
	}
	
	
	
}
