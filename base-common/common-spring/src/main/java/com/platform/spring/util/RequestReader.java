package com.platform.spring.util;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

/**   
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author boat 
 * @date 2020年4月5日 下午5:24:02
 * @version V1.0   
 */

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
