package com.org.fashtag.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.AppConstants;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.dao.CartDao;
import com.org.fashtag.dao.CategoryDao;
import com.org.fashtag.dao.CustomerDao;
import com.org.fashtag.dao.ProductDao;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerAddress;
import com.org.fashtag.model.DesignAttributeSpecification;
import com.org.fashtag.model.OrderProduct;
import com.org.fashtag.model.Product;
import com.org.fashtag.model.Size;
import com.org.fashtag.model.State;
import com.org.fashtag.service.CartService;
import com.org.fashtag.service.HomeService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.CommonUtils;

public class CartServiceImpl extends BaseServiceImpl implements CartService {
	
	public CartDao cartDao;
	public ProductDao productDao;
	public CustomerDao customerDao;
	
	public AppDTO getCartProducts(AppDTO appDTO)
	{
		String sessCart=(String)getHttpSession().getAttribute(SESSION_CART);
		int count=0;
		BigDecimal totalPrice=new BigDecimal(0);
		Map dataMap=new HashMap();
		ArrayList orderProducts=new ArrayList();
		try
		{

			if(!CommonUtils.isEmpty(sessCart))
			{
		JSONObject cartObj=new JSONObject(sessCart);
		JSONArray productArray=cartObj.getJSONArray("products");
		count=cartObj.getInt("count");
		 totalPrice=new BigDecimal(""+cartObj.get("totalPrice"));
		
		for(int i=0;i<productArray.length();i++)
		{
			JSONObject prodObj=productArray.getJSONObject(i);
			JSONArray designArray=prodObj.getJSONArray("designAttributeSpecs");
			Product product=productDao.getProductById((Integer)prodObj.get("prodId"));
			Size size=productDao.getSizeById(prodObj.getInt("sizeId"));
			BigDecimal totalAmount=new BigDecimal(prodObj.getInt("quantity")).multiply(product.getOfferPrice());
			
			OrderProduct orderProduct=new OrderProduct();
			orderProduct.setProduct(product);
			orderProduct.setDesignAttributeSpecification(designArray.toString());
			orderProduct.setQuantity(prodObj.getInt("quantity"));
			orderProduct.setSize(size);
			orderProduct.setUnitPrice(product.getOfferPrice());
			orderProduct.setTotalAmount(totalAmount);
			orderProducts.add(orderProduct);
		}
			}
			
		dataMap.put("orderProducts",orderProducts);
		dataMap.put("totalPrice",totalPrice);
		dataMap.put("count",count);
		
		
		appDTO.setControllerMap(dataMap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return appDTO;
	}


	@Override
	public AppDTO addProductToCart(AppDTO appDTO) 
	{

		Map dataMap=appDTO.getControllerMap();
		JSONObject cartObj=new JSONObject();	
		
		try{
		
		String userId=(String) getHttpSession().getAttribute(LOGGED_IN_CUSTOMER_USERID);
		Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
		String sessionCart=(String) getHttpSession().getAttribute(SESSION_CART);
		
		int sizeId=(Integer)dataMap.get("sizeId");
		int productId=(Integer)dataMap.get("productId");
		String custDesign=(String)dataMap.get("custDesign");
		int quantity=(Integer)dataMap.get("quantity"); 
		
		Product product=productDao.getProductById(productId);
		Size size=productDao.getSizeById(sizeId);
		
		if(product.getQuantity()<quantity)
		{
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage(SELECTED_QUANTITY_NOT_AVAIL_PRODUCT));
		}
		else
		{	
		int productCountInCart=0;
		BigDecimal totalPrice=new BigDecimal(0.00).setScale(2,RoundingMode.HALF_UP);
		
		
		if(!CommonUtils.isEmpty(sessionCart))
		{
			cartObj=new JSONObject(sessionCart);
			JSONArray productArr=cartObj.getJSONArray("products");
			productCountInCart=(Integer)cartObj.get("count");
			totalPrice=new BigDecimal(""+cartObj.get("totalPrice"));
			
			if(!isProductExistsInCart(sessionCart,productId))
			{
			
			
			BigDecimal itemCost=product.getOfferPrice().multiply(new BigDecimal(quantity));
			totalPrice=totalPrice.add(itemCost);
			

			productCountInCart=productCountInCart+1;
			JSONObject productObj=new JSONObject();
			
			productObj.put("name", product.getName());
			productObj.put("image", product.getDefaultImage());
			productObj.put("prodId", product.getId());
			productObj.put("quantity", quantity);
			productObj.put("price", product.getOfferPrice());
			productObj.put("sizeName", size.getName());
			productObj.put("sizeId", size.getId());
			productObj.put("designAttributeSpecs", getDesignAttrSpecs(custDesign));
			
			productArr.put(productObj);
			
			cartObj.put("products",productArr);
			cartObj.put("count", productCountInCart);
			cartObj.put("totalPrice",totalPrice);
			
			getHttpSession().setAttribute(SESSION_CART,cartObj.toString());
			appDTO.setInfoMessage(getResourceMessage("PRODUCT_ADDED_CART"));
			
			}
			else
			{
			 /*update existing object with new values so that cart
                edit product functionaliity will also work */
				
				for(int i=0;i<productArr.length();i++)
				{
					JSONObject productObj=productArr.getJSONObject(i);
						
					if(productObj!=null && productObj.getInt("prodId")==product.getId())
					{
						int oldQty=productObj.getInt("quantity");
						BigDecimal oldTotalPrice=new BigDecimal(""+cartObj.get("totalPrice"));
						BigDecimal oldTotUnitPrice=new BigDecimal(oldQty).multiply(product.getOfferPrice());
						BigDecimal newTotUnitPrice=new BigDecimal(quantity).multiply(product.getOfferPrice());
						totalPrice=oldTotalPrice.subtract(oldTotUnitPrice).add(newTotUnitPrice);

						productObj.put("name", product.getName());
						productObj.put("image", product.getDefaultImage());
						productObj.put("prodId", product.getId());
						productObj.put("quantity", quantity);
						productObj.put("price", product.getOfferPrice());
						productObj.put("sizeName", size.getName());
						productObj.put("sizeId", size.getId());
						productObj.put("designAttributeSpecs", getDesignAttrSpecs(custDesign));
									
						
						break;
						
					}
					
					
					
				}
				cartObj.put("count", productCountInCart);
				cartObj.put("totalPrice",totalPrice);
				
				getHttpSession().setAttribute(SESSION_CART,cartObj.toString());
				appDTO.setInfoMessage(getResourceMessage("PRODUCT_ADDED_CART"));
				
				
			}
		}
		else
		{
			productCountInCart=1;

			BigDecimal itemCost=product.getOfferPrice().multiply(new BigDecimal(quantity));
			totalPrice=totalPrice.add(itemCost);
			
			cartObj.put("count",1);
			cartObj.put("totalPrice",totalPrice.toString());
			
			JSONArray productArr=new JSONArray();
			JSONObject productObj=new JSONObject();
			
			productObj.put("name", product.getName());
			productObj.put("image", product.getDefaultImage());
			productObj.put("prodId", product.getId());
			productObj.put("quantity", quantity);
			productObj.put("price", product.getOfferPrice());
			productObj.put("sizeName", size.getName());
			productObj.put("sizeId", size.getId());
			productObj.put("designAttributeSpecs", getDesignAttrSpecs(custDesign));
			productArr.put(productObj);
			cartObj.put("products",productArr);

			getHttpSession().setAttribute(SESSION_CART,cartObj.toString());
			appDTO.setInfoMessage(getResourceMessage("PRODUCT_ADDED_CART"));
			
		}

		if((!CommonUtils.isEmpty(userId)) && (customer!=null) )
		{
			/* update cart to table */
			cartDao.updateCustomerCart(cartObj.toString(), customer.getId());
			
		}
		
		dataMap.put("product_count",productCountInCart);
		dataMap.put("total_price",totalPrice);
		
		appDTO.setControllerMap(dataMap);
		appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		}
		
		
		}
		catch(Exception e)
		{
			appDTO.setErrorMessage("unexpected error");
			appDTO.setResponseStatus(ResponseStatus.ERROR);

		}
		
		
		return appDTO;
	}
	
	
	
	
	public AppDTO getCartProdDesigns(AppDTO appDTO)
	{

		Map dataMap=appDTO.getControllerMap();
		JSONArray designArray=new JSONArray();
		try{
		Integer productId=(Integer)dataMap.get("productId");
		String sessionCart=getSessionCart();
		JSONObject cartObj=new JSONObject(sessionCart);;
		
		JSONArray productArr=cartObj.getJSONArray("products");
		
		for(int i=0;i<productArr.length();i++)
		{
			JSONObject prodObj=productArr.getJSONObject(i);
			int cartProdId=prodObj.getInt("prodId");
			if(cartProdId==productId)
			{
				designArray=prodObj.getJSONArray("designAttributeSpecs");
				dataMap.put("designArray", designArray);
			}
			
			
		}

		
		
		appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		appDTO.setControllerMap(dataMap);

		
		}catch(Exception e)
		{
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage("DESIGNS_NOT_AVAILABLE"));
			e.printStackTrace();
		}
		
		
		return appDTO;
		
	}
	
	
	
	
	
	
	public AppDTO removeProductFromCart(AppDTO appDTO)
	{
		
		
		try{
			
			Map dataMap=appDTO.getControllerMap();
			JSONObject sessCartObj=new JSONObject(getSessionCart());
			
			String userId=(String) getHttpSession().getAttribute(LOGGED_IN_CUSTOMER_USERID);
			Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
			
			
			JSONArray sesProdArr=sessCartObj.getJSONArray("products");
			ArrayList prodList=new ArrayList();
			int productId=(Integer)dataMap.get("productId");
			BigDecimal totalPrice=new BigDecimal(""+sessCartObj.get("totalPrice"));
			int count=sessCartObj.getInt("count");
			
			for(int i=0;i<sesProdArr.length();i++)
			{
				
				JSONObject prodObj=sesProdArr.getJSONObject(i);
				
				if(productId==prodObj.getInt("prodId"))
				{

					int remQty=prodObj.getInt("quantity");
					BigDecimal totalItemPrice=new BigDecimal(remQty).
					multiply(new BigDecimal(""+prodObj.get("price")));
					
					totalPrice=totalPrice.subtract(totalItemPrice);
					count=count-1;
				}
				else
				{
					prodList.add(prodObj);
				}
			}
			
			JSONArray prodArray=new JSONArray(prodList);
			sessCartObj.put("products", prodArray);
			sessCartObj.put("count", count);
			sessCartObj.put("totalPrice", totalPrice);
			

			if((!CommonUtils.isEmpty(userId)) && (customer!=null) )
			{
				/* update cart to table */
				
				cartDao.updateCustomerCart(sessCartObj.toString(), customer.getId());
				
			}

			
			getHttpSession().setAttribute(SESSION_CART,sessCartObj.toString());
			
			
			appDTO.setInfoMessage(getResourceMessage("PRODUCT_REMOVED_CART"));
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		}
		catch(Exception e)
		{
			appDTO.setErrorMessage(getResourceMessage("UNEXPECTED_ERROR"));
			appDTO.setResponseStatus(ResponseStatus.ERROR);
		}
		
		
		
		return appDTO;
	}
	
	
	
