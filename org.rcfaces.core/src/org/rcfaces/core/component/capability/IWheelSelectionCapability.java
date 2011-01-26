/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Fred (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IWheelSelectionCapability {

    /**
     * Returns a boolean value indicating wether the mouse wheel change the selection
     * 
     * @return wheelSelection boolean property
     */
    boolean isWheelSelection();

    /**
     * Sets a boolean value indicating wether the component mouse wheel change the selection
     * 
     * @param wheelSelection
     *            wheelSelection boolean property
     */
    void setWheelSelection(boolean wheelSelection);
}
