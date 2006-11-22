/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICalendarModeCapability {
    /**
     * 
     */
    int CALENDAR_MODE_DATE = 0;

    /**
     * 
     */
    String CALENDAR_MODE_DATE_NAME = "date";

    /**
     * 
     */
    int CALENDAR_MODE_PERIOD = 1;

    /**
     * 
     */
    String CALENDAR_MODE_PERIOD_NAME = "period";

    /**
     * 
     */
    int CALENDAR_MODE_PERIODS = 2;

    /**
     * 
     */
    String CALENDAR_MODE_PERIODS_NAME = "periods";

    /**
     *
     */
    int DEFAULT_CALENDAR_MODE = CALENDAR_MODE_DATE;

    /**
     * 
     */
    String DEFAULT_CALENDAR_MODE_NAME = "default";

    /**
     * 
     */
    int getMode();

    /**
     * 
     */
    void setMode(int mode);
}
