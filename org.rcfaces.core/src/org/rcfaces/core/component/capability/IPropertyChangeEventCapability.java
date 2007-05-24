/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IPropertyChangeListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IPropertyChangeEventCapability {

    /**
     * Adds a listener to the component for the propertyChange event
     * 
     * @param facesListener
     *            the propertyChange listener to add
     */
    void addPropertyChangeListener(IPropertyChangeListener facesListener);

    /**
     * Removes a listener from the component for the propertyChange event
     * 
     * @param facesListener
     *            the propertyChange listener to remove
     */
    void removePropertyChangeListener(IPropertyChangeListener facesListener);

    /**
     * Returns a list of propertyChange listener for the component
     * 
     * @return propertyChange listeners' list
     */
    FacesListener[] listPropertyChangeListeners();

}
