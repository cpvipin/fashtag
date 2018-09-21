package com.org.fashtag.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.context.VendorBeanConstants;
import com.org.fashtag.dao.OrdersDao;
import com.org.fashtag.dao.ProductDao;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.OrderHistory;
import com.org.fashtag.model.OrderProduct;
import com.org.fashtag.model.Orders;
import com.org.fashtag.model.Size;
import com.org.fashtag.model.State;
import com.org.fashtag.service.OrdersService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.DateUtils;

public class OrdersServiceImpl extends BaseServiceImpl implements OrdersService,
		VendorBeanConstants {

	
	private OrdersDao ordersDao;

	
	public AppDTO listOrders(AppDTO appDTO)
	{
		
		appDTO=ordersDao.listOrders(appDTO);
		return appDTO;
	}
	
	
	public AppDTO getOrderProductById(AppDTO appDTO)
	{
		Map controllerMap=appDTO.getControllerMap();
		
		OrderProduct orderProduct=(OrderProduct)controllerMap.get("ORDERPRODUCT");
		int orderProdId=orderProduct.getId();
		
		orderProduct = (OrderProduct) ordersDao
		.findObjectByCondition(OrderProduct.class,
				new String[] { "id" },
				new Object[] {orderProdId});
		
		controllerMap.put("ORDERPRODUCT", orderProduct);
		appDTO.setControllerMap(controllerMap);
		
		return appDTO;
	}
	
	public Collection getActiveOrdersStatus()
	{
		Collection orderStatusColl=ordersDao.getOrdersStatusByStatus(true);
		return orderStatusColl;
	}

	

/* getters and setters*/
	
	
	
	public OrdersDao getOrdersDao() {
		return ordersDao;
	}


	public void setOrdersDao(OrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}


	
	
	


}
