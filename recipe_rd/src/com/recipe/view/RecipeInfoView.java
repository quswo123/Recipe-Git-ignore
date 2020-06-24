package com.recipe.view;

import java.io.IOException;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.share.RDShare;
import com.recipe.vo.RecipeInfo;

public class RecipeInfoView {
	private Scanner sc;
	private DataIO dio;
	
	public RecipeInfoView(DataIO dio) {
		sc = new Scanner(System.in);
		this.dio = dio;
	}
	
	/**
	 * 레시피 정보를 화면에 출력하는 메소드. 이때 레시피 과정 정보를 서버에서 전달받아 화면에 출력한다.
	 * @param info 화면에 보여줄 레시피 정보
	 * @author 최종국
	 */
	public void showRecipeInfoView(RecipeInfo info) {
		System.out.println(info); //레시피 정보 출력
		try {
			dio.sendMenu(Menu.RECIPE_PROCESS); //레시피 과정 정보를 서버에 요청
			dio.send(info.getRecipeProcess()); //레시피 과정 경로를 서버에 전송 (이렇게 하는게 맞을지... recipeCode를 보내면 그에 대한 파일 경로를 검색해서 과정 정보를 보내주도록 하는게 맞는지...)
			System.out.println(dio.receive()); //레시피 과정 정보 출력
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(RDShare.loginedId.equals("")) {
			basicMenu();
		} else {
			rdMenu();
		}
	}
	
	/**
	 * 로그인하지 않은 상태에서 보여줄 메뉴를 출력
	 * @author 최종국
	 */
	private void basicMenu() {
		String menu = null;
		do {
			System.out.println("1.좋아요 2.싫어요 3.후기목록보기 0.목록으로 *초기화면");
			menu = sc.nextLine();
			if(menu.equals("1")) {
				
			} else if(menu.equals("2")) {
				
			} else if(menu.equals("3")) {
				
			}
		}while(menu.equals("0") || menu.equals("*")); //초기화면으로 가는 처리는 아직 고민중
	}
	
	/**
	 * 로그인한 상태에서 보여줄 메뉴를 출력
	 * @author 최종국
	 */
	private void rdMenu() {
		String menu = null;
		do {
			System.out.println("1.후기목록보기 2.수정하기 3.삭제하기 0.목록으로 *초기화면");
			menu = sc.nextLine();
			if(menu.equals("1")) {
				//레시피 후기 목록
			} else if(menu.equals("2")) {
				//레시피 수정
			} else if(menu.equals("3")) {
				//레시피 삭제
			}
		}while(menu.equals("0") || menu.equals("*"));
	}
}
