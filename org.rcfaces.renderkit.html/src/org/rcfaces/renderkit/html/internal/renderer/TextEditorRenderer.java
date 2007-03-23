/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.TextAreaComponent;
import org.rcfaces.core.component.TextEditorComponent;
import org.rcfaces.core.component.TextEntryComponent;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractInputRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TextEditorRenderer extends AbstractInputRenderer {
    private static final String REVISION = "$Revision$";

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {
        TextEditorComponent textEditorComponent = (TextEditorComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        htmlWriter.startElement("IFRAME");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        Object value = textEditorComponent.getValue();

        String valueMimeType = textEditorComponent
                .getValueMimeType(facesContext);

        if (valueMimeType == null) {
            if (value instanceof String) {
                valueMimeType = "text/plain";
            }
        }

        if (valueMimeType != null) {
            valueMimeType = valueMimeType.toLowerCase();
        }

        if (valueMimeType.equals("text/plain")) {
            formatTextPlain(htmlWriter, value);

        } else if (valueMimeType.equals("text/html")) {
            formatTextHtml(htmlWriter, value);

        } else {

        }

        htmlWriter.endElement("IFRAME");

        htmlWriter.enableJavaScript();
    }

    private void formatTextHtml(IHtmlWriter htmlWriter, Object value) {
        // TODO Auto-generated method stub

    }

    private void formatTextPlain(IHtmlWriter htmlWriter, Object value) {
        // TODO Auto-generated method stub

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

    protected void writeValueAttributes(IHtmlWriter htmlWriter)
            throws WriterException {
        TextEntryComponent textEntryComponent = (TextEntryComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        String text = convertValue(facesContext, textEntryComponent, null);
        if (text != null) {
            htmlWriter.writeValue(text);
        }
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        TextAreaComponent textAreaComponent = (TextAreaComponent) component;

        String newValue = componentData.getStringProperty("text");

        if (newValue == null) {
            // Toujours rien ... on essaye les données du form !
            newValue = componentData.getComponentParameter();
        }

        if (newValue != null
                && textAreaComponent.isValueLocked(context.getFacesContext()) == false) {
            textAreaComponent.setSubmittedValue(newValue);
        }
    }
}