package com.org.fashtag.dao.impl;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.org.fashtag.AppDTO;
import com.org.fashtag.beans.Page;
import com.org.fashtag.dao.SizeDao;
import com.org.fashtag.dao.impl.BaseDaoImpl;
import com.org.fashtag.hibernate.PaginationCriteria;
import com.org.fashtag.model.Size;
import com.org.fashtag.util.CommonUtils;

public class SizeDaoImpl extends BaseDaoImpl implements SizeDao {


	
	public void addSize(Size size) {
		getHibernateTemplate().save(size);

	}
	
	

	public AppDTO listAllSizes(AppDTO appDTO) {

		Session session=getSession();
		try{
			Page categoryPage = (Page) appDTO.getControllerMap().get("PAGE");
			String sortField = categoryPage.getOrderBy();
			String sizeName = (String) appDTO.getControllerMap().get("name");
						
			PaginationCriteria pCriteria = getPaginationCriteria(session,Size.class);
			ArrayList aliasArr = new ArrayList();
			
			
			if(!CommonUtils.isEmpty(sizeName))
			{
			pCriteria.add(Expression.like("name","%"+sizeName+"%"));
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


	
	public void updateSize(Size size) {
		getHibernateTemplate().update(size);
		
	}
	
	

	public void deleteSize(Size size)
	{
		getHibernateTemplate().delete(size);

	}
	
	
	/*public Size getSizeById(int id)
	{
		
		return size;
	}
	*/
	
}
