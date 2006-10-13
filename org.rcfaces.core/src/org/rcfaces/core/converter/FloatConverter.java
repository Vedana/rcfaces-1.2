/*
 * $Id$
 */
package org.rcfaces.core.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FloatConverter extends AbstractNumberConverter {
    private static final String REVISION = "$Revision$";

    public FloatConverter() {
        setIntegerOnly(false);
    }

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {

        if (value == null || value.trim().length() < 1) {
            Object v = getDefaultValue();
            if (v instanceof Float) {
                return v;
            }
            if (v instanceof Number) {
                return new Float(((Number) v).floatValue());
            }
            value = (String) v;
        }

        Number number = (Number) super.getAsObject(context, component, value);

        if (number == null || (number instanceof Float)) {
            return number;
        }

        double d = number.doubleValue();
        if (d < Float.MIN_VALUE || d > Float.MAX_VALUE) {
            throw new ConverterException(
                    "Number can not be converted to float. (value=" + d + ")");
        }

        return new Float(number.floatValue());
    }
}
