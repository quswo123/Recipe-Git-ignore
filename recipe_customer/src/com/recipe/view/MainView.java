package com.recipe.view;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MainView {
	private static final int CUSTOMER_PORT = 1025;
	Scanner sc;
	Socket s;
	
	public MainView() {
		sc = new Scanner(System.in);
		try {
			s = new Socket("127.0.0.1", CUSTOMER_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void mainMenu() {
		int menu = -1;
		do {
			System.out.println("1.레시피검색 2.추천레시피 3.로그인 4.회원가입 0.프로그램 종료");
			menu = Integer.parseInt(sc.nextLine());
			switch(menu) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				LoginView loginView = new LoginView(s);
				
				break;
			case 4:
				break;
			case 5:
				break;
			default:
				menu = -1;
				break;	
			}
		}while(menu != 0);
	}
	
	public static void main(String[] args) {

	}

}
