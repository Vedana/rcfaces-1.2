/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.component;

import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDeltaPropertiesAccessor extends IPropertiesAccessor {
    void commitProperties(FacesContext facesContext);
}
