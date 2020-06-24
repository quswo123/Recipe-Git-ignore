package com.recipe.control;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.recipe.exception.FindException;
import com.recipe.exception.ModifyException;
import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.vo.Favorite;
import com.recipe.share.CustomerShare;
import com.recipe.vo.Purchase;
import com.recipe.vo.Customer;
import com.recipe.vo.RecipeInfo;
import com.recipe.vo.Review;


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
					// TO DO
					break;
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
					recommendRecipeFront();
					break;
				case Menu.SEARCH_RECIPE_CODE: // 레시피 코드 검색
					// TO DO
					break;
				case Menu.SEARCH_RECIPE_NAME: //레시피 제목 검색
					selectByNameFront();
					break;
				case Menu.SEARCH_RECIPE_INGREDIENTS: // 레시피 재료 검색
					selectByIngFront();
					break;
				case Menu.RECIPE_PROCESS: //레시피 과정 정보
					recipeProcessFront();
					break;
				case Menu.PURCHASE_LIST: // 구매 내역
					purchaseList();
					break;
				case Menu.CUSTOMER_LOGOUT:
					logoutFront();
					break;
				case Menu.SEARCH_FAVORITE_BY_CUSTOMERID: //로그인한 사용자 즐겨찾기 목록 보기
					favoriteByCustomerIdFront();
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
	 * @author 최종국
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
	
	/**
	 * customerId에 해당하는 즐겨찾기 목록을 조회한 후 반환한다.
	 * @throws IOException
	 * @author 고수정
	 */
	public void favoriteByCustomerIdFront() throws IOException {
		String customerId = dio.receive();
		List<Favorite> list = new ArrayList<>();
		try {
			list = control.viewFavorite(customerId);
			dio.sendFavorites(list);
			
		} catch (FindException e) {
		
		}
	}
	 /* 로그아웃에 필요한 ID를 클라이언트로부터 전달받아 로그아웃 절차를 수행한다.
	 * @throws IOException
	 * @author 최종국
	 */
	public void logoutFront() throws IOException {
		String customerId = dio.receiveId();
		CustomerShare.removeSession(customerId);
		
		dio.sendSuccess();
	}
	
	/**
	 * ID에 해당하는 구매목록을 클라이언트부터 전달받음
	 * @throws IOException
	 */
	public void purchaseList() throws IOException{
		String customerId = dio.receiveId();
		List<Purchase> list = null;
		List<Review> rlist = null;
		try {
			System.out.println("보냄");
			list = control.viewMyPurchase(customerId);
			rlist = control.viewMyReview(customerId);
			
			System.out.println("보냄1");
			dio.sendPurchase(list);
			dio.sendReviews(rlist);
			System.out.println("보냄2");
		} catch (FindException e) {
			e.printStackTrace();
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
	
	/**
	 * 클라이언트에게 추천 레시피를 탐색하여 전송한다
	 * @throws IOException
	 * @author 최종국
	 */
	public void recommendRecipeFront() throws IOException {
		RecipeInfo info = null;
		try {
			info = control.searchRecommended();
			dio.sendSuccess();
			dio.send(info);
		} catch (FindException e) {
			e.printStackTrace();
			dio.sendFail(e.getMessage());
		}
	}
	
	/**
	 * 레시피 과정 정보를 클라이언트에 전송한다
	 * @throws IOException
	 * @author 최종국
	 */
	public void recipeProcessFront() throws IOException {
		String filePath = dio.receive();
		String result = "";
		String process = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
			while((process = br.readLine()) != null) result += process + "\n";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		dio.send(result);
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
}
