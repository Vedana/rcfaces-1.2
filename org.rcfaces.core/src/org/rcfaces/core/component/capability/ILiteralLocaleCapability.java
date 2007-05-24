/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import java.util.Locale;

/**
 * Best practice : define this on the init component.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ILiteralLocaleCapability {

    /**
     * Returns the locale defined for literal attributes. (date, number)
     * 
     * @return the defined locale
     */
    Locale getLiteralLocale();

    /**
     * Sets the locale for literal attributes. (date, number)
     * 
     * @param locale
     *            locale to define
     */
    void setLiteralLocale(Locale locale);
}
