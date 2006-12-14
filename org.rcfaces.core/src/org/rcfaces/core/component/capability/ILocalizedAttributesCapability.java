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
public interface ILocalizedAttributesCapability {
	
	/**
	 * Returns the locale defined for the component or its parents
	 * @return the defined locale
	 */
    Locale getAttributesLocale();

    /**
     * Sets the locale for the component and its children
     * @param locale locale to define
     */
    void setAttributesLocale(Locale locale);
}
