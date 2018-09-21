package com.org.fashtag.service;

import java.util.Collection;

import com.org.fashtag.AppDTO;
import com.org.fashtag.model.Category;
import com.org.fashtag.service.BaseService;

public interface CategoryService  extends BaseService
{
	
	public AppDTO addCategory(AppDTO appDTO);
	
	public AppDTO updateCategory(AppDTO appDTO);
	
	public AppDTO deleteCategory(AppDTO appDTO);
	
	public Category getCategoryById(int id);
	
	public Collection getParentCategoryList(String likeKey);
	
	public AppDTO listAllCategories(AppDTO appDTO);
	
}
