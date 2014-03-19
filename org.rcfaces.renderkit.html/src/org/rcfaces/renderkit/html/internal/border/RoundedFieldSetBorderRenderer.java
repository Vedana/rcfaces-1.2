/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.border;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class RoundedFieldSetBorderRenderer extends
        AbstractFieldSetBorderRenderer {
    

    private static final String FIELDSET_BORDER_CLASS = "fb_rounded";

    protected String getClassName() {
        return FIELDSET_BORDER_CLASS;
    }

    public int getEastBorderWidth() {
        return 8;
    }

    public int getNorthBorderHeight() {
        return 17;
    }

    public int getSouthBorderHeight() {
        return 9;
    }

    public int getWestBorderWidth() {
        return 8;
    }
}
