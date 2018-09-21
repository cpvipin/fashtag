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
	
	public void addProduct(Product product);
	
	public void deleteProductImages(Product product);
	
	public void deleteProductSizes(Product product);
	
	public Collection getChildCategoryList(String likeKey);

	public AppDTO listAllProductsOfVendor(AppDTO appDTO);
	
	public void deleteSavedProductImages(int productId,int imageId);
	
	public Collection getproductDesignAttributes(int productId);
	
	public void deleteProductToDesignAttributeSpec(Product product);
}
