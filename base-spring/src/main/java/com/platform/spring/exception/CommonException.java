package com.platform.spring.exception;

public class CommonException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2645982476100356779L;

	/**
	 * @param code
	 *            异常编码
	 */
	public CommonException(String code) {
		super(code, null);
	}

	/**
	 * @param code
	 *            异常编码
	 * @param cause
	 *            原异常信息
	 */
	public CommonException(String code, Throwable cause) {
		super(code, cause);
	}

	
	
	public CommonException() {
		super(null, null);
	}
}
