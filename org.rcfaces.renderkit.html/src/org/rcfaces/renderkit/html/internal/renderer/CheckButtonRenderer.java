/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.CheckButtonComponent;
import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractInputRenderer;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CheckButtonRenderer extends AbstractInputRenderer {
    private static final String REVISION = "$Revision$";

    protected static final String DEFAULT_VALUE = "CHECKED";

    public static final String INPUT = "_input";

    public static final String TEXT = "_text";

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        encodeComponent(htmlWriter);

        super.encodeEnd(writer);
    }

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {
        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        CheckButtonComponent button = (CheckButtonComponent) componentRenderContext
                .getComponent();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        htmlWriter.startElement("SPAN");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);
        if (button.isDisabled(facesContext)) {
            htmlWriter.writeAttribute("DISABLED");
        }

        if (button instanceof IRequiredCapability) {
            IRequiredCapability requiredCapability = (IRequiredCapability) button;

            if (requiredCapability.isRequired()) {
                htmlWriter.writeAttribute("v:required", "true");

                htmlWriter.enableJavaScript();
            }
        }

        String buttonId = componentRenderContext.getComponentClientId();

        String className = getMainStyleClassName();

        int horizontalTextPosition = button.getTextPosition(facesContext);
        if (horizontalTextPosition == IHorizontalTextPositionCapability.LEFT_POSITION) {
            writeLabel(htmlWriter, button, className, buttonId);

            writeInput(htmlWriter, button, className, buttonId);

        } else {
            writeInput(htmlWriter, button, className, buttonId);

            writeLabel(htmlWriter, button, className, buttonId);
        }

        htmlWriter.endElement("SPAN");
    }

    protected void writeInput(IHtmlWriter htmlWriter,
            CheckButtonComponent button, String className, String componentId)
            throws WriterException {

        String inputId = componentId + "_input";

        htmlWriter.startElement("INPUT");
        htmlWriter.writeAttribute("id", inputId);
        writeInputAttributes(htmlWriter, inputId);
        writeChecked(htmlWriter, button);

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();
        String value = getValue(facesContext, button);
        if (value != null) {
            htmlWriter.writeAttribute("value", value);
        }

        htmlWriter.writeAttribute("class", className + INPUT);

        if (htmlWriter.isJavaScriptEnabled() == false) {
            // Pour le FOCUS, pour retrouver le composant parent !
            htmlWriter.writeAttribute("v:container", componentId);
        }

        String accessKey = button.getAccessKey(facesContext);
        if (accessKey != null) {
            htmlWriter.writeAttribute("accessKey", accessKey);
        }

        htmlWriter.endElement("INPUT");
    }

    protected IHtmlWriter writeLabel(IHtmlWriter htmlWriter,
            CheckButtonComponent button, String className, String componentId)
            throws WriterException {
        htmlWriter.startElement("LABEL");
        String claz = className + TEXT;

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();
        if (button.isDisabled(facesContext)) {
            claz += "_disabled";
        }
        htmlWriter.writeAttribute("class", claz);

        String inputId = componentId + "_input";
        htmlWriter.writeAttribute("for", inputId);

        String text = button.getText(facesContext);
        HtmlTools.writeSpanAccessKey(htmlWriter, button, text, true);

        htmlWriter.endElement("LABEL");

        return htmlWriter;
    }

    protected String getValue(FacesContext facesContext,
            CheckButtonComponent component) {
        Object value = component.getValue();

        String svalue = convertValue(facesContext, component, value);

        if (svalue != null) {
            return svalue;
        }

        return DEFAULT_VALUE;
    }

    protected String getInputType(UIComponent component) {
        return CHECKBOX_TYPE;
    }

    protected void decode(IRequestContext context, UIComponent element,
            IComponentData componentData) {
        super.decode(context, element, componentData);

        CheckButtonComponent button = (CheckButtonComponent) element;

        parseSelectedProperty(context.getFacesContext(), button, componentData);
    }

    protected void parseSelectedProperty(FacesContext facesContext,
            CheckButtonComponent button, IComponentData clientData) {
        String values[] = clientData.getComponentParameters();

        boolean selected = false;
        if (values != null && values.length > 0) {
            String componentValue = getValue(facesContext, button);
            if (componentValue != null) {
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];

                    if (componentValue.equals(value) == false) {
                        continue;
                    }

                    selected = true;

                    break;
                }
            }
        }

        if (button.isSelected(facesContext) != selected) {
            button.setSelected(selected);

            button.queueEvent(new PropertyChangeEvent(button,
                    Properties.SELECTED, Boolean.valueOf(selected == false),
                    Boolean.valueOf(selected)));
        }

    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.CHECK_BUTTON;
    }
}