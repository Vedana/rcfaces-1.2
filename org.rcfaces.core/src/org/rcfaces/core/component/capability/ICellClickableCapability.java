/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * A boolean value indicating whether the component can be clicked.
 * 
 * @version $Revision$ $Date$
 */
public interface ICellClickableCapability {

    /**
     * Returns a boolean value indicating whether the component can be clicked.
     * 
     * @return boolean
     */
    boolean isCellClickable();

    /**
     * Sets a boolean value indicating whether the component can be clicked.
     * 
     * @param checkable
     *            boolean
     */
    void setCellClickable(boolean clickable);

    /**
     * Returns a boolean value indicating whether the component can be clicked.
     * 
     * @return boolean
     */
    boolean isAllCellClickable();

    /**
     * Sets a boolean value indicating whether the component can be clicked.
     * 
     * @param checkable
     *            boolean
     */
    void setAllCellClickable(boolean clickable);

}
