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
public interface ISchrodingerCheckableCapability {

    /**
     * Returns a boolean value indicating whether the component
     * 
     * @return boolean
     */
    boolean isSchrodingerCheckable();

    /**
     * Sets a boolean value indicating whether the component .
     * 
     * @param checkable
     *            boolean
     */
    void setSchrodingerCheckable(boolean schrodingerCheckable);

}
