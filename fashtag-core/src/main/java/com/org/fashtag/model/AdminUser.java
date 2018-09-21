package com.org.fashtag.model;

import java.util.Date;
import java.util.Set;

import com.org.fashtag.model.base.BaseModel;

public class AdminUser extends BaseModel {

	private Integer id;
	private String username;
	private String password;
	private boolean activeStatus;
	private Date lastLogin;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	
	
	public boolean getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

}
