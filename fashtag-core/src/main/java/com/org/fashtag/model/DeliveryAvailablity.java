package com.org.fashtag.model;

import java.util.Set;

import com.org.fashtag.model.base.AbstractModel;
import com.org.fashtag.model.base.BaseModel;

public class DeliveryAvailablity extends BaseModel {

	private Integer id;
	private int pincode;
	private boolean activeStatus;
		
	
	/*getters and setters*/
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public int getPincode() {
		return pincode;
	}
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	
	public boolean getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

}
