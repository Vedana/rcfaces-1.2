/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICalendarLayoutCapability {

    int SHORT_LAYOUT = 1;

    int MEDIUM_LAYOUT = 2;

    int LONG_LAYOUT = 3;

    int FULL_LAYOUT = 4;

    int DEFAULT_LAYOUT = MEDIUM_LAYOUT;

    int getCalendarLayout();

    void setCalendarLayout(int layout);
}
