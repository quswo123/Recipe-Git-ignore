package com.recipe.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import com.recipe.exception.FindException;
import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.share.AdminShare;

public class AdminFrontThread implements Runnable{
	private Socket client;
	private DataIO dio;
	private RecipeMarketControl control;
	
	public AdminFrontThread(Socket s) {
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
				case Menu.ADMIN_LOGIN: // 로그인
					loginFront();
					break;
				case Menu.ADMIN_LOGOUT:
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
	 * 로그아웃할 아이디를 전달받아 로그아웃 절차를 수행한다.
	 * @throws IOException 
	 * @author 최종국
	 */
	private void logoutFront() throws IOException {
		String adminId = dio.receiveId();
		AdminShare.removeSession(adminId);
		
		dio.sendSuccess();
	}
}
