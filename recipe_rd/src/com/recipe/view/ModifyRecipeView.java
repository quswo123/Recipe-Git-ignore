package com.recipe.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.recipe.vo.Ingredient;
import com.recipe.vo.RecipeInfo;

public class ModifyRecipeView {

	private Scanner sc;

	public void ModifyRecipeView() {
		RecipeInfo recipe_InfoVo = new RecipeInfo();

		System.out.println("레시피수정");

		sc = new Scanner(System.in);
		System.out.println("레시피명을 입력해주세요: ");
		recipe_InfoVo.setRecipeName(sc.nextLine());

		System.out.println("------------------------------");
		List<Ingredient> ingList = new ArrayList<Ingredient>();		//재료를 넣기위한 ingList를 선언
		while (true) {
			Ingredient ingredientVo = new Ingredient();

			sc = new Scanner(System.in);
			System.out.println("재료를 입력해주세요(종료는 exit입력): ");
			String ing_name = sc.nextLine();
			if("exit".contentEquals(ing_name.toLowerCase())) {
				break;	
			}
			ingredientVo.setIngName(ing_name);	
			
			sc = new Scanner(System.in);
			System.out.println("재료의 용량을 입력해주세요: ");
//			ingredientVo.setIngCpcty(sc.nextLine());
			
			ingList.add(ingredientVo);		//입력받은 VO를 ingList에 넣는다.
		}
		System.out.println("ingList: " + ingList.toString());
		
		System.out.println("------------------------------");
		String ing_summ = "";
		while(true) {
			sc = new Scanner(System.in);
			System.out.println("요리 설명을 입력해주세요(종료는 exit입력):");
			ing_summ = "\n" + sc.nextLine();
			recipe_InfoVo.setRecipeSumm(ing_summ);
			
			if("exit".equals(ing_summ.toLowerCase())) {
				break;
			}
		}
		
		System.out.println("------------------------------");
		sc = new Scanner(System.in);
		System.out.println("가격을 입력해주세요.: ");
		recipe_InfoVo.setRecipePrice(Integer.parseInt(sc.nextLine()));
		
//		String resultFlag = new RecipeMarketControl().addRecipe(recipe_InfoVo, ingList);
//		
//		if("".contentEquals(resultFlag)) {
//			System.out.println("등록이 완료되었습니다.");
//			System.out.println("recipe_InfoVo: ");
//		}
	}
}
