package com.recipe.control;

import java.util.ArrayList;
import java.util.List;

import com.recipe.exception.AddException;
import com.recipe.exception.DuplicatedException;
import com.recipe.exception.FindException;
import com.recipe.exception.ModifyException;
import com.recipe.exception.RemoveException;
import com.recipe.service.AccountService;
import com.recipe.service.AdminAccountService;
import com.recipe.service.FavoriteService;
import com.recipe.service.PostService;
import com.recipe.service.PurchaseService;
import com.recipe.service.RDAccountService;
import com.recipe.service.RecipeService;
import com.recipe.service.ReviewService;
import com.recipe.vo.Customer;
import com.recipe.vo.Favorite;
import com.recipe.vo.Purchase;
import com.recipe.vo.RecipeInfo;
import com.recipe.vo.Review;

public class RecipeMarketControl {
	private static RecipeMarketControl control = new RecipeMarketControl();

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
	 * 
	 * @param customerId  Customer 클라이언트에서 전달받은 아이디
	 * @param customerPwd Customer 클라이언트에서 전달받은 패스워드
	 * @throws FindException
	 * @author 최종국
	 */
	public void loginToAccount(String customerId, String customerPwd) throws FindException {
		accountService.login(customerId, customerPwd);
	}

	/**
	 * R&D 클라이언트의 로그인 절차를 위한 Control 메소드
	 * 
	 * @param rdId  R&D 클라이언트에서 전달받은 아이디
	 * @param rdPwd R&D 클라이언트에서 전달받은 패스워드
	 * @throws FindException
	 * @author 최종국
	 */
	public void loginToRd(String rdId, String rdPwd) throws FindException {
		rdAccountService.login(rdId, rdPwd);
	}

	/**
	 * Admin 클라이언트의 로그인 절차를 위한 Control 메소드
	 * 
	 * @param adminId  Admin 클라이언트에서 전달받은 아이디
	 * @param adminPwd Admin 클라이언트에서 전달받은 패스워드
	 * @throws FindException
	 * @author 최종국
	 */
	public void loginToAdmin(String adminId, String adminPwd) throws FindException {
		adminAccountService.login(adminId, adminPwd);
	}

	/**
	 * 구매하기 메소드
	 * 
	 * @param p
	 * @throws AddException
	 * @author 변재원
	 */
	public void buyRecipe(Purchase p) throws AddException {
		purchaseService.buy(p);
	}

	/**
	 * 구매목록 메소드
	 * 
	 * @param List<Purchase>
	 * @throws AddException
	 * @author 변재원
	 */
	public List<Purchase> viewMyPurchase(String customerId) throws FindException {
		return purchaseService.findById(customerId);
	}

	/**
	 * 
	 * @param f
	 * @throws AddException
	 * @throws DuplicatedException
	 * @author 고수정
	 */
	public void addFavorite(Favorite f) throws AddException, DuplicatedException {
		favoriteService.add(f);
	}

	/**
	 * 
	 * @param customerId
	 * @throws FindException
	 * @author 고수정
	 */
	public List<Favorite> viewFavorite(String customerId) throws FindException {
		List<Favorite> list = new ArrayList<>();
		list = favoriteService.findById(customerId);
		return list;
	}

	/**
	 * 
	 * @param f
	 * @throws RemoveException
	 * @author 고수정
	 */
	public void removeFavorite(Favorite f) throws RemoveException {
		favoriteService.remove(f);
	}

	/**
	 * 
	 * @param recipeCode
	 * @throws FindException
	 * @author 고수정
	 */
	public void viewRecipeReview(int recipeCode) throws FindException {
		reviewService.findByCode(recipeCode);
	}

	/**
	 * 
	 * @param r
	 * @throws AddException
	 * @throws DuplicatedException
	 * @author 고수정
	 */
	public void addReview(Review r) throws AddException, DuplicatedException {
		reviewService.add(r);
	}

	/**
	 * 
	 * @param customerId
	 * @throws FindException
	 * @author 고수정
	 */
	public void viewMyReview(String customerId) throws FindException {
		reviewService.findById(customerId);
	}

	/**
	 * 
	 * @param recipeCode
	 * @return
	 * @throws FindException
	 * @author 이혜림
	 */
	public RecipeInfo searchByCode(int recipeCode) throws FindException {
		return recipeService.findByCode(recipeCode);
	}

	/**
	 * 
	 * @param recipeName
	 * @return
	 * @throws FindException
	 * @author 이혜림
	 */
	public List<RecipeInfo> searchByName(String recipeName) throws FindException {
		return recipeService.findByName(recipeName);
	}

	/**
	 * 
	 * @param ingName
	 * @return
	 * @throws FindException
	 * @author 이혜림
	 */
	public List<RecipeInfo> searchByIngName(List<String> ingName) throws FindException {
		return recipeService.findByIngName(ingName);
	}

	/*
	 * Control에서 accountService의 add 호출
	 * 
	 * @param Customer C
	 * 
	 * @author 영민
	 */
	public void addAccount(Customer c) throws AddException, DuplicatedException {
		accountService.add(c);
	}

	/*
	 * Control에서 accountService의 findById호출
	 * 
	 * @param String id
	 * 
	 * @author 영민
	 */
	public Customer viewMyAccount(String id) throws FindException {
		return accountService.findById(id);
	}

	/*
	 * Control에서 accountService의 modify호출
	 * 
	 * @param Customer c
	 * 
	 * @author 영민
	 */
	public void modifyMyAccount(Customer c) throws ModifyException {
		accountService.modify(c);
	}

	/*
	 * Control에서 accountService의 removeMyAccount
	 * 
	 * @param Cusotomer c
	 * 
	 * @author 영민
	 */
	public void removeMyAccount(Customer c) throws RemoveException {
		accountService.remove(c);
	}

	/**
	 * 추천 레시피 탐색 절차를 위한 메소드
	 * @return 추천 레시피 정보를 가진 RecipeInfo
	 * @throws FindException
	 */
	public RecipeInfo searchRecommended() throws FindException {
		return recipeService.findRecommended();
	}
}
