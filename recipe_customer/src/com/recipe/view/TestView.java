package com.recipe.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.recipe.io.DataIO;

/**
 * @author Soojeong
 * 테스트 파일 입니다. upload 금지
 */
public class TestView {

	public static void main(String[] args) {
		Socket s;
		DataIO dio;
		
		try {
			s = new Socket("127.0.0.1", 1025);
			dio = new DataIO(new DataOutputStream(s.getOutputStream()), new DataInputStream(s.getInputStream()));
			FavoriteListView view = new FavoriteListView(dio);
			String customerId = "tester";
			view.showFavoriteListView(customerId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
} // end class TestView()
