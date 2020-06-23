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
import com.recipe.vo.Purchase;
import com.recipe.vo.RecipeInfo;
import com.recipe.vo.Review;

public class PurchaseListVIew {
	private DataIO dio;
	private Scanner sc = new Scanner(System.in);
	
	public PurchaseListVIew(Socket s) {
		try {
			dio = new DataIO(new DataOutputStream(s.getOutputStream()), new DataInputStream(s.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void purchaseView() {
		try {
			dio.sendMenu(Menu.PURCHASE_LIST);
			
			List<Purchase> list = dio.receivePurchaseList();
			List<Review> rlist = dio.receiveReviews();
			
			System.out.println("구매내역");
			System.out.println("레시피상품명/구매일자/후기등록여부");
			System.out.println(list.size() + "건의 내역이 출력되었습니다");
			
			for(Purchase p : list) {
				System.out.print(p.getPurchaseDetail().getRecipeInfo().getRecipeName() + "/ ");
				System.out.print(p.getPurchaseDate() + "/ ");
			}
			
			for(Review r : rlist) {
				if(r.getReviewComment()!=null) {
					System.out.print("Yes");
				}else {
					System.out.print("No");
				}
			}
		
			System.out.println("-이전페이지 | + 다음페이지 | *메인메뉴");
			System.out.println("원하시는 번호를 입력하세요 : ");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
