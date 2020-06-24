package com.recipe.view;

public class FailView {
	/**
	 * 로그인 실패 메시지를 출력한다
	 * @param msg 발생한 오류 메시지
	 * @author 최종국
	 */
	public void loginCustomerView(String msg) {
		System.out.println(msg);
	}
	/**
	 * 로그아웃 실패 메시지를 출력한다
	 * @author 최종국
	 */
	public void logoutCustomerView() {
		System.out.println("로그아웃 실패");
	}
	
	/**
<<<<<<< HEAD
	 * 즐겨찾기 목록 조회 실패 메시지를 출력한다
	 * @author 고수정
	 */
	public void favoriteListView(String msg) {
		System.out.println(msg);
	}
	

=======
	 * 추천 레시피 실패 메시지를 출력한다
	 * @author 최종국
	 */
	public void recommendedRecipe() {
		System.out.println("추천 레시피 실패");
	}
>>>>>>> 90e1451617b4b88c7e7cd6165b38d6dd4341f37d
}
