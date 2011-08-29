/*
 * $Id$
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.ICriteriaListener;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICriteriaEventCapability {

    /**
     * Adds a listener to the component for the selection event
     * 
     * @param facesListener
     *            the selection listener to add
     */
    void addCriteriaListener(ICriteriaListener facesListener);

    /**
     * Removes a listener from the component for the selection event
     * 
     * @param facesListener
     *            the selection listener to remove
     */
    void removeCriteriaListener(ICriteriaListener facesListener);

    /**
     * Returns a list of selection listener for the component
     * 
     * @return selection listeners' list
     */
    FacesListener[] listCriteriaListeners();
}