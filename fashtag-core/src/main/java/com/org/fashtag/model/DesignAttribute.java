package com.org.fashtag.model;

import java.util.Set;

import com.org.fashtag.model.base.AbstractModel;

/**
* @hibernate.class table="design_attribute"    
* 
@hibernate.query name="prod.getDesignAttributesByProduct"
 query="select dats.name ,dats.id , dat.name ,dat.id from DesignAttributeSpecification as dats inner join  dats.designAttribute as dat where dat.id in(select das.designAttribute.id from ProductToDesignAttributeSpecification as pds inner join  pds.designAttributeSpecification as das where pds.product.id=:productId) order by dat.name"
*/
public class DesignAttribute extends AbstractModel {

	private Integer id;
	private String name;
	private String description;
	private boolean activeStatus;
	private Integer sortOrder;
	private String displayText;
	
	private Set designAttributeSpecifications;

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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getDisplayText() {
		return displayText;
	}
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	public Set getDesignAttributeSpecifications() {
		return designAttributeSpecifications;
	}
	public void setDesignAttributeSpecifications(Set designAttributeSpecifications) {
		this.designAttributeSpecifications = designAttributeSpecifications;
	}
	
	
	
	

}
