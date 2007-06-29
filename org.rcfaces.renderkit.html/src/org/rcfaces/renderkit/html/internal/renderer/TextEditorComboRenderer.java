/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.TextEditorComboComponent;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TextEditorComboRenderer extends ComboRenderer {
    private static final String REVISION = "$Revision$";

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        TextEditorComboComponent component = (TextEditorComboComponent) writer
                .getComponentRenderContext().getComponent();

        component.setDisabled(true);

        super.encodeBegin(writer);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TEXT_EDITOR_COMBO;
    }

    protected void writeComboAttributes(IHtmlWriter htmlWriter)
            throws WriterException {

        super.writeComboAttributes(htmlWriter);

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        TextEditorComboComponent combo = (TextEditorComboComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        String type = combo.getType(facesContext);
        if (type != null) {
            htmlWriter.writeAttribute("v:type", type);
        }

        String forProperty = combo.getFor(facesContext);
        if (forProperty != null) {
            htmlWriter.writeAttribute("v:for", forProperty);
        }

        htmlWriter.enableJavaScript();
    }
}
