/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.taglib;

import javax.faces.webapp.UIComponentELTag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class CameliaTag extends UIComponentELTag {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(CameliaTag.class);

    protected static final boolean getBool(String value) {
        return Boolean.valueOf(value).booleanValue();
    }

    protected static final Boolean getBoolean(String value) {
        if (value == null) {
            return null;
        }

        return Boolean.valueOf(value);
    }

    protected static final Integer getInteger(String value) {
        if (value == null) {
            return null;
        }

        return Integer.valueOf(value);
    }

    protected static final int getInt(String value) {
        return Integer.parseInt(value);
    }

    protected static final double getDouble(String value) {
        return Double.parseDouble(value);
    }

    public String getRendererType() {
        return null;
    }

}
