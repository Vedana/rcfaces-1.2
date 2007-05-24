/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import java.util.Locale;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentLocaleCapability {

    /**
     * Returns the locale defined for the component
     * 
     * @return the defined locale
     */
    Locale getComponentLocale();

    /**
     * Sets the locale for the component
     * 
     * @param locale
     *            locale to define
     */
    void setComponentLocale(Locale locale);
}
