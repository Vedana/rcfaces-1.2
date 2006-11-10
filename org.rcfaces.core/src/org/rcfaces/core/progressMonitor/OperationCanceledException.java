/*
 * $Id$
 * 
 */
package org.rcfaces.core.progressMonitor;

/**
 * This exception is thrown to blow out of a long-running method when the user
 * cancels it.
 */
public final class OperationCanceledException extends RuntimeException {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 5920274573566673815L;

    public OperationCanceledException() {
    }

    /**
     * Creates a new exception with the given message.
     * 
     * @param message
     *            the message for the exception
     */
    public OperationCanceledException(String message) {
        super(message);
    }
}
