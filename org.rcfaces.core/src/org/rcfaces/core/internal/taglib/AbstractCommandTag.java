package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.AbstractCommandComponent;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public abstract class AbstractCommandTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AbstractCommandTag.class);

	private String hiddenMode;
	private String visible;
	private String height;
	private String width;
	private String helpMessage;
	private String helpURL;
	private String toolTipText;
	private String mouseOutListeners;
	private String mouseOverListeners;
	private String disabled;
	private String unlockedClientAttributeNames;
	private String tabIndex;
	private String lookId;
	private String x;
	private String y;
	private String blurListeners;
	private String focusListeners;
	private String marginBottom;
	private String marginLeft;
	private String marginRight;
	private String marginTop;
	private String backgroundColor;
	private String foregroundColor;
	private String textAlignment;
	private String styleClass;
	private String userEventListeners;
	private String propertyChangeListeners;
	private String fontBold;
	private String fontItalic;
	private String fontName;
	private String fontSize;
	private String fontUnderline;
	private String accessKey;
	private String initListeners;
	private String margins;
	private String immediate;
	private String value;
	public final String getHiddenMode() {
		return hiddenMode;
	}

	public final void setHiddenMode(String hiddenMode) {
		this.hiddenMode = hiddenMode;
	}

	public final String getVisible() {
		return visible;
	}

	public final void setVisible(String visible) {
		this.visible = visible;
	}

	public final String getHeight() {
		return height;
	}

	public final void setHeight(String height) {
		this.height = height;
	}

	public final String getWidth() {
		return width;
	}

	public final void setWidth(String width) {
		this.width = width;
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

	public final void setUnlockedClientAttributeNames(String unlockedClientAttributeNames) {
		this.unlockedClientAttributeNames = unlockedClientAttributeNames;
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

	public final String getTextAlignment() {
		return textAlignment;
	}

	public final void setTextAlignment(String textAlignment) {
		this.textAlignment = textAlignment;
	}

	public final String getStyleClass() {
		return styleClass;
	}

	public final void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public final String getUserEventListener() {
		return userEventListeners;
	}

	public final void setUserEventListener(String userEventListeners) {
		this.userEventListeners = userEventListeners;
	}

	public final String getPropertyChangeListener() {
		return propertyChangeListeners;
	}

	public final void setPropertyChangeListener(String propertyChangeListeners) {
		this.propertyChangeListeners = propertyChangeListeners;
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

	public final String getAccessKey() {
		return accessKey;
	}

	public final void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public final String getInitListener() {
		return initListeners;
	}

	public final void setInitListener(String initListeners) {
		this.initListeners = initListeners;
	}

	public final String getMargins() {
		return margins;
	}

	public final void setMargins(String margins) {
		this.margins = margins;
	}

	public final String getImmediate() {
		return immediate;
	}

	public final void setImmediate(String immediate) {
		this.immediate = immediate;
	}

	public final String getValue() {
		return value;
	}

	public final void setValue(String value) {
		this.value = value;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  height='"+height+"'");
			LOG.debug("  width='"+width+"'");
			LOG.debug("  helpMessage='"+helpMessage+"'");
			LOG.debug("  helpURL='"+helpURL+"'");
			LOG.debug("  toolTipText='"+toolTipText+"'");
			LOG.debug("  disabled='"+disabled+"'");
			LOG.debug("  unlockedClientAttributeNames='"+unlockedClientAttributeNames+"'");
			LOG.debug("  tabIndex='"+tabIndex+"'");
			LOG.debug("  lookId='"+lookId+"'");
			LOG.debug("  x='"+x+"'");
			LOG.debug("  y='"+y+"'");
			LOG.debug("  marginBottom='"+marginBottom+"'");
			LOG.debug("  marginLeft='"+marginLeft+"'");
			LOG.debug("  marginRight='"+marginRight+"'");
			LOG.debug("  marginTop='"+marginTop+"'");
			LOG.debug("  backgroundColor='"+backgroundColor+"'");
			LOG.debug("  foregroundColor='"+foregroundColor+"'");
			LOG.debug("  textAlignment='"+textAlignment+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  fontBold='"+fontBold+"'");
			LOG.debug("  fontItalic='"+fontItalic+"'");
			LOG.debug("  fontName='"+fontName+"'");
			LOG.debug("  fontSize='"+fontSize+"'");
			LOG.debug("  fontUnderline='"+fontUnderline+"'");
			LOG.debug("  accessKey='"+accessKey+"'");
			LOG.debug("  margins='"+margins+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof AbstractCommandComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractCommandComponent'.");
		}

		AbstractCommandComponent component = (AbstractCommandComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (hiddenMode != null) {
			if (isValueReference(hiddenMode)) {
				ValueBinding vb = application.createValueBinding(hiddenMode);

				component.setHiddenMode(vb);
			} else {
				component.setHiddenMode(hiddenMode);
			}
		}

		if (visible != null) {
			if (isValueReference(visible)) {
				ValueBinding vb = application.createValueBinding(visible);

				component.setVisible(vb);
			} else {
				component.setVisible(getBoolean(visible));
			}
		}

		if (height != null) {
			if (isValueReference(height)) {
				ValueBinding vb = application.createValueBinding(height);

				component.setHeight(vb);
			} else {
				component.setHeight(height);
			}
		}

		if (width != null) {
			if (isValueReference(width)) {
				ValueBinding vb = application.createValueBinding(width);

				component.setWidth(vb);
			} else {
				component.setWidth(width);
			}
		}

		if (helpMessage != null) {
			if (isValueReference(helpMessage)) {
				ValueBinding vb = application.createValueBinding(helpMessage);

				component.setHelpMessage(vb);
			} else {
				component.setHelpMessage(helpMessage);
			}
		}

		if (helpURL != null) {
			if (isValueReference(helpURL)) {
				ValueBinding vb = application.createValueBinding(helpURL);

				component.setHelpURL(vb);
			} else {
				component.setHelpURL(helpURL);
			}
		}

		if (toolTipText != null) {
			if (isValueReference(toolTipText)) {
				ValueBinding vb = application.createValueBinding(toolTipText);

				component.setToolTipText(vb);
			} else {
				component.setToolTipText(toolTipText);
			}
		}

		if (mouseOutListeners != null) {
			Listeners.parseListener(facesContext, component, Listeners.MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
		}

		if (mouseOverListeners != null) {
			Listeners.parseListener(facesContext, component, Listeners.MOUSE_OVER_LISTENER_TYPE, mouseOverListeners);
		}

		if (disabled != null) {
			if (isValueReference(disabled)) {
				ValueBinding vb = application.createValueBinding(disabled);

				component.setDisabled(vb);
			} else {
				component.setDisabled(getBool(disabled));
			}
		}

		if (unlockedClientAttributeNames != null) {
			if (isValueReference(unlockedClientAttributeNames)) {
				ValueBinding vb = application.createValueBinding(unlockedClientAttributeNames);

				component.setUnlockedClientAttributeNames(vb);
			} else {
				component.setUnlockedClientAttributeNames(unlockedClientAttributeNames);
			}
		}

		if (tabIndex != null) {
			if (isValueReference(tabIndex)) {
				ValueBinding vb = application.createValueBinding(tabIndex);

				component.setTabIndex(vb);
			} else {
				component.setTabIndex(getInteger(tabIndex));
			}
		}

		if (lookId != null) {
			if (isValueReference(lookId)) {
				ValueBinding vb = application.createValueBinding(lookId);

				component.setLookId(vb);
			} else {
				component.setLookId(lookId);
			}
		}

		if (x != null) {
			if (isValueReference(x)) {
				ValueBinding vb = application.createValueBinding(x);

				component.setX(vb);
			} else {
				component.setX(x);
			}
		}

		if (y != null) {
			if (isValueReference(y)) {
				ValueBinding vb = application.createValueBinding(y);

				component.setY(vb);
			} else {
				component.setY(y);
			}
		}

		if (blurListeners != null) {
			Listeners.parseListener(facesContext, component, Listeners.BLUR_LISTENER_TYPE, blurListeners);
		}

		if (focusListeners != null) {
			Listeners.parseListener(facesContext, component, Listeners.FOCUS_LISTENER_TYPE, focusListeners);
		}

		if (marginBottom != null) {
			if (isValueReference(marginBottom)) {
				ValueBinding vb = application.createValueBinding(marginBottom);

				component.setMarginBottom(vb);
			} else {
				component.setMarginBottom(marginBottom);
			}
		}

		if (marginLeft != null) {
			if (isValueReference(marginLeft)) {
				ValueBinding vb = application.createValueBinding(marginLeft);

				component.setMarginLeft(vb);
			} else {
				component.setMarginLeft(marginLeft);
			}
		}

		if (marginRight != null) {
			if (isValueReference(marginRight)) {
				ValueBinding vb = application.createValueBinding(marginRight);

				component.setMarginRight(vb);
			} else {
				component.setMarginRight(marginRight);
			}
		}

		if (marginTop != null) {
			if (isValueReference(marginTop)) {
				ValueBinding vb = application.createValueBinding(marginTop);

				component.setMarginTop(vb);
			} else {
				component.setMarginTop(marginTop);
			}
		}

		if (backgroundColor != null) {
			if (isValueReference(backgroundColor)) {
				ValueBinding vb = application.createValueBinding(backgroundColor);

				component.setBackgroundColor(vb);
			} else {
				component.setBackgroundColor(backgroundColor);
			}
		}

		if (foregroundColor != null) {
			if (isValueReference(foregroundColor)) {
				ValueBinding vb = application.createValueBinding(foregroundColor);

				component.setForegroundColor(vb);
			} else {
				component.setForegroundColor(foregroundColor);
			}
		}

		if (textAlignment != null) {
			if (isValueReference(textAlignment)) {
				ValueBinding vb = application.createValueBinding(textAlignment);

				component.setTextAlignment(vb);
			} else {
				component.setTextAlignment(textAlignment);
			}
		}

		if (styleClass != null) {
			if (isValueReference(styleClass)) {
				ValueBinding vb = application.createValueBinding(styleClass);

				component.setStyleClass(vb);
			} else {
				component.setStyleClass(styleClass);
			}
		}

		if (userEventListeners != null) {
			Listeners.parseListener(facesContext, component, Listeners.USER_EVENT_LISTENER_TYPE, userEventListeners);
		}

		if (propertyChangeListeners != null) {
			Listeners.parseListener(facesContext, component, Listeners.PROPERTY_CHANGE_LISTENER_TYPE, propertyChangeListeners);
		}

		if (fontBold != null) {
			if (isValueReference(fontBold)) {
				ValueBinding vb = application.createValueBinding(fontBold);

				component.setFontBold(vb);
			} else {
				component.setFontBold(getBoolean(fontBold));
			}
		}

		if (fontItalic != null) {
			if (isValueReference(fontItalic)) {
				ValueBinding vb = application.createValueBinding(fontItalic);

				component.setFontItalic(vb);
			} else {
				component.setFontItalic(getBoolean(fontItalic));
			}
		}

		if (fontName != null) {
			if (isValueReference(fontName)) {
				ValueBinding vb = application.createValueBinding(fontName);

				component.setFontName(vb);
			} else {
				component.setFontName(fontName);
			}
		}

		if (fontSize != null) {
			if (isValueReference(fontSize)) {
				ValueBinding vb = application.createValueBinding(fontSize);

				component.setFontSize(vb);
			} else {
				component.setFontSize(fontSize);
			}
		}

		if (fontUnderline != null) {
			if (isValueReference(fontUnderline)) {
				ValueBinding vb = application.createValueBinding(fontUnderline);

				component.setFontUnderline(vb);
			} else {
				component.setFontUnderline(getBoolean(fontUnderline));
			}
		}

		if (accessKey != null) {
			if (isValueReference(accessKey)) {
				ValueBinding vb = application.createValueBinding(accessKey);

				component.setAccessKey(vb);
			} else {
				component.setAccessKey(accessKey);
			}
		}

		if (initListeners != null) {
			Listeners.parseListener(facesContext, component, Listeners.INIT_LISTENER_TYPE, initListeners);
		}

		if (margins != null) {
			if (isValueReference(margins)) {
				throw new javax.faces.FacesException("Attribute 'margins' does not accept binding !");
			}
				component.setMargins(margins);
		}

		if (immediate != null) {
			if (isValueReference(immediate)) {
				ValueBinding vb = application.createValueBinding(immediate);
				component.setImmediate(vb);
			} else {
				component.setImmediate(getBool(immediate));
			}
		}

		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = application.createValueBinding(value);
				component.setValue(vb);
			} else {
				component.setValue(value);
			}
		}
	}

	public void release() {
		hiddenMode = null;
		visible = null;
		height = null;
		width = null;
		helpMessage = null;
		helpURL = null;
		toolTipText = null;
		mouseOutListeners = null;
		mouseOverListeners = null;
		disabled = null;
		unlockedClientAttributeNames = null;
		tabIndex = null;
		lookId = null;
		x = null;
		y = null;
		blurListeners = null;
		focusListeners = null;
		marginBottom = null;
		marginLeft = null;
		marginRight = null;
		marginTop = null;
		backgroundColor = null;
		foregroundColor = null;
		textAlignment = null;
		styleClass = null;
		userEventListeners = null;
		propertyChangeListeners = null;
		fontBold = null;
		fontItalic = null;
		fontName = null;
		fontSize = null;
		fontUnderline = null;
		accessKey = null;
		initListeners = null;
		margins = null;
		immediate = null;
		value = null;

		super.release();
	}

}
