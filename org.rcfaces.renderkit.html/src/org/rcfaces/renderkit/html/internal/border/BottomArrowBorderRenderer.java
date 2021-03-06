/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.border;

import org.rcfaces.renderkit.html.internal.IHtmlWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BottomArrowBorderRenderer extends NoneBorderRenderer {

    protected String getComboImageVerticalAlign(IHtmlWriter writer) {
        return "bottom";
    }

}
