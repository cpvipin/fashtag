package com.org.fashtag.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.context.AdminBeanConstants;
import com.org.fashtag.dao.CategoryDao;
import com.org.fashtag.dao.impl.CategoryDaoImpl;
import com.org.fashtag.model.AdminUser;
import com.org.fashtag.model.Category;
import com.org.fashtag.service.CategoryService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.DateUtils;
import com.org.fashtag.util.PasswordEncryptor;



public class CategoryServiceImpl extends BaseServiceImpl implements CategoryService,AdminBeanConstants {

	
	private CategoryDaoImpl categoryDao;

	public AppDTO addCategory(AppDTO appDTO) {
		
		Map controllerMap=appDTO.getControllerMap();
		Category category=(Category)controllerMap.get("CATEGORY");
		boolean isDuplicateName=((CategoryDao) getDAOBean(CATEGORY_DAO)).isDuplicateField(Category.class, 
				"name", null, null, category.getName());
		
		if(isDuplicateName)
		{ 
			appDTO.setErrorMessage(getResourceMessage("DUPLICATE_CATEGORY"));
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			return appDTO;
		}
		
		Category parentCategory=(Category)((CategoryDao) getDAOBean(CATEGORY_DAO))
								.findObjectByCondition
								(Category.class,
								new String[] {"id"},
								new Object[] {category.getParent().getId()
								});
		
		category.setParent(parentCategory);
		category.setDateAdded(DateUtils.getCurrentDate());
		category.setDateModified(DateUtils.getCurrentDate());
		((CategoryDao) getDAOBean(CATEGORY_DAO)).addCategory(category);
		appDTO.setInfoMessage(getResourceMessage("CATEGORY_ADDED"));
		
		return appDTO;
		
	}
	
		
	public AppDTO updateCategory(AppDTO appDTO) {
		try{

		Map controllerMap=appDTO.getControllerMap();
		Category category=(Category)controllerMap.get("CATEGORY");
		
		Category parentCategory=(Category)((CategoryDao) getDAOBean(CATEGORY_DAO)).
								findObjectByCondition(Category.class,
								new String[] {"id"},
								new Object[] {category.getParent().getId()});
				category.setParent(parentCategory);
				category.setDateModified(DateUtils.getCurrentDate());
				
				((CategoryDao) getDAOBean(CATEGORY_DAO)).updateCategory(category);
				
				appDTO.setInfoMessage(getResourceMessage("CATEGORY_UPDATED"));
		}catch(Exception e){e.printStackTrace();}
		
			return appDTO;
	}

	
	public AppDTO deleteCategory(AppDTO appDTO) {
		try
		{
			Map controllerMap=appDTO.getControllerMap();
			Category category=(Category)controllerMap.get("CATEGORY");
			((CategoryDao) getDAOBean(CATEGORY_DAO)).deleteCategory(category);
			appDTO.setInfoMessage(getResourceMessage("CATEGORY_DELETED"));
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			
		}
		catch(Exception e)
		{
			appDTO.setErrorMessage(getResourceMessage("CHILD_RECORD_EXISTS"));
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			return appDTO;
		}
		return appDTO;
	}
	
	public Collection getParentCategoryList(String likeKey)
	{
		Collection categoryList=new ArrayList();
		categoryList=((CategoryDao) getDAOBean(CATEGORY_DAO))
		.findListByLikeSelection(Category.class, "name", likeKey+"%");
		
		return categoryList;
	}
	
	
	public AppDTO listAllCategories(AppDTO appDTO)
	{
		
		appDTO=((CategoryDao) getDAOBean(CATEGORY_DAO)).listAllCategories(appDTO);
		return appDTO;
	}
	
	/* getters and setters */

	public CategoryDaoImpl getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDaoImpl categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	public Category getCategoryById(int id)
	{
		Category category=(Category)((CategoryDao) getDAOBean(CATEGORY_DAO)).
				findObjectByCondition
				(Category.class,new String[] 
				{"id"},
				new Object[] {id});
		
		return category;
	}


	

}
