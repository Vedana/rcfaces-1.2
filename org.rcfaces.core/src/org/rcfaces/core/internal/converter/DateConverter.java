/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.converter;

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.internal.tools.CalendarTools;
import org.rcfaces.core.model.AbstractConverter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DateConverter extends AbstractConverter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new DateConverter();

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        return CalendarTools.parseValue(null, component, value, isLiteral());
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        return CalendarTools.formatDate(component, (Date) value, isLiteral());
    }

    protected boolean isLiteral() {
        return false;
    }
}
