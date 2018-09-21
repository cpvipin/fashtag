package com.org.fashtag.dao;

import java.util.Collection;

import com.org.fashtag.AppDTO;
import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.model.Category;

public interface CategoryDao extends BaseDao {
	
	public Collection getParentCategories(boolean activeStatus);
	
	public Collection getMenuHierarchy(AppDTO appDTO);

	public Category getCategoryById(int categoryId);

	public Collection getBannerImagesByLayoutAndCategory(String layout,int filterId);
	
	public Collection getLeafNodesOfCategoryById(int catId);
}
