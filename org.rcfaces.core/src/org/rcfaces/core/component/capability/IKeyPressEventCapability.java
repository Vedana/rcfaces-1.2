/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IKeyPressListener;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IKeyPressEventCapability {

    /**
     * Adds a listener to the component for the keyPress event
     * 
     * @param facesListener
     *            the keyPress listener to add
     */
    void addKeyPressListener(IKeyPressListener facesListener);

    /**
     * Removes a listener from the component for the keyPress event
     * 
     * @param facesListener
     *            the keyPress listener to remove
     */
    void removeKeyPressListener(IKeyPressListener facesListener);

    /**
     * Returns a list of keyPress listener for the component
     * 
     * @return keyPress listeners' list
     */
    FacesListener[] listKeyPressListeners();
}
