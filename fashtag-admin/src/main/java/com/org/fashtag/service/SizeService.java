package com.org.fashtag.service;

import java.util.Collection;

import com.org.fashtag.AppDTO;
import com.org.fashtag.model.Size;
import com.org.fashtag.service.BaseService;

public interface SizeService extends BaseService {
	
/* size */
	
	public AppDTO addSize(AppDTO appDTO);
	
	public AppDTO updateSize(AppDTO appDTO);
	
	public AppDTO listAllSizes(AppDTO appDTO);
	
	public AppDTO deleteSize(AppDTO appDTO) ;

	public Size getSizeById(int id);
	
	public Collection getAllActiveSize();


}
