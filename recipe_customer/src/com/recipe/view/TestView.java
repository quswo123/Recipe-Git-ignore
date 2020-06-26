package com.recipe.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.recipe.io.DataIO;
import com.recipe.share.CustomerShare;
import com.recipe.vo.Review;

/**
 * @author Soojeong 테스트 파일 입니다. upload 금지
 */
public class TestView {

	public static void main(String[] args) {
		Socket s;
		DataIO dio;

		try {
			s = new Socket("127.0.0.1", 1025);
			dio = new DataIO(new DataOutputStream(s.getOutputStream()), new DataInputStream(s.getInputStream()));
			
			//test_MyReviewView(dio);
			//test_MyReviewViewList(dio);
			test_recipeCodeReviewViewList(dio);
//			CustomerShare.loginedId = "backym";
//			RecipeInfo info = new RecipeInfo();
//			info.setRecipeCode(135);
//			info.setIngredients(null);
//			info.setRecipeName("유부계란찜");
//			info.setRecipePrice(3000);
//			info.setRecipeProcess("C:/project/recipeMakret_server/resource/recipeProcess/135.txt");
//			info.setRecipeSumm("유부에 넣어 달걀찜을 하면 도시락 반찬으로 모양도 예쁘고 맛도 있는 센스만점 반찬이 된답니다~");
//
//			Point p = new Point();
//			p.setLikeCount(71);
//			p.setDisLikeCount(52);
//			p.setRecipeCode(135);
//			info.setPoint(p);

			/* test_favorite */
			// FavoriteListView view = new FavoriteListView(dio);
			// Favorite f = new Favorite();
			// f.setCustomerId(CustomerShare.loginedId);
			// f.setRecipeInfo(info);
			// view.insertFavorite(f);
			/* test_review */
			// ReviewListView view = new ReviewListView(dio);
			// view.showReviewListView(CustomerShare.loginedId);
			// view.showReviewListByRecipeCodeView(134);

			/* review insert Test */
//			AddReviewView view = new AddReviewView(dio);
//			view.insertReviewView(info);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void test_MyReviewView (DataIO dio) {
		MyReviewListView view = new MyReviewListView(dio);
		CustomerShare.loginedId = "kosj";
		view.showMyReviewListView(CustomerShare.loginedId);
	}
	
	private static void test_MyReviewViewList (DataIO dio) {
		MyReviewListView view = new MyReviewListView(dio);
		CustomerShare.loginedId = "kosj";
		List<Review> list = view.searchReviewList(CustomerShare.loginedId);
		
		for (Review r : list ) {
			System.out.println(r.getCustomerId());
			System.out.println(r.getReviewComment());
			System.out.println(r.getReviewDate());
		}
	}
	
	private static void test_recipeCodeReviewViewList (DataIO dio) {
		ReviewListView view = new ReviewListView(dio);
		int recipeCode = 134;
		view.showReviewListByRecipeCodeView(recipeCode);
	}

} // end class TestView()
