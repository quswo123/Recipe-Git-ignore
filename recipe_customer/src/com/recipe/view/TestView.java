package com.recipe.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.recipe.io.DataIO;
import com.recipe.share.CustomerShare;
import com.recipe.vo.Favorite;
import com.recipe.vo.Point;
import com.recipe.vo.RecipeInfo;

/**
 * @author Soojeong
 * 테스트 파일 입니다. upload 금지
 */
public class TestView {

	public static void main(String[] args) {
		Socket s;
		DataIO dio;
		
		try {
			s = new Socket("127.0.0.1", 1025);
			dio = new DataIO(new DataOutputStream(s.getOutputStream()), new DataInputStream(s.getInputStream()));
			CustomerShare.loginedId ="kosj";
			RecipeInfo info = new RecipeInfo();
			info.setRecipeCode(135);
			info.setIngredients(null);
			info.setRecipeName(" ");
			info.setRecipePrice(0 );
			info.setRecipeProcess("adasdad");
			info.setRecipeSumm("asdasdasd");
			
			Point p = new Point();
			p.setDisLikeCount(3151);
			p.setLikeCount(32125);
			p.setRecipeCode(135);
			info.setPoint(p);
			
			/*test_favorite*/
			
			FavoriteListView view = new FavoriteListView(dio);
			
			Favorite f = new Favorite();
			f.setCustomerId(CustomerShare.loginedId);
			f.setRecipeInfo(info);
			view.insertFavorite(f);
			/*test_review*/
			//ReviewListView view = new ReviewListView(dio);
			//view.showReviewListView(CustomerShare.loginedId);
			//view.showReviewListByRecipeCodeView(134);
			String msg = dio.receiveStatus();
			System.out.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
} // end class TestView()
