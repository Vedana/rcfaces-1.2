/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IRenderContext extends StateHolder {

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

    void encodeEnd(IComponentWriter writer) throws WriterException;

    void pushScopeVar(String varName, Object value, Object valueBinding,
            boolean valueMustBeStored);

    void popScopeVar(String varName);
}