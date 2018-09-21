package com.org.fashtag.service;

import java.util.Collection;

import com.org.fashtag.AppDTO;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerBodyMeasurement;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.model.Size;
import com.org.fashtag.service.BaseService;

public interface CustomerService extends BaseService {
	
/* size */
	
	public AppDTO addCustomer(AppDTO appDTO);
	
	public AppDTO updateCustomer(AppDTO appDTO);
	
	public AppDTO listCustomer(AppDTO appDTO);
	
	public AppDTO deleteCustomer(AppDTO appDTO) ;

	public Customer getCustomerById(int id);
	
	public Collection getAllStates();
	
	public AppDTO getCustomerDetails(AppDTO appDTO);
	
	public CustomerBodyMeasurement getCustomerBodyMeasurement(Customer customer);
	
	public AppDTO addCustomerBodyMeasurement(AppDTO appDTO);
	
	
	public Collection getCustomerProfiles(Customer customer);
	
	public Collection getCustomerBodyMeasurementsByProfile(int customerProfileId);
}
