package com.org.fashtag.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.org.fashtag.model.AdminUser;
import com.org.fashtag.service.BaseService;


public interface AdminUserService extends BaseService {

	public AdminUser doAuthenticate(AdminUser adminUser);
	
	public Collection getLatestOrders();
	
}
