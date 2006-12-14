/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IServiceEventListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IServiceEventCapability {

	/**
	 * Adds a listener to the component for the service event
	 * 
	 * @param facesListener the service listener to add 
	 */
	void addServiceEventListener(IServiceEventListener facesListener);

	/**
	 * Removes a listener from the component for the service event
	 * 
	 * @param facesListener the service listener to remove
	 */
    void removeServiceEventListener(IServiceEventListener facesListener);

	/**
	 * Returns a list of service listener for the component
	 * 
	 * @return service listeners' list
	 */
    FacesListener[] listServiceEventListeners();

}
