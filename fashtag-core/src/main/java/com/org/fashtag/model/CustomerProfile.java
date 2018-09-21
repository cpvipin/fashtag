package com.org.fashtag.model;

import java.util.Date;
import java.util.Set;

import com.org.fashtag.model.base.AbstractModel;
import com.org.fashtag.model.base.BaseModel;



/**
 * @hibernate.class table="CustomerProfile"    
 * 
*
@hibernate.query name="custprof.getProfileByCustomerId" query="from CustomerProfile as cp where  cp.customer.id=:custId"                  

@hibernate.query name="custprof.getAllCustomerProfileByCustomerId" query="from CustomerProfile as cp where  cp.customer.id=:custId"                  

@hibernate.query name="custprof.getDefaultProfile" query="from CustomerProfile as cp where  cp.customer.id=:custId and cp.isDefault=true"                  



*
*/
public class CustomerProfile extends AbstractModel {

	private Integer id;
	private Customer customer;
	private int gender;
	private String name;
	private boolean isDefault;
	
	private Set customerMeasurements;
	
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	
	public Set getCustomerMeasurements() {
		return customerMeasurements;
	}
	public void setCustomerMeasurements(Set customerMeasurements) {
		this.customerMeasurements = customerMeasurements;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	
}
