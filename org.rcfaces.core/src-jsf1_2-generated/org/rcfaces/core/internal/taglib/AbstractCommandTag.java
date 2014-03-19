package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.component.AbstractCommandComponent;
import javax.faces.context.FacesContext;

public abstract class AbstractCommandTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AbstractCommandTag.class);

	private ValueExpression helpMessage;
	private ValueExpression helpURL;
	private ValueExpression toolTipText;
	private ValueExpression blurListeners;
	private ValueExpression focusListeners;
	private ValueExpression backgroundColor;
	private ValueExpression foregroundColor;
	private ValueExpression visible;
	private ValueExpression errorListeners;
	private ValueExpression disabled;
	private ValueExpression validationListeners;
	private ValueExpression fontBold;
	private ValueExpression fontItalic;
	private ValueExpression fontName;
	private ValueExpression fontSize;
	private ValueExpression fontUnderline;
	private ValueExpression mouseOutListeners;
	private ValueExpression mouseOverListeners;
	private ValueExpression tabIndex;
	private ValueExpression unlockedClientAttributeNames;
	private ValueExpression propertyChangeListeners;
	private ValueExpression marginBottom;
	private ValueExpression marginLeft;
	private ValueExpression marginRight;
	private ValueExpression marginTop;
	private ValueExpression userEventListeners;
	private ValueExpression x;
	private ValueExpression y;
	private ValueExpression partialRendering;
	private ValueExpression styleClass;
	private ValueExpression lookId;
	private ValueExpression width;
	private ValueExpression height;
	private ValueExpression textAlignment;
	private ValueExpression accessKey;
	private ValueExpression ariaLabel;
	private ValueExpression ariaLevel;
	private ValueExpression waiRole;
<<<<<<< HEAD
	private ValueExpression bottomPosition;
	private ValueExpression leftPosition;
	private ValueExpression rightPosition;
	private ValueExpression topPosition;
=======
	private ValueExpression bottom;
	private ValueExpression horizontalCenter;
	private ValueExpression left;
	private ValueExpression right;
	private ValueExpression top;
	private ValueExpression verticalCenter;
