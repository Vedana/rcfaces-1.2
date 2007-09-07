/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.BindingTools;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractRenderContext implements IRenderContext {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractRenderContext.class);

    private static final int COMPONENT_STACK_INITIAL_DEPTH = 16;

    private static final int SCOPE_VAR_INITIAL_SIZE = 4;

    private final List stack = new ArrayList(COMPONENT_STACK_INITIAL_DEPTH * 3);

    private List scopeVars = null;

    private FacesContext facesContext;

    private Map attributes;

    protected AbstractRenderContext() {
    }

    public final FacesContext getFacesContext() {
        return facesContext;
    }

    protected void initialize(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public String getComponentClientId(UIComponent component) {
        return component.getClientId(facesContext);
    }

    public String computeBrotherComponentClientId(UIComponent brotherComponent,
            String componentId) {

        if (componentId.length() > 0
                && componentId.charAt(0) == NamingContainer.SEPARATOR_CHAR) {
            return componentId;
        }

        String brotherClientId = getComponentClientId(brotherComponent);

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

    public void pushComponent(UIComponent component, String componentClientId) {
        stack.add(component);
        stack.add(componentClientId);
        stack.add(Boolean.FALSE);

        if (LOG.isDebugEnabled()) {
            StringBuffer sb = new StringBuffer();

            for (Iterator it = stack.iterator(); it.hasNext();) {
                it.next();
                sb.append(" / " + it.next());
                it.next();
            }

            LOG.debug("Push component. stack=" + sb);
        }
    }

    public void popComponent(UIComponent component) {

        int level = getStackLevel();
        stack.remove(level);
        String componentClientId = (String) stack.remove(level);
        Object componentContextAttributes = stack.remove(level);
        if (componentContextAttributes != Boolean.FALSE) {
            releaseMap((Map) componentContextAttributes);
        }

        if (LOG.isDebugEnabled()) {
            StringBuffer sb = new StringBuffer();

            for (Iterator it = stack.iterator(); it.hasNext();) {
                it.next();
                sb.append(" / " + it.next());
                it.next();
            }

            LOG.debug("Pop component (" + componentClientId + "). stack=" + sb);
        }

        /*
         * On ne fait pas ca ... Car il y a peut etre d'autres composants
         * "freres" Camelia
         * 
         * if (getStackLevel() < 0) { release(); }
         */
    }

    /*
     * protected void releaseComponentAttributes(Map map) { map.clear(); }
     */

    private void releaseMap(Map map) {
        map.clear();
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

    public final Object removeComponentContextAttribute(String key) {
        int componentContextLevel = getStackLevel() + 2;

        Object object = stack.get(componentContextLevel);
        if (object == Boolean.FALSE) {
            return null;
        }

        Map map = (Map) object;

        return map.remove(key);
    }

    public final Object getAttribute(String key) {
        if (attributes == null) {
            return null;
        }

        return attributes.get(key);
    }

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

        if (level < 0) {
            throw new IllegalStateException("Empty stack of components !");
        }

        return (UIComponent) stack.get(level);
    }

    public final String getComponentClientId() {

        int level = getStackLevel();

        return (String) stack.get(level + 1);
    }

    public final IComponentWriter getComponentWriter() {
        return createWriter(getComponent());
    }

    protected abstract IComponentWriter createWriter(UIComponent component);

    public void encodeEnd(UIComponent component) {
    }

    public void popScopeVar(String varName) {
        if (scopeVars == null || scopeVars.isEmpty()) {
            throw new FacesException("Scope var stack is empty");
        }

        int level = scopeVars.size();
        scopeVars.remove(--level); // ScopeVar
        String stackVarName = (String) scopeVars.remove(--level);

        if (varName.equals(stackVarName) == false) {
            throw new FacesException("Not the same varName ? (stackVarName="
                    + stackVarName + " varName=" + varName + ")");
        }

        getFacesContext().getExternalContext().getRequestMap().remove(varName);
    }

    public void pushScopeVar(String varName, Object scopeValue) {

        FacesContext facesContext = getFacesContext();

        Object value = BindingTools.resolveBinding(facesContext, scopeValue);

        if (scopeVars == null) {
            scopeVars = new ArrayList(SCOPE_VAR_INITIAL_SIZE * 2);
        }

        scopeVars.add(varName);
        scopeVars.add(scopeValue);

        facesContext.getExternalContext().getRequestMap().put(varName, value);
    }

    public Object saveRenderContextState() {
        Object ret[] = new Object[1];

        if (scopeVars != null && scopeVars.isEmpty() == false) {
            Object sv[] = new Object[scopeVars.size() * 2];
            FacesContext facesContext = getFacesContext();

            int idx = 0;
            for (Iterator it = scopeVars.iterator(); it.hasNext();) {
                sv[idx++] = it.next();
                sv[idx++] = UIComponentBase.saveAttachedState(facesContext, it
                        .next());
            }

            ret[0] = sv;
        }

        return ret;
    }

    public void restoreState(Object object) {
        Object ret[] = (Object[]) object;

        if (ret[0] != null) {
            Object sv[] = (Object[]) ret[0];
            FacesContext facesContext = getFacesContext();

            for (int i = 0; i < sv.length;) {
                String varName = (String) sv[i++];
                Object valueBinding = UIComponentBase.restoreAttachedState(
                        facesContext, sv[i++]);

                pushScopeVar(varName, valueBinding);
            }
        }
    }

}