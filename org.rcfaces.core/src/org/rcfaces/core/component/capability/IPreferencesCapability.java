/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import org.rcfaces.core.preference.IComponentPreferences;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IPreferencesCapability {

    /**
     * Returns an object that holds the preferences for the component.
     * 
     * @return ComponentPreference object
     */
    IComponentPreferences getPreferences();

    /**
     * Sets an object that holds the preferences for the component.
     * 
     * @param preferences
     *            ComponentPreference object
     */
    void setPreferences(IComponentPreferences preferences);
}
