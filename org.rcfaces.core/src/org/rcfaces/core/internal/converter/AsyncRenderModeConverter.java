/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.converter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.model.AbstractConverter;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AsyncRenderModeConverter extends AbstractConverter {
    private static final String REVISION = "$Revision$";

    private static final String NONE_ASYNC_RENDER_MODE_NAME = "none";

    private static final String BUFFER_ASYNC_RENDER_MODE_NAME = "buffer";

    private static final String TREE_ASYNC_RENDER_MODE_NAME = "tree";

    private static final Integer DEFAULT_ASYNC_RENDER_MODE = new Integer(
            IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE);

    public static final Converter SINGLETON = new AsyncRenderModeConverter();

    private static Map ASYNC_RENDER_MODES = new HashMap(5);
    static {
        ASYNC_RENDER_MODES.put(NONE_ASYNC_RENDER_MODE_NAME, new Integer(
                IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE));
        ASYNC_RENDER_MODES.put(BUFFER_ASYNC_RENDER_MODE_NAME, new Integer(
                IAsyncRenderModeCapability.BUFFER_ASYNC_RENDER_MODE));
        ASYNC_RENDER_MODES.put(TREE_ASYNC_RENDER_MODE_NAME, new Integer(
                IAsyncRenderModeCapability.TREE_ASYNC_RENDER_MODE));
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent, java.lang.String)
     */
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {

        if (value == null || value.length() < 1) {
            return DEFAULT_ASYNC_RENDER_MODE;
        }

        value = value.toLowerCase();

        Integer i = (Integer) ASYNC_RENDER_MODES.get(value);
        if (i != null) {
            return i;
        }

        if ("default".equalsIgnoreCase(value)) {
            return DEFAULT_ASYNC_RENDER_MODE;
        }

        throw new IllegalArgumentException("Keyword '" + value
                + "' is not supported for a async-render type !");
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent, java.lang.Object)
     */
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {

        if (value == null) {
            return (String) ASYNC_RENDER_MODES.get(DEFAULT_ASYNC_RENDER_MODE);
        }

        if ((value instanceof Integer) == false) {
            throw new IllegalArgumentException("Value must be an Integer !");
        }

        for (Iterator it = ASYNC_RENDER_MODES.entrySet().iterator(); it
                .hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            if (value.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }

        throw new IllegalArgumentException("Value '" + value
                + "' is not supported for a async-render-mode type !");
    }

    public static final String getName(int asyncRenderMode) {
        switch (asyncRenderMode) {
        case IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE:
            return NONE_ASYNC_RENDER_MODE_NAME;

        case IAsyncRenderModeCapability.BUFFER_ASYNC_RENDER_MODE:
            return BUFFER_ASYNC_RENDER_MODE_NAME;

        case IAsyncRenderModeCapability.TREE_ASYNC_RENDER_MODE:
            return TREE_ASYNC_RENDER_MODE_NAME;
        }

        return null;
    }
}
