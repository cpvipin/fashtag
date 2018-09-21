package com.org.fashtag.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.RespectBinding;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.common.Validation;
import com.org.fashtag.controller.BaseController;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerAddress;
import com.org.fashtag.model.CustomerBodyMeasurement;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.service.AuthenticateService;
import com.org.fashtag.service.CustomerService;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.PasswordEncryptor;


@Controller
public class CustomerController extends BaseController {
	
	@Autowired
	private CustomerService customerService;
	

	@Autowired
	private AuthenticateService authenticateService;

	
	
	@RequestMapping(value="/addWishList", method = RequestMethod.POST)
	public @ResponseBody String addWishList(HttpServletRequest request)
	{
		JSONObject respObj=new JSONObject();
		
	try{
		int productId=Integer.parseInt(request.getParameter("prodId"));
		Customer customer=getLoggedInCustomer();
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		dataMap.put("PRODUCT_ID",productId);
		dataMap.put("CUSTOMER",customer);
		appDTO.setControllerMap(dataMap);
		customerService.addWishList(appDTO);
		if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
		{
			respObj.put("MESSAGE",appDTO.getInfoMessage());
			respObj.put("STATUS", "SUCCESS");
		}
		else
		{
			respObj.put("MESSAGE",appDTO.getErrorMessage());
			respObj.put("STATUS", "ERROR");
			
		}
		
	}catch(Exception e)
	{
		e.printStackTrace();
	}
		
	return respObj.toString();
	}
	
	

