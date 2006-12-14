/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IInitListener;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IInitEventCapability {

	/**
	 * Adds a listener to the component for the init event
	 * 
	 * @param facesListener the init listener to add 
	 */
    void addInitListener(IInitListener facesListener);

	/**
	 * Removes a listener from the component for the init event
	 * 
	 * @param facesListener the init listener to remove
	 */
    void removeInitListener(IInitListener facesListener);

	/**
	 * Returns a list of init listener for the component
	 * 
	 * @return init listeners' list
	 */
    FacesListener[] listInitListeners();
}
