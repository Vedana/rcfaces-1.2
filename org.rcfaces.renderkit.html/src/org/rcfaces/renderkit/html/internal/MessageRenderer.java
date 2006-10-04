/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.MessageComponent;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ContextTools;
import org.rcfaces.core.internal.tools.MessageTools;

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

        htmlWriter.startElement("DIV");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        String forValue = messageComponent.getFor();
        if (forValue != null) {
            htmlWriter.writeAttribute("v:for", forValue);
        }

        if (messageComponent.isShowSummary()) {
            htmlWriter.writeAttribute("v:showSummary", "true");
        }

        if (messageComponent.isShowDetail()) {
            htmlWriter.writeAttribute("v:showDetail", "true");
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

        String imageURL = messageComponent.getImageURL(facesContext);
        String infoImageURL = messageComponent.getInfoImageURL(facesContext);
        String warnImageURL = messageComponent.getWarnImageURL(facesContext);
        String errorImageURL = messageComponent.getErrorImageURL(facesContext);
        String fatalImageURL = messageComponent.getFatalImageURL(facesContext);
        if (imageURL != null || infoImageURL != null || warnImageURL != null
                || errorImageURL != null || fatalImageURL != null) {

            if (infoImageURL != null) {
                htmlWriter.writeAttribute("v:infoImageURL", infoImageURL);
            }

            if (warnImageURL != null) {
                htmlWriter.writeAttribute("v:warnImageURL", warnImageURL);
            }

            if (errorImageURL != null) {
                htmlWriter.writeAttribute("v:errorImageURL", errorImageURL);
            }

            if (fatalImageURL != null) {
                htmlWriter.writeAttribute("v:fatalImageURL", fatalImageURL);
            }

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

        String noMessageText = messageComponent.getNoMessageText(facesContext);
        if (noMessageText != null) {
            htmlWriter.startElement("LABEL");

            String noMessageStyleClass = getStyleClassName(componentContext,
                    messageComponent, "_noMessage");

            htmlWriter.writeAttribute("class", noMessageStyleClass);

            htmlWriter.writeText(noMessageText);
            htmlWriter.endElement("LABEL");
        }

        htmlWriter.endElement("DIV");

        htmlWriter.enableJavaScript();
    }

    protected void writeText(IHtmlWriter htmlWriter,
            MessageComponent messageComponent, String bundleVar, String text)
            throws WriterException {
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
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#encodeJavaScript(org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter)
     */
    protected void encodeJavaScript(IJavaScriptWriter js)
            throws WriterException {
        super.encodeJavaScript(js);

        IHtmlRenderContext htmlRenderContext = getHtmlRenderContext(js
                .getWriter());
        MessageComponent messageComponent = (MessageComponent) htmlRenderContext
                .getComponent();

        FacesContext facesContext = js.getFacesContext();

        String forValue = messageComponent.getFor();
        if (forValue == null) {
            return;
        }

        Iterator iterator = MessageTools.listMessages(facesContext, forValue,
                messageComponent);
        if (iterator.hasNext() == false) {
            return;
        }

        FacesMessage facesMessage = (FacesMessage) iterator.next();

        String bundleVar = messageComponent.getBundleVar(facesContext);

        writeMessage(js, facesMessage, forValue, false, bundleVar);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.MESSAGE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#decode(org.rcfaces.core.internal.renderkit.IRequestContext,
     *      javax.faces.component.UIComponent,
     *      org.rcfaces.core.internal.renderkit.IComponentData)
     */
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

    static IJavaScriptWriter writeMessage(IJavaScriptWriter js,
            FacesMessage facesMessage, String componentId, boolean isGlobal,
            String bundleVar) throws WriterException {

        IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) js
                .getComponentRenderContext().getRenderContext();

        IJavaScriptRenderContext javascriptRenderContext = htmlRenderContext
                .getJavaScriptRenderContext();

        FacesContext facesContext = js.getFacesContext();

        boolean declare[] = new boolean[1];
        String key = javascriptRenderContext.allocateFacesMessage(facesMessage,
                declare);

        int pred;
        if (declare[0]) {
            String summary = facesMessage.getSummary();
            if (summary != null) {
                if (bundleVar != null) {
                    summary = ContextTools.resolveText(facesContext, bundleVar,
                            summary);
                }

                summary = js.allocateString(summary);
            }

            String detail = facesMessage.getDetail();
            if (detail != null) {
                if (bundleVar != null) {
                    detail = ContextTools.resolveText(facesContext, bundleVar,
                            detail);
                }
                detail = js.allocateString(detail);
            }

            js.write("var ").write(key).write('=').writeConstructor(
                    "f_messageObject");

            pred = 0;

            Severity severity = facesMessage.getSeverity();
            // La severity ne peut etre null !
            js.writeInt(severity.getOrdinal());

            if (summary != null) {
                for (; pred > 0; pred--) {
                    js.write(',').writeNull();
                }

                js.write(',').write(summary);
            } else {
                pred++;
            }

            if (detail != null && detail.equals(summary) == false) {
                for (; pred > 0; pred--) {
                    js.write(',').writeNull();
                }
                js.write(',').write(detail);

            } else {
                pred++;
            }

            js.writeln(");");
        }

        js.writeMethodCall("_addMessageObject");

        pred = 0;
        if (componentId != null) {
            js.writeString(componentId);

        } else if (isGlobal) {
            js.write("true");

        } else {
            js.writeNull();
        }

        js.write(',').write(key).writeln(");");

        return js;
    }
}
