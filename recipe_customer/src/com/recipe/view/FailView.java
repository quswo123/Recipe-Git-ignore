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
	 * 즐겨찾기 목록 조회 실패 메시지를 출력한다
	 * @author 고수정
	 */
	public void favoriteListView(String msg) {
		System.out.println(msg);
	}
	
	 /* 추천 레시피 실패 메시지를 출력한다
	 * @author 최종국
	 */
	public void recommendedRecipe(String msg) {
		System.out.println("추천 레시피 실패 : " + msg);
	}
	
	/**
	 * 좋아요 실패 메시지를 출력한다
	 * @author 최종국
	 */
	public void likeRecipe(String msg) {
		System.out.println("좋아요 실패 : " + msg);
	}
	
	 /* 추천 레시피 실패 메시지를 출력한다
	 * @author 최종국
	 */
	public void reviewListView(String msg) {
		System.out.println(msg);
	}
}
