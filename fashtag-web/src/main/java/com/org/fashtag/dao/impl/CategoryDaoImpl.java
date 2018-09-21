package com.org.fashtag.dao.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.org.fashtag.AppDTO;
import com.org.fashtag.context.ApplicationContext;
import com.org.fashtag.context.BeanConstants;
import com.org.fashtag.dao.CategoryDao;
import com.org.fashtag.dao.impl.BaseDaoImpl;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.CustomerBodyMeasurement;
import com.org.fashtag.util.QueryUtils;

public class CategoryDaoImpl extends BaseDaoImpl implements CategoryDao {
	
	public Collection getParentCategories(boolean activeStatus)
	{

	
		Collection categoryColl=Collections.EMPTY_LIST;
	try	{
		
		categoryColl=((QueryUtils) ApplicationContext.getApplicationContext().
				getBean(BeanConstants.QUERYUTILS_DAO)).findByNamedQuery("ca.getParentCategoriesByStatus",
						new String[]{"activeStatus"},
						new Object[]{ activeStatus });
		
	}
	catch(Exception e)
	{ 
		e.printStackTrace();
	}
		return categoryColl;
	}
	
	public Collection getMenuHierarchy(AppDTO appDTO)
	{
		Map dataMap=appDTO.getControllerMap();
		String menuId=(String)dataMap.get("PARENT_ID");
		SQLQuery query=null;
		Collection menuHierarchyList=Collections.EMPTY_LIST;
		try{
		
		String sqlQuery="SELECT t2.name as level2Name,t2.id as level2Id, t3.name as level3Name,t3.id as level3Id "+ 
			" FROM category AS t1 "+
			" LEFT JOIN category AS t2 ON t2.parent_id = t1.id and t2.active_status=true"+
			" LEFT JOIN category AS t3 ON t3.parent_id = t2.id  and t3.active_status=true"+
			" where t1.parent_id is null and t1.id="+Integer.parseInt(menuId)+" order by t2.sort_order,t3.sort_order";
		
		Session session = getSession();
		query=session.createSQLQuery(sqlQuery).addScalar("level2Name",Hibernate.STRING).addScalar("level2Id",Hibernate.INTEGER).addScalar("level3Name",Hibernate.STRING).addScalar("level3Id",Hibernate.INTEGER);
		menuHierarchyList=query.list();
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		
		return menuHierarchyList;
	}
	
	
	public Category getCategoryById(int categoryId)
	{
		return getHibernateTemplate().get(Category.class, categoryId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.org.fashtag.dao.CategoryDao#getBannerImagesByLayoutAndCategory(java.lang.String, int)
	 * Return banner images with layout and filetrid . if there is no filters available then pass 0 for this value.
	 */
	public Collection getBannerImagesByLayoutAndCategory(String layout,int filterId)
	{
		Collection bannerImages=getHibernateTemplate().findByNamedQueryAndNamedParam(
				"bannerImage.getBannerImageByLayoutAndFilter",
				new String[] { "layout", "filterId" },
				new Object[] { layout, filterId });
		
		return bannerImages;
		
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.org.fashtag.dao.CategoryDao#getChildCategories(int)
	 * 
	 * Return all active child categories
	 */
	public Collection getLeafNodesOfCategoryById(int catId)
	{
		
		Collection childCategories=getHibernateTemplate().findByNamedQueryAndNamedParam(
				"ca.getLeafNodesOfCategoryById",
				new String[] { "catId"},
				new Object[] { catId});
		
		return childCategories;
		
	}

	


}
