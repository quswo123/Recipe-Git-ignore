package com.recipe.view;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.share.CustomerShare;
import com.recipe.vo.Favorite;
import com.recipe.vo.Purchase;
import com.recipe.vo.PurchaseDetail;
import com.recipe.vo.RecipeInfo;

public class RecipeInfoView {
	private Scanner sc;
	private DataIO dio;
	
	public RecipeInfoView(DataIO dio) {
		sc = new Scanner(System.in);
		this.dio = dio;
	}
	
	/**
	 * 레시피 정보를 화면에 출력하는 메소드. 이때 레시피 과정 정보를 서버에서 전달받아 화면에 출력한다.
	 * @param info 화면에 보여줄 레시피 정보
	 * @author 최종국
	 */
	public void showRecipeInfoView(RecipeInfo info) {
		System.out.println(info); //레시피 정보 출력
		try {
			dio.sendMenu(Menu.RECIPE_PROCESS); //레시피 과정 정보를 서버에 요청
			dio.send(info.getRecipeProcess()); //레시피 과정 경로를 서버에 전송 (이렇게 하는게 맞을지... recipeCode를 보내면 그에 대한 파일 경로를 검색해서 과정 정보를 보내주도록 하는게 맞는지...)
			System.out.println(dio.receive()); //레시피 과정 정보 출력
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(CustomerShare.loginedId.equals("")) {
			basicMenu(info);
		} else {
			customerMenu(info);
		}
	}
	
	/**
	 * 로그인하지 않은 상태에서 보여줄 메뉴를 출력
	 * @author 최종국
	 */
	private void basicMenu(RecipeInfo info) {
		String menu = null;
		try {
			do {
				System.out.println("1.좋아요 2.싫어요 3.후기목록보기 0.이전화면");
				menu = sc.nextLine();
				if (menu.equals("1")) {
					likeThisRecipe(info);
				} else if (menu.equals("2")) {
					disLikeThisRecipe(info);
				} else if (menu.equals("3")) {

				}
			} while (!menu.equals("0")); // 초기화면으로 가는 처리는 아직 고민중
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 로그인한 상태에서 보여줄 메뉴를 출력
	 * @author 최종국
	 */
	private void customerMenu(RecipeInfo info) {
		String menu = null;
		try {
			do {
				System.out.println("1.구매하기 2.후기목록보기 3.즐겨찾기추가 4.좋아요 5.싫어요 0.이전화면");
				menu = sc.nextLine();
				if (menu.equals("1")) {
					purchaseRecipe(info);
				} else if (menu.equals("2")) {

				} else if (menu.equals("3")) {
					addFavorite(info);
				} else if (menu.equals("4")) {
					likeThisRecipe(info);
				} else if (menu.equals("5")) {
					disLikeThisRecipe(info);
				}
			} while (!menu.equals("0"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void addFavorite(RecipeInfo info) throws IOException {
		Favorite f = new Favorite();
		dio.sendMenu(Menu.ADD_FAVORITE);
		f.setCustomerId(CustomerShare.loginedId);
		f.setRecipeInfo(info);
		dio.send(f);
	
		if (dio.receiveStatus().equals("fail")) {
			FailView fail = new FailView();
			fail.favoriteListView("즐겨찾기 추가 실패");
		}
	}
	/**
	 * 현재 레시피의 좋아요 개수를 하나 증가한다
	 * @param info 현재 레시피 정보를 가진 RecipeInfo 객체
	 * @author 최종국
	 * @throws IOException 
	 */
	private void likeThisRecipe(RecipeInfo info) throws IOException {
		dio.sendMenu(Menu.LIKE);
		dio.send(info.getPoint());
		
		if(dio.receiveStatus().equals("fail")) {
			FailView fail = new FailView();
			fail.likeRecipe(dio.receive());
		}
	}
	
	/**
	 * 현재 레시피의 싫어요 개수를 하나 증가한다
	 * @param info 현재 레시피 정보를 가진 RecipeInfo 객체
	 * @author 최종국
	 * @throws IOException 
	 */
	private void disLikeThisRecipe(RecipeInfo info) throws IOException {
		dio.sendMenu(Menu.DISLIKE);
		dio.send(info.getPoint());
		
		if(dio.receiveStatus().equals("fail")) {
			FailView fail = new FailView();
			fail.likeRecipe(dio.receive());
		}
	}
	
	/**
	 * 현재레시피의 수량을 
	 * @param info
	 * @throws IOException
	 */
	private void purchaseRecipe(RecipeInfo info) throws IOException{
		System.out.println("수량을 입력해주세요");
		int line = Integer.parseInt(sc.nextLine());
		System.out.println("총가격은"+ line*info.getRecipePrice()+"입니다 구매하시겠습니까?(Y/N)");
		String purchaseLine = sc.nextLine();
		if(purchaseLine.equals("y")) {
			Purchase p = new Purchase();
			PurchaseDetail pd = new PurchaseDetail();
			p.setCustomerId(CustomerShare.loginedId);
			
			pd.setPurchaseDetailQuantity(line);
			pd.setRecipeInfo(info);
			
			p.setPurchaseDetail(pd);
			
			dio.sendMenu(Menu.PURCHASE);
			dio.send(p);
			System.out.println("send");
			if(dio.receiveStatus().equals("success")){
				SuccessView success = new SuccessView();
				success.purchaseView("구매성공");
			}else {
				FailView fail = new FailView();
				fail.purchaseView(dio.receive());
			}
		}
	}
}
