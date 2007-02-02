/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ProgressBarComponent;
import org.rcfaces.core.component.ProgressIndicatorComponent;
import org.rcfaces.core.component.TextComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.ISgmlWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.renderkit.html.internal.AbstractTemplateRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ProgressIndicatorRenderer extends AbstractTemplateRenderer {
    private static final String REVISION = "$Revision$";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.PROGRESS_INDICATOR;
    }

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        ProgressIndicatorComponent progressIndicatorComponent = (ProgressIndicatorComponent) componentRenderContext
                .getComponent();

        htmlWriter.startElement("TABLE");

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

       // htmlWriter.writeAttribute("v:nc", "true"); // C'est un naming container
        // !
        htmlWriter.writeCellSpacing(0);
        htmlWriter.writeCellPadding(0);

        htmlWriter.startElement("TR");
        htmlWriter.startElement("TD");
        htmlWriter.writeAlign("center");
        htmlWriter.writeClass(getCellBarClassName(htmlWriter));

        String width = progressIndicatorComponent.getWidth(facesContext);

        ProgressBarComponent progressBarComponent = (ProgressBarComponent) progressIndicatorComponent
                .findComponent("progressBar");
        if (progressBarComponent != null) {
            if (width != null) {
                progressBarComponent.setWidth(width);
            }

            progressBarComponent.setIndeterminate(progressIndicatorComponent
                    .isIndeterminate(facesContext));

            ComponentTools.encodeRecursive(facesContext, progressBarComponent);
        }

        htmlWriter.endElement("TD");
        htmlWriter.endElement("TR");

        htmlWriter.startElement("TR");
        htmlWriter.startElement("TD");
        htmlWriter.writeAlign("left");
        htmlWriter.writeClass(getTaskNameClassName(htmlWriter));

        TextComponent textComponent = (TextComponent) progressIndicatorComponent
                .findComponent("label");
        if (textComponent != null) {
            if (width != null) {
                textComponent.setWidth(width);
            }

            textComponent.setText(ISgmlWriter.NBSP);

            ComponentTools.encodeRecursive(facesContext, textComponent);
        }

        htmlWriter.endElement("TD");
        htmlWriter.endElement("TR");
        htmlWriter.endElement("TABLE");
    }

    protected String getTaskNameClassName(IHtmlWriter htmlWriter) {
        return getMainStyleClassName() + "_taskName";
    }

    protected String getCellBarClassName(IHtmlWriter htmlWriter) {
        return getMainStyleClassName() + "_progressBar";
    }

}