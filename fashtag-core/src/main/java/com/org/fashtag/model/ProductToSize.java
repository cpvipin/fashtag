package com.org.fashtag.model;

import com.org.fashtag.model.base.AbstractModel;


/**
 * @hibernate.class table="product_to_size"    

*
@hibernate.query name="ps.getProductSizesByProduct" query="select si.name,si.id,si.displayText from 
ProductToSize as pi inner join  pi.size as si where pi.product.id=:productId and si.activeStatus=true"                  
*
*
@hibernate.query name="prod.ProductToSize" query="from ProductToSize where product.id=:productId"                  

*/
public class ProductToSize extends AbstractModel {

	private Integer id;
	private Product product;
	private Size size;
	
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
	public Size getSize() {
		return size;
	}
	public void setSize(Size size) {
		this.size = size;
	}
	
	

}
