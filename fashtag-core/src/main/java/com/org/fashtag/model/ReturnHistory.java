package com.org.fashtag.model;

import java.math.BigDecimal;
import java.util.Date;

import com.org.fashtag.model.base.BaseModel;

public class ReturnHistory extends BaseModel {

	private Integer id;
	private Returns returns;
	private ReturnStatus returnStatus;
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
	public Returns getReturns() {
		return returns;
	}
	public void setReturns(Returns returns) {
		this.returns = returns;
	}
	public ReturnStatus getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(ReturnStatus returnStatus) {
		this.returnStatus = returnStatus;
	}
		
	
	
}
