package com.org.fashtag.model;

import java.math.BigDecimal;
import java.util.Date;

import com.org.fashtag.model.base.BaseModel;

public class OrderHistory extends BaseModel {

	private Integer id;
	private Orders order;
	private OrdersStatus ordersStatus;
	private boolean notify;
	private String comment;
	private Date dateAdded;
	
	/* getters and setters */
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	public OrdersStatus getOrdersStatus() {
		return ordersStatus;
	}
	public void setOrdersStatus(OrdersStatus ordersStatus) {
		this.ordersStatus = ordersStatus;
	}
	public boolean isNotify() {
		return notify;
	}
	public void setNotify(boolean notify) {
		this.notify = notify;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
		
	
	
}
