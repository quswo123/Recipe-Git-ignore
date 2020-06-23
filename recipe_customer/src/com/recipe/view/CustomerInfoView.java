package com.recipe.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.vo.Customer;

public class CustomerInfoView {
	private DataIO dataio;
	private Socket s;
	private Scanner sc;

	public CustomerInfoView() throws UnknownHostException, IOException {
		sc = new Scanner(System.in);
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
			// 응답
			if ("success".equals(dataio.receiveStatus())) {
				Customer receiveCustomer = dataio.receiveCustomer();
				System.out.println(receiveCustomer.getCustomerName());
				System.out.println(receiveCustomer.getCustomerId());
				System.out.println(receiveCustomer.getCustomerPwd());
				System.out.println(receiveCustomer.getCustomerEmail());
				System.out.println(receiveCustomer.getPostal().getCity() + receiveCustomer.getPostal().getDoro());
				System.out.println(receiveCustomer.getCustomerPhone());
				customerInfoMenu();
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
	public void customerInfoMenu() {
		String menu = null;
		System.out.print("메뉴 번호를 입력하세요 : ");
		menu = sc.nextLine();
		do {
			if (menu.equals("1")) {
				ModifyCustomerInfoView modifyinfoview = new ModifyCustomerInfoView();
			} else if (menu.equals("2")) {
				DeleteCustomerInfoView deleteinfoview = new DeleteCustomerInfoView();
			}
		} while (menu.equals("-"));

	}

	public static void main(String[] args) {
		CustomerInfoView view = null;
		try {
			view = new CustomerInfoView();
			view.viewMyAccount();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (view != null)
				try {
					view.s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
