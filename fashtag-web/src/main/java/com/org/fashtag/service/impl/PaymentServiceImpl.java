package com.org.fashtag.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ccavenue.security.AesCryptUtil;
import com.org.fashtag.AppDTO;
import com.org.fashtag.common.AppCoreConstants;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.context.ApplicationContext;
import com.org.fashtag.context.PaymentConstants;
import com.org.fashtag.context.PaymentStatus;
import com.org.fashtag.context.TransactionManager;
import com.org.fashtag.context.WebBeanConstants;
import com.org.fashtag.dao.CustomerDao;
import com.org.fashtag.dao.OrdersDao;
import com.org.fashtag.dao.ProductDao;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerAddress;
import com.org.fashtag.model.CustomerBodyMeasurement;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.model.OrderHistory;
import com.org.fashtag.model.OrderProduct;
import com.org.fashtag.model.Orders;
import com.org.fashtag.model.OrdersStatus;
import com.org.fashtag.model.Product;
import com.org.fashtag.model.Size;
import com.org.fashtag.notify.MailSender;
import com.org.fashtag.service.AuthenticateService;
import com.org.fashtag.service.CartService;
import com.org.fashtag.service.PaymentService;
import com.org.fashtag.service.ProductService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.DateUtils;
import com.org.fashtag.util.PasswordEncryptor;

