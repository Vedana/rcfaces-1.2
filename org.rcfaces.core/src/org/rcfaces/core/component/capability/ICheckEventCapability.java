/*
 * $Id$
 * 
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.ICheckListener;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICheckEventCapability {

    /**
     * Adds a listener to the component for the checked event
     * 
     * @param checkListener
     *            the listener to add
     */
    void addCheckListener(ICheckListener checkListener);

    /**
     * Removes a listener from the list of listeners
     * 
     * @param checkListener
     *            the listener to remove
     */
    void removeCheckListener(ICheckListener checkListener);

    /**
     * Returns the list of check listener for the component
     * 
     * @return check listeners' list
     */
    FacesListener[] listCheckListeners();
}
