package com.org.fashtag.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.dao.CategoryDao;
import com.org.fashtag.dao.OrdersDao;
import com.org.fashtag.dao.ProductDao;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.Orders;
import com.org.fashtag.model.Product;
import com.org.fashtag.service.HomeService;
import com.org.fashtag.service.OrdersService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.CommonUtils;

public class OrdersServiceImpl extends BaseServiceImpl implements OrdersService {
	
	private OrdersDao ordersDao;
	
	public AppDTO trackOrder(AppDTO appDTO)
	{
		try{
		
			Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
			Map dataMap=appDTO.getControllerMap();
			int orderId=(Integer)dataMap.get("orderId");
			
			Orders order=ordersDao.getOrderByOrderId(orderId);
			
			if(order !=null && order.getCustomer().getId().intValue()==customer.getId().intValue())
			{
			Collection orderHistoryColl=ordersDao.getOrderHistoryByOrderId(orderId);
			dataMap.put("orderHisColl", orderHistoryColl);
			appDTO.setControllerMap(dataMap);
			}
			else
			{
				appDTO.setErrorMessage(getResourceMessage(TRACK_YOUR_ORDER_ONLY));
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				return appDTO;
			}
			
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return appDTO;
	}
	
	
	

	public OrdersDao getOrdersDao() {
		return ordersDao;
	}

	public void setOrdersDao(OrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}
}
