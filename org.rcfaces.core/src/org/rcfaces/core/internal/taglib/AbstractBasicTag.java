package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.AbstractBasicComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;

public abstract class AbstractBasicTag extends CameliaTag implements Tag {

    private static final Log LOG = LogFactory.getLog(AbstractBasicTag.class);

    private String propertyChangeListeners;

    private String userEventListeners;

    private String errorListeners;

    private String waiRole;

    private String x;

    private String y;

    private String width;

    private String height;

    private String marginBottom;

    private String marginLeft;

    private String marginRight;

    private String marginTop;

    private String helpMessage;

    private String helpURL;

    private String toolTipText;

    private String backgroundColor;

    private String foregroundColor;

    private String visible;

    private String hiddenMode;

    private String lookId;

    private String styleClass;

    private String margins;

    public final String getPropertyChangeListener() {
        return propertyChangeListeners;
    }

    public final void setPropertyChangeListener(String propertyChangeListeners) {
        this.propertyChangeListeners = propertyChangeListeners;
    }

    public final String getUserEventListener() {
        return userEventListeners;
    }

    public final void setUserEventListener(String userEventListeners) {
        this.userEventListeners = userEventListeners;
    }

    public final String getErrorListener() {
        return errorListeners;
    }

    public final void setErrorListener(String errorListeners) {
        this.errorListeners = errorListeners;
    }

    public final String getWaiRole() {
        return waiRole;
    }

    public final void setWaiRole(String waiRole) {
        this.waiRole = waiRole;
    }

    public final String getX() {
        return x;
    }

    public final void setX(String x) {
        this.x = x;
    }

    public final String getY() {
        return y;
    }

    public final void setY(String y) {
        this.y = y;
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

    public final String getMarginBottom() {
        return marginBottom;
    }

    public final void setMarginBottom(String marginBottom) {
        this.marginBottom = marginBottom;
    }

    public final String getMarginLeft() {
        return marginLeft;
    }

    public final void setMarginLeft(String marginLeft) {
        this.marginLeft = marginLeft;
    }

    public final String getMarginRight() {
        return marginRight;
    }

    public final void setMarginRight(String marginRight) {
        this.marginRight = marginRight;
    }

    public final String getMarginTop() {
        return marginTop;
    }

    public final void setMarginTop(String marginTop) {
        this.marginTop = marginTop;
    }

    public final String getHelpMessage() {
        return helpMessage;
    }

    public final void setHelpMessage(String helpMessage) {
        this.helpMessage = helpMessage;
    }

    public final String getHelpURL() {
        return helpURL;
    }

    public final void setHelpURL(String helpURL) {
        this.helpURL = helpURL;
    }

    public final String getToolTipText() {
        return toolTipText;
    }

    public final void setToolTipText(String toolTipText) {
        this.toolTipText = toolTipText;
    }

    public final String getBackgroundColor() {
        return backgroundColor;
    }

    public final void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public final String getForegroundColor() {
        return foregroundColor;
    }

    public final void setForegroundColor(String foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public final String getVisible() {
        return visible;
    }

    public final void setVisible(String visible) {
        this.visible = visible;
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

    public final String getStyleClass() {
        return styleClass;
    }

    public final void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public final String getMargins() {
        return margins;
    }

    public final void setMargins(String margins) {
        this.margins = margins;
    }

    protected void setProperties(UIComponent uiComponent) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("  waiRole='" + waiRole + "'");
            LOG.debug("  x='" + x + "'");
            LOG.debug("  y='" + y + "'");
            LOG.debug("  width='" + width + "'");
            LOG.debug("  height='" + height + "'");
            LOG.debug("  marginBottom='" + marginBottom + "'");
            LOG.debug("  marginLeft='" + marginLeft + "'");
            LOG.debug("  marginRight='" + marginRight + "'");
            LOG.debug("  marginTop='" + marginTop + "'");
            LOG.debug("  helpMessage='" + helpMessage + "'");
            LOG.debug("  helpURL='" + helpURL + "'");
            LOG.debug("  toolTipText='" + toolTipText + "'");
            LOG.debug("  backgroundColor='" + backgroundColor + "'");
            LOG.debug("  foregroundColor='" + foregroundColor + "'");
            LOG.debug("  visible='" + visible + "'");
            LOG.debug("  hiddenMode='" + hiddenMode + "'");
            LOG.debug("  lookId='" + lookId + "'");
            LOG.debug("  styleClass='" + styleClass + "'");
            LOG.debug("  margins='" + margins + "'");
        }
        super.setProperties(uiComponent);

        if ((uiComponent instanceof AbstractBasicComponent) == false) {
            if (uiComponent instanceof UIViewRoot) {
                throw new IllegalStateException(
                        "The first component of the page must be a UIViewRoot component !");
            }
            throw new IllegalStateException(
                    "Component specified by tag is not instanceof of 'AbstractBasicComponent'.");
        }

        AbstractBasicComponent component = (AbstractBasicComponent) uiComponent;
        FacesContext facesContext = getFacesContext();
        Application application = facesContext.getApplication();

        if (propertyChangeListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.PROPERTY_CHANGE_LISTENER_TYPE,
                    propertyChangeListeners);
        }

        if (userEventListeners != null) {
            ListenersTools
                    .parseListener(facesContext, component,
                            ListenersTools.USER_EVENT_LISTENER_TYPE,
                            userEventListeners);
        }

        if (errorListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.ERROR_LISTENER_TYPE, errorListeners);
        }

