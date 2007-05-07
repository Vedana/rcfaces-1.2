/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;

import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ResetButtonRenderer extends ButtonRenderer {
    private static final String REVISION = "$Revision$";

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractInputRenderer#getInputType()
     */
    protected String getInputType(UIComponent component) {
        return IHtmlWriter.RESET_INPUT_TYPE;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.RESET_BUTTON;
    }
}
