package com.org.fashtag.model;

import java.util.Date;

import com.org.fashtag.model.base.BaseModel;



/**
 * @hibernate.class table="banner_images"    
 * 
@hibernate.query name="bannerImage.getBannerImageByLayoutAndFilter" 
query="select bi.image from  BannerImages as bi where bi.layoutName=:layout and bi.filterId=:filterId"                  
*/

public class BannerImages extends BaseModel {

	private Integer id;
	private String layoutName;
	private int filterId;
	private String image;
	private Date dateAdded;
	
	
	/*getters and setters*/

	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLayoutName() {
		return layoutName;
	}
	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}
	public int getFilterId() {
		return filterId;
	}
	public void setFilterId(int filterId) {
		this.filterId = filterId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	
	
		
	
}
