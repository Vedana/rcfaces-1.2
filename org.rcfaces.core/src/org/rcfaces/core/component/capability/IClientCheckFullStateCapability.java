/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientCheckFullStateCapability extends
        IClientFullStateCapability {
    /**
     * Returns a boolean value indicating wether the client should know about
     * the component's full state even if only a part of the data is present
     * (AJAX).
     * 
     * @return
     */
    int getClientCheckFullState();

    /**
     * Sets a boolean value indicating wether the client should know about the
     * component's full state even if only a part of the data is present (AJAX).
     * 
     * @param clientCheckFullState
     * 
     */
    void setClientCheckFullState(int clientCheckFullState);
}
