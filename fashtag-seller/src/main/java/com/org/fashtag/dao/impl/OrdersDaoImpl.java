package com.org.fashtag.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.org.fashtag.AppDTO;
import com.org.fashtag.beans.Page;
import com.org.fashtag.dao.OrdersDao;
import com.org.fashtag.dao.impl.BaseDaoImpl;
import com.org.fashtag.hibernate.PaginationCriteria;
import com.org.fashtag.model.OrderHistory;
import com.org.fashtag.model.OrderProduct;
import com.org.fashtag.model.Orders;
import com.org.fashtag.model.OrdersStatus;
import com.org.fashtag.model.Size;
import com.org.fashtag.model.Vendor;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.DateUtils;

public class OrdersDaoImpl extends BaseDaoImpl implements OrdersDao {



	public AppDTO listOrders(AppDTO appDTO) {

		Session session=getSession();
		try{
			Page ordersPage = (Page) appDTO.getControllerMap().get("PAGE");
			String sortField = ordersPage.getOrderBy();

			  /* Integer orderId=(Integer) appDTO.getControllerMap().get("orderId");*/
			   Integer orderStatus=(Integer)appDTO.getControllerMap().get("orderStatus");
			   Vendor vendor=(Vendor)appDTO.getControllerMap().get("vendor");
			   Date orderDate=DateUtils.getDate((String)appDTO.getControllerMap().get("orderDate"));
			   Date nextDayDate=null;
			   if(orderDate!=null)
			   {
				   nextDayDate=DateUtils.getFutureDate(orderDate,1);
			   }
			   
			PaginationCriteria pCriteria = getPaginationCriteria(session,OrderProduct.class);
			ArrayList aliasArr = new ArrayList();
			
			pCriteria.createAlias("order", "order");
			aliasArr.add("order");
			pCriteria.createAlias("product", "product"); 
			aliasArr.add("product");
			pCriteria.createAlias("product.vendor", "vendor"); 
			aliasArr.add("vendor");
			pCriteria.add(Expression.eq("vendor.id", vendor.getId()));
			
			 
			if (orderStatus != null && orderStatus.intValue() > 0) {
				pCriteria.createAlias("order.ordersStatus", "ordersStatus"); 
				pCriteria.add(Expression.eq("ordersStatus.id", orderStatus));
			}
			
			if(orderDate!=null && nextDayDate!=null)
			{
				pCriteria.add(Expression.between("order.dateAdded", orderDate,nextDayDate));
			}

			if (!CommonUtils.isEmpty(sortField)) {
				String[] tokens = sortField.split("-");
				String sortFld = tokens[tokens.length - 1];
				for (int i = 0; i < tokens.length - 1; i++) {
					if (!aliasArr.contains(tokens[i])) {
						if (i > 0) {
							pCriteria.createAlias(tokens[i - 1] + "."
									+ tokens[i], tokens[i]);
						} else {
							pCriteria.createAlias(tokens[i], tokens[i]);
						}
					}
					if (i == (tokens.length - 2)) {
						sortField = tokens[i] + "." + sortFld;
					}
				}
				if (ordersPage.getOrder() == PaginationCriteria.ORDER_ASC) {
					pCriteria.addOrder(Order.asc(sortField));
				} else {
					pCriteria.addOrder(Order.desc(sortField));
				}
			}
			ordersPage = pCriteria.createPage(ordersPage);
			appDTO.getControllerMap().put("PAGE", ordersPage);
			
			
		}
		catch(Exception e)
		{
		e.printStackTrace();	
		}
		return appDTO;
	}
	
	
	
	
	public Collection getOrdersStatusByStatus(boolean status)
	{
		Collection orderStatColl=getHibernateTemplate().findByNamedQueryAndNamedParam("ordersStatus.findAllOrdersStatusByStatus",
				"activeStatus",status );
		
	return orderStatColl;
	}
	
	
	public Collection getLatestOrders(){
		getHibernateTemplate().setMaxResults(10);
		Collection ordersColl=getHibernateTemplate().findByNamedQuery("orders.getLatestOrders");
		
		getHibernateTemplate().clear();
		
		return ordersColl;
	}
	
	
}
