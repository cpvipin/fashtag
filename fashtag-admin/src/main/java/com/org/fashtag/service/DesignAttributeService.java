package com.org.fashtag.service;

import java.util.Collection;

import com.org.fashtag.AppDTO;
import com.org.fashtag.model.DesignAttribute;
import com.org.fashtag.service.BaseService;

public interface DesignAttributeService extends BaseService {

/* design Attribute */
	
	public AppDTO addDesignAttribute(AppDTO appDTO);
	
	public AppDTO listAllDesignAttributes(AppDTO appDTO);

	public DesignAttribute getDesignAttributeById(int id);

	public AppDTO updateDesignAttribute(AppDTO appDTO);
	
	public AppDTO deleteDesignAttribute(AppDTO appDTO) ;
	
	public Collection getAllActiveDesignAttributes();
	
	public Collection getDesignAttributeListByKey(String likeKey);


/* design attribute specification */
	
	public AppDTO addDesignAttributeSpecification(AppDTO appDTO);

	public AppDTO listAllDesignAttrSpec(AppDTO appDTO);

	public Collection getDesignAttrSpecByDesignAttrId(int id);
	
	public AppDTO updateDesignAttributeSpecification(AppDTO appDTO);
	
	
	
	
}
