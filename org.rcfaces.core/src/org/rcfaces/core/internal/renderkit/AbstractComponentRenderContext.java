/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.manager.ITransientAttributesManager;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractComponentRenderContext implements
        IComponentRenderContext {
    private static final String REVISION = "$Revision$";

    private static final String ATTRIBUTES_MAP = "org.rcfaces.core.RENDER_CONTEXT_ATTRIBUTES";

    private static final boolean PUT_ATTRIBUTES_MAP_INTO_COMPONENT = false;

    private final UIComponent component;

    private final String componentClientId;

    private FacesContext facesContext;

    private Map attributes;

    private boolean componentVisible = true;

    protected AbstractComponentRenderContext(FacesContext facesContext,
            UIComponent component, String componentClientId) {
        this.facesContext = facesContext;
        this.component = component;
        this.componentClientId = componentClientId;

        if (PUT_ATTRIBUTES_MAP_INTO_COMPONENT) {
            if (component instanceof ITransientAttributesManager) {
                attributes = (Map) ((ITransientAttributesManager) component)
                        .getTransientAttribute(ATTRIBUTES_MAP);
            }
        }
    }

    public final FacesContext getFacesContext() {
        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }
        return facesContext;
    }

    public final UIComponent getComponent() {
        return component;
    }

    public final String getComponentClientId() {
        return componentClientId;
    }

    public Object getAttribute(String key) {
        if (attributes == null) {
            return null;
        }

        return attributes.get(key);
    }

    public boolean containsAttribute(String key) {
        if (attributes == null) {
            return false;
        }

        return attributes.containsKey(key);
    }

    public Object setAttribute(String key, Object value) {
        if (attributes == null) {
            attributes = new HashMap();

            if (PUT_ATTRIBUTES_MAP_INTO_COMPONENT) {
                if (component instanceof ITransientAttributesManager) {
                    ((ITransientAttributesManager) component)
                            .setTransientAttribute(ATTRIBUTES_MAP, attributes);
                }
            }
        }

        return attributes.put(key, value);
    }

    public Object removeAttribute(String key) {
        if (attributes == null) {
            return null;
        }

        return attributes.remove(key);
    }

    public boolean isComponentVisible() {
        return componentVisible;
    }

    public void setComponentHidden() {
        componentVisible = false;
    }
}
