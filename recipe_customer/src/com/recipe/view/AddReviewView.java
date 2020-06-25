package com.recipe.view;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.vo.RecipeInfo;
import com.recipe.vo.Review;

/**
 * @author Soojeong
 *
 */
public class AddReviewView {

	private DataIO dio;
	private Scanner sc;
	private String customerId;
	private RecipeInfo recipeInfo;
	public AddReviewView(DataIO dio) {
		this.dio = dio;
	}
	
	public void insertReviewView () throws IOException {
		System.out.println("===== 후기 등록  =====");
		System.out.println("== 구매 후기를 입력해주세요 : ");
		String comment = sc.nextLine();
		
		dio.sendMenu(Menu.ADD_REVIEW);
		Review review = new Review();
		review.setCustomerId(customerId);
		review.setRecipeInfo(recipeInfo);
		
		try {
			dio.receiveReview();
			String msg = "후기 등록 성공 완료!";
			SuccessView success = new SuccessView();
			success.reviewInsertView(msg);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	
} //end class AddReviewView
