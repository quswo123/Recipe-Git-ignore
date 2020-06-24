package com.recipe.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.recipe.io.DataIO;
import com.recipe.io.Menu;
import com.recipe.vo.Customer;
import com.recipe.vo.Postal;

public class ModifyCustomerInfoView {
	private DataIO dataio;
	private Socket s;
	private Scanner sc;

	public ModifyCustomerInfoView() throws UnknownHostException, IOException {
		sc = new Scanner(System.in);
		s = new Socket("localhost", 1025);
		DataInputStream dis = new DataInputStream(s.getInputStream());
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		dataio = new DataIO(dos, dis);

	}

	public void modifyMyAccount() {
		Customer c = modifyInfoMenu();
		if (c == null) {
			try {
				// 내정보보기를 요청
				dataio.sendMenu(Menu.CUSTOMER_MODIFY);
				// id를 송신
				dataio.send(c);
				if ("success".equals(dataio.receiveStatus())) {
					System.out.println("수정되었습니다!");
				} else {
					String failMsg = dataio.receive();
					System.out.println(failMsg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Customer modifyInfoMenu() {
		Customer c = new Customer();
		System.out.println("내 정보 수정하기");
		System.out.print("1.비밀번호를 입력해주세요  : ");
		String customerPwd = sc.nextLine();
		if (!"".equals(customerPwd)) {
			c.setCustomerPwd(customerPwd);
		}
		System.out.print("2.이름을 입력해주세요  : ");
		String customerName = sc.nextLine();
		if (!"".equals(customerName)) {
			c.setCustomerPwd(customerName);
		}
		System.out.print("3.이메일을 입력해주세요  : ");
		String customerEmail = sc.nextLine();
		if (!"".equals(customerEmail)) {
			c.setCustomerPwd(customerEmail);
		}
		System.out.print("4.도로명 주소를 입력해주세요  : ");
		String doro = sc.nextLine();
		if (!"".equals(doro)) {
			try {
				dataio.sendMenu(Menu.SEARCH_POSTAL);
				dataio.send(doro);
				if ("success".equals(dataio.receiveStatus())) {
					int size = Integer.parseInt(dataio.receive());
					List<Postal> list = new ArrayList<>();
					for (int i = 0; i < size; i++) {
						Postal p = dataio.receivePostal();
						list.add(p);
						System.out.println((i + 1) + ":" + p.getZipcode() + " " + p.getCity() + " " + p.getDoro() + " "
								+ p.getBuilding());
					}
					System.out.println("번호를 선택하세요");
					int index = Integer.parseInt(sc.nextLine());
					if (index < 0 || index >= list.size()) {
						System.out.println("잘못입력하셨습니다");
						return null;
					}
					Postal modifyPostal = list.get(index);
					c.setPostal(modifyPostal);
				} else {
					String failMsg = dataio.receive();
					System.out.println(failMsg);
					return null;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		System.out.print("5.핸드폰 번호를 입력해주세요  : ");
		String customerphone = sc.nextLine();

		return c;
	}

	public static void main(String[] args) {
		ModifyCustomerInfoView view1 = null;
		try {
			view1 = new ModifyCustomerInfoView();
			view1.modifyMyAccount();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (view1 != null)
				try {
					view1.s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

}
