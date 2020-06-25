package com.recipe.view;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.vo.Review;

/**
 * @author Soojeong
 *
 */
public class ReviewListView {

	private String customerId;
	private int recipeCode;
	private DataIO dio;
	private Scanner sc;

	public ReviewListView(DataIO dio) {
		this.dio = dio;
	}
	
	/**
	 * 즐겨찾기 로그인 한 customerId로 목록조회
	 */
	public void showReviewListView(String customerId) {
		System.out.println("===== 나의 후기 목록 보기 =====");
		List<Review> reviewList = searchReviewList(customerId);
		/*목록 출력*/
		if ( reviewList.size() == 0 ) {
			FailView fail = new FailView();
			String msg = "등록된 후기가 없습니다.";
			fail.reviewListView(msg);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
			System.out.println("=="+reviewList.size() +"건의 등록된 즐겨찾기 목록이 조회되었습니다==");
			System.out.println("레시피상품명 | 후기작성일자 | 후기내용 ");
			for (Review r : reviewList ) {
				System.out.println(
						r.getRecipeInfo().getRecipeName()
						+ " | " + sdf.format(r.getReviewDate())
						+ " | " + r.getReviewComment()                                                           
				);
			}
		}

		/*목록 보기 하단 메뉴바*/
		System.out.println("---------------------------------------------");
		System.out.println("- 이전페이지 | + 다음페이지 ");
		sc = new Scanner(System.in);
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
			goBackMainView();
			break;
		default:
			System.out.println("상세페이지로 이동합니다.");
			break;
		}

	}
	
	/**
	 * 초기화면(메인메뉴)으로 이동
	 */
	private void goBackMainView() {
		System.out.println("아직 PL님이 고민중인 기능입니다!");
	}
	
	/**
	 * 즐겨찾기 로그인 한 customerId로 목록조회
	 */
	public void showReviewListByRecipeCodeView(int recipeCode) {
		System.out.println("===== 레시피 후기 목록 보기 =====");
		List<Review> reviewList = searchByRecipeCodeReviewList(recipeCode);
		/*목록 출력*/
		if ( reviewList.size() == 0 ) {
			FailView fail = new FailView();
			String msg = "등록된 후기가 없습니다.";
			fail.reviewListView(msg);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
			System.out.println("=="+reviewList.size() +"건의 등록된 즐겨찾기 목록이 조회되었습니다==");
			System.out.println("후기작성일자 | 후기내용 ");
			for (Review r : reviewList ) {
				System.out.println(sdf.format(r.getReviewDate()) + " | " + r.getReviewComment());
			}
		}
		
		/*목록 보기 하단 메뉴바*/
		System.out.println("---------------------------------------------");
		System.out.println("- 이전페이지 | + 다음페이지 ");
		sc = new Scanner(System.in);
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
			goBackMainView();
			break;
		default:
			System.out.println("상세페이지로 이동합니다.");
			break;
		}
		
	}
	
	
	/**
	 * Review 목록 보기 수행시, 사용자의 customerId로 조회한 결과값 반환한다.
	 * @param customerId 로그인한 사용자의 ID
	 */
	public List<Review> searchReviewList(String customerId) {
		List<Review> reviewList = null;
		try {
			//favorite 목록을 조회 절차 수행을 위한 customerId와 RecipeInfo객체 전송
			dio.sendMenu(Menu.SEARCH_REVIEW_BY_CUSTOMERID);
			dio.send(customerId);
			reviewList = dio.receiveReviews();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return reviewList;
	}
	
	/**
	 * Review 목록 보기 수행시, recipeCode로 조회한 결과값 반환한다.
	 * @param recipeCode
	 */
	public List<Review> searchByRecipeCodeReviewList(int recipeCode) {
		List<Review> reviewList = null;
		try {
			//favorite 목록을 조회 절차 수행을 위한 customerId와 RecipeInfo객체 전송
			dio.sendMenu(Menu.SEARCH_REVIEW_BY_RECIPECODE);
			dio.sendMenu(recipeCode);
			reviewList = dio.receiveReviews();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return reviewList;
	}
	
	
}// end class ReviewListView
