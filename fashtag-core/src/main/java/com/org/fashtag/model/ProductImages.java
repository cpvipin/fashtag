package com.org.fashtag.model;

import com.org.fashtag.model.base.BaseModel;


/**
 * @hibernate.class table="category"    
@hibernate.query name="pi.getProductImagesByProduct" query="select pi.image from ProductImages as pi on pi.product.id=:productId"                  

*
*/

public class ProductImages extends BaseModel {

	private Integer id;
	private Product product;
	private String image;
	private int sortOrder;

	/* getters and setters */
	
	
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	
	

}
