/*
 * $Id$
 * 
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.ILoadListener;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ILoadEventCapability {

    /**
     * Adds a listener to the component for the load event
     * 
     * @param loadListener
     *            the load listener to add
     */
    void addLoadListener(ILoadListener loadListener);

    /**
     * Removes a listener from the component for the load event
     * 
     * @param loadListener
     *            the load listener to remove
     */
    void removeLoadListener(ILoadListener loadListener);

    /**
     * Returns a list of load listener for the component
     * 
     * @return load listeners' list
     */
    FacesListener[] listLoadListeners();
}
