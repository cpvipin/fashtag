package com.org.fashtag.model;

import java.util.Date;

import com.org.fashtag.model.base.BaseModel;



public class CustomerIp extends BaseModel {

	private Integer id;
	private Customer customer;
	private String ipAddress;
	private Date dateAdded;
	
	/*getters and setters*/

	
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
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	


	
		
	
}
