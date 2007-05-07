/**
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
public class PasswordEntryRenderer extends TextEntryRenderer {
    private static final String REVISION = "$Revision$";

    protected String getInputType(UIComponent component) {
        return IHtmlWriter.PASSWORD_INPUT_TYPE;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.PASSWORD_ENTRY;
    }
}
