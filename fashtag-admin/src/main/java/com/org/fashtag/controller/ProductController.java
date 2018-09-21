package com.org.fashtag.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.beans.Page;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.controller.BaseController;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.DesignAttribute;
import com.org.fashtag.model.DesignAttributeSpecification;
import com.org.fashtag.model.Product;
import com.org.fashtag.model.Size;
import com.org.fashtag.model.Vendor;
import com.org.fashtag.service.DesignAttributeService;
import com.org.fashtag.service.ProductService;
import com.org.fashtag.service.SizeService;
import com.org.fashtag.service.impl.CategoryServiceImpl;
import com.org.fashtag.service.impl.ProductServiceImpl;
import com.org.fashtag.util.CommonUtils;

@Controller
public class ProductController extends BaseController {
	
	private static Logger logger = (Logger) Logger.getInstance(ProductController.class);
	
	@Autowired
	private ProductService productService;
	@Autowired
	private SizeService sizeService;
	@Autowired
	private DesignAttributeService designAttributeService;

	

	
/* product manage*/

	@RequestMapping("/addProduct")
	public ModelAndView addProduct() {
	
		ModelAndView mv = new ModelAndView("addProduct");
		Collection sizeList=Collections.EMPTY_LIST;
		Collection designAttributeList=Collections.EMPTY_LIST;
		
		sizeList=sizeService.getAllActiveSize();
		designAttributeList=designAttributeService.getAllActiveDesignAttributes();
		mv.addObject("sizeList",sizeList);
		mv.addObject("designAttributeList",designAttributeList);

		
		return mv;
	
	}
	
	
	@RequestMapping("/editProduct")
	public ModelAndView editProduct(@ModelAttribute Product product) {
	
		ModelAndView mv = new ModelAndView("addProduct");
		
		Collection sizeList=sizeService.getAllActiveSize();
		Collection designAttributeList=designAttributeService.getAllActiveDesignAttributes();
		
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		controllerMap.put("PRODUCT", product);
		appDTO.setControllerMap(controllerMap);
		appDTO=productService.getProductDetails(appDTO);
		
		product=(Product) appDTO.getControllerMap().get("PRODUCT");
		Collection prodDesignAttributeList=(Collection) appDTO.getControllerMap().get("DESIGN_ATTR_LIST");
		
		mv.addObject("product",product);
		mv.addObject("sizeList",sizeList);
		mv.addObject("prodDesignAttributeList",prodDesignAttributeList);
		
		return mv;
	
	}
	
	@RequestMapping(value="/saveProduct", method = RequestMethod.POST)
	public ModelAndView saveProduct(@ModelAttribute Product product,HttpServletRequest req) {
		ModelAndView mv=new ModelAndView();
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		try 
		{
			String[] productSize=req.getParameterValues("productSize");
			String[] productImage=req.getParameterValues("productImage");
			
			controllerMap.put("PRODUCT", product);
			controllerMap.put("DATE_AVAILABLE", req.getParameter("dateAvailable"));
			controllerMap.put("PRODUCT_SIZE",productSize);
			controllerMap.put("PRODUCT_IMAGE",productImage);
			controllerMap.put("REQUEST",req);

			appDTO.setControllerMap(controllerMap);
			appDTO=productService.addProduct(appDTO);

			if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
			{
				setError_message(appDTO.getErrorMessage(), true); 
					mv.setViewName("redirect:/addProduct.htm");
			}
			
			mv.setViewName("redirect:/listProduct.htm");
			setInfo_message(appDTO.getInfoMessage(), true);
		
		} 
		catch (Exception e) 
		{
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			mv.setViewName("redirect:/addProduct.htm");
		}
		return mv;
	
	}
	
	
	
	@RequestMapping(value="/updateProduct", method = RequestMethod.POST)
	public ModelAndView updateProduct(@ModelAttribute Product product,HttpServletRequest req) {
		ModelAndView mv=new ModelAndView();
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		try 
		{
			String[] productSize=req.getParameterValues("productSize");
			String[] productImage=req.getParameterValues("productImage");
			
			controllerMap.put("PRODUCT", product);
			controllerMap.put("DATE_AVAILABLE", req.getParameter("dateAvailable"));
			controllerMap.put("PRODUCT_SIZE",productSize);
			controllerMap.put("PRODUCT_IMAGE",productImage);
			controllerMap.put("REQUEST",req);

			appDTO.setControllerMap(controllerMap);
			appDTO=productService.updateProduct(appDTO);

			if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
			{
				setError_message(appDTO.getErrorMessage(), true); 
					mv.setViewName("redirect:/addProduct.htm");
			}
			
			mv.setViewName("redirect:/listProduct.htm");
			setInfo_message(appDTO.getInfoMessage(), true);
		
		} 
		catch (Exception e) 
		{
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			mv.setViewName("redirect:/addProduct.htm");
		}
		return mv;
	
	}
	
	
	
	
	
