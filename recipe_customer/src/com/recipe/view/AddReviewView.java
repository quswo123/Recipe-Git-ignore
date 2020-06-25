package com.recipe.view;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.share.CustomerShare;
import com.recipe.vo.RecipeInfo;
import com.recipe.vo.Review;

/**
 * @author Soojeong
 *
 */
public class AddReviewView {

	private DataIO dio;
	private Scanner sc;
//	private String customerId;
//	private RecipeInfo recipeInfo;

	public AddReviewView(DataIO dio) {
		this.dio = dio;
	}

	public void insertReviewView(RecipeInfo info) throws IOException {
		System.out.println("===== 후기 등록  =====");
		System.out.print("== 구매 후기를 입력해주세요 : ");
		sc = new Scanner(System.in);
		String comment = sc.nextLine();

		dio.sendMenu(Menu.ADD_REVIEW);
		
		Review review = new Review();
		review.setCustomerId(CustomerShare.loginedId);
		review.setReviewComment(comment);
		review.setRecipeInfo(info);
		review.setReviewDate(new Date());
		dio.send(review);

		System.out.print("==== 1.좋아요 | 2.싫어요  : ");
		int select = sc.nextInt();
		if (select == 1) {
			dio.send(Menu.LIKE);
		} else {
			dio.send(Menu.DISLIKE);
		}

		String msg = dio.receiveStatus();
		if (msg.equals("success")) {
			SuccessView success = new SuccessView();
			success.reviewInsertView(msg);

		} else {
			FailView fail = new FailView();
			System.out.println(msg);
		}
	}

} // end class AddReviewView
