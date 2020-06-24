package com.recipe.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.vo.RecipeInfo;
import com.recipe.vo.RecipeIngredient;

public class RecipeSearchView {
	private Scanner sc;		
	private DataIO dio;	
	public RecipeSearchView(DataIO dio) {
		this.dio = dio;
	}
	public void showRecipeInfoView() {
		Scanner sc = new Scanner(System.in);
		System.out.println("레시피 검색");
		System.out.println("1. 재료로 레시피 검색");
		System.out.println("2. 이름으로 레시피 검색");
		int num = Integer.parseInt(sc.nextLine());
		if (num == 1) {
			showFindbyIngNameView();
		} else if (num == 2) {
			showFindbyName();
		} else {
			showRecipeInfoView();
		}
		
	}
	private void showFindbyIngNameView() {
		Scanner sc = new Scanner(System.in);
		System.out.println("재료로 검색");
		System.out.println("재료를 입력하세요: ");
		
		
	}
	private void showFindbyName() {
		Scanner sc = new Scanner(System.in);
		System.out.println("이름으로 검색");
		System.out.println("이름을 입력하세요: ");
		String ingName = sc.nextLine();
		findByName(ingName);		
	}
	public void findByName(String recipeName){
		List<RecipeInfo> recipeInfo = null;
		try {
			dio.sendMenu(Menu.SEARCH_RECIPE_NAME);

			dio.send(recipeName);		
			recipeInfo = dio.receiveRecipeInfos();		
			dio.receiveStatus();
			RecipeListView listView = new RecipeListView(dio);
			listView.searchedRecipeList(recipeInfo);

		} catch (IOException e) {		
			e.printStackTrace();
		}
		
	}
	
	
	
}
