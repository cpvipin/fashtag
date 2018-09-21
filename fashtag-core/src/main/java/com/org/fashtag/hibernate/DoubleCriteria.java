package com.org.fashtag.hibernate;


import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.transform.ResultTransformer;

import com.org.fashtag.hibernate.DoubleCriteria;

/**
 * @author Vipin CP
 * 
 */

public class DoubleCriteria implements Criteria {

	protected Criteria criteria1, criteria2;

	public Criteria getCriteria1() {
		return criteria1;
	}

	public Criteria getCriteria2() {
		return criteria2;
	}

	public static DoubleCriteria createDoubleCriteria(Session session,
			Class clazz) {
		return new DoubleCriteria(session.createCriteria(clazz), session
				.createCriteria(clazz));
	}

	protected DoubleCriteria(Criteria a, Criteria b) {
		this.criteria1 = a;
		this.criteria2 = b;
	}

	public Criteria add(Criterion arg0) {
		criteria1.add(arg0);
		criteria2.add(arg0);
		return this;
	}

	public Criteria addOrder(Order arg0) {
		
		criteria2.addOrder(arg0);
		return this;
	}

	public Criteria createAlias(String arg0, String arg1)
			throws HibernateException {
		return new DoubleCriteria(criteria1.createAlias(arg0, arg1), criteria2
				.createAlias(arg0, arg1));
	}

	public Criteria createCriteria(String arg0) throws HibernateException {
		return new DoubleCriteria(criteria1.createCriteria(arg0), criteria2
				.createCriteria(arg0));
	}

	public Criteria createCriteria(String arg0, String arg1)
			throws HibernateException {
		return new DoubleCriteria(criteria1.createCriteria(arg0, arg1),
				criteria2.createCriteria(arg0, arg1));
	}

	public boolean equals(Object obj) {
		return criteria1.equals(obj);
	}

	public String getAlias() {
		return criteria1.getAlias();
	}

	public int hashCode() {
		return criteria1.hashCode() + criteria2.hashCode();
	}

	public List list() throws HibernateException {
		return criteria1.list();
	}

	public ScrollableResults scroll() throws HibernateException {
		return criteria1.scroll();
	}

	public ScrollableResults scroll(ScrollMode arg0) throws HibernateException {
		return criteria1.scroll(arg0);
	}

	public Criteria setCacheable(boolean arg0) {
		criteria1.setCacheable(arg0);
		criteria2.setCacheable(arg0);
		return this;
	}

	public Criteria setCacheMode(CacheMode arg0) {
		criteria1.setCacheMode(arg0);
		criteria2.setCacheMode(arg0);
		return this;
	}

	public Criteria setCacheRegion(String arg0) {
		criteria1.setCacheRegion(arg0);
		criteria2.setCacheRegion(arg0);
		return this;
	}

	public Criteria setComment(String arg0) {
		criteria1.setComment(arg0);
		criteria2.setComment(arg0);
		return this;
	}

	public Criteria setFetchMode(String arg0, FetchMode arg1)
			throws HibernateException {
		criteria1.setFetchMode(arg0, arg1);
		criteria2.setFetchMode(arg0, arg1);
		return this;
	}

	public Criteria setFetchSize(int arg0) {
		criteria1.setFetchSize(arg0);
		criteria2.setFetchSize(arg0);
		return this;
	}

	public Criteria setFirstResult(int arg0) {
		criteria1.setFirstResult(arg0);
		criteria2.setFirstResult(arg0);
		return this;
	}

	public Criteria setFlushMode(FlushMode arg0) {
		criteria1.setFlushMode(arg0);
		criteria2.setFlushMode(arg0);
		return this;
	}

	public Criteria setLockMode(String arg0, LockMode arg1) {
		criteria1.setLockMode(arg0, arg1);
		criteria2.setLockMode(arg0, arg1);
		return this;
	}

	public Criteria setLockMode(LockMode arg0) {
		criteria1.setLockMode(arg0);
		criteria2.setLockMode(arg0);
		return this;
	}

	public Criteria setMaxResults(int arg0) {
		criteria1.setMaxResults(arg0);
		criteria2.setMaxResults(arg0);
		return this;
	}

	public Criteria setProjection(Projection arg0) {
		criteria1.setProjection(arg0);
		criteria2.setProjection(arg0);
		return this;
	}

	public Criteria setResultTransformer(ResultTransformer arg0) {
		criteria1.setResultTransformer(arg0);
		criteria2.setResultTransformer(arg0);
		return this;
	}

	public Criteria setTimeout(int arg0) {
		criteria1.setTimeout(arg0);
		criteria1.setTimeout(arg0);
		return this;
	}

	public String toString() {
		return criteria1.toString();
	}

	public Object uniqueResult() throws HibernateException {
		return criteria1.uniqueResult();
	}

	public Criteria createAlias(String arg0, String arg1, int arg2)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	public Criteria createCriteria(String arg0, int arg1)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	public Criteria createCriteria(String arg0, String arg1, int arg2)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	public Criteria createAlias(String associationPath, String alias,
			int joinType, Criterion withClause) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	public Criteria createCriteria(String associationPath, String alias,
			int joinType, Criterion withClause) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isReadOnlyInitialized() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	public Criteria setReadOnly(boolean readOnly) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}