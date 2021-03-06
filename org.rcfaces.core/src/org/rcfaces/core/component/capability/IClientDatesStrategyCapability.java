/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * A int value indicating the AJAX transfert strategy for the calendar component :
 * the dates' characteristics (styleClass, toolTip ...). Those characteristics
 * can be retrieved by month, year or totally.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientDatesStrategyCapability {

    int ALL_DATES_STRATEGY = 1;

    int YEAR_DATES_STRATEGY = 2;

    int MONTH_DATES_STRATEGY = 3;

    int DEFAULT_DATES_STRATEGY = ALL_DATES_STRATEGY;

    /**
     * Sets an int value indicating the AJAX transfert strategy for the calendar
     * component : the dates' characteristics (styleClass, toolTip ...).
     * 
     * @param clientDatesStrategy
     *            1:all|2:year|3:month|1:default default=all
     */
    void setClientDatesStrategy(int clientDatesStrategy);

    /**
     * Returns an int value indicating the AJAX transfert strategy for the
     * calendar component : the dates' characteristics (styleClass, toolTip
     * ...).
     * 
     * @return 1:all|2:year|3:month
     */
    int getClientDatesStrategy();
}
