/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import org.rcfaces.core.component.TextComponent;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StyledTextRenderer extends TextRenderer {
    private static final String REVISION = "$Revision$";

    protected boolean writeText(IHtmlWriter writer, TextComponent textComponent)
            throws WriterException {

        String text = textComponent.getText(writer.getComponentRenderContext()
                .getFacesContext());

        if (text == null || text.length() < 1) {
            return false;
        }
        text = ParamUtils.formatMessage(textComponent, text);

        if (text.length() > 0) {
            writer.write(text);
        }

        return false;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.STYLED_TEXT;
    }
}