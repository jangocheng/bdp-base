package com.platform.kafka.exception;


import javax.jms.JMSException;

/**
* Base class for exception thrown by the framework whenever it
* encounters a problem related to JMS.
*
* @author wlhbdp
* @since 1.1
*/
@SuppressWarnings("serial")
public abstract class JmsException extends RuntimeException {

	/**
	 * Constructor that takes a message.
	 * @param msg the detail message
	 */
	public JmsException(String msg) {
		super(msg);
	}

	/**
	 * Constructor that takes a message and a root cause.
	 * @param msg the detail message
	 * @param cause the cause of the exception. This argument is generally
	 * expected to be a proper subclass of {@link JMSException},
	 * but can also be a JNDI NamingException or the like.
	 */
	public JmsException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Constructor that takes a plain root cause, intended for
	 * subclasses mirroring corresponding {@code javax.jms} exceptions.
	 * @param cause the cause of the exception. This argument is generally
	 * expected to be a proper subclass of {@link JMSException}.
	 */
	public JmsException(Throwable cause) {
		super(cause != null ? cause.getMessage() : null, cause);
	}


	/**
	 * Convenience method to get the vendor specific error code if
	 * the root cause was an instance of JMSException.
	 * @return a string specifying the vendor-specific error code if the
	 * root cause is an instance of JMSException, or {@code null}
	 */
	public String getErrorCode() {
		Throwable cause = getCause();
		if (cause instanceof JMSException) {
			return ((JMSException) cause).getErrorCode();
		}
		return null;
	}

	/**
	 * Return the detail message, including the message from the linked exception
	 * if there is one.
	 * @see JMSException#getLinkedException()
	 */
	@Override
	public String getMessage() {
		String message = super.getMessage();
		Throwable cause = getCause();
		if (cause instanceof JMSException) {
			Exception linkedEx = ((JMSException) cause).getLinkedException();
			if (linkedEx != null) {
				String linkedMessage = linkedEx.getMessage();
				String causeMessage = cause.getMessage();
				if (linkedMessage != null && (causeMessage == null || !causeMessage.contains(linkedMessage))) {
					message = message + "; nested exception is " + linkedEx;
				}
			}
		}
		return message;
	}

}

