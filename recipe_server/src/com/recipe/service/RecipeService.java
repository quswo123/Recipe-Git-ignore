package com.recipe.service;

import java.util.List;

import com.recipe.dao.RecipeInfoDAO;
import com.recipe.exception.FindException;
import com.recipe.vo.RecipeInfo;

public class RecipeService {
	private RecipeInfoDAO dao = new RecipeInfoDAO();	
	public List<RecipeInfo> findByName(String recipeName) throws FindException{
		return dao.selectByName(recipeName);		
	}
	public RecipeInfo findByCode(int recipeCode) throws FindException {
		return dao.selectByCode(recipeCode);
	}
	
	

}