public class PaymentServiceImpl extends BaseServiceImpl implements
PaymentService,PaymentConstants {
	
	
	private ProductDao productDao;
	private CustomerDao customerDao;
	private OrdersDao ordersDao;
	
	private ProductService productService;
	
	
	public AppDTO initializePayment(AppDTO appDTO)
	{
		Map dataMap=appDTO.getControllerMap();
		try{
		String sessCart=(String)getHttpSession().getAttribute(SESSION_CART);
		
		if(!CommonUtils.isEmpty(sessCart))
		{
		JSONObject sessCartObj=new JSONObject(sessCart);
		JSONObject cartObj=new JSONObject(sessCart);
		JSONArray productArray=cartObj.getJSONArray("products");
		int count=cartObj.getInt("count");
        BigDecimal totalPrice=new BigDecimal("0.00");
        int deliveryAddress=sessCartObj.getInt("deliveryAddress");
        Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
		CustomerAddress custAddress=customerDao.getCustomerAddressById(deliveryAddress);

        if(customer==null)
        {
        	dataMap.put("returnUrl","redirect:/index.htm");
			appDTO.setControllerMap(dataMap);
			appDTO.setErrorMessage(getResourceMessage(NOT_AUTHORISED));
			return appDTO;
        }
        else if(custAddress==null)
        {
        	dataMap.put("returnUrl","redirect:/cart.htm");
			appDTO.setControllerMap(dataMap);
			appDTO.setErrorMessage(getResourceMessage(ADDRESS_INVALID));
			return appDTO;        	
        }
      
        /* create an order */
        
        OrdersStatus orderStat=ordersDao.getOrderStatusByName("PENDING");
        
        TransactionManager.begin();

        Orders orders=new Orders();
        orders.setAddress(custAddress.getAddress());
        orders.setCustomer(customer);
        orders.setDateAdded(DateUtils.getCurrentDate());
        orders.setDateModified(DateUtils.getCurrentDate());
        orders.setFullName(custAddress.getFullName());
        orders.setLocality(custAddress.getLocality());
        orders.setMobile(custAddress.getMobile());
        orders.setOrdersStatus(orderStat);
        orders.setPaymentType(PAYMENT_TYPE_ONLINE);
        orders.setPincode(custAddress.getPinCode());
        orders.setState(custAddress.getState());
        orders.setTotalAmount(new BigDecimal("0.00"));
        TransactionManager.txCreate(orders);
       
        Session session= TransactionManager.getInstance().getTxSession();
        session.flush();
        
       
        for(int i=0;i<productArray.length();i++)
		{
        	
			JSONObject prodObj=productArray.getJSONObject(i);
			Product product=productDao.getProductById((Integer)prodObj.get("prodId"));
			
			if(productService.isProductAvaialble(product))
			{
			int	quantity=prodObj.getInt("quantity");
			int sizeId= prodObj.getInt("sizeId");
			
			Size size=productDao.getSizeById(sizeId);
			
			JSONArray designAttrSpec=(JSONArray)prodObj.get("designAttributeSpecs");

			
			BigDecimal itemCost=product.getOfferPrice().multiply(new BigDecimal(quantity));
			totalPrice=totalPrice.add(itemCost);
			
			OrderProduct orderProd=new OrderProduct();
			orderProd.setDesignAttributeSpecification(designAttrSpec.toString());
			orderProd.setOrder(orders);
			orderProd.setProduct(product);
			orderProd.setQuantity(quantity);
			orderProd.setTotalAmount(itemCost);
			orderProd.setUnitPrice(product.getOfferPrice());
			orderProd.setSize(size);
			TransactionManager.txCreate(orderProd);
						
			}
			else
			{
				dataMap.put("returnView","redirect:/cart.htm");
				appDTO.setControllerMap(dataMap);
				appDTO.setErrorMessage(getResourceMessage("Product "+product.getName()+
						" in your cart is not available. Please remove and checkout the rest of products." ));
				
				TransactionManager.rollback();
				return appDTO;
				
				
			}
			
			
		}
        
        /* update orders with total amount */
        orders.setTotalAmount(totalPrice);
        TransactionManager.txUpdate(orders);
        
        OrderHistory orderHistory=new OrderHistory();
        orderHistory.setComment("order generated with order id -"+orders.getId());
        orderHistory.setDateAdded(DateUtils.getCurrentDate());
        orderHistory.setNotify(false);
        orderHistory.setOrder(orders);
        orderHistory.setOrdersStatus(orderStat);
        TransactionManager.txCreate(orderHistory);
        
        TransactionManager.commit();

        StringBuffer pgUrl=new StringBuffer();
        pgUrl.append("&tid=100&merchant_id="+PG_MERCHANT_ID);
        pgUrl.append("&order_id="+orders.getId());
        pgUrl.append("&currency="+PG_CURRENCY);
        pgUrl.append("&amount="+orders.getTotalAmount());
        pgUrl.append("&redirect_url="+PG_REDIRECT_URL);
        pgUrl.append("&cancel_url="+PG_CANCEL_URL);
        pgUrl.append("&language="+PG_LANGUAGE);
        pgUrl.append("&billing_name="+orders.getCustomer().getFullName());
        pgUrl.append("&billing_address="+custAddress.getAddress());
        pgUrl.append("&billing_tel="+custAddress.getMobile());
        pgUrl.append("&billing_city="+custAddress.getLocality());
        pgUrl.append("&billing_state="+custAddress.getState().getName());
        pgUrl.append("&billing_zip="+custAddress.getPinCode());
        pgUrl.append("&billing_country=India");
        pgUrl.append("&billing_email="+orders.getCustomer().getEmail());
        
        AesCryptUtil aesUtil = new AesCryptUtil(PG_WORKING_KEY);
        
        String encRequest = aesUtil.encrypt(pgUrl.toString());
        String url=PG_URL+encRequest+"&access_code="+PG_ACCESS_CODE;

        dataMap.put("pgUrl",PG_URL);
        dataMap.put("encRequest",encRequest);
        dataMap.put("accessCode",PG_ACCESS_CODE);
        dataMap.put("returnView","paymentInit");

        
        appDTO.setControllerMap(dataMap);
		appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		
		return appDTO;
		}
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			TransactionManager.rollback();
			
		
		}
		

        dataMap.put("returnView","redirect:/index.htm");
		appDTO.setControllerMap(dataMap);
		appDTO.setResponseStatus(ResponseStatus.ERROR);
		return appDTO;
	}
	
	public AppDTO finalizePayment(AppDTO appDTO)
	{
		Map dataMap=appDTO.getControllerMap();
		try{
		
			String encResp=(String)dataMap.get("encResp");
			AesCryptUtil aesUtil=new AesCryptUtil(PG_WORKING_KEY);
			String decResp = aesUtil.decrypt(encResp);
			StringTokenizer tokenizer = new StringTokenizer(decResp, "&");
			Hashtable hs=new Hashtable();
			String pair=null, pname=null, pvalue=null;
			while (tokenizer.hasMoreTokens()) {
				pair = (String)tokenizer.nextToken();
				if(pair!=null) {
					StringTokenizer strTok=new StringTokenizer(pair, "=");
					pname=""; pvalue="";
					if(strTok.hasMoreTokens()) {
						pname=(String)strTok.nextToken();
						if(strTok.hasMoreTokens())
							pvalue=(String)strTok.nextToken();
						hs.put(pname, pvalue);
					}
				}
			}
			
			
			String paymentStatus=(String)hs.get("order_status");
			int orderId=Integer.parseInt((String)hs.get("order_id"));
			Orders orders=ordersDao.getOrderByOrderId(orderId);
			OrdersStatus orderStat;

			/*
			 * If payment failed cancel the order .
			 * If payment success order status processing
			 */
			if(paymentStatus.equals(PaymentStatus.SUCCESS.getStatus()))
			{
				orderStat=ordersDao.getOrderStatusByName("PROCESSING");
				dataMap.put("STATUS","SUCCESS");
				getHttpSession().removeAttribute(SESSION_CART);
				appDTO.setInfoMessage("Thankyou for shopping with us . Your Order is placed with order id :"+orders.getId());
				dataMap.put("returnView", "redirect:/userPreference.htm?res=ORDERS");
			}
			else if(paymentStatus.equals(PaymentStatus.ABORTED.getStatus()))
			{
				orderStat=ordersDao.getOrderStatusByName("VOID");
				appDTO.setErrorMessage(getResourceMessage(PAYMENT_DECLINED));
				dataMap.put("returnView", "redirect:/cart.htm");
				dataMap.put("STATUS","FAILURE");
				
			}
			else
			{
		        orderStat=ordersDao.getOrderStatusByName("CANCELLED");
				appDTO.setErrorMessage(getResourceMessage(PAYMENT_FAILED_TRY_AGAIN));
				dataMap.put("returnView", "redirect:/cart.htm");
				dataMap.put("STATUS","FAILURE");
			}
			
			orders.setOrdersStatus(orderStat);
			orders.setPaymentStatus(paymentStatus);
			orders.setPgResponse(decResp);
			ordersDao.updatePaymentStatus(orders);
			
			
			
			appDTO.setControllerMap(dataMap);

		}
		catch(Exception e)
		{
		appDTO.setErrorMessage(getResourceMessage(PAYMENT_FAILED_TRY_AGAIN));
		dataMap.put("returnView", "redirect:/cart.htm");
		dataMap.put("STATUS","FAILURE");
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

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public OrdersDao getOrdersDao() {
		return ordersDao;
	}

	public void setOrdersDao(OrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	

}
