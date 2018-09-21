package com.org.fashtag.model;

import java.util.Date;

import com.org.fashtag.model.base.BaseModel;



/**
 * @hibernate.class table="category"    
 * 
@hibernate.query name="custmeas.deleteCustomerMeasurementsByProfile" query="delete from CustomerBodyMeasurement where customerProfile.id=:profileId"                  
*/
public class CustomerBodyMeasurement extends BaseModel {

	private Integer id;
	private CustomerProfile customerProfile;
	private BodyMeasurementAttribute bodyMeasurementAttribute;
	private int value;
	private String unit;
	
	
	
	/*getters and setters*/

	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public CustomerProfile getCustomerProfile() {
		return customerProfile;
	}
	public void setCustomerProfile(CustomerProfile customerProfile) {
		this.customerProfile = customerProfile;
	}
	public BodyMeasurementAttribute getBodyMeasurementAttribute() {
		return bodyMeasurementAttribute;
	}
	public void setBodyMeasurementAttribute(
			BodyMeasurementAttribute bodyMeasurementAttribute) {
		this.bodyMeasurementAttribute = bodyMeasurementAttribute;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	

	}
