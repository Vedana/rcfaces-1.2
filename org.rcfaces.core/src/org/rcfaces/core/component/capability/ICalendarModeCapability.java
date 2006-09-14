/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
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
     * Type de calendrier o� l'utilisateur s�lectionne une date.
     */
    int CALENDAR_MODE_DATE = 0;

    String CALENDAR_MODE_DATE_NAME = "date";

    /**
     * Type de calendrier o� l'utilisateur s�lectionne une p�riode.
     */
    int CALENDAR_MODE_PERIOD = 1;

    String CALENDAR_MODE_PERIOD_NAME = "period";

    /**
     * Type de calendrier o� l'utilisateur s�lectionne des p�riodes.
     */
    int CALENDAR_MODE_PERIODS = 2;

    String CALENDAR_MODE_PERIODS_NAME = "periods";

    /**
     * Valeur par defaut du CalendarMode.
     */
    int DEFAULT_CALENDAR_MODE = CALENDAR_MODE_DATE;

    String DEFAULT_CALENDAR_MODE_NAME = "default";

    int getMode();
    
    void setMode(int mode);
}
