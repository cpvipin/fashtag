package com.org.fashtag.dao.impl;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.org.fashtag.AppDTO;
import com.org.fashtag.beans.Page;
import com.org.fashtag.dao.BodyMeasurementAttributeDao;
import com.org.fashtag.dao.impl.BaseDaoImpl;
import com.org.fashtag.hibernate.PaginationCriteria;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.util.CommonUtils;

public class BodyMeasurementAttributeDaoImpl extends BaseDaoImpl implements BodyMeasurementAttributeDao {


	
	public void addBodyMeasurementAttribute(BodyMeasurementAttribute bodyMeasurementAttribute) {
		getHibernateTemplate().save(bodyMeasurementAttribute);

	}
	
	

	public AppDTO listAllBodyMeasurementAttributes(AppDTO appDTO) {

		Session session=getSession();
		try{
			Page attrPage = (Page) appDTO.getControllerMap().get("PAGE");
			String sortField = attrPage.getOrderBy();
			String attrName = (String) appDTO.getControllerMap().get("name");
						
			PaginationCriteria pCriteria = getPaginationCriteria(session,BodyMeasurementAttribute.class);
			ArrayList aliasArr = new ArrayList();
			
			
			if(!CommonUtils.isEmpty(attrName))
			{
			pCriteria.add(Expression.like("name","%"+attrName+"%"));
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
				if (attrPage.getOrder() == PaginationCriteria.ORDER_ASC) {
					pCriteria.addOrder(Order.asc(sortField));
				} else {
					pCriteria.addOrder(Order.desc(sortField));
				}
			}
			attrPage = pCriteria.createPage(attrPage);
			appDTO.getControllerMap().put("PAGE", attrPage);
			
			
		}
		catch(Exception e)
		{
			
		}
		return appDTO;
	}


	
	public void updateBodyMeasurementAttribute(BodyMeasurementAttribute bodyMeasurementAttribute) {
		getHibernateTemplate().update(bodyMeasurementAttribute);
		
	}
	
	

	public void deleteBodyMeasurementAttribute(BodyMeasurementAttribute bodyMeasurementAttribute)
	{
		getHibernateTemplate().delete(bodyMeasurementAttribute);

	}
	
	

	
}
