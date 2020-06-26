package com.recipe.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.share.RDShare;
import com.recipe.vo.Ingredient;
import com.recipe.vo.RecipeInfo;

public class ModifyRecipeView {

	private Scanner sc;
	private DataIO dio;

	public ModifyRecipeView(DataIO dio) {
		this.dio = dio;
		sc = new Scanner(System.in);
	}
	public void showModifyRecipeView(RecipeInfo recipeInfo) {

		List<Ingredient> ingList = new ArrayList<Ingredient>();

		System.out.println("레시피수정");
		System.out.println("레시피명을 입력해주세요: ");
		recipeInfo.setRecipeName(sc.nextLine());		//입력받은 레시피명을 setRecipe_name에 넣는다.

		System.out.println("------------------------------");
		String ingInfo="";
		while (true) {
			Ingredient ingredientVo = new Ingredient();				//재료명과, 용량을 넣기위한 VO 선언

			System.out.println("재료를 입력해주세요(종료는 exit입력): ");
			String name = sc.nextLine();
			if("exit".equals(name.toLowerCase())) {		//입력받은 값을 소문자로 변환후 exit라면 break;
				break;
			}
			ingInfo += name;
			ingredientVo.setIngName(name);		//입력받은 값을 setIng_name에 넣는다.

			sc = new Scanner(System.in);
			System.out.println("재료의 용량을 입력해주세요: ");
			ingInfo += " " +(sc.nextLine()) + " ";		//입력받은 값을 setIng_cpcty에 넣는다.

			ingList.add(ingredientVo);					//입력받은 VO를 ingList에 넣는다.
		}

		System.out.println("------------------------------");
		System.out.println("레시피 한줄 소개를 입력해주세요: ");
		recipeInfo.setRecipeSumm(sc.nextLine());

		System.out.println("------------------------------");
		System.out.println("가격을 입력해주세요.: ");
		recipeInfo.setRecipePrice(Integer.parseInt(sc.nextLine()));		//입력받은값을 Integer형식으로 바꿔서 setRecipe_price에 넣는다.
		
		System.out.println("------------------------------");
		String process="";
		while (true) {
			System.out.println("과정을 입력해주세요(종료는 exit입력): ");
			String temp = sc.nextLine();
			if("exit".equals(temp.toLowerCase())) {		//입력받은 값을 소문자로 변환후 exit라면 break;
				break;
			}
			process += "\n" + temp;
		}
		
		try {
			dio.sendMenu(Menu.MODIFY_RECIPE);
			dio.sendId(RDShare.loginedId);
			dio.send(recipeInfo);
			dio.send(ingInfo);
			dio.sendIngredientList(ingList);
			dio.send(process);
			
			if(dio.receiveStatus().equals("success")) {		//rd로 로그인이 잘됬는지?
				SuccessView success = new SuccessView();
				success.modifyRecipeView();
			}else {
				FailView fail = new FailView();
				fail.modifyRecipeView(dio.receive());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
