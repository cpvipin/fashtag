package com.org.fashtag.dao;

import java.util.Collection;

import com.org.fashtag.AppDTO;
import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.model.DesignAttributeSpecification;
import com.org.fashtag.model.Product;
import com.org.fashtag.model.ReturnStatus;
import com.org.fashtag.model.Returns;
import com.org.fashtag.model.Size;

public interface ProductDao extends BaseDao {
	
	public Collection getProductListByLayout(String layoutName);
	
	public boolean isProductExists(int productId);
	
	public AppDTO getProductListByCategory(AppDTO appDTO);
	
	public Product getProductById(int productId);
	
	public Collection getProductImages(int productId);
	
	public Collection getProductSizes(int productId);
	
	public Collection getMatchingProducts(int productCatId);
	
	public Collection getSimilarProducts(int productId);
	
	public boolean isPincodeExists(int pincode);
	
	public Collection getProductDesignAttrSpecificationCount(int productId);

	public Collection getProductDesigns(int productId);
	
	public Size getSizeById(int sizeId);
	
	public DesignAttributeSpecification getDesignAttributeSpecById(int id);
	
	public void addOrderReturn(Returns returns);
	
	public ReturnStatus getReturnStatusByName(String name);

	
}
