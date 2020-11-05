package com.platform.spring.exception;


public class SecrecyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4114095629871926170L;

	/**
	 * @param code
	 *            异常编码
	 * @param cause
	 *            原异常信息
	 */
	public SecrecyException(String code, Throwable cause) {
		super(code, cause);
	}

}