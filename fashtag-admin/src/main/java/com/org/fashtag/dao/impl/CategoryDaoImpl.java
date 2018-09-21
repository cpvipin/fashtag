package com.org.fashtag.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.org.fashtag.AppDTO;
import com.org.fashtag.beans.Page;
import com.org.fashtag.context.ApplicationContext;
import com.org.fashtag.context.BeanConstants;
import com.org.fashtag.dao.CategoryDao;
import com.org.fashtag.dao.impl.BaseDaoImpl;
import com.org.fashtag.hibernate.PaginationCriteria;
import com.org.fashtag.model.Category;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.QueryUtils;



public class CategoryDaoImpl extends BaseDaoImpl implements CategoryDao {

	
	public void addCategory(Category category) {
		getHibernateTemplate().save(category);
		
	}
	

	public void updateCategory(Category category) {
		getHibernateTemplate().update(category);
		
	}
	
	
	public AppDTO listAllCategories(AppDTO appDTO)
	{
		Session session=getSession();
		try{
			Page categoryPage = (Page) appDTO.getControllerMap().get("PAGE");
			String sortField = categoryPage.getOrderBy();
			String categoryName = (String) appDTO.getControllerMap().get("name");
						
			PaginationCriteria pCriteria = getPaginationCriteria(session,Category.class);
			ArrayList aliasArr = new ArrayList();
			
			
			if(!CommonUtils.isEmpty(categoryName))
			{
			pCriteria.add(Expression.like("name","%"+categoryName+"%"));
			}
			
			
			
			
			if (!CommonUtils.isEmpty(sortField)) {
				String[] tokens = sortField.split("-");
				String sortFld = tokens[tokens.length - 1];
				for (int i = 0; i < tokens.length - 1; i++) {
					if (!aliasArr.contains(tokens[i])) {
						if (i > 0) {
							pCriteria.createAlias(tokens[i - 1] + "."
									+ tokens[i], tokens[i]);
						} else {
							pCriteria.createAlias(tokens[i], tokens[i]);
						}
					}
					if (i == (tokens.length - 2)) {
						sortField = tokens[i] + "." + sortFld;
					}
				}
				if (categoryPage.getOrder() == PaginationCriteria.ORDER_ASC) {
					pCriteria.addOrder(Order.asc(sortField));
				} else {
					pCriteria.addOrder(Order.desc(sortField));
				}
			}
			categoryPage = pCriteria.createPage(categoryPage);
			appDTO.getControllerMap().put("PAGE", categoryPage);
			
			
		}
		catch(Exception e)
		{
			
		}
		return appDTO;
	}
	
	
	public void deleteCategory(Category category)
	{
		getHibernateTemplate().delete(category);

	}
	
	
	
	

	public Collection getChildCategoryList(String likeKey)
	{

	
		Collection categoryColl=Collections.EMPTY_LIST;
	try	{
		
		categoryColl=((QueryUtils) ApplicationContext.getApplicationContext().
				getBean(BeanConstants.QUERYUTILS_DAO)).findByNamedQuery("ca.getChildCategories",
						new String[]{"name"},
						new Object[]{ likeKey });
		
	}catch(Exception e){ e.printStackTrace();}
		return categoryColl;
	}


	
	

}
