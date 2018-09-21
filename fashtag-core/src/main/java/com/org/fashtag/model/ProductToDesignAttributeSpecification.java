package com.org.fashtag.model;

import com.org.fashtag.model.base.BaseModel;


/**
* @hibernate.class table="product_to_design_attribute_specification"    
* 
@hibernate.query name="ptd.productToDesignAttributeSpecCount" 

query="select count(ptd.id)  from ProductToDesignAttributeSpecification as ptd where ptd.product.id=:productId "                 

*/

public class ProductToDesignAttributeSpecification extends BaseModel {

	private Integer id;
	private Product product;
	private DesignAttributeSpecification  designAttributeSpecification;
	private boolean isRecommended;
	
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
	public DesignAttributeSpecification getDesignAttributeSpecification() {
		return designAttributeSpecification;
	}
	public void setDesignAttributeSpecification(
			DesignAttributeSpecification designAttributeSpecification) {
		this.designAttributeSpecification = designAttributeSpecification;
	}
	public boolean getIsRecommended() {
		return isRecommended;
	}
	public void setIsRecommended(boolean isRecommended) {
		this.isRecommended = isRecommended;
	}
	
	
	
	

}
