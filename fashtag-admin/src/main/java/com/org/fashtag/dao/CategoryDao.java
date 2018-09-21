package com.org.fashtag.dao;

import java.util.Collection;

import com.org.fashtag.AppDTO;
import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.model.Category;

public interface CategoryDao extends BaseDao {
	
	public void addCategory(Category category);
	
	public void updateCategory(Category category);
	
	public void deleteCategory(Category category);
	
	public AppDTO listAllCategories(AppDTO appDTO);
	
	public Collection getChildCategoryList(String likeKey);


}
