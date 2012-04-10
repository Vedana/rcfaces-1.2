/*
 * $Id$
 */
package org.rcfaces.core.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DoubleConverter extends AbstractNumberConverter {

    public DoubleConverter() {
        setIntegerOnly(false);
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {

        if (value == null || value.trim().length() < 1) {
            Object v = getDefaultValue();
            if (v instanceof Double) {
                return v;
            }
            if (v instanceof Number) {
                return new Double(((Number) v).doubleValue());
            }
            value = (String) v;
        }

        Number number = (Number) super.getAsObject(context, component, value);

        if (number == null || (number instanceof Double)) {
            return number;
        }

        return new Double(number.doubleValue());
    }
}
