/*
 * $Id$
 */
package org.rcfaces.core.internal.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public final class ApplicationParametersMap implements Map {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ApplicationParametersMap.class);

    private static final Object REMOVED = new Object();

    private ServletContext servletContext;

    private ExternalContext externalContext;

    public ApplicationParametersMap(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ApplicationParametersMap(FacesContext facesContext) {
        this.externalContext = facesContext.getExternalContext();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    public Set entrySet() {
        throw new UnsupportedOperationException();
    }

    public Object get(Object key) {
        Object ret = getAttribute(key);
        if (ret == REMOVED) {
            return null;
        }
        if (ret != null) {
            return ret;
        }

        return getInitParameter(key);
    }

    public boolean isEmpty() {
        return false;
    }

    public Set keySet() {
        throw new UnsupportedOperationException();
    }

    public Object put(Object key, Object value) {

        Object old = getInitParameter(key);
        Object old2 = getAttribute(key);

        if (value == null) {
            if (old != null) {
                if (old2 == REMOVED) {
                    old = null;

                } else if (old2 != null) {
                    old = old2;
                }

                setAttribute(key, REMOVED);
                return old;
            }

            removeAttribute(key);
            return old2;
        }

        if (old2 == REMOVED) {
            old2 = null;
        }

        setAttribute(key, value);

        return old2;
    }

    public void putAll(Map arg0) {
        throw new UnsupportedOperationException();
    }

    public Object remove(Object key) {
        return put(key, REMOVED);
    }

    public int size() {
        throw new UnsupportedOperationException();
    }

    public Collection values() {
        throw new UnsupportedOperationException();
    }

    private Object getInitParameter(Object key) {
        if (servletContext != null) {
            return servletContext.getInitParameter((String) key);
        }

        return externalContext.getInitParameter((String) key);
    }

    private Object getAttribute(Object key) {
        if (servletContext != null) {
            return servletContext.getAttribute((String) key);
        }

        return externalContext.getApplicationMap().get(key);
    }

    private void removeAttribute(Object key) {
        if (servletContext != null) {
            servletContext.removeAttribute((String) key);
            return;
        }

        externalContext.getApplicationMap().remove(key);
    }

    private void setAttribute(Object key, Object value) {
        if (servletContext != null) {
            servletContext.setAttribute((String) key, value);
            return;
        }

        // Ne compile pas sans le cast !!!
        externalContext.getApplicationMap().put((String)key, value);
    }

}