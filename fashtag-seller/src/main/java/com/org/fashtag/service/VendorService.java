package com.org.fashtag.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.org.fashtag.model.AdminUser;
import com.org.fashtag.model.Vendor;
import com.org.fashtag.service.BaseService;


public interface VendorService extends BaseService {

	public Vendor doAuthenticate(Vendor vendor);
	
	/*public Collection getLatestOrders();*/
	
}
