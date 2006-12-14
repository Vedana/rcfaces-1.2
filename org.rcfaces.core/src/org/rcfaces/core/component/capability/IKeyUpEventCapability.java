/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IKeyUpListener;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IKeyUpEventCapability {

	/**
	 * Adds a listener to the component for the keyUp event
	 * 
	 * @param facesListener the keyUp listener to add 
	 */
    void addKeyUpListener(IKeyUpListener facesListener);

	/**
	 * Removes a listener from the component for the keyUp event
	 * 
	 * @param facesListener the keyUp listener to remove
	 */
    void removeKeyUpListener(IKeyUpListener facesListener);

	/**
	 * Returns a list of keyUp listener for the component
	 * 
	 * @return keyUp listeners' list
	 */
    FacesListener[] listKeyUpListeners();
}
