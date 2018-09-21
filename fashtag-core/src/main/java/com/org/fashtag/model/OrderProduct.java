package com.org.fashtag.model;

import java.math.BigDecimal;

import com.org.fashtag.model.base.BaseModel;

public class OrderProduct extends BaseModel {

	private Integer id;
	private Orders order;
	private Product product;
	private Integer quantity;
	private BigDecimal unitPrice;
	private BigDecimal totalAmount;
	private Size size;
	private String designAttributeSpecification;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
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
	public Size getSize() {
		return size;
	}
	public void setSize(Size size) {
		this.size = size;
	}
	public String getDesignAttributeSpecification() {
		return designAttributeSpecification;
	}
	public void setDesignAttributeSpecification(
			String designAttributeSpecification) {
		this.designAttributeSpecification = designAttributeSpecification;
	}
	
	
	
}
