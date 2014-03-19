/*
 * $Id$
 */
package org.rcfaces.core.lang;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = -1121873372303775679L;

	private final int errorCode;

	private final String errorMessage;

	public ApplicationException(String message, Throwable cause, int errorCode,
			String errorMessage) {
		super(message, cause);

		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ApplicationException(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public final int getErrorCode() {
		return errorCode;
	}

	public final String getErrorMessage() {
		return errorMessage;
	}

}
