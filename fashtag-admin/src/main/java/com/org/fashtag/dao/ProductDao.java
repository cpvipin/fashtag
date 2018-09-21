package com.org.fashtag.dao;

import java.util.Collection;

import com.org.fashtag.AppDTO;
import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.DesignAttribute;
import com.org.fashtag.model.DesignAttributeSpecification;
import com.org.fashtag.model.Product;
import com.org.fashtag.model.Size;

public interface ProductDao extends BaseDao {
	
	
	
	
	/* products */
	public void addProduct(Product product);
	
	public void deleteProductImages(Product product);
	
	public void deleteProductSizes(Product product);
	
	

	public void deleteProductToDesignAttributeSpec(Product product);
	
	
	public AppDTO listAllProduct(AppDTO appDTO);

	public Collection getproductDesignAttributes(int productId);

	
}
