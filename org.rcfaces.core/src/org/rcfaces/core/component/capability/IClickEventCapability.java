/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IClickListener;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClickEventCapability {

    /**
     * Adds a listener to the component for the doubleClick event
     * 
     * @param facesListener
     *            the doubleClick listener to add
     */
    void addClickListener(IClickListener facesListener);

    /**
     * Removes a listener from the component for the doubleClick event
     * 
     * @param facesListener
     *            the doubleClick listener to remove
     */
    void removeClickListener(IClickListener facesListener);

    /**
     * Returns a list of doubleClick listener for the component
     * 
     * @return doubleClick listeners' list
     */
    FacesListener[] listClickListeners();
}
