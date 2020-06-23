package com.recipe.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.vo.RecipeInfo;

public class RecipeInfoView {
	private Scanner sc;	
	private Socket s;
	private DataIO dio;
	public RecipeInfoView(Socket s) {
		try {
			dio = new DataIO(new DataOutputStream(s.getOutputStream()), new DataInputStream(s.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	public List<RecipeInfo> findByName(String recipeName){
		return null;
	}
	public static void main(String[] args) {
		Socket s = new Socket();
		RecipeInfoView riv = new RecipeInfoView(s);
		riv.showRecipeInfoView();		
	}
	
}
