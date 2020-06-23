package com.recipe.view;

import java.util.Scanner;

public class AdminMainView {
	private Scanner sc;
	
	public AdminMainView() {
		sc = new Scanner(System.in);
	}
	
	public void mainMenu() {
		int menu = -1;
		do {
			System.out.println("1.R&D계정추가 2.R&D계정수정 3.R&D계정삭제 4.R&D계정조회 5.로그아웃 6.시스템종료");
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
				menu = -1;
				break;
			case 6:
				System.exit(0);
				break;
			}
		}while(menu != -1);
	}
}
