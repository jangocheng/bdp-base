package com.platform.spring.exception;

public class PollingException  extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	/**
	 * @param code
	 *            异常编码
	 */
	public PollingException(String code) {
		super(code, null);
	}

	/**
	 * @param code
	 *            异常编码
	 * @param cause
	 *            原异常信息
	 */
	public PollingException(String code, Throwable cause) {
		super(code, cause);
	}

	
	
	public PollingException() {
		super(null, null);
	}
}
