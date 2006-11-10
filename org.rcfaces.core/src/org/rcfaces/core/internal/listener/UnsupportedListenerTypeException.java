/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.FacesException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UnsupportedListenerTypeException extends FacesException {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 2303989217005897745L;

    public UnsupportedListenerTypeException(String listenerType) {
        super("Listener of type '" + listenerType
                + "' defined in server side, is not supported !");
    }
}