	public AppDTO confirmDeliveryAddress(AppDTO appDTO)
	{
		try{
			Map dataMap=appDTO.getControllerMap();
			int addressId=(Integer)dataMap.get("addressId");
		 
			CustomerAddress custAddress=customerDao.getCustomerAddressById(addressId);
			if(custAddress!=null)
			{
				boolean isAvail=productDao.isPincodeExists(Integer.parseInt(custAddress.getPinCode()));
				if(isAvail)
				{
					JSONObject sessCartObj=new JSONObject(getSessionCart());
					sessCartObj.put("deliveryAddress", custAddress.getId());
					getHttpSession().setAttribute(SESSION_CART,sessCartObj.toString());
					appDTO.getControllerMap().clear();
					appDTO.setResponseStatus(ResponseStatus.SUCCESS);
				}
				else
				{
					appDTO.setErrorMessage(getResourceMessage("DELIVERY_UN_AVAILABLE"));
					appDTO.setResponseStatus(ResponseStatus.ERROR);
				}
			}
			else
			{
				appDTO.setErrorMessage(getResourceMessage(SELECT_DELIVERY_ADDRESS));
				appDTO.setResponseStatus(ResponseStatus.ERROR);
			}
			
		
		}
		catch(Exception e)
		{
			
		}
		return appDTO;
	}

	
	
	
	
	
	/* methods */
	
	
	
/*
 * If same product available in both session and usercart then
 * session cart product will not add to the new cart. 
 * 
 * if no session cart availble usercart directly set to session
 * 
 */
	public void mergeSessionAndUserCart(String userCart)
	{
	
		String sessionCart= getSessionCart();
		try{
		if(!CommonUtils.isEmpty(userCart))
		{
			/* if session cart empty then cartObj will put directly in
			 * session at the end 
			 */
			JSONObject cartObj=new JSONObject(userCart);
			BigDecimal userCartTotalPrice=new BigDecimal(""+cartObj.get("totalPrice"));
			JSONArray productArr=cartObj.getJSONArray("products");
			
			
			/*
			 * Below objects are for calculation purpose while merging
			 */
			BigDecimal totalAmount=new BigDecimal("0.00");
			int totalCount=0;
			
			
			if(!CommonUtils.isEmpty(sessionCart))
			{
				JSONObject sessCartObj=new JSONObject(sessionCart);
				JSONArray sessProductArr=sessCartObj.getJSONArray("products");
				BigDecimal sessCartTotalPrice=new BigDecimal(""+sessCartObj.get("totalPrice"));

				totalCount=((Integer) cartObj.get("count") )+((Integer) sessCartObj.get("count") ) ;
				totalAmount=userCartTotalPrice.add(sessCartTotalPrice);
			
				/*merge cart in session and in DB */
				for(int i=0;i<sessProductArr.length();i++)
				{
				JSONObject prodObj=sessProductArr.getJSONObject(i);
				
				/* eliminate common products*/
				if(!isProductExistsInCart(productArr.toString(),(Integer)prodObj.get("prodId")))
				{
				productArr.put(sessProductArr.getJSONObject(i));
				}
				else
				{
					/* 
					 * Subtract count and amount from merged total count and amount calculated
					 * 
					 */
					BigDecimal unitPrice=new BigDecimal(""+prodObj.get("price")).multiply(new BigDecimal(""+prodObj.get("quantity")));
					totalAmount=totalAmount.subtract(unitPrice);
					totalCount=totalCount-1;
				}
				
			 }
				/*
				 * set new products , total price and count to cart Obj
				 */
				cartObj.put("products", productArr);
				cartObj.put("totalPrice", totalAmount.toString());
				cartObj.put("count",totalCount);
			
			}
			/*
			 * if no session cart available cartobj will have only user db cart products
			 */
			getHttpSession().setAttribute(SESSION_CART, cartObj.toString());
			
			
		}
		
		}
		catch(Exception e)
		{
			
		}
		
		
	}

	
	
