package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;

public abstract class AbstractInputTag extends CameliaTag implements Tag {

    private static final Log LOG = LogFactory.getLog(AbstractInputTag.class);

    private String width;

    private String height;

    private String visible;

    private String mouseOutListeners;

    private String mouseOverListeners;

    private String disabled;

    private String unlockedClientAttributeNames;

    private String blurListeners;

    private String focusListeners;

    private String errorListeners;

    private String keyPressListeners;

    private String keyDownListeners;

    private String keyUpListeners;

    private String backgroundColor;

    private String foregroundColor;

    private String styleClass;

    private String fontBold;

    private String fontItalic;

    private String fontName;

    private String fontSize;

    private String fontUnderline;

    private String initListeners;

    private String helpMessage;

    private String helpURL;

    private String toolTipText;

    private String valueLocked;

    private String tabIndex;

    private String lookId;

    private String x;

    private String y;

    private String marginBottom;

    private String marginLeft;

    private String marginRight;

    private String marginTop;

    private String textAlignment;

    private String immediate;

    private String userEventListeners;

    private String hiddenMode;

    private String waiRole;

    private String propertyChangeListeners;

    private String accessKey;

    private String margins;

    private String value;

    private String converter;

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

    public final String getVisible() {
        return visible;
    }

    public final void setVisible(String visible) {
        this.visible = visible;
    }

    public final String getMouseOutListener() {
        return mouseOutListeners;
    }

    public final void setMouseOutListener(String mouseOutListeners) {
        this.mouseOutListeners = mouseOutListeners;
    }

    public final String getMouseOverListener() {
        return mouseOverListeners;
    }

    public final void setMouseOverListener(String mouseOverListeners) {
        this.mouseOverListeners = mouseOverListeners;
    }

    public final String getDisabled() {
        return disabled;
    }

    public final void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public final String getUnlockedClientAttributeNames() {
        return unlockedClientAttributeNames;
    }

    public final void setUnlockedClientAttributeNames(
            String unlockedClientAttributeNames) {
        this.unlockedClientAttributeNames = unlockedClientAttributeNames;
    }

    public final String getBlurListener() {
        return blurListeners;
    }

    public final void setBlurListener(String blurListeners) {
        this.blurListeners = blurListeners;
    }

    public final String getFocusListener() {
        return focusListeners;
    }

    public final void setFocusListener(String focusListeners) {
        this.focusListeners = focusListeners;
    }

    public final String getErrorListener() {
        return errorListeners;
    }

    public final void setErrorListener(String errorListeners) {
        this.errorListeners = errorListeners;
    }

    public final String getKeyPressListener() {
        return keyPressListeners;
    }

    public final void setKeyPressListener(String keyPressListeners) {
        this.keyPressListeners = keyPressListeners;
    }

    public final String getKeyDownListener() {
        return keyDownListeners;
    }

    public final void setKeyDownListener(String keyDownListeners) {
        this.keyDownListeners = keyDownListeners;
    }

    public final String getKeyUpListener() {
        return keyUpListeners;
    }

