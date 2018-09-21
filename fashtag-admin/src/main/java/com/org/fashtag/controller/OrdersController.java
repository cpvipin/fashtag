package com.org.fashtag.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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
import com.org.fashtag.model.OrderHistory;
import com.org.fashtag.model.Orders;
import com.org.fashtag.model.Size;
import com.org.fashtag.service.OrdersService;
import com.org.fashtag.service.SizeService;
import com.org.fashtag.util.DateUtils;

@Controller
public class OrdersController extends BaseController {
	
	@Autowired
	private OrdersService ordersService;

	
	
	@RequestMapping("/listOrders")
	public ModelAndView listOrders(HttpServletRequest request) {

		ModelAndView mv =new ModelAndView("listOrders");
		Page ordersPage=getPage();
		List ordersList=new ArrayList();
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();

		ordersPage.setOrderBy("dateAdded");
		ordersPage.setResultList(Collections.EMPTY_LIST);
		controllerMap.put("PAGE", ordersPage);
		
		try
		{

		   String customerName=(String)request.getParameter("customerName");
		   Integer orderId=Integer.parseInt(request.getParameter("orderId"));
		   int orderStatus=Integer.parseInt(request.getParameter("orderStatus"));
		   
		   
			controllerMap.put("customerName",customerName);
			controllerMap.put("orderId",orderId);
			controllerMap.put("orderStatus",orderStatus);

			appDTO.setControllerMap(controllerMap);
			appDTO=ordersService.listOrders(appDTO);
			ordersPage=(Page)appDTO.getControllerMap().get("PAGE");
			ordersList=ordersPage.getResultList();
			setPage(ordersPage);
			
			mv.addObject("ordersList",ordersList);
			
		    Collection ordersStatusColl=ordersService.getActiveOrdersStatus();
		    mv.addObject("ordersStatusColl",ordersStatusColl);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mv;

	}
	
	
	@RequestMapping("/viewOrders")
	public ModelAndView viewOrders(@ModelAttribute Orders orders) {
	
		ModelAndView mv = new ModelAndView("viewOrders"); 
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		
	    controllerMap.put("ORDERS", orders);
	    appDTO.setControllerMap(controllerMap);
	    appDTO=ordersService.getOrderById(appDTO);	
	    controllerMap=appDTO.getControllerMap();
	    orders=(Orders)controllerMap.get("ORDERS");
	    mv.addObject("orders", orders);
	    
	    
	    Collection ordersStatusColl=ordersService.getActiveOrdersStatus();
	    mv.addObject("ordersStatusColl",ordersStatusColl);
	    return mv;
	
	}
	
	
	@RequestMapping("/addOrderHistory")
	public ModelAndView addOrderHistory(@ModelAttribute OrderHistory orderHistory) {
		ModelAndView mv = new ModelAndView("redirect:/viewOrders.htm?id="+orderHistory.getOrder().getId());
		AppDTO appDTO=new AppDTO();
		try{
		
		Map dataMap=new HashMap();
		Date dateAdded=DateUtils.getCurrentDate();
		orderHistory.setDateAdded(dateAdded);
		dataMap.put("orderHistory", orderHistory);
		appDTO.setControllerMap(dataMap);
		appDTO=ordersService.addOrderHistory(appDTO);
		
		setInfo_message(appDTO.getInfoMessage(), true);
		}
		catch(Exception e ){ setError_message(appDTO.getErrorMessage(), true);}
		 
		return mv;
	
	}
	
	
	
	/*
	
	@RequestMapping("/addSize")
	public ModelAndView addSize() {
	
		ModelAndView mv = new ModelAndView("addSize");
	
		return mv;
	
	}
	
	
	@RequestMapping("/saveSize")
	public ModelAndView saveSize(@ModelAttribute Size size) {
	
		ModelAndView mv=new ModelAndView();
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		try 
		{
			controllerMap.put("SIZE", size);
			appDTO.setControllerMap(controllerMap);
			appDTO=sizeService.addSize(appDTO);

			if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
			{
				setError_message(appDTO.getErrorMessage(), true); 
				mv.setViewName("redirect:/addSize.htm");
			}
			
			mv.setViewName("redirect:/listSize.htm");
			setInfo_message(appDTO.getInfoMessage(), true);
		
		} 
		catch (Exception e) 
		{
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			mv.setViewName("redirect:/addSize.htm");
		}
		return mv;
	
	}
	
	
	
	@RequestMapping("/editSize")
	public ModelAndView editSize(HttpServletRequest req) 
	{

		ModelAndView mv = new ModelAndView("addSize");
		
		int id=Integer.parseInt(req.getParameter("id"));
		Size size=sizeService.getSizeById(id);
		
		mv.addObject("size", size);
		return mv;

	}
	
	@RequestMapping("/updateSize")
	public String updateSize(@ModelAttribute Size size) 
	{
		ModelAndView mv=new ModelAndView();
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		try 
		{
			controllerMap.put("SIZE", size);
			appDTO.setControllerMap(controllerMap);
			appDTO=sizeService.updateSize(appDTO);
			if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
			{
				setError_message(appDTO.getErrorMessage(), true);
				return "redirect:/editSize.htm?id="+size.getId();
			}
		} 
		catch (Exception e) 
		{
			
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			return "redirect:/editSize.htm?id="+size.getId();
		}
		setInfo_message(appDTO.getInfoMessage(), true);
		return "redirect:/listSize.htm?name="+size.getName();

	}


	@RequestMapping("/deleteSize")
	public ModelAndView deleteSize(@ModelAttribute Size size,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("redirect:/listSize.htm");
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		controllerMap.put("SIZE", size);
		appDTO.setControllerMap(controllerMap);
		appDTO=sizeService.deleteSize(appDTO);
		if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
		{
			setError_message(appDTO.getErrorMessage(),true);
			mv.setViewName("redirect:/listSize.htm");
		}
		
		setInfo_message(appDTO.getInfoMessage(), true);
		return mv;
	}
	
	
	
	@RequestMapping("/listSize")
	public ModelAndView listSize(HttpServletRequest request) {

		Page sizePage=getPage();
		List sizeList=new ArrayList();
		sizePage.setResultList(Collections.EMPTY_LIST);
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		ModelAndView mv =new ModelAndView("listSize");
		controllerMap.put("PAGE", sizePage);
		
		try
		{

		   String name=(String)request.getParameter("name");
					
			controllerMap.put("name",name);
			appDTO.setControllerMap(controllerMap);
			appDTO=sizeService.listAllSizes(appDTO);
			sizePage=(Page)appDTO.getControllerMap().get("PAGE");
			sizeList=sizePage.getResultList();
			setPage(sizePage);
			mv.addObject("sizeList",sizeList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mv;

	}
	*/
	
	
}
