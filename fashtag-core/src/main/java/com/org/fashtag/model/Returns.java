package com.org.fashtag.model;

import java.math.BigDecimal;
import java.util.Set;

import com.org.fashtag.model.base.AbstractModel;
import com.org.fashtag.model.base.BaseModel;


/*
 * 
 *  @hibernate.query name = "returns.getReturnsByCustomer" 
 *  query = "from Returns as returns where returns.customer.id=:custId order by returns.dateAdded desc"


/**
 * @author vipin
 *
 */

public class Returns extends AbstractModel{

	private Integer id;
	private Customer customer;
	private Orders orders;
	private ReturnStatus returnStatus;
	private BigDecimal totalAmount;	

	private Set returnProduct;
	private Set returnHistory;
	
	
	/* getters and setters */
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	public ReturnStatus getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(ReturnStatus returnStatus) {
		this.returnStatus = returnStatus;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Set getReturnProduct() {
		return returnProduct;
	}
	public void setReturnProduct(Set returnProduct) {
		this.returnProduct = returnProduct;
	}
	public Set getReturnHistory() {
		return returnHistory;
	}
	public void setReturnHistory(Set returnHistory) {
		this.returnHistory = returnHistory;
	}
	
	
	
	
	
	

}
