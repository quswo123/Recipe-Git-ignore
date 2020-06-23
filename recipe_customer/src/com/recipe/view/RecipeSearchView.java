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
		int num = sc.nextInt();
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
			searchedRecipeList(recipeInfo);
		} catch (IOException e) {		
			e.printStackTrace();
		}
		
	}
	private void searchedRecipeList(List<RecipeInfo> recipeInfo) {
		Scanner sc = new Scanner(System.in);
		List<RecipeInfo> list = recipeInfo;
		System.out.println("[" + list.size() + "개의 건이 조회되었습니다]");
		int i =0;
		if (list.size() > 5) {
			viewList(list,i, 0);
		}
		else {
			for(RecipeInfo ri : list) {
				System.out.println(i+1 + list.get(i).getRecipeCode() + "  " + i+1 + list.get(i).getRecipeName());
				i++;
			}
			System.out.println("0:다시검색 | *:메인메뉴");
			System.out.println("상세레시피를 보시려면 번호를 입력하세요");
		}
		String value = sc.nextLine();
		if (value.equals("*")) {
			
		} else if(value.equals("0")) {
			showRecipeInfoView();
		}
	}
	private void viewList(List<RecipeInfo> recipeInfo, int i, int j) {
		Scanner sc = new Scanner(System.in);
			
		for (; i < j+5; i++) {
			System.out.println(i+1 + " "+ recipeInfo.get(i).getRecipeName());
		}
		System.out.println("-:이전페이지 | +:다음페이지 | 0:다시검색 | *:메인메뉴");
		System.out.println("상세레시피를 보시려면 번호를 입력하세요");
		String value = sc.nextLine();
		if (value.equals("+")) {
			viewList(recipeInfo, i, j+5);
		} else if(value.equals("-")) {
			viewList(recipeInfo, i-10, j-5);
		} else if(value.equals("0")) {
			showRecipeInfoView();
		}
	}
	public static void main(String[] args) {		
		
		
	}
	
}
