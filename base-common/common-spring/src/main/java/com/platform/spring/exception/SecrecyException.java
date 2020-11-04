package com.platform.spring.exception;

/**
 * 
 * <p>
 * Title : 加密、解密异常类实现
 * </p>
 * <p>
 * Description: 对加密，解密过程中抛出来的类进行格式化处理
 * </p>
 * <p>
 * Author :Even
 * </p>
 */
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