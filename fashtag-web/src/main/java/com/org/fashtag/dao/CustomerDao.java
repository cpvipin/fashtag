package com.org.fashtag.dao;

import java.util.Collection;

import com.org.fashtag.AppDTO;
import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerAddress;
import com.org.fashtag.model.CustomerCare;
import com.org.fashtag.model.CustomerProfile;

public interface CustomerDao extends BaseDao {
	
	public AppDTO customerSignIn(AppDTO appDTO);

	public void createCustomer(Customer customer);
	
	public void updateCustomer(Customer customer);

	public Customer getCustomerById(int customerId);
	
	public Customer getCustomerByUserId(String userId);

	public AppDTO getRequiredCustomerSizeAttributesByGender(AppDTO appDTO);
	
	public void createCustomerBodyMeasurement(AppDTO appDTO);
	
	public BodyMeasurementAttribute getBodyBodyMeasurementAttributeBbyId(int Id);
	
	public CustomerProfile getOnlyProfileOfCustomer(Customer customer);
	
	public void deleteCustomerMeasurementsByProfile(int profileId);
	
	public void createCustomeProfile(CustomerProfile customerProfile);
	
	public CustomerProfile getCustomerProfileByProfileId(int profileId);
	
	public void updateCustomerProfile(CustomerProfile customerProfile);
	
	public String getWishListOfCustomer(Customer customer);
	
	public Collection getAllCustomerProfiles(Customer customer);

	public Collection getAllBodyMeasurementAttributesByGender(int gender);
	
	public Collection getCustomerMeasurementByProfile(CustomerProfile customerProfile);

	public void deleteCustomerProfile(CustomerProfile custProfile);
	
	public CustomerProfile getDefaultProfile(Customer customer);
	
	public Collection getAllCustomerAddress(Customer customer);
	
	public CustomerAddress getCustomerAddressById(int addressId);
	
	public Collection getReturnsByCustomer(Customer customer);
	
	public void addCustomerCare(CustomerCare custCare);
	
	public int getCountOfZeroValuedRequiredMeasurement(CustomerProfile custProfile);
	



}
