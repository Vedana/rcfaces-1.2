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
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.ParamUtils;
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

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {
        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        CheckButtonComponent button = (CheckButtonComponent) componentRenderContext
                .getComponent();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        htmlWriter.startElement(IHtmlWriter.LABEL);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        if (button.isDisabled(facesContext)) {
            htmlWriter.writeAttribute("DISABLED");
        }

        if (button instanceof IRequiredCapability) {
            IRequiredCapability requiredCapability = (IRequiredCapability) button;

            if (requiredCapability.isRequired()) {
                htmlWriter.writeAttribute("v:required", true);

                htmlWriter.enableJavaScript();
            }
        }

        String buttonId = componentRenderContext.getComponentClientId();

        String className = getMainStyleClassName();

        int horizontalTextPosition = button.getTextPosition(facesContext);
        if (horizontalTextPosition == 0) {
            horizontalTextPosition = IHorizontalTextPositionCapability.DEFAULT_POSITION;
        }

        if (horizontalTextPosition == IHorizontalTextPositionCapability.LEFT_POSITION) {
            writeLabel(htmlWriter, button, className, buttonId);

            writeInput(htmlWriter, button, className, buttonId);

        } else {
            writeInput(htmlWriter, button, className, buttonId);

            writeLabel(htmlWriter, button, className, buttonId);
        }

        htmlWriter.endElement(IHtmlWriter.LABEL);
    }

    protected void writeInput(IHtmlWriter htmlWriter,
            CheckButtonComponent button, String className, String componentId)
            throws WriterException {

        String inputId = componentId + "_input";

        htmlWriter.startElement(IHtmlWriter.INPUT);
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
        writeAlternateText(htmlWriter, button);

        htmlWriter.endElement(IHtmlWriter.INPUT);
    }

    protected IHtmlWriter writeLabel(IHtmlWriter htmlWriter,
            CheckButtonComponent button, String className, String componentId)
            throws WriterException {
        htmlWriter.startElement(IHtmlWriter.SPAN);
        String claz = className + TEXT;

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();
        htmlWriter.writeAttribute("class", claz);
        writeTextDirection(htmlWriter, button);

        String text = button.getText(facesContext);
        if (text != null) {
            text = ParamUtils.formatMessage(button, text);
        }

        HtmlTools.writeSpanAccessKey(htmlWriter, button, text, true);

        htmlWriter.endElement(IHtmlWriter.SPAN);

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
        return IHtmlWriter.CHECKBOX_INPUT_TYPE;
    }

    protected void decode(IRequestContext context, UIComponent element,
            IComponentData componentData) {
        super.decode(context, element, componentData);

        CheckButtonComponent button = (CheckButtonComponent) element;

        parseSelectedProperty(context.getFacesContext(), button, componentData);
    }

    protected void parseSelectedProperty(FacesContext facesContext,
            CheckButtonComponent button, IComponentData clientData) {

        boolean selected = false;

        String selectedProperty = clientData.getStringProperty("selected");
        if (selectedProperty != null) {
            selected = (selectedProperty.length() > 0);

        } else {
            String values[] = clientData.getComponentParameters();

            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    selected = (values[i].length() > 0);

                    break;
                }
            }
        }

        if (button.isSelected(facesContext) != selected) {
            button.setValue(Boolean.valueOf(selected));

            button.queueEvent(new PropertyChangeEvent(button,
                    Properties.SELECTED, Boolean.valueOf(selected == false),
                    Boolean.valueOf(selected)));
        }

    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.CHECK_BUTTON;
    }
}