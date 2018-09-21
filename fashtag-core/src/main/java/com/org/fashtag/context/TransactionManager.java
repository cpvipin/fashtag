package com.org.fashtag.context;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.org.fashtag.dao.impl.BaseDaoImpl;
import com.org.fashtag.model.base.BaseModel;
/*
 * @author "vipin cp"
 */
public class TransactionManager extends BaseDaoImpl{
	
	
		private static TransactionManager transactionManager = null;
		private static ThreadLocal threadSession = new ThreadLocal();
		private static ThreadLocal threadTransaction = new ThreadLocal();

		private static void init() {
			Session s= null;
			transactionManager = new TransactionManager();		
			try{		
			 s = transactionManager.getSession();
			}catch (Exception e) {
				SessionFactory sf = (SessionFactory)((BaseDaoImpl) ApplicationContext.getApplicationContext()
						.getBean(BeanConstants.BASE_DAO_IMPL)).getSessionFactory();			
				s=sf.openSession();
			}
			threadSession.set(s);
		}

		public static void begin() {
			init();
			Transaction tx = (Transaction) threadTransaction.get();
			try {
				if (tx == null) {
					Session session = (Session) threadSession.get();

					tx=session.beginTransaction();
					threadTransaction.set(tx);
				}
			} catch (HibernateException e) {
				throw new HibernateException("", e);
			}
		}
		
		

		public static void commit() {
			Session session = (Session) threadSession.get();
			if (session != null && session.isConnected() && session.isOpen()) {
				Transaction tx = (Transaction) threadTransaction.get();
				try {
					if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
						tx.commit();
					}
					threadTransaction.set(null);
				} catch (Exception e) {
					rollback();
					throw new HibernateException("Error while commit", e);
				} finally {
					closeSession();
				}
			}
		}

		public static void rollback() {
			Session session = (Session) threadSession.get();

			if (session != null && session.isConnected() && session.isOpen()) {
				Transaction tx = (Transaction) threadTransaction.get();
				try {
					threadTransaction.set(null);
					if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
						tx.rollback();
					}
				} catch (HibernateException e) {
					throw new HibernateException("Error while rollback", e);
				} finally {
					closeSession();
				}
			}
		}

		
		public static boolean txCreate(BaseModel baseModel) {
			return txCreate(baseModel, null);
		}
		
		
		public static boolean txCreate(BaseModel baseModel, String modifiedGroup) {
			try {
				Session session = (Session) threadSession.get();			
				session.save(baseModel);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		
		public static boolean txUpdate(BaseModel baseModel) {
			return txUpdate(baseModel, null);
		}
		
		
		public static boolean txUpdate(BaseModel baseModel, String modifiedGroup) {
			try {
				Session session = (Session) threadSession.get();
				session.update(baseModel);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		public static boolean txDelete(BaseModel baseModel) {
			try {
				Session session = (Session) threadSession.get();
				session.delete(baseModel);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		
		public static void closeSession() {
			try {			
				Session session = (Session) threadSession.get();
				threadSession.set(null);
				if (session != null && session.isOpen()) {
					session.close();
				}
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}

		public static TransactionManager getInstance() {
			return transactionManager;
		}

		public Session getTxSession() {
			return (Session) threadSession.get();
		}
	}

	
	