package com.org.fashtag.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.model.Customer;
import com.org.fashtag.service.BaseService;

public interface CustomerService extends BaseService {
	
	
	public AppDTO addWishList(AppDTO appDTO);
	
	public AppDTO getUserPreference(AppDTO appDTO);
	
	public AppDTO getCustomerMeasurementAttributes(AppDTO appDTO);
	
	public AppDTO addNewCustomerProfile(AppDTO appDTO);
	
	public AppDTO editCustomerProfile(AppDTO appDTO);
	
	public AppDTO updateCustomerProfile(AppDTO appDTO);
	
	public AppDTO deleteCustomerProfile(AppDTO appDTO);
	
	public AppDTO chooseDefaultProfile(AppDTO appDTO);
	
	public AppDTO addCustomerAddress(AppDTO appDTO);
	
	public AppDTO deleteCustomerAddress(AppDTO appDTO);
	
	public AppDTO updateCustomerAddress(AppDTO appDTO);

	public AppDTO updateUserInfo(AppDTO appDTO);
	
	
	public AppDTO returnOrderProducts(AppDTO appDTO);
	
	
	public AppDTO cancelOrder(AppDTO appDTO);
	
	
}
