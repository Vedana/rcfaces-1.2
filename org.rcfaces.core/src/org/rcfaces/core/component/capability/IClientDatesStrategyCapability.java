/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IClientDatesStrategyCapability {

    int ALL_DATES_STRATEGY = 1;

    int YEAR_DATES_STRATEGY = 2;

    int MONTH_DATES_STRATEGY = 3;

    int DEFAULT_DATES_STRATEGY = ALL_DATES_STRATEGY;

    void setClientDatesStrategy(int clientDatesStrategy);

    int getClientDatesStrategy();
}
