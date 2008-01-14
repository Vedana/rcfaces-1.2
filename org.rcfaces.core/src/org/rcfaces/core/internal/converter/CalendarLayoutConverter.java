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

import org.rcfaces.core.component.capability.ICalendarLayoutCapability;
import org.rcfaces.core.model.AbstractConverter;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CalendarLayoutConverter extends AbstractConverter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new CalendarLayoutConverter();

    private static final String SHORT_LAYOUT_NAME = "short";

    private static final String MEDIUM_LAYOUT_NAME = "medium";

    private static final String LONG_LAYOUT_NAME = "long";

    private static final String FULL_LAYOUT_NAME = "full";

    private static final Integer DEFAULT_LAYOUT = new Integer(
            ICalendarLayoutCapability.DEFAULT_LAYOUT);

    private static Map LAYOUTS = new HashMap(4);
    static {
        Integer i = new Integer(ICalendarLayoutCapability.SHORT_LAYOUT);
        LAYOUTS.put(SHORT_LAYOUT_NAME, i);

        i = new Integer(ICalendarLayoutCapability.MEDIUM_LAYOUT);
        LAYOUTS.put(MEDIUM_LAYOUT_NAME, i);

        i = new Integer(ICalendarLayoutCapability.LONG_LAYOUT);
        LAYOUTS.put(LONG_LAYOUT_NAME, i);

        i = new Integer(ICalendarLayoutCapability.FULL_LAYOUT);
        LAYOUTS.put(FULL_LAYOUT_NAME, i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent, java.lang.String)
     */
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {

        if (value == null || value.length() < 1
                || "default".equalsIgnoreCase(value)) {
            return DEFAULT_LAYOUT;
        }

        value = value.toLowerCase();

        Integer i = (Integer) LAYOUTS.get(value);
        if (i != null) {
            return i;
        }

        throw new IllegalArgumentException("Keyword '" + value
                + "' is not supported for a calendar layout !");
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
            return (String) LAYOUTS.get(DEFAULT_LAYOUT);
        }

        if ((value instanceof Integer) == false) {
            throw new IllegalArgumentException("Value must be an Integer !");
        }

        for (Iterator it = LAYOUTS.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            if (value.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }

        throw new IllegalArgumentException("Value '" + value
                + "' is not supported for a calendar layout !");
    }
}
