package com.org.fashtag.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.controller.BaseController;
import com.org.fashtag.model.Customer;
import com.org.fashtag.service.OrdersService;
import com.org.fashtag.util.CommonUtils;
@Controller
public class OrdersController extends BaseController {
	
	@Autowired
	public OrdersService ordersService;
	
	@RequestMapping("/trackOrder")
	public ModelAndView listProducts(HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView("trackOrder");
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
try{
	
	Customer customer =(Customer) getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
	if(customer==null)
	{
		setError_message(getResourceMessage(LOGIN_FOR_TRACK_ORDER), false);
		return mv;
	}
	String id=request.getParameter("orderId");
	if(!CommonUtils.isEmpty(id))
	{
	
		int orderId=Integer.parseInt(id);
		dataMap.put("orderId",orderId);
		appDTO.setControllerMap(dataMap);
		appDTO=ordersService.trackOrder(appDTO);
		
		if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
		{
			setError_message(appDTO.getErrorMessage(), true);
			mv.setViewName("redirect:/trackOrder.htm");
			return mv;

		}
		else
		{
			dataMap=appDTO.getControllerMap();
			mv.addAllObjects(dataMap);
		}
	
	}
	
	
}
	catch(Exception e)
	{
		
	}
		return mv;
		
	}
	
	/* getters and setters */
	

	public OrdersService getOrdersService() {
		return ordersService;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

}
