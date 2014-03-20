/*
 * $Id: IDeltaPropertiesAccessor.java,v 1.4 2010/02/10 14:39:05 oeuillot Exp $
 * 
 */
package org.rcfaces.core.internal.component;

import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author: oeuillot $)
 * @version $Revision: 1.4 $ $Date: 2010/02/10 14:39:05 $
 */
public interface IDeltaPropertiesAccessor extends IPropertiesAccessor {
    boolean hasModifiedProperties();

    void commitProperties(FacesContext facesContext);
}
