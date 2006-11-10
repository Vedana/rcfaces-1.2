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
    IComponentPreference getPreference();

    void setPreference(IComponentPreference preference);
}
