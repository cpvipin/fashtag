package com.org.fashtag.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.PropertyProjection;

import com.org.fashtag.beans.Page;
import com.org.fashtag.hibernate.DoubleCriteria;
/**
 * @author Vipin CP
 */

public class PaginationCriteria extends DoubleCriteria {

	public static final int ORDER_ASC = 0;

	public static final int ORDER_DES = 1;

	protected PaginationCriteria(Criteria a, Criteria b) {
		super(a, b);
	}

	public static PaginationCriteria getInstance(Criteria a, Criteria b) {
		return new PaginationCriteria(a,b);
	}
	public Criteria setFirstResult(int arg0) {
		criteria2.setFirstResult(arg0);
		return this;
	}

	public Criteria setMaxResults(int arg0) {
		criteria2.setMaxResults(arg0);
		return this;
	}

	public Criteria addOrder(Order arg0) {
		criteria2.addOrder(arg0);
		return this;
	}

	public Criteria setProjection(Projection arg0) {
		if (!(arg0 instanceof PropertyProjection)) {
			throw new HibernateException(
					"Pagination criteria only supports specification of a PropertyProjection");
		}
		criteria1.setProjection(arg0);
		return this;
	}

	public Page createPage(int pageSize, int firstRow) {
		return createPage(pageSize, firstRow, true, false);
	}

	public Page createPageWithCount(int pageSize, int firstRow) {
		return createPage(pageSize, firstRow, true, true);
	}

	public Page createPage(int pageSize, int firstRow, boolean paginate,
			boolean needCount) {
		if (paginate) {
			int count = 0;
			if (needCount) {
				getCriteria1().setProjection(Projections.rowCount());
				count = ((Integer) getCriteria1().uniqueResult()).intValue();
			}
			getCriteria2().setMaxResults(pageSize);
			
			if(firstRow >0){
				getCriteria2().setFirstResult(firstRow);
			}else{
				getCriteria2().setFirstResult(0);
			}
			
			List list = getCriteria2().list();
			return new Page(list, pageSize, firstRow, count);
		} else {
			List list = getCriteria2().list();
			return new Page(list, list.size());

		}

	}

	public Page createPage(Page page){		
		
		
		if (page.getRecordCount()<=0) {
			getCriteria1().setProjection(Projections.rowCount());
			page.setRecordCount(((Long) getCriteria1().uniqueResult()).intValue());
			}
		
		getCriteria2().setMaxResults(page.getPageSize());
		
		if(page.getFirstRow() >0){
		getCriteria2().setFirstResult(page.getFirstRow());
		}else{
			getCriteria2().setFirstResult(0);
		}
		page.setResultList(getCriteria2().list());
		return page;
	}
	
	public Criteria getSortByCriteria(Criteria criteria, String sortBy,
			int sortOrder) {
		Criteria tempCriteria = criteria;
		if (sortBy.indexOf('.') >= 0) {
			String[] tokens = sortBy.split("\\.");
			for (int i = 0; i < tokens.length - 1; i++) {
				tempCriteria = tempCriteria.createCriteria(tokens[i]);
			}
			sortBy = tokens[tokens.length - 1];
		}
		if (sortOrder == ORDER_ASC) {
			tempCriteria.addOrder(Order.asc(sortBy));
		} else {
			tempCriteria.addOrder(Order.desc(sortBy));
		}
		return tempCriteria;
	}
	
}