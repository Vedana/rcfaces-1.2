/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IRenderContext {

    // Pas de FacesContext car en cas de traitement à la Tiles ca marche plus !

    FacesContext getFacesContext();

    IProcessContext getProcessContext();

    IScriptRenderContext getScriptRenderContext();

    String getComponentClientId(UIComponent component);

    String computeBrotherComponentClientId(UIComponent brotherComponent,
            String componentId);

    IComponentWriter getComponentWriter();

    void pushComponent(UIComponent component, String componentId);

    void popComponent(UIComponent component);

    Object setAttribute(String key, Object value);

    Object getAttribute(String key);

    boolean containsAttribute(String key);

    Object removeAttribute(String key);

    UIComponent getComponent();

    void encodeEnd(UIComponent component);

    void pushScopeVar(String varName, ValueBinding value);

    void popScopeVar(String varName);
}