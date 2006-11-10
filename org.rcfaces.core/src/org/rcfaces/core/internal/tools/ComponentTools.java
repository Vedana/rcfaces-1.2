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

import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public final class ComponentTools {
    private static final String REVISION = "$Revision$";

    public static final String[] STRING_EMPTY_ARRAY = new String[0];

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
        return facesContext.getApplication().createConverter(converterId);
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
}
