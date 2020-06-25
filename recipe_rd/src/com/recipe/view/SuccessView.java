package com.recipe.view;

public class SuccessView {
	/**
	 * 로그인 성공 메시지를 출력한다
	 * @param id 로그인한 아이디
	 * @author 최종국
	 */
	public void loginRdView(String id) {
		System.out.println(id + "로그인 성공");
	}
	/**
	 * 로그인 실패 메시지를 출력한다
	 */
	public void logoutRdView() {
		System.out.println("로그아웃 되었습니다");
	}
	
	public void addRecipeView() {
		System.out.println("레시피가 등록되었습니다");
	}
}
