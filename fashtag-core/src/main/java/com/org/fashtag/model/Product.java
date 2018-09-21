package com.org.fashtag.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.org.fashtag.model.base.AbstractModel;

/**
* @hibernate.class table="product"    
* 
@hibernate.query name="product.isProductExists" 
query="select  count(prod.id) from product prod where prod.id:=prodId"                 

 @hibernate.query name="prod.fetchSimilarPorducts" query="from Product as prod where prod.category.id=:prodCatId and  prod.id!=:prodId"                  

@hibernate.query name="prod.getProductDesigns" query="select das.name,das.id,das.designAttribute,das.image 
from ProductToDesignAttributeSpecification as pdas join pdas.designAttributeSpecification as das  
join das.designAttribute as da where pdas.product.id=:productId order by da.id"                  


*/


public class Product extends AbstractModel {

	private Integer id;
	private Category category;
	private Vendor vendor;
	private boolean isFeatured;
	private int gender;
	private String name;
	private Integer quantity;
	private String defaultImage;	
	private BigDecimal actualPrice;
	private BigDecimal offerPrice;
	private Date dateAvailable;
	private boolean activeStatus;
	private String description;
	private String actualColor;
	private Integer totalViews;
	private int totalLikes;
	
	private Set productToSize;
	private Set productImages;
	private Set productToDesignAttributeSpecification;
	


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
	
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
	public boolean getIsFeatured() {
		return isFeatured;
	}
	public void setIsFeatured(boolean isFeatured) {
		this.isFeatured = isFeatured;
	}
	
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getDefaultImage() {
		return defaultImage;
	}
	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}
	public BigDecimal getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}
	public BigDecimal getOfferPrice() {
		return offerPrice;
	}
	public void setOfferPrice(BigDecimal offerPrice) {
		this.offerPrice = offerPrice;
	}
	public Date getDateAvailable() {
		return dateAvailable;
	}
	public void setDateAvailable(Date dateAvailable) {
		this.dateAvailable = dateAvailable;
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
	public String getActualColor() {
		return actualColor;
	}
	public void setActualColor(String actualColor) {
		this.actualColor = actualColor;
	}
	public Integer getTotalViews() {
		return totalViews;
	}
	public void setTotalViews(Integer totalViews) {
		this.totalViews = totalViews;
	}
	public int getTotalLikes() {
		return totalLikes;
	}
	public void setTotalLikes(int totalLikes) {
		this.totalLikes = totalLikes;
	}
	public Set getProductToSize() {
		return productToSize;
	}
	public void setProductToSize(Set productToSize) {
		this.productToSize = productToSize;
	}
	public Set getProductImages() {
		return productImages;
	}
	public void setProductImages(Set productImages) {
		this.productImages = productImages;
	}
	public Set getProductToDesignAttributeSpecification() {
		return productToDesignAttributeSpecification;
	}
	public void setProductToDesignAttributeSpecification(
			Set productToDesignAttributeSpecification) {
		this.productToDesignAttributeSpecification = productToDesignAttributeSpecification;
	}
	
	
}
