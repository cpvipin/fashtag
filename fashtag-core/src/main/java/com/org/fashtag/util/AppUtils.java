package com.org.fashtag.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
/**
* @author(name="vipin c p") 
*/
public class AppUtils {

	
	private AppUtils() {}
	
	public static Collection getMyObject(Class myClass, Collection result,
			String[] myAttribute) {
		List results = new ArrayList();
		try {
			Iterator iter = result.iterator();
			while (iter.hasNext()) {
				Object bean = myClass.newInstance();
				if (myAttribute.length > 1) {
					Object[] row = (Object[]) iter.next();
					for (int j = 0; j < myAttribute.length; j++) {
						if (row[j] != null) {
							initialisePath(bean, myAttribute[j]);
							PropertyUtils.setProperty(bean, myAttribute[j],
									row[j]);
						}
					}
				} else {
					Object row = (Object) iter.next();
					if (row != null) {
						initialisePath(bean, myAttribute[0]);
						PropertyUtils.setProperty(bean, myAttribute[0], row);
					}
				}

				results.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	
	public static void initialisePath(final Object bean, final String fieldName)
	throws Exception {
int dot = fieldName.indexOf('.');
while (dot >= 0) {
	String attributeName = fieldName.substring(0, dot);
	Class attributeClass = PropertyUtils.getPropertyType(bean,
			attributeName);
	if (PropertyUtils.getProperty(bean, attributeName) == null) {
		PropertyUtils.setProperty(bean, attributeName, attributeClass
				.newInstance());
	}
	dot = fieldName.indexOf('.', dot + 1);
}
}
}
