package com.recipe.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.recipe.exception.FindException;
import com.recipe.exception.ModifyException;
import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.service.PostService;
import com.recipe.vo.Customer;
import com.recipe.vo.Postal;

public class CustomerFrontThread implements Runnable {
	private Socket client;
	private DataIO dio;
	private RecipeMarketControl control;

	public CustomerFrontThread(Socket s) {
		client = s;
		try {
			dio = new DataIO(new DataOutputStream(client.getOutputStream()),
					new DataInputStream(client.getInputStream()));
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
				switch (menu) {
				//내 정보 보기 . 영민
				case Menu.CUSTOMER_INFO: // 내 정보 보기
					String id = dio.receiveId();
					try {
						Customer c1 = control.viewMyAccount(id);
						System.out.println(c1);
						dio.sendSuccess();
						dio.send(c1);
					} catch (FindException e) {
						e.printStackTrace();
						dio.sendFail(e.getMessage());
					}
					break;
				case Menu.CUSTOMER_LOGIN: // 로그인
					loginFront();
					break;
				case Menu.CUSTOMER_REGISTER: // 회원가입
					break;
				//내 정보 수정 .영민
				case Menu.CUSTOMER_MODIFY: //내 정보 수정
					Customer c2 = dio.receiveCustomer();
					try {
						control.modifyMyAccount(c2);
						dio.sendSuccess();
					} catch (ModifyException e) {
						e.printStackTrace();
						dio.sendFail(e.getMessage());
					}
					break;
				case Menu.SEARCH_POSTAL:
					String doro = dio.receive();
					List<Postal> list;
					try {
						list = control.searchByDoro(doro);
						dio.sendSuccess();
						dio.send("" + list.size());
						for(Postal p : list) {
							dio.send(p);
						}
					} catch (FindException e) {
						e.printStackTrace();
						dio.sendFail(e.getMessage());
					}
					
				case Menu.RECOMMENDED_RECIPE: // 추천 레시피
					// TO DO
					break;
				case Menu.SEARCH_RECIPE_CODE: // 레시피 코드 검색
					// TO DO
					break;
				case Menu.SEARCH_RECIPE_NAME: // 레시피 제목 검색
					// TO DO
					break;
				case Menu.SEARCH_RECIPE_INGREDIENTS: // 레시피 재료 검색
					// TO DO
					break;
				case Menu.PURCHASE_LIST: // 구매 내역
					// TO DO
					break;
				default:
					break;
				}
			} while (menu != -1);
		} catch (EOFException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * 로그인에 필요한 ID, 패스워드를 Client로부터 전달받아 로그인 절차를 수행한다
	 * 
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
