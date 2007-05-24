/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IShowValueCapability {

    /**
     * 
     * @return The value of the item will be shown.
     */
    Object getShowValue();

    /**
     * Set the value of the item will be shown.
     * 
     * @param value
     *            The value of the item
     */
    void setShowValue(Object value);
}
