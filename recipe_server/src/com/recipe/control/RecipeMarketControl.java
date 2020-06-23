package com.recipe.control;

import com.recipe.exception.AddException;
import com.recipe.exception.DuplicatedException;
import com.recipe.exception.FindException;
import com.recipe.exception.ModifyException;
import com.recipe.exception.RemoveException;
import com.recipe.service.AccountService;
import com.recipe.service.FavoriteService;
import com.recipe.service.PostService;
import com.recipe.service.PurchaseService;
import com.recipe.service.RDAccountService;
import com.recipe.service.RecipeService;
import com.recipe.service.ReviewService;
import com.recipe.vo.Customer;

public class RecipeMarketControl {
	private static RecipeMarketControl control = new RecipeMarketControl();

	AccountService accountService;
	FavoriteService favoriteService;
	PostService postService;
	PurchaseService purchaseService;
	RDAccountService rdAccountService;
	RecipeService recipeService;
	ReviewService reviewService;

	private RecipeMarketControl() {
		accountService = new AccountService();
		favoriteService = new FavoriteService();
		postService = new PostService();
		purchaseService = new PurchaseService();
		rdAccountService = new RDAccountService();
		recipeService = new RecipeService();
		reviewService = new ReviewService();
	}

	public static RecipeMarketControl getInstance() {
		return control;
	}

	public void loginToAccount(String customerId, String customerPwd) throws FindException {
		accountService.login(customerId, customerPwd);
	}

	/*
	 * Control에서 accountService의 add 호출
	 * @param Customer C
	 * @author 영민
	 */
	public void addAccount(Customer c) throws AddException, DuplicatedException {
		accountService.add(c);
	}

	/*
	 * Control에서 accountService의 findById호출
	 * @param String id
	 * @author 영민
	 */
	public void viewMyAccount(String id) throws FindException {
		accountService.findById(id);
	}

	/*
	 * Control에서 accountService의 modify호출
	 * @param Customer c
	 * @author 영민
	 */
	public void modifyMyAccount(Customer c) throws ModifyException{
		accountService.modify(c);
	}

	/*
	 * Control에서 accountService의 removeMyAccount
	 * @param Cusotomer c
	 * @author 영민
	 */
	public void removeMyAccount(Customer c) throws RemoveException{
		accountService.remove(c);
	}
}
