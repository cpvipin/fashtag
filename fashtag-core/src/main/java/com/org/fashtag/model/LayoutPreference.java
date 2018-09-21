package com.org.fashtag.model;

import java.util.Date;

import com.org.fashtag.model.base.BaseModel;



public class LayoutPreference extends BaseModel {

	private Integer id;
	private Category category;
	private String layoutName;
	private int countLimit;
	private boolean activeStatus;
	
	
	/*getters and setters*/

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getLayoutName() {
		return layoutName;
	}
	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}
	public int getCountLimit() {
		return countLimit;
	}
	public void setCountLimit(int countLimit) {
		this.countLimit = countLimit;
	}
	public boolean isActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
	

	


	
		
	
}
