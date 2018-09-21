package com.org.fashtag.dao.impl;

import com.org.fashtag.util.CommonUtils;
/**
* @author(name="vipin c p") 
*/
public final class QueryGenerator {

	
	public static String generateConditionQuery(Class aClass,
			String returnProperty, String[] propertyName,
			Object[] propertyValue, String orderProperty, boolean isDescending) {

		if (propertyName != null && propertyValue != null) {
			if (propertyName.length != propertyValue.length)
				throw new RuntimeException(
						"Null or Un-mached property name-value pair");
		}

		StringBuffer sb = new StringBuffer();
		try {
			if (CommonUtils.isEmpty(returnProperty)) 
			{
				sb.append("select al from ");
			} 
			else {
				
					sb.append("select al.").append(returnProperty).append(
							" from ");
				
			}
			sb.append(aClass.getName()).append(" as").append(" al");
			if (propertyName != null && propertyValue != null) {
				for (int i = 0; i < propertyValue.length; i++) {
					if (i == 0) {
						sb.append(" where ");
					} else {
						sb.append(" and ");
					}
					sb.append(" al.").append(propertyName[i]);
					if (propertyValue[i] == null) {
						sb.append(" is null ");
					} else {
						sb.append(" = ? ");
					}
				}
			}
			sb.append(" order by");
			if (orderProperty != null) {
				sb.append(" al.").append(orderProperty);
				if (isDescending) {
					sb.append(" desc");
				}
			} else {
				sb.append(" al.id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	public static String generateQueryUtilQuery(Class aClass,
			String[] returnProperty, String[] propertyNames,
			Object[] propertyValues, String orderProperty,
			boolean isDescending, boolean isLike) {
		
		return generateQueryUtilQuery(aClass, returnProperty, propertyNames,
				propertyValues, orderProperty, isDescending, isLike, false);
		
	}
	
	
	
	
	public static String generateConditionQury(Class aClass,
			String returnProperty, String[] propertyName,
			Object[] propertyValue, String orderProperty, boolean isDescending) {

		if (propertyName != null && propertyValue != null) {
			if (propertyName.length != propertyValue.length)
				throw new RuntimeException(
						"Null or Un-mached property name-value pair");
		}

		StringBuffer sb = new StringBuffer();
		try {
			if (CommonUtils.isEmpty(returnProperty)) {
				sb.append("select al from ");
			} else {
				
					sb.append("select al.").append(returnProperty).append(
							" from ");
				
			}
			sb.append(aClass.getName()).append(" as").append(" al");
			if (propertyName != null && propertyValue != null) {
				for (int i = 0; i < propertyValue.length; i++) {
					if (i == 0) {
						sb.append(" where ");
					} else {
						sb.append(" and ");
					}
					sb.append(" al.").append(propertyName[i]);
					if (propertyValue[i] == null) {
						sb.append(" is null ");
					} else {
						sb.append(" = ? ");
					}
				}
			}
			sb.append(" order by");
			if (orderProperty != null) {
				sb.append(" al.").append(orderProperty);
				if (isDescending) {
					sb.append(" desc");
				}
			} else {
				sb.append(" al.id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return sb.toString();
	}
	
	
	
	
	
	
	

	
	
	
	
	public static String generateQueryUtilQuery(Class aClass,
			String[] returnProperty, String[] propertyNames,
			Object[] propertyValues, String orderProperty,
			boolean isDescending, boolean isLike, boolean isDistinct) {

		if (propertyNames != null && propertyValues != null) {
			if (propertyNames.length != propertyValues.length)
				throw new RuntimeException(
						"Null or Un-mached property name-value pair");
		}
		StringBuffer sb = null;
		if (isDistinct) {
			sb = new StringBuffer("select distinct ");
		} else {
			sb = new StringBuffer("select ");
		}
		try {
			for (int i = 0; i < returnProperty.length; i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append("al.").append(returnProperty[i]);
			}
			
			sb.append(" from ");
			sb.append(aClass.getName()).append(" as al ");
			if (propertyNames != null) {
				for (int i = 0; i < propertyNames.length; i++) {
					if (i == 0) {
						sb.append(" where ");
					} else {
						sb.append(" and ");
					}
					if (isLike && propertyValues != null
							&& propertyValues[i] instanceof String) {
						sb.append(" upper(").append(" al.");
						sb.append(propertyNames[i]).append(")");
						sb.append(" like ? ");
					} else if (isLike
							&& (propertyValues == null || propertyValues[i] == null)) {
						sb.append(" al.").append(propertyNames[i]);
						sb.append(" like '%'");
					} else {
						sb.append(" al.").append(propertyNames[i]);
						if (propertyValues == null || propertyValues[i] == null) {
							sb.append(" is null ");
						} else {
							sb.append(" = ? ");
						}
					}
				}
			}
			if (!CommonUtils.isEmpty(orderProperty)) {
				sb.append(" order by ");
				sb.append("al.").append(orderProperty);
				if (isDescending) {
					sb.append(" desc ");
				}
			} else if (!isDistinct) {
				sb.append(" order by ");
				sb.append(" al.id ");
				if (isDescending) {
					sb.append(" desc ");
				}
			}
		} catch(Exception e)
		{
			
		}
		return sb.toString();
	}
	
	
	public static String listByLikeSelectionQury(Class aClass,
			String returnProperty, String propertyName, String propValue,
			String orderProperty, boolean isDescending) {

		StringBuffer sb = new StringBuffer();
		try {
			if (CommonUtils.isEmpty(returnProperty)) {
				sb.append("select al from ");
			} else {
				
					sb.append("select al.").append(returnProperty).append(
							" from ");
				
			}
			sb.append(aClass.getName()).append(" as").append(" al");
			if (!CommonUtils.isEmpty(propertyName)) {
				sb.append(" where").append(" upper(");
				sb.append(" al.").append(propertyName).append(")");
				if (propValue == null) {
					sb.append(" like '%' ");
				} else {
					sb.append(" like ? ");
				}
			}
			sb.append(" order by");
			if (orderProperty != null) {
				sb.append(" al.").append(orderProperty);
				if (isDescending) {
					sb.append(" desc");
				}
			} else {
				sb.append(" al.id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return sb.toString();
	}
	
	
	
	public static String generateDuplicationCheckQuery(Class aClass,
			String fieldName, Object fieldValue, String propertyName,
			Object propValue) {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("select al.id").append(" from ");
			sb.append(aClass.getName()).append(" as").append(" al");

			if (!CommonUtils.isEmpty(fieldName)) {
				sb.append(" where").append(" al.").append(fieldName);
				sb.append(" = ? ");
			}

			if (!CommonUtils.isEmpty(propertyName)) {
				sb.append(" and ").append(" al.").append(propertyName);
				sb.append(" <> ?");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String generateBulkDeleteQuery(Class aClass,
			String[] propertyName, Object[] propertyValue) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append(aClass.getName()).append(" where ");
		for (int i = 0; i < propertyValue.length; i++) {
			if (propertyName[i].indexOf(".") > 0) {
				throw new RuntimeException(
						"Use simple property values; Please omit '.' from property");
			}
			if (i != 0) {
				sb.append(" and ");
			}
			// Field aField = ReflectionUtil.getFieldByName(aClass,
			// propertyName[i]);
			// if (ReflectionUtil.getType(aField).equals("ModelBase")) {
			// sb.append(propertyName[i]).append(".id");
			// } else {
			sb.append(propertyName[i]);
			// }

			if (propertyValue[i] == null) {
				sb.append(" is null ");
			} else {
				sb.append(" = ? ");
			}
		}
		return sb.toString();
	}
	
	
	
}
