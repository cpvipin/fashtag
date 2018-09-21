package com.org.fashtag.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.context.AdminBeanConstants;
import com.org.fashtag.context.TransactionManager;
import com.org.fashtag.dao.CustomerDao;
import com.org.fashtag.dao.SizeDao;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerAddress;
import com.org.fashtag.model.CustomerBodyMeasurement;
import com.org.fashtag.model.CustomerIp;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.model.Size;
import com.org.fashtag.model.State;
import com.org.fashtag.service.CustomerService;
import com.org.fashtag.service.SizeService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.DateUtils;
import com.org.fashtag.util.PasswordEncryptor;

public class CustomerServiceImpl extends BaseServiceImpl implements
		CustomerService, AdminBeanConstants {

	private CustomerDao customerDao;
	
	/* size services */

	public AppDTO addCustomer(AppDTO appDTO) {

		Map controllerMap = appDTO.getControllerMap();
		Customer customer = (Customer) controllerMap.get("CUSTOMER");
		HttpServletRequest req = (HttpServletRequest) controllerMap
				.get("REQUEST");
		Integer count = Integer.parseInt((String) controllerMap
				.get("ADDRESS_COUNT"));

		customer.setDateAdded(DateUtils.getCurrentDate());
		customer.setDateModified(DateUtils.getCurrentDate());
		try {
			TransactionManager.begin();
			customer.setIsAdminAdded(true);
			customer.setIsFirstLogin(true);
			customer.setPassword(PasswordEncryptor.encrypt(customer.getPassword()));
			((CustomerDao) getDAOBean(CUSTOMER_DAO)).addCustomer(customer);

			if(!addCustomerAddress(controllerMap))
			{
				appDTO.setInfoMessage(getResourceMessage("UNEXPECTED_ERROR"));

				TransactionManager.rollback();
				
			}
			TransactionManager.commit();
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			appDTO.setInfoMessage(getResourceMessage("CUSTOMER_ADDED"));

		} catch (Exception e) {
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			appDTO.setInfoMessage(getResourceMessage("UNEXPECTED_ERROR"));

			TransactionManager.rollback();
		}

		return appDTO;
	}
	
	
	private boolean addCustomerAddress(Map controllerMap)
	{
		Customer customer = (Customer) controllerMap.get("CUSTOMER");
		HttpServletRequest req = (HttpServletRequest) controllerMap
				.get("REQUEST");
		Integer count = Integer.parseInt((String) controllerMap
				.get("ADDRESS_COUNT"));
		
		try{
		for (int i = 0; i < count; i++) {
		State state = (State) ((CustomerDao) getDAOBean(CUSTOMER_DAO))
		.findObjectByCondition(State.class,
				new String[] { "id" },
				new Object[] { Integer.parseInt(req
						.getParameter("state" + i)) });

boolean isDefault = req.getParameter("isDefault" + i) != null ? true : false;

CustomerAddress custAddr = new CustomerAddress();
custAddr.setFullName(req.getParameter("fullName" + i));
custAddr.setAddress(req.getParameter("address" + i));
custAddr.setCustomer(customer);
custAddr.setIsDefault(isDefault);
custAddr.setLocality(req.getParameter("locality" + i));
custAddr.setMobile(req.getParameter("mobile" + i));
custAddr.setPinCode(req.getParameter("pincode" + i));
custAddr.setState(state);
TransactionManager.txCreate(custAddr);
}
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	
	
	public AppDTO getCustomerDetails(AppDTO appDTO){
		
		Map controllerMap=appDTO.getControllerMap();
		
		Customer customer=(Customer) controllerMap.get("CUSTOMER");
		
		customer = (Customer) ((CustomerDao) getDAOBean(CUSTOMER_DAO))
		.findObjectByCondition(Customer.class, new String[] { "id" },
				new Object[] { customer.getId() });		
		
		Collection customerAddrColl = (Collection) ((CustomerDao) getDAOBean(CUSTOMER_DAO))
		.findListByCondition(
				CustomerAddress.class, 
				new String[] { "customer.id" },
				new Object[] { customer.getId() });	
		
		Collection customerIpColl = (Collection) ((CustomerDao) getDAOBean(CUSTOMER_DAO))
		.findListByCondition(
				CustomerIp.class, 
				new String[] { "customer.id" },
				new Object[] { customer.getId() });	
		
		
		
		controllerMap.put("CUSTOMER", customer);
		controllerMap.put("CUSTOMER_ADDRESS",customerAddrColl);
		controllerMap.put("CUSTOMER_IP",customerIpColl);

		appDTO.setControllerMap(controllerMap);
		
		return appDTO;
	}
	
	

	public AppDTO updateCustomer(AppDTO appDTO) {
		try {

			Map controllerMap = appDTO.getControllerMap();
			Customer customer = (Customer) controllerMap.get("CUSTOMER");
			customer.setDateModified(DateUtils.getCurrentDate());
			TransactionManager.begin();
			customerDao.updateCustomer(customer);
			
			if(customerDao.deleteCustomerAddress(customer))
			{
				if(addCustomerAddress(controllerMap))
				{	
					TransactionManager.commit();
					appDTO.setInfoMessage(getResourceMessage("CUSTOMER_UPDATED"));

					
				}
			}
			else
			{
				appDTO.setInfoMessage(getResourceMessage("UNEXPECTED_ERROR"));

				TransactionManager.rollback();
			}
			
		} catch (Exception e) {
			TransactionManager.rollback();
			e.printStackTrace();
		}

		return appDTO;

	}

	public AppDTO listCustomer(AppDTO appDTO) {

		appDTO = ((CustomerDao) getDAOBean(CUSTOMER_DAO)).listCustomer(appDTO);
		return appDTO;
	}

	public Customer getCustomerById(int id) {
		Customer customer = (Customer) ((CustomerDao) getDAOBean(CUSTOMER_DAO))
				.findObjectByCondition(Customer.class, new String[] { "id" },
						new Object[] { id });

		return customer;
	}

	public AppDTO deleteCustomer(AppDTO appDTO) {
		try {
			Map controllerMap = appDTO.getControllerMap();
			Customer customer = (Customer) controllerMap.get("CUSTOMER");
			customer=getCustomerById(customer.getId());
			if(((CustomerDao) getDAOBean(CUSTOMER_DAO)).deleteCustomer(customer))
			{
			appDTO.setInfoMessage(getResourceMessage("CUSTOMER_DELETED"));
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			}
			
		} catch (Exception e) {
			appDTO.setErrorMessage(getResourceMessage("UNEXPECTED_ERROR"));
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			return appDTO;
		}
		return appDTO;
	}
	
	
	
	
	
	public CustomerBodyMeasurement getCustomerBodyMeasurement(Customer customer)
	{
		
		
		CustomerBodyMeasurement customerBodyMeasurement = (CustomerBodyMeasurement) ((CustomerDao) getDAOBean(CUSTOMER_DAO))
		.findObjectByCondition(CustomerBodyMeasurement.class, 
				new String[] { "customer.id" },
				new Object[] { customer.getId() });		
		
		
		return customerBodyMeasurement;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.org.fashtag.service.CustomerService#addCustomerBodyMeasurement(com.org.fashtag.AppDTO)
	 * If no customer profile is created then create a customer profile with user's name and add the measurements to that.
	 * 
	 */
	
	public AppDTO addCustomerBodyMeasurement(AppDTO appDTO){
		
		try{
		Map controllerMap=appDTO.getControllerMap();
		HttpServletRequest req=(HttpServletRequest)controllerMap.get("REQUEST");
		int measurementCount=Integer.parseInt(req.getParameter("bodyMeasurementCount"));
		int customerId=Integer.parseInt(req.getParameter("customerId"));
		int customerProfileId=Integer.parseInt(req.getParameter("customerProfileId"));
		
		Customer customer=getCustomerById(customerId);
		CustomerProfile customerProfile=new CustomerProfile();
		if(customerProfileId>0)
		{
		customerProfile=customerDao.getCustomerProfileById(customerProfileId);		
		}
		else
		{
			customerProfile.setName(customer.getFullName());
			customerProfile.setCustomer(customer);
			customerProfile.setDateAdded(DateUtils.getCurrentDate());
			customerProfile.setDateModified(DateUtils.getCurrentDate());
			customerProfile=customerDao.createNewCustomerProfile(customerProfile);
		}
		
		
		Collection customerMeasurementCurrList= getCustomerBodyMeasurementsByProfile(customerProfile.getId());		
		TransactionManager.begin();
		if(customerMeasurementCurrList.size()>0)
		{
			Iterator iter=customerMeasurementCurrList.iterator();
			while(iter.hasNext())
			{
			CustomerBodyMeasurement custMeasurment=(CustomerBodyMeasurement) iter.next();
			customerDao.deleteCustomerBodyMeasurement(custMeasurment);

			}
			
			
		}
		
		
		for(int i=0;i<measurementCount;i++)
		{
			int attrId=Integer.parseInt(req.getParameter("measurement_attr"+i));
			int value=Integer.parseInt(req.getParameter("value"+i));
			String unit=req.getParameter("unit"+i);
			
			
			BodyMeasurementAttribute bodyMeasurementAttribute=(BodyMeasurementAttribute)
			((CustomerDao) getDAOBean(CUSTOMER_DAO)).
			findObjectByCondition
			(BodyMeasurementAttribute.class,new String[] 
			{"id"},
			new Object[] {attrId});
			
			CustomerBodyMeasurement customerBodyMeasurement=new CustomerBodyMeasurement();
			customerBodyMeasurement.setBodyMeasurementAttribute(bodyMeasurementAttribute);
			customerBodyMeasurement.setCustomerProfile(customerProfile);
			customerBodyMeasurement.setValue(value);
			customerBodyMeasurement.setUnit(unit);
			
			customerDao.addCustomerBodyMeasurement(customerBodyMeasurement);
			}
		
		
		

		TransactionManager.commit();
		controllerMap.put("CUSTOMER",customer);
		appDTO.setControllerMap(controllerMap);
		
		
		appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		appDTO.setInfoMessage(getResourceMessage("MEASUREMENT_ADDED"));
		}
		catch(Exception e){
			TransactionManager.rollback();
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage("UNEXPECTED_ERROR"));
		}
		finally{
			TransactionManager.closeSession();
		}
		return appDTO;
	}
	
	
public Collection getCustomerBodyMeasurementsByProfile(int customerProfileId)
{
	Collection measurementColl=Collections.EMPTY_LIST;
	measurementColl = (Collection) ((CustomerDao) getDAOBean(CUSTOMER_DAO))
	.findListByCondition(
			CustomerBodyMeasurement.class, 
			new String[] { "customerProfile.id" },
			new Object[] { customerProfileId });	
	
	return measurementColl;
}


public Collection getCustomerProfiles(Customer customer)
{
	Collection profileColl=Collections.EMPTY_LIST;
	profileColl = (Collection) ((CustomerDao) getDAOBean(CUSTOMER_DAO))
	.findListByCondition(
			CustomerProfile.class, 
			new String[] { "customer.id" },
			new Object[] { customer.getId() });	
	
	return profileColl;
	
}
	
	

	public Collection getAllStates() {
		Collection allStates = customerDao.getAllStates();
		return allStates;
	}

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}



	

}
