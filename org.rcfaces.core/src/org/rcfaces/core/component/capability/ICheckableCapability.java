/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * A boolean value indicating whether the component can be checked. cf.
 * checkCardinality.
 * 
 * @version $Revision$ $Date$
 */
public interface ICheckableCapability {

    /**
     * Returns a boolean value indicating whether the component can be checked.
     * 
     * @return boolean
     */
    boolean isCheckable();

    /**
     * Sets a boolean value indicating whether the component can be checked.
     * 
     * @param checkable
     *            boolean
     */
    void setCheckable(boolean checkable);

}
