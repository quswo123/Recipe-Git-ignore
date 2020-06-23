package com.recipe.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.vo.Customer;

public class CustomerInfoView {
	private DataIO dataio;
	private Socket s;

	public CustomerInfoView() throws UnknownHostException, IOException {
		s = new Socket("localhost", 1025);
		DataInputStream dis = new DataInputStream(s.getInputStream());
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		dataio = new DataIO(dos, dis);
	}

	public void viewMyAccount() {
		String customerId = "kosj";
		try {
			// 내정보보기를 요청
			dataio.sendMenu(Menu.CUSTOMER_INFO);
			// id를 송신
			dataio.sendId(customerId);
			System.out.println("기다리는 중!");
			// 응답
			if ("success".equals(dataio.receiveStatus())) {
				System.out.println("성공:");
				Customer receiveCustomer = dataio.receiveCustomer();
				System.out.println("내정보");
				System.out.println(receiveCustomer.getCustomerName());
			} else {
				String failMsg = dataio.receive();
				System.out.println(failMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public static void main(String[] args) { CustomerInfoView view = null; try {
	 * view = new CustomerInfoView(); view.viewMyAccount(); } catch
	 * (UnknownHostException e) { e.printStackTrace(); } catch (IOException e) {
	 * e.printStackTrace(); } finally { if (view != null) try { view.s.close(); }
	 * catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } }
	 */
}
