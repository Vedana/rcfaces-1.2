package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.MessageDialogComponent;
import javax.faces.application.Application;

public class MessageDialogTag extends CameliaTag implements Tag {

    private static final Log LOG = LogFactory.getLog(MessageDialogTag.class);

    private String imageURL;

    private String styleClass;

    private String text;

    private String textDirection;

    private String visible;

    private String dialogPriority;

    private String immediate;

    private String validationListeners;

    private String width;

    private String height;

    private String hiddenMode;

    private String lookId;

    private String waiRole;

    private String selectionListeners;

    private String title;

    private String defaultValue;

    private String value;

    private String converter;

    public String getComponentType() {
        return MessageDialogComponent.COMPONENT_TYPE;
    }

    public final String getImageURL() {
        return imageURL;
    }

    public final void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public final String getStyleClass() {
        return styleClass;
    }

    public final void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
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

    public final String getVisible() {
        return visible;
    }

    public final void setVisible(String visible) {
        this.visible = visible;
    }

    public final String getDialogPriority() {
        return dialogPriority;
    }

    public final void setDialogPriority(String dialogPriority) {
        this.dialogPriority = dialogPriority;
    }

    public final String getImmediate() {
        return immediate;
    }

    public final void setImmediate(String immediate) {
        this.immediate = immediate;
    }

    public final String getValidationListener() {
        return validationListeners;
    }

    public final void setValidationListener(String validationListeners) {
        this.validationListeners = validationListeners;
    }

    public final String getWidth() {
        return width;
    }

    public final void setWidth(String width) {
        this.width = width;
    }

    public final String getHeight() {
        return height;
    }

    public final void setHeight(String height) {
        this.height = height;
    }

    public final String getHiddenMode() {
        return hiddenMode;
    }

    public final void setHiddenMode(String hiddenMode) {
        this.hiddenMode = hiddenMode;
    }

    public final String getLookId() {
        return lookId;
    }

    public final void setLookId(String lookId) {
        this.lookId = lookId;
    }

    public final String getWaiRole() {
        return waiRole;
    }

    public final void setWaiRole(String waiRole) {
        this.waiRole = waiRole;
    }

    public final String getSelectionListener() {
        return selectionListeners;
    }

