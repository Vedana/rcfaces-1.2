/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IUserEventListener;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IUserEventCapability {

	/**
	 * Adds a listener to the component for the user event
	 * 
	 * @param facesListener the user listener to add 
	 */
	void addUserEventListener(IUserEventListener facesListener);

	/**
	 * Removes a listener from the component for the user event
	 * 
	 * @param facesListener the user listener to remove
	 */
    void removeUserEventListener(IUserEventListener facesListener);

	/**
	 * Returns a list of user listener for the component
	 * 
	 * @return user listeners' list
	 */
    FacesListener[] listUserEventListeners();

}
