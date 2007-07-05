/*
 * $Id$
 */

package org.rcfaces.core.component.capability;

import javax.faces.event.FacesListener;

import org.rcfaces.core.event.IAdditionalInformationListener;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAdditionalInformationEventCapability {

    /**
     * Adds a listener to the component for the additional event
     * 
     * @param facesListener
     *            the selection listener to add
     */
    void addAdditionalInformationListener(
            IAdditionalInformationListener facesListener);

    /**
     * Removes a listener from the component for the additional event
     * 
     * @param facesListener
     *            the selection listener to remove
     */
    void removeAdditionalInformationListener(
            IAdditionalInformationListener facesListener);

    /**
     * Returns a list of additional listener for the component
     * 
     * @return selection listeners' list
     */
    FacesListener[] listAdditionalInformationListeners();
}