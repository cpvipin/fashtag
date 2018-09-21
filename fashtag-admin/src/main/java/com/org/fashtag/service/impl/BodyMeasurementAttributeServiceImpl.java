package com.org.fashtag.service.impl;

import java.util.Collection;
import java.util.Map;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.context.AdminBeanConstants;
import com.org.fashtag.dao.BodyMeasurementAttributeDao;
import com.org.fashtag.dao.CustomerDao;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.CustomerIp;
import com.org.fashtag.service.BodyMeasurementAttributeService;
import com.org.fashtag.service.impl.BaseServiceImpl;

public class BodyMeasurementAttributeServiceImpl extends BaseServiceImpl implements BodyMeasurementAttributeService,
		AdminBeanConstants {

	
	private BodyMeasurementAttributeDao bodyMeasuremenetAttributeDao;

	
	
	public AppDTO addBodyMeasurementAttribute(AppDTO appDTO) {
		Map controllerMap=appDTO.getControllerMap();
		BodyMeasurementAttribute bodyMeasurementAttribute=(BodyMeasurementAttribute)controllerMap.get("BODY_MEASUREMENT_ATTR");
		bodyMeasuremenetAttributeDao.addBodyMeasurementAttribute(bodyMeasurementAttribute);
		appDTO.setInfoMessage(getResourceMessage("MEASUREMENT_ATTRIBUTE_ADDED"));
		
		return appDTO;
	}
	
	
	

	
	public AppDTO updateBodyMeasurementAttribute(AppDTO appDTO) {
			try{

			Map controllerMap=appDTO.getControllerMap();
			BodyMeasurementAttribute bodyMeasurementAttribute=(BodyMeasurementAttribute)controllerMap.get("BODY_MEASUREMENT_ATTR");
			
					
			bodyMeasuremenetAttributeDao.updateBodyMeasurementAttribute(bodyMeasurementAttribute);
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
					appDTO.setInfoMessage(getResourceMessage("MEASUREMENT_ATTRIBUTE_UPDATED"));
			}catch(Exception e){e.printStackTrace();}
			
				return appDTO;
		
		
		
	}

	
	
	
	public AppDTO listAllBodyMeasurementAttributes(AppDTO appDTO)
	{
		
		appDTO=bodyMeasuremenetAttributeDao.listAllBodyMeasurementAttributes(appDTO);
		return appDTO;
	}
	
	
	public Collection getAllActiveBodyMeasurementAttribute()
	{
		Collection activeAttrColl=bodyMeasuremenetAttributeDao.
		findListByCondition(BodyMeasurementAttribute.class, 
				 new String[]{ "activeStatus"}
				, new Object[]{true});
		return activeAttrColl;
	}
	
	public BodyMeasurementAttribute getBodyMeasurementAttributeById(int id)
	{
		BodyMeasurementAttribute bodyMeasurementAttribute=(BodyMeasurementAttribute)bodyMeasuremenetAttributeDao.
				findObjectByCondition
				(BodyMeasurementAttribute.class,new String[] 
				{"id"},
				new Object[] {id});
		
		return bodyMeasurementAttribute;
	}


	
	

	public AppDTO deleteBodyMeasurementAttribute(AppDTO appDTO) {
		try
		{
			Map controllerMap=appDTO.getControllerMap();
			BodyMeasurementAttribute bodyMeasurementAttribute=(BodyMeasurementAttribute)controllerMap.get("BODY_MEASUREMENT_ATTR");
			((BodyMeasurementAttributeDao) getDAOBean(BODY_MEASUREMENET_ATTRIBUTE_DAO)).deleteBodyMeasurementAttribute(bodyMeasurementAttribute);
			appDTO.setInfoMessage(getResourceMessage("MEASUREMENT_ATTRIBUTE_DELETED"));
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
	
	
	public Collection getBodyMeasurementAttributeByGender(int gender){
		
		
		Collection bodyMeasurementAttrColl = (Collection) ((BodyMeasurementAttributeDao) getDAOBean(BODY_MEASUREMENET_ATTRIBUTE_DAO))
		.findListByCondition(
				BodyMeasurementAttribute.class, 
				new String[] { "gender","activeStatus" },
				new Object[] {  gender,true});	
		
		return bodyMeasurementAttrColl;
	}



/* getters and setters */

	public BodyMeasurementAttributeDao getBodyMeasuremenetAttributeDao() {
		return bodyMeasuremenetAttributeDao;
	}





	public void setBodyMeasurementAttributeDao(
			BodyMeasurementAttributeDao bodyMeasuremenetAttributeDao) {
		this.bodyMeasuremenetAttributeDao = bodyMeasuremenetAttributeDao;
	}





	

}
