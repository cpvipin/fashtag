package com.org.fashtag.dao.impl;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.org.fashtag.AppDTO;
import com.org.fashtag.beans.Page;
import com.org.fashtag.dao.DesignAttributeDao;
import com.org.fashtag.dao.impl.BaseDaoImpl;
import com.org.fashtag.hibernate.PaginationCriteria;
import com.org.fashtag.model.DesignAttribute;
import com.org.fashtag.model.DesignAttributeSpecification;
import com.org.fashtag.util.CommonUtils;

public class DesignAttributeDaoImpl extends BaseDaoImpl implements DesignAttributeDao{
	
	/* Design Attribute */

	public void addDesignAttribute(DesignAttribute designAttribute) {

		getHibernateTemplate().save(designAttribute);
	}



	
	public AppDTO listAllDesignAttributes(AppDTO appDTO) {

		Session session=getSession();
		try{
			Page categoryPage = (Page) appDTO.getControllerMap().get("PAGE");
			String sortField = categoryPage.getOrderBy();
			String designAttributeName = (String) appDTO.getControllerMap().get("name");
						
			PaginationCriteria pCriteria = getPaginationCriteria(session,DesignAttribute.class);
			ArrayList aliasArr = new ArrayList();
			
			
			if(!CommonUtils.isEmpty(designAttributeName))
			{
			pCriteria.add(Expression.like("name","%"+designAttributeName+"%"));
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

	

	
	public void updateDesignAttribute(DesignAttribute designAttribute) {
		getHibernateTemplate().update(designAttribute);

	}
	
	

	
	public void deleteDesignAttribute(DesignAttribute designAttribute) {
		
		getHibernateTemplate().delete(designAttribute);

	}
	
	/* design attribute specification*/
	
	public void addDesignAttributeSpecification(DesignAttributeSpecification designAttrSpec)
	{
		getHibernateTemplate().save(designAttrSpec);
		
		
	}
	
	

	public AppDTO listAllDesignAttrSpec(AppDTO appDTO) {

		Session session=getSession();
		try{
			Page designAttrSpecPage = (Page) appDTO.getControllerMap().get("PAGE");
			String sortField = designAttrSpecPage.getOrderBy();
			Integer designAttributeId =(Integer) appDTO.getControllerMap().get("designAttributeId");
						
			PaginationCriteria pCriteria = getPaginationCriteria(session,DesignAttributeSpecification.class);
			ArrayList aliasArr = new ArrayList();
			
			if (designAttributeId != null && designAttributeId.intValue() > 0) {
				pCriteria.add(Expression.eq("designAttribute.id", designAttributeId));
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
				if (designAttrSpecPage.getOrder() == PaginationCriteria.ORDER_ASC) {
					pCriteria.addOrder(Order.asc(sortField));
				} else {
					pCriteria.addOrder(Order.desc(sortField));
				}
			}
			designAttrSpecPage = pCriteria.createPage(designAttrSpecPage);
			appDTO.getControllerMap().put("PAGE", designAttrSpecPage);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return appDTO;
	}

	

}