    public final void setKeyUpListener(String keyUpListeners) {
        this.keyUpListeners = keyUpListeners;
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

    public final String getStyleClass() {
        return styleClass;
    }

    public final void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public final String getFontBold() {
        return fontBold;
    }

    public final void setFontBold(String fontBold) {
        this.fontBold = fontBold;
    }

    public final String getFontItalic() {
        return fontItalic;
    }

    public final void setFontItalic(String fontItalic) {
        this.fontItalic = fontItalic;
    }

    public final String getFontName() {
        return fontName;
    }

    public final void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public final String getFontSize() {
        return fontSize;
    }

    public final void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public final String getFontUnderline() {
        return fontUnderline;
    }

    public final void setFontUnderline(String fontUnderline) {
        this.fontUnderline = fontUnderline;
    }

    public final String getInitListener() {
        return initListeners;
    }

    public final void setInitListener(String initListeners) {
        this.initListeners = initListeners;
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

    public final String getValueLocked() {
        return valueLocked;
    }

    public final void setValueLocked(String valueLocked) {
        this.valueLocked = valueLocked;
    }

    public final String getTabIndex() {
        return tabIndex;
    }

    public final void setTabIndex(String tabIndex) {
        this.tabIndex = tabIndex;
    }

    public final String getLookId() {
        return lookId;
    }

    public final void setLookId(String lookId) {
        this.lookId = lookId;
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

    public final String getTextAlignment() {
        return textAlignment;
    }

    public final void setTextAlignment(String textAlignment) {
        this.textAlignment = textAlignment;
    }

    public final String getImmediate() {
        return immediate;
    }

    public final void setImmediate(String immediate) {
        this.immediate = immediate;
    }

    public final String getUserEventListener() {
        return userEventListeners;
    }

    public final void setUserEventListener(String userEventListeners) {
        this.userEventListeners = userEventListeners;
    }

    public final String getHiddenMode() {
        return hiddenMode;
    }

    public final void setHiddenMode(String hiddenMode) {
        this.hiddenMode = hiddenMode;
    }

    public final String getWaiRole() {
        return waiRole;
    }

    public final void setWaiRole(String waiRole) {
        this.waiRole = waiRole;
    }

    public final String getPropertyChangeListener() {
        return propertyChangeListeners;
    }

    public final void setPropertyChangeListener(String propertyChangeListeners) {
        this.propertyChangeListeners = propertyChangeListeners;
    }

    public final String getAccessKey() {
        return accessKey;
    }

    public final void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public final String getMargins() {
        return margins;
    }

    public final void setMargins(String margins) {
        this.margins = margins;
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
            LOG.debug("  width='" + width + "'");
            LOG.debug("  height='" + height + "'");
            LOG.debug("  visible='" + visible + "'");
            LOG.debug("  disabled='" + disabled + "'");
            LOG.debug("  unlockedClientAttributeNames='"
                    + unlockedClientAttributeNames + "'");
            LOG.debug("  backgroundColor='" + backgroundColor + "'");
            LOG.debug("  foregroundColor='" + foregroundColor + "'");
            LOG.debug("  styleClass='" + styleClass + "'");
            LOG.debug("  fontBold='" + fontBold + "'");
            LOG.debug("  fontItalic='" + fontItalic + "'");
            LOG.debug("  fontName='" + fontName + "'");
            LOG.debug("  fontSize='" + fontSize + "'");
            LOG.debug("  fontUnderline='" + fontUnderline + "'");
            LOG.debug("  helpMessage='" + helpMessage + "'");
            LOG.debug("  helpURL='" + helpURL + "'");
            LOG.debug("  toolTipText='" + toolTipText + "'");
            LOG.debug("  valueLocked='" + valueLocked + "'");
            LOG.debug("  tabIndex='" + tabIndex + "'");
            LOG.debug("  lookId='" + lookId + "'");
            LOG.debug("  x='" + x + "'");
            LOG.debug("  y='" + y + "'");
            LOG.debug("  marginBottom='" + marginBottom + "'");
            LOG.debug("  marginLeft='" + marginLeft + "'");
            LOG.debug("  marginRight='" + marginRight + "'");
            LOG.debug("  marginTop='" + marginTop + "'");
            LOG.debug("  textAlignment='" + textAlignment + "'");
            LOG.debug("  immediate='" + immediate + "'");
            LOG.debug("  hiddenMode='" + hiddenMode + "'");
            LOG.debug("  waiRole='" + waiRole + "'");
            LOG.debug("  accessKey='" + accessKey + "'");
            LOG.debug("  margins='" + margins + "'");
        }
        super.setProperties(uiComponent);

        if ((uiComponent instanceof AbstractInputComponent) == false) {
            if (uiComponent instanceof UIViewRoot) {
                throw new IllegalStateException(
                        "The first component of the page must be a UIViewRoot component !");
            }
            throw new IllegalStateException(
                    "Component specified by tag is not instanceof of 'AbstractInputComponent'.");
        }

        AbstractInputComponent component = (AbstractInputComponent) uiComponent;
        FacesContext facesContext = getFacesContext();
        Application application = facesContext.getApplication();

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

        if (visible != null) {
            if (isValueReference(visible)) {
                ValueBinding vb = application.createValueBinding(visible);
                component.setValueBinding(Properties.VISIBLE, vb);

            } else {
                component.setVisible(getBool(visible));
            }
        }

        if (mouseOutListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
        }

        if (mouseOverListeners != null) {
            ListenersTools
                    .parseListener(facesContext, component,
                            ListenersTools.MOUSE_OVER_LISTENER_TYPE,
                            mouseOverListeners);
        }

        if (disabled != null) {
            if (isValueReference(disabled)) {
                ValueBinding vb = application.createValueBinding(disabled);
                component.setValueBinding(Properties.DISABLED, vb);

            } else {
                component.setDisabled(getBool(disabled));
            }
        }

        if (unlockedClientAttributeNames != null) {
            if (isValueReference(unlockedClientAttributeNames)) {
                ValueBinding vb = application
                        .createValueBinding(unlockedClientAttributeNames);
                component.setValueBinding(
                        Properties.UNLOCKED_CLIENT_ATTRIBUTE_NAMES, vb);

            } else {
                component
                        .setUnlockedClientAttributeNames(unlockedClientAttributeNames);
            }
        }

        if (blurListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.BLUR_LISTENER_TYPE, blurListeners);
        }

        if (focusListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.FOCUS_LISTENER_TYPE, focusListeners);
        }

        if (errorListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.ERROR_LISTENER_TYPE, errorListeners);
        }

