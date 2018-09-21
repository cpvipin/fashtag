package com.org.fashtag.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.Gender;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.common.WebAppConstants;
import com.org.fashtag.dao.CategoryDao;
import com.org.fashtag.dao.CustomerDao;
import com.org.fashtag.dao.ProductDao;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.model.Product;
import com.org.fashtag.model.ProductToDesignAttributeSpecification;
import com.org.fashtag.model.ProductToSize;
import com.org.fashtag.model.Size;
import com.org.fashtag.service.CartService;
import com.org.fashtag.service.HomeService;
import com.org.fashtag.service.ProductService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.DateUtils;

public class ProductServiceImpl extends BaseServiceImpl implements ProductService,WebAppConstants {
	
	public ProductDao productDao;
	public CategoryDao categoryDao;
	public CartService cartService;
	public CustomerDao customerDao;
	

	/*
	 * (non-Javadoc)
	 * @see com.org.fashtag.service.ProductService#getProductsByCategory(com.org.fashtag.AppDTO)
	 * If level2 categry id is requested then all the leaf nodecategories of the level2 category 
	 * will find out and all products in all thea categoriers will be pulled
	 * if level3 category is requested then products under than category will be directly taken
	*/
	
	@Override
	public AppDTO getProductsByCategory(AppDTO appDTO) {

		try{
			
			Map dataMap=appDTO.getControllerMap();
			String catId=""+((Integer) dataMap.get("CATEGORY_ID"));
			
			Collection leafNodeCategories=categoryDao.getLeafNodesOfCategoryById(Integer.parseInt(catId));
			
			if(leafNodeCategories.size()>0)
			{
				StringBuilder allCatStr=new StringBuilder();
				Iterator iter=leafNodeCategories.iterator();
				
				while(iter.hasNext())
				{
					Category catObj=(Category)iter.next();
					allCatStr.append(catObj.getId());
					if(iter.hasNext())
					{
					allCatStr.append(",");
					}
				}
				
				dataMap.put("CATEGORY_ID",allCatStr.toString());

			}
			else
			{
			dataMap.put("CATEGORY_ID",catId);
			
			}
			appDTO.setControllerMap(dataMap);
			appDTO=productDao.getProductListByCategory(appDTO);
			dataMap=appDTO.getControllerMap();
			
			Category category=categoryDao.getCategoryById(Integer.parseInt(catId));
			dataMap.put("CATEGORY",category);
			
			appDTO.setControllerMap(dataMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			appDTO.setErrorMessage(getResourceMessage("UNEXPECTED_ERROR"));
			
		}
		
		return appDTO;
	}
	
	public Collection getListProductBannerImages(int catId)
	{
		Collection bannerImages=categoryDao.getBannerImagesByLayoutAndCategory(LIST_PRODUCT_LAYOUT, catId);
		return bannerImages;
		
	}

	
	public AppDTO getProductDetails(AppDTO appDTO)
	{
		Map dataMap=appDTO.getControllerMap();
		int productId=(Integer) dataMap.get("productId");
		int selSizeId=0;
		int selQty=1;
		boolean edit=(Boolean) dataMap.get("edit");
		
		try{
		Product product=productDao.getProductById(productId);
		if(product!=null && product.getActiveStatus() && product.getQuantity()>0)
		{
		Collection productImages=productDao.getProductImages(productId);
		Collection productSizes=productDao.getProductSizes(productId);
		Collection productDesignsColl=productDao.getProductDesigns(productId);

		
		
		String selectedDesigns="";
		
		if(edit)
		{
			JSONObject selProdObj=cartService.getSelCartProduct(productId);
			JSONArray selDesArray=selProdObj.getJSONArray("designAttributeSpecs");
			
			for(int i=0;i<selDesArray.length();i++)
			{
				selectedDesigns+=selDesArray.getJSONObject(i).getInt("id");
				if(i<(selDesArray.length()-1))
				{
					selectedDesigns+=",";
				}
			}
			selSizeId=selProdObj.getInt("sizeId");
			selQty=selProdObj.getInt("quantity");
			
		}
		else
		{
		Iterator iter=productDesignsColl.iterator();
		while(iter.hasNext())
		{
			ProductToDesignAttributeSpecification proDes=(ProductToDesignAttributeSpecification)iter.next();
			if(proDes.getIsRecommended()==true)
			{
			selectedDesigns+=proDes.getDesignAttributeSpecification().getId()+",";
			}
		}
		
		}
	
		/* if only one size available in product detail then select that size by default
		 * and request shouldnot be in edit mode.
		 * */
		if(productSizes.size()==1 && !edit)
		{
			selSizeId=((Size)(productSizes.iterator().next())).getId();
		}
		
		
		dataMap.put("productDesignCount",productDesignsColl.size());
		dataMap.put("productImages",productImages);
		dataMap.put("productSizes",productSizes);
		dataMap.put("product",product);
		dataMap.put("selectedDesigns",selectedDesigns);
		dataMap.put("selSizeId",selSizeId);
		dataMap.put("selQty",selQty);

		appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		}
		else
		{
			appDTO.setErrorMessage(getResourceMessage(PRODUCT_NOT_EXISTS));
			appDTO.setResponseStatus(ResponseStatus.ERROR);
		}
		appDTO.setControllerMap(dataMap);
		
		}
		catch(Exception e)
		{
			appDTO.setErrorMessage(getResourceMessage("PRODUCT_NOT_EXISTS"));
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			e.printStackTrace();
		}
		return appDTO;
	}
	
/*
 * (non-Javadoc)
 * @see com.org.fashtag.service.ProductService#getMatchingProducts(com.org.fashtag.AppDTO)
 * get matching products that can be bought along with the specified product.
 * productid will be supplied and this method will provide the list of matching products.
 */
	@Override
	public AppDTO getMatchingProducts(AppDTO appDTO) {
		
		try{
			Map dataMap=appDTO.getControllerMap();
			int productId=(Integer)dataMap.get("productId");
			Product product=productDao.getProductById(productId);
			Collection matchingProducts=productDao.getMatchingProducts(product.getCategory().getId());
			dataMap.put("matchingProducts",matchingProducts);
			appDTO.setControllerMap(dataMap);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		return appDTO;
	}
	
	@Override
	public AppDTO getSimilarProducts(AppDTO appDTO){
		Map dataMap=appDTO.getControllerMap();
		int productId=(Integer)dataMap.get("productId");
		Collection similarProducts=Collections.EMPTY_LIST;
		try{
/*			Product product=productDao.getProductById(productId);*/
			similarProducts=productDao.getSimilarProducts(productId);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		dataMap.put("similarProducts",similarProducts);
		appDTO.setControllerMap(dataMap);
	
		
		return appDTO;
		
	}
	
	public AppDTO isDeliveryAvailable(AppDTO appDTO)
	{

		Map dataMap=appDTO.getControllerMap();
		try{
			int pincode=(Integer)dataMap.get("pincode");
			boolean isAvail=productDao.isPincodeExists(pincode);
			dataMap.put("isAvailable", isAvail);
			if(isAvail)
			{
				appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			appDTO.setInfoMessage(getResourceMessage("DELIVERY_AVAILABLE"));
			}
			else
			{
			appDTO.setErrorMessage(getResourceMessage("DELIVERY_UN_AVAILABLE"));
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			}
			
		}
		catch(Exception e)
		{
			dataMap.put("isAvailable", false);
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage("DELIVERY_UN_AVAILABLE"));
		}
		
		return appDTO;
	}

	
	public AppDTO getProductDesigns(AppDTO appDTO)
	{
		
		Map dataMap=appDTO.getControllerMap();
		try{
		int productId=(Integer)dataMap.get("productId");
		Collection productDesignsColl=productDao.getProductDesigns(productId);
		dataMap.put("productDesignsColl", productDesignsColl);
		appDTO.setControllerMap(dataMap);
		appDTO.setInfoMessage("");
		appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		}
		catch(Exception e)
		{
				appDTO.setResponseStatus(ResponseStatus.ERROR);
		}
		return appDTO;
		
		
	}

	
	public AppDTO isCustomSizeAvail(AppDTO appDTO)
	{
		try{
			CustomerProfile customerProfile=(CustomerProfile) getHttpSession().getAttribute(LOGGED_IN_CUSTOMER_PROFILE);
			Map dataMap=appDTO.getControllerMap();
			int prodId=(Integer)dataMap.get("prodId");
			Product product=productDao.getProductById(prodId);
			Collection prodSize=product.getProductToSize();
			
			Size customSize=(Size)productDao.findObjectByCondition(Size.class, 
				new String[] { "name" },
				new Object[] { "CUSTOM" });
			int reqMeasCount=0;
			
			if(customerProfile!=null)
			{
				reqMeasCount=customerDao.
							getCountOfZeroValuedRequiredMeasurement
							(customerProfile);
			}
			
			boolean isCustSizeAdded=false;
			Iterator iter=prodSize.iterator();			
			while(iter.hasNext())
			{
				ProductToSize prodToSize=(ProductToSize)iter.next();
				if(prodToSize.getSize().getId().intValue()==customSize.getId().intValue())
				{
					isCustSizeAdded=true;
					break;
				}
				
			}
			
			if(!isCustSizeAdded)
			{
				dataMap.put("message", "Custom size not available for this product");
				dataMap.put("action", false);
				appDTO.setControllerMap(dataMap);
				appDTO.setResponseStatus(ResponseStatus.SUCCESS);
				return appDTO;
			}
			else if(customerProfile==null)
			{

				dataMap.put("message", "Please login for the custom size to be enabled");
				dataMap.put("action", false);
				appDTO.setControllerMap(dataMap);
				appDTO.setResponseStatus(ResponseStatus.SUCCESS);
				return appDTO;
			}
			else if(customerProfile.getGender()!=product.getGender())
			{
				String genderProdName="";
				String genderProfName="";

				if(product.getGender()==Gender.MALE.getValue())
					genderProdName="Men";
				else if(product.getGender()==Gender.FEMALE.getValue())
					genderProdName="Women";
				

				if(customerProfile.getGender()==Gender.MALE.getValue())
					genderProfName="Men";
				else if(customerProfile.getGender()==Gender.FEMALE.getValue())
					genderProfName="Women";
				
				
				dataMap.put("message", "This Product is only for "+genderProdName+
						" and the profile selected is of"+genderProfName+
						". Please select some other profile or add a new one.");
				dataMap.put("action", false);
				appDTO.setControllerMap(dataMap);
				appDTO.setResponseStatus(ResponseStatus.SUCCESS);
				return appDTO;
			}
			
			else if(reqMeasCount>0)
			{
				dataMap.put("message", "Please enter values for all required measurements for the profile "+customerProfile.getName());
				dataMap.put("action", false);
				appDTO.setControllerMap(dataMap);
				appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			}
			else
			{
				dataMap.put("message", "Custom size available for the profile "+customerProfile.getName());
				dataMap.put("action", true);
				appDTO.setControllerMap(dataMap);
				appDTO.setResponseStatus(ResponseStatus.SUCCESS);
				return appDTO;
			}
			
			
			
			
		
		}
		catch(Exception e)
		{
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			e.printStackTrace();
		}
		
		
		return appDTO;
	}
	
	/* product will be availbel only if avail_date >= todays date
	 * active status should be true
	 * 
	 */
	public boolean isProductAvaialble(Product product)
	{
		Date currDate=DateUtils.getCurrentDate();
		if(product.getActiveStatus())
		{
			if(DateUtils.differenceInDays(currDate, product.getDateAvailable())<=0)
			{
				return true;
			}
			
		}
		
		return false;
	}
	

	public ProductDao getProductDao() {
		return productDao;
	}


	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}


	public CategoryDao getCategoryDao() {
		return categoryDao;
	}


	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public CartService getCartService() {
		return cartService;
	}

	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}


}
