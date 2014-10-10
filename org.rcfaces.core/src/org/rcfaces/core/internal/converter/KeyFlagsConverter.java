/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.model.AbstractConverter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class KeyFlagsConverter extends AbstractConverter {
    public static final Converter SINGLETON = new KeyFlagsConverter();

    private static final Integer NO_FLAGS = new Integer(0);

    public static final int SHIFT_FLAG = 0x01;

    public static final int CONTROL_FLAG = 0x02;

    public static final int ALT_FLAG = 0x04;

    public static final int META_FLAG = 0x08;

    public static final int OS_FLAG = 0x10;

    private static final Map<String, Integer> FLAGS = new HashMap<String, Integer>(
            8);
    static {
        FLAGS.put("SHIFT", new Integer(SHIFT_FLAG));
        FLAGS.put("CONTROL", new Integer(CONTROL_FLAG));
        FLAGS.put("CTRL", new Integer(CONTROL_FLAG));
        FLAGS.put("ALT", new Integer(ALT_FLAG));
        FLAGS.put("META", new Integer(META_FLAG));
        FLAGS.put("OS", new Integer(OS_FLAG));
    }

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        if (value == null) {
            return NO_FLAGS;
        }

        StringTokenizer st = new StringTokenizer(value, ",; ");

        int mask = 0;

        for (; st.hasMoreTokens();) {
            String token = st.nextToken().toUpperCase();

            Integer flags = FLAGS.get(token);
            if (flags == null) {
                continue;
            }

            mask |= flags.intValue();
        }

        if (mask == 0) {
            return NO_FLAGS;
        }

        return new Integer(mask);
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        throw new FacesException("Not implemented !");
    }

    public static Integer convertUpperCase(String key) {
        return FLAGS.get(key);
    }
}
