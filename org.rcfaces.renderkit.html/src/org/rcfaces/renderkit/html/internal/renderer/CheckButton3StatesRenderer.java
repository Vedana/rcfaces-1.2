/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractInputRenderer;
import org.rcfaces.renderkit.html.internal.IAccessibilityRoles;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CheckButton3StatesRenderer extends AbstractInputRenderer {
    private static final String REVISION = "$Revision$";

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {
        htmlWriter.writeText("Composant: checkButton3States");
    }

    protected String getWAIRole() {
        return IAccessibilityRoles.CHECK_BOX_3_STATE;
    }

    protected String getInputType(UIComponent component) {
        return "image";
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.CHECK_BUTTON_3_STATES;
    }
}
