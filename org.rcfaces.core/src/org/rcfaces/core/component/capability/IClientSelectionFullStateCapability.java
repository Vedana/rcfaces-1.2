/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientSelectionFullStateCapability extends
        IClientFullStateCapability {

    /**
     * Returns a boolean value indicating whether the client should know about
     * the component's full state even if only a part of the data is present
     * (AJAX).
     * 
     * @return boolean
     */
    int getClientSelectionFullState();

    /**
     * Sets a boolean value indicating whether the client should know about the
     * component's full state even if only a part of the data is present (AJAX).
     * 
     * @param clientSelectionFullState
     *            boolean
     */
    void setClientSelectionFullState(int clientSelectionFullState);
}
