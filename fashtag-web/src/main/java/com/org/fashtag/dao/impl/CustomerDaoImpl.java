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
import com.org.fashtag.context.TransactionManager;
import com.org.fashtag.dao.CategoryDao;
import com.org.fashtag.dao.CustomerDao;
import com.org.fashtag.dao.impl.BaseDaoImpl;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerAddress;
import com.org.fashtag.model.CustomerBodyMeasurement;
import com.org.fashtag.model.CustomerCare;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.model.Product;
import com.org.fashtag.util.QueryUtils;
import com.org.fashtag.util.UniqueResultHibCallback;

public class CustomerDaoImpl extends BaseDaoImpl implements CustomerDao {
	
	public AppDTO customerSignIn(AppDTO appDTO)
	{
		Map dataMap=appDTO.getControllerMap();
		String userId=(String) dataMap.get("USERID");
		String password=(String) dataMap.get("PASSWORD");
		
		Customer customer=(Customer)getHibernateTemplate().execute(
				new UniqueResultHibCallback(
						"cust.authenticateByUserIdPassword", new String[] {
								"userId", "password" },
						new Object[] { userId, password }));
		
			dataMap.put("CUSTOMER", customer);
			appDTO.setControllerMap(dataMap);
			
			return appDTO;
	
	}
	
	
	public void createCustomer(Customer customer)
	{
		getHibernateTemplate().save(customer);
	}
	
	public void updateCustomer(Customer customer)
	{
		getHibernateTemplate().update(customer);
		
	}
	
