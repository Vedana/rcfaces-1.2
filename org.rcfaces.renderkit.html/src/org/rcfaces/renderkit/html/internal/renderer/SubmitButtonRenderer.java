/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.renderkit.WriterException;
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

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {
        super.encodeComponent(htmlWriter);

        // Il faut l'activer pour récuperer la touche SUBMIT
        htmlWriter.getJavaScriptEnableMode().enableOnInit();
    }

    protected String getInputType(UIComponent component) {
        return IHtmlWriter.SUBMIT_INPUT_TYPE;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.SUBMIT_BUTTON;
    }
}