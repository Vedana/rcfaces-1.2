/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IRadioValueCapability extends IRadioGroupCapability {

    /**
     * Returns the object associated with the group valued for this component.
     * 
     * @return value object
     */
    Object getRadioValue();

    /**
     * Sets the object associated with the group valued for this component.
     * 
     * @param value
     *            value object
     */
    void setRadioValue(Object value);
}
