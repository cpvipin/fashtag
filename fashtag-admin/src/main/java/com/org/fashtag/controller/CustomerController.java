package com.org.fashtag.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.beans.Page;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.controller.BaseController;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerBodyMeasurement;
import com.org.fashtag.model.Size;
import com.org.fashtag.service.BodyMeasurementAttributeService;
import com.org.fashtag.service.CustomerService;
import com.org.fashtag.service.SizeService;

@Controller
public class CustomerController extends BaseController {
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private BodyMeasurementAttributeService bodyMeasurementAttributeService;

	
	@RequestMapping("/addCustomer")
	public ModelAndView addCustomer(HttpServletRequest req)
	{
		ModelAndView mv = new ModelAndView("addCustomer");

		Collection allStates=customerService.getAllStates();
		
		mv.addObject("stateList",allStates);
		return mv;
		
	}
	@RequestMapping("/listCustomer")
	public ModelAndView listCustomer(HttpServletRequest request) {
	
		ModelAndView mv = new ModelAndView("listCustomer");
	
		

		Page sizePage=getPage();
		List sizeList=new ArrayList();
		sizePage.setResultList(Collections.EMPTY_LIST);
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		controllerMap.put("PAGE", sizePage);
		
		try
		{

		   String name=(String)request.getParameter("name");
					
			controllerMap.put("name",name);
			appDTO.setControllerMap(controllerMap);
			appDTO=customerService.listCustomer(appDTO);
			sizePage=(Page)appDTO.getControllerMap().get("PAGE");
			sizeList=sizePage.getResultList();
			setPage(sizePage);
			mv.addObject("customerList",sizeList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mv;
	
	}
	
	
	@RequestMapping("/saveCustomer")
	public ModelAndView saveSize(@ModelAttribute Customer customer,HttpServletRequest request) {
	
		ModelAndView mv=new ModelAndView();
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		try 
		{
			controllerMap.put("CUSTOMER", customer);
			controllerMap.put("REQUEST", request);
			controllerMap.put("ADDRESS_COUNT", request.getParameter("addressCount"));
			appDTO.setControllerMap(controllerMap);
			appDTO=customerService.addCustomer(appDTO);

			if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
			{
				setError_message(appDTO.getErrorMessage(), true); 
				mv.setViewName("redirect:/addCustomer.htm");
			}
			
			mv.setViewName("redirect:/listCustomer.htm");
			setInfo_message(appDTO.getInfoMessage(), true);
		
		} 
		catch (Exception e) 
		{
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			mv.setViewName("redirect:/addCustomer.htm");
		}
		return mv;
	
	}
	
	
	
	@RequestMapping("/editCustomer")
	public ModelAndView editSize(@ModelAttribute Customer customer) 
	{

		ModelAndView mv = new ModelAndView("addCustomer");
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		controllerMap.put("CUSTOMER", customer);
		appDTO.setControllerMap(controllerMap);
		appDTO=customerService.getCustomerDetails(appDTO);

		controllerMap=appDTO.getControllerMap();
		customer=(Customer)controllerMap.get("CUSTOMER");
		Collection addressColl=(Collection)controllerMap.get("CUSTOMER_ADDRESS");
		Collection ipColl=(Collection)controllerMap.get("CUSTOMER_IP");

		Collection allStates=customerService.getAllStates();
		mv.addObject("stateList",allStates);
		mv.addObject("customer", customer);
		mv.addObject("addressColl",addressColl);
		mv.addObject("ipColl",ipColl);

		return mv;

		
	}
	
	@RequestMapping("/updateCustomer")
	public String updateSize(@ModelAttribute Customer customer,HttpServletRequest request) 
	{
		ModelAndView mv=new ModelAndView();
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		try 
		{
			controllerMap.put("CUSTOMER", customer);
			controllerMap.put("REQUEST", request);
			controllerMap.put("ADDRESS_COUNT", request.getParameter("addressCount"));

			appDTO.setControllerMap(controllerMap);
			appDTO=customerService.updateCustomer(appDTO);
			if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
			{
				setError_message(appDTO.getErrorMessage(), true);
				return "redirect:/editCustomer.htm?id="+customer.getId();
			}
		} 
		catch (Exception e) 
		{
			
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			return "redirect:/editCustomer.htm?id="+customer.getId();
		}
		setInfo_message(appDTO.getInfoMessage(), true);
		return "redirect:/listCustomer.htm?name="+customer.getFullName();

	}


	@RequestMapping("/deleteCustomer")
	public ModelAndView deleteCustomer(@ModelAttribute Customer customer,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("redirect:/listCustomer.htm");
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		controllerMap.put("CUSTOMER", customer);
		appDTO.setControllerMap(controllerMap);
		appDTO=customerService.deleteCustomer(appDTO);
		if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
		{
			setError_message(appDTO.getErrorMessage(),true);
			mv.setViewName("redirect:/listCustomer.htm");
		}
		
		setInfo_message(appDTO.getInfoMessage(), true);
		return mv;
	}
	/*
	 * Customer body measurements will be added against customer profiles
	 */
	
	@RequestMapping("/addCustomerBodyMeasurement")
	public ModelAndView addCustomerBodyMeasurement(HttpServletRequest req) 
	{

		ModelAndView mv = new ModelAndView("addCustomerBodyMeasurement");
		
		try{
		int customerId=Integer.parseInt(req.getParameter("custId"));
		int profileId=Integer.parseInt(req.getParameter("profileId"));
		int bodyMeasurementCount=0;
		
		Customer customer=customerService.getCustomerById(customerId);
		
		Collection customerProfileList=customerService.getCustomerProfiles(customer);
		Collection customerMeasurementList=Collections.EMPTY_LIST;
		Collection bodyMeasurementAttributeList=Collections.EMPTY_LIST;
			
		
		
		if(profileId>0)
		{
		customerMeasurementList=customerService.getCustomerBodyMeasurementsByProfile(profileId);
		}

		/*
		 * If customer profile is available then it should be selected for the 
		 * attributes to populate.
		 */
		if(customerProfileList.size()==0||profileId>0)
		{
			bodyMeasurementAttributeList=bodyMeasurementAttributeService.
		getBodyMeasurementAttributeByGender(customer.getGender());
		}
		
		
		mv.addObject("customerMeasurementList",customerMeasurementList);
		mv.addObject("bodyMeasurementAttributeList",bodyMeasurementAttributeList);
		mv.addObject("customerProfileList",customerProfileList);
		mv.addObject("bodyMeasurementCount", customerMeasurementList.size());
		mv.addObject("customer",customer);
	    mv.addObject("customerProfileId",profileId);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return mv;

		
	}
	
	
	
	@RequestMapping("/saveCustomerBodyMeasurement")
	public ModelAndView saveCustomerBodyMeasurement(HttpServletRequest req) 
	{
		AppDTO appDto=new AppDTO();
		Map controllerMap=new HashMap();
		controllerMap.put("REQUEST",req);
		appDto.setControllerMap(controllerMap);
		appDto=customerService.addCustomerBodyMeasurement(appDto);
		Customer customer=(Customer) appDto.getControllerMap().get("CUSTOMER");
		ModelAndView mv=new ModelAndView("redirect:/listCustomer.htm?name="+customer.getFullName());
		
				
		
		if(appDto.getResponseStatus()==ResponseStatus.SUCCESS)
			setInfo_message(appDto.getInfoMessage(), true);
		else
			setError_message(appDto.getErrorMessage(), true);
			
		return mv;
	}
	
	
	public CustomerService getCustomerService() {
		return customerService;
	}
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	public BodyMeasurementAttributeService getBodyMeasurementAttributeService() {
		return bodyMeasurementAttributeService;
	}
	public void setBodyMeasurementAttributeService(
			BodyMeasurementAttributeService bodyMeasurementAttributeService) {
		this.bodyMeasurementAttributeService = bodyMeasurementAttributeService;
	}
	
	
	
	
	
}
