/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.StyledMessageComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.ns.INamespaceConfiguration;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class StyledMessageRenderer extends AbstractCssRenderer {

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.STYLED_MESSAGE;
    }

    public void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement(IHtmlWriter.DIV);

        writeComponentAttributes(htmlWriter);
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.endElement(IHtmlWriter.DIV);

        super.encodeEnd(writer);

        htmlWriter.getJavaScriptEnableMode().enableOnInit();
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
            // Iterator iterator = MessageTools.listMessages(facesContext,
            // forValue, messageComponent);

            // On masque, on affichera une fois le message traité !
            messageComponent.setVisible(false);
        }

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        if (forValue != null) {
            htmlWriter.writeAttributeNS("for", forValue);
        }

        if (showIfMessage) {
            htmlWriter.writeAttributeNS("showIfMessage", true);
        }

        if (messageComponent.isSetFocusIfMessage(facesContext)) {
            htmlWriter.writeAttributeNS("setFocusIfMessage", true);
        }

        String infoStyleClass = messageComponent
                .getInfoStyleClass(facesContext);
        if (infoStyleClass != null) {
            htmlWriter.writeAttributeNS("infoStyleClass", infoStyleClass);
        }

        String warnStyleClass = messageComponent
                .getWarnStyleClass(facesContext);
        if (warnStyleClass != null) {
            htmlWriter.writeAttributeNS("warnStyleClass", warnStyleClass);
        }

        String errorStyleClass = messageComponent
                .getErrorStyleClass(facesContext);
        if (errorStyleClass != null) {
            htmlWriter.writeAttributeNS("errorStyleClass", errorStyleClass);
        }

        String fatalStyleClass = messageComponent
                .getFatalStyleClass(facesContext);
        if (fatalStyleClass != null) {
            htmlWriter.writeAttributeNS("fatalStyleClass", fatalStyleClass);
        }
    }

    /*
     * protected void encodeJavaScript(IJavaScriptWriter js) throws
     * WriterException { super.encodeJavaScript(js);
     * 
     * JavaScriptTools.writeFirstMessage(js); }
     */

    public void declare(INamespaceConfiguration nameSpaceProperties) {
        super.declare(nameSpaceProperties);

        nameSpaceProperties.addAttributes(null, new String[] { "for",
                "showIfMessage", "setFocusIfMessage", "infoStyleClass",
                "warnStyleClass", "errorStyleClass", "fatalStyleClass" });
    }
}
