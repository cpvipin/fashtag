package com.org.fashtag.controller;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.controller.BaseController;
import com.org.fashtag.service.CartService;
import com.org.fashtag.service.ProductService;

@Controller
public class CartController extends BaseController {
	
	@Autowired
	private CartService cartService;

	@RequestMapping(value="/addToCart", method = RequestMethod.POST)
	public @ResponseBody String addToCart(HttpServletRequest request)
	{
		JSONObject respObj=new JSONObject();
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		
		try{

			int productId=Integer.parseInt(request.getParameter("productId"));
			int sizeId=Integer.parseInt(request.getParameter("sizeId"));
			String custDesign=request.getParameter("custDesign");
			int quantity=Integer.parseInt(request.getParameter("quantity"));
	
			dataMap.put("sizeId",sizeId);
			dataMap.put("productId",productId);
			dataMap.put("custDesign",custDesign);
			dataMap.put("quantity",quantity);
			
			appDTO.setControllerMap(dataMap);
			appDTO=cartService.addProductToCart(appDTO);
			
			dataMap=appDTO.getControllerMap();
			
			

			if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
			{
				respObj.put("STATUS", "ERROR");
				respObj.put("MESSAGE",appDTO.getErrorMessage());
			}
			else
			{
				int productsCount=(Integer)dataMap.get("product_count");
				BigDecimal totalPrice=(BigDecimal) dataMap.get("total_price");
				respObj.put("COUNT", productsCount);
				respObj.put("TOTAL_PRICE",totalPrice);
				respObj.put("MESSAGE",appDTO.getInfoMessage());
				respObj.put("STATUS", "SUCCESS");
			}
		
		}
		catch(Exception e)
		{
		e.printStackTrace();	
		}
		
		return respObj.toString();
		
	}
	
	
	
	@RequestMapping(value="/cart")
	public ModelAndView showCart(HttpServletRequest request)
	{
		ModelAndView mv =new ModelAndView("cart");
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		appDTO.setControllerMap(dataMap);
		appDTO=cartService.getCartProducts(appDTO);
		dataMap=appDTO.getControllerMap();
		mv.addAllObjects(dataMap);
		return mv;
		
	}
	
	
	
	
	@RequestMapping(value="/getCartProdDesigns")
	public @ResponseBody String getCartProdDesigns(HttpServletRequest request)
	{
		
		JSONObject respObj=new JSONObject();
		try{
		int productId=Integer.parseInt(request.getParameter("prodId"));
		
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		appDTO.setControllerMap(dataMap);
		dataMap.put("productId",productId);
		appDTO.setControllerMap(dataMap);
		appDTO=cartService.getCartProdDesigns(appDTO);
		dataMap=appDTO.getControllerMap();
		JSONArray designArray=(JSONArray) dataMap.get("designArray");
		respObj.put("selDesigns",designArray);
		respObj.put("STATUS",appDTO.getResponseStatus().toString());

		}
		catch(NumberFormatException ne)
		{
			
		}
		catch(Exception e)
		{
			
		}
		
		return respObj.toString();
	}
	
	
	@RequestMapping(value="/removeCartProduct")
	public @ResponseBody String removeCartProduct(HttpServletRequest request)
	{
	JSONObject respObj=new JSONObject();
	int productId=Integer.parseInt(request.getParameter("prodId"));
	try{
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		dataMap.put("productId",productId);
		appDTO.setControllerMap(dataMap);
		appDTO=cartService.removeProductFromCart(appDTO);
		
		if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
		{
			respObj.put("STATUS","ERROR");
			respObj.put("MESSAGE",appDTO.getErrorMessage());
		}
		else
		{
			respObj.put("STATUS","SUCCESS");
			respObj.put("MESSAGE",appDTO.getInfoMessage());
		}
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	return respObj.toString();
		
	}
	

	@RequestMapping(value="/chooseDeliveryAddress")
	public ModelAndView chooseDeliveryAddress(HttpServletRequest request)
	{
		ModelAndView mv =new ModelAndView("deliveryAddress");

		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		appDTO.setControllerMap(dataMap);
		appDTO=cartService.getAllDeliveryAddress(appDTO);
		
		if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
		{
		dataMap=appDTO.getControllerMap();
		mv.addAllObjects(dataMap);
		}
		else
		{
			mv.setViewName("redirect:/index.htm");
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
		}
		return mv;
		
	}
	
	
	@RequestMapping(value="/confirmAddress")
	public ModelAndView confirmAddress(HttpServletRequest request)
	{
		ModelAndView mv =new ModelAndView("redirect:/paymentInit.htm");
		AppDTO appDTO=new AppDTO();
		
		try{
		Integer addressId=Integer.parseInt(request.getParameter("selAddressId"));
		
		
		
		Map dataMap=new HashMap();
		dataMap.put("addressId",addressId);
		appDTO.setControllerMap(dataMap);
		appDTO=cartService.confirmDeliveryAddress(appDTO);
		
		if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
		{
		dataMap=appDTO.getControllerMap();
		mv.addAllObjects(dataMap);
		}
		else
		{
			mv.setViewName("redirect:/chooseDeliveryAddress.htm");
			setError_message(appDTO.getErrorMessage(),true);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			mv.setViewName("redirect:/chooseDeliveryAddress.htm");
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
		}
		return mv;
		
	}
	


	/* getters and setters */

	public CartService getCartService() {
		return cartService;
	}


	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}
	
	

}
