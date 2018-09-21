package com.org.fashtag.model;

import com.org.fashtag.model.base.BaseModel;


public class DesignAttributeSpecification extends BaseModel {

	private Integer id;
	private DesignAttribute designAttribute; 
	private String description;
	private String image;
	private String name;
    private int sortOrder;

	
	/* getters and setters */
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public DesignAttribute getDesignAttribute() {
		return designAttribute;
	}
	public void setDesignAttribute(DesignAttribute designAttribute) {
		this.designAttribute = designAttribute;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	
	
	

}
