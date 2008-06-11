/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.border;

import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.renderer.ICssStyleClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SolidBorderRenderer extends AbstractHtmlBorderRenderer {
    private static final String REVISION = "$Revision$";

    public static final String SOLID_BORDER_CLASS = "fb_solid";

    protected boolean hasBorder() {
        return false;
    }

    protected String getClassName() {
        return SOLID_BORDER_CLASS;
    }

    public void initialize(IHtmlWriter writer,
            ICssStyleClasses cssStyleClasses, String width, String height,
            int horizontalSpan, int verticalSpan, boolean disabled,
            boolean selected) throws WriterException {

        if (horizontalSpan < 2 && verticalSpan < 2) {
            // C'est pas Thread-safe ca !!!!
            this.noTable = true;
        }

        super.initialize(writer, cssStyleClasses, width, height,
                horizontalSpan, verticalSpan, disabled, selected);
    }

}
