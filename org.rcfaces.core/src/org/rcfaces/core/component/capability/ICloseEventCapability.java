/*
 * $Id$
 * 
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.ICloseListener;


/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICloseEventCapability {

	/**
	 * Adds a listener to the component for the close event
	 * 
	 * @param facesListener the close listener to add
	 */
	void addCloseListener(ICloseListener facesListener);

	/**
	 * Removes a listener from the component for the close event
	 * 
	 * @param facesListener the close listener to remove
	 */
	void removeCloseListener(ICloseListener facesListener);

	/**
	 * Returns a list of close listener for the component
	 * 
	 * @return close listeners' list
	 */
	FacesListener [] listCloseListeners();
}
