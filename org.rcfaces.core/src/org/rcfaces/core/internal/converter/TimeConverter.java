/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.internal.tools.TimeTools;
import org.rcfaces.core.model.AbstractConverter;
import org.rcfaces.core.model.Time;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TimeConverter extends AbstractConverter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new TimeConverter();

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        if (value == null || value.trim().length() < 1) {
            return null;
        }

        return TimeTools.parseValue(context, component, value);
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        if (value == null) {
            return null;
        }

        return TimeTools.formatValue(component, (Time) value);
    }
}
