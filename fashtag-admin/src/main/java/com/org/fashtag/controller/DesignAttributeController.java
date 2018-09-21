package com.org.fashtag.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.org.fashtag.beans.Page;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.controller.BaseController;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.DesignAttribute;
import com.org.fashtag.model.DesignAttributeSpecification;
import com.org.fashtag.service.DesignAttributeService;

@Controller
public class DesignAttributeController extends BaseController {

	@Autowired
	private DesignAttributeService designAttributeService;
	
	
	
	

	/* design attribute */
		
		@RequestMapping("/addDesignAttribute")
		public ModelAndView addDesignAttibute() {
		
			ModelAndView mv = new ModelAndView("addDesignAttribute");
		
			return mv;
		
		}
		
		
		

		@RequestMapping("/saveDesignAttribute")
		public ModelAndView saveDesignAttribute(@ModelAttribute DesignAttribute designAttribute) {
		
			ModelAndView mv=new ModelAndView();
			AppDTO appDTO=new AppDTO();
			Map controllerMap=new HashMap();
			try 
			{
				controllerMap.put("DESIGN_ATTRIBUTE", designAttribute);
				appDTO.setControllerMap(controllerMap);
				appDTO=designAttributeService.addDesignAttribute(appDTO);

				if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
				{
					setError_message(appDTO.getErrorMessage(), true); 
					mv.setViewName("redirect:/addDesignAttribute.htm");
				}
				
				mv.setViewName("redirect:/listDesignAttribute.htm");
				setInfo_message(appDTO.getInfoMessage(), true);
			
			} 
			catch (Exception e) 
			{
				setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
				mv.setViewName("redirect:/addDesignAttribute.htm");
			}
			return mv;
		
		}
		
		
		
		

		@RequestMapping("/editDesignAttribute")
		public ModelAndView editDesignAttribute(HttpServletRequest req) 
		{

			ModelAndView mv = new ModelAndView("addDesignAttribute");
			
			int id=Integer.parseInt(req.getParameter("id"));
			DesignAttribute designAttribute=designAttributeService.getDesignAttributeById(id);
			
			mv.addObject("designAttribute", designAttribute);
			return mv;

		}
		
		
		
