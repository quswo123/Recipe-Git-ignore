package com.recipe.view;

import java.io.IOException;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.vo.RecipeInfo;

public class RecipeInfoView {
	private DataIO dio;
	
	public RecipeInfoView(DataIO dio) {
		this.dio = dio;
	}
	
	/**
	 * 레시피 정보를 화면에 출력하는 메소드. 이때 레시피 과정 정보를 서버에서 전달받아 화면에 출력한다.
	 * @param info 화면에 보여줄 레시피 정보
	 * @author 최종국
	 */
	public void showRecipeInfoView(RecipeInfo info) {
		System.out.println(info);
		try {
			dio.sendMenu(Menu.RECIPE_PROCESS);
			dio.send(info.getRecipeProcess());
			System.out.println(dio.receive());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
