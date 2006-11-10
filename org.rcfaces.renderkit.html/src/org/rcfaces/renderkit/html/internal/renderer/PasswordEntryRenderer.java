/**
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;

import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class PasswordEntryRenderer extends TextEntryRenderer {
    private static final String REVISION = "$Revision$";

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getInputType(UIComponent component) {
        return PASSWORD_TYPE;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.PASSWORD_ENTRY;
    }
}