        if (keyPressListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.KEY_PRESS_LISTENER_TYPE, keyPressListeners);
        }

        if (keyDownListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.KEY_DOWN_LISTENER_TYPE, keyDownListeners);
        }

        if (keyUpListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.KEY_UP_LISTENER_TYPE, keyUpListeners);
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

        if (styleClass != null) {
            if (isValueReference(styleClass)) {
                ValueBinding vb = application.createValueBinding(styleClass);
                component.setValueBinding(Properties.STYLE_CLASS, vb);

            } else {
                component.setStyleClass(styleClass);
            }
        }

        if (fontBold != null) {
            if (isValueReference(fontBold)) {
                ValueBinding vb = application.createValueBinding(fontBold);
                component.setValueBinding(Properties.FONT_BOLD, vb);

            } else {
                component.setFontBold(getBoolean(fontBold));
            }
        }

        if (fontItalic != null) {
            if (isValueReference(fontItalic)) {
                ValueBinding vb = application.createValueBinding(fontItalic);
                component.setValueBinding(Properties.FONT_ITALIC, vb);

            } else {
                component.setFontItalic(getBoolean(fontItalic));
            }
        }

        if (fontName != null) {
            if (isValueReference(fontName)) {
                ValueBinding vb = application.createValueBinding(fontName);
                component.setValueBinding(Properties.FONT_NAME, vb);

            } else {
                component.setFontName(fontName);
            }
        }

        if (fontSize != null) {
            if (isValueReference(fontSize)) {
                ValueBinding vb = application.createValueBinding(fontSize);
                component.setValueBinding(Properties.FONT_SIZE, vb);

            } else {
                component.setFontSize(fontSize);
            }
        }

        if (fontUnderline != null) {
            if (isValueReference(fontUnderline)) {
                ValueBinding vb = application.createValueBinding(fontUnderline);
                component.setValueBinding(Properties.FONT_UNDERLINE, vb);

            } else {
                component.setFontUnderline(getBoolean(fontUnderline));
            }
        }

        if (initListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.INIT_LISTENER_TYPE, initListeners);
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

        if (valueLocked != null) {
            if (isValueReference(valueLocked)) {
                ValueBinding vb = application.createValueBinding(valueLocked);
                component.setValueBinding(Properties.VALUE_LOCKED, vb);

            } else {
                component.setValueLocked(getBool(valueLocked));
            }
        }

        if (tabIndex != null) {
            if (isValueReference(tabIndex)) {
                ValueBinding vb = application.createValueBinding(tabIndex);
                component.setValueBinding(Properties.TAB_INDEX, vb);

            } else {
                component.setTabIndex(getInteger(tabIndex));
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

        if (textAlignment != null) {
            if (isValueReference(textAlignment)) {
                ValueBinding vb = application.createValueBinding(textAlignment);
                component.setValueBinding(Properties.TEXT_ALIGNMENT, vb);

            } else {
                component.setTextAlignment(textAlignment);
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

        if (userEventListeners != null) {
            ListenersTools
                    .parseListener(facesContext, component,
                            ListenersTools.USER_EVENT_LISTENER_TYPE,
                            userEventListeners);
        }

        if (hiddenMode != null) {
            if (isValueReference(hiddenMode)) {
                ValueBinding vb = application.createValueBinding(hiddenMode);
                component.setValueBinding(Properties.HIDDEN_MODE, vb);

            } else {
                component.setHiddenMode(hiddenMode);
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

        if (propertyChangeListeners != null) {
            ListenersTools.parseListener(facesContext, component,
                    ListenersTools.PROPERTY_CHANGE_LISTENER_TYPE,
                    propertyChangeListeners);
        }

        if (accessKey != null) {
            if (isValueReference(accessKey)) {
                ValueBinding vb = application.createValueBinding(accessKey);
                component.setValueBinding(Properties.ACCESS_KEY, vb);

            } else {
                component.setAccessKey(accessKey);
            }
        }

        if (margins != null) {
            if (isValueReference(margins)) {
                throw new javax.faces.FacesException(
                        "Attribute 'margins' does not accept binding !");
            }
            component.setMargins(margins);
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
        width = null;
        height = null;
        visible = null;
        mouseOutListeners = null;
        mouseOverListeners = null;
        disabled = null;
        unlockedClientAttributeNames = null;
        blurListeners = null;
        focusListeners = null;
        errorListeners = null;
        keyPressListeners = null;
        keyDownListeners = null;
        keyUpListeners = null;
        backgroundColor = null;
        foregroundColor = null;
        styleClass = null;
        fontBold = null;
        fontItalic = null;
        fontName = null;
        fontSize = null;
        fontUnderline = null;
        initListeners = null;
        helpMessage = null;
        helpURL = null;
        toolTipText = null;
        valueLocked = null;
        tabIndex = null;
        lookId = null;
        x = null;
        y = null;
        marginBottom = null;
        marginLeft = null;
        marginRight = null;
        marginTop = null;
        textAlignment = null;
        immediate = null;
        userEventListeners = null;
        hiddenMode = null;
        waiRole = null;
        propertyChangeListeners = null;
        accessKey = null;
        margins = null;
        value = null;
        converter = null;

        super.release();
    }

}
