/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

import java.util.TimeZone;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentTimeZoneCapability {

    /**
     * Returns the timeZone defined for the component.
     * 
     * @return the defined timeZone
     */
    TimeZone getComponentTimeZone();

    /**
     * Sets the timeZone for component.
     * 
     * @param timeZone
     *            timeZone to define
     */
    void setComponentTimeZone(TimeZone timeZone);
}
