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
	private Scanner sc;
	
	public PurchaseListVIew(DataIO dio) {
		sc = new Scanner(System.in);
		this.dio = dio;
	}
	
	public void purchaseView() {
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
			System.out.println("-:이전페이지 | +:다음페이지 | *:이전화면");
			System.out.println("상세구매번호를 보시려면 해당번호를 입력하세요");
			String value = sc.nextLine();
			
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}	
	}
	
	private void printRecipeList(List<RecipeInfo> list) {
		String menu;
		int size = list.size();
		int start_index = 0; //화면에 다섯개씩 보여줄때 사용할 시작 인덱스
		int end_index = size <= 5 ? size : 5;//화면에 다섯개씩 보여줄때 사용할 끝 인덱스
											//ListView를 최초로 구성할때, list의 size가 5 이하이면 size만큼 화면에 출력하고, 5를 초과하면 5만큼만 화면에 출력
		System.out.println("[" + size + "개의 건이 조회되었습니다]");
		do {			
			for(int i = start_index; i < end_index; i++) {
				System.out.println(i+1 + ". " + list.get(i).getRecipeName());
			}
			if(size < 5) {
				System.out.println("0.뒤로가기");
				menu = sc.nextLine();
			}
			else {
				System.out.println("-:이전페이지 +:다음페이지 0.뒤로가기");
				menu = sc.nextLine();
				if(menu.equals("-")) {
					start_index = (start_index - 5) >= 0 ? (start_index - 5) : 0; //이전 페이지를 누르면 시작 인덱스 값을 5 감소시킨다. 이떄, 0보다 작아지면 0으로 설정한다
					end_index = start_index + 5; //시작 인덱스부터 다섯개를 출력하기 위해 끝 인덱스는 시작 인덱스에서 5 증가한 값을 갖는다
				} else if(menu.equals("+")) {
					end_index = (end_index + 5) <= size ? (end_index + 5) : size; //다음 페이지를 누르면 end_index 값을 5 증가시킨다. 이때, list의 size보다 커지면 size와 같은 값으로 설정한다
					start_index = end_index - 5; //시작 인덱스부터 다섯개를 출력하기 위해 시작 인덱스는 끝 인덱스에서 5 감소한 값을 갖는다
				}  else if (!menu.equals("0")){
					int n = Integer.parseInt(menu);
					System.out.println(menu);
					RecipeInfo param = list.get(n-1);			
					RecipeInfoView infoView = new RecipeInfoView(dio);
					infoView.showRecipeInfoView(param);
				}
			}
		} while (!menu.equals("0"));
	}
	
}
