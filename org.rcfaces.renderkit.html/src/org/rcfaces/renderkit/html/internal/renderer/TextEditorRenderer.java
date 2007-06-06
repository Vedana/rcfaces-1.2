/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.TextEditorComponent;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractInputRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptComponent;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.w3c.dom.Document;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TextEditorRenderer extends AbstractInputRenderer {
    private static final String REVISION = "$Revision$";

    private static final IJavaScriptComponent TEXTEDITOR_JAVASCRIPT_COMPONENT = new IJavaScriptComponent() {
        private static final String REVISION = "$Revision$";

        public void initializeJavaScript(IJavaScriptWriter javaScriptWriter)
                throws WriterException {
            javaScriptWriter.getJavaScriptRenderContext()
                    .initializeJavaScriptDocument(javaScriptWriter);
        }

        public void initializeJavaScriptComponent(
                IJavaScriptWriter javaScriptWriter) {
        }

        public void releaseJavaScript(IJavaScriptWriter javaScriptWriter) {
        }

        public void initializePendingComponents(IJavaScriptWriter writer) {
        }
    };

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {

        IHtmlRenderContext htmlRenderContext = htmlWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext();

        // Il faut que la JAVASCRIPT f_textEditor soit dispo ICI ! (a cause du
        // onload)

        htmlRenderContext.removeJavaScriptWriter(htmlWriter);

        IJavaScriptRenderContext javascriptRenderContext = htmlRenderContext
                .getJavaScriptRenderContext();

        // Il faut calculer les dependances
        javascriptRenderContext.computeRequires(htmlWriter, this);

        IJavaScriptWriter js = htmlRenderContext.getJavaScriptWriter(
                htmlWriter, TEXTEDITOR_JAVASCRIPT_COMPONENT);

        initializePendingComponents(js);

        js.end();

        TextEditorComponent textEditorComponent = (TextEditorComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        htmlWriter.startElement(IHtmlWriter.IFRAME);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        htmlWriter.writeAttribute("frameborder", 0);
        htmlWriter.writeAttribute("marginwidth", 0);
        htmlWriter.writeAttribute("marginheight", 0);
        htmlWriter.writeAttribute("hspace", 0);
        htmlWriter.writeAttribute("vspace", 0);
        htmlWriter.writeAttribute("name", htmlWriter
                .getComponentRenderContext().getComponentClientId());

        String onLoad = "f_textEditor."
                + htmlWriter.getHtmlComponentRenderContext().getRenderContext()
                        .getScriptRenderContext().convertSymbol("f_textEditor",
                                "_OnLoad") + "(this)";

        htmlWriter.writeAttribute("onload", onLoad);

        Object value = textEditorComponent.getValue();

        String valueMimeType = textEditorComponent
                .getValueMimeType(facesContext);

        if (valueMimeType == null) {
            if (value instanceof String) {
                valueMimeType = "text/plain";

            } else if (value instanceof Document) {
                valueMimeType = "text/html";
            }
        }

        if (valueMimeType != null) {
            valueMimeType = valueMimeType.toLowerCase();
        }

        if ("text/plain".equals(valueMimeType)) {
            formatTextPlain(htmlWriter, value);

        } else if ("text/html".equals(valueMimeType)) {
            formatTextHtml(htmlWriter, value);

        } else {

        }

        if (valueMimeType != null) {
            htmlWriter.writeAttribute("v:mimeType", valueMimeType);
        }

        htmlWriter.endElement(IHtmlWriter.IFRAME);

        htmlWriter.enableJavaScript();
    }

    private void formatTextHtml(IHtmlWriter htmlWriter, Object value) {
        // TODO Auto-generated method stub

    }

    private void formatTextPlain(IHtmlWriter htmlWriter, Object value)
            throws WriterException {
        htmlWriter.writeAttribute("v:text", String.valueOf(value));
    }

    protected boolean useHtmlAccessKeyAttribute() {
        return true;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TEXT_EDITOR;
    }

    protected String getInputType(UIComponent component) {
        return null; //
    }

    protected boolean hasComponenDecoratorSupport() {
        return true;
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        TextEditorComponent textAreaComponent = (TextEditorComponent) component;

        String newValue = componentData.getStringProperty("text");

        if (newValue != null
                && textAreaComponent.isValueLocked(context.getFacesContext()) == false) {
            textAreaComponent.setSubmittedExternalValue(newValue);
        }
    }
}