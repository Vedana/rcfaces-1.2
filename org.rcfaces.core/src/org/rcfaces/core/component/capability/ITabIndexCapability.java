/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ITabIndexCapability {

    /**
     * Returns an int value specifying the position of this element in the
     * tabbing order for the current document. This value must be an integer
     * between 0 and 32767.
     * 
     * @return index
     */
    Integer getTabIndex();

    /**
     * Sets an int value specifying the position of this element in the tabbing
     * order for the current document. This value must be an integer between 0
     * and 32767.
     * 
     * @param tabIndex
     *            index
     */
    void setTabIndex(Integer tabIndex);
}
