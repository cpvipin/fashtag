package com.org.fashtag.dao;

import java.util.Collection;

import com.org.fashtag.AppDTO;
import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerBodyMeasurement;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.model.Size;

public interface CustomerDao extends BaseDao {
/* customer */
	
	public void addCustomer(Customer customer);

	
	public void updateCustomer(Customer customer);

	
	public AppDTO listCustomer(AppDTO appDTO);
	
	
	public boolean deleteCustomer(Customer customer);
	
	public boolean deleteCustomerAddress(Customer customer);
	
	public Collection getAllStates();
	
	public void deleteCustomerBodyMeasurement(CustomerBodyMeasurement customerBodyMeasurement);

	
	public void addCustomerBodyMeasurement(CustomerBodyMeasurement customerBodyMeasurement);
	
	public CustomerProfile getCustomerProfileById(int customerProfileId);
	
	public CustomerProfile createNewCustomerProfile(CustomerProfile customerProfile);
	
}
