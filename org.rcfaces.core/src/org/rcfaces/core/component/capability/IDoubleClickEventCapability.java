/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IDoubleClickListener;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDoubleClickEventCapability {

	/**
	 * Adds a listener to the component for the doubleClick event
	 * 
	 * @param facesListener the doubleClick listener to add 
	 */
	void addDoubleClickListener(IDoubleClickListener facesListener);

	/**
	 * Removes a listener from the component for the doubleClick event
	 * 
	 * @param facesListener the doubleClick listener to remove
	 */
	void removeDoubleClickListener(IDoubleClickListener facesListener);

	/**
	 * Returns a list of doubleClick listener for the component
	 * 
	 * @return doubleClick listeners' list
	 */
	FacesListener [] listDoubleClickListeners();
}
