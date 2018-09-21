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
import com.org.fashtag.service.PaymentService;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.PasswordEncryptor;


@Controller
public class PaymentController extends BaseController {
	
	@Autowired
	private PaymentService paymentService;

	
	@RequestMapping("/paymentInit")
	public ModelAndView paymentInit(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView();
		Map dataMap=new HashMap();
		AppDTO appDTO=new AppDTO();
		try
		{
			appDTO.setControllerMap(dataMap);
			appDTO=paymentService.initializePayment(appDTO);
			dataMap=appDTO.getControllerMap();
			String returnView=(String)dataMap.get("returnView");
			mv.setViewName(returnView);
			mv.addAllObjects(dataMap);
		}
		catch(Exception e)
		{
			
		}

		return mv;
	}
	

	
	
	@RequestMapping("/paymentFin")
	public ModelAndView paymentFinalize(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("redirect:/cart.htm");
		Map dataMap=new HashMap();
		AppDTO appDTO=new AppDTO();
		try
		{
			String encResp= request.getParameter("encResp");
			dataMap.put("encResp",encResp);
			appDTO.setControllerMap(dataMap);
			appDTO=paymentService.finalizePayment(appDTO);
			dataMap=appDTO.getControllerMap();
			String returnView=(String)dataMap.get("returnView");
			dataMap.clear();
			mv.setViewName(returnView);
			
			
			if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
			{
				setError_message(appDTO.getErrorMessage(), true);
			}
			else
			{
				setInfo_message(appDTO.getInfoMessage(), false);
			}
		}
		catch(Exception e)
		{
			setError_message(getResourceMessage(UNEXPECTED_ERROR), true);
			mv.setViewName("redirect:/cart.htm");
			e.printStackTrace();
		}

		return mv;
	}

	
	
	/* getters and setters */


	public PaymentService getPaymentService() {
		return paymentService;
	}


	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	

	
	
	
	
	
}
