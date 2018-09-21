package com.org.fashtag.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.service.BaseService;

public interface CartService extends BaseService {
	
	
	public AppDTO addProductToCart(AppDTO appDTO);
	
	public void mergeSessionAndUserCart(String userCart);
		
	public String getSessionCart();
	
	public AppDTO getCartProducts(AppDTO appDTO);
	
	public JSONObject getSelCartProduct(int productId);
	
	public AppDTO getCartProdDesigns(AppDTO appDTO);
	
	public AppDTO removeProductFromCart(AppDTO appDTO);
	
	public AppDTO getAllDeliveryAddress(AppDTO appDTO);
	
	public AppDTO confirmDeliveryAddress(AppDTO appDTO);

}
