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
public class LongConverter extends AbstractNumberConverter {
    private static final String REVISION = "$Revision$";

    public LongConverter() {
        setIntegerOnly(true);
    }

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {

        if (value == null || value.trim().length() < 1) {
            Object v = getDefaultValue();
            if (v instanceof Long) {
                return v;
            }
            if (v instanceof Number) {
                return new Long(((Number) v).longValue());
            }
            value = (String) v;
        }

        Number number = (Number) super.getAsObject(context, component, value);

        if (number == null || (number instanceof Long)) {
            return number;
        }

        double d = number.doubleValue();
        if (d < Long.MIN_VALUE || d > Long.MAX_VALUE) {
            throw new ConverterException(
                    "Number can not be converted to long. (value=" + d + ")");
        }

        return new Long(number.longValue());
    }
}
