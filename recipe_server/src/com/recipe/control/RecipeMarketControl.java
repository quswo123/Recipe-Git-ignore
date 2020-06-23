package com.recipe.control;

import java.util.List;

import com.recipe.exception.FindException;
import com.recipe.service.AccountService;
import com.recipe.service.FavoriteService;
import com.recipe.service.PostService;
import com.recipe.service.PurchaseService;
import com.recipe.service.RDAccountService;
import com.recipe.service.RecipeService;
import com.recipe.service.ReviewService;
import com.recipe.vo.RecipeInfo;

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
