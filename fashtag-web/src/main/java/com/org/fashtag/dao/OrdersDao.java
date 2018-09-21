package com.org.fashtag.dao;

import java.util.Collection;

import com.org.fashtag.AppDTO;
import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.model.OrderProduct;
import com.org.fashtag.model.Orders;
import com.org.fashtag.model.OrdersStatus;

public interface OrdersDao extends BaseDao {
	
	public OrdersStatus getOrderStatusByName(String name);
	
	public Collection getOrdersByCustomer(Customer customer);
	
	public Orders getOrderByOrderId(int orderId);
	
	public OrderProduct getOrderProductById(int id);
	
	public Collection getOrderHistoryByOrderId(int orderId);
	
	public void updatePaymentStatus(Orders orders);
	
	
	
}
