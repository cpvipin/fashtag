package com.org.fashtag.model;

import java.math.BigDecimal;

import com.org.fashtag.model.base.BaseModel;

public class ReturnProduct extends BaseModel {

	private Integer id;
	private Returns returns;
	private Product product;
	private Integer quantity;
	private BigDecimal unitPrice;
	private BigDecimal totalAmount;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Returns getReturns() {
		return returns;
	}
	public void setReturns(Returns returns) {
		this.returns = returns;
	}
	
	
	
	
}
