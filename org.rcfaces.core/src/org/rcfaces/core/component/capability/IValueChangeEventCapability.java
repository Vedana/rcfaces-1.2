/*
 * $Id$
 * 
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;
import javax.faces.event.ValueChangeListener;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IValueChangeEventCapability {

    /**
     * Adds a listener to the component for the valueChange event
     * 
     * @param facesListener
     *            the valueChange listener to add
     */
    void addValueChangeListener(ValueChangeListener facesListener);

    /**
     * Removes a listener from the component for the valueChange event
     * 
     * @param facesListener
     *            the valueChange listener to remove
     */
    void removeValueChangeListener(ValueChangeListener facesListener);

    /**
     * Returns a list of valueChange listener for the component
     * 
     * @return valueChange listeners' list
     */
    FacesListener[] listValueChangeListeners();
}
