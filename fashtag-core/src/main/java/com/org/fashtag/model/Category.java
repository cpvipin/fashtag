package com.org.fashtag.model;

import java.util.Set;

import com.org.fashtag.model.base.AbstractModel;


/**
 * @hibernate.class table="category"    
 * 
@hibernate.query name="ca.getChildCategories" query="select ca.name,ca.id from Category as ca where ca.id not in (select c2.parent.id from Category as c2 where c2.parent.id=ca.id) and ca.name like :name and ca.activeStatus=true "                  
*
*
*
*
@hibernate.query name="ca.getParentCategoriesByStatus" query="select ca.name,ca.id from Category 
as ca where ca.activeStatus=:activeStatus  and ca.parent is null order by sortOrder"                  


@hibernate.query name="ca.getLeafNodesOfCategoryById" query="from Category as ca where ca.parent.id =:catId "                  



*
*/
public class Category extends AbstractModel {

	private Integer id;
	private String name;
	private String icon;
	private Category parent;
	private int sortOrder;
	private boolean activeStatus;
	
	
	private Set childCategory;

	
	
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Category getParent() {
		return parent;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public boolean getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
	public Set getChildCategory() {
		return childCategory;
	}
	public void setChildCategory(Set childCategory) {
		this.childCategory = childCategory;
	}
	

}
