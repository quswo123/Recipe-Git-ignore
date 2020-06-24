package com.recipe.view;

public class SuccessView {
	/**
	 * 로그인 성공 메시지를 출력한다
	 * @param id 로그인한 아이디
	 * @author 최종국
	 */
	public void loginCustomerView(String id) {
		System.out.println(id + "로그인 성공");
	}
	/**
	 * 로그아웃 성공 메시지를 출력한다
	 * @author 최종국
	 */
	public void logoutCustomerView() {
		System.out.println("로그아웃 되었습니다");
	}
	/**
	 * 즐겨찾기 목록에서 삭제 시 성공메세지를 출력한다.
	 * @author 고수정
	 */
	public void favoriteDeleteView(String msg) {
		System.out.println(msg);
	}
}
