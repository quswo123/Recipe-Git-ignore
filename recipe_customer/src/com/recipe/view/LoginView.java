package com.recipe.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;

public class LoginView {
	private String id;
	private String pwd;
	private DataIO dio;
	
	public LoginView(Socket s) {
		try {
			dio = new DataIO(new DataOutputStream(s.getOutputStream()), new DataInputStream(s.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showLoginView() {
		Scanner sc = new Scanner(System.in);
		System.out.println("1.아이디를 입력하세요 : ");
		id = sc.nextLine();
		System.out.println("2.비밀번호를 입력하세요 : ");
		pwd = sc.nextLine();
		
		login(id, pwd);
		sc.close();
	}
	
	public void login(String id, String pwd) {
		try {
			dio.sendMenu(Menu.CUSTOMER_LOGIN);
			dio.sendId(id);
			dio.sendPwd(pwd);
			
			if(dio.receiveStatus().equals("success")) {
				SuccessView success = new SuccessView();
				success.loginCustomerView(id);
			} else {
				String msg = dio.receive();
				FailView fail = new FailView();
				fail.loginCustomerView(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
