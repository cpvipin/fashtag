package com.org.fashtag.model;

import java.util.Date;

import com.org.fashtag.model.base.BaseModel;



public class CustomerCare extends BaseModel {

	private Integer id;
	private Customer customer;
	private String careType;
	private String commonField;
	private String comment;
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
	public String getCareType() {
		return careType;
	}
	public void setCareType(String careType) {
		this.careType = careType;
	}
	public String getCommonField() {
		return commonField;
	}
	public void setCommonField(String commonField) {
		this.commonField = commonField;
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
