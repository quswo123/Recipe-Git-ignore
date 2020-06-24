package com.recipe.view;

import java.util.List;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.vo.RecipeInfo;

public class RecipeListView {
	private Scanner sc;		
	private DataIO dio;	
	public RecipeListView(DataIO dio) {
		this.dio = dio;
	}
	public void searchedRecipeList(List<RecipeInfo> recipeInfo) {
		Scanner sc = new Scanner(System.in);
		List<RecipeInfo> list = recipeInfo;
		System.out.println("[" + list.size() + "개의 건이 조회되었습니다]");
		int i =0;
		if (list.size() > 5) {
			//리스트 사이즈가 5개 넘으면 viewList 로 데이타를 넘겨줌
			viewList(list,i, 0);
		}
		else {
			//아닐경우 sout
			for(RecipeInfo ri : list) {
				System.out.println(i+1 + " | " + list.get(i).getRecipeName());
				i++;
			}
			System.out.println("0:다시검색 | *:메인메뉴");
			System.out.println("상세레시피를 보시려면 번호를 입력하세요");
		}
		String value = sc.nextLine();
		if (value.equals("*")) {

		} else if (value.equals("0")) {
			// showRecipeInfoView();
		} else {
			int t = Integer.valueOf(value);
			RecipeInfo param = recipeInfo.get(t-1);			
			RecipeInfoView infoView = new RecipeInfoView(dio);
			infoView.showRecipeInfoView(param);
		}
	}

	private void viewList(List<RecipeInfo> recipeInfo, int i, int j) {
		Scanner sc = new Scanner(System.in);
		// j+5 만큼 돔
		for (; i < j + 5; i++) {
			//recipeInfo 의 마지막 오브젝트에 달했을때
			if (i == recipeInfo.size()) {
				//i 값을 j-5 으로 대입해주고 , j 를 -5 해준다
				i = j - 5;
				j -= 5;
				break;
			}
			System.out.println(i+1 + " | " + recipeInfo.get(i).getRecipeName());

		}
		System.out.println("-:이전페이지 | +:다음페이지 | 0:다시검색 | *:메인메뉴");
		System.out.println("상세레시피를 보시려면 번호를 입력하세요");
		String value = null;
		do {
			value = sc.nextLine();
			switch (value) {
			case "+":
				viewList(recipeInfo, i, j + 5);
			case "-":
				if (i == 5 || i < 10) {
					// 첫페이지로 계속 갱신해줌
					viewList(recipeInfo, 0, 0);
				}
				//아닌경우, i -10 과 j-5로 갱신해줌
				viewList(recipeInfo, i - 10, j - 5);		
			default:
				int t = Integer.valueOf(value);
				RecipeInfo param = recipeInfo.get(t-1);			
				RecipeInfoView infoView = new RecipeInfoView(dio);
				infoView.showRecipeInfoView(param);
			}

		} while (value != Integer.toString(0));
		
	}
}
