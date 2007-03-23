/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IValueLockedCapability {

    /**
     * Get the lock state of the main value of the component.
     * 
     * @return The lock state.
     */
    boolean isValueLocked();

    /**
     * Set the lock of the main value of the component.
     * 
     * @param valueLocked
     *            The lock state.
     */
    void setValueLocked(boolean valueLocked);

}
