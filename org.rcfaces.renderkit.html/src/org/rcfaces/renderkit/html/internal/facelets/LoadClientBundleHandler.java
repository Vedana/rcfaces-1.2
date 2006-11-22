/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.facelets;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
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

    private final static class ResourceBundleMap implements Map {
        private final static class ResourceEntry implements Map.Entry {

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
                return this.key.hashCode();
            }

            public boolean equals(Object obj) {
                return (obj instanceof ResourceEntry && this.hashCode() == obj
                        .hashCode());
            }
        }

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
            Enumeration e = this.bundle.getKeys();
            Set s = new HashSet();
            String k;
            while (e.hasMoreElements()) {
                k = (String) e.nextElement();
                s.add(new ResourceEntry(k, this.bundle.getString(k)));
            }
            return s;
        }

        public Object get(Object key) {
            return this.bundle.getObject((String) key);
        }

        public boolean isEmpty() {
            return false;
        }

        public Set keySet() {
            Enumeration e = this.bundle.getKeys();
            Set s = new HashSet();
            while (e.hasMoreElements()) {
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
            Enumeration e = this.bundle.getKeys();
            Set s = new HashSet();
            while (e.hasMoreElements()) {
                s.add(this.bundle.getObject((String) e.nextElement()));
            }
            return s;
        }
    }

    /**
     * See taglib documentation.
     * 
     * @see com.sun.facelets.FaceletHandler#apply(com.sun.facelets.FaceletContext,
     *      javax.faces.component.UIComponent)
     */
    public void apply(FaceletContext ctx, UIComponent parent)
            throws IOException, FacesException, FaceletException, ELException {
        UIViewRoot root = ComponentSupport.getViewRoot(ctx, parent);
        ResourceBundle bundle = null;
        try {
            String name = this.basename.getValue(ctx);
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (root != null && root.getLocale() != null) {
                bundle = ResourceBundle.getBundle(name, root.getLocale(), cl);
            } else {
                bundle = ResourceBundle
                        .getBundle(name, Locale.getDefault(), cl);
            }

        } catch (Throwable th) {
            throw new TagAttributeException(this.tag, this.basename, th);
        }
        ResourceBundleMap map = new ResourceBundleMap(bundle);
        FacesContext faces = ctx.getFacesContext();
        faces.getExternalContext().getRequestMap().put(this.var.getValue(ctx),
                map);
    }

}
