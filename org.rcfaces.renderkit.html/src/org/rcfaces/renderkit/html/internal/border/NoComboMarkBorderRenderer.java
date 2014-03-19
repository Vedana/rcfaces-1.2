/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.border;

import org.rcfaces.renderkit.html.internal.IHtmlWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class NoComboMarkBorderRenderer extends NoneBorderRenderer {
    

    protected boolean hasBorder() {
        return false;
    }

    protected String getClassName() {
        return NONE_BORDER_CLASS;
    }

    public void writeComboImage(IHtmlWriter writer, String componentClassName) {
    }
}
