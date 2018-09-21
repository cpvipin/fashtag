package com.org.fashtag.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.org.fashtag.AppDTO;
import com.org.fashtag.model.AdminUser;
import com.org.fashtag.model.Vendor;
import com.org.fashtag.service.BaseService;


public interface ProductService extends BaseService {
	

	public AppDTO listAllProductsOfVendor(AppDTO appDTO);
	
	public Collection getAllActiveSize();
	
	public AppDTO addProduct(AppDTO appDTO);
	
	public Collection getChildCategoryList(String searchKey);
	
	public AppDTO getProductDetails(AppDTO appDTO);
	
	public AppDTO updateProduct(AppDTO appDTO);
	
	public AppDTO removeSavedProductImage(AppDTO appDTO);
	
	public AppDTO deleteProduct(AppDTO appDTO);
	
	public AppDTO productSoldOut(AppDTO appDTO);
	
	

	
}
