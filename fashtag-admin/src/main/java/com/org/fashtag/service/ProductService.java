package com.org.fashtag.service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.org.fashtag.AppDTO;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.DesignAttribute;
import com.org.fashtag.model.DesignAttributeSpecification;
import com.org.fashtag.model.Size;
import com.org.fashtag.service.BaseService;

public interface ProductService  extends BaseService
{
	
	/* product */
	
	public Collection getChildCategoryList(String searchKey);
	
	public Collection getVendorsByName(String searchKey);
	
	public AppDTO addProduct(AppDTO appDTO);
	
	public AppDTO listAllProducts(AppDTO appDTO);

	public AppDTO getProductDetails(AppDTO appDTO);

	public AppDTO updateProduct(AppDTO appDTO);
	
	public AppDTO deleteProduct(AppDTO appDTO);

}
