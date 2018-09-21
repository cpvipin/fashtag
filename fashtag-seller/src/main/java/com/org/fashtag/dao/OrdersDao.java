package com.org.fashtag.dao;

import java.util.Collection;

import com.org.fashtag.AppDTO;
import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.model.Size;

public interface OrdersDao extends BaseDao {

	
	public AppDTO listOrders(AppDTO appDTO);
		
	public Collection getOrdersStatusByStatus(boolean status);
	
	
	public Collection getLatestOrders();
}
