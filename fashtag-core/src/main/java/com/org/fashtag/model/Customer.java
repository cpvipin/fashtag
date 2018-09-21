package com.org.fashtag.model;

import java.util.Date;

import com.org.fashtag.model.base.AbstractModel;


/**
* @hibernate.class table="customer"    
* 
@hibernate.query name="cust.authenticateByUserIdPassword" 
query="from Customer as cust  where (cust.EmailId=:emailId or cust.phone=:phone) and password=:password 
"                  
*
*@hibernate.query name="cust.getCustomerByUserId" 
query="from Customer as cust  where (cust.EmailId=:emailId or cust.phone=:phone)"


*@hibernate.query name="cust.getCustomerWishList" 
query="select wishList from Customer as cust  where cust.id=:custId "update Customer set cart=:cart where id=:custId
*/
public class Customer extends AbstractModel {

	private Integer id;
	private String fullName;
	private int gender;
	private String email;	
	private String phone;	
	private String password;	
	private boolean activeStatus;
	private boolean newsLetter	;
	private boolean isAdminAdded;
	private boolean isFirstLogin;
	private String wishList;
	private String cart;
	
	/*getters and setters*/
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
	public boolean isNewsLetter() {
		return newsLetter;
	}
	public void setNewsLetter(boolean newsLetter) {
		this.newsLetter = newsLetter;
	}
	public boolean getIsAdminAdded() {
		return isAdminAdded;
	}
	public void setIsAdminAdded(boolean isAdminAdded) {
		this.isAdminAdded = isAdminAdded;
	}
	
	public boolean getIsFirstLogin() {
		return isFirstLogin;
	}
	public void setIsFirstLogin(boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}
	public String getWishList() {
		return wishList;
	}
	public void setWishList(String wishList) {
		this.wishList = wishList;
	}
	public String getCart() {
		return cart;
	}
	public void setCart(String cart) {
		this.cart = cart;
	}
	
}
