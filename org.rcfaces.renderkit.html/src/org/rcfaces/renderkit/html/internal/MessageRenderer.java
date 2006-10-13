/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.MessageComponent;
import org.rcfaces.core.component.capability.ISeverityImagesCapability;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ContextTools;
import org.rcfaces.renderkit.html.internal.util.JavaScriptTools;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MessageRenderer extends AbstractCssRenderer {
    public static final String REVISION = "$Revision$";

    private static final String SUMMARY = "_summary";

    private static final String DETAIL = "_detail";

    private static final String STYLE_CLASS_PROPERTY = "message.style.class";

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentContext.getFacesContext();

        MessageComponent messageComponent = (MessageComponent) componentContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("SPAN");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        String forValue = messageComponent.getFor();
        if (forValue != null) {
            htmlWriter.writeAttribute("v:for", forValue);
        }

        if (messageComponent.isSetFocusIfMessage(facesContext)) {
            htmlWriter.writeAttribute("v:setFocusIfMessage", "true");
        }

        if (messageComponent.isShowSummary()) {
            htmlWriter.writeAttribute("v:showSummary", "true");
        }

        if (messageComponent.isShowDetail()) {
            htmlWriter.writeAttribute("v:showDetail", "true");
        }

        writeSeverityStyleClasses(htmlWriter, messageComponent);

        if (writeSeverityImages(htmlWriter, messageComponent)) {
            String imageURL = messageComponent.getImageURL(facesContext);

            htmlWriter.startElement("IMG");

            String imageStyleClass = getStyleClassName(componentContext,
                    messageComponent, "_image");

            htmlWriter.writeAttribute("class", imageStyleClass);

            if (imageURL != null) {
                htmlWriter.writeAttribute("src", imageURL);
            } else {
                htmlWriter.writeAttribute("style", "display:none");
            }

            int imageWidth = messageComponent.getImageWidth(facesContext);
            if (imageWidth > 0) {
                htmlWriter.writeAttribute("width", imageWidth);
            }

            int imageHeight = messageComponent.getImageHeight(facesContext);
            if (imageHeight > 0) {
                htmlWriter.writeAttribute("height", imageHeight);
            }

            htmlWriter.endElement("IMG");
        }

        String noMessageText = messageComponent.getText(facesContext);
        if (noMessageText != null) {
            htmlWriter.startElement("LABEL");

            String noMessageStyleClass = getStyleClassName(componentContext,
                    messageComponent, "_noMessage");

            htmlWriter.writeAttribute("class", noMessageStyleClass);

            htmlWriter.writeText(noMessageText);
            htmlWriter.endElement("LABEL");
        }

        htmlWriter.endElement("SPAN");

        htmlWriter.enableJavaScript();
    }

    protected void writeText(IHtmlWriter htmlWriter,
            ISeverityImagesCapability messageComponent, String bundleVar,
            String text) throws WriterException {
        if (bundleVar != null) {
            FacesContext facesContext = htmlWriter.getComponentRenderContext()
                    .getFacesContext();

            text = ContextTools.resolveText(facesContext, bundleVar, text);
        }

        if (text == null || text.trim().length() < 1) {
            return;
        }

        htmlWriter.writeText(text);
    }

    protected void encodeJavaScript(IJavaScriptWriter js)
            throws WriterException {
        super.encodeJavaScript(js);

        JavaScriptTools.writeFirstMessage(js);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.MESSAGE;
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {

        MessageComponent messageComponent = (MessageComponent) component;

        Boolean showDetail = componentData.getBooleanProperty("showDetail");
        if (showDetail != null) {
            messageComponent.setShowDetail(showDetail.booleanValue());
        }

        Boolean showSummary = componentData.getBooleanProperty("showSummary");
        if (showSummary != null) {
            messageComponent.setShowDetail(showSummary.booleanValue());
        }
        /*
         * String forValue=componentData.getProperty("for"); if (forValue!=null) {
         * messageComponent.setFor(forValue); }
         */

        super.decode(context, component, componentData);
    }

    public String getStyleClassName(
            IComponentRenderContext componentRenderContext,
            UIComponent component, String suffix) {
        String styleClass = (String) componentRenderContext
                .getAttribute(STYLE_CLASS_PROPERTY);
        if (styleClass != null) {
            return styleClass;
        }

        return super.getStyleClassName(componentRenderContext, component,
                suffix);
    }
}
