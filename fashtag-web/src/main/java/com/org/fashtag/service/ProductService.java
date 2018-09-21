package com.org.fashtag.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.model.Product;
import com.org.fashtag.service.BaseService;

public interface ProductService extends BaseService {
	
	public Collection getListProductBannerImages(int catId);
	
	public AppDTO getProductsByCategory(AppDTO appDTO);
	
	public AppDTO getProductDetails(AppDTO appDTO);
	
	public AppDTO getMatchingProducts(AppDTO appDTO);
	
	public AppDTO getSimilarProducts(AppDTO appDTO);
	
	public AppDTO isDeliveryAvailable(AppDTO appDTO);
	
	public AppDTO getProductDesigns(AppDTO appDTO);
	
	public AppDTO isCustomSizeAvail(AppDTO appDTO);
	
	public boolean isProductAvaialble(Product product);


}
