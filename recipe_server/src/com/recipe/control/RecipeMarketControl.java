package com.recipe.control;

import com.recipe.exception.FindException;
import com.recipe.service.AccountService;
import com.recipe.service.FavoriteService;
import com.recipe.service.PostService;
import com.recipe.service.PurchaseService;
import com.recipe.service.RDAccountService;
import com.recipe.service.RecipeService;
import com.recipe.service.ReviewService;

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
	
	public void loginToAccount(String customerId, String customerPwd) throws FindException{
		accountService.login(customerId, customerPwd);
	}
}
