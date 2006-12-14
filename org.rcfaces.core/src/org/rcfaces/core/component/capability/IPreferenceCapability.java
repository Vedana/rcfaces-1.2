/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

import org.rcfaces.core.preference.IComponentPreference;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IPreferenceCapability {
    
	/**
	 * Returns an object that holds the preferences for the component.
	 * @return ComponentPreference object
	 */
	IComponentPreference getPreference();

	/**
	 * Sets an object that holds the preferences for the component.
	 * @param preference ComponentPreference object
	 */
    void setPreference(IComponentPreference preference);
}
