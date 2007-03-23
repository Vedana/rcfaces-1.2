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

import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import org.rcfaces.core.model.AbstractConverter;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HorizontalTextPositionConverter extends AbstractConverter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new HorizontalTextPositionConverter();

    protected static final String RIGHT_POSITION_NAME = "right";

    protected static final String LEFT_POSITION_NAME = "left";

    private static final String DEFAULT_POSITION_NAME = "default";

    private static final Integer DEFAULT_POSITION = new Integer(
            IHorizontalTextPositionCapability.DEFAULT_POSITION);

    protected static Map HORIZONTAL_TEXT_POSITIONS = new HashMap(5);
    static {
        Integer i = new Integer(IHorizontalTextPositionCapability.LEFT_POSITION);
        HORIZONTAL_TEXT_POSITIONS.put(LEFT_POSITION_NAME, i);

        i = new Integer(IHorizontalTextPositionCapability.RIGHT_POSITION);
        HORIZONTAL_TEXT_POSITIONS.put(RIGHT_POSITION_NAME, i);
    }

    protected Map getTextPositions() {
        return HORIZONTAL_TEXT_POSITIONS;
    }

    protected Integer getDefaultPosition() {
        return DEFAULT_POSITION;
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
            return getDefaultPosition();
        }

        value = value.toLowerCase();

        Integer i = (Integer) getTextPositions().get(value);
        if (i != null) {
            return i;
        }

        throw new IllegalArgumentException("Keyword '" + value
                + "' is not supported for a text-position type !");
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
            return (String) getTextPositions().get(getDefaultPosition());
        }

        if ((value instanceof Integer) == false) {
            throw new IllegalArgumentException("Value must be an Integer !");
        }

        for (Iterator it = getTextPositions().entrySet().iterator(); it
                .hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            if (value.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }

        throw new IllegalArgumentException("Value '" + value
                + "' is not supported for a text-position type !");
    }

}
