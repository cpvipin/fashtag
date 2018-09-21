package com.org.fashtag.service;

import com.org.fashtag.AppDTO;
import com.org.fashtag.model.Vendor;
import com.org.fashtag.service.BaseService;

public interface VendorService  extends BaseService
{
	
	public AppDTO addVendor(AppDTO appDTO);
	
	public AppDTO listAllVendors(AppDTO appDTO);
	
	public Vendor getVendorById(Vendor vendor);
	
	public AppDTO updateVendor(AppDTO appDTO);
	
	public AppDTO deleteVendor(AppDTO appDTO);


	
	
}
