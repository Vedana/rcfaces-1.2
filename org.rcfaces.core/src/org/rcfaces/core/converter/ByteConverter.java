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
public class ByteConverter extends AbstractNumberConverter {
    private static final String REVISION = "$Revision$";

    public ByteConverter() {
        setIntegerOnly(true);
        setMaxFractionDigits(3);
    }

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {

        if (value == null || value.trim().length() < 1) {
            Object v = getDefaultValue();
            if (v instanceof Byte) {
                return v;
            }
            if (v instanceof Number) {
                return new Byte(((Number) v).byteValue());
            }
            value = (String) v;
        }

        Number number = (Number) super.getAsObject(context, component, value);

        if (number == null || (number instanceof Byte)) {
            return number;
        }

        long l = number.longValue();
        if (l < Byte.MIN_VALUE || l > Byte.MAX_VALUE) {
            throw new ConverterException(
                    "Number can not be converted to byte. (value=" + l + ")");
        }

        return new Byte(number.byteValue());
    }
}
