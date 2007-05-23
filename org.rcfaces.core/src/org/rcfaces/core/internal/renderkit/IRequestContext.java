/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IRequestContext /*extends IReleasable */{

    IProcessContext getProcessContext();

    FacesContext getFacesContext();

    boolean isLockedClientAttributes();

    IComponentData getComponentData(UIComponent component, String componentId);

    String getComponentId(UIComponent component);
}
