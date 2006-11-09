/*
 * $Id$
 */
package org.rcfaces.core.internal.converter;

import java.util.Date;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.component.capability.ILocalizedAttributesCapability;
import org.rcfaces.core.internal.tools.CalendarTools;
import org.rcfaces.core.model.AbstractConverter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class LocalizedDateConverter extends AbstractConverter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new LocalizedDateConverter();

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {

        UIComponent calendarComponent = findCalendarComponent(component);

        return CalendarTools.parseValue(calendarComponent, value);
    }

    protected UIComponent findCalendarComponent(UIComponent component) {

        for (; component != null; component = component.getParent()) {
            if ((component instanceof ILocalizedAttributesCapability) == false) {
                continue;
            }

            return component;
        }

        throw new FacesException(
                "CalendarConvert can not find ILocalizedAttributesCapability !");

    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        if (value == null) {
            return null;
        }

        // XXX Gerer le cas de plusieurs Dates
        Date date = (Date) value;

        UIComponent calendarComponent = findCalendarComponent(component);

        return CalendarTools.formatDate(calendarComponent, date);
    }
}
