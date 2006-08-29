/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:55  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.1  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.1  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 */
package org.rcfaces.core.progressMonitor;

/**
 * This exception is thrown to blow out of a long-running method when the user
 * cancels it.
 */
public final class OperationCanceledException extends RuntimeException {
    private static final String REVISION = "$Revision$";

    /**
     * Creates a new exception.
     */
    public OperationCanceledException() {
        super();
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