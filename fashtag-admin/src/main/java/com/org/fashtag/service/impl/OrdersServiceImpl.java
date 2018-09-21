package com.org.fashtag.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.context.AdminBeanConstants;
import com.org.fashtag.dao.CustomerDao;
import com.org.fashtag.dao.OrdersDao;
import com.org.fashtag.dao.ProductDao;
import com.org.fashtag.dao.SizeDao;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.OrderHistory;
import com.org.fashtag.model.Orders;
import com.org.fashtag.model.Size;
import com.org.fashtag.model.State;
import com.org.fashtag.service.OrdersService;
import com.org.fashtag.service.SizeService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.DateUtils;

public class OrdersServiceImpl extends BaseServiceImpl implements OrdersService,
		AdminBeanConstants {

	
	private OrdersDao ordersDao;

	
	public AppDTO listOrders(AppDTO appDTO)
	{
		
		appDTO=ordersDao.listOrders(appDTO);
		return appDTO;
	}
	
	
	public AppDTO getOrderById(AppDTO appDTO)
	{
		Map controllerMap=appDTO.getControllerMap();
		
		Orders orders=(Orders)controllerMap.get("ORDERS");
		int orderId=orders.getId();
		
		orders = (Orders) ((OrdersDao) getDAOBean(ORDERS_DAO))
		.findObjectByCondition(Orders.class,
				new String[] { "id" },
				new Object[] {orderId});
		
		controllerMap.put("ORDERS", orders);
		appDTO.setControllerMap(controllerMap);
		
		return appDTO;
	}
	
	public Collection getActiveOrdersStatus()
	{
		Collection orderStatusColl=ordersDao.getOrdersStatusByStatus(true);
		return orderStatusColl;
	}

	public AppDTO addOrderHistory(AppDTO appDTO)
	{
		try{
		Map dataMap=appDTO.getControllerMap();
		
		ordersDao.addOrderHistory(appDTO);
		
		OrderHistory orderHistory=(OrderHistory)dataMap.get("orderHistory"); 
		
		Orders orders=(Orders)ordersDao.findObjectByCondition(Orders.class,
				new String[] { "id" },
				new Object[] {orderHistory.getOrder().getId()});
		orders.setOrdersStatus(orderHistory.getOrdersStatus());
		ordersDao.update(orders);
		
		appDTO.setInfoMessage(
				getResourceMessage("ORDER_HISTORY_ADDED"));
		}
		catch(Exception e)
		{
			appDTO.setErrorMessage(
					getResourceMessage("UNEXPECTED_ERROR"));
		}
		return appDTO;
	}

/* getters and setters*/
	
	
	
	public OrdersDao getOrdersDao() {
		return ordersDao;
	}


	public void setOrdersDao(OrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}


	
	
	


}
