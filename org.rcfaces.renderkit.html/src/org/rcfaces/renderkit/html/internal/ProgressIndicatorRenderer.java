/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ProgressBarComponent;
import org.rcfaces.core.component.ProgressIndicatorComponent;
import org.rcfaces.core.component.TextComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.ISgmlWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ComponentTools;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
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

        // IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext)
        // componentRenderContext.getRenderContext();

        String styleClass = getStyleClassName(htmlWriter
                .getComponentRenderContext());

        htmlWriter.startElement("TABLE");

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        htmlWriter.writeAttribute("v:nc", "true"); // C'est un naming container
        // !
        htmlWriter.writeAttribute("cellspacing", "0");
        htmlWriter.writeAttribute("cellpadding", "0");

        htmlWriter.startElement("TR");
        htmlWriter.startElement("TD");
        htmlWriter.writeAttribute("align", "center");
        htmlWriter.writeAttribute("class", styleClass + "_progressBar");

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
        htmlWriter.writeAttribute("align", "left");
        htmlWriter.writeAttribute("class", styleClass + "_taskName");

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

}