package com.org.fashtag.dao;

import java.util.Collection;

import com.org.fashtag.AppDTO;
import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerProfile;

public interface CartDao extends BaseDao {
	
	public void updateCustomerCart(String cart, int customerId);
	

}
