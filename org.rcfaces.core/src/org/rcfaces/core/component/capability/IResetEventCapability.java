/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IResetListener;


/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IResetEventCapability {

	/**
	 * Adds a listener to the component for the reset event
	 * 
	 * @param facesListener the reset listener to add 
	 */
	void addResetListener(IResetListener facesListener);

	/**
	 * Removes a listener from the component for the reset event
	 * 
	 * @param facesListener the reset listener to remove
	 */
	void removeResetListener(IResetListener facesListener);

	/**
	 * Returns a list of reset listener for the component
	 * 
	 * @return reset listeners' list
	 */
	FacesListener[] listResetListeners();
}
