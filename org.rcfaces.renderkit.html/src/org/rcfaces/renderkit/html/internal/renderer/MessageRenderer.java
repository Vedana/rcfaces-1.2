/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.MessageComponent;
import org.rcfaces.core.component.capability.ISeverityImagesCapability;
import org.rcfaces.core.internal.component.ISeverityImageAccessors;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ContextTools;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IAccessibilityRoles;
import org.rcfaces.renderkit.html.internal.ICssWriter;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MessageRenderer extends AbstractCssRenderer {
    public static final String REVISION = "$Revision$";

    private static final String SUMMARY = "_summary";

    private static final String DETAIL = "_detail";

    // private static final String STYLE_CLASS_PROPERTY = "message.style.class";
    
    protected String getWAIRole() {
    	return IAccessibilityRoles.ALERT;
    }

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentContext.getFacesContext();

        MessageComponent messageComponent = (MessageComponent) componentContext
                .getComponent();

        boolean showIfMessage = messageComponent.isShowIfMessage(facesContext);
        String forValue = messageComponent.getFor();
        if (showIfMessage && forValue != null) {
            // Iterator iterator =
            // MessageTools.listMessages(facesContext,forValue,
            // messageComponent);

            // L'affiche ne se fera que coté client !
            messageComponent.setVisible(false);// iterator.hasNext());
        }

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement(IHtmlWriter.DIV);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        if (forValue != null) {
            htmlWriter.writeAttribute("v:for", forValue);
        }

        if (showIfMessage) {
            htmlWriter.writeAttribute("v:showIfMessage", true);
        }

        /*
        if (messageComponent.isSetFocusIfMessage(facesContext)) {
            htmlWriter.writeAttribute("v:setFocusIfMessage", true);
        }
        */

        if (messageComponent.isShowSummary()) {
            htmlWriter.writeAttribute("v:showSummary", true);
        }

        if (messageComponent.isShowDetail()) {
            htmlWriter.writeAttribute("v:showDetail", true);
        }

        if (messageComponent.isShowActiveComponentMessage(facesContext)) {
            htmlWriter.writeAttribute("v:showActiveComponentMessage", true);
        }

        ISeverityImageAccessors accessors = (ISeverityImageAccessors) messageComponent
                .getImageAccessors(facesContext);

        if (writeSeverityImages(htmlWriter, accessors)) {
            IContentAccessor imageAccessor = accessors.getImageAccessor();

            String imageURL = null;
            if (imageAccessor != null) {
                imageURL = imageAccessor.resolveURL(facesContext, null, null);
            }

            htmlWriter.startElement(IHtmlWriter.IMG);

            htmlWriter.writeClass(getImageClassName(htmlWriter));

            if (imageURL != null) {
                htmlWriter.writeSrc(imageURL);
            } else {
                htmlWriter.writeStyle().writeDisplay(ICssWriter.NONE);
            }

            int imageWidth = messageComponent.getImageWidth(facesContext);
            if (imageWidth > 0) {
                htmlWriter.writeWidth(imageWidth);
            }

            int imageHeight = messageComponent.getImageHeight(facesContext);
            if (imageHeight > 0) {
                htmlWriter.writeHeight(imageHeight);
            }

            htmlWriter.endElement(IHtmlWriter.IMG);
        }

        String noMessageText = messageComponent.getText(facesContext);
        if (noMessageText != null) {
            noMessageText = ParamUtils.formatMessage(messageComponent,
                    noMessageText);

            htmlWriter.startElement(IHtmlWriter.LABEL);

            htmlWriter.writeClass(getNoMessageClassName(htmlWriter));

            htmlWriter.writeText(noMessageText);
            htmlWriter.endElement(IHtmlWriter.LABEL);
        }

        htmlWriter.endElement(IHtmlWriter.DIV);

        htmlWriter.getJavaScriptEnableMode().enableOnMessage(); // Pour les messages ?
    }

    protected String getNoMessageClassName(IHtmlWriter htmlWriter) {
        return getMainStyleClassName() + "_noMessage";
    }

    protected String getImageClassName(IHtmlWriter htmlWriter) {
        return getMainStyleClassName() + "_image";
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

    /*
     * protected void encodeJavaScript(IJavaScriptWriter js) throws
     * WriterException { super.encodeJavaScript(js);
     * 
     * JavaScriptTools.writeFirstMessage(js); }
     */

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
}