	@RequestMapping(value="/cancelOrder")
	public ModelAndView cancelOrder(HttpServletRequest request)
	{
	
		ModelAndView mv=new ModelAndView("redirect:/userPreference.htm?res=ORDERS");
		try{
				AppDTO appDTO=new AppDTO();
				Map dataMap=new HashMap();
				int orderid=Integer.parseInt(request.getParameter("orderid"));
				dataMap.put("orderid",orderid);

				appDTO.setControllerMap(dataMap);
				appDTO=customerService.cancelOrder(appDTO);
				
				
				dataMap=appDTO.getControllerMap();
				mv.addAllObjects(dataMap);
				
				if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
				{
				
					setError_message(appDTO.getErrorMessage(), true);
				}
				else
				{
					setInfo_message(appDTO.getInfoMessage(), true);
				}
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mv;
	
	}
	
	@RequestMapping(value="/returnOrder")
	public ModelAndView returnOrder(HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView("userPreference");
		try{
				AppDTO appDTO=new AppDTO();
				Map dataMap=new HashMap();
				
				String[] retProdArr=request.getParameterValues("selProdRet");
				int OrderId=Integer.parseInt(request.getParameter("orderid"));
				String backUrl=request.getHeader("Referer");

				
				dataMap.put("orderid",OrderId);
				dataMap.put("retProdArr",retProdArr);
				
				appDTO.setControllerMap(dataMap);
				
				appDTO=customerService.returnOrderProducts(appDTO);
				
				
				dataMap=appDTO.getControllerMap();
				mv.addAllObjects(dataMap);
				
				if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
				{
					mv.setViewName("redirect:"+backUrl);
					setError_message(appDTO.getErrorMessage(), true);
				}
				else
				{
					mv.setViewName("redirect:/userPreference.htm?res=ORDERS");
					setInfo_message(appDTO.getInfoMessage(), true);
				}
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			mv.setViewName("redirect:/index.htm");
			
		}
				
				return mv;
		
	}
	
	
	@RequestMapping(value="/userPreference")
	public ModelAndView userPreference(HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView("userPreference");
try{
		String resource=request.getParameter("res");
		
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		dataMap.put("resource",resource);
		if(resource.equals("RETURNORDER"))
		{
		int orderId=Integer.parseInt(request.getParameter("orderid"));
		dataMap.put("orderid",orderId);
		}
		appDTO.setControllerMap(dataMap);
		appDTO=customerService.getUserPreference(appDTO);
		
		
		dataMap=appDTO.getControllerMap();
		mv.addAllObjects(dataMap);
		
		if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
		{
			mv.setViewName("redirect:/index.htm");
			setError_message(appDTO.getErrorMessage(), true);
		}
		else
		{
			setInfo_message(appDTO.getInfoMessage(), false);
		}
		
}
catch(Exception e)
{
	e.printStackTrace();
	setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
	mv.setViewName("redirect:/index.htm");
	
}
		
		return mv;
		
	}
	
	
	
	@RequestMapping(value="/getMeasurementAttribute", method = RequestMethod.POST)
	public @ResponseBody String getCustomerSizeAttribute(HttpServletRequest request)
	{
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		JSONObject respObj=new JSONObject();
		JSONArray respArray=new JSONArray();
		Customer customer=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
		/*String profileName=(String)request.getParameter("profileName");*/
		int gender=Integer.parseInt(request.getParameter("profileGender"));
		
		dataMap.put("GENDER",gender);
		/*dataMap.put("PROFILENAME", profileName);*/
		
		dataMap.put("CUSTOMER", customer);
		appDTO.setControllerMap(dataMap);
		try{

appDTO=customerService.getCustomerMeasurementAttributes(appDTO);
dataMap=appDTO.getControllerMap();

 respArray=(JSONArray)dataMap.get("attrArray");
		
if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
{
	respObj.put("attrColl",respArray);
	respObj.put("STATUS","SUCCESS");
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
	
	
	
	
	
	@RequestMapping(value="/addCustomerProfile")
	public ModelAndView addCustomerProfile(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("redirect:/userPreference.htm?res=PROFILE");
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		try{
		
		String profileName=request.getParameter("profileName");
		int profileGender=Integer.parseInt(request.getParameter("profileGender"));

		String[] sizeAttrVal=request.getParameterValues("sizeAttrVal");
		String[] sizeAttrId=request.getParameterValues("sizeAttrId");
		
		
		dataMap.put("profileName", profileName);
		dataMap.put("profileGender", profileGender);

		dataMap.put("sizeAttrVal", sizeAttrVal);
		dataMap.put("sizeAttrId", sizeAttrId);
		
		appDTO.setControllerMap(dataMap);
		appDTO=customerService.addNewCustomerProfile(appDTO);
		
		dataMap=appDTO.getControllerMap();
		
		if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
		{
			setInfo_message(appDTO.getInfoMessage(), true);	

		}
		else
		{
			setError_message(appDTO.getErrorMessage(), true);
		}
		
		
		}catch(Exception e)
		{
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);

		}
		
		
		return mv;
		
		
	}
	
	
	
	@RequestMapping(value="/editCustomerProfile")
	public @ResponseBody String editCustomerProfile(HttpServletRequest request)
	{
		JSONObject respObj=new JSONObject();
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		try{
			int profileId=Integer.parseInt(request.getParameter("profileId"));
			dataMap.put("profileId",profileId);
			appDTO.setControllerMap(dataMap);
			appDTO=customerService.editCustomerProfile(appDTO);
			dataMap=appDTO.getControllerMap();
		
	
			if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
			{
				JSONArray measArray=(JSONArray)dataMap.get("measArr");
				respObj.put("measArray",measArray);
				respObj.put("profileId",profileId);
				respObj.put("profileName",(String)dataMap.get("profileName"));
				respObj.put("profileGender",(Integer)dataMap.get("profileGender"));
				respObj.put("STATUS","SUCCESS");
			}
			else
			{
				
				respObj.put("STATUS","ERROR");
			}
			
		}
		catch(Exception e)
		{
			
		}
		
		return respObj.toString();
	}
	
	
	
	

	@RequestMapping(value="/updateCustomerProfile")
	public ModelAndView updateCustomerProfile(HttpServletRequest request)
	{
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		ModelAndView mv=new ModelAndView();
		try
		{
		
			int profileId=Integer.parseInt(request.getParameter("profileId"));
			String profileName=request.getParameter("profileName");
			int profileGender=Integer.parseInt(request.getParameter("profileGender"));
			String[] sizeAttrVal=request.getParameterValues("sizeAttrVal");
			String[] sizeAttrId=request.getParameterValues("sizeAttrId");
			
			
			dataMap.put("profileId",profileId);
			dataMap.put("profileName",profileName);
			dataMap.put("profileGender",profileGender);

			dataMap.put("SIZEATTRVAL", sizeAttrVal);
			dataMap.put("SIZEATTRID", sizeAttrId);
			
			
			appDTO.setControllerMap(dataMap);
			appDTO=customerService.updateCustomerProfile(appDTO);
			if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
			{
				setInfo_message(appDTO.getInfoMessage(),true);
				mv.setViewName("redirect:/userPreference.htm?res=PROFILE");
			}
			else
			{
				setError_message(appDTO.getErrorMessage(), true);
				mv.setViewName("redirect:/userPreference.htm?res=PROFILE");
			}
		
		
		}
		catch(Exception e)
		{
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			e.printStackTrace();
		}
		
		return mv;
	}
	
	@RequestMapping(value="/deleteCustomerProfile", method = RequestMethod.GET)
	public ModelAndView deleteCustomerProfile(HttpServletRequest request)
	{
		ModelAndView mv =new ModelAndView("redirect:/userPreference.htm?res=PROFILE");

		try
		{
		int profileId=Integer.parseInt(request.getParameter("profileId"));
		Map dataMap=new HashMap();
		dataMap.put("profileId", profileId);
		AppDTO appDTO =new AppDTO();
		appDTO.setControllerMap(dataMap);
		customerService.deleteCustomerProfile(appDTO);
		
		if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
		{
			setInfo_message(appDTO.getInfoMessage(),true);
		}
		else
		{
			setError_message(appDTO.getErrorMessage(), true);
		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mv;
	}
	
	

	
	@RequestMapping(value="/chooseDefaultProfile", method = RequestMethod.GET)
	public ModelAndView chooseDefaultProfile(HttpServletRequest request)
	{
		ModelAndView mv =new ModelAndView("redirect:/userPreference.htm?res=PROFILE");

		try
		{
		int profileId=Integer.parseInt(request.getParameter("profileId"));
		Map dataMap=new HashMap();
		dataMap.put("profileId", profileId);
		AppDTO appDTO =new AppDTO();
		appDTO.setControllerMap(dataMap);
		appDTO=customerService.chooseDefaultProfile(appDTO);

		/* choose default profile will set that profile to session */
		
		authenticateService.setSessCustomerProfile(appDTO);
		
		
		if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
		{
			setInfo_message(appDTO.getInfoMessage(),true);
		}
		else
		{
			setError_message(appDTO.getErrorMessage(), true);
		}
		
		}
		catch(Exception e)
		{
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			e.printStackTrace();
		}
		
		return mv;
	}

	
	
	@RequestMapping(value="/addAddress")
	public @ResponseBody String addAddress(HttpServletRequest request)
	{
		JSONObject respObj=new JSONObject();
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		try{
			
			String fullName=request.getParameter("fullName");
			String locality=request.getParameter("locality");
			String address=request.getParameter("address");
			String pinCode=request.getParameter("pinCode");
			String mobile=request.getParameter("mobile");
			int state=Integer.parseInt(request.getParameter("state"));
			
			
		if(			CommonUtils.isEmpty(fullName)||
					CommonUtils.isEmpty(locality)||					
					CommonUtils.isEmpty(address)||
					CommonUtils.isEmpty(pinCode)||
					CommonUtils.isEmpty(mobile)					
			)
			{
				respObj.put("MESSAGE",getResourceMessage("ALL_FIELDS_MANDATORY"));
				respObj.put("STATUS","ERROR");
				return respObj.toString();
				
			}
			else if(state==0)
			{
				respObj.put("MESSAGE",getResourceMessage("ALL_FIELDS_MANDATORY"));
				respObj.put("STATUS","ERROR");
				return respObj.toString();
			}
			else if(!Validation.isValidMobile(mobile))
			{
				respObj.put("MESSAGE",getResourceMessage("INVALID_MOBILE"));
				respObj.put("STATUS","ERROR");
				return respObj.toString();
			}
				
			else
			{
				
			
			
			dataMap.put("fullName",fullName);
			dataMap.put("locality",locality);
			dataMap.put("state",state);
			dataMap.put("address",address);
			dataMap.put("pinCode",pinCode);
			dataMap.put("mobile",mobile);
			
			
			appDTO.setControllerMap(dataMap);
			appDTO=customerService.addCustomerAddress(appDTO);
			dataMap=appDTO.getControllerMap();
		
	
			if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
			{
				respObj.put("STATUS","SUCCESS");
				respObj.put("MESSAGE",appDTO.getInfoMessage());
			}
			else
			{
				respObj.put("MESSAGE",appDTO.getErrorMessage());
				respObj.put("STATUS","ERROR");
			}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return respObj.toString();
	}
	
	@RequestMapping(value="/deleteAddress", method = RequestMethod.GET)
	public ModelAndView deleteAddress(HttpServletRequest request)
	{
		ModelAndView mv =new ModelAndView("redirect:/userPreference.htm?res=ADDRESS");

		try
		{
		int addressId=Integer.parseInt(request.getParameter("addressId"));
		Map dataMap=new HashMap();
		dataMap.put("addressId", addressId);
		AppDTO appDTO =new AppDTO();
		appDTO.setControllerMap(dataMap);
		customerService.deleteCustomerAddress(appDTO);
		
		if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
		{
			setInfo_message(appDTO.getInfoMessage(),true);
		}
		else
		{
			setError_message(appDTO.getErrorMessage(), true);
		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mv;
	}
	
	
	
	@RequestMapping(value="/updateAddress")
	public @ResponseBody String updateAddress(HttpServletRequest request)
	{
		JSONObject respObj=new JSONObject();
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		try{
			
			String fullName=request.getParameter("fullName");
			String locality=request.getParameter("locality");
			String address=request.getParameter("address");
			String pinCode=request.getParameter("pinCode");
			String mobile=request.getParameter("mobile");
			int state=Integer.parseInt(request.getParameter("state"));
			
			
		if(			CommonUtils.isEmpty(fullName)||
					CommonUtils.isEmpty(locality)||					
					CommonUtils.isEmpty(address)||
					CommonUtils.isEmpty(pinCode)||
					CommonUtils.isEmpty(mobile)					
			)
			{
				respObj.put("MESSAGE",getResourceMessage("ALL_FIELDS_MANDATORY"));
				respObj.put("STATUS","ERROR");
				return respObj.toString();
				
			}
			else if(state==0)
			{
				respObj.put("MESSAGE",getResourceMessage("ALL_FIELDS_MANDATORY"));
				respObj.put("STATUS","ERROR");
				return respObj.toString();
			}
			else if(!Validation.isValidMobile(mobile))
			{
				respObj.put("MESSAGE",getResourceMessage("INVALID_MOBILE"));
				respObj.put("STATUS","ERROR");
				return respObj.toString();
			}
			
			dataMap.put("addressId",Integer.parseInt(request.getParameter("addressId")));
			dataMap.put("fullName",fullName);
			dataMap.put("locality",locality);
			dataMap.put("state",state);
			dataMap.put("address",address);
			dataMap.put("pinCode",pinCode);
			dataMap.put("mobile",mobile);
			
			
			
			appDTO.setControllerMap(dataMap);
			appDTO=customerService.updateCustomerAddress(appDTO);
			dataMap=appDTO.getControllerMap();
		
	
			if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
			{
				respObj.put("STATUS","SUCCESS");
				respObj.put("MESSAGE",appDTO.getInfoMessage());
			}
			else
			{
				respObj.put("MESSAGE",appDTO.getErrorMessage());
				respObj.put("STATUS","ERROR");
			}
			
		}
		catch(Exception e)
		{
			
		}
		
		return respObj.toString();
	}
	
	
	
	
	
	@RequestMapping(value="/updateUserInfo", method = RequestMethod.POST)
	public ModelAndView updateUserInfo(HttpServletRequest request,@ModelAttribute Customer customer)
	{
		ModelAndView mv =new ModelAndView("redirect:/userPreference.htm?res=INFO");

		try
		{
		Map dataMap=new HashMap();
		dataMap.put("CUSTOMER",customer);
		
		AppDTO appDTO =new AppDTO();
		appDTO.setControllerMap(dataMap);
		customerService.updateUserInfo(appDTO);
		
		if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
		{
			setInfo_message(appDTO.getInfoMessage(),true);
		}
		else
		{
			setError_message(appDTO.getErrorMessage(), true);
		}
		
		}
		catch(Exception e)
		{
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			e.printStackTrace();
		}
		
		return mv;
	}
	
	

	public CustomerService getCustomerService() {
		return customerService;
	}


	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
		
	
	
}
