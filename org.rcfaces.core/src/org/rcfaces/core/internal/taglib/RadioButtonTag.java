package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.RadioButtonComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;

public class RadioButtonTag extends AbstractInputTag implements Tag {

    private static final Log LOG = LogFactory.getLog(RadioButtonTag.class);

    private String text;

    private String textDirection;

    private String textPosition;

    private String selectionListeners;

    private String readOnly;

    private String alternateText;

    private String selected;

    private String radioValue;

    private String groupName;

    private String required;

    public String getComponentType() {
        return RadioButtonComponent.COMPONENT_TYPE;
    }

    public final String getText() {
        return text;
    }

    public final void setText(String text) {
        this.text = text;
    }

    public final String getTextDirection() {
        return textDirection;
    }

    public final void setTextDirection(String textDirection) {
        this.textDirection = textDirection;
    }

    public final String getTextPosition() {
        return textPosition;
    }

    public final void setTextPosition(String textPosition) {
        this.textPosition = textPosition;
    }

    public final String getSelectionListener() {
        return selectionListeners;
    }

    public final void setSelectionListener(String selectionListeners) {
        this.selectionListeners = selectionListeners;
    }

    public final String getReadOnly() {
        return readOnly;
    }

    public final void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }

    public final String getAlternateText() {
        return alternateText;
    }

    public final void setAlternateText(String alternateText) {
        this.alternateText = alternateText;
    }

    public final String getSelected() {
        return selected;
    }

    public final void setSelected(String selected) {
        this.selected = selected;
    }

    public final String getRadioValue() {
        return radioValue;
    }

    public final void setRadioValue(String radioValue) {
        this.radioValue = radioValue;
    }

    public final String getGroupName() {
        return groupName;
    }

    public final void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public final String getRequired() {
        return required;
    }

    public final void setRequired(String required) {
        this.required = required;
    }

    protected void setProperties(UIComponent uiComponent) {
        if (LOG.isDebugEnabled()) {
            if (RadioButtonComponent.COMPONENT_TYPE == getComponentType()) {
                LOG.debug("Component id='" + getId() + "' type='"
                        + getComponentType() + "'.");
            }
            LOG.debug("  text='" + text + "'");
            LOG.debug("  textDirection='" + textDirection + "'");
            LOG.debug("  textPosition='" + textPosition + "'");
            LOG.debug("  readOnly='" + readOnly + "'");
            LOG.debug("  alternateText='" + alternateText + "'");
            LOG.debug("  selected='" + selected + "'");
            LOG.debug("  radioValue='" + radioValue + "'");
            LOG.debug("  groupName='" + groupName + "'");
            LOG.debug("  required='" + required + "'");
        }
        super.setProperties(uiComponent);

        if ((uiComponent instanceof RadioButtonComponent) == false) {
            if (uiComponent instanceof UIViewRoot) {
                throw new IllegalStateException(
                        "The first component of the page must be a UIViewRoot component !");
            }
            throw new IllegalStateException(
                    "Component specified by tag is not instanceof of 'RadioButtonComponent'.");
        }

        RadioButtonComponent component = (RadioButtonComponent) uiComponent;
        FacesContext facesContext = getFacesContext();
        Application application = facesContext.getApplication();

        if (text != null) {
            if (isValueReference(text)) {
                ValueBinding vb = application.createValueBinding(text);
                component.setValueBinding(Properties.TEXT, vb);

            } else {
                component.setText(text);
            }
        }

        if (textDirection != null) {
            if (isValueReference(textDirection)) {
                ValueBinding vb = application.createValueBinding(textDirection);
                component.setValueBinding(Properties.TEXT_DIRECTION, vb);

            } else {
                component.setTextDirection(getInt(textDirection));
            }
        }

        if (textPosition != null) {
            if (isValueReference(textPosition)) {
                ValueBinding vb = application.createValueBinding(textPosition);
                component.setValueBinding(Properties.TEXT_POSITION, vb);

            } else {
                component.setTextPosition(textPosition);
            }
        }

        if (selectionListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
        }

        if (readOnly != null) {
            if (isValueReference(readOnly)) {
                ValueBinding vb = application.createValueBinding(readOnly);
                component.setValueBinding(Properties.READ_ONLY, vb);

            } else {
                component.setReadOnly(getBool(readOnly));
            }
        }

        if (alternateText != null) {
            if (isValueReference(alternateText)) {
                ValueBinding vb = application.createValueBinding(alternateText);
                component.setValueBinding(Properties.ALTERNATE_TEXT, vb);

            } else {
                component.setAlternateText(alternateText);
            }
        }

        if (selected != null) {
            if (isValueReference(selected)) {
                ValueBinding vb = application.createValueBinding(selected);
                component.setValueBinding(Properties.SELECTED, vb);

            } else {
                component.setSelected(getBool(selected));
            }
        }

        if (radioValue != null) {
            if (isValueReference(radioValue)) {
                ValueBinding vb = application.createValueBinding(radioValue);
                component.setValueBinding(Properties.RADIO_VALUE, vb);

            } else {
                component.setRadioValue(radioValue);
            }
        }

        if (groupName != null) {
            if (isValueReference(groupName)) {
                ValueBinding vb = application.createValueBinding(groupName);
                component.setValueBinding(Properties.GROUP_NAME, vb);

            } else {
                component.setGroupName(groupName);
            }
        }

        if (required != null) {
            if (isValueReference(required)) {
                ValueBinding vb = application.createValueBinding(required);
                component.setValueBinding(Properties.REQUIRED, vb);

            } else {
                component.setRequired(getBool(required));
            }
        }
    }

    public void release() {
        text = null;
        textDirection = null;
        textPosition = null;
        selectionListeners = null;
        readOnly = null;
        alternateText = null;
        selected = null;
        radioValue = null;
        groupName = null;
        required = null;

        super.release();
    }

}
