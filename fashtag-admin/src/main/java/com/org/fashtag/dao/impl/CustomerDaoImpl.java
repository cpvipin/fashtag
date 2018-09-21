package com.org.fashtag.dao.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.org.fashtag.AppDTO;
import com.org.fashtag.beans.Page;
import com.org.fashtag.context.TransactionManager;
import com.org.fashtag.dao.CustomerDao;
import com.org.fashtag.dao.SizeDao;
import com.org.fashtag.dao.impl.BaseDaoImpl;
import com.org.fashtag.hibernate.PaginationCriteria;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerBodyMeasurement;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.model.Size;
import com.org.fashtag.model.State;
import com.org.fashtag.util.CommonUtils;

public class CustomerDaoImpl extends BaseDaoImpl implements CustomerDao {


	
	public void addCustomer(Customer customer) {
		
		TransactionManager.txCreate(customer);

	}
	
	

	public AppDTO listCustomer(AppDTO appDTO) {

		Session session=getSession();
		try{
			Page customerPage = (Page) appDTO.getControllerMap().get("PAGE");
			String sortField = customerPage.getOrderBy();
			String customerName = (String) appDTO.getControllerMap().get("name");
						
			PaginationCriteria pCriteria = getPaginationCriteria(session,Customer.class);
			ArrayList aliasArr = new ArrayList();
			
			
			if(!CommonUtils.isEmpty(customerName))
			{
			pCriteria.add(Expression.like("fullName","%"+customerName+"%"));
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
				if (customerPage.getOrder() == PaginationCriteria.ORDER_ASC) {
					pCriteria.addOrder(Order.asc(sortField));
				} else {
					pCriteria.addOrder(Order.desc(sortField));
				}
			}
			customerPage = pCriteria.createPage(customerPage);
			appDTO.getControllerMap().put("PAGE", customerPage);
			
			
		}
		catch(Exception e)
		{
			
		}
		return appDTO;
	}


	
	public void updateCustomer(Customer customer) {
		TransactionManager.txUpdate(customer);
		
	}
	
	public boolean deleteCustomerIp(Customer customer)
	{

		Session session=TransactionManager.getInstance().getTxSession();
		try {
			session.createQuery(
					"delete from CustomerIp where " + "customer.id="
							+ customer.getId()).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean deleteCustomerAddress(Customer customer)
	{
		
		Session session=TransactionManager.getInstance().getTxSession();
		try {
			session.createQuery(
					"delete from CustomerAddress where " + "customer.id="
							+ customer.getId()).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteCustomer(Customer customer)
	{
		TransactionManager.begin();
		try{
		if(deleteCustomerAddress(customer))
		{
			if(deleteCustomerIp(customer))
			{
		TransactionManager.txDelete(customer);
		TransactionManager.commit();
			}
		}
		}
		catch(Exception e)
		{
	TransactionManager.rollback();
		}
		finally{
			TransactionManager.closeSession();
		}
		return true;
		
	}
	
	
	public Collection getAllStates()
	{
	Collection allStates=findAll(State.class);	
	return allStates;
	}
	
	public void deleteCustomerBodyMeasurement(CustomerBodyMeasurement customerBodyMeasurement)
	{
		try{
		TransactionManager.txDelete(customerBodyMeasurement);
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void addCustomerBodyMeasurement(CustomerBodyMeasurement customerBodyMeasurement){
		try{
		TransactionManager.txCreate(customerBodyMeasurement);
		}catch(Exception e){
			
			
			e.printStackTrace();
		}
	}
	
	public CustomerProfile getCustomerProfileById(int customerProfileId)
	{
		return getHibernateTemplate().get(CustomerProfile.class,customerProfileId);
	}
	
	public CustomerProfile createNewCustomerProfile(CustomerProfile customerProfile)
	{
		
		getHibernateTemplate().save(customerProfile);
		return customerProfile;

	}
	
	
}