        if (waiRole != null) {
            if (isValueReference(waiRole)) {
                ValueBinding vb = application.createValueBinding(waiRole);
                component.setValueBinding(Properties.WAI_ROLE, vb);

            } else {
                component.setWaiRole(waiRole);
            }
        }

        if (x != null) {
            if (isValueReference(x)) {
                ValueBinding vb = application.createValueBinding(x);
                component.setValueBinding(Properties.X, vb);

            } else {
                component.setX(x);
            }
        }

        if (y != null) {
            if (isValueReference(y)) {
                ValueBinding vb = application.createValueBinding(y);
                component.setValueBinding(Properties.Y, vb);

            } else {
                component.setY(y);
            }
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

        if (marginBottom != null) {
            if (isValueReference(marginBottom)) {
                ValueBinding vb = application.createValueBinding(marginBottom);
                component.setValueBinding(Properties.MARGIN_BOTTOM, vb);

            } else {
                component.setMarginBottom(marginBottom);
            }
        }

        if (marginLeft != null) {
            if (isValueReference(marginLeft)) {
                ValueBinding vb = application.createValueBinding(marginLeft);
                component.setValueBinding(Properties.MARGIN_LEFT, vb);

            } else {
                component.setMarginLeft(marginLeft);
            }
        }

        if (marginRight != null) {
            if (isValueReference(marginRight)) {
                ValueBinding vb = application.createValueBinding(marginRight);
                component.setValueBinding(Properties.MARGIN_RIGHT, vb);

            } else {
                component.setMarginRight(marginRight);
            }
        }

        if (marginTop != null) {
            if (isValueReference(marginTop)) {
                ValueBinding vb = application.createValueBinding(marginTop);
                component.setValueBinding(Properties.MARGIN_TOP, vb);

            } else {
                component.setMarginTop(marginTop);
            }
        }

        if (helpMessage != null) {
            if (isValueReference(helpMessage)) {
                ValueBinding vb = application.createValueBinding(helpMessage);
                component.setValueBinding(Properties.HELP_MESSAGE, vb);

            } else {
                component.setHelpMessage(helpMessage);
            }
        }

        if (helpURL != null) {
            if (isValueReference(helpURL)) {
                ValueBinding vb = application.createValueBinding(helpURL);
                component.setValueBinding(Properties.HELP_URL, vb);

            } else {
                component.setHelpURL(helpURL);
            }
        }

        if (toolTipText != null) {
            if (isValueReference(toolTipText)) {
                ValueBinding vb = application.createValueBinding(toolTipText);
                component.setValueBinding(Properties.TOOL_TIP_TEXT, vb);

            } else {
                component.setToolTipText(toolTipText);
            }
        }

        if (backgroundColor != null) {
            if (isValueReference(backgroundColor)) {
                ValueBinding vb = application
                        .createValueBinding(backgroundColor);
                component.setValueBinding(Properties.BACKGROUND_COLOR, vb);

            } else {
                component.setBackgroundColor(backgroundColor);
            }
        }

        if (foregroundColor != null) {
            if (isValueReference(foregroundColor)) {
                ValueBinding vb = application
                        .createValueBinding(foregroundColor);
                component.setValueBinding(Properties.FOREGROUND_COLOR, vb);

            } else {
                component.setForegroundColor(foregroundColor);
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

        if (styleClass != null) {
            if (isValueReference(styleClass)) {
                ValueBinding vb = application.createValueBinding(styleClass);
                component.setValueBinding(Properties.STYLE_CLASS, vb);

            } else {
                component.setStyleClass(styleClass);
            }
        }

        if (margins != null) {
            if (isValueReference(margins)) {
                throw new javax.faces.FacesException(
                        "Attribute 'margins' does not accept binding !");
            }
            component.setMargins(margins);
        }
    }

    public void release() {
        propertyChangeListeners = null;
        userEventListeners = null;
        errorListeners = null;
        waiRole = null;
        x = null;
        y = null;
        width = null;
        height = null;
        marginBottom = null;
        marginLeft = null;
        marginRight = null;
        marginTop = null;
        helpMessage = null;
        helpURL = null;
        toolTipText = null;
        backgroundColor = null;
        foregroundColor = null;
        visible = null;
        hiddenMode = null;
        lookId = null;
        styleClass = null;
        margins = null;

        super.release();
    }

}
