package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.AbstractSelectManyComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public abstract class AbstractSelectManyTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AbstractSelectManyTag.class);

	private ValueExpression visible;
	private ValueExpression width;
	private ValueExpression height;
	private ValueExpression mouseOutListeners;
	private ValueExpression mouseOverListeners;
	private ValueExpression disabled;
	private ValueExpression unlockedClientAttributeNames;
	private ValueExpression blurListeners;
	private ValueExpression focusListeners;
	private ValueExpression errorListeners;
	private ValueExpression backgroundColor;
	private ValueExpression foregroundColor;
	private ValueExpression keyPressListeners;
	private ValueExpression keyDownListeners;
	private ValueExpression keyUpListeners;
	private ValueExpression styleClass;
	private ValueExpression fontBold;
	private ValueExpression fontItalic;
	private ValueExpression fontName;
	private ValueExpression fontSize;
	private ValueExpression fontUnderline;
	private ValueExpression initListeners;
	private ValueExpression helpMessage;
	private ValueExpression helpURL;
	private ValueExpression toolTipText;
	private ValueExpression valueLocked;
	private ValueExpression tabIndex;
	private ValueExpression lookId;
	private ValueExpression x;
	private ValueExpression y;
	private ValueExpression validationListeners;
	private ValueExpression marginBottom;
	private ValueExpression marginLeft;
	private ValueExpression marginRight;
	private ValueExpression marginTop;
	private ValueExpression textAlignment;
	private ValueExpression immediate;
	private ValueExpression userEventListeners;
	private ValueExpression hiddenMode;
	private ValueExpression waiRole;
	private ValueExpression propertyChangeListeners;
	private ValueExpression accessKey;
	private ValueExpression margins;
	private ValueExpression value;
	public final void setVisible(ValueExpression visible) {
		this.visible = visible;
	}

	public final void setWidth(ValueExpression width) {
		this.width = width;
	}

	public final void setHeight(ValueExpression height) {
		this.height = height;
	}

	public final void setMouseOutListener(ValueExpression mouseOutListeners) {
		this.mouseOutListeners = mouseOutListeners;
	}

	public final void setMouseOverListener(ValueExpression mouseOverListeners) {
		this.mouseOverListeners = mouseOverListeners;
	}

	public final void setDisabled(ValueExpression disabled) {
		this.disabled = disabled;
	}

	public final void setUnlockedClientAttributeNames(ValueExpression unlockedClientAttributeNames) {
		this.unlockedClientAttributeNames = unlockedClientAttributeNames;
	}

	public final void setBlurListener(ValueExpression blurListeners) {
		this.blurListeners = blurListeners;
	}

	public final void setFocusListener(ValueExpression focusListeners) {
		this.focusListeners = focusListeners;
	}

	public final void setErrorListener(ValueExpression errorListeners) {
		this.errorListeners = errorListeners;
	}

	public final void setBackgroundColor(ValueExpression backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public final void setForegroundColor(ValueExpression foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public final void setKeyPressListener(ValueExpression keyPressListeners) {
		this.keyPressListeners = keyPressListeners;
	}

	public final void setKeyDownListener(ValueExpression keyDownListeners) {
		this.keyDownListeners = keyDownListeners;
	}

	public final void setKeyUpListener(ValueExpression keyUpListeners) {
		this.keyUpListeners = keyUpListeners;
	}

	public final void setStyleClass(ValueExpression styleClass) {
		this.styleClass = styleClass;
	}

	public final void setFontBold(ValueExpression fontBold) {
		this.fontBold = fontBold;
	}

	public final void setFontItalic(ValueExpression fontItalic) {
		this.fontItalic = fontItalic;
	}

	public final void setFontName(ValueExpression fontName) {
		this.fontName = fontName;
	}

	public final void setFontSize(ValueExpression fontSize) {
		this.fontSize = fontSize;
	}

	public final void setFontUnderline(ValueExpression fontUnderline) {
		this.fontUnderline = fontUnderline;
	}

	public final void setInitListener(ValueExpression initListeners) {
		this.initListeners = initListeners;
	}

	public final void setHelpMessage(ValueExpression helpMessage) {
		this.helpMessage = helpMessage;
	}

	public final void setHelpURL(ValueExpression helpURL) {
		this.helpURL = helpURL;
	}

	public final void setToolTipText(ValueExpression toolTipText) {
		this.toolTipText = toolTipText;
	}

	public final void setValueLocked(ValueExpression valueLocked) {
		this.valueLocked = valueLocked;
	}

	public final void setTabIndex(ValueExpression tabIndex) {
		this.tabIndex = tabIndex;
	}

	public final void setLookId(ValueExpression lookId) {
		this.lookId = lookId;
	}

	public final void setX(ValueExpression x) {
		this.x = x;
	}

	public final void setY(ValueExpression y) {
		this.y = y;
	}

	public final void setValidationListener(ValueExpression validationListeners) {
		this.validationListeners = validationListeners;
	}

	public final void setMarginBottom(ValueExpression marginBottom) {
		this.marginBottom = marginBottom;
	}

	public final void setMarginLeft(ValueExpression marginLeft) {
		this.marginLeft = marginLeft;
	}

	public final void setMarginRight(ValueExpression marginRight) {
		this.marginRight = marginRight;
	}

	public final void setMarginTop(ValueExpression marginTop) {
		this.marginTop = marginTop;
	}

	public final void setTextAlignment(ValueExpression textAlignment) {
		this.textAlignment = textAlignment;
	}

	public final void setImmediate(ValueExpression immediate) {
		this.immediate = immediate;
	}

	public final void setUserEventListener(ValueExpression userEventListeners) {
		this.userEventListeners = userEventListeners;
	}

	public final void setHiddenMode(ValueExpression hiddenMode) {
		this.hiddenMode = hiddenMode;
	}

	public final void setWaiRole(ValueExpression waiRole) {
		this.waiRole = waiRole;
	}

	public final void setPropertyChangeListener(ValueExpression propertyChangeListeners) {
		this.propertyChangeListeners = propertyChangeListeners;
	}

	public final void setAccessKey(ValueExpression accessKey) {
		this.accessKey = accessKey;
	}

	public final void setMargins(ValueExpression margins) {
		this.margins = margins;
	}

	public final void setValue(ValueExpression value) {
		this.value = value;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  width='"+width+"'");
			LOG.debug("  height='"+height+"'");
			LOG.debug("  disabled='"+disabled+"'");
			LOG.debug("  unlockedClientAttributeNames='"+unlockedClientAttributeNames+"'");
			LOG.debug("  backgroundColor='"+backgroundColor+"'");
			LOG.debug("  foregroundColor='"+foregroundColor+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  fontBold='"+fontBold+"'");
			LOG.debug("  fontItalic='"+fontItalic+"'");
			LOG.debug("  fontName='"+fontName+"'");
			LOG.debug("  fontSize='"+fontSize+"'");
			LOG.debug("  fontUnderline='"+fontUnderline+"'");
			LOG.debug("  helpMessage='"+helpMessage+"'");
			LOG.debug("  helpURL='"+helpURL+"'");
			LOG.debug("  toolTipText='"+toolTipText+"'");
			LOG.debug("  valueLocked='"+valueLocked+"'");
			LOG.debug("  tabIndex='"+tabIndex+"'");
			LOG.debug("  lookId='"+lookId+"'");
			LOG.debug("  x='"+x+"'");
			LOG.debug("  y='"+y+"'");
			LOG.debug("  marginBottom='"+marginBottom+"'");
			LOG.debug("  marginLeft='"+marginLeft+"'");
			LOG.debug("  marginRight='"+marginRight+"'");
			LOG.debug("  marginTop='"+marginTop+"'");
			LOG.debug("  textAlignment='"+textAlignment+"'");
			LOG.debug("  immediate='"+immediate+"'");
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  waiRole='"+waiRole+"'");
			LOG.debug("  accessKey='"+accessKey+"'");
			LOG.debug("  margins='"+margins+"'");
		}
		if ((uiComponent instanceof AbstractSelectManyComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractSelectManyComponent'.");
		}

		super.setProperties(uiComponent);

		AbstractSelectManyComponent component = (AbstractSelectManyComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (visible != null) {
			if (visible.isLiteralText()==false) {
				component.setValueExpression(Properties.VISIBLE, visible);

			} else {
				component.setVisible(getBool(visible.getExpressionString()));
			}
		}

		if (width != null) {
			if (width.isLiteralText()==false) {
				component.setValueExpression(Properties.WIDTH, width);

			} else {
				component.setWidth(width.getExpressionString());
			}
		}

		if (height != null) {
			if (height.isLiteralText()==false) {
				component.setValueExpression(Properties.HEIGHT, height);

			} else {
				component.setHeight(height.getExpressionString());
			}
		}

		if (mouseOutListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
		}

		if (mouseOverListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MOUSE_OVER_LISTENER_TYPE, mouseOverListeners);
		}

		if (disabled != null) {
			if (disabled.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLED, disabled);

			} else {
				component.setDisabled(getBool(disabled.getExpressionString()));
			}
		}

		if (unlockedClientAttributeNames != null) {
			if (unlockedClientAttributeNames.isLiteralText()==false) {
				component.setValueExpression(Properties.UNLOCKED_CLIENT_ATTRIBUTE_NAMES, unlockedClientAttributeNames);

			} else {
				component.setUnlockedClientAttributeNames(unlockedClientAttributeNames.getExpressionString());
			}
		}

		if (blurListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.BLUR_LISTENER_TYPE, blurListeners);
		}

		if (focusListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.FOCUS_LISTENER_TYPE, focusListeners);
		}

		if (errorListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.ERROR_LISTENER_TYPE, errorListeners);
		}

		if (backgroundColor != null) {
			if (backgroundColor.isLiteralText()==false) {
				component.setValueExpression(Properties.BACKGROUND_COLOR, backgroundColor);

			} else {
				component.setBackgroundColor(backgroundColor.getExpressionString());
			}
		}

		if (foregroundColor != null) {
			if (foregroundColor.isLiteralText()==false) {
				component.setValueExpression(Properties.FOREGROUND_COLOR, foregroundColor);

			} else {
				component.setForegroundColor(foregroundColor.getExpressionString());
			}
		}

		if (keyPressListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.KEY_PRESS_LISTENER_TYPE, keyPressListeners);
		}

		if (keyDownListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.KEY_DOWN_LISTENER_TYPE, keyDownListeners);
		}

		if (keyUpListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.KEY_UP_LISTENER_TYPE, keyUpListeners);
		}

		if (styleClass != null) {
			if (styleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.STYLE_CLASS, styleClass);

			} else {
				component.setStyleClass(styleClass.getExpressionString());
			}
		}

		if (fontBold != null) {
			if (fontBold.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_BOLD, fontBold);

			} else {
				component.setFontBold(getBoolean(fontBold.getExpressionString()));
			}
		}

		if (fontItalic != null) {
			if (fontItalic.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_ITALIC, fontItalic);

			} else {
				component.setFontItalic(getBoolean(fontItalic.getExpressionString()));
			}
		}

		if (fontName != null) {
			if (fontName.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_NAME, fontName);

			} else {
				component.setFontName(fontName.getExpressionString());
			}
		}

		if (fontSize != null) {
			if (fontSize.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_SIZE, fontSize);

			} else {
				component.setFontSize(fontSize.getExpressionString());
			}
		}

		if (fontUnderline != null) {
			if (fontUnderline.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_UNDERLINE, fontUnderline);

			} else {
				component.setFontUnderline(getBoolean(fontUnderline.getExpressionString()));
			}
		}

		if (initListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.INIT_LISTENER_TYPE, initListeners);
		}

		if (helpMessage != null) {
			if (helpMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.HELP_MESSAGE, helpMessage);

			} else {
				component.setHelpMessage(helpMessage.getExpressionString());
			}
		}

		if (helpURL != null) {
			if (helpURL.isLiteralText()==false) {
				component.setValueExpression(Properties.HELP_URL, helpURL);

			} else {
				component.setHelpURL(helpURL.getExpressionString());
			}
		}

		if (toolTipText != null) {
			if (toolTipText.isLiteralText()==false) {
				component.setValueExpression(Properties.TOOL_TIP_TEXT, toolTipText);

			} else {
				component.setToolTipText(toolTipText.getExpressionString());
			}
		}

		if (valueLocked != null) {
			if (valueLocked.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE_LOCKED, valueLocked);

			} else {
				component.setValueLocked(getBool(valueLocked.getExpressionString()));
			}
		}

		if (tabIndex != null) {
			if (tabIndex.isLiteralText()==false) {
				component.setValueExpression(Properties.TAB_INDEX, tabIndex);

			} else {
				component.setTabIndex(getInteger(tabIndex.getExpressionString()));
			}
		}

		if (lookId != null) {
			if (lookId.isLiteralText()==false) {
				component.setValueExpression(Properties.LOOK_ID, lookId);

			} else {
				component.setLookId(lookId.getExpressionString());
			}
		}

		if (x != null) {
			if (x.isLiteralText()==false) {
				component.setValueExpression(Properties.X, x);

			} else {
				component.setX(x.getExpressionString());
			}
		}

		if (y != null) {
			if (y.isLiteralText()==false) {
				component.setValueExpression(Properties.Y, y);

			} else {
				component.setY(y.getExpressionString());
			}
		}

		if (validationListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.VALIDATION_LISTENER_TYPE, validationListeners);
		}

		if (marginBottom != null) {
			if (marginBottom.isLiteralText()==false) {
				component.setValueExpression(Properties.MARGIN_BOTTOM, marginBottom);

			} else {
				component.setMarginBottom(marginBottom.getExpressionString());
			}
		}

		if (marginLeft != null) {
			if (marginLeft.isLiteralText()==false) {
				component.setValueExpression(Properties.MARGIN_LEFT, marginLeft);

			} else {
				component.setMarginLeft(marginLeft.getExpressionString());
			}
		}

		if (marginRight != null) {
			if (marginRight.isLiteralText()==false) {
				component.setValueExpression(Properties.MARGIN_RIGHT, marginRight);

			} else {
				component.setMarginRight(marginRight.getExpressionString());
			}
		}

		if (marginTop != null) {
			if (marginTop.isLiteralText()==false) {
				component.setValueExpression(Properties.MARGIN_TOP, marginTop);

			} else {
				component.setMarginTop(marginTop.getExpressionString());
			}
		}

		if (textAlignment != null) {
			if (textAlignment.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_ALIGNMENT, textAlignment);

			} else {
				component.setTextAlignment(textAlignment.getExpressionString());
			}
		}

		if (immediate != null) {
			if (immediate.isLiteralText()==false) {
				component.setValueExpression(Properties.IMMEDIATE, immediate);

			} else {
				component.setImmediate(getBool(immediate.getExpressionString()));
			}
		}

		if (userEventListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.USER_EVENT_LISTENER_TYPE, userEventListeners);
		}

		if (hiddenMode != null) {
			if (hiddenMode.isLiteralText()==false) {
				component.setValueExpression(Properties.HIDDEN_MODE, hiddenMode);

			} else {
				component.setHiddenMode(hiddenMode.getExpressionString());
			}
		}

		if (waiRole != null) {
			if (waiRole.isLiteralText()==false) {
				component.setValueExpression(Properties.WAI_ROLE, waiRole);

			} else {
				component.setWaiRole(waiRole.getExpressionString());
			}
		}

		if (propertyChangeListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.PROPERTY_CHANGE_LISTENER_TYPE, propertyChangeListeners);
		}

		if (accessKey != null) {
			if (accessKey.isLiteralText()==false) {
				component.setValueExpression(Properties.ACCESS_KEY, accessKey);

			} else {
				component.setAccessKey(accessKey.getExpressionString());
			}
		}

		if (margins != null) {
			if (margins.isLiteralText()==false) {
				throw new javax.faces.FacesException("Attribute 'margins' does not accept binding !");
			}
				component.setMargins(margins.getExpressionString());
		}

		if (value != null) {
			if (value.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE, value);

			} else {
				component.setValue(value.getExpressionString());
			}
		}
	}

	public void release() {
		visible = null;
		width = null;
		height = null;
		mouseOutListeners = null;
		mouseOverListeners = null;
		disabled = null;
		unlockedClientAttributeNames = null;
		blurListeners = null;
		focusListeners = null;
		errorListeners = null;
		backgroundColor = null;
		foregroundColor = null;
		keyPressListeners = null;
		keyDownListeners = null;
		keyUpListeners = null;
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
		validationListeners = null;
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

		super.release();
	}

}