    public final void setSelectionListener(String selectionListeners) {
        this.selectionListeners = selectionListeners;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public final String getDefaultValue() {
        return defaultValue;
    }

    public final void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public final String getValue() {
        return value;
    }

    public final void setValue(String value) {
        this.value = value;
    }

    public final String getConverter() {
        return converter;
    }

    public final void setConverter(String converter) {
        this.converter = converter;
    }

    protected void setProperties(UIComponent uiComponent) {
        if (LOG.isDebugEnabled()) {
            if (MessageDialogComponent.COMPONENT_TYPE == getComponentType()) {
                LOG.debug("Component id='" + getId() + "' type='"
                        + getComponentType() + "'.");
            }
            LOG.debug("  imageURL='" + imageURL + "'");
            LOG.debug("  styleClass='" + styleClass + "'");
            LOG.debug("  text='" + text + "'");
            LOG.debug("  textDirection='" + textDirection + "'");
            LOG.debug("  visible='" + visible + "'");
            LOG.debug("  dialogPriority='" + dialogPriority + "'");
            LOG.debug("  immediate='" + immediate + "'");
            LOG.debug("  width='" + width + "'");
            LOG.debug("  height='" + height + "'");
            LOG.debug("  hiddenMode='" + hiddenMode + "'");
            LOG.debug("  lookId='" + lookId + "'");
            LOG.debug("  waiRole='" + waiRole + "'");
            LOG.debug("  title='" + title + "'");
            LOG.debug("  defaultValue='" + defaultValue + "'");
        }
        super.setProperties(uiComponent);

        if ((uiComponent instanceof MessageDialogComponent) == false) {
            if (uiComponent instanceof UIViewRoot) {
                throw new IllegalStateException(
                        "The first component of the page must be a UIViewRoot component !");
            }
            throw new IllegalStateException(
                    "Component specified by tag is not instanceof of 'MessageDialogComponent'.");
        }

        MessageDialogComponent component = (MessageDialogComponent) uiComponent;
        FacesContext facesContext = getFacesContext();
        Application application = facesContext.getApplication();

        if (imageURL != null) {
            if (isValueReference(imageURL)) {
                ValueBinding vb = application.createValueBinding(imageURL);
                component.setValueBinding(Properties.IMAGE_URL, vb);

            } else {
                component.setImageURL(imageURL);
            }
        }

        if (styleClass != null) {
            if (isValueReference(styleClass)) {
                ValueBinding vb = application.createValueBinding(styleClass);
                component.setValueBinding(Properties.STYLE_CLASS, vb);

            } else {
                component.setStyleClass(styleClass);
            }
        }

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

        if (visible != null) {
            if (isValueReference(visible)) {
                ValueBinding vb = application.createValueBinding(visible);
                component.setValueBinding(Properties.VISIBLE, vb);

            } else {
                component.setVisible(getBool(visible));
            }
        }

        if (dialogPriority != null) {
            if (isValueReference(dialogPriority)) {
                ValueBinding vb = application
                        .createValueBinding(dialogPriority);
                component.setValueBinding(Properties.DIALOG_PRIORITY, vb);

            } else {
                component.setDialogPriority(getInt(dialogPriority));
            }
        }

        if (immediate != null) {
            if (isValueReference(immediate)) {
                ValueBinding vb = application.createValueBinding(immediate);
                component.setValueBinding(Properties.IMMEDIATE, vb);

            } else {
                component.setImmediate(getBool(immediate));
            }
        }

        if (validationListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.VALIDATION_LISTENER_TYPE,
                    validationListeners);
        }

        if (width != null) {
            if (isValueReference(width)) {
                ValueBinding vb = application.createValueBinding(width);
                component.setValueBinding(Properties.WIDTH, vb);

            } else {
                component.setWidth(width);
            }
        }

        if (height != null) {
            if (isValueReference(height)) {
                ValueBinding vb = application.createValueBinding(height);
                component.setValueBinding(Properties.HEIGHT, vb);

            } else {
                component.setHeight(height);
            }
        }

        if (hiddenMode != null) {
            if (isValueReference(hiddenMode)) {
                ValueBinding vb = application.createValueBinding(hiddenMode);
                component.setValueBinding(Properties.HIDDEN_MODE, vb);

            } else {
                component.setHiddenMode(hiddenMode);
            }
        }

        if (lookId != null) {
            if (isValueReference(lookId)) {
                ValueBinding vb = application.createValueBinding(lookId);
                component.setValueBinding(Properties.LOOK_ID, vb);

            } else {
                component.setLookId(lookId);
            }
        }

        if (waiRole != null) {
            if (isValueReference(waiRole)) {
                ValueBinding vb = application.createValueBinding(waiRole);
                component.setValueBinding(Properties.WAI_ROLE, vb);

            } else {
                component.setWaiRole(waiRole);
            }
        }

        if (selectionListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
        }

        if (title != null) {
            if (isValueReference(title)) {
                ValueBinding vb = application.createValueBinding(title);
                component.setValueBinding(Properties.TITLE, vb);

            } else {
                component.setTitle(title);
            }
        }

        if (defaultValue != null) {
            if (isValueReference(defaultValue)) {
                ValueBinding vb = application.createValueBinding(defaultValue);
                component.setValueBinding(Properties.DEFAULT_VALUE, vb);

            } else {
                component.setDefaultValue(defaultValue);
            }
        }

        if (value != null) {
            if (isValueReference(value)) {
                ValueBinding vb = application.createValueBinding(value);
                component.setValueBinding(Properties.VALUE, vb);

            } else {
                component.setValue(value);
            }
        }

        if (converter != null) {
            if (isValueReference(converter)) {
                ValueBinding vb = application.createValueBinding(converter);
                component.setValueBinding(Properties.CONVERTER, vb);

            } else {
                component.setConverter(converter);
            }
        }
    }

    public void release() {
        imageURL = null;
        styleClass = null;
        text = null;
        textDirection = null;
        visible = null;
        dialogPriority = null;
        immediate = null;
        validationListeners = null;
        width = null;
        height = null;
        hiddenMode = null;
        lookId = null;
        waiRole = null;
        selectionListeners = null;
        title = null;
        defaultValue = null;
        value = null;
        converter = null;

        super.release();
    }

}
