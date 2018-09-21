package com.org.fashtag.model;

import java.math.BigDecimal;
import java.util.Set;

import com.org.fashtag.model.base.AbstractModel;
import com.org.fashtag.model.base.BaseModel;


/**
 * @hibernate.query name = "orders.getLatestOrders" query = " 
 * from Orders as orders  order by orders.dateAdded desc 10"

 * @hibernate.query name = "orders.getOrdersByCustomer" query = " 
 * from Orders as orders where orders.customer.id=:custId  and orders.ordersStatus.name!=:filterStatusName  order by orders.dateAdded desc"



 * @author vipin
 *
 */

public class Orders extends AbstractModel{

	private Integer id;
	private Customer customer;
	private OrdersStatus ordersStatus;
	private String fullName;
	private String address;
	private String locality;
	private State state;
	private	String pincode;
	private String mobile;
	private BigDecimal totalAmount;	
	private Integer paymentType;
	private String paymentStatus;
	private String pgResponse;
	
	private Set orderProduct;
	private Set orderHistory;
	
	
	
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
	
	public OrdersStatus getOrdersStatus() {
		return ordersStatus;
	}
	public void setOrdersStatus(OrdersStatus ordersStatus) {
		this.ordersStatus = ordersStatus;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	
	
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPgResponse() {
		return pgResponse;
	}
	public void setPgResponse(String pgResponse) {
		this.pgResponse = pgResponse;
	}
	public Set getOrderProduct() {
		return orderProduct;
	}
	public void setOrderProduct(Set orderProduct) {
		this.orderProduct = orderProduct;
	}
	public Set getOrderHistory() {
		return orderHistory;
	}
	public void setOrderHistory(Set orderHistory) {
		this.orderHistory = orderHistory;
	}
	
	

}
