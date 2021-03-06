package com.org.fashtag.service;

import java.util.Collection;

import com.org.fashtag.AppDTO;
import com.org.fashtag.model.Size;
import com.org.fashtag.service.BaseService;

public interface OrdersService extends BaseService {
	
	public AppDTO listOrders(AppDTO appDTO);
	
	public AppDTO getOrderById(AppDTO appDTO);
	
	public Collection getActiveOrdersStatus();
	
	public AppDTO addOrderHistory(AppDTO appDTO);
}
