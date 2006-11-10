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
public interface IClientDatesStrategyCapability {

    int ALL_DATES_STRATEGY = 1;

    int YEAR_DATES_STRATEGY = 2;

    int MONTH_DATES_STRATEGY = 3;

    int DEFAULT_DATES_STRATEGY = ALL_DATES_STRATEGY;

    void setClientDatesStrategy(int clientDatesStrategy);

    int getClientDatesStrategy();
}
