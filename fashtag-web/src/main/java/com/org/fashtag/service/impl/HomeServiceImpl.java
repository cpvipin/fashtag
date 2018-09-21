package com.org.fashtag.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.dao.CategoryDao;
import com.org.fashtag.dao.CustomerDao;
import com.org.fashtag.dao.ProductDao;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerCare;
import com.org.fashtag.model.CustomerIp;
import com.org.fashtag.model.Product;
import com.org.fashtag.service.HomeService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.DateUtils;

public class HomeServiceImpl extends BaseServiceImpl implements HomeService {
	
	public  CategoryDao categoryDao;
	public ProductDao productDao;
	private CustomerDao customerDao;
	
	/*
	 * (non-Javadoc)
	 * @see com.org.fashtag.service.HomeService#loadHomeElements()
	 * Loads all required data for Home layout and pass this to controller.
	 * Product list has the preferred category of products assigned to home layout.
	 * Banner images with HOME layout value will be provided
	 */
	public AppDTO loadHomeElements()
	{
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		Collection productPreferenceList=getProductPreferenceList();
		Collection bannerImages=categoryDao.getBannerImagesByLayoutAndCategory("HOME",0);
		dataMap.put("productList", productPreferenceList);
		dataMap.put("bannerImages", bannerImages);	
		appDTO.setControllerMap(dataMap);
		
		return appDTO;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.org.fashtag.service.HomeService#getChildMenuList(com.org.fashtag.AppDTO)
	 * All Child categories from level2 and level3 will be returned
	 */
	public AppDTO getChildMenuList(AppDTO appDTO)
	{
		try{
		Map dataMap=appDTO.getControllerMap();
		String parentStr=(String)dataMap.get("PARENT_ID");
		Collection childMenuList=Collections.EMPTY_LIST;
		if(!CommonUtils.isEmpty(parentStr))
		{
			childMenuList=categoryDao.getMenuHierarchy(appDTO);
		}
		
		dataMap.put("childMenuList",childMenuList);
		appDTO.setControllerMap(dataMap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			return appDTO;
		
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.org.fashtag.service.HomeService#addCareRequest(com.org.fashtag.AppDTO)
	 * Care request will be of type contact feedback or complaint
	 * common field can have value subject for contact and order id for the other two.
	 */
	
	
	
	public AppDTO addCareRequest(AppDTO appDTO)
	{
		
		try{
			
			Map dataMap=appDTO.getControllerMap();
			Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
			if(customer!=null)
			{
			String commonField=(String)dataMap.get("commonField");
			String comment=(String)dataMap.get("comment");
			String careReq=(String)dataMap.get("page");

			CustomerCare customerCare=new CustomerCare();
			customerCare.setCommonField(commonField);
			customerCare.setCareType(careReq);
			customerCare.setComment(comment);
			customerCare.setCustomer(customer);
			customerCare.setDateAdded(DateUtils.getCurrentDate());
			
			customerDao.addCustomerCare(customerCare);
			appDTO.setInfoMessage(getResourceMessage(THANKYOU_FOR_CONTACT));
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			
			
			}
			else
			{
				appDTO.setErrorMessage(getResourceMessage(NOT_AUTHORISED));
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				
			}
			
			
			
		}
		catch(Exception e)
		{

			appDTO.setErrorMessage(getResourceMessage(UNEXPECTED_ERROR));
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			
			e.printStackTrace();
		}
		
		return appDTO;
	}
	
	
	
	private Collection getProductPreferenceList()
	{
		
		Collection prodList=productDao.getProductListByLayout("home");
		Collection productList=new ArrayList();
		Iterator iter=prodList.iterator();
		while(iter.hasNext())
		{
			Object[] objects=(Object[])iter.next();
			String productName=(String) objects[0];
			int productId=(Integer) objects[1];
			String defaultImage=(String) objects[2];
			BigDecimal offerPrice=(BigDecimal) objects[3];
			BigDecimal actualPrice=(BigDecimal) objects[4];
			int categoryId=(Integer) objects[5];
			int totalLikes=(Integer) objects[6];
			
			Category category=categoryDao.getCategoryById(categoryId);
			
			Product product=new Product();
			product.setName(productName);
			product.setId(productId);
			product.setDefaultImage(defaultImage);
			product.setOfferPrice(offerPrice.setScale(0));
			product.setActualPrice(actualPrice.setScale(0));
			product.setCategory(category);
			product.setTotalLikes(totalLikes);
			productList.add(product);
			
		}
		
		
		
		
		return productList;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.org.fashtag.service.HomeService#logAnalytics(java.lang.String)
	 * 
	 * log customer request
	 * 
	 */
	public void logAnalytics(String ipAddress)
	{
		
		CustomerIp custIp=new CustomerIp();
		Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
		custIp.setDateAdded(DateUtils.getCurrentDate());
		custIp.setIpAddress(ipAddress);
		custIp.setCustomer(customer);
		customerDao.add(custIp);
		
	}
	/* getters and setters */
	

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}


	public ProductDao getProductDao() {
		return productDao;
	}


	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
}
