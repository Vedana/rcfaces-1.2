/**
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.TextAreaComponent;
import org.rcfaces.core.component.TextEntryComponent;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
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
public class TextAreaRenderer extends AbstractInputRenderer {
    private static final String REVISION = "$Revision$";

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        TextAreaComponent textAreaComponent = (TextAreaComponent) componentRenderContext
                .getComponent();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        htmlWriter.startElement(IHtmlWriter.TEXTAREA);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);
        writeInputAttributes(htmlWriter);
        writeTextDirection(htmlWriter, textAreaComponent);
        writeTextAreaAttributes(htmlWriter);

        String text = textAreaComponent.getText(facesContext);
        if (text != null) {
            htmlWriter.writeText(text);
        }

        htmlWriter.endElement(IHtmlWriter.TEXTAREA);

        htmlWriter.addSubFocusableComponent(htmlWriter
                .getComponentRenderContext().getComponentClientId());

        if (textAreaComponent.isRequired()) {
            // Il nous faut le javascript, car c'est un traitement javascript !
            htmlWriter.getJavaScriptEnableMode().enableOnSubmit();
        }

        String value = null; // (String) componentRenderContext.getAttribute(
        // VALIDATOR_INTERNAL_VALUE_ATTRIBUTE);

        if (value != null
                && value.equals(text) == false
                && htmlWriter.getJavaScriptEnableMode().isOnInitEnabled() == false) {

            htmlWriter.startElement(IHtmlWriter.INPUT);
            htmlWriter.writeType(IHtmlWriter.HIDDEN_INPUT_TYPE);

            String name = componentRenderContext.getComponentClientId()
                    + "::value";
            htmlWriter.writeName(name);

            htmlWriter.writeValue(value);

            htmlWriter.endElement(IHtmlWriter.INPUT);
        }
    }

    protected void writeTextAreaAttributes(IHtmlWriter htmlWriter)
            throws WriterException {
        TextAreaComponent textAreaComponent = (TextAreaComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        int col = textAreaComponent.getColumnNumber(facesContext);
        if (col > 0) {
            htmlWriter.writeCols(col);
        }
        int row = textAreaComponent.getRowNumber(facesContext);
        if (row > 0) {
            htmlWriter.writeRows(row);
        }

        int maxTextLength = textAreaComponent.getMaxTextLength(facesContext);
        if (maxTextLength > 0) {
            htmlWriter.writeAttribute("v:maxTextLength", maxTextLength);

            // htmlWriter.enableJavaScript(); Bof bof ... on peut le faire à la
            // génération ?
        }

    }

    protected boolean useHtmlAccessKeyAttribute() {
        return true;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TEXT_AREA;
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

        String text = convertValue(facesContext, textEntryComponent);
        if (text != null) {
            htmlWriter.writeValue(text);
        }
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        FacesContext facesContext = context.getFacesContext();

        TextAreaComponent textAreaComponent = (TextAreaComponent) component;

        String newValue = componentData.getStringProperty("text");

        if (newValue == null) {

            // Le TextArea est disabled ou en lazy-init inchangé
            String name = textAreaComponent.getClientId(facesContext)
                    + "::value";
            newValue = componentData.getParameter(name);

            if (newValue == null) {
                // Toujours rien ... on essaye les données du form !
                newValue = componentData.getComponentParameter();
            }
        }

        if (newValue != null
                && textAreaComponent.isValueLocked(facesContext) == false) {
            textAreaComponent.setSubmittedExternalValue(newValue);
        }
    }
}