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

import org.rcfaces.core.component.capability.IAsyncDecodeModeCapability;
import org.rcfaces.core.model.AbstractConverter;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AsyncDecodeModeConverter extends AbstractConverter {
    private static final String REVISION = "$Revision$";

    private static final String COMPLETE_ASYNC_DECODE_MODE_NAME = "complete";

    private static final String PARTIAL_ASYNC_DECODE_MODE_NAME = "partial";

    private static final Integer DEFAULT_ASYNC_DECODE_MODE = new Integer(
            IAsyncDecodeModeCapability.DEFAULT_ASYNC_DECODE_MODE);

    public static final Converter SINGLETON = new AsyncDecodeModeConverter();

    private static Map ASYNC_DECODE_MODES = new HashMap(5);
    static {
        ASYNC_DECODE_MODES.put(COMPLETE_ASYNC_DECODE_MODE_NAME, new Integer(
                IAsyncDecodeModeCapability.COMPLETE_ASYNC_DECODE_MODE));
        ASYNC_DECODE_MODES.put(PARTIAL_ASYNC_DECODE_MODE_NAME, new Integer(
                IAsyncDecodeModeCapability.PARTIAL_ASYNC_DECODE_MODE));
    }

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {

        if (value == null || value.length() < 1) {
            return DEFAULT_ASYNC_DECODE_MODE;
        }

        value = value.toLowerCase();

        Integer i = (Integer) ASYNC_DECODE_MODES.get(value);
        if (i != null) {
            return i;
        }

        if ("default".equalsIgnoreCase(value)) {
            return DEFAULT_ASYNC_DECODE_MODE;
        }

        throw new IllegalArgumentException("Keyword '" + value
                + "' is not supported for a async-decode type !");
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) {

        if (value == null) {
            return (String) ASYNC_DECODE_MODES.get(DEFAULT_ASYNC_DECODE_MODE);
        }

        if ((value instanceof Integer) == false) {
            throw new IllegalArgumentException("Value must be an Integer !");
        }

        for (Iterator it = ASYNC_DECODE_MODES.entrySet().iterator(); it
                .hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            if (value.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }

        throw new IllegalArgumentException("Value '" + value
                + "' is not supported for a async-decode-mode type !");
    }

    public static final String getName(int asyncDecodeMode) {
        switch (asyncDecodeMode) {
        case IAsyncDecodeModeCapability.COMPLETE_ASYNC_DECODE_MODE:
            return COMPLETE_ASYNC_DECODE_MODE_NAME;

        case IAsyncDecodeModeCapability.PARTIAL_ASYNC_DECODE_MODE:
            return PARTIAL_ASYNC_DECODE_MODE_NAME;
        }

        return null;
    }
}
