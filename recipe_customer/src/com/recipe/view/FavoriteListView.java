package com.recipe.view;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.vo.Favorite;

/**
 * @author Soojeong
 *
 */
public class FavoriteListView {
	private DataIO dio;

	public FavoriteListView(DataIO dio) {
		this.dio = dio;
	}

	/**
	 * 즐겨찾기 로그인 한 customerId로 목록조회
	 */
	public void showFavoriteListView(String customerId) {
		System.out.println("===== 즐겨찾기 목록 보기 =====");
		searchFavoriteList(customerId);
	}
	
	/**
	 * 즐겨찾기 목록 보기 수행시, 사용자의 customerId로 조회한 결과값 반환한다.
	 * @param customerId 로그인한 사용자의 ID
	 */
	public void searchFavoriteList(String customerId) {
		try {
			//favorite 목록을 조회 절차 수행을 위한 customerId와 RecipeInfo객체 전송
			dio.sendMenu(Menu.SEARCH_FAVORITE_BY_CUSTOMERID); //Menu.java에 추가할 것 
			dio.send(customerId);
			
			List<Favorite> favoriteList = dio.receiveFavorites();

			/*목록 출력*/
			if ( favoriteList.size() == 0 ) {
				FailView fail = new FailView();
				String msg = "즐겨찾기 추가 된 레시피가 없습니다.";
				fail.favoriteListView(msg);
			} else {
				System.out.println("=="+favoriteList.size() +"건의 등록된 즐겨찾기 목록이 조회되었습니다==");
				System.out.println("레시피코드 | 레시피이름");
				for (Favorite f : favoriteList ) {
					System.out.println(f.getRecipeInfo().getRecipeCode() + " | " + f.getRecipeInfo().getRecipeName());
				}
			}

			/*목록 보기 하단 메뉴바*/
			System.out.println("---------------------------------------------");
			System.out.println("- 이전페이지 | + 다음페이지 | * 메인메뉴 | D 즐겨찾기 해제 ");
			System.out.print("상세레시피를 보기원하시면 번호를 입력하세요 : ");
			Scanner sc = new Scanner(System.in);
			String menuKey = sc.nextLine();
			switch (menuKey) {
			case "-":
				System.out.println("이전페이지로 이동합니다.");
				break;
			case "+":
				System.out.println("다음페이지로 이동합니다.");
				break;
			case "*":
				System.out.println("메인메뉴로 이동합니다.");
				
				break;
			case "D":
				System.out.println("즐겨찾기 해제를 입력하셨습니다.");
				break;

			default:
				System.out.println("상세페이지로 이동합니다.");
				break;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
} // end class FavoriteListView