	public Customer getCustomerById(int customerId)
	{
		
		Customer customer =getHibernateTemplate().get(Customer.class, customerId);
		return customer;
		
	}
	
	
	/*
	 * 
	 * User id is either email or mobile
	 */
	public Customer getCustomerByUserId(String userId)
	{
		Customer customer=(Customer)getHibernateTemplate().execute(
				new UniqueResultHibCallback(
						"cust.getCustomerByUserId", new String[] {
								"userId"},
						new Object[] { userId }));
		
		return customer;
	}


	
	public AppDTO getRequiredCustomerSizeAttributesByGender(AppDTO appDTO) {
		
		Map dataMap=appDTO.getControllerMap();
		try{
		Customer customer=(Customer) dataMap.get("CUSTOMER");
		CustomerProfile customerProfile=(CustomerProfile) dataMap.get("CUSTOMER_PROFILE");
		int profileId=0;
		
		if(customerProfile!=null)
		{
			profileId=customerProfile.getId();
		}
		
		String sqlQuery="select {bma.*},{cbm.*}"; 
		sqlQuery+=" from body_measurement_attribute bma ";
		
		sqlQuery+=" left join  customer_body_measurement as cbm  on bma.id=cbm.measurement_attribute_id " +
				" and  cbm.customer_profile_id="+profileId;
		 
		sqlQuery+=" where bma.is_required=1 and bma.gender="+customer.getGender()+" and bma.active_status=1 ";

		
		Session session = getSession();
		
		SQLQuery query=session.createSQLQuery(sqlQuery);
		query.addEntity("cbm",CustomerBodyMeasurement.class);
		query.addEntity("bma",BodyMeasurementAttribute.class);
		Collection customerSizeAttributes=query.list();
		
		dataMap.put("SIZEATTRIBUTES", customerSizeAttributes);
		appDTO.setControllerMap(dataMap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return appDTO;
	}
	
	public BodyMeasurementAttribute getBodyBodyMeasurementAttributeBbyId(int measureId)
	{
		return (BodyMeasurementAttribute)getHibernateTemplate().get(
				BodyMeasurementAttribute.class , 
				measureId);
	}
	
	
	public Collection getAllBodyMeasurementAttributesByGender(int gender)
	{
		String sqlQuery="select {bma.*}"; 
		sqlQuery+=" from body_measurement_attribute bma ";
		sqlQuery+=" where bma.gender="+gender+" and bma.active_status=1 ";
		Session session = getSession();
		SQLQuery query=session.createSQLQuery(sqlQuery);
		query.addEntity("bma",BodyMeasurementAttribute.class);
		Collection measurementAttributeColl=query.list();
		
		
		return measurementAttributeColl;
		
		
	}
	
	
	public Collection getCustomerMeasurementByProfile(CustomerProfile customerProfile)
	{
		Collection customerProfiles=Collections.EMPTY_LIST;
		String sqlQuery="select {bma.*},{cbm.*}"; 
		sqlQuery+=" from body_measurement_attribute bma ";
		sqlQuery+=" left join  customer_body_measurement as cbm  on bma.id=cbm.measurement_attribute_id " +
				" and  cbm.customer_profile_id="+customerProfile.getId();
		sqlQuery+=" where bma.gender="+customerProfile.getGender()+" and bma.active_status=1 ";

		
		Session session = getSession();
		SQLQuery query=session.createSQLQuery(sqlQuery);
		query.addEntity("cbm",CustomerBodyMeasurement.class);
		query.addEntity("bma",BodyMeasurementAttribute.class);
		customerProfiles=query.list();
		
		 return customerProfiles;	
	}
	
	/*
	 * This method will return number of required measuremnt attributes still to be entered
	 * 
	 */
	public int getCountOfZeroValuedRequiredMeasurement(CustomerProfile custProfile)
	{
		int count=0;

		String sql="select count(bma.id) as count from body_measurement_attribute bma where  bma.gender="+custProfile.getGender()+"  and " +
				"bma.is_required=1 and bma.active_status=1 "+
				" and bma.id  not in "+
				" ( select cbm.measurement_attribute_id from customer_body_measurement cbm where customer_profile_id="+custProfile.getId()+" and value>0 )";

		Session session =getSession();
		SQLQuery sqlQuery=session.createSQLQuery(sql).addScalar("count", Hibernate.INTEGER);
		Collection resultSet=sqlQuery.list();
		count=(Integer)resultSet.iterator().next();
		
		return count;
		
		
	}


	
	public void createCustomerBodyMeasurement(AppDTO appDTO) {
	
		Map dataMap=appDTO.getControllerMap();
		CustomerBodyMeasurement customerBodyMeasurement=(CustomerBodyMeasurement)dataMap.get("CUSTOMER_BODY_MEASUREMENT");
		TransactionManager.getInstance().txCreate(customerBodyMeasurement);
	
	
	}


	@Override
	public CustomerProfile getOnlyProfileOfCustomer(Customer customer) {
	
		CustomerProfile customerProfile=(CustomerProfile)getHibernateTemplate().execute(
				new UniqueResultHibCallback(
						"custprof.getProfileByCustomerId", new String[] {
								"custId"},
						new Object[] { customer.getId() }));
		
		return customerProfile;
	
	}
	
	public Collection getAllCustomerProfiles(Customer customer)
	{
		Collection profileColl=getHibernateTemplate().findByNamedQueryAndNamedParam(
				"custprof.getAllCustomerProfileByCustomerId",
				new String[] { "custId"},
				new Object[] { customer.getId()});
		
		return profileColl;	
		
	}
	
	
	public Collection getAllCustomerAddress(Customer customer)
	{

		Collection addressColl=	getHibernateTemplate().findByNamedQueryAndNamedParam(
					"custaddr.getAllCustomerAddressByCustomerId",
					new String[] { "custId" },
					new Object[] { customer.getId()});
		
		return addressColl;	
		
	}


	@Override
	public void deleteCustomerMeasurementsByProfile(int profileId) {
		

		String deleteQuery ="delete from CustomerBodyMeasurement where customerProfile.id="+profileId;
	
		TransactionManager.getInstance().getTxSession().createQuery(deleteQuery).executeUpdate();
	}
	
	public CustomerProfile getCustomerProfileByProfileId(int profileId)
	{
	CustomerProfile customeProfile=getHibernateTemplate().get(CustomerProfile.class, profileId);	
	return customeProfile;
		
	}
	
	
	public void createCustomeProfile(CustomerProfile customerProfile)
	{
		TransactionManager.getInstance().txCreate(customerProfile);
	}
	
	public void updateCustomerProfile(CustomerProfile customerProfile)
	{
		TransactionManager.getInstance().txUpdate(customerProfile);
	}

	
	
	
	
	
	public String getWishListOfCustomer(Customer customer)
	{
		String wishList="";
		
		wishList=(String)getHibernateTemplate()
		.execute(
			new UniqueResultHibCallback(
				"cust.getCustomerWishList",
				new String[] { "custId" },
				new Object[] { customer.getId() }));
		
		return wishList;
	}
	
	
	public void deleteCustomerProfile(CustomerProfile custProfile)
	{

		TransactionManager.getInstance().txDelete(custProfile);
	
	}
	
	
	
	public CustomerProfile getDefaultProfile(Customer customer)
	{
		
		CustomerProfile custProfile=(CustomerProfile)
		getHibernateTemplate()
		.execute(
			new UniqueResultHibCallback(
				"custprof.getDefaultProfile",
				new String[] { "custId","isDefault" },
				new Object[] { customer.getId(),true }));
		
		return custProfile;
		
	}
	
	
	public CustomerAddress getCustomerAddressById(int addressId)
	{
		CustomerAddress customerAddress=getHibernateTemplate().get(CustomerAddress.class, addressId);	
	return customerAddress;
		
	}
	
	@Override
	public Collection getReturnsByCustomer(Customer customer) {

		Collection ordersColl=getHibernateTemplate().findByNamedQueryAndNamedParam(
				"returns.getReturnsByCustomer",
				new String[] { "custId"},
				new Object[] { customer.getId()});
		
		return ordersColl;	
		
	}
	
	public void addCustomerCare(CustomerCare custCare)
	{
		getHibernateTemplate().save(custCare);
	}

	
}
