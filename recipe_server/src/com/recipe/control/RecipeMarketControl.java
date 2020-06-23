package com.recipe.control;

import java.util.ArrayList;
import java.util.List;

import com.recipe.exception.AddException;
import com.recipe.exception.DuplicatedException;
import java.util.List;
import com.recipe.exception.FindException;
import com.recipe.exception.RemoveException;
import com.recipe.service.AccountService;
import com.recipe.service.AdminAccountService;
import com.recipe.service.FavoriteService;
import com.recipe.service.PostService;
import com.recipe.service.PurchaseService;
import com.recipe.service.RDAccountService;
import com.recipe.service.RecipeService;
import com.recipe.service.ReviewService;
import com.recipe.vo.Purchase;
import com.recipe.vo.Favorite;
import com.recipe.vo.Review;
import com.recipe.vo.RecipeInfo;

public class RecipeMarketControl {
	private static RecipeMarketControl control = new RecipeMarketControl(); //singleton
	
	AccountService accountService;
	FavoriteService favoriteService;
	PostService postService;
	PurchaseService purchaseService;
	RDAccountService rdAccountService;
	RecipeService recipeService;
	ReviewService reviewService;
	AdminAccountService adminAccountService;
	
	private RecipeMarketControl() {
		accountService = new AccountService();
		favoriteService = new FavoriteService();
		postService = new PostService();
		purchaseService = new PurchaseService();
		rdAccountService = new RDAccountService();
		recipeService = new RecipeService();
		reviewService = new ReviewService();
		adminAccountService = new AdminAccountService();
	}
	
	public static RecipeMarketControl getInstance() {
		return control;
	}
	
	/**
	 * Customer 클라이언트의 로그인 절차를 위한 Control 메소드
	 * @param customerId Customer 클라이언트에서 전달받은 아이디
	 * @param customerPwd Customer 클라이언트에서 전달받은 패스워드
	 * @throws FindException
	 */
	public void loginToAccount(String customerId, String customerPwd) throws FindException{
		accountService.login(customerId, customerPwd);
	}
	
	/**
	 * R&D 클라이언트의 로그인 절차를 위한 Control 메소드
	 * @param rdId R&D 클라이언트에서 전달받은 아이디
	 * @param rdPwd R&D 클라이언트에서 전달받은 패스워드
	 * @throws FindException
	 */
	public void loginToRd(String rdId, String rdPwd) throws FindException {
		rdAccountService.login(rdId, rdPwd);
	}
	
	/**
	 * Admin 클라이언트의 로그인 절차를 위한 Control 메소드
	 * @param adminId Admin 클라이언트에서 전달받은 아이디
	 * @param adminPwd Admin 클라이언트에서 전달받은 패스워드
	 * @throws FindException
	 */
	public void loginToAdmin(String adminId, String adminPwd) throws FindException{
		adminAccountService.login(adminId, adminPwd);
	}
	
	//구매하기control
	public void buyRecipe(Purchase p) throws AddException{
		purchaseService.buy(p);
	}
	
	//나의 구매목록
	public List<Purchase> viewMyPurchase(String customerId) throws FindException{
		List<Purchase> list = new ArrayList<>();
		list = purchaseService.findById(customerId);
		return list; 
	}

	public void addFavorite(Favorite f) throws AddException, DuplicatedException {
		favoriteService.add(f);
	}
	
	public void viewFavorite(String customerId) throws FindException {
		favoriteService.findById(customerId);
	}
	
	public void removeFavorite(Favorite f) throws RemoveException {
		favoriteService.remove(f);
	}
	
	public void viewRecipeReview(int recipeCode) throws FindException{
		reviewService.findByCode(recipeCode);
	}
	
	public void addReview(Review r ) throws AddException, DuplicatedException {
		reviewService.add(r);
	}
	
	public void viewMyReview(String customerId) throws FindException {
		reviewService.findById(customerId);
	}
	
	public RecipeInfo searchByCode(int recipeCode) throws FindException{
		return recipeService.findByCode(recipeCode);
	}
	public List<RecipeInfo> searchByName(String recipeName) throws FindException{
		return recipeService.findByName(recipeName);
	}
	public List<RecipeInfo> searchByIngName(List<String> ingName) throws FindException{
		return recipeService.findByIngName(ingName);
	}
}