	@RequestMapping("/listProduct")
	public ModelAndView listProduct(HttpServletRequest request) {
	
		ModelAndView mv = new ModelAndView("listProduct");
	
		Page listProductPage=getPage();
		List productList=new ArrayList();
		listProductPage.setResultList(Collections.EMPTY_LIST);
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		controllerMap.put("PAGE", listProductPage);
		
		try
		{

		   String name=(String)request.getParameter("name");
			controllerMap.put("name",name);
			appDTO.setControllerMap(controllerMap);
			appDTO=productService.listAllProducts(appDTO);
			listProductPage=(Page)appDTO.getControllerMap().get("PAGE");
			productList=listProductPage.getResultList();
			setPage(listProductPage);
			mv.addObject("productList",productList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return mv;
	
	}
	
	
	
	

	@RequestMapping(value = "/getChildCategories", method = RequestMethod.POST)
	public @ResponseBody String getParentCategories(HttpServletRequest req) 
	{
		String searchKey = req.getParameter("searchKey");
		JSONArray jsonArr = new JSONArray();
		Collection categoryList = new ArrayList();

		categoryList = productService.getChildCategoryList(searchKey);
		Iterator iter = categoryList.iterator();
		
		while (iter.hasNext()) 
		{
			try 
			{
				JSONObject jsonObj = new JSONObject();
				
				Category catObj = (Category) iter.next();
				jsonObj.put("name", catObj.getName());
				jsonObj.put("id", catObj.getId());
				jsonArr.put(jsonObj);
				

			
			}
			catch (Exception e) 
			{
				logger.warn(getResourceMessage("UNEXPECTED_ERROR"));
				
			}
		}
		
		return jsonArr.toString();
	}
	
	
	@RequestMapping(value = "/getVendorsByName", method = RequestMethod.POST)
	public @ResponseBody String getVendorsByName(HttpServletRequest req) 
	{
		String searchKey = req.getParameter("searchKey");
		JSONArray jsonArr = new JSONArray();
		Collection vendorList = new ArrayList();

		vendorList = productService.getVendorsByName(searchKey);
		Iterator iter = vendorList.iterator();
		
		while (iter.hasNext()) 
		{
			try 
			{
				JSONObject jsonObj = new JSONObject();
				
				Vendor vendorObj = (Vendor) iter.next();
				jsonObj.put("name", vendorObj.getName());
				jsonObj.put("id", vendorObj.getId());
				jsonArr.put(jsonObj);
				

			
			}
			catch (Exception e) 
			{
				logger.warn(getResourceMessage("UNEXPECTED_ERROR"));
				
			}
		}
		
		return jsonArr.toString();
	}
	

	@RequestMapping("/deleteProduct")
	public ModelAndView deleteCatgeory(@ModelAttribute Product product,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("redirect:/listCategory.htm");
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		controllerMap.put("PRODUCT", product);
		appDTO.setControllerMap(controllerMap);
		appDTO=productService.deleteProduct(appDTO);
		if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
		{
			setError_message(appDTO.getErrorMessage(),true);
			mv.setViewName("redirect:/listProduct.htm");
		}
		
		setInfo_message(appDTO.getInfoMessage(), true);
		return mv;
	}
	
	
	
	

	
	/* getters and setters */


	public ProductService getProductService() {
		return productService;
	}


	public void setProductService(ProductService productService) {
		this.productService = productService;
	}


	public static Logger getLogger() {
		return logger;
	}


	public static void setLogger(Logger logger) {
		ProductController.logger = logger;
	}


	public SizeService getSizeService() {
		return sizeService;
	}


	public void setSizeService(SizeService sizeService) {
		this.sizeService = sizeService;
	}


	public DesignAttributeService getDesignAttributeService() {
		return designAttributeService;
	}


	public void setDesignAttributeService(
			DesignAttributeService designAttributeService) {
		this.designAttributeService = designAttributeService;
	}
}
