package com.recipe.view;

import java.util.Scanner;

import com.recipe.io.DataIO;

public class RdMainView {
	private Scanner sc;
	private DataIO dio;
	
	public RdMainView(DataIO dio) {
		sc = new Scanner(System.in);
		this.dio = dio;
	}
	
	public void mainMenu() {
		int menu = -1;
		do {
			System.out.println("1.레시피등록 2.레시피검색 3.레시피전체보기 4.추천레시피 5.내정보보기 6.로그아웃 7.시스템종료");
			menu = Integer.parseInt(sc.nextLine());
			switch(menu) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				menu = -1;
				break;
			case 7:
				System.exit(0);
				break;
			}
		}while(menu != -1);
	}
}
