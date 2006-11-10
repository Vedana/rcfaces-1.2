/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentRenderContext {
    FacesContext getFacesContext();

    IRenderContext getRenderContext();

    UIComponent getComponent();

    String getComponentClientId();

    boolean containsAttribute(String key);

    Object getAttribute(String key);

    Object setAttribute(String key, Object value);

    Object removeAttribute(String key);
}
