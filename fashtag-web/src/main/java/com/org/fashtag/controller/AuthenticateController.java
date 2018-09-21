package com.org.fashtag.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RespectBinding;

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
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.common.Validation;
import com.org.fashtag.controller.BaseController;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerBodyMeasurement;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.service.AuthenticateService;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.PasswordEncryptor;


@Controller
public class AuthenticateController extends BaseController {
	
	@Autowired
	private AuthenticateService authenticateService;

	
	@RequestMapping("/changePassword")
	public ModelAndView changePassword(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("changePassword");
		String userId=request.getParameter("userId");
		mv.addObject("USERID",userId);
		return mv;
	}
	
	@RequestMapping("/setUserPassword")
	public @ResponseBody String setUserPassword(HttpServletRequest request) {

		JSONObject respObj = new JSONObject();
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		
		try{
		String userId=request.getParameter("userId");
		String password=request.getParameter("password");
		
		dataMap.put("USERID", userId);
		dataMap.put("PASSWORD",password);
		appDTO.setControllerMap(dataMap);
		appDTO=authenticateService.setUserPassword(appDTO);
		if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
		{
		Customer customer=(Customer)appDTO.getControllerMap().get("CUSTOMER");
		respObj.put("message", appDTO.getInfoMessage());
		respObj.put("isFirstLogin", customer.getIsFirstLogin());
		respObj.put("status", "SUCCESS");
		respObj.put("userId",userId);

		}
		else
		{
			respObj.put("message", appDTO.getErrorMessage());
			respObj.put("status", "ERROR");
				
		}
		}
		catch(Exception e){ 
			e.printStackTrace();
			}
		return respObj.toString();
	}

