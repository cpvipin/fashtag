package com.org.fashtag.model;

import com.org.fashtag.model.base.AbstractModel;
import com.org.fashtag.model.base.BaseModel;

/**
 * 
*@hibernate.query name="cust.getCustomerByUserId" 
query="from ReturnStatus as retStat  where retStat.name=:name "

 * @author vipin
 *
 */
public class ReturnStatus extends BaseModel{

	private Integer id;
	private String name;
	private boolean activeStatus;
	
	
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
	
	public boolean getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
	
	

}
