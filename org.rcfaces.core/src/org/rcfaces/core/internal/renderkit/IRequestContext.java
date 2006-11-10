/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.IReleasable;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IRequestContext extends IReleasable {

    IProcessContext getProcessContext();

    FacesContext getFacesContext();

    IComponentData getComponentData(UIComponent component, String componentId);

    String getComponentId(UIComponent component);
}
