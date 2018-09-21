package com.org.fashtag.model;

import java.util.Set;

import com.org.fashtag.model.base.AbstractModel;
import com.org.fashtag.model.base.BaseModel;
/**
 * @hibernate.class table="Vendor"    
 * 
@hibernate.query name="ve.getVendorsByName" query="select ve.name,ve.id from Vendor as ve.name like :name"                  
*
*/
public class Vendor extends AbstractModel {


	private Integer id;
	
	private String name;
	
	private String emailId;
	
	private String password;
	
	private String phone;
	
	private String address;

	private boolean activeStatus;

	
	/* getters and setters */
	
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	
	

}
