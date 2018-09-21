package com.org.fashtag.dao;

import com.org.fashtag.AppDTO;
import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.model.BodyMeasurementAttribute;

public interface BodyMeasurementAttributeDao extends BaseDao {

	
	public void addBodyMeasurementAttribute(BodyMeasurementAttribute bodyMeasurementAttribute);

	
	public void updateBodyMeasurementAttribute(BodyMeasurementAttribute bodyMeasurementAttribute);

	
	public AppDTO listAllBodyMeasurementAttributes(AppDTO appDTO);
	
	
	public void deleteBodyMeasurementAttribute(BodyMeasurementAttribute bodyMeasurementAttribute);


}
