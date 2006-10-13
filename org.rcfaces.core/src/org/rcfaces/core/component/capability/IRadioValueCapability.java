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

    Object getRadioValue();

    void setRadioValue(Object value);
}
