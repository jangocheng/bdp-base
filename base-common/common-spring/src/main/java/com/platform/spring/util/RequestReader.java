package com.platform.spring.util;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;


public class RequestReader {
	
	public static String readContent(HttpServletRequest request){
		StringBuffer content = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				content.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}
	
}
