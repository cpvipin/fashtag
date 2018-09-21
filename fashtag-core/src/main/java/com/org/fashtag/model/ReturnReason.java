package com.org.fashtag.model;

import com.org.fashtag.model.base.AbstractModel;
import com.org.fashtag.model.base.BaseModel;

/**
 
 * @author vipin
 *
 */
public class ReturnReason extends BaseModel{

	private Integer id;
	private String name;
	
	
	/*getters and setters*/
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
