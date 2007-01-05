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
public interface ILiteralTimeZoneCapability {

    /**
     * Returns the timeZone defined for literal attributes. (date)
     * 
     * @return the defined timeZone
     */
    TimeZone getLiteralTimeZone();

    /**
     * Sets the timeZone for literal attributes. (date)
     * 
     * @param timeZone
     *            timeZone to define
     */
    void setLiteralTimeZone(TimeZone timeZone);
}
