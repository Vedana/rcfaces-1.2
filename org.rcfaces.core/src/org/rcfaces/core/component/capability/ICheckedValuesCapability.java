/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

import javax.faces.context.FacesContext;

import org.rcfaces.core.lang.provider.ICheckProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICheckedValuesCapability extends ICheckProvider {
    /*
     * Object getCheckedValues();
     * 
     * void setCheckedValues(Object values);
     */

    Class< ? > getCheckedValuesType(FacesContext facesContext);
}
