/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.tools;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IVariableScopeCapability;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.adapter.IAdapterManager;
import org.rcfaces.core.internal.manager.ITransientAttributesManager;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.model.IAdaptable;
import org.rcfaces.core.model.ICheckProvider;
import org.rcfaces.core.model.ICursorProvider;
import org.rcfaces.core.model.ISelectionProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public final class ComponentTools {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ComponentTools.class);

    public static final String[] STRING_EMPTY_ARRAY = new String[0];

    private static final String VARIABLE_SCOPE_VALUE = "org.rcfaces.core.internal.VARIABLE_SCOPE_VALUE";

    private static final String NONE_VARIABLE_SCOPE = "##~NONE~"
            + System.currentTimeMillis();

    public static final boolean isAnonymousComponentId(String componentId) {
        if (componentId == null) {
            return false;
        }

        int idx = componentId.lastIndexOf(NamingContainer.SEPARATOR_CHAR);
        if (idx >= 0) {
            componentId = componentId.substring(idx + 1);
        }

        return componentId.startsWith(UIViewRoot.UNIQUE_ID_PREFIX);
    }

    public static final void encodeRecursive(FacesContext context,
            UIComponent component) throws WriterException {
        if (component.isRendered() == false) {
            return;
        }

        try {
            component.encodeBegin(context);

            if (component.getRendersChildren()) {
                component.encodeChildren(context);

            } else {
                encodeChildrenRecursive(context, component);
            }

            component.encodeEnd(context);

        } catch (WriterException ex) {
            throw ex;

        } catch (IOException ex) {
            throw new WriterException("Can not encode component '"
                    + component.getId() + "'.", ex, component);
        }
    }

    public static final void encodeChildrenRecursive(FacesContext context,
            UIComponent component) throws WriterException {
        Iterator children = component.getChildren().iterator();

        for (; children.hasNext();) {
            UIComponent child = (UIComponent) children.next();
            if (child.isRendered() == false) {
                continue;
            }

            try {
                child.encodeBegin(context);

                if (child.getRendersChildren()) {
                    child.encodeChildren(context);

                } else {
                    encodeChildrenRecursive(context, child);
                }

                child.encodeEnd(context);

            } catch (WriterException ex) {
                throw ex;

            } catch (IOException ex) {
                throw new WriterException("Can not encode child component '"
                        + child.getId() + "'.", ex, child);
            }
        }
    }

    public static UIComponent getForComponent(FacesContext context,
            String forComponent, UIComponent component) {
        if (forComponent == null || forComponent.length() < 1) {
            return null;
        }

        try {
            // Check the naming container of the current
            // component for component identified by
            // 'forComponent'
            for (UIComponent currentParent = component; currentParent != null; currentParent = currentParent
                    .getParent()) {

                UIComponent result = currentParent.findComponent(forComponent);
                if (result != null) {
                    return result;
                }
            }

            return findUIComponentBelow(context.getViewRoot(), forComponent);

        } catch (RuntimeException ex) {
            throw ex;

        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static UIComponent findUIComponentBelow(UIComponent startPoint,
            String forComponent) {
        UIComponent retComp = null;
        List children = startPoint.getChildren();

        int size = children.size();
        for (int i = 0; i < size; i++) {
            UIComponent comp = (UIComponent) children.get(i);

            if (comp instanceof NamingContainer) {
                retComp = comp.findComponent(forComponent);
                if (retComp != null) {
                    return retComp;
                }
            }

            if (comp.getChildCount() > 0) {
                retComp = findUIComponentBelow(comp, forComponent);
                if (retComp != null) {
                    return retComp;
                }
            }
        }

        return null;
    }

    public static UIViewRoot getViewRoot(UIComponent component) {
        for (; component != null;) {

            if (component instanceof UIViewRoot) {
                return (UIViewRoot) component;
            }

            component = component.getParent();
        }

        return null;
    }

    public static Converter createConverter(FacesContext facesContext,
            String converterId) {
        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        Converter converter = facesContext.getApplication().createConverter(
                converterId);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Get converter id=" + converterId + "' object="
                    + converter);
        }

        return converter;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class Entry implements Map.Entry {
        private final Object key;

        private Object value;

        /**
         * Create new entry.
         */
        Entry(Object k, Object v) {
            value = v;
            key = k;
        }

        public Object getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }

            Map.Entry e = (Map.Entry) o;
            Object k1 = getKey();
            Object k2 = e.getKey();
            if (k1 == k2 || (k1 != null && k1.equals(k2))) {
                Object v1 = getValue();
                Object v2 = e.getValue();
                if (v1 == v2 || (v1 != null && v1.equals(v2))) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return key.hashCode() ^ (value == null ? 0 : value.hashCode());
        }

        public String toString() {
            return getKey() + "=" + getValue();
        }

        public Object setValue(Object value) {
            throw new UnsupportedOperationException("Not implemented !");
        }
    }

    public static UIComponent findComponent(UIComponent startPoint, Class clazz) {
        UIComponent retComp = null;
        List children = startPoint.getChildren();

        int size = children.size();
        for (int i = 0; i < size; i++) {
            UIComponent comp = (UIComponent) children.get(i);

            if (clazz.isInstance(comp)) {
                return comp;
            }

            if (comp.getChildCount() > 0) {
                retComp = findComponent(comp, clazz);
                if (retComp != null) {
                    return retComp;
                }
            }
        }

        return null;
    }

    public static IVarScope processVariableScope(FacesContext facesContext,
            IVariableScopeCapability variableScopeCapability) {
        String var = variableScopeCapability.getScopeVar();
        if (var == null || var.length() < 1) {
            return null;
        }

        ITransientAttributesManager manager = (ITransientAttributesManager) variableScopeCapability;
        Object ret = manager.getTransientAttribute(VARIABLE_SCOPE_VALUE);
        if (ret != null) {
            if (ret == NONE_VARIABLE_SCOPE) {
                ret = null;
            }

            Map requestMap = facesContext.getExternalContext().getRequestMap();

            Object old = requestMap.put(var, ret);

            return new VarScope(var, old);
        }

        ValueBinding valueBinding = variableScopeCapability.getScopeValue();
        if (valueBinding == null) {
            return null;
        }

        Map requestMap = facesContext.getExternalContext().getRequestMap();

        ret = valueBinding.getValue(facesContext);
        if (ret == null) {
            manager.setTransientAttribute(VARIABLE_SCOPE_VALUE,
                    NONE_VARIABLE_SCOPE);

        } else {
            manager.setTransientAttribute(VARIABLE_SCOPE_VALUE, ret);
        }

        Object old = requestMap.put(var, ret);
        return new VarScope(var, old);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface IVarScope {
        void popVar(FacesContext facesContext);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class VarScope implements IVarScope {
        private static final String REVISION = "$Revision$";

        private final String var;

        private final Object previousObject;

        public VarScope(String var, Object previousObject) {
            this.var = var;
            this.previousObject = previousObject;
        }

        public void popVar(FacesContext facesContext) {
            Map requestMap = facesContext.getExternalContext().getRequestMap();

            if (previousObject == null) {
                requestMap.remove(var);
                return;
            }

            requestMap.put(var, previousObject);
        }
    }

    public static void broadcast(UIComponent component, FacesEvent facesEvent,
            FacesListener listeners[]) {

        if (facesEvent == null) {
            throw new NullPointerException("Event is null");
        }

        if (listeners == null || listeners.length < 1) {
            return;
        }

        for (int i = 0; i < listeners.length; i++) {
            FacesListener listener = listeners[i];

            if (facesEvent.isAppropriateListener(listener) == false) {
                continue;
            }

            facesEvent.processListener(listener);
        }
    }

    public static Object getSelectedValues(Object value, UIComponent component,
            FacesContext facesContext) {
        if (value == null) {
            return null;
        }

        ISelectionProvider selectionProvider = null;

        if (value instanceof IAdaptable) {
            selectionProvider = (ISelectionProvider) ((IAdaptable) value)
                    .getAdapter(ISelectionProvider.class, component);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Try to adapt '" + value
                        + "' to ISelectionProvider => " + selectionProvider);
            }
        }

        if (selectionProvider == null) {
            IAdapterManager adapterManager = RcfacesContext.getInstance(
                    facesContext).getAdapterManager();

            selectionProvider = (ISelectionProvider) adapterManager.getAdapter(
                    value, ISelectionProvider.class, component);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Ask adapterManager to adapt '" + value
                        + "' to ISelectionProvider => " + selectionProvider);
            }
        }

        if (selectionProvider == null) {
            return null;
        }

        return selectionProvider.getSelection();
    }

    public static Object getCheckedValues(Object value, UIComponent component,
            FacesContext facesContext) {
        if (value == null) {
            return null;
        }

        ICheckProvider checkProvider = null;

        if (value instanceof IAdaptable) {
            checkProvider = (ICheckProvider) ((IAdaptable) value).getAdapter(
                    ICheckProvider.class, component);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Try to adapt '" + value + "' to ICheckProvider => "
                        + checkProvider);
            }
        }

        if (checkProvider == null) {
            IAdapterManager adapterManager = RcfacesContext.getInstance(
                    facesContext).getAdapterManager();

            checkProvider = (ICheckProvider) adapterManager.getAdapter(value,
                    ICheckProvider.class, component);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Ask adapterManager to adapt '" + value
                        + "' to ICheckProvider => " + checkProvider);
            }
        }

        if (checkProvider == null) {
            return null;
        }

        return checkProvider.getCheckValue();
    }

    public static Object getCursorValue(Object value, UIComponent component,
            FacesContext facesContext) {
        if (value == null) {
            return null;
        }

        ICursorProvider cursorProvider = null;

        if (value instanceof IAdaptable) {
            cursorProvider = (ICursorProvider) ((IAdaptable) value).getAdapter(
                    ICursorProvider.class, component);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Try to adapt '" + value + "' to ICursorProvider => "
                        + cursorProvider);
            }
        }

        if (cursorProvider == null) {
            IAdapterManager adapterManager = RcfacesContext.getInstance(
                    facesContext).getAdapterManager();

            cursorProvider = (ICursorProvider) adapterManager.getAdapter(value,
                    ICursorProvider.class, component);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Ask adapterManager to adapt '" + value
                        + "' to ICursorProvider => " + cursorProvider);
            }
        }

        if (cursorProvider == null) {
            return null;
        }

        return cursorProvider.getCursorValue();
    }
}
