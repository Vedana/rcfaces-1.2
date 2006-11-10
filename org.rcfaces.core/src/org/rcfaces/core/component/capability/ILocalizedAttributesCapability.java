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
public interface ILocalizedAttributesCapability {
    Locale getAttributesLocale();

    void setAttributesLocale(Locale locale);
}
