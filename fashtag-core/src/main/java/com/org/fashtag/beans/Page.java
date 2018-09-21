
package com.org.fashtag.beans;

import java.io.Serializable;
import java.util.List;
/**
 * @author(name="vipin c p") 
 */
public class Page implements Serializable {
	
	private List resultList;

	private int pageSize; 

	private long recordCount;

	private int firstRow; 

	private int order; 

	private String orderBy; 

	private static final int defaultPageSize = 5;

	public Page() {
		// default constructor
		if(this.pageSize==0)
		{
		this.pageSize = defaultPageSize;
		}
	}

	public Page(List list, int pageSize, int firstRow, int count, int order) {
		this.resultList = list;
		setPageSize(pageSize);
		this.firstRow = firstRow;
		this.recordCount = count;
		this.order = order;
	}

	public Page(List list, int pageSize, int firstRow, int count) {
		this.resultList = list;
		setPageSize(pageSize);
		this.firstRow = firstRow;
		this.recordCount = count;
	}

	public Page(List list, int count) {
		this.resultList = list;
		this.recordCount = count;
		this.pageSize = defaultPageSize;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public List getResultList() {
		return resultList;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	public void setPageSize(int pageSize) {
		if (pageSize == 0) {
			this.pageSize = defaultPageSize;
		} else {
			this.pageSize = pageSize;
		}
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}



}
