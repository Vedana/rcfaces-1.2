/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IScopeValue extends StateHolder {
    Object getValue(FacesContext facesContext);
}
