package com.org.fashtag.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.context.AdminBeanConstants;
import com.org.fashtag.context.TransactionManager;
import com.org.fashtag.dao.CategoryDao;
import com.org.fashtag.dao.DesignAttributeDao;
import com.org.fashtag.dao.ProductDao;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.DesignAttribute;
import com.org.fashtag.model.DesignAttributeSpecification;
import com.org.fashtag.model.Size;
import com.org.fashtag.service.DesignAttributeService;
import com.org.fashtag.service.ProductService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.DateUtils;

public class DesignAttributeServiceImpl extends BaseServiceImpl implements
		DesignAttributeService, AdminBeanConstants {
	
	private DesignAttributeDao designAttributeDao;
	
	
/* Design Attribute */
	

	public AppDTO addDesignAttribute(AppDTO appDTO) {
			Map controllerMap=appDTO.getControllerMap();
			DesignAttribute designAttribute=(DesignAttribute)controllerMap.get("DESIGN_ATTRIBUTE");
			designAttribute.setDateAdded(DateUtils.getCurrentDate());
			designAttribute.setDateModified(DateUtils.getCurrentDate());
			((DesignAttributeDao) getDAOBean(DESIGN_ATTRIBUTE_DAO)).addDesignAttribute(designAttribute);
			appDTO.setInfoMessage(getResourceMessage("DESIGN_ATTRIBUTE_ADDED"));
			
			return appDTO;
		}
		
	


	
	public DesignAttribute getDesignAttributeById(int id) {
		DesignAttribute designAttribute=(DesignAttribute)((ProductDao) getDAOBean(PRODUCT_DAO)).
		findObjectByCondition
		(DesignAttribute.class,new String[] 
		{"id"},
		new Object[] {id});

		return designAttribute;
	}



	
	public AppDTO listAllDesignAttributes(AppDTO appDTO) {
		
		appDTO=((DesignAttributeDao) getDAOBean(DESIGN_ATTRIBUTE_DAO)).listAllDesignAttributes(appDTO);
		return appDTO;
	}

	
	
	public AppDTO updateDesignAttribute(AppDTO appDTO) {
		try{

		Map controllerMap=appDTO.getControllerMap();
		DesignAttribute designAttribute=(DesignAttribute)controllerMap.get("DESIGN_ATTRIBUTE");
		
		designAttribute.setDateModified(DateUtils.getCurrentDate());
				
		((DesignAttributeDao) getDAOBean(DESIGN_ATTRIBUTE_DAO)).updateDesignAttribute(designAttribute);
				
		appDTO.setInfoMessage(getResourceMessage("DESIGN_ATTRIBUTE_UPDATED"));
		appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		}catch(Exception e){
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage(getResourceMessage("DESIGN_ATTRIBUTE_EXISTS"));
			return appDTO;
		}
		
			return appDTO;
	
	
	
}

	
	public AppDTO deleteDesignAttribute(AppDTO appDTO) {
		
		try
		{
			Map controllerMap=appDTO.getControllerMap();
			DesignAttribute deleteDesignAttribute=(DesignAttribute)controllerMap.get("DESIGN_ATTRIBUTE");
			((DesignAttributeDao) getDAOBean(DESIGN_ATTRIBUTE_DAO)).deleteDesignAttribute(deleteDesignAttribute);
			appDTO.setInfoMessage(getResourceMessage("DESIGN_ATTRIBUTE_DELETED"));
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			
		}
		catch(Exception e)
		{
			appDTO.setErrorMessage(getResourceMessage("CHILD_RECORD_EXISTS"));
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			return appDTO;
		}
		return appDTO;
		
	}
	
	
	public Collection getAllActiveDesignAttributes()
	{
		Collection activeDesignAttrColl=((ProductDao) getDAOBean(PRODUCT_DAO)).findListByCondition(DesignAttribute.class, 
				 new String[]{ "activeStatus"}
				, new Object[]{true});
		return activeDesignAttrColl;
	}
	
	public Collection getDesignAttributeListByKey(String likeKey)
	{
		Collection designAttrList=new ArrayList();
		designAttrList=((DesignAttributeDao) getDAOBean(DESIGN_ATTRIBUTE_DAO))
		.findListByLikeSelection(DesignAttribute.class, "name", likeKey+"%");
		
		return designAttrList;
	}
	
	
	
	

/* design attribute specification*/
	
	public AppDTO addDesignAttributeSpecification(AppDTO appDTO) {
		try{
			
			Map controllerMap=appDTO.getControllerMap();
			
			HttpServletRequest request=(HttpServletRequest) controllerMap.get("REQUEST");
			Integer designAttrId=Integer.parseInt(request.getParameter("design_attribute"));
			DesignAttribute designAttr=(DesignAttribute)((ProductDao) getDAOBean(PRODUCT_DAO)).
			findObjectByCondition
			(DesignAttribute.class,new String[] 
			{"id"},
			new Object[] {designAttrId});
			
			
		int count=Integer.parseInt(request.getParameter("designAttributeSpecCount"));
		for(int i=0;i<count;i++)
		{
			DesignAttributeSpecification designAttrSpec=new DesignAttributeSpecification();
			designAttrSpec.setDesignAttribute(designAttr);

			int sortOrder=Integer.parseInt(request.getParameter("spec_sort_order_"+i));
			designAttrSpec.setDescription(request.getParameter("spec_description_"+i));
			designAttrSpec.setName(request.getParameter("spec_name_"+i));
			designAttrSpec.setImage(request.getParameter("spec_image"+i));
			
			((DesignAttributeDao) getDAOBean(DESIGN_ATTRIBUTE_DAO)).addDesignAttributeSpecification(designAttrSpec);


		}
		appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		appDTO.setInfoMessage("DONE");

		}
		catch(Exception e){
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage("ERROR");

			e.printStackTrace(); 
			}
		return appDTO;
	}
	
	
	public AppDTO listAllDesignAttrSpec(AppDTO appDTO)
	{
		appDTO=((DesignAttributeDao) getDAOBean(DESIGN_ATTRIBUTE_DAO)).listAllDesignAttrSpec(appDTO);
		return appDTO;
		
	}

	
	
	public Collection getDesignAttrSpecByDesignAttrId(int designAttrId) {
		Collection designAtributeList=((ProductDao) getDAOBean(PRODUCT_DAO)).
		findListByCondition
		(DesignAttributeSpecification.class,new String[] 
		{"designAttribute.id"},
		new Object[] {designAttrId});

		return designAtributeList;
	}

	public AppDTO updateDesignAttributeSpecification(AppDTO appDTO) {
		try{
			
			Map controllerMap=appDTO.getControllerMap();
			
			HttpServletRequest request=(HttpServletRequest) controllerMap.get("REQUEST");
			Integer designAttrId=Integer.parseInt(request.getParameter("design_attribute"));
			
			Collection designAtributeSpecList=((ProductDao) getDAOBean(PRODUCT_DAO)).
			findListByCondition
			(DesignAttributeSpecification.class,new String[] 
			{"designAttribute.id"},
			new Object[] {designAttrId});
			TransactionManager.begin();
			Iterator iter=designAtributeSpecList.iterator();
			while(iter.hasNext())
			{
				DesignAttributeSpecification delObj=(DesignAttributeSpecification) iter.next();
				TransactionManager.txDelete(delObj);
			}

			DesignAttribute designAttr=(DesignAttribute)((ProductDao) getDAOBean(PRODUCT_DAO)).
			findObjectByCondition
			(DesignAttribute.class,new String[] 
			{"id"},
			new Object[] {designAttrId});
			
			
		int count=Integer.parseInt(request.getParameter("designAttributeSpecCount"));
		for(int i=0;i<count;i++)
		{
			DesignAttributeSpecification designAttrSpec=new DesignAttributeSpecification();
			designAttrSpec.setDesignAttribute(designAttr);

			int sortOrder=Integer.parseInt(request.getParameter("spec_sort_order_"+i));
			designAttrSpec.setDescription(request.getParameter("spec_description_"+i));
			designAttrSpec.setName(request.getParameter("spec_name_"+i));
			designAttrSpec.setImage(request.getParameter("spec_image"+i));
			
			TransactionManager.txCreate(designAttrSpec);


		}
		appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		appDTO.setInfoMessage("DONE");
TransactionManager.commit();
		}
		catch(Exception e){
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage("ERROR");
TransactionManager.rollback();
			e.printStackTrace(); 
			}
	 return appDTO;
	}





	public DesignAttributeDao getDesignAttributeDao() {
		return designAttributeDao;
	}





	public void setDesignAttributeDao(DesignAttributeDao designAttributeDao) {
		this.designAttributeDao = designAttributeDao;
	}

}