		@RequestMapping("/updateDesignAttribute")
		public String updateDesignAttribute(@ModelAttribute DesignAttribute designAttribute) 
		{
			System.out.println("system product controller");
			ModelAndView mv=new ModelAndView();
			AppDTO appDTO=new AppDTO();
			Map controllerMap=new HashMap();
			try 
			{
				controllerMap.put("DESIGN_ATTRIBUTE", designAttribute);
				appDTO.setControllerMap(controllerMap);
				appDTO=designAttributeService.updateDesignAttribute(appDTO);
				if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
				{
					setError_message(appDTO.getErrorMessage(), true);
					return "redirect:/editDesignAttribute.htm?id="+designAttribute.getId();
				}
			} 
			catch (Exception e) 
			{
				
				setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
				return "redirect:/editDesignAttribute.htm?id="+designAttribute.getId();
			}
			setInfo_message(appDTO.getInfoMessage(), true);
			return "redirect:/listDesignAttribute.htm?name="+designAttribute.getName()+"&st="+appDTO.getErrorMessage();

		}

		
		
		
		@RequestMapping("/listDesignAttribute")
		public ModelAndView listDesignAttribute(HttpServletRequest request) {

			Page designAttributePage=getPage();
			List designAttributeList=new ArrayList();
			designAttributePage.setResultList(Collections.EMPTY_LIST);
			AppDTO appDTO=new AppDTO();
			Map controllerMap=new HashMap();
			ModelAndView mv =new ModelAndView("listDesignAttribute");
			controllerMap.put("PAGE", designAttributePage);
			
			try
			{

			   String name=(String)request.getParameter("name");
						
				controllerMap.put("name",name);
				appDTO.setControllerMap(controllerMap);
				appDTO=designAttributeService.listAllDesignAttributes(appDTO);
				designAttributePage=(Page)appDTO.getControllerMap().get("PAGE");
				designAttributeList=designAttributePage.getResultList();
				setPage(designAttributePage);
				mv.addObject("designAttributeList",designAttributeList);
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return mv;

		}
		
		
		@RequestMapping("/deleteDesignAttribute")
		public ModelAndView deleteDesignAttribute(@ModelAttribute DesignAttribute designAttribute,HttpServletRequest request) {
			ModelAndView mv = new ModelAndView("redirect:/listDesignAttribute.htm");
			AppDTO appDTO=new AppDTO();
			Map controllerMap=new HashMap();
			controllerMap.put("DESIGN_ATTRIBUTE", designAttribute);
			appDTO.setControllerMap(controllerMap);
			appDTO=designAttributeService.deleteDesignAttribute(appDTO);
			if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
			{
				setError_message(appDTO.getErrorMessage(),true);
				mv.setViewName("redirect:/listDesignAttribute.htm");
			}
			
			setInfo_message(appDTO.getInfoMessage(), true);
			return mv;
		}
		
		
		
		
		@RequestMapping(value = "/getDesignAttributes", method = RequestMethod.POST)
		public @ResponseBody String getDesignAttributes(HttpServletRequest req) 
		{
			String designAttribute = req.getParameter("searchKey");
			JSONArray jsonArr = new JSONArray();
			Collection designAttributeList = new ArrayList();

			designAttributeList = designAttributeService.getDesignAttributeListByKey(designAttribute);
			Iterator iter = designAttributeList.iterator();
			
			while (iter.hasNext()) 
			{
				try 
				{
					JSONObject jsonObj = new JSONObject();
					DesignAttribute designAttrObj = (DesignAttribute) iter.next();
					jsonObj.put("name", designAttrObj.getName());
					jsonObj.put("id", designAttrObj.getId());
					jsonArr.put(jsonObj);
				
				
				}
				catch (Exception e) 
				{
					e.printStackTrace();
					return getResourceMessage("ERROR");
				}
			}
			
			return jsonArr.toString();
		}

		
		
		
	
	/* design attribute specification*/
	
		
		@RequestMapping(value = "/getDesignAttrSpec", method = RequestMethod.POST)
		public @ResponseBody String getDesignAttrSpec(HttpServletRequest req) 
		{
			Integer designAttributeId =Integer.parseInt(req.getParameter("designAttrId"));
			JSONArray jsonArr = new JSONArray();
			Collection designAttributeList = new ArrayList();

			designAttributeList = designAttributeService.getDesignAttrSpecByDesignAttrId(designAttributeId);
			Iterator iter = designAttributeList.iterator();
			
			while (iter.hasNext()) 
			{
				try 
				{
					JSONObject jsonObj = new JSONObject();
					DesignAttributeSpecification designAttrSpecObj = (DesignAttributeSpecification) iter.next();
					jsonObj.put("name", designAttrSpecObj.getName());
					jsonObj.put("id", designAttrSpecObj.getId());
					jsonArr.put(jsonObj);
				
				
				}
				catch (Exception e) 
				{
					e.printStackTrace();
					return getResourceMessage("ERROR");
				}
			}
			
			return jsonArr.toString();
		}

	@RequestMapping("/addDesignAttrSpec")
	public ModelAndView addDesignAttributeSpecification() {
	
		ModelAndView mv = new ModelAndView("addDesignAttributeSpec");
		Collection designAttributeList=Collections.EMPTY_LIST;
		
		designAttributeList=designAttributeService.getAllActiveDesignAttributes();
		mv.addObject("designAttributeList",designAttributeList);
		mv.addObject("designAttrSpecCount", 0);

		return mv;
	
	}
	
	@RequestMapping("/saveDesignAttrSpec")
	public ModelAndView saveDesignAttributeSpecification(HttpServletRequest request) {
	
		ModelAndView mv = new ModelAndView("addDesignAttributeSpec");
		AppDTO appDTO=new AppDTO();
	Map controllerMap=new HashMap();
	controllerMap.put("REQUEST",request);
	appDTO.setControllerMap(controllerMap);
	designAttributeService.addDesignAttributeSpecification(appDTO);
	
	if(appDTO.getResponseStatus().equals(ResponseStatus.SUCCESS))
	setInfo_message(appDTO.getInfoMessage(),true);
	return mv;
	
	}
	
	
	@RequestMapping("/editDesignAttrSpec")
	public ModelAndView editDesignAttributeSpec(HttpServletRequest req) 
	{

		ModelAndView mv = new ModelAndView("addDesignAttributeSpec");
		
		int designAttrId=Integer.parseInt(req.getParameter("designAttributeId"));
		Collection  designAttributeSpecList=designAttributeService.getDesignAttrSpecByDesignAttrId(designAttrId);
		Collection designAttributeList=Collections.EMPTY_LIST;
		designAttributeList=designAttributeService.getAllActiveDesignAttributes();
		
		mv.addObject("designAttributeList",designAttributeList);
		mv.addObject("designAttributeSpecList", designAttributeSpecList);
		mv.addObject("designAttrId", designAttrId);
		mv.addObject("designAttrSpecCount", designAttributeSpecList.size());

		
		return mv;

	}
	
	@RequestMapping("/updateDesignAttributeSpec")
	public ModelAndView updateDesignAttributeSpec(HttpServletRequest request) {
	
		ModelAndView mv = new ModelAndView("addDesignAttributeSpec");
		AppDTO appDTO=new AppDTO();
	Map controllerMap=new HashMap();
	controllerMap.put("REQUEST",request);
	appDTO.setControllerMap(controllerMap);
	designAttributeService.updateDesignAttributeSpecification(appDTO);
	
	if(appDTO.getResponseStatus().equals(ResponseStatus.SUCCESS))
	setInfo_message(appDTO.getInfoMessage(),true);
	return mv;
	
	}	
	
	
	
	@RequestMapping("/listDesignAttrSpec")
	public ModelAndView listDesignAttrSpec(HttpServletRequest request) {

		Collection designAttributesList =Collections.EMPTY_LIST;
		
		 
		designAttributesList=designAttributeService.getAllActiveDesignAttributes();
		
		Page designAttributeSpecPage=getPage();
		List designAttributeSpecList=new ArrayList();
		designAttributeSpecPage.setResultList(Collections.EMPTY_LIST);
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		ModelAndView mv =new ModelAndView("listDesignAttributeSpec");
		mv.addObject("designAttributeList",designAttributesList);

		controllerMap.put("PAGE", designAttributeSpecPage);
		
		try
		{

			
			 Integer designAttributeId=0;

			designAttributeId=Integer.parseInt(request.getParameter("designAttributeId"));
			controllerMap.put("designAttributeId",designAttributeId);
			appDTO.setControllerMap(controllerMap);
			appDTO=designAttributeService.listAllDesignAttrSpec(appDTO);
			designAttributeSpecPage=(Page)appDTO.getControllerMap().get("PAGE");
			designAttributeSpecList=designAttributeSpecPage.getResultList();
			setPage(designAttributeSpecPage);

			mv.addObject("designAttributeSpecList",designAttributeSpecList);

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mv;

	}


/* getters and setters*/
	

	public DesignAttributeService getDesignAttributeService() {
		return designAttributeService;
	}




	public void setDesignAttributeService(
			DesignAttributeService designAttributeService) {
		this.designAttributeService = designAttributeService;
	}
	
	
	
	
}
