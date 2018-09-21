package com.org.fashtag.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.org.fashtag.service.impl.CategoryServiceImpl;
import com.org.fashtag.util.CommonUtils;

@Controller
public class CategoryController extends BaseController {
	
	private static Logger logger = (Logger) Logger.getInstance(CategoryController.class);
	
	@Autowired
	private CategoryServiceImpl categoryService;

	@RequestMapping("/addCategory")
	public ModelAndView addCatgeory() {
		ModelAndView mv = new ModelAndView("addCategory");
		return mv;
	}
	
	
	@RequestMapping("/saveCategory")
	public ModelAndView saveCategory(@ModelAttribute Category category,HttpServletRequest request) 
	{
		ModelAndView mv=new ModelAndView();
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		try 
		{
			controllerMap.put("CATEGORY", category);
			appDTO.setControllerMap(controllerMap);
			appDTO=categoryService.addCategory(appDTO);

			if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
			{
				setError_message(appDTO.getErrorMessage(), true); 
				mv.setViewName("redirect:/addCategory.htm");
			}
			
			mv.setViewName("redirect:/listCategory.htm");
			setInfo_message(appDTO.getInfoMessage(), true);
		
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			mv.setViewName("redirect:/addCategory.htm");
		}
		return mv;
	}

	
	
	@RequestMapping("/editCategory")
	public ModelAndView editCatgeory(HttpServletRequest req) 
	{

		ModelAndView mv = new ModelAndView("addCategory");
		
		int id=Integer.parseInt(req.getParameter("id"));
		Category category=categoryService.getCategoryById(id);
		
		mv.addObject("category", category);
		return mv;

	}
	
	@RequestMapping("/updateCategory")
	public String updateCategory(@ModelAttribute Category category) 
	{
		ModelAndView mv=new ModelAndView();
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		try 
		{
			controllerMap.put("CATEGORY", category);
			appDTO.setControllerMap(controllerMap);
			appDTO=categoryService.updateCategory(appDTO);
			if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
			{
				setError_message(appDTO.getErrorMessage(), true);
				return "redirect:/editCategory.htm?id="+category.getId();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			return "redirect:/editCategory.htm?id="+category.getId();
		}
		setInfo_message(appDTO.getInfoMessage(), true);
		return "redirect:/listCategory.htm?name="+category.getName();

	}
	
	@RequestMapping("/listCategory")
	public ModelAndView listCatgeory(HttpServletRequest request) {

		Page categoryPage=getPage();
		List categoryList=new ArrayList();
		categoryPage.setResultList(Collections.EMPTY_LIST);
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		ModelAndView mv =new ModelAndView("listCategory");
		controllerMap.put("PAGE", categoryPage);
		
		try
		{

		String name=(String)request.getParameter("name");
					
			controllerMap.put("name",name);
			appDTO.setControllerMap(controllerMap);
			appDTO=categoryService.listAllCategories(appDTO);
			categoryPage=(Page)appDTO.getControllerMap().get("PAGE");
			categoryList=categoryPage.getResultList();
			setPage(categoryPage);
			mv.addObject("categoryList",categoryList);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mv;

	}
	
	@RequestMapping(value = "/getParentCategories", method = RequestMethod.POST)
	public @ResponseBody String getParentCategories(HttpServletRequest req) 
	{
		String parentCategory = req.getParameter("parentCategory");
		JSONArray jsonArr = new JSONArray();
		Collection categoryList = new ArrayList();

		categoryList = categoryService.getParentCategoryList(parentCategory);
		Iterator iter = categoryList.iterator();
		
		while (iter.hasNext()) 
		{
			try 
			{
				JSONObject jsonObj = new JSONObject();
				Category catObj = (Category) iter.next();
				jsonObj.put("name", catObj.getName());
				jsonObj.put("id", catObj.getId());
				jsonArr.put(jsonObj);
			
			
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				logger.warn(getResourceMessage("UNEXPECTED_ERROR")+e.getMessage());
				return getResourceMessage("ERROR");
			}
		}
		
		return jsonArr.toString();
	}

	
	@RequestMapping("/deleteCategory")
	public ModelAndView deleteCatgeory(@ModelAttribute Category category,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("redirect:/listCategory.htm");
		AppDTO appDTO=new AppDTO();
		Map controllerMap=new HashMap();
		controllerMap.put("CATEGORY", category);
		appDTO.setControllerMap(controllerMap);
		appDTO=categoryService.deleteCategory(appDTO);
		if(appDTO.getResponseStatus()==ResponseStatus.ERROR)
		{
			setError_message(appDTO.getErrorMessage(),true);
			mv.setViewName("redirect:/listCategory.htm");
		}
		
		setInfo_message(appDTO.getInfoMessage(), true);
		return mv;
	}
	
	
	
	
	/* getters and setters */

	public CategoryServiceImpl getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryServiceImpl categoryService) {
		this.categoryService = categoryService;
	}
	
	

}
