/*
 * $Id$
 */
package org.rcfaces.core.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AbstractNumberConverter extends
        javax.faces.convert.NumberConverter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new AbstractNumberConverter();

    private Object defaultValue;

    public final Object getDefaultValue() {
        return defaultValue;
    }

    public final void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public final void setDefaultValue(Number defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        if (context == null) {
            context = FacesContext.getCurrentInstance();
        }

        return super.getAsObject(context, component, value);
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        if (context == null) {
            context = FacesContext.getCurrentInstance();
        }

        return super.getAsString(context, component, value);
    }

}
