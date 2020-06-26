package com.recipe.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.recipe.io.DataIO;
import com.recipe.share.CustomerShare;
import com.recipe.vo.Point;
import com.recipe.vo.RecipeInfo;

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
			CustomerShare.loginedId = "backym";
			RecipeInfo info = new RecipeInfo();
			info.setRecipeCode(135);
			info.setIngredients(null);
			info.setRecipeName("유부계란찜");
			info.setRecipePrice(3000);
			info.setRecipeProcess("C:/project/recipeMakret_server/resource/recipeProcess/135.txt");
			info.setRecipeSumm("유부에 넣어 달걀찜을 하면 도시락 반찬으로 모양도 예쁘고 맛도 있는 센스만점 반찬이 된답니다~");

			Point p = new Point();
			p.setLikeCount(71);
			p.setDisLikeCount(52);
			p.setRecipeCode(135);
			info.setPoint(p);

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
			AddReviewView view = new AddReviewView(dio);
			view.insertReviewView(info);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

} // end class TestView()
