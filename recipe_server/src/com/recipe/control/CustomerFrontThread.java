package com.recipe.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.recipe.exception.FindException;
import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.share.CustomerShare;

public class CustomerFrontThread implements Runnable{
	private Socket client;
	private DataIO dio;
	private RecipeMarketControl control;
	
	public CustomerFrontThread(Socket s) {
		client = s;
		try {
			dio = new DataIO(new DataOutputStream(client.getOutputStream()), new DataInputStream(client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		control = RecipeMarketControl.getInstance();
	}

	@Override
	public void run() {
		int menu = -1;
		try {
			do {
				menu = dio.receiveMenu();
				switch(menu) {
				case Menu.CUSTOMER_LOGIN: //로그인
					loginFront();
					break;
				case Menu.CUSTOMER_REGISTER: //회원가입
					//TO DO
					break;
				case Menu.RECOMMENDED_RECIPE: //추천 레시피
					//TO DO
					break;
				case Menu.SEARCH_RECIPE_CODE: //레시피 코드 검색
					//TO DO
					break;
				case Menu.SEARCH_RECIPE_NAME: //레시피 제목 검색
					//TO DO
					break;
				case Menu.SEARCH_RECIPE_INGREDIENTS: //레시피 재료 검색 
					//TO DO
					break;
				case Menu.PURCHASE_LIST: //구매 내역
					//TO DO
					break;
				default:
					break;
				}
			} while (menu != -1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 로그인에 필요한 ID, 패스워드를 Client로부터 전달받아 로그인 절차를 수행한다
	 * @throws IOException
	 */
	public void loginFront() throws IOException {
		String id = dio.receiveId();
		String pwd = dio.receivePwd();
		try {
			control.loginToAccount(id, pwd);
			dio.sendSuccess();
		} catch (FindException e) {
			dio.sendFail(e.getMessage());
		}
	}
}
