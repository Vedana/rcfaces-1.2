/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.facelets;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagAttributeException;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;
import com.sun.facelets.tag.jsf.ComponentSupport;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class LoadClientBundleHandler extends TagHandler {
    private static final String REVISION = "$Revision$";

    private final TagAttribute basename;

    private final TagAttribute var;

    public LoadClientBundleHandler(TagConfig config) {
        super(config);

        this.basename = this.getRequiredAttribute("basename");
        this.var = this.getRequiredAttribute("var");
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private final static class ResourceBundleMap implements Map {
        private static final String REVISION = "$Revision$";

        protected final ResourceBundle bundle;

        public ResourceBundleMap(ResourceBundle bundle) {
            this.bundle = bundle;
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }

        public boolean containsKey(Object key) {
            try {
                bundle.getString(key.toString());
                return true;
            } catch (MissingResourceException e) {
                return false;
            }
        }

        public boolean containsValue(Object value) {
            throw new UnsupportedOperationException();
        }

        public Set entrySet() {
            Set s = new HashSet();
            for (Enumeration e = bundle.getKeys(); e.hasMoreElements();) {
                String k = (String) e.nextElement();

                s.add(new ResourceEntry(k, bundle.getString(k)));
            }
            return s;
        }

        public Object get(Object key) {
            return bundle.getObject((String) key);
        }

        public boolean isEmpty() {
            return false;
        }

        public Set keySet() {
            Set s = new HashSet();
            for (Enumeration e = bundle.getKeys(); e.hasMoreElements();) {
                s.add(e.nextElement());
            }
            return s;
        }

        public Object put(Object key, Object value) {
            throw new UnsupportedOperationException();
        }

        public void putAll(Map t) {
            throw new UnsupportedOperationException();
        }

        public Object remove(Object key) {
            throw new UnsupportedOperationException();
        }

        public int size() {
            return this.keySet().size();
        }

        public Collection values() {
            Set s = new HashSet();
            for (Enumeration e = bundle.getKeys(); e.hasMoreElements();) {
                s.add(bundle.getObject((String) e.nextElement()));
            }
            return s;
        }

        /**
         * 
         * @author Olivier Oeuillot (latest modification by $Author$)
         * @version $Revision$ $Date$
         */
        private final static class ResourceEntry implements Map.Entry {
            private static final String REVISION = "$Revision$";

            protected final String key;

            protected final String value;

            public ResourceEntry(String key, String value) {
                this.key = key;
                this.value = value;
            }

            public Object getKey() {
                return this.key;
            }

            public Object getValue() {
                return this.value;
            }

            public Object setValue(Object value) {
                throw new UnsupportedOperationException();
            }

            public int hashCode() {
                final int PRIME = 31;
                int result = 1;
                result = PRIME * result + ((key == null) ? 0 : key.hashCode());
                result = PRIME * result
                        + ((value == null) ? 0 : value.hashCode());
                return result;
            }

            public boolean equals(Object obj) {
                if (this == obj)
                    return true;
                if (obj == null)
                    return false;
                if (getClass() != obj.getClass())
                    return false;

                final ResourceEntry other = (ResourceEntry) obj;
                if (key == null) {
                    if (other.key != null) {
                        return false;
                    }

                } else if (!key.equals(other.key)) {
                    return false;
                }

                if (value == null) {
                    if (other.value != null) {
                        return false;
                    }
                } else if (!value.equals(other.value)) {
                    return false;
                }

                return true;
            }

        }
    }

    /**
     * See taglib documentation.
     * 
     * @see com.sun.facelets.FaceletHandler#apply(com.sun.facelets.FaceletContext,
     *      javax.faces.component.UIComponent)
     */
    public void apply(FaceletContext ctx, UIComponent parent) {
        UIViewRoot root = ComponentSupport.getViewRoot(ctx, parent);
        ResourceBundle bundle = null;
        try {
            String name = basename.getValue(ctx);

            Locale locale = null;
            if (root != null) {
                locale = root.getLocale();
            }

            if (locale == null) {
                locale = Locale.getDefault();
            }

            ClassLoader cl = Thread.currentThread().getContextClassLoader();

            bundle = ResourceBundle.getBundle(name, locale, cl);

        } catch (Throwable th) {
            throw new TagAttributeException(tag, basename, th);
        }

        ResourceBundleMap map = new ResourceBundleMap(bundle);
        FacesContext facesContext = ctx.getFacesContext();
        facesContext.getExternalContext().getRequestMap().put(
                var.getValue(ctx), map);
    }

}