>>>>>>> refs/remotes/origin/BRELEASE_1-2-0
	private ValueExpression initListeners;
	private ValueExpression hiddenMode;
	private ValueExpression margins;
	private ValueExpression immediate;
	private ValueExpression value;
	public void setHelpMessage(ValueExpression helpMessage) {
		this.helpMessage = helpMessage;
	}

	public void setHelpURL(ValueExpression helpURL) {
		this.helpURL = helpURL;
	}

	public void setToolTipText(ValueExpression toolTipText) {
		this.toolTipText = toolTipText;
	}

	public void setBlurListener(ValueExpression blurListeners) {
		this.blurListeners = blurListeners;
	}

	public void setFocusListener(ValueExpression focusListeners) {
		this.focusListeners = focusListeners;
	}

	public void setBackgroundColor(ValueExpression backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setForegroundColor(ValueExpression foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public void setVisible(ValueExpression visible) {
		this.visible = visible;
	}

	public void setErrorListener(ValueExpression errorListeners) {
		this.errorListeners = errorListeners;
	}

	public void setDisabled(ValueExpression disabled) {
		this.disabled = disabled;
	}

	public void setValidationListener(ValueExpression validationListeners) {
		this.validationListeners = validationListeners;
	}

	public void setFontBold(ValueExpression fontBold) {
		this.fontBold = fontBold;
	}

	public void setFontItalic(ValueExpression fontItalic) {
		this.fontItalic = fontItalic;
	}

	public void setFontName(ValueExpression fontName) {
		this.fontName = fontName;
	}

	public void setFontSize(ValueExpression fontSize) {
		this.fontSize = fontSize;
	}

	public void setFontUnderline(ValueExpression fontUnderline) {
		this.fontUnderline = fontUnderline;
	}

	public void setMouseOutListener(ValueExpression mouseOutListeners) {
		this.mouseOutListeners = mouseOutListeners;
	}

	public void setMouseOverListener(ValueExpression mouseOverListeners) {
		this.mouseOverListeners = mouseOverListeners;
	}

	public void setTabIndex(ValueExpression tabIndex) {
		this.tabIndex = tabIndex;
	}

	public void setUnlockedClientAttributeNames(ValueExpression unlockedClientAttributeNames) {
		this.unlockedClientAttributeNames = unlockedClientAttributeNames;
	}

	public void setPropertyChangeListener(ValueExpression propertyChangeListeners) {
		this.propertyChangeListeners = propertyChangeListeners;
	}

	public void setMarginBottom(ValueExpression marginBottom) {
		this.marginBottom = marginBottom;
	}

	public void setMarginLeft(ValueExpression marginLeft) {
		this.marginLeft = marginLeft;
	}

	public void setMarginRight(ValueExpression marginRight) {
		this.marginRight = marginRight;
	}

	public void setMarginTop(ValueExpression marginTop) {
		this.marginTop = marginTop;
	}

	public void setUserEventListener(ValueExpression userEventListeners) {
		this.userEventListeners = userEventListeners;
	}

	public void setX(ValueExpression x) {
		this.x = x;
	}

	public void setY(ValueExpression y) {
		this.y = y;
	}

	public void setPartialRendering(ValueExpression partialRendering) {
		this.partialRendering = partialRendering;
	}

	public void setStyleClass(ValueExpression styleClass) {
		this.styleClass = styleClass;
	}

	public void setLookId(ValueExpression lookId) {
		this.lookId = lookId;
	}

	public void setWidth(ValueExpression width) {
		this.width = width;
	}

	public void setHeight(ValueExpression height) {
		this.height = height;
	}

	public void setTextAlignment(ValueExpression textAlignment) {
		this.textAlignment = textAlignment;
	}

	public void setAccessKey(ValueExpression accessKey) {
		this.accessKey = accessKey;
	}

	public void setAriaLabel(ValueExpression ariaLabel) {
		this.ariaLabel = ariaLabel;
	}

	public void setAriaLevel(ValueExpression ariaLevel) {
		this.ariaLevel = ariaLevel;
	}

	public void setWaiRole(ValueExpression waiRole) {
		this.waiRole = waiRole;
	}

<<<<<<< HEAD
	public final void setBottomPosition(ValueExpression bottomPosition) {
		this.bottomPosition = bottomPosition;
	}

	public final void setLeftPosition(ValueExpression leftPosition) {
		this.leftPosition = leftPosition;
	}

	public final void setRightPosition(ValueExpression rightPosition) {
		this.rightPosition = rightPosition;
	}

	public final void setTopPosition(ValueExpression topPosition) {
		this.topPosition = topPosition;
	}

	public final void setInitListener(ValueExpression initListeners) {
=======
	public void setBottom(ValueExpression bottom) {
		this.bottom = bottom;
	}

	public void setHorizontalCenter(ValueExpression horizontalCenter) {
		this.horizontalCenter = horizontalCenter;
	}

	public void setLeft(ValueExpression left) {
		this.left = left;
	}

	public void setRight(ValueExpression right) {
		this.right = right;
	}

	public void setTop(ValueExpression top) {
		this.top = top;
	}

	public void setVerticalCenter(ValueExpression verticalCenter) {
		this.verticalCenter = verticalCenter;
	}

	public void setInitListener(ValueExpression initListeners) {
>>>>>>> refs/remotes/origin/BRELEASE_1-2-0
		this.initListeners = initListeners;
	}

	public void setHiddenMode(ValueExpression hiddenMode) {
		this.hiddenMode = hiddenMode;
	}

	public void setMargins(ValueExpression margins) {
		this.margins = margins;
	}

	public final void setImmediate(ValueExpression immediate) {
		this.immediate = immediate;
	}

	public final void setValue(ValueExpression value) {
		this.value = value;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  helpMessage='"+helpMessage+"'");
			LOG.debug("  helpURL='"+helpURL+"'");
			LOG.debug("  toolTipText='"+toolTipText+"'");
			LOG.debug("  backgroundColor='"+backgroundColor+"'");
			LOG.debug("  foregroundColor='"+foregroundColor+"'");
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  disabled='"+disabled+"'");
			LOG.debug("  fontBold='"+fontBold+"'");
			LOG.debug("  fontItalic='"+fontItalic+"'");
			LOG.debug("  fontName='"+fontName+"'");
			LOG.debug("  fontSize='"+fontSize+"'");
			LOG.debug("  fontUnderline='"+fontUnderline+"'");
			LOG.debug("  tabIndex='"+tabIndex+"'");
			LOG.debug("  unlockedClientAttributeNames='"+unlockedClientAttributeNames+"'");
			LOG.debug("  marginBottom='"+marginBottom+"'");
			LOG.debug("  marginLeft='"+marginLeft+"'");
			LOG.debug("  marginRight='"+marginRight+"'");
			LOG.debug("  marginTop='"+marginTop+"'");
			LOG.debug("  x='"+x+"'");
			LOG.debug("  y='"+y+"'");
			LOG.debug("  partialRendering='"+partialRendering+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  lookId='"+lookId+"'");
			LOG.debug("  width='"+width+"'");
			LOG.debug("  height='"+height+"'");
			LOG.debug("  textAlignment='"+textAlignment+"'");
			LOG.debug("  accessKey='"+accessKey+"'");
			LOG.debug("  ariaLabel='"+ariaLabel+"'");
			LOG.debug("  ariaLevel='"+ariaLevel+"'");
			LOG.debug("  waiRole='"+waiRole+"'");
<<<<<<< HEAD
			LOG.debug("  bottomPosition='"+bottomPosition+"'");
			LOG.debug("  leftPosition='"+leftPosition+"'");
			LOG.debug("  rightPosition='"+rightPosition+"'");
			LOG.debug("  topPosition='"+topPosition+"'");
=======
			LOG.debug("  bottom='"+bottom+"'");
			LOG.debug("  horizontalCenter='"+horizontalCenter+"'");
			LOG.debug("  left='"+left+"'");
			LOG.debug("  right='"+right+"'");
			LOG.debug("  top='"+top+"'");
			LOG.debug("  verticalCenter='"+verticalCenter+"'");
>>>>>>> refs/remotes/origin/BRELEASE_1-2-0
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  margins='"+margins+"'");
		}
		if ((uiComponent instanceof AbstractCommandComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractCommandComponent'.");
		}

		super.setProperties(uiComponent);

		AbstractCommandComponent component = (AbstractCommandComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

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

		if (blurListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.BLUR_LISTENER_TYPE, blurListeners);
		}

		if (focusListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.FOCUS_LISTENER_TYPE, focusListeners);
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

		if (visible != null) {
			if (visible.isLiteralText()==false) {
				component.setValueExpression(Properties.VISIBLE, visible);

			} else {
				component.setVisible(getBool(visible.getExpressionString()));
			}
		}

		if (errorListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.ERROR_LISTENER_TYPE, errorListeners);
		}

		if (disabled != null) {
			if (disabled.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLED, disabled);

			} else {
				component.setDisabled(getBool(disabled.getExpressionString()));
			}
		}

		if (validationListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.VALIDATION_LISTENER_TYPE, validationListeners);
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

		if (mouseOutListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
		}

		if (mouseOverListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MOUSE_OVER_LISTENER_TYPE, mouseOverListeners);
		}

		if (tabIndex != null) {
			if (tabIndex.isLiteralText()==false) {
				component.setValueExpression(Properties.TAB_INDEX, tabIndex);

			} else {
				component.setTabIndex(getInteger(tabIndex.getExpressionString()));
			}
		}

		if (unlockedClientAttributeNames != null) {
			if (unlockedClientAttributeNames.isLiteralText()==false) {
				component.setValueExpression(Properties.UNLOCKED_CLIENT_ATTRIBUTE_NAMES, unlockedClientAttributeNames);

			} else {
				component.setUnlockedClientAttributeNames(unlockedClientAttributeNames.getExpressionString());
			}
		}

		if (propertyChangeListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.PROPERTY_CHANGE_LISTENER_TYPE, propertyChangeListeners);
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

		if (userEventListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.USER_EVENT_LISTENER_TYPE, userEventListeners);
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

		if (partialRendering != null) {
			if (partialRendering.isLiteralText()==false) {
				component.setValueExpression(Properties.PARTIAL_RENDERING, partialRendering);

			} else {
				component.setPartialRendering(getBool(partialRendering.getExpressionString()));
			}
		}

		if (styleClass != null) {
			if (styleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.STYLE_CLASS, styleClass);

			} else {
				component.setStyleClass(styleClass.getExpressionString());
			}
		}

		if (lookId != null) {
			if (lookId.isLiteralText()==false) {
				component.setValueExpression(Properties.LOOK_ID, lookId);

			} else {
				component.setLookId(lookId.getExpressionString());
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

		if (textAlignment != null) {
			if (textAlignment.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_ALIGNMENT, textAlignment);

			} else {
				component.setTextAlignment(textAlignment.getExpressionString());
			}
		}

		if (accessKey != null) {
			if (accessKey.isLiteralText()==false) {
				component.setValueExpression(Properties.ACCESS_KEY, accessKey);

			} else {
				component.setAccessKey(accessKey.getExpressionString());
			}
		}

		if (ariaLabel != null) {
			if (ariaLabel.isLiteralText()==false) {
				component.setValueExpression(Properties.ARIA_LABEL, ariaLabel);

			} else {
				component.setAriaLabel(ariaLabel.getExpressionString());
			}
		}

		if (ariaLevel != null) {
			if (ariaLevel.isLiteralText()==false) {
				component.setValueExpression(Properties.ARIA_LEVEL, ariaLevel);

			} else {
				component.setAriaLevel(getInt(ariaLevel.getExpressionString()));
			}
		}

		if (waiRole != null) {
			if (waiRole.isLiteralText()==false) {
				component.setValueExpression(Properties.WAI_ROLE, waiRole);

			} else {
				component.setWaiRole(waiRole.getExpressionString());
			}
		}

<<<<<<< HEAD
		if (bottomPosition != null) {
			if (bottomPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.BOTTOM_POSITION, bottomPosition);

			} else {
				component.setBottomPosition(getInt(bottomPosition.getExpressionString()));
			}
		}

		if (leftPosition != null) {
			if (leftPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.LEFT_POSITION, leftPosition);

			} else {
				component.setLeftPosition(getInt(leftPosition.getExpressionString()));
			}
		}

		if (rightPosition != null) {
			if (rightPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.RIGHT_POSITION, rightPosition);

			} else {
				component.setRightPosition(getInt(rightPosition.getExpressionString()));
			}
		}

		if (topPosition != null) {
			if (topPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.TOP_POSITION, topPosition);

			} else {
				component.setTopPosition(getInt(topPosition.getExpressionString()));
=======
		if (bottom != null) {
			if (bottom.isLiteralText()==false) {
				component.setValueExpression(Properties.BOTTOM, bottom);

			} else {
				component.setBottom(getNumber(bottom.getExpressionString()));
			}
		}

		if (horizontalCenter != null) {
			if (horizontalCenter.isLiteralText()==false) {
				component.setValueExpression(Properties.HORIZONTAL_CENTER, horizontalCenter);

			} else {
				component.setHorizontalCenter(getNumber(horizontalCenter.getExpressionString()));
			}
		}

		if (left != null) {
			if (left.isLiteralText()==false) {
				component.setValueExpression(Properties.LEFT, left);

			} else {
				component.setLeft(getNumber(left.getExpressionString()));
			}
		}

		if (right != null) {
			if (right.isLiteralText()==false) {
				component.setValueExpression(Properties.RIGHT, right);

			} else {
				component.setRight(getNumber(right.getExpressionString()));
			}
		}

		if (top != null) {
			if (top.isLiteralText()==false) {
				component.setValueExpression(Properties.TOP, top);

			} else {
				component.setTop(getNumber(top.getExpressionString()));
			}
		}

		if (verticalCenter != null) {
			if (verticalCenter.isLiteralText()==false) {
				component.setValueExpression(Properties.VERTICAL_CENTER, verticalCenter);

			} else {
				component.setVerticalCenter(getNumber(verticalCenter.getExpressionString()));
>>>>>>> refs/remotes/origin/BRELEASE_1-2-0
			}
		}

		if (initListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.INIT_LISTENER_TYPE, initListeners);
		}

		if (hiddenMode != null) {
			if (hiddenMode.isLiteralText()==false) {
				component.setValueExpression(Properties.HIDDEN_MODE, hiddenMode);

			} else {
				component.setHiddenMode(hiddenMode.getExpressionString());
			}
		}

		if (margins != null) {
			if (margins.isLiteralText()==false) {
				throw new javax.faces.FacesException("Attribute 'margins' does not accept binding !");
			}
				component.setMargins(margins.getExpressionString());
		}

		if (immediate != null) {
			if (immediate.isLiteralText()==false) {
				component.setValueExpression(Properties.IMMEDIATE, immediate);

			} else {
				component.setImmediate(getBool(immediate.getExpressionString()));
			}
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
		helpMessage = null;
		helpURL = null;
		toolTipText = null;
		blurListeners = null;
		focusListeners = null;
		backgroundColor = null;
		foregroundColor = null;
		visible = null;
		errorListeners = null;
		disabled = null;
		validationListeners = null;
		fontBold = null;
		fontItalic = null;
		fontName = null;
		fontSize = null;
		fontUnderline = null;
		mouseOutListeners = null;
		mouseOverListeners = null;
		tabIndex = null;
		unlockedClientAttributeNames = null;
		propertyChangeListeners = null;
		marginBottom = null;
		marginLeft = null;
		marginRight = null;
		marginTop = null;
		userEventListeners = null;
		x = null;
		y = null;
		partialRendering = null;
		styleClass = null;
		lookId = null;
		width = null;
		height = null;
		textAlignment = null;
		accessKey = null;
		ariaLabel = null;
		ariaLevel = null;
		waiRole = null;
<<<<<<< HEAD
		bottomPosition = null;
		leftPosition = null;
		rightPosition = null;
		topPosition = null;
=======
		bottom = null;
		horizontalCenter = null;
		left = null;
		right = null;
		top = null;
		verticalCenter = null;
>>>>>>> refs/remotes/origin/BRELEASE_1-2-0
		initListeners = null;
		hiddenMode = null;
		margins = null;
		immediate = null;
		value = null;

		super.release();
	}

}
