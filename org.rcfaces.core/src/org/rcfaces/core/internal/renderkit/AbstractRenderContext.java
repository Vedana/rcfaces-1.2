/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.AbstractReleasable;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractRenderContext extends AbstractReleasable
        implements IRenderContext {
    private static final String REVISION = "$Revision$";

    private static final int COMPONENT_DEPTH = 16;

    private final List stack = new ArrayList(COMPONENT_DEPTH * 3);

    private Map attributes;

    protected AbstractRenderContext() {
    }

    protected void initialize(FacesContext facesContext) {
    }

    public String getComponentClientId(FacesContext facesContext,
            UIComponent component) {
        return component.getClientId(facesContext);
    }

    public String computeBrotherComponentClientId(FacesContext facesContext,
            UIComponent brotherComponent, String componentId) {

        if (componentId.length() > 0
                && componentId.charAt(0) == NamingContainer.SEPARATOR_CHAR) {
            return componentId;
        }

        String brotherClientId = getComponentClientId(facesContext,
                brotherComponent);

        if (brotherClientId == null) {
            return null;
        }

        int idx = brotherClientId.lastIndexOf(NamingContainer.SEPARATOR_CHAR);
        if (idx < 0) {
            return componentId;
        }

        brotherClientId = brotherClientId.substring(0, idx + 1);

        return brotherClientId + componentId;
    }

    public void pushComponent(FacesContext facesContext, UIComponent component,
            String componentClientId) {
        stack.add(component);
        stack.add(componentClientId);
        stack.add(Boolean.FALSE);
    }

    public void popComponent(UIComponent component) {

        int level = getStackLevel();
        stack.remove(level);
        stack.remove(level);
        Object componentContextAttributes = stack.remove(level);
        if (componentContextAttributes != Boolean.FALSE) {
            releaseMap((Map) componentContextAttributes);
        }

        /*
         * On ne fait pas ca ... Car il y a peut etre d'autres composants
         * "freres" Camelia
         * 
         * if (getStackLevel() < 0) { release(); }
         */
    }

    public void release() {
        if (attributes != null) {
            releaseComponentAttributes(attributes);
            releaseMap(attributes);
            attributes = null;
        }

        super.release();
    }

    protected void releaseComponentAttributes(Map map) {
    }

    private void releaseMap(Map map) {

    }

    protected int getStackLevel() {
        return stack.size() - 3;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IRenderContext#getComponentContext()
     */
    public final Object getComponentContextAttribute(String key) {
        int componentContextLevel = getStackLevel() + 2;

        Object object = stack.get(componentContextLevel);
        if (object == Boolean.FALSE) {
            return null;
        }

        Map map = (Map) object;

        return map.get(key);
    }

    public final boolean containsComponentContextAttribute(String key) {
        int componentContextLevel = getStackLevel() + 2;

        Object object = stack.get(componentContextLevel);
        if (object == Boolean.FALSE) {
            return false;
        }

        Map map = (Map) object;

        return map.containsKey(key);
    }

    public final Object setComponentContextAttribute(String key, Object value) {
        int componentContextLevel = getStackLevel() + 2;

        Object object = stack.get(componentContextLevel);
        if (object == Boolean.FALSE) {
            object = new HashMap();
            stack.set(componentContextLevel, object);
        }

        Map map = (Map) object;

        return map.put(key, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IRenderContext#getAttribute(java.lang.String)
     */
    public final Object getAttribute(String key) {
        if (attributes == null) {
            return null;
        }

        return attributes.get(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IRenderContext#setAttribute(java.lang.String,
     *      java.lang.Object)
     */
    public final Object setAttribute(String key, Object value) {
        if (attributes == null) {
            attributes = new HashMap();
        }

        return attributes.put(key, value);
    }

    public boolean containsAttribute(String key) {
        if (attributes == null) {
            return false;
        }
        return attributes.containsKey(key);
    }

    public Object removeAttribute(String key) {
        if (attributes == null) {
            return null;
        }

        return attributes.remove(key);
    }

    public final UIComponent getComponent() {

        int level = getStackLevel();

        return (UIComponent) stack.get(level);
    }

    public final String getComponentClientId() {

        int level = getStackLevel();

        return (String) stack.get(level + 1);
    }

    public final IComponentWriter getComponentWriter(FacesContext facesContext) {
        return createWriter(facesContext, getComponent());
    }

    protected abstract IComponentWriter createWriter(FacesContext facesContext,
            UIComponent component);

    public void encodeEnd(FacesContext facesContext, UIComponent component) {
    }
}