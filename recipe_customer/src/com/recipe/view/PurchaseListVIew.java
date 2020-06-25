package com.recipe.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
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
				//리스트 사이즈가 5개 넘으면 viewList 로 데이타를 넘겨줌
				viewList(list,i, 0);
			}else {
				for(Purchase p : list) {
					System.out.print(p.getPurchaseDetail().getRecipeInfo().getRecipeName() + "/ ");
					System.out.print(sdf.format(p.getPurchaseDate())+ "/ ");
					for(Review r : rlist) {
						if(p.getPurchaseDate().equals(r.getReviewDate()) && p.getPurchaseDetail().getRecipeInfo().getRecipeCode() == r.getRecipeInfo().getRecipeCode()) {
							System.out.println("No");
						}else {
							System.out.println("Yes");
						}
					}
				}
			}
			String value = sc.nextLine();
			if (value.equals("*")) {

			} else if (value.equals("0")) {
				// showRecipeInfoView();
			} else {
				
			}
			
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}	
	}
	
	private void viewList(List<Purchase> purchaseInfo, int i, int j) {
		Scanner sc = new Scanner(System.in);
		// j+5 만큼 돔
		for (; i < j + 5; i++) {
			//recipeInfo 의 마지막 오브젝트에 달했을때
			if (i == purchaseInfo.size()) {
				//i 값을 j-5 으로 대입해주고 , j 를 -5 해준다
				i = j - 5;
				j -= 5;
				break;
			}
			System.out.println(i+1 + " | " + purchaseInfo.get(i).getPurchaseDetail().getRecipeInfo().getRecipeName());

		}
		System.out.println("-:이전페이지 | +:다음페이지 | 0:다시검색 | *:메인메뉴");
		System.out.println("상세레시피를 보시려면 번호를 입력하세요");
		String value = null;
		do {
			value = sc.nextLine();
			switch (value) {
			case "+":
				viewList(purchaseInfo, i, j + 5);
			case "-":
				if (i == 5 || i < 10) {
					// 첫페이지로 계속 갱신해줌
					viewList(purchaseInfo, 0, 0);
				}
				//아닌경우, i -10 과 j-5로 갱신해줌
				viewList(purchaseInfo, i - 10, j - 5);		
			default:
				int t = Integer.valueOf(value);
				Purchase param = purchaseInfo.get(t-1);			
			}

		} while (value != Integer.toString(0));
		
	}
	
	
}
