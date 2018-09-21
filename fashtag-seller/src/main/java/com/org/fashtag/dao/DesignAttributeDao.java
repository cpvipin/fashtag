package com.org.fashtag.dao;

import com.org.fashtag.AppDTO;
import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.model.DesignAttribute;
import com.org.fashtag.model.DesignAttributeSpecification;

public interface DesignAttributeDao extends BaseDao {

/* Design attribute */
	
	public void addDesignAttribute(DesignAttribute designAttribute);
	
	public AppDTO listAllDesignAttributes(AppDTO appDTO);

	public void updateDesignAttribute(DesignAttribute designAttribute);

	public void deleteDesignAttribute(DesignAttribute designAttribute);

	/* design attribute specification */
	
	public void addDesignAttributeSpecification(DesignAttributeSpecification designAttrSpec);
	
	public AppDTO listAllDesignAttrSpec(AppDTO appDTO);

}
