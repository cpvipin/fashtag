package com.org.fashtag.model;

import com.org.fashtag.model.base.AbstractModel;
public class State extends AbstractModel {

	private Integer id;
	private int countryId;
	private String  code;
	private String name;
	private boolean activeStatus;
	
	
	
	
	/*getters and setters*/
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

}
