package com.recipe.view;

import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;

public class CustomerMainView {
	private Scanner sc;
	private DataIO dio;
	
	public CustomerMainView(DataIO dio) {
		sc = new Scanner(System.in);
		this.dio = dio;
	}
	
	public void mainMenu() {
		int menu = -1;
		do {
			System.out.println("1.레시피검색 2.추천레시피 3.구매내역 4.즐겨찾기보기 5.나의후기목록 6.내 정보보기 7.로그아웃 8.시스템종료");
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
				break;
			case 7:
				menu = -1;
				break;
			case 8:
				System.exit(0);
				break;
			}
		}while(menu != -1);
	}
}
