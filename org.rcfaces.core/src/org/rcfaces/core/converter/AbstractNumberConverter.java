/*
 * $Id$
 */
package org.rcfaces.core.converter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AbstractNumberConverter extends
        javax.faces.convert.NumberConverter {
    private static final String REVISION = "$Revision$";

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

}
