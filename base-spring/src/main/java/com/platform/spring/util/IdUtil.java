package com.platform.spring.util;

import java.util.UUID;


public class IdUtil {

	
	/**
	 * <p>
	 * 作用描述：生成全局统一ID，长度为32位的唯一UUID
	 * </p>
	 * <p>
	 * @return String
	 */
	public static synchronized String createUUID() {
		UUID uuid = UUID.randomUUID(); 
		return uuid.toString().replace("-", ""); 
	}
}