	public String getSessionCart()
	{
		String sessionCart="";
		try{
		sessionCart= (String) getHttpSession().getAttribute(SESSION_CART);
		}
		catch(Exception e)
		{
			
		}
		
		return sessionCart;
	}
	
	
	private boolean isProductExistsInCart(String cart,int prodId)
	{
	    return cart.toString().contains("\"prodId\":"+prodId);
	}
	
	
	public JSONObject getSelCartProduct(int productId)
	{
	String sessionCart=getSessionCart();
	JSONObject sessCartObj=new JSONObject();
	JSONObject prodObj=new JSONObject();

	try{
	 sessCartObj=new JSONObject(sessionCart);
	JSONArray sessProductArr=new JSONArray();
	sessProductArr=sessCartObj.getJSONArray("products");
	
	for(int i=0;i<sessProductArr.length();i++)
	{
		
		prodObj=sessProductArr.getJSONObject(i);
		int prodId=prodObj.getInt("prodId");
		
		if(prodId==productId)
		{
			
			break;
			
		}
	}
	
	
		
		}
		catch(Exception e)
		{
			
		}
		
		return prodObj;
	}
	
	private JSONArray getDesignAttrSpecs(String custDesign)
	{
		JSONArray designArray=new JSONArray();
		try{
		String[] designSpecId=custDesign.split(",");
		for(String desStr:designSpecId)
		{
			int designId=Integer.parseInt(desStr);
			DesignAttributeSpecification desAttrSpec=productDao.getDesignAttributeSpecById(designId);
			String imageUrl=getAppSystemProperties(AppConstants.DES_ATTR_IMAGE_URL)+desAttrSpec.getImage();
			JSONObject designObj=new JSONObject();
			designObj.put("name", desAttrSpec.getName());
			designObj.put("image", imageUrl);
			designObj.put("id", desAttrSpec.getId());
			designObj.put("desAttrId", desAttrSpec.getDesignAttribute().getId());
			designObj.put("desAttrName", desAttrSpec.getDesignAttribute().getName());
			
			designArray.put(designObj);
			
		}
		}
		catch(Exception e)
		{
			designArray=new JSONArray();
		}
		
		return designArray;
		
	}
	
	
	public AppDTO getAllDeliveryAddress(AppDTO appDTO)
	{
		try{
			
			String sesscart=getSessionCart();
			if(!CommonUtils.isEmpty(sesscart))
			{
			Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
			Collection addressColl=customerDao.getAllCustomerAddress(customer);
			Map dataMap=new HashMap();
			Collection stateColl=customerDao.findAll(State.class);
			int selAddressId=0;
			
			dataMap.put("addressColl",addressColl);
			dataMap.put("stateColl",stateColl);
			if(addressColl.size()==1)
			{
			CustomerAddress custAddress=(CustomerAddress)addressColl.iterator().next();
				selAddressId=custAddress.getId();
			}
			dataMap.put("selAddressId",selAddressId);
			appDTO.setControllerMap(dataMap);
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			}
			else
			{
				appDTO.setResponseStatus(ResponseStatus.ERROR);
			}
			
			
		}
		catch(Exception e)
		{
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			e.printStackTrace();
		}
		return appDTO;
		
	}
	
	
	

	/* getters and setters */

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public CartDao getCartDao() {
		return cartDao;
	}

	public void setCartDao(CartDao cartDao) {
		this.cartDao = cartDao;
	}



	public CustomerDao getCustomerDao() {
		return customerDao;
	}



	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	
}
