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
     * Returns an integer value indicating wether the client should know about
     * the component's full state even if only a part of the data is present
     * (AJAX).
     * 
     * @return An integer value indicating wether the client should know about the component's full state.
     * @see IClientFullStateCapability#NONE_CLIENT_FULL_STATE 
     * @see IClientFullStateCapability#ONEWAY_CLIENT_FULL_STATE 
     * @see IClientFullStateCapability#TWOWAYS_CLIENT_FULL_STATE 
     */
    int getClientCheckFullState();

    /**
     * Sets a boolean value indicating wether the client should know about the
     * component's full state even if only a part of the data is present (AJAX).
     * 
     * @param clientCheckFullState
     * @see IClientFullStateCapability#NONE_CLIENT_FULL_STATE 
     * @see IClientFullStateCapability#ONEWAY_CLIENT_FULL_STATE 
     * @see IClientFullStateCapability#TWOWAYS_CLIENT_FULL_STATE 
     */
    void setClientCheckFullState(int clientCheckFullState);
}
