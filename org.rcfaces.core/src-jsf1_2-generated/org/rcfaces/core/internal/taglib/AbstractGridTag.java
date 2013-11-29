package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.AbstractGridComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public abstract class AbstractGridTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AbstractGridTag.class);

	private ValueExpression helpMessage;
	private ValueExpression helpURL;
	private ValueExpression toolTipText;
	private ValueExpression blurListeners;
	private ValueExpression focusListeners;
	private ValueExpression backgroundColor;
	private ValueExpression foregroundColor;
	private ValueExpression visible;
	private ValueExpression errorListeners;
	private ValueExpression sortManager;
	private ValueExpression mouseOutListeners;
	private ValueExpression mouseOverListeners;
	private ValueExpression tabIndex;
	private ValueExpression unlockedClientAttributeNames;
	private ValueExpression propertyChangeListeners;
	private ValueExpression marginBottom;
	private ValueExpression marginLeft;
	private ValueExpression marginRight;
	private ValueExpression marginTop;
	private ValueExpression keyUpListeners;
	private ValueExpression keyDownListeners;
	private ValueExpression keyPressListeners;
	private ValueExpression resetListeners;
	private ValueExpression userEventListeners;
	private ValueExpression x;
	private ValueExpression y;
	private ValueExpression partialRendering;
	private ValueExpression styleClass;
	private ValueExpression lookId;
	private ValueExpression width;
	private ValueExpression height;
	private ValueExpression ariaLabel;
	private ValueExpression ariaLevel;
	private ValueExpression waiRole;
	private ValueExpression bottom;
	private ValueExpression horizontalCenter;
	private ValueExpression left;
	private ValueExpression right;
	private ValueExpression top;
	private ValueExpression verticalCenter;
	private ValueExpression initListeners;
	private ValueExpression hiddenMode;
	private ValueExpression margins;
	private ValueExpression var;
	private ValueExpression rows;
	private ValueExpression first;
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

	public void setSortManager(ValueExpression sortManager) {
		this.sortManager = sortManager;
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

	public void setKeyUpListener(ValueExpression keyUpListeners) {
		this.keyUpListeners = keyUpListeners;
	}

	public void setKeyDownListener(ValueExpression keyDownListeners) {
		this.keyDownListeners = keyDownListeners;
	}

	public void setKeyPressListener(ValueExpression keyPressListeners) {
		this.keyPressListeners = keyPressListeners;
	}

	public void setResetListener(ValueExpression resetListeners) {
		this.resetListeners = resetListeners;
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

	public void setAriaLabel(ValueExpression ariaLabel) {
		this.ariaLabel = ariaLabel;
	}

	public void setAriaLevel(ValueExpression ariaLevel) {
		this.ariaLevel = ariaLevel;
	}

	public void setWaiRole(ValueExpression waiRole) {
		this.waiRole = waiRole;
	}

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
		this.initListeners = initListeners;
	}

	public void setHiddenMode(ValueExpression hiddenMode) {
		this.hiddenMode = hiddenMode;
	}

	public void setMargins(ValueExpression margins) {
		this.margins = margins;
	}

	public void setVar(ValueExpression var) {
		this.var = var;
	}

	public void setRows(ValueExpression rows) {
		this.rows = rows;
	}

	public void setFirst(ValueExpression first) {
		this.first = first;
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
			LOG.debug("  sortManager='"+sortManager+"'");
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
			LOG.debug("  ariaLabel='"+ariaLabel+"'");
			LOG.debug("  ariaLevel='"+ariaLevel+"'");
			LOG.debug("  waiRole='"+waiRole+"'");
			LOG.debug("  bottom='"+bottom+"'");
			LOG.debug("  horizontalCenter='"+horizontalCenter+"'");
			LOG.debug("  left='"+left+"'");
			LOG.debug("  right='"+right+"'");
			LOG.debug("  top='"+top+"'");
			LOG.debug("  verticalCenter='"+verticalCenter+"'");
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  margins='"+margins+"'");
			LOG.debug("  var='"+var+"'");
			LOG.debug("  rows='"+rows+"'");
			LOG.debug("  first='"+first+"'");
		}
		if ((uiComponent instanceof AbstractGridComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractGridComponent'.");
		}

		super.setProperties(uiComponent);

		AbstractGridComponent component = (AbstractGridComponent) uiComponent;
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

		if (sortManager != null) {
			if (sortManager.isLiteralText()==false) {
				component.setValueExpression(Properties.SORT_MANAGER, sortManager);

			} else {
				component.setSortManager(sortManager.getExpressionString());
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

		if (keyUpListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.KEY_UP_LISTENER_TYPE, keyUpListeners);
		}

		if (keyDownListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.KEY_DOWN_LISTENER_TYPE, keyDownListeners);
		}

		if (keyPressListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.KEY_PRESS_LISTENER_TYPE, keyPressListeners);
		}

		if (resetListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.RESET_LISTENER_TYPE, resetListeners);
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

		if (var != null) {
			if (var.isLiteralText()==false) {
				component.setValueExpression(Properties.VAR, var);

			} else {
				component.setVar(var.getExpressionString());
			}
		}

		if (rows != null) {
			if (rows.isLiteralText()==false) {
				component.setValueExpression(Properties.ROWS, rows);

			} else {
				component.setRows(getInt(rows.getExpressionString()));
			}
		}

		if (first != null) {
			if (first.isLiteralText()==false) {
				component.setValueExpression(Properties.FIRST, first);

			} else {
				component.setFirst(getInt(first.getExpressionString()));
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
		sortManager = null;
		mouseOutListeners = null;
		mouseOverListeners = null;
		tabIndex = null;
		unlockedClientAttributeNames = null;
		propertyChangeListeners = null;
		marginBottom = null;
		marginLeft = null;
		marginRight = null;
		marginTop = null;
		keyUpListeners = null;
		keyDownListeners = null;
		keyPressListeners = null;
		resetListeners = null;
		userEventListeners = null;
		x = null;
		y = null;
		partialRendering = null;
		styleClass = null;
		lookId = null;
		width = null;
		height = null;
		ariaLabel = null;
		ariaLevel = null;
		waiRole = null;
		bottom = null;
		horizontalCenter = null;
		left = null;
		right = null;
		top = null;
		verticalCenter = null;
		initListeners = null;
		hiddenMode = null;
		margins = null;
		var = null;
		rows = null;
		first = null;
		value = null;

		super.release();
	}

}
