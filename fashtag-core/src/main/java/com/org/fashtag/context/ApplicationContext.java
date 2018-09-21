/* * Created on Sep 21, 2004
 * @author vipin c p infobits 
*/
package com.org.fashtag.context;


import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;




import com.org.fashtag.context.ApplicationContext;
import com.org.fashtag.context.BeanConstants;

import com.org.fashtag.util.CommonUtils;



public class ApplicationContext implements BeanConstants{

	private static ApplicationContext applicationContext = null;
	private BeanFactory beanFactory;
	private static Properties beanFactoryFileProps = new Properties();
	private static Map activeSessionMap;
	
	
	static 
	{
		Thread.currentThread().setContextClassLoader(ApplicationContext.class.getClassLoader());
		applicationContext = new ApplicationContext();
	}
	
	private ApplicationContext() throws BeansException {
		
		
		try{
			beanFactoryFileProps.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("AppContextFile.properties"));
			Collection props = beanFactoryFileProps.values();
			
			String[] configFiles = null;
			if (!CommonUtils.isEmpty(props)) {
				Object[] config = props.toArray();
				configFiles = new String[config.length];
				for (int i = 0; i < config.length; i++) {
					configFiles[i] = (String) config[i];
				}
			} 
			else
			{
				configFiles=new String[] {"ApplicationContext.xml"};
			}
			beanFactory = new ClassPathXmlApplicationContext(configFiles);
			}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Exception loading application context");
		}
	}

	public synchronized static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public Object getBean(String beanName) {
		return beanFactory.getBean(beanName);
	}
	
	public static Map getActiveSessionMap() {
		return activeSessionMap;
	}

}
