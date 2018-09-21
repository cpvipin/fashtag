package com.org.fashtag.dao.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.org.fashtag.AppDTO;
import com.org.fashtag.context.ApplicationContext;
import com.org.fashtag.context.BeanConstants;
import com.org.fashtag.context.TransactionManager;
import com.org.fashtag.dao.CartDao;
import com.org.fashtag.dao.CategoryDao;
import com.org.fashtag.dao.CustomerDao;
import com.org.fashtag.dao.impl.BaseDaoImpl;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerBodyMeasurement;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.util.QueryUtils;
import com.org.fashtag.util.UniqueResultHibCallback;

public class CartDaoImpl extends BaseDaoImpl implements CartDao {
	
	
	public void updateCustomerCart(String cart, int customerId)
	{
		String hql="update Customer set cart=:cart where id=:custId";
		Query query=getSession().createQuery(hql);
		
		query.setParameter("cart", cart);
		query.setParameter("custId",customerId);
		query.executeUpdate();
		
	}
	
}
