package com.org.fashtag.model;

import com.org.fashtag.model.base.AbstractModel;
import com.org.fashtag.model.base.BaseModel;

/**
 * @hibernate.query name = "ordersStatus.findAllOrdersStatusByStatus" query = " 
 * from OrdersStatus as ordersStatus where ordersStatus.activeStatus=:status order by ordersStatus.name"

*@hibernate.query name="orderStat.getOrderStatusByName" 
query="from OrdersStatus as orderStat  where orderStat.name=:name "

 * @author vipin
 *
 */
public class OrdersStatus extends BaseModel{

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
