/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.TextComponent;
import org.rcfaces.core.component.capability.IErrorTextCapability;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.util.AudioDescriptionTools;
import org.rcfaces.renderkit.html.internal.util.TextTypeTools;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TextRenderer extends AbstractCssRenderer {
    private static final String DEFAULT_TEXT_ELEMENT = IHtmlWriter.LABEL;

    private static final String ERROR_TEXT_ATTRIBUTE = "core.ERROR_TEXT";

    private static final String WARNING_14x14 = "images/pe_warn_14x14.png";

    protected boolean useHtmlAccessKeyAttribute() {
        return true;
    }

    @Override
    protected void encodeBegin(IComponentWriter writer) throws WriterException {

        UIComponent component = writer.getComponentRenderContext()
                .getComponent();
        if (component instanceof IErrorTextCapability) {
            IErrorTextCapability errorTextCapability = (IErrorTextCapability) component;

            String errorText = errorTextCapability.getErrorText();

            if (errorText != null && errorText.length() > 0) {
                writer.getComponentRenderContext().setAttribute(
                        ERROR_TEXT_ATTRIBUTE, errorText);
            }
        }

        super.encodeBegin(writer);
    }

    @Override
    protected ICssStyleClasses createStyleClasses(IHtmlWriter htmlWriter) {
        ICssStyleClasses cssStyleClasses = super.createStyleClasses(htmlWriter);

        String error = (String) htmlWriter.getComponentRenderContext()
                .getAttribute(ERROR_TEXT_ATTRIBUTE);
        if (error != null) {
            String errorTextStyleClass = getErrorTextStyleClass(htmlWriter);
            cssStyleClasses.addStyleClass(errorTextStyleClass);
        }

        return cssStyleClasses;
    }

    protected String getErrorTextStyleClass(IHtmlWriter htmlWriter) {
        return "f_errorText";
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        // En dernier (encodeEnd) car les clientDatas / converter ne sont peut
        // être pas
        // encore positionnés !
        TextComponent textComponent = (TextComponent) componentRenderContext
                .getComponent();

        String element = null;
        String ac = textComponent.getFor();
        if (element == null) {
            element = getTextElement(htmlWriter);
        }

        if (element == null) {
            element = getDefaultTextElement(htmlWriter);
        }

        htmlWriter.startElement(element);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);
        writeTextDirection(htmlWriter, textComponent);

        writeAriaLevelAttribute(htmlWriter, textComponent);

        if (ac != null) {
            // On peut pas calculer la véritable ID car le composant est peut
            // etre pas encore présent.
            // On considere que nous sommes dans le même namingContainer que le
            // for ! (si un namespace separator n'est pas spécifié dans l'id)

            IRenderContext renderContext = componentRenderContext
                    .getRenderContext();

            String forId = renderContext.computeBrotherComponentClientId(
                    textComponent, ac);

            if (forId != null) {
                String inputForId = HtmlTools.computeSubInputComponentId(
                        htmlWriter, forId);
                if (inputForId != null) {
                    forId = inputForId;
                }

                htmlWriter.writeFor(forId);

                htmlWriter.getJavaScriptEnableMode().enableOnMessage();
            }
        }
        writeTextAttributes(htmlWriter, textComponent);

        if (writeText(htmlWriter, textComponent)) {
            // Un accessKey est postionné !

            htmlWriter.getJavaScriptEnableMode().enableOnAccessKey();
        }

        AudioDescriptionTools.writeAudioDescription(htmlWriter);

        htmlWriter.endElement(element);

        super.encodeEnd(htmlWriter);
    }

    protected void writeTextAttributes(IHtmlWriter htmlWriter,
            TextComponent element) throws WriterException {

        String error = (String) htmlWriter.getComponentRenderContext()
                .getAttribute(ERROR_TEXT_ATTRIBUTE);
        if (error != null) {
            htmlWriter.writeAttribute("v:errorText", true);
        }

    }

    protected void writeAriaLevelAttribute(IHtmlWriter htmlWriter,
            TextComponent textComponent) throws WriterException {

        int ariaLevel = textComponent.getAriaLevel(htmlWriter
                .getComponentRenderContext().getFacesContext());
        if (ariaLevel > 0) {
            htmlWriter.writeAriaLevel(ariaLevel);
        }

    }

    protected String getDefaultTextElement(IHtmlWriter htmlWriter) {
        return DEFAULT_TEXT_ELEMENT;
    }

    protected String getTextElement(IHtmlWriter htmlWriter) {

        return TextTypeTools.getType(htmlWriter);
    }

    protected boolean writeText(IHtmlWriter htmlWriter,
            TextComponent textComponent) throws WriterException {

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        String errorText = (String) htmlWriter.getComponentRenderContext()
                .getAttribute(ERROR_TEXT_ATTRIBUTE);
        if (errorText != null) {
            errorText = ParamUtils.formatMessage(htmlWriter
                    .getComponentRenderContext().getComponent(), errorText);

            writeTextError(htmlWriter, errorText);

            return true;
        }

        String text = textComponent.getText(facesContext);
        if (text == null || text.trim().length() < 1) {
            return false;
        }

        text = ParamUtils.formatMessage(textComponent, text);

        designerEditableZone(htmlWriter, "text");

        return HtmlTools.writeSpanAccessKey(htmlWriter, textComponent, text,
                true);
    }

    protected void writeTextError(IHtmlWriter htmlWriter, String errorText)
            throws WriterException {

        htmlWriter.writeText(errorText);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TEXT;
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        FacesContext facesContext = context.getFacesContext();

        TextComponent textComponent = (TextComponent) component;

        String newValue = componentData.getStringProperty("text");

        if (newValue != null) {
            if (textComponent.getConverter() == null) {
                // On considere le TEXT
                String old = textComponent.getText();

                if (old != newValue
                        && (old == null || old.equals(newValue) == false)) {
                    textComponent.setText(newValue);

                    component.queueEvent(new PropertyChangeEvent(component,
                            Properties.TEXT, old, newValue));
                }

            } else {
                // On considere la VALUE
                Object old = textComponent.getValue();

                Object value = ValuesTools.convertStringToValue(facesContext,
                        component, newValue, true);

                if (old != value && (old == null || old.equals(value) == false)) {
                    textComponent.setValue(value);

                    component.queueEvent(new PropertyChangeEvent(component,
                            Properties.VALUE, old, value));
                }
            }
        }
    }
}