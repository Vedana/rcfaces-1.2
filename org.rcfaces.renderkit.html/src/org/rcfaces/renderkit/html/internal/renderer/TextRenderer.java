/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.TextComponent;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.util.TextTypeTools;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TextRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    private static final String DEFAULT_TEXT_ELEMENT = IHtmlWriter.LABEL;

    protected boolean useHtmlAccessKeyAttribute() {
        return true;
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        // En dernier car les clientDatas / converter ne sont peut �tre pas
        // encore positionn�s !
        TextComponent textComponent = (TextComponent) writer
                .getComponentRenderContext().getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        String element = null;
        String ac = textComponent.getFor();
        if (ac == null) {
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

        if (ac != null) {
            // On peut pas calculer la véritable ID car le composant est peut
            // etre pas encore présent.
            // On considere que nous sommes dans le même namingContainer que le
            // for ! (si un namespace separator n'est pas spécifié dans l'id)

            IComponentRenderContext componentRenderContext = htmlWriter
                    .getComponentRenderContext();

            IRenderContext renderContext = componentRenderContext
                    .getRenderContext();

            String forId = renderContext.computeBrotherComponentClientId(
                    textComponent, ac);

            if (forId != null) {
                htmlWriter.writeFor(forId);
            }
        }

        if (writeText(htmlWriter, textComponent)) {
            // Un accessKey est postionné !

            htmlWriter.enableJavaScript();
        }

        htmlWriter.endElement(element);

        super.encodeEnd(htmlWriter);
    }

    protected String getDefaultTextElement(IHtmlWriter htmlWriter) {
        return DEFAULT_TEXT_ELEMENT;
    }

    protected String getTextElement(IHtmlWriter htmlWriter) {

        return TextTypeTools.getType(htmlWriter);
    }

    protected boolean writeText(IHtmlWriter htmlWriter,
            TextComponent textComponent) throws WriterException {
        String text = textComponent.getText(htmlWriter
                .getComponentRenderContext().getFacesContext());
        if (text == null || text.trim().length() < 1) {
            return false;
        }

        text = ParamUtils.formatMessage(textComponent, text);

        return HtmlTools.writeSpanAccessKey(htmlWriter, textComponent, text,
                true);
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
            String old = textComponent.getText(facesContext);

            if (newValue.equals(old) == false) {
                textComponent.setText(newValue);

                component.queueEvent(new PropertyChangeEvent(component,
                        Properties.TEXT, old, newValue));
            }
        }
    }
}