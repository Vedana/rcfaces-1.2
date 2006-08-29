/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.1  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.3  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.2  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.1  2005/02/21 17:33:06  oeuillot
 * Reorganisation du JAVASCRIPT
 * Reorganisation des ImageXxxxButton
 * Reorganise le ComponentTools => Converters
 *
 */
package org.rcfaces.core.internal.converter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.component.capability.ICalendarModeCapability;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class CalendarModeConverter implements Converter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new CalendarModeConverter();

    private static final Integer DEFAULT_CALENDAR_MODE = new Integer(
            ICalendarModeCapability.DEFAULT_CALENDAR_MODE);

    private static Map CALENDAR_MODES = new HashMap(5);
    static {
        CALENDAR_MODES.put(ICalendarModeCapability.CALENDAR_MODE_DATE_NAME,
                new Integer(ICalendarModeCapability.CALENDAR_MODE_DATE));
        CALENDAR_MODES.put(ICalendarModeCapability.CALENDAR_MODE_PERIOD_NAME,
                new Integer(ICalendarModeCapability.CALENDAR_MODE_PERIOD));
        CALENDAR_MODES.put(ICalendarModeCapability.CALENDAR_MODE_PERIODS_NAME,
                new Integer(ICalendarModeCapability.CALENDAR_MODE_PERIODS));
        CALENDAR_MODES.put(ICalendarModeCapability.DEFAULT_CALENDAR_MODE_NAME,
                new Integer(ICalendarModeCapability.DEFAULT_CALENDAR_MODE));
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent, java.lang.String)
     */
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {

        if (value == null || value.length() < 1 || "default".equals(value)) {
            return DEFAULT_CALENDAR_MODE;
        }

        value = value.toLowerCase();

        Integer i = (Integer) CALENDAR_MODES.get(value);
        if (i != null) {
            return i;
        }

        throw new IllegalArgumentException("Keyword '" + value
                + "' is not supported for a calendar-mode type !");
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
            return (String) CALENDAR_MODES.get(DEFAULT_CALENDAR_MODE);
        }

        if ((value instanceof Integer) == false) {
            throw new IllegalArgumentException("Value must be an Integer !");
        }

        for (Iterator it = CALENDAR_MODES.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            if (value.equals(entry.getValue())) {
                return (String) entry.getKey();
            }
        }

        throw new IllegalArgumentException("Value '" + value
                + "' is not supported for a calendar-mode type !");
    }

    public static final String getName(int hiddenMode) {
        switch (hiddenMode) {
        case ICalendarModeCapability.CALENDAR_MODE_DATE:
            return ICalendarModeCapability.CALENDAR_MODE_DATE_NAME;

        case ICalendarModeCapability.CALENDAR_MODE_PERIOD:
            return ICalendarModeCapability.CALENDAR_MODE_PERIOD_NAME;

        case ICalendarModeCapability.CALENDAR_MODE_PERIODS:
            return ICalendarModeCapability.CALENDAR_MODE_PERIODS_NAME;
        }

        return null;
    }
}
