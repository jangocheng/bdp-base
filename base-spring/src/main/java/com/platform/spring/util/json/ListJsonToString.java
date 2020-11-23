package com.platform.spring.util.json;

import java.util.List;



public class ListJsonToString {

	public static String toString(List<String> jsonList) {
		if (jsonList == null || jsonList.size() == 0) {
			return "[]";
		}
		StringBuffer sb = new StringBuffer("[");
		for (String json : jsonList) {
			sb.append(json);
		}
		sb.append("]");
		return sb.toString();
	}
}
