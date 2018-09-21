package com.org.fashtag.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
import com.org.fashtag.model.DesignAttributeSpecification;
import com.org.fashtag.service.ProductService;
import com.org.fashtag.util.CommonUtils;

@Controller
public class ProductController extends BaseController {
	
	@Autowired
	private ProductService productService;

	
	@RequestMapping("/listProducts")
	public ModelAndView listProducts(HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView("listProducts");
		
		int categoryId=Integer.parseInt(request.getParameter("catId"));
		Collection bannerImages=productService.getListProductBannerImages(categoryId);
		mv.addObject("CATEGORY_ID", categoryId);
		mv.addObject("bannerImages",bannerImages);
		
		return mv;
		
	}
	
	
	

	@RequestMapping("/listProductsContent")
	public ModelAndView listProductsContent(HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView("listProductsContent");
		try{
		
			int categoryId=Integer.parseInt(request.getParameter("catId"));
			String priceFilters=request.getParameter("priceFilter");
			
			AppDTO appDTO=new AppDTO();
			Map dataMap=new HashMap();
			dataMap.put("CATEGORY_ID",categoryId);
			dataMap.put("priceFilter",priceFilters);
			appDTO.setControllerMap(dataMap);
			appDTO=productService.getProductsByCategory(appDTO);
			mv.addAllObjects(appDTO.getControllerMap());
		
			
		}
		catch(Exception e)
		{
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			mv.setViewName("redirect:/index.htm");
		}
		
		return mv;
		
	}
	
	
	@RequestMapping("/productDetails")
	public ModelAndView productDetails(HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView("productDetails");
		try
		{
		int productId=Integer.parseInt(request.getParameter("prodId"));
		boolean edit=false;
		
		if(!CommonUtils.isEmpty(request.getParameter("edit")))
		{
		edit=Boolean.parseBoolean(request.getParameter("edit"));
		}
		
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		dataMap.put("productId",productId);
		dataMap.put("edit", edit);
		appDTO.setControllerMap(dataMap);
		appDTO=productService.getProductDetails(appDTO);
		if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
		{		
		mv.addAllObjects(appDTO.getControllerMap());
		}
		else
		{
			setError_message(appDTO.getErrorMessage(),true);
			mv.setViewName("redirect:/index.htm");
		}
		
		}
		catch(Exception e)
		{
		setError_message(getResourceMessage("UNEXPECTED_ERROR"),true);
		mv.setViewName("redirect:/index.htm");
		}
		
		return mv;
		
	}
	

	@RequestMapping("/matchingProducts")
	public ModelAndView matchingProducts(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("matchingProducts");
		
		try{
		int productId=Integer.parseInt(request.getParameter("prodId"));
		
		Map dataMap=new HashMap();
		AppDTO appDTO=new AppDTO();
		dataMap.put("productId",productId);
		appDTO.setControllerMap(dataMap);
		
		appDTO=productService.getMatchingProducts(appDTO);
		
		mv.addAllObjects(dataMap);

		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mv;
	}

	

	@RequestMapping("/similarProducts")
	public ModelAndView similarProducts(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("similarProducts");
		
		try{
		int productId=Integer.parseInt(request.getParameter("prodId"));
		
		Map dataMap=new HashMap();
		AppDTO appDTO=new AppDTO();
		dataMap.put("productId",productId);
		appDTO.setControllerMap(dataMap);
		
		appDTO=productService.getSimilarProducts(appDTO);
		
		mv.addAllObjects(dataMap);
		

		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mv;
	}
	
	@RequestMapping(value="/checkDelivery", method = RequestMethod.POST)
	public @ResponseBody String isDeliverAvailable(HttpServletRequest request)
	{
		JSONObject respObj=new JSONObject();
		try{

			int pincode=Integer.parseInt(request.getParameter("pincode"));
			int productId=Integer.parseInt(request.getParameter("prodId"));
			
			AppDTO appDTO=new AppDTO();
			Map dataMap=new HashMap();
			dataMap.put("pincode",pincode);
			dataMap.put("productId",productId);
			appDTO.setControllerMap(dataMap);
			appDTO=productService.isDeliveryAvailable(appDTO);
			dataMap=appDTO.getControllerMap();
			if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
			{
				respObj.put("ISAVAIL",dataMap.get("isAvailable"));
				respObj.put("STATUS", "ERROR");
				respObj.put("MESSAGE",appDTO.getErrorMessage());
			}
			else
			{
				
				respObj.put("ISAVAIL",dataMap.get("isAvailable"));
				respObj.put("MESSAGE",appDTO.getInfoMessage());
				respObj.put("STATUS", "SUCCESS");
			}
		}
		catch(Exception e)
		{
			
		}
		
		return respObj.toString();
		
	}
	
	@RequestMapping(value="/productDesigns", method = RequestMethod.GET)
	public ModelAndView getProductDesigns(HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView("productDesigns");
		AppDTO appDTO=new AppDTO();
			try{
				int productId=Integer.parseInt(request.getParameter("productId"));
				String custDesign=request.getParameter("custDesign");
				
				
				Map dataMap=new HashMap();
				dataMap.put("productId",productId);
				dataMap.put("custDesign",custDesign);
				appDTO.setControllerMap(dataMap);
				appDTO=productService.getProductDesigns(appDTO);
				if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
				{
			
				}
				dataMap=appDTO.getControllerMap();
				Collection productDesignsColl=(Collection)dataMap.get("productDesignsColl");
				
				Iterator iter=productDesignsColl.iterator();
				mv.addAllObjects(dataMap);
				
			}
			catch(Exception e)
			{
			
			}
		return mv;
		
	}
	
	
	
	@RequestMapping(value="/customSizeAvailablity", method = RequestMethod.POST)
	public @ResponseBody String isCustomizeAvailable(HttpServletRequest request)
	{
		
		JSONObject respObj= new JSONObject();
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		
		try{
			
			int productId=Integer.parseInt(request.getParameter("prodId"));
			dataMap.put("prodId",productId);
			appDTO.setControllerMap(dataMap);
			
			appDTO=productService.isCustomSizeAvail(appDTO);
			dataMap=appDTO.getControllerMap();
			if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
			{
				respObj.put("message",dataMap.get("message"));
				respObj.put("action",dataMap.get("action"));
				respObj.put("status","success");
				
			}
			else
			{
				if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
				{
					respObj.put("message",dataMap.get("message"));
					respObj.put("action",false);
					respObj.put("status","success");
					
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return respObj.toString();
		
	}
	
	/* getters and setters */
	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	

}
