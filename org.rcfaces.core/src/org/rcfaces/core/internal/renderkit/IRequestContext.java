/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IRequestContext /* extends IReleasable */{

    IProcessContext getProcessContext();

    FacesContext getFacesContext();

    boolean isLockedClientAttributes();

    IComponentData getComponentData(UIComponent component, String componentId,
            Renderer renderer);

    String getComponentId(UIComponent component);
}
