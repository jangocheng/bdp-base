package com.platform.spring.util.java;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;



public class FieldUtil {
	public static Map<String, String> getFieldNames(Class clz) {
		Map<String, String> nameTypeMap = new HashMap<String, String>();
		Field[] field = clz.getDeclaredFields();
		String name = StringUtils.EMPTY;
		String type = StringUtils.EMPTY;
		for (int i = 0; i < field.length; i++) {
			name = field[i].getName();
			type = field[i].getType().toString();
			if ("class java.lang.String".equals(type) || "class java.lang.Integer".equals(type) || "class java.lang.Long".equals(type) || "class java.lang.Boolean".equals(type)) {
				nameTypeMap.put(name, type);
			}
		}
		return nameTypeMap;
	}
}
