/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.4  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.3  2005/12/27 16:08:17  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.2  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.1  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 */
package org.rcfaces.renderkit.html.internal.border;

import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
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

    public void initialize(IHtmlWriter writer, String width, String height,
            int horizontalSpan, int verticalSpan, boolean disabled,
            boolean selected) throws WriterException {

        if (horizontalSpan < 2 && verticalSpan < 2) {
            // C'est pas Thread-safe ca !!!!
            this.noTable = true;
        }

        super.initialize(writer, width, height, horizontalSpan, verticalSpan,
                disabled, selected);
    }

}
