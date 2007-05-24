/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IMenuListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IMenuEventCapability {

    /**
     * Adds a listener to the component for the menu event
     * 
     * @param facesListener
     *            the menu listener to add
     */
    void addMenuListener(IMenuListener facesListener);

    /**
     * Removes a listener from the component for the menu event
     * 
     * @param facesListener
     *            the menu listener to remove
     */
    void removeMenuListener(IMenuListener facesListener);

    /**
     * Returns a list of menu listener for the component
     * 
     * @return menu listeners' list
     */
    FacesListener[] listMenuListeners();
}