	@RequestMapping(value="/signIn", method = RequestMethod.POST)
	public @ResponseBody String signIn(HttpServletRequest request)
	{
		    JSONObject respObj = new JSONObject();
			AppDTO appDTO=new AppDTO();
			Map dataMap=new HashMap();
			String backUrl=request.getHeader("Referer");
			
			try{
			String userId=request.getParameter("user_id");
			String password= request.getParameter("password");
			if((!CommonUtils.isEmpty(userId)&&!CommonUtils.isEmpty(password)))
			{
			dataMap.put("USERID",userId);
			dataMap.put("PASSWORD",PasswordEncryptor.encrypt(password));
			appDTO.setControllerMap(dataMap);
			appDTO=authenticateService.signin(appDTO);
			if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
			{
				Customer customer=(Customer)appDTO.getControllerMap().get("CUSTOMER");
				respObj.put("status", "SUCCESS");
				respObj.put("isFirstLogin", customer.getIsFirstLogin());
				respObj.put("userId", userId);
				respObj.put("message", appDTO.getInfoMessage());	
				respObj.put("backUrl",backUrl);
			}
			else
			{	
				respObj.put("status", "ERROR");
				respObj.put("message", appDTO.getErrorMessage());
			}
			}
			else
			{
				respObj.put("status", "ERROR");
				respObj.put("message", getResourceMessage("EMPTY_USER_ID_PASSWORD"));
				
			}
			
			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return respObj.toString();
	}
	
	
	@RequestMapping(value="/signUp",method = RequestMethod.POST)
	public @ResponseBody String signUp(@ModelAttribute Customer customer,HttpServletRequest request)
	{
		
			JSONObject respObj = new JSONObject();
			String backUrl=request.getHeader("Referer");
			AppDTO appDTO=new AppDTO();
			Map dataMap=new HashMap();
			try{
			
			String userId=request.getParameter("userId");
			String password=customer.getPassword();
			String fullName=customer.getFullName();
			
			if(CommonUtils.isEmpty(userId)
					||CommonUtils.isEmpty(password)
					||CommonUtils.isEmpty(fullName))
				
			{
				respObj.put("STATUS","ERROR");
				respObj.put("MESSAGE",getResourceMessage("ENTER_ALL_FIELDS"));
				return respObj.toString();
			}
			else if(customer.getFullName().length()>100)
			{
				respObj.put("STATUS","ERROR");
				respObj.put("MESSAGE",getResourceMessage(NAME_TOO_LONG));
				return respObj.toString();
			}
			else if(!(customer.getGender()>0)){
				respObj.put("STATUS","ERROR");
				respObj.put("MESSAGE",getResourceMessage("GENDER_EMPTY"));
				return respObj.toString();
			}
			else if(Validation.isValidEmail(userId))
				{ customer.setEmail(userId); }
			else if(Validation.isValidMobile(userId))
				{ customer.setPhone(userId); }
			else
			{
				respObj.put("STATUS","ERROR");
				respObj.put("MESSAGE",getResourceMessage("ENTER_VALID_MOBILE_OR_EMAIL"));
				return respObj.toString();
			}
				
			customer.setPassword(PasswordEncryptor.encrypt(customer.getPassword()));
			dataMap.put("CUSTOMER",customer);
			dataMap.put("USERID",userId);
			appDTO.setControllerMap(dataMap);
			appDTO=authenticateService.signup(appDTO);

			if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
			{
				respObj.put("STATUS","SUCCESS");
				respObj.put("USERID",userId);
				respObj.put("backUrl",backUrl);
				
			}
			else
			{	
				respObj.put("STATUS","ERROR");
				respObj.put("MESSAGE",appDTO.getErrorMessage());
				return respObj.toString();

			}
			
			}
			catch(Exception e)
			{
			
			}
			return respObj.toString();
	}
	
	/*
	 * This URL handles only the first login customer's size attribute.
	 * Others should have size attribute based on profile
	 */
	@RequestMapping(value="/getCustomerSizeAttribute", method = RequestMethod.POST)
	public @ResponseBody String getCustomerSizeAttribute(HttpServletRequest request)
	{
		String userId=(String) request.getParameter("userId");
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		JSONObject respObj = new JSONObject();
		String backUrl=request.getHeader("Referer");

		try{
			
			
		
		dataMap.put("USERID", userId);
		appDTO.setControllerMap(dataMap);
		appDTO=authenticateService.getCustomerSizeAttributes(appDTO);
if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
{
		dataMap=appDTO.getControllerMap();
		Collection measurementAttributeColl=(Collection) dataMap.get("SIZEATTRIBUTES");
		Customer customer=(Customer) dataMap.get("CUSTOMER");
		CustomerProfile customerProfile=(CustomerProfile) dataMap.get("CUSTOMER_PROFILE");
		 String profileName=customer.getFullName();
		 int profileId=0;
		 
	       if(customerProfile!=null) {
	    	   profileName=customerProfile.getName();
	    	   profileId=customerProfile.getId();
	    	   
	       }
		
		
		JSONArray jsonArray=new JSONArray();
		
     Iterator iter= measurementAttributeColl.iterator();
		while(iter.hasNext())
       {
			JSONObject jsonObj=new JSONObject();
			Object[] measureAttr=(Object[]) iter.next();
			BodyMeasurementAttribute bodyMeasurementAttribute=(BodyMeasurementAttribute) measureAttr[1];
			CustomerBodyMeasurement	customerBodyMeasurement=(CustomerBodyMeasurement) measureAttr[0];
			
			String customerMeasurement="";
			if(customerBodyMeasurement!=null)
			{
				customerMeasurement=""+customerBodyMeasurement.getValue();
			}
			
			
			jsonObj.put("name",bodyMeasurementAttribute.getName());
			jsonObj.put("description", bodyMeasurementAttribute.getDescription());
			jsonObj.put("id", bodyMeasurementAttribute.getId());
			jsonObj.put("customerValue", customerMeasurement);
			jsonArray.put(jsonObj);
		}
		
       respObj.put("sizeAttributeColl",jsonArray);
       respObj.put("USERID",userId);
       respObj.put("STATUS","SUCCESS");
       respObj.put("profileName", profileName);
       respObj.put("profileId", profileId);
       respObj.put("backUrl",backUrl);

}
else
{
    respObj.put("STATUS","ERROR");
    respObj.put("MESSAGE",appDTO.getErrorMessage());

}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		String str=respObj.toString();
		
		return respObj.toString();
	}
	
	/*
	 * Common method used for customer settings also.
	 */
	@RequestMapping("/addCustomerSize")
	public ModelAndView addCustomerSize(HttpServletRequest request)
	{
		String backUrl=request.getHeader("Referer");
		if(CommonUtils.isEmpty(backUrl)) {backUrl="redirect:/index.htm";}
		ModelAndView mv =new ModelAndView("redirect:"+backUrl);
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		
		try{

			String userId=request.getParameter("userId");
			int profileId=Integer.parseInt(request.getParameter("profileId"));
			String[] sizeAttrVal=request.getParameterValues("sizeAttrVal");
			String[] sizeAttrId=request.getParameterValues("sizeAttrId");
			String profileName=request.getParameter("profileName");
			
			dataMap.put("SIZEATTRVAL", sizeAttrVal);
			dataMap.put("SIZEATTRID", sizeAttrId);
			dataMap.put("USERID",userId);
			dataMap.put("PROFILEID",profileId);
			dataMap.put("PROFILENAME", profileName);
			appDTO.setControllerMap(dataMap);
			appDTO=authenticateService.addCustomerRequiredSize(appDTO);
			if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
				setInfo_message(appDTO.getInfoMessage(), true);
			else
				setError_message(appDTO.getErrorMessage(), true);
			
		}
		catch(Exception e)
		{
			
		}
		return mv;
	}
	
	@RequestMapping(value="/forgotPassword", method = RequestMethod.POST)
	public @ResponseBody String forgetPassword(HttpServletRequest request)
	{
		String backUrl=request.getHeader("Referer");
		JSONObject respObj=new JSONObject();
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		try{
			String userId=request.getParameter("userId");
			dataMap.put("userId",userId);
			appDTO.setControllerMap(dataMap);
			appDTO=authenticateService.forgotPassword(appDTO);
			
			if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
			{
				respObj.put("MESSAGE", appDTO.getInfoMessage());
				respObj.put("STATUS","SUCCESS");
				respObj.put("backUrl",backUrl);
			
			}
				else
				{
			
					respObj.put("MESSAGE", appDTO.getErrorMessage());
					respObj.put("STATUS","FAILURE");
					respObj.put("backUrl",backUrl);
				
				}
			
		}
		catch(Exception e)
		{
			setError_message(getResourceMessage(UNEXPECTED_ERROR), true);
			e.printStackTrace();
		}
		
		
		return respObj.toString();
		
	}
	
	
	
	
	@RequestMapping("/updatePassword")
	public ModelAndView updatePassword(HttpServletRequest request)
	{
		String backUrl=request.getHeader("Referer");
		ModelAndView mv =new ModelAndView("redirect:"+backUrl);
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		
		try{

			String oldPass=request.getParameter("oldPass");
			String newPass=request.getParameter("newPass");
			String confirmPass=request.getParameter("confirmPass");

			if(CommonUtils.isEmpty(oldPass)||CommonUtils.isEmpty(newPass)||CommonUtils.isEmpty(confirmPass))
			{
				setError_message(getResourceMessage(ALL_FIELDS_MANDATORY), true);
				return mv;
			}
			else if(!newPass.equals(confirmPass))
			{
				setError_message(getResourceMessage(PASSWORDS_NOT_MATCHING), true);
				return mv;
			}
				
			
			
			
			dataMap.put("oldPass", oldPass);
			dataMap.put("newPass", newPass);
			dataMap.put("confirmPass",confirmPass);
			appDTO.setControllerMap(dataMap);
			
			appDTO=authenticateService.updatePassword(appDTO);
			if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
			{
				setInfo_message(appDTO.getInfoMessage(), true);
			}
				else
				{
					setError_message(appDTO.getErrorMessage(), true);
				}
			
		}
		catch(Exception e)
		{
			setError_message(getResourceMessage(UNEXPECTED_ERROR), true);

		}
		return mv;
	}
	
	
	@RequestMapping("/setUserProfile")
	public @ResponseBody String setUserProfile(HttpServletRequest request)
	{
		JSONObject json=new JSONObject();
		try{
		
			int profileId=Integer.parseInt(request.getParameter("profileId"));
		
			AppDTO appDTO =new AppDTO();
			Map dataMap=new HashMap();
			dataMap.put("profileid",profileId);
			appDTO.setControllerMap(dataMap);
		authenticateService.setSessCustomerProfile(appDTO);
		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return json.toString();
		
	}
	@RequestMapping("/logout")
	@ResponseBody
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response) {
		
		/*
		 * For invalidating cached back button in browser
		 */
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0); 
	        
	    
		ModelAndView mv=new ModelAndView("redirect:/index.htm");
		getHttpSession().invalidate();
	
		
		
		return mv;
		}

	
	

	/* getters and setters */

	
	
	public AuthenticateService getAuthenticateService() {
		return authenticateService;
	}


	public void setAuthenticateService(AuthenticateService authenticateService) {
		this.authenticateService = authenticateService;
	}
	
		
	/* methods */
	
	
	
}
