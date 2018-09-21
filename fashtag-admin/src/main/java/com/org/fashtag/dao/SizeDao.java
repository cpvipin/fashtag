package com.org.fashtag.dao;

import com.org.fashtag.AppDTO;
import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.model.Size;

public interface SizeDao extends BaseDao {
/* size */
	
	public void addSize(Size size);

	
	public void updateSize(Size size);

	
	public AppDTO listAllSizes(AppDTO appDTO);
	
	
	public void deleteSize(Size size);
	/*
	public Size getSizeById(int id);*/

}
