/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IKeyDownListener;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IKeyDownEventCapability {

    /**
     * Adds a listener to the component for the keyDown event
     * 
     * @param facesListener
     *            the keyDown listener to add
     */
    void addKeyDownListener(IKeyDownListener facesListener);

    /**
     * Removes a listener from the component for the keyDown event
     * 
     * @param facesListener
     *            the keyDown listener to remove
     */
    void removeKeyDownListener(IKeyDownListener facesListener);

    /**
     * Returns a list of keyDown listener for the component
     * 
     * @return keyDown listeners' list
     */
    FacesListener[] listKeyDownListeners();
}
