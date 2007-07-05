/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

import javax.faces.context.FacesContext;

import org.rcfaces.core.lang.provider.IExpansionProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IExpandedValuesCapability extends IExpansionProvider {
    /*
     * Object getCheckedValues();
     * 
     * void setCheckedValues(Object values);
     */

    Class getExpandedValuesType(FacesContext facesContext);
}
