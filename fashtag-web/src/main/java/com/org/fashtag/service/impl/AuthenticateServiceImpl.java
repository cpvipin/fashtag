package com.org.fashtag.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.aop.EmailNotifier;
import com.org.fashtag.common.AppCoreConstants;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.context.ApplicationContext;
import com.org.fashtag.context.TransactionManager;
import com.org.fashtag.context.WebBeanConstants;
import com.org.fashtag.dao.CustomerDao;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerBodyMeasurement;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.notify.MailSender;
import com.org.fashtag.notify.SMSSender;
import com.org.fashtag.service.AuthenticateService;
import com.org.fashtag.service.CartService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.DateUtils;
import com.org.fashtag.util.PasswordEncryptor;

public class AuthenticateServiceImpl extends BaseServiceImpl implements AuthenticateService,WebBeanConstants {
	
	private CustomerDao customerDao;
	
	private CartService cartService;
	
	
	
	public AppDTO signin(AppDTO appDTO)
	{
		try{
		appDTO=customerDao.customerSignIn(appDTO);

		Map dataMap=appDTO.getControllerMap();
		appDTO=customerDao.customerSignIn(appDTO);
		Customer customer = (Customer) appDTO.getControllerMap().get("CUSTOMER");
		
		
	if(customer!=null && customer.getId().intValue()>0)
		{
			
			/* 
			 * if first login user session will be 
			 * set only after changing the password
			 * While setting session put cart added in session to DB
			 */
			if(!customer.getIsFirstLogin())
			{
				cartService.mergeSessionAndUserCart(customer.getCart());
				customer.setCart(cartService.getSessionCart());
				customerDao.updateCustomer(customer);
				setCustomerSession(customer);
			}
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			return appDTO;
		}
		else
		{
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage("USER_NAME_PASSWORD_MISMATCH"));
		}
		
		}
		catch(Exception e)
		{
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage("USER_NAME_PASSWORD_MISMATCH"));

		}
		return appDTO;
	}
	
	
	
	
	
	
	public AppDTO signup(AppDTO appDTO)
	{
		try{
			Map dataMap=appDTO.getControllerMap();
			Customer customer=(Customer) dataMap.get("CUSTOMER");
			String userId=(String) dataMap.get("USERID");
			
			Customer existCust=customerDao.getCustomerByUserId(userId);
			if(existCust!=null){
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				appDTO.setErrorMessage(getResourceMessage("USER_EXISTS"));
				return appDTO;
			}
			
			customer.setDateAdded(DateUtils.getCurrentDate());
			customer.setDateModified(DateUtils.getCurrentDate());
			
			customer.setActiveStatus(true);
			customer.setIsAdminAdded(false);
			customer.setIsFirstLogin(false);
			customer.setNewsLetter(false);
			
			/*cartService.mergeSessionAndUserCart(customer.getCart());
			*/
			
			customer.setCart(cartService.getSessionCart());
			customerDao.createCustomer(customer);
			
			setCustomerSession(customer);
			
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		}
		catch(Exception e)
		{
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage("UNEXPECTED_ERROR"));
			
		}
		return appDTO;
	}
	
	


	
	public AppDTO setUserPassword(AppDTO appDTO) {
		try{
		Map dataMap=appDTO.getControllerMap();
		
		String userId=(String)dataMap.get("USERID");
		String password=(String)dataMap.get("PASSWORD");
		Customer customer=customerDao.getCustomerByUserId(userId);
		customer.setPassword(PasswordEncryptor.encrypt(password));
		customerDao.update(customer);
	    setCustomerSession(customer);
		
	    dataMap.put("CUSTOMER",customer);
	    appDTO.setControllerMap(dataMap);
	    appDTO.setInfoMessage(getResourceMessage("PASSWORD_UPDATED"));
	    appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		}
		catch(Exception e)
		{
			appDTO.setErrorMessage(getResourceMessage("UNEXPECTED_ERROR"));
			appDTO.setResponseStatus(ResponseStatus.ERROR);
		}
		return appDTO;
	
	}	
	
	







	@Override
	public AppDTO updatePassword(AppDTO appDTO) {
		try{
			Map dataMap=appDTO.getControllerMap();
			
			String oldPass=(String)dataMap.get("oldPass");
			String newPass=(String)dataMap.get("newPass");
			String confirmPass=(String)dataMap.get("confirmPass");

			Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
			customer=customerDao.getCustomerById(customer.getId());
			
			if(!PasswordEncryptor.encrypt(oldPass).equals(customer.getPassword()))
			{
				appDTO.setErrorMessage(getResourceMessage(WRONG_PASSWORD));
				appDTO.setResponseStatus(ResponseStatus.ERROR);
				
				return appDTO;
			}
			
			
			customer.setPassword(PasswordEncryptor.encrypt(newPass));
			customerDao.update(customer);
		    setCustomerSession(customer);
			
		    dataMap.put("CUSTOMER",customer);
		    appDTO.setControllerMap(dataMap);
		    appDTO.setInfoMessage(getResourceMessage(PASSWORD_UPDATED));
		    appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			}
			catch(Exception e)
			{
				appDTO.setErrorMessage(getResourceMessage(UNEXPECTED_ERROR));
				appDTO.setResponseStatus(ResponseStatus.ERROR);
			}
			return appDTO;
	}
	

	
	/*
	 * (non-Javadoc)
	 * @see com.org.fashtag.service.AuthenticateService#getCustomerSizeAttributes(com.org.fashtag.AppDTO)
	 * This method assumes customer has only one profile, ie, for the first login customer.
	 * Return customer size attribute(only required) based on customer gender and customer profile.
	 * Already entered customer values will be returned based on customer profile.
	 * Befor accessing this method a user session should be set. After getting the size attributes isFirstLogin set to false.
	 * No need to show measurement form every time user login.
	 */
	
	public AppDTO getCustomerSizeAttributes(AppDTO appDTO)
	{
		try{
			
		
		String loggedInCustomer=(String)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER_USERID);
		if(!CommonUtils.isEmpty(loggedInCustomer))
		{
			Map dataMap=appDTO.getControllerMap();
			String userId=(String) dataMap.get("USERID");
			Customer customer=customerDao.getCustomerByUserId(userId);
			
			CustomerProfile customerProfile=customerDao.getOnlyProfileOfCustomer(customer);
			
			customer.setIsFirstLogin(false);
			customerDao.updateCustomer(customer);
					
			dataMap.put("CUSTOMER",customer);
			dataMap.put("CUSTOMER_PROFILE", customerProfile);
			appDTO.setControllerMap(dataMap);
			appDTO=customerDao.getRequiredCustomerSizeAttributesByGender(appDTO);
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		}
		else
		{
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage("NOT_AUTHORISED"));
			
			
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return appDTO;
		
		
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * @see com.org.fashtag.service.AuthenticateService#addCustomerRequiredSize(com.org.fashtag.AppDTO)
	 * Delete existing customer size for the specific profile using profile id
	 * add new customer sizes against profile.
	 * Only required size is manipulating here. All other attributes should not touch in this method
	 * existing profile is coming from admin side entry
	 * Set customer profile to session at the end which will be the default profile
	 */
	public AppDTO addCustomerRequiredSize(AppDTO appDTO)
	{
		Map dataMap=appDTO.getControllerMap();
		try
		{
		int profileId=(Integer) dataMap.get("PROFILEID");
		String[] sizeAttrVal=(String[]) dataMap.get("SIZEATTRVAL");
		String[] sizeAttrId=(String[]) dataMap.get("SIZEATTRID");
		String profileName=(String)dataMap.get("PROFILENAME");
		String userId=(String) dataMap.get("USERID");
		Customer customer=customerDao.getCustomerByUserId(userId);
		if(CommonUtils.isEmpty(profileName))
		{
			profileName=customer.getFullName();
		}
		CustomerProfile custProf;
		TransactionManager.begin();
		if(profileId>0)
		{
			custProf=customerDao.getCustomerProfileByProfileId(profileId);
			if(!custProf.getName().equals(profileName))
			{
				custProf.setName(profileName);
				customerDao.updateCustomerProfile(custProf);
			}
			customerDao.deleteCustomerMeasurementsByProfile(profileId);
		}
		else
		{
custProf=new CustomerProfile();
custProf.setName(profileName);
custProf.setCustomer(customer);
custProf.setDateAdded(DateUtils.getCurrentDate());
custProf.setDateModified(DateUtils.getCurrentDate());
custProf.setIsDefault(true);
custProf.setGender(customer.getGender());
customerDao.createCustomeProfile(custProf);
		}
		
		for(int i=0; i<sizeAttrId.length;i++)
		{
			int attrId=Integer.parseInt(sizeAttrId[i]);
			int attrVal=0;
			
			if(!CommonUtils.isEmpty(sizeAttrVal[i]))
			{
				attrVal=Integer.parseInt(sizeAttrVal[i]);
			}
			
			CustomerBodyMeasurement customerMeasurement=new CustomerBodyMeasurement();
			BodyMeasurementAttribute bodyMeasurement=customerDao.getBodyBodyMeasurementAttributeBbyId(attrId);
			customerMeasurement.setBodyMeasurementAttribute(bodyMeasurement);
			customerMeasurement.setCustomerProfile(custProf);
			customerMeasurement.setValue(attrVal);
			customerMeasurement.setUnit("CM");
			
			dataMap.put("CUSTOMER_BODY_MEASUREMENT",customerMeasurement);
			customerDao.createCustomerBodyMeasurement(appDTO);
			
		}
		TransactionManager.commit();
		
		/* customer profile has to add to session  */
		setCustomerSession(customer);
		
		appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		appDTO.setInfoMessage(getResourceMessage("MEASUREMENT_ADDED"));
		}
		catch(Exception e)
		{
			TransactionManager.rollback();
			appDTO.setErrorMessage(getResourceMessage("MEASUREMENT_NOT_ADDED"));
			appDTO.setResponseStatus(ResponseStatus.ERROR);
		}
		return appDTO;
	}

	
	public AppDTO forgotPassword(AppDTO appDTO)
	{
		Map dataMap=appDTO.getControllerMap();
		try{
		String userId=(String)dataMap.get("userId");
		Customer customer=customerDao.getCustomerByUserId(userId);
		if(customer!=null)
		{
			String randomPass=PasswordEncryptor.generateRandomPassword();
			customer.setPassword(PasswordEncryptor.encrypt(randomPass));
			
			customer.setIsFirstLogin(true);
			customerDao.updateCustomer(customer);
			StringBuffer msgBody = new StringBuffer();
			if(!CommonUtils.isEmpty(customer.getEmail()))
			{
				EmailNotifier emailNotify=new EmailNotifier();
				emailNotify.sendTemporaryPassword(customer,randomPass);
				
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			appDTO.setInfoMessage(getResourceMessage(PASSWORD_SEND_TO_EMAIL));
			}
			else if(!CommonUtils.isEmpty(customer.getPhone()))
			{
				msgBody.append("Hi ")
				.append(customer.getFullName());
				msgBody.append("\n\n").append(
				"Your new password is generated . Please login using below password.");
				msgBody.append("\n").append("Password  : ").append(customer.getPassword());
				SMSSender smsSender = SMSSender.getSoleInstance();
				smsSender.sendMessage(customer.getPhone(),msgBody.toString());
				appDTO.setInfoMessage(getResourceMessage(PASSWORD_SEND_TO_MOBILE));
				appDTO.setResponseStatus(ResponseStatus.SUCCESS);
				
			}
				
			
			
			
		}
		else
		{
			appDTO.setErrorMessage(getResourceMessage(USER_DOESNOT_EXIST));
			appDTO.setResponseStatus(ResponseStatus.ERROR);
		}
		
		}
		catch(Exception e)
		{
			appDTO.setErrorMessage(getResourceMessage(UNEXPECTED_ERROR));
			appDTO.setResponseStatus(ResponseStatus.ERROR);
		}
		return appDTO;
		
	}
	
	public AppDTO setSessCustomerProfile(AppDTO appDTO)
	{
		try{

			Map dataMap=appDTO.getControllerMap();
			int profileId=(Integer)dataMap.get("profileid");
			
			CustomerProfile custProf=customerDao.getCustomerProfileByProfileId(profileId);
			Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
			if(customer!=null &&( custProf.getCustomer().getId().intValue()==customer.getId().intValue()  ))
			{
				this.setCustomerProfile(custProf);	
				
			}
			
			
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			
		}
		catch(Exception e)
		{
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			e.printStackTrace();
		}
		
		return appDTO;
	}
	
	/* methods */
	
	private void setCustomerProfile(CustomerProfile customerProfile)
	{
		/* avoid vulnerablity by setting all info of customer to session */
		customerProfile.setCustomer(null);
		getHttpSession().setAttribute(LOGGED_IN_CUSTOMER_PROFILE,customerProfile);
	}
	private void validateSession(Customer customer) {
		try {
			HttpSession aSession = getHttpSession();
			HttpSession oldSess = (HttpSession) ApplicationContext.getActiveSessionMap().get(customer.getId());
			if (oldSess != null	&& !CommonUtils.nullSafeObjectEquals(oldSess.getId(),aSession.getId())) {
				oldSess.invalidate();
			}
			ApplicationContext.getActiveSessionMap().put(customer.getId(),aSession);
		} 
		catch (Exception e) {
			
		}
	}
	
	
	private void setCustomerSession(Customer customer)
	{
		String userId= customer.getEmail()!=null?customer.getEmail():customer.getPhone();
		CustomerProfile custProf=customerDao.getDefaultProfile(customer);
		
		if(custProf!=null)
		{
			custProf.setCustomer(null);
			getHttpSession().setAttribute(LOGGED_IN_CUSTOMER_PROFILE,custProf);
			
		}
		
		
		
		//set password  to null for avoiding vulnerablity
		customer.setPassword(null);
		
		getHttpSession().setAttribute(LOGGED_IN_CUSTOMER_USERID,userId);
		getHttpSession().setAttribute(LOGGED_IN_CUSTOMER,customer);
		
		
		
		validateSession(customer);
		
	}
	

	


	/* getters and setters */

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}






	public CartService getCartService() {
		return cartService;
	}






	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}







	

}
