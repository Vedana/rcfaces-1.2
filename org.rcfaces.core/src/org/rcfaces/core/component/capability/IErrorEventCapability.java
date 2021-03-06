/*
 * $Id$
 * 
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IErrorListener;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IErrorEventCapability {

    /**
     * Adds a listener to the component for the error event
     * 
     * @param errorListener
     *            the load listener to add
     */
    void addErrorListener(IErrorListener errorListener);

    /**
     * Removes a listener from the component for the error event
     * 
     * @param errorListener
     *            the load listener to remove
     */
    void removeErrorListener(IErrorListener errorListener);

    /**
     * Returns a list of Error listener for the component
     * 
     * @return load listeners' list
     */
    FacesListener[] listErrorListeners();
}
