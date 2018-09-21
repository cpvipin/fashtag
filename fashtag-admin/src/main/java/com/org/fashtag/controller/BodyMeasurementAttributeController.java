package com.org.fashtag.controller;

import java.util.ArrayList;
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
import com.org.fashtag.model.Size;
import com.org.fashtag.service.BodyMeasurementAttributeService;
import com.org.fashtag.service.SizeService;

@Controller
public class BodyMeasurementAttributeController extends BaseController {
	
	@Autowired
	private BodyMeasurementAttributeService bodyMeasurementAttributeService;

/* Size manage*/
	
	@RequestMapping("/addBodyMeasurementAttribute")
	public ModelAndView addSize() {
	
		ModelAndView mv = new ModelAndView("addBodyMeasurementAttribute");
	
		return mv;
	
	}
	
	
	@RequestMapping("/saveBodyMeasurementAttribute")
	public ModelAndView saveBodyMeasurementAttribute(@ModelAttribute BodyMeasurementAttribute bodyMeasurementAttribute) {
	
		ModelAndView mv=new ModelAndView();
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		try 
		{
			controllerMap.put("BODY_MEASUREMENT_ATTR", bodyMeasurementAttribute);
			appDTO.setControllerMap(controllerMap);
			appDTO=bodyMeasurementAttributeService.addBodyMeasurementAttribute(appDTO);

			if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
			{
				setError_message(appDTO.getErrorMessage(), true); 
				mv.setViewName("redirect:/addBodyMeasurementAttribute.htm");
			}
			
			mv.setViewName("redirect:/listBodyMeasurementAttribute.htm");
			setInfo_message(appDTO.getInfoMessage(), true);
		
		} 
		catch (Exception e) 
		{
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			mv.setViewName("redirect:/addBodyMeasurementAttribute.htm");
		}
		return mv;
	
	}
	
	
	
	@RequestMapping("/editBodyMeasurementAttribute")
	public ModelAndView editBodyMeasurementAttribute(HttpServletRequest req) 
	{

		ModelAndView mv = new ModelAndView("addBodyMeasurementAttribute");
		
		int id=Integer.parseInt(req.getParameter("id"));
		BodyMeasurementAttribute bodyMeasurementAttribute=bodyMeasurementAttributeService.getBodyMeasurementAttributeById(id);
		
		mv.addObject("bodyMeasurementAttribute", bodyMeasurementAttribute);
		return mv;

	}
	
	@RequestMapping("/updateBodyMeasurementAttribute")
	public String updateBodyMeasurementAttribute(@ModelAttribute BodyMeasurementAttribute bodyMeasurementAttribute) 
	{
		ModelAndView mv=new ModelAndView();
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		try 
		{
			controllerMap.put("BODY_MEASUREMENT_ATTR", bodyMeasurementAttribute);
			appDTO.setControllerMap(controllerMap);
			appDTO=bodyMeasurementAttributeService.updateBodyMeasurementAttribute(appDTO);
			if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
			{
				setError_message(appDTO.getErrorMessage(), true);
				return "redirect:/editBodyMeasurementAttribute.htm?id="+bodyMeasurementAttribute.getId();
			}
		} 
		catch (Exception e) 
		{
			
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			return "redirect:/editBodyMeasurementAttribute.htm?id="+bodyMeasurementAttribute.getId();
		}
		setInfo_message(appDTO.getInfoMessage(), true);
		return "redirect:/listBodyMeasurementAttribute.htm?name="+bodyMeasurementAttribute.getName();

	}


	@RequestMapping("/deleteBodyMeasurementAttribute")
	public ModelAndView deleteSize(@ModelAttribute BodyMeasurementAttribute bodyMeasurementAttribute,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("redirect:/listSize.htm");
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		controllerMap.put("BODY_MEASUREMENT_ATTR", bodyMeasurementAttribute);
		appDTO.setControllerMap(controllerMap);
		appDTO=bodyMeasurementAttributeService.deleteBodyMeasurementAttribute(appDTO);
		if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
		{
			setError_message(appDTO.getErrorMessage(),true);
			mv.setViewName("redirect:/listBodyMeasurementAttribute.htm");
		}
		
		setInfo_message(appDTO.getInfoMessage(), true);
		return mv;
	}
	
	
	
	@RequestMapping("/listBodyMeasurementAttribute")
	public ModelAndView listBodyMeasurementAttribute(HttpServletRequest request) {

		Page bodyMeasurementAttrPage=getPage();
		List bodyMeasurementAttrList=new ArrayList();
		bodyMeasurementAttrPage.setResultList(Collections.EMPTY_LIST);
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		ModelAndView mv =new ModelAndView("listBodyMeasurementAttribute");
		controllerMap.put("PAGE", bodyMeasurementAttrPage);
		
		try
		{

		   String name=(String)request.getParameter("name");
					
			controllerMap.put("name",name);
			appDTO.setControllerMap(controllerMap);
			appDTO=bodyMeasurementAttributeService.listAllBodyMeasurementAttributes(appDTO);
			bodyMeasurementAttrPage=(Page)appDTO.getControllerMap().get("PAGE");
			bodyMeasurementAttrList=bodyMeasurementAttrPage.getResultList();
			setPage(bodyMeasurementAttrPage);
			mv.addObject("bodyMeasurementAttrList",bodyMeasurementAttrList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mv;

	}
	
	/* getters and setters */

	public BodyMeasurementAttributeService getBodyMeasurementAttributeService() {
		return bodyMeasurementAttributeService;
	}


	public void setBodyMeasurementAttributeService(
			BodyMeasurementAttributeService bodyMeasurementAttributeService) {
		this.bodyMeasurementAttributeService = bodyMeasurementAttributeService;
	}
	
	
	
}
