package com.org.fashtag.model.base;

import java.util.Date;

public abstract class AbstractModel extends BaseModel {

	private Date dateAdded;
	private Date dateModified;
	
	
	
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	
	
}
