/*
 * $Id$
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IDropCompleteListener;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDropCompleteEventCapability {

    /**
     * Adds a listener to the component for the drop event
     * 
     * @param facesListener
     *            the drop listener to add
     */
    void addDropCompleteListener(IDropCompleteListener facesListener);

    /**
     * Removes a listener from the component for the drop event
     * 
     * @param facesListener
     *            the drop listener to remove
     */
    void removeDropCompleteListener(IDropCompleteListener facesListener);

    /**
     * Returns a list of drop listeners for the component
     * 
     * @return drop listeners' list
     */
    FacesListener[] listDropCompleteListeners();
}