package com.org.fashtag.model;

import java.util.Date;
import java.util.Set;

import com.org.fashtag.model.base.BaseModel;
/**
* @hibernate.class table="body_measurement_attribute"    
*/


public class BodyMeasurementAttribute extends BaseModel {

	private Integer id;
	private String name;
	private int gender;
	private boolean isRequired;
	private boolean activeStatus;
	private String description;
	
	private Set customerBodyMeasurements;
	
	
	
	/*getters and setters*/

	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public boolean getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	public boolean getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	



	
		
	
}
