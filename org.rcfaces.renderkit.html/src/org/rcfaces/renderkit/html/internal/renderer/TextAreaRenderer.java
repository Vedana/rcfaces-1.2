/**
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.TextAreaComponent;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TextAreaRenderer extends TextEntryRenderer {
    private static final String REVISION = "$Revision$";

    /*
     * public void encodeBegin(IWriter writer) throws WriterException { }
     */

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {
        TextAreaComponent textAreaComponent = (TextAreaComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        htmlWriter.startElement("TEXTAREA");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);
        writeInputAttributes(htmlWriter);

        int col = textAreaComponent.getColumnNumber(facesContext);
        if (col > 0) {
            htmlWriter.writeCols(col);
        }
        int row = textAreaComponent.getRowNumber(facesContext);
        if (row > 0) {
            htmlWriter.writeRows(row);
        }

        writeTextEntryAttributes(htmlWriter);
        writeValidatorAttributes(htmlWriter);

        String txt = textAreaComponent.getText(facesContext);
        if (txt != null) {
            htmlWriter.writeText(txt);
        }

        htmlWriter.endElement("TEXTAREA");

        if (textAreaComponent.isRequired()) {
            // Il nous faut le javascript, car c'est un traitement javascript !
            htmlWriter.enableJavaScript();
        }
    }

    protected boolean useHtmlAccessKeyAttribute() {
        return true;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TEXT_AREA;
    }
}