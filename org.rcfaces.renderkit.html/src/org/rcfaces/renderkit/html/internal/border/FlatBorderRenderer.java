/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.border;

import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FlatBorderRenderer extends AbstractHtmlBorderRenderer {
    private static final String REVISION = "$Revision$";

    private static final String FLAT_BORDER_CLASS = "fb_flat";

    public void initialize(IHtmlWriter writer, String componentClassName,
            String width, String height, int horizontalSpan, int verticalSpan,
            boolean disabled, boolean selected) throws WriterException {
        super.initialize(writer, componentClassName, width, height,
                horizontalSpan, verticalSpan, disabled, selected);

        writer.writeAttribute("v:flatMode", true);
    }

    protected boolean hasBorder() {
        return true;
    }

    protected String getClassName() {
        return FLAT_BORDER_CLASS;
    }

}
