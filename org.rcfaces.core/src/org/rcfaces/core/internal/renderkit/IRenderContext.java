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
public interface IRenderContext {

    // Pas de FacesContext car en cas de traitement à la Tiles ca marche plus !

    IProcessContext getProcessContext();

    IScriptRenderContext getScriptRenderContext();

    String getComponentClientId(FacesContext facesContext, UIComponent component);

    String computeBrotherComponentClientId(FacesContext facesContext,
            UIComponent brotherComponent, String componentId);

    IComponentWriter getComponentWriter(FacesContext facesContext);

    void pushComponent(FacesContext facesContext, UIComponent component,
            String componentId);

    void popComponent(UIComponent component);

    Object setAttribute(String key, Object value);

    Object getAttribute(String key);

    boolean containsAttribute(String key);

    Object removeAttribute(String key);

    UIComponent getComponent();

    void encodeEnd(FacesContext facesContext, UIComponent component);

}