/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDefaultUnlockedPropertiesRenderer {
    String[] getDefaultUnlockedProperties(FacesContext facesContext,
            UIComponent component);
}
