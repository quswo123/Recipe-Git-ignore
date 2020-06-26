package com.recipe.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.share.CustomerShare;
import com.recipe.vo.Customer;

public class DeleteCustomerInfoView {
	private DataIO dataio;
	private Scanner sc;
	

	/*
	 * IO 연결
	 * 
	 * @author 영민
	 */
	public DeleteCustomerInfoView(DataIO dio) throws UnknownHostException, IOException {
		sc = new Scanner(System.in);
		dataio = dio;

	}

	public void deleteMyAccount() {
		try {
			// 회원탈퇴를 요청
			dataio.sendMenu(Menu.CUSTOMER_REMOVE);
			// Customer 정보를 송신
			dataio.sendId(CustomerShare.loginedId);
			if ("success".equals(dataio.receiveStatus())) {
				System.out.println("정상적으로 탈퇴되었습니다!");
			} else {
				String failMsg = dataio.receive();
				System.out.println(failMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
