package com.recipe.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.recipe.vo.Ingredient;
import com.recipe.vo.RecipeInfo;

public class AddRecipeView {

	private Scanner sc;

	public void AddRecipeFormView() {
		RecipeInfo recipe_InfoVo = new RecipeInfo();

		System.out.println("레시피등록");

		sc = new Scanner(System.in);
		System.out.println("레시피명을 입력해주세요: ");
		recipe_InfoVo.setRecipeName(sc.nextLine());		//입력받은 레시피명을 setRecipe_name에 넣는다.

		System.out.println("------------------------------");
		List<Ingredient> ingList = new ArrayList<Ingredient>();		//재료를 넣기위한 ingList를 선언
		while (true) {
			Ingredient ingredientVo = new Ingredient();				//재료명과, 용량을 넣기위한 VO 선언

			sc = new Scanner(System.in);
			System.out.println("재료를 입력해주세요(종료는 exit입력): ");
			String ing_name = sc.nextLine();
			if("exit".equals(ing_name.toLowerCase())) {		//입력받은 값을 소문자로 변환후 exit라면 break;
				break;
			}
			ingredientVo.setIngName(ing_name);		//입력받은 값을 setIng_name에 넣는다.

			sc = new Scanner(System.in);
			System.out.println("재료의 용량을 입력해주세요: ");
		//	ingredientVo.setIngCpcty(sc.nextLine());		//입력받은 값을 setIng_cpcty에 넣는다.

			ingList.add(ingredientVo);					//입력받은 VO를 ingList에 넣는다.
		}

		System.out.println("ingList: " + ingList.toString());		//현재 담겨져 있는것을 출력test

		System.out.println("------------------------------");

		sc = new Scanner(System.in);
		System.out.println("레시피 한줄 소개를 입력해주세요: ");
		recipe_InfoVo.setRecipeSumm(sc.nextLine());
		//		while (true) {		//여러줄 레시피 요약설명적기
		//			sc = new Scanner(System.in);
		//			System.out.println("요리 설명을 입력해주세요(종료는 exit입력): ");
		//			ing_summ = "\n" + sc.nextLine();
		//			recipe_InfoVo.setRecipeSumm(ing_summ);			//setRecipe_summ에 입력받은값을 넣는다.
		//
		//			if("exit".equals(ing_summ.toLowerCase())) {
		//				break;
		//			}
		//		}

		System.out.println("------------------------------");
		sc = new Scanner(System.in);
		System.out.println("가격을 입력해주세요.: ");
		recipe_InfoVo.setRecipePrice(Integer.parseInt(sc.nextLine()));		//입력받은값을 Integer형식으로 바꿔서 setRecipe_price에 넣는다.


		System.out.println("recipe_InfoVo: " + recipe_InfoVo.toString());

		//		String resultFlag = new RecipeMarketControl().addRecipe(recipe_InfoVo, ingList);
		//		
		//		if("".equals(resultFlag)) {
		//			System.out.println("등록이 완료되었습니다.");
		//			System.out.println("recipe_InfoVo: " + recipe_InfoVo.toString());
		//		}else {
		//			System.out.println(resultFlag);
		//		}
	}
//	public static void main(String []args) {
//		AddRecipeView addview = new AddRecipeView();
//		addview.AddRecipeFormView();
//	}
}
