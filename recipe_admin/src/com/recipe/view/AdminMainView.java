package com.recipe.view;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.share.AdminShare;


public class AdminMainView {
	private Scanner sc;
	private DataIO dio;
	
	public AdminMainView(DataIO dio) {
		sc = new Scanner(System.in);
		this.dio = dio;
	}
	
	public void mainMenu() {
		int menu = -1;
		try {
			do {
				System.out.println("1.R&D계정추가 2.R&D계정수정 3.R&D계정삭제 4.R&D계정조회 5.로그아웃 6.시스템종료");
				menu = Integer.parseInt(sc.nextLine());
				switch (menu) {
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
					logout();
					break;
				case 6:
					System.exit(0);
					break;
				}
			} while (menu != -1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logout() throws IOException {
		dio.sendMenu(Menu.ADMIN_LOGOUT);
		dio.sendId(AdminShare.loginedId);
		
		if(dio.receiveStatus().equals("success")) {
			AdminShare.loginedId = "";
			SuccessView success = new SuccessView();
			success.logoutAdminView();
		} else {
			FailView fail = new FailView();
			fail.logoutAdminView();
		}
	}
}
