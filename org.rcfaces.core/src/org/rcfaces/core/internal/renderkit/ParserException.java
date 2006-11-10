/*
 * $Id$
 * 
 */

package org.rcfaces.core.internal.renderkit;

/**
 * Probleme d'analyse de l'aspect graphique d'un composant.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ParserException extends Exception {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -8711696478442649846L;

    public ParserException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
