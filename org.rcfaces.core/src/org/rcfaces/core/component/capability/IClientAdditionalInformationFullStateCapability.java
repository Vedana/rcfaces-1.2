/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientAdditionalInformationFullStateCapability {
    /**
     * Returns a boolean value indicating whether the client should know about
     * the component's full state even if only a part of the data is present
     * (AJAX).
     * 
     * @return boolean
     */
    boolean isClientAdditionalInformationFullState();

    /**
     * Sets a boolean value indicating whether the client should know about the
     * component's full state even if only a part of the data is present (AJAX).
     * 
     * @param clientAdditionalInformationFullState
     *            boolean
     */
    void setClientAdditionalInformationFullState(
            boolean clientAdditionalInformationFullState);
}
