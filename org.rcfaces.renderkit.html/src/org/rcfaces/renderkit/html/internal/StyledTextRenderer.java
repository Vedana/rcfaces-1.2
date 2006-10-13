/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.component.TextComponent;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StyledTextRenderer extends TextRenderer {
    private static final String REVISION = "$Revision$";

    protected boolean writeText(IHtmlWriter writer, TextComponent textComponent)
            throws WriterException {

        String text = textComponent.getText();
        if (text == null || text.length() < 1) {
            return false;
        }

        writer.write(text);

        return false;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.STYLED_TEXT;
    }
}