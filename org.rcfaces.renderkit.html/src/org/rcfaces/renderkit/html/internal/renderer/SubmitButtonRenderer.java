/*
 * $Id$
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
public class SubmitButtonRenderer extends ButtonRenderer {
    private static final String REVISION = "$Revision$";

    /*
     * protected void encodeComponent(IHtmlWriter htmlWriter) throws
     * WriterException { super.encodeComponent(htmlWriter);
     * 
     * htmlWriter.enableJavaScript(); }
     */

    protected String getInputType(UIComponent component) {
        return IHtmlWriter.SUBMIT_INPUT_TYPE;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.SUBMIT_BUTTON;
    }
}