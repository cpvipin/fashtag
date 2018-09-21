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
import com.org.fashtag.dao.VendorDao;
import com.org.fashtag.dao.impl.BaseDaoImpl;
import com.org.fashtag.hibernate.PaginationCriteria;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.Vendor;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.QueryUtils;




public class VendorDaoImpl extends BaseDaoImpl implements VendorDao {

	
	public void addVendor(Vendor vendor) {
		getHibernateTemplate().save(vendor);
		
	}

	public AppDTO listAllVendors(AppDTO appDTO)
	{
		Session session=getSession();
		try{
			Page vendorPage = (Page) appDTO.getControllerMap().get("PAGE");
			String sortField = vendorPage.getOrderBy();
			String vendorName = (String) appDTO.getControllerMap().get("name");
						
			PaginationCriteria pCriteria = getPaginationCriteria(session,Vendor.class);
			ArrayList aliasArr = new ArrayList();
			
			
			if(!CommonUtils.isEmpty(vendorName))
			{
			pCriteria.add(Expression.like("name","%"+vendorName+"%"));
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
				if (vendorPage.getOrder() == PaginationCriteria.ORDER_ASC) {
					pCriteria.addOrder(Order.asc(sortField));
				} else {
					pCriteria.addOrder(Order.desc(sortField));
				}
			}
			vendorPage = pCriteria.createPage(vendorPage);
			appDTO.getControllerMap().put("PAGE", vendorPage);
			
			
		}
		catch(Exception e)
		{
			
		}
		return appDTO;
	}

	
	public void updateVendor(Vendor vendor) {
		getHibernateTemplate().update(vendor);
		
	}
	
	public void deleteVendor(Vendor vendor)
	{
		getHibernateTemplate().delete(vendor);

	}


	public Collection getVendorsByName(String likeKey) {

		Collection vendorColl=Collections.EMPTY_LIST;
	try	{
		
		vendorColl=((QueryUtils) ApplicationContext.getApplicationContext().
				getBean(BeanConstants.QUERYUTILS_DAO)).findByNamedQuery("getVendorsByName",
						new String[]{"name"},
						new Object[]{ likeKey });
		
	}catch(Exception e){
		

	}
	
	
		return vendorColl;
		
		
	}


}
