/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.RadioButtonComponent;
import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import org.rcfaces.core.component.capability.IRadioValueCapability;
import org.rcfaces.core.component.capability.ISelectedCapability;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ValuesTools;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class RadioButtonRenderer extends AbstractInputRenderer {
    private static final String REVISION = "$Revision$";

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

        RadioButtonComponent button = (RadioButtonComponent) componentRenderContext
                .getComponent();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        htmlWriter.startElement("SPAN");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);
        if (button.isDisabled(facesContext)) {
            htmlWriter.writeAttribute("DISABLED");
        }

        if (button.isRequired()) {
            htmlWriter.writeAttribute("v:required", "true");

            htmlWriter.enableJavaScript();
        }

        String buttonId = componentRenderContext.getComponentClientId();

        String className = getStyleClassName(componentRenderContext, button);

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
            RadioButtonComponent radioButtonComponent, String className,
            String componentId) throws WriterException {

        String inputId = componentId + "_input";

        htmlWriter.startElement("INPUT");
        htmlWriter.writeAttribute("id", inputId);
        writeInputAttributes(htmlWriter, inputId);
        writeChecked(htmlWriter, radioButtonComponent);

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();
        String svalue = null;
        Object value = radioButtonComponent.getRadioValue(facesContext);
        if (value != null) {
            svalue = ValuesTools.convertValueToString(value,
                    radioButtonComponent, facesContext);
        }

        if (svalue == null) {
            svalue = getValueAsText(facesContext, radioButtonComponent);
        }
        if (value != null) {
            htmlWriter.writeAttribute("value", svalue);
        }

        htmlWriter.writeAttribute("class", className + INPUT);

        if (htmlWriter.isJavaScriptEnabled() == false) {
            // Pour le FOCUS, pour retrouver le composant parent !
            htmlWriter.writeAttribute("v:container", componentId);
        }

        String accessKey = radioButtonComponent.getAccessKey(facesContext);
        if (accessKey != null) {
            htmlWriter.writeAttribute("accessKey", accessKey);
        }

        htmlWriter.endElement("INPUT");
    }

    protected void writeLabel(IHtmlWriter htmlWriter,
            RadioButtonComponent button, String className, String componentId)
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
    }

    protected void decode(IRequestContext context, UIComponent element,
            IComponentData componentData) {
        super.decode(context, element, componentData);

        RadioButtonComponent button = (RadioButtonComponent) element;

        parseSelectedProperty((IHtmlRequestContext) context, button,
                componentData);
    }

    protected void parseSelectedProperty(IHtmlRequestContext requestContext,
            RadioButtonComponent radioButton, IComponentData clientData) {

        String gb = radioButton.getGroupName();
        if (gb == null) {
            return;
        }

        FacesContext facesContext = requestContext.getFacesContext();

        gb = HtmlTools.computeGroupName(requestContext.getHtmlProcessContext(),
                radioButton, gb);

        Object submittedValue = null;
        String svalue = clientData.getParameter(gb);
        if (svalue != null) {
            submittedValue = getConvertedValue(facesContext, radioButton,
                    svalue);
        }

        boolean selected = false;

        Object radioValue = radioButton.getRadioValue(facesContext);
        if (radioValue != null) {
            selected = (radioValue.equals(submittedValue));

        } else {
            String text = clientData.getParameter(gb);
            if (text != null) {
                String value = getValueAsText(facesContext, radioButton);

                selected = (text.equals(value));
            }
        }

        radioButton.setSubmittedValue(submittedValue);

        if (radioButton.isSelected(facesContext) != selected) {
            radioButton.setSelected(selected);

            radioButton.queueEvent(new PropertyChangeEvent(radioButton,
                    Properties.SELECTED, Boolean.valueOf(selected == false),
                    Boolean.valueOf(selected)));
        }
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.RADIO_BUTTON;
    }

    protected String getInputType(UIComponent component) {
        return RADIO_TYPE;
    }

    protected String getInputName(
            IHtmlComponentRenderContext componentRenderContext, String id) {
        RadioButtonComponent radioButtonComponent = (RadioButtonComponent) componentRenderContext
                .getComponent();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        String groupName = radioButtonComponent.getGroupName(facesContext);

        groupName = HtmlTools.computeGroupName(componentRenderContext
                .getHtmlRenderContext().getHtmlProcessContext(),
                radioButtonComponent, groupName);

        return groupName;
    }

    protected String getValueAsText(FacesContext facesContext,
            RadioButtonComponent component) {
        Object value = component.getValue();

        String v = convertValue(facesContext, component, value);
        if (v != null) {
            return v;
        }

        return getUndefinedValue(facesContext, component);
    }

    private String getUndefinedValue(FacesContext facesContext,
            RadioButtonComponent component) {
        return component.getId();
    }

    protected IHtmlWriter writeChecked(IHtmlWriter writer,
            ISelectedCapability selectedCapability) throws WriterException {
        IRadioValueCapability radioValueCapability = (IRadioValueCapability) selectedCapability;

        Object radioValue = radioValueCapability.getRadioValue();
        if (radioValue == null) {
            return super.writeChecked(writer, selectedCapability);
        }

        ValueHolder valueHolder = (ValueHolder) radioValueCapability;

        Object currentValue = valueHolder.getValue();
        if (radioValue.equals(currentValue)) {
            writer.writeAttribute("CHECKED");
        }

        return writer;
    }
}