package com.org.fashtag.dao.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.org.fashtag.AppDTO;
import com.org.fashtag.context.ApplicationContext;
import com.org.fashtag.context.BeanConstants;
import com.org.fashtag.context.TransactionManager;
import com.org.fashtag.dao.CartDao;
import com.org.fashtag.dao.CategoryDao;
import com.org.fashtag.dao.CustomerDao;
import com.org.fashtag.dao.OrdersDao;
import com.org.fashtag.dao.impl.BaseDaoImpl;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerBodyMeasurement;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.model.OrderHistory;
import com.org.fashtag.model.OrderProduct;
import com.org.fashtag.model.Orders;
import com.org.fashtag.model.OrdersStatus;
import com.org.fashtag.model.ReturnStatus;
import com.org.fashtag.util.QueryUtils;
import com.org.fashtag.util.UniqueResultHibCallback;

public class OrdersDaoImpl extends BaseDaoImpl implements OrdersDao {

	@Override
	public Collection getOrdersByCustomer(Customer customer) {

		Collection ordersColl=getHibernateTemplate().findByNamedQueryAndNamedParam(
				"orders.getOrdersByCustomer",
				new String[] { "custId","filterStatusName"},
				new Object[] { customer.getId(),"void"});
		
		return ordersColl;	
		
	}
	
	public Orders getOrderByOrderId(int orderId)
	{
		return getHibernateTemplate().get(Orders.class,orderId);
		
	}
	
	public OrderProduct getOrderProductById(int id)
	{
		return getHibernateTemplate().get(OrderProduct.class,id);
		
	}
	
	public OrdersStatus getOrderStatusByName(String name)
	{
		return (OrdersStatus)getHibernateTemplate().execute(
				new UniqueResultHibCallback(
						"orderStat.getOrderStatusByName", new String[] {
								"name"},
						new Object[] { name})); 
	}
	
	
	
	public Collection getOrderHistoryByOrderId(int orderId)
	{
	Collection orderHistoryColl= (Collection)this.findOptimizedListByCondition( 
			OrderHistory.class,
			new String[] { "ordersStatus.name","comment","dateAdded"},
			new String[] { "order.id" },
			new Object[] {orderId}, "dateAdded", true ,false);	
	
	return orderHistoryColl;
		
	}
	
	public void updatePaymentStatus(Orders orders)
	{
		getHibernateTemplate().update(orders);
	}
	
	

	
}
