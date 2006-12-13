/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * A string specifying the selection mode :
 *		<ul><li>
 *		date: one day per selection
 *		</li><li>
 *		period: a continuous period of time
 *		</li><li>
 *		periods: several non-continuous period of time (for example every wednesday)
 *		</li></ul>
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
     * Returns a int value specifying the selection mode.
     * 
     * @return 0:date|1:period|2:periods
     */
    int getMode();

    /**
     * Sets a int value specifying the selection mode.
     * 
     * @param mode 0:date|1:period|2:periods
     */
    void setMode(int mode);
}
