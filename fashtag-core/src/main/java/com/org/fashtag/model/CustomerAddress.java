package com.org.fashtag.model;

import java.util.Date;

import com.org.fashtag.model.base.BaseModel;


/* @hibernate.query name = "custaddr.deleteAddressByCustomer" 
 * query = "delete from CustomerAddress where customer.id=:customerId"
 * 
 * @hibernate.query name="custaddr.getAllCustomerAddressByCustomerId" query="from CustomerAddress as ca where  ca.customer.id=:custId"                  

*        
*/
public class CustomerAddress extends BaseModel {

	private Integer id;
	private Customer customer;
	private String fullName;
	private String pinCode;
	private String locality;
	private State state;
	private String address;
	private String mobile;
	private String email;
	private boolean isDefault;
	
	
	
	
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
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	
}
