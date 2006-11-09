/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.Iterator;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.StyledMessageComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.MessageTools;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.util.JavaScriptTools;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StyledMessageRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.STYLED_MESSAGE;
    }

    public void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("DIV");

        writeComponentAttributes(htmlWriter);
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.endElement("DIV");

        super.encodeEnd(writer);

        htmlWriter.enableJavaScript();
    }

    protected void writeComponentAttributes(IHtmlWriter htmlWriter)
            throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();

        StyledMessageComponent messageComponent = (StyledMessageComponent) componentRenderContext
                .getComponent();

        boolean showIfMessage = messageComponent.isShowIfMessage(facesContext);
        String forValue = messageComponent.getFor(facesContext);
        if (showIfMessage && forValue != null) {
            Iterator iterator = MessageTools.listMessages(facesContext,
                    forValue, messageComponent);

            messageComponent.setVisible(Boolean.valueOf(iterator.hasNext()));
        }

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        if (forValue != null) {
            htmlWriter.writeAttribute("v:for", forValue);
        }
        if (showIfMessage) {
            htmlWriter.writeAttribute("v:showIfMessage", "true");
        }

        if (messageComponent.isSetFocusIfMessage(facesContext)) {
            htmlWriter.writeAttribute("v:setFocusIfMessage", "true");
        }

        String infoStyleClass = messageComponent
                .getInfoStyleClass(facesContext);
        if (infoStyleClass != null) {
            htmlWriter.writeAttribute("v:infoStyleClass", infoStyleClass);
        }

        String warnStyleClass = messageComponent
                .getWarnStyleClass(facesContext);
        if (warnStyleClass != null) {
            htmlWriter.writeAttribute("v:warnStyleClass", warnStyleClass);
        }

        String errorStyleClass = messageComponent
                .getErrorStyleClass(facesContext);
        if (errorStyleClass != null) {
            htmlWriter.writeAttribute("v:errorStyleClass", errorStyleClass);
        }

        String fatalStyleClass = messageComponent
                .getFatalStyleClass(facesContext);
        if (fatalStyleClass != null) {
            htmlWriter.writeAttribute("v:fatalStyleClass", fatalStyleClass);
        }
    }

    protected void encodeJavaScript(IJavaScriptWriter js)
            throws WriterException {
        super.encodeJavaScript(js);

        JavaScriptTools.writeFirstMessage(js);
    }
}
