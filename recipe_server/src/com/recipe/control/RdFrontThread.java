package com.recipe.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.recipe.exception.FindException;
import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.share.RDShare;
import com.recipe.vo.RecipeInfo;

public class RdFrontThread implements Runnable{
	private Socket client;
	private DataIO dio;
	private RecipeMarketControl control;
	
	public RdFrontThread(Socket s) {
		client = s;
		try {
			dio = new DataIO(new DataOutputStream(client.getOutputStream()), new DataInputStream(client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		control = RecipeMarketControl.getInstance();
	}
	
	/**
	 * 전달받은 메뉴 번호에 해당하는 절차를 수행한다.
	 */
	@Override
	public void run() {
		int menu = -1;
		try {
			do {
				menu = dio.receiveMenu();
				switch (menu) {
				case Menu.RD_LOGIN: // 로그인
					loginFront();
					break;
				case Menu.RECOMMENDED_RECIPE: // 추천 레시피
					// TO DO
					break;
				case Menu.SEARCH_RECIPE_CODE: // 레시피 코드 검색
					
					break;
				case Menu.SEARCH_RECIPE_NAME: // 레시피 제목 검색
					selectByNameFront();
					break;
				case Menu.SEARCH_RECIPE_INGREDIENTS: // 레시피 재료 검색
					selectByIngFront();
					break;
				case Menu.RD_LOGOUT:
					logoutFront();
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
	 * @throws IOException
	 * @author 최종국
	 */
	public void loginFront() throws IOException {
		String id = dio.receiveId();
		String pwd = dio.receivePwd();
		try {
			control.loginToRd(id, pwd);
			dio.sendSuccess();
		} catch (FindException e) {
			dio.sendFail(e.getMessage());
		}
	}
	
	/**
	 * 로그아웃에 필요한 아이디를 전달받아 로그아웃 절차를 수행한다.
	 * @throws IOException
	 * @author 최종국
	 */
	public void logoutFront() throws IOException {
		String rdId = dio.receiveId();
		RDShare.removeSession(rdId);
		
		dio.sendSuccess();
	}
	public void selectByIngFront() throws IOException {
		List<String> recipeInfo = dio.receiveListString();
		List<RecipeInfo> searchedRecipeInfo = null;		
		try {
			searchedRecipeInfo = control.searchByIngName(recipeInfo);
			dio.send(searchedRecipeInfo);
			//dio.sendSuccess();
		} catch (FindException e) {
			dio.sendFail(e.getMessage());
		}
	}
	/**
	 * 레시피이름에 해당하는 레시피목록을 클라이언트부터 전달받음
	 * @throws IOException
	 */
	public void selectByNameFront() throws IOException {
		List<RecipeInfo> recipeInfo = null;
		String recipeName = dio.receive();
		try {
			recipeInfo = control.searchByName(recipeName);
			dio.send(recipeInfo);
			//dio.sendSuccess();
		} catch (FindException e) {
			dio.sendFail(e.getMessage());
		}
	}
}
