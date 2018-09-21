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
import com.org.fashtag.model.OrderProduct;
import com.org.fashtag.model.Orders;
import com.org.fashtag.model.Size;
import com.org.fashtag.service.OrdersService;
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

		ordersPage.setOrderBy("order.dateAdded");
		ordersPage.setResultList(Collections.EMPTY_LIST);
		controllerMap.put("PAGE", ordersPage);
		
		try
		{

		   int orderStatus=Integer.parseInt(request.getParameter("orderStatus"));
		   
		   String orderDate=request.getParameter("orderDate");		   
			controllerMap.put("orderDate",orderDate);
			controllerMap.put("orderStatus",orderStatus);
			controllerMap.put("vendor", getHttpSession().getAttribute(LOGGED_IN_VENDOR));
			
			
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
	public ModelAndView viewOrders(@ModelAttribute OrderProduct orderProduct) {
	
		ModelAndView mv = new ModelAndView("viewOrders"); 
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		
		controllerMap.put("ORDERPRODUCT", orderProduct);
	    appDTO.setControllerMap(controllerMap);
	    appDTO=ordersService.getOrderProductById(appDTO);	
	    controllerMap=appDTO.getControllerMap();
	    orderProduct=(OrderProduct)controllerMap.get("ORDERPRODUCT");
	    mv.addObject("orderProduct", orderProduct);
	    
	    Collection ordersStatusColl=ordersService.getActiveOrdersStatus();
	    mv.addObject("ordersStatusColl",ordersStatusColl);
	    return mv;
	    
	}
	
	
	
	
}
