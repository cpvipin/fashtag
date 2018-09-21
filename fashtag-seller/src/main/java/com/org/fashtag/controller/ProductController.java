package com.org.fashtag.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.beans.Page;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.context.ApplicationContext;
import com.org.fashtag.controller.BaseController;
import com.org.fashtag.model.AdminUser;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.DesignAttribute;
import com.org.fashtag.model.DesignAttributeSpecification;
import com.org.fashtag.model.Product;
import com.org.fashtag.model.Vendor;
import com.org.fashtag.service.DesignAttributeService;
import com.org.fashtag.service.ProductService;
import com.org.fashtag.service.VendorService;
import com.org.fashtag.service.impl.VendorServiceImpl;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.DateUtils;
import com.org.fashtag.util.PasswordEncryptor;



@Controller
public class ProductController extends BaseController {
	
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private DesignAttributeService designAttributeService;


	
	@RequestMapping("/addProduct")
	public ModelAndView addProduct() {
		
		ModelAndView mv = new ModelAndView("addProduct");
		Collection sizeList=Collections.EMPTY_LIST;
		Collection designAttributeList=Collections.EMPTY_LIST;
		sizeList=productService.getAllActiveSize();
		designAttributeList=designAttributeService.getAllActiveDesignAttributes();
		mv.addObject("sizeList",sizeList);
		mv.addObject("designAttributeList",designAttributeList);
		return mv;
	
	}
	
	@RequestMapping("/editProduct")
	public ModelAndView editProduct(@ModelAttribute Product product) {
	
		ModelAndView mv = new ModelAndView("addProduct");
		
		Collection sizeList=productService.getAllActiveSize();
		
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
	public ModelAndView saveProduct(@ModelAttribute Product product,HttpServletRequest req,MultipartHttpServletRequest fReq) {
		ModelAndView mv=new ModelAndView();
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		try 
		{
			String[] productSize=req.getParameterValues("productSize");
			Collection productImage=fReq.getFiles("productImage");
			
			
			
			controllerMap.put("PRODUCT", product);
			controllerMap.put("DATE_AVAILABLE", req.getParameter("dateAvailable"));
			controllerMap.put("PRODUCT_SIZE",productSize);
			controllerMap.put("PRODUCT_IMAGE",productImage);
			controllerMap.put("REQUEST",fReq);
			
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
	public ModelAndView updateProduct(@ModelAttribute Product product,HttpServletRequest req,MultipartHttpServletRequest fReq) {
		ModelAndView mv=new ModelAndView();
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		try 
		{
			String[] productSize=req.getParameterValues("productSize");
			Collection productImage=fReq.getFiles("productImage");
			
			controllerMap.put("PRODUCT", product);
			controllerMap.put("DATE_AVAILABLE", req.getParameter("dateAvailable"));
			controllerMap.put("PRODUCT_SIZE",productSize);
			controllerMap.put("PRODUCT_IMAGE",productImage);
			controllerMap.put("REQUEST",fReq);
			
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
		   boolean approvalStatus=Boolean.parseBoolean(request.getParameter("approvalStatus"));
		   Vendor vendor=(Vendor)getHttpSession().getAttribute(LOGGED_IN_VENDOR); 

			controllerMap.put("name",name);
			controllerMap.put("vendor",vendor);
			controllerMap.put("approvalStatus",approvalStatus);

			
			appDTO.setControllerMap(controllerMap);
			appDTO=productService.listAllProductsOfVendor(appDTO);
			listProductPage=(Page)appDTO.getControllerMap().get("PAGE");
			productList=listProductPage.getResultList();
			setPage(listProductPage);
			mv.addObject("approvalStatus",approvalStatus);
			mv.addObject("productList",productList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return mv;
	
	}

	

	@RequestMapping(value = "/deleteSavedImage", method = RequestMethod.POST)
	public @ResponseBody String deleteSavedImage(HttpServletRequest req) 
	{

		try{
		Map dataMap=new HashMap();
		AppDTO appDTO=new AppDTO();
		dataMap.put("imageId",req.getParameter("imageId"));
		dataMap.put("productId",req.getParameter("productId"));
		appDTO.setControllerMap(dataMap);
		
		productService.removeSavedProductImage(appDTO);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "SUCCESS";
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
	

	@RequestMapping("/productSoldOut")
	public ModelAndView productSoldOut(@ModelAttribute Product product,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("redirect:/listProduct.htm");
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		controllerMap.put("PRODUCT", product);
		appDTO.setControllerMap(controllerMap);
		appDTO=productService.productSoldOut(appDTO);
		if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
		{
			setError_message(appDTO.getErrorMessage(),true);
			mv.setViewName("redirect:/listProduct.htm");
		}
		
		setInfo_message(appDTO.getInfoMessage(), true);
		return mv;
	}
	
	
	
	
	

	
	@RequestMapping(value = "/getDesignAttributes", method = RequestMethod.POST)
	public @ResponseBody String getDesignAttributes(HttpServletRequest req) 
	{
		String designAttribute = req.getParameter("searchKey");
		JSONArray jsonArr = new JSONArray();
		Collection designAttributeList = new ArrayList();

		designAttributeList = designAttributeService.getDesignAttributeListByKey(designAttribute);
		Iterator iter = designAttributeList.iterator();
		
		while (iter.hasNext()) 
		{
			try 
			{
				JSONObject jsonObj = new JSONObject();
				DesignAttribute designAttrObj = (DesignAttribute) iter.next();
				jsonObj.put("name", designAttrObj.getName());
				jsonObj.put("id", designAttrObj.getId());
				jsonArr.put(jsonObj);
			
			
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				return getResourceMessage("ERROR");
			}
		}
		
		return jsonArr.toString();
	}

	
	
	

/* design attribute specification*/

	
	@RequestMapping(value = "/getDesignAttrSpec", method = RequestMethod.POST)
	public @ResponseBody String getDesignAttrSpec(HttpServletRequest req) 
	{
		Integer designAttributeId =Integer.parseInt(req.getParameter("designAttrId"));
		JSONArray jsonArr = new JSONArray();
		Collection designAttributeList = new ArrayList();

		designAttributeList = designAttributeService.getDesignAttrSpecByDesignAttrId(designAttributeId);
		Iterator iter = designAttributeList.iterator();
		
		while (iter.hasNext()) 
		{
			try 
			{
				JSONObject jsonObj = new JSONObject();
				DesignAttributeSpecification designAttrSpecObj = (DesignAttributeSpecification) iter.next();
				jsonObj.put("name", designAttrSpecObj.getName());
				jsonObj.put("id", designAttrSpecObj.getId());
				jsonArr.put(jsonObj);
			
			
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				return getResourceMessage("ERROR");
			}
		}
		
		return jsonArr.toString();
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
				e.printStackTrace();
			}
		}
		
		return jsonArr.toString();
	}
	
	

	public ProductService getProductService() {
		return productService;
	}



	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	
}
