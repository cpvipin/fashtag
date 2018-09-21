package com.org.fashtag.service;

import java.util.Collection;

import com.org.fashtag.AppDTO;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Size;
import com.org.fashtag.service.BaseService;

public interface BodyMeasurementAttributeService extends BaseService {
	
/* size */
	
	public AppDTO addBodyMeasurementAttribute(AppDTO appDTO);
	
	public AppDTO updateBodyMeasurementAttribute(AppDTO appDTO);
	
	public AppDTO listAllBodyMeasurementAttributes(AppDTO appDTO);
	
	public AppDTO deleteBodyMeasurementAttribute(AppDTO appDTO) ;

	public BodyMeasurementAttribute getBodyMeasurementAttributeById(int id);
	
	public Collection getAllActiveBodyMeasurementAttribute();
	
	public Collection getBodyMeasurementAttributeByGender(int gender);



}
