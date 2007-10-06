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

	private ValueExpression marginBottom;
	private ValueExpression marginLeft;
	private ValueExpression marginRight;
	private ValueExpression marginTop;
	private ValueExpression resetListeners;
	private ValueExpression keyUpListeners;
	private ValueExpression keyDownListeners;
	private ValueExpression keyPressListeners;
	private ValueExpression x;
	private ValueExpression y;
	private ValueExpression userEventListeners;
	private ValueExpression helpMessage;
	private ValueExpression helpURL;
	private ValueExpression toolTipText;
	private ValueExpression styleClass;
	private ValueExpression lookId;
	private ValueExpression width;
	private ValueExpression height;
	private ValueExpression blurListeners;
	private ValueExpression focusListeners;
	private ValueExpression backgroundColor;
	private ValueExpression foregroundColor;
	private ValueExpression visible;
	private ValueExpression errorListeners;
	private ValueExpression sortManager;
	private ValueExpression waiRole;
	private ValueExpression mouseOutListeners;
	private ValueExpression mouseOverListeners;
	private ValueExpression unlockedClientAttributeNames;
	private ValueExpression initListeners;
	private ValueExpression propertyChangeListeners;
	private ValueExpression hiddenMode;
	private ValueExpression first;
	private ValueExpression margins;
	private ValueExpression rows;
	private ValueExpression var;
	private ValueExpression value;
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

	public final void setResetListener(ValueExpression resetListeners) {
		this.resetListeners = resetListeners;
	}

	public final void setKeyUpListener(ValueExpression keyUpListeners) {
		this.keyUpListeners = keyUpListeners;
	}

	public final void setKeyDownListener(ValueExpression keyDownListeners) {
		this.keyDownListeners = keyDownListeners;
	}

	public final void setKeyPressListener(ValueExpression keyPressListeners) {
		this.keyPressListeners = keyPressListeners;
	}

	public final void setX(ValueExpression x) {
		this.x = x;
	}

	public final void setY(ValueExpression y) {
		this.y = y;
	}

	public final void setUserEventListener(ValueExpression userEventListeners) {
		this.userEventListeners = userEventListeners;
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

	public final void setStyleClass(ValueExpression styleClass) {
		this.styleClass = styleClass;
	}

	public final void setLookId(ValueExpression lookId) {
		this.lookId = lookId;
	}

	public final void setWidth(ValueExpression width) {
		this.width = width;
	}

	public final void setHeight(ValueExpression height) {
		this.height = height;
	}

	public final void setBlurListener(ValueExpression blurListeners) {
		this.blurListeners = blurListeners;
	}

	public final void setFocusListener(ValueExpression focusListeners) {
		this.focusListeners = focusListeners;
	}

	public final void setBackgroundColor(ValueExpression backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public final void setForegroundColor(ValueExpression foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public final void setVisible(ValueExpression visible) {
		this.visible = visible;
	}

	public final void setErrorListener(ValueExpression errorListeners) {
		this.errorListeners = errorListeners;
	}

	public final void setSortManager(ValueExpression sortManager) {
		this.sortManager = sortManager;
	}

	public final void setWaiRole(ValueExpression waiRole) {
		this.waiRole = waiRole;
	}

	public final void setMouseOutListener(ValueExpression mouseOutListeners) {
		this.mouseOutListeners = mouseOutListeners;
	}

	public final void setMouseOverListener(ValueExpression mouseOverListeners) {
		this.mouseOverListeners = mouseOverListeners;
	}

	public final void setUnlockedClientAttributeNames(ValueExpression unlockedClientAttributeNames) {
		this.unlockedClientAttributeNames = unlockedClientAttributeNames;
	}

	public final void setInitListener(ValueExpression initListeners) {
		this.initListeners = initListeners;
	}

	public final void setPropertyChangeListener(ValueExpression propertyChangeListeners) {
		this.propertyChangeListeners = propertyChangeListeners;
	}

	public final void setHiddenMode(ValueExpression hiddenMode) {
		this.hiddenMode = hiddenMode;
	}

	public final void setFirst(ValueExpression first) {
		this.first = first;
	}

	public final void setMargins(ValueExpression margins) {
		this.margins = margins;
	}

	public final void setRows(ValueExpression rows) {
		this.rows = rows;
	}

	public final void setVar(ValueExpression var) {
		this.var = var;
	}

	public final void setValue(ValueExpression value) {
		this.value = value;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  marginBottom='"+marginBottom+"'");
			LOG.debug("  marginLeft='"+marginLeft+"'");
			LOG.debug("  marginRight='"+marginRight+"'");
			LOG.debug("  marginTop='"+marginTop+"'");
			LOG.debug("  x='"+x+"'");
			LOG.debug("  y='"+y+"'");
			LOG.debug("  helpMessage='"+helpMessage+"'");
			LOG.debug("  helpURL='"+helpURL+"'");
			LOG.debug("  toolTipText='"+toolTipText+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  lookId='"+lookId+"'");
			LOG.debug("  width='"+width+"'");
			LOG.debug("  height='"+height+"'");
			LOG.debug("  backgroundColor='"+backgroundColor+"'");
			LOG.debug("  foregroundColor='"+foregroundColor+"'");
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  sortManager='"+sortManager+"'");
			LOG.debug("  waiRole='"+waiRole+"'");
			LOG.debug("  unlockedClientAttributeNames='"+unlockedClientAttributeNames+"'");
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  first='"+first+"'");
			LOG.debug("  margins='"+margins+"'");
			LOG.debug("  rows='"+rows+"'");
			LOG.debug("  var='"+var+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof AbstractGridComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractGridComponent'.");
		}

		AbstractGridComponent component = (AbstractGridComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

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

		if (resetListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.RESET_LISTENER_TYPE, resetListeners);
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

		if (userEventListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.USER_EVENT_LISTENER_TYPE, userEventListeners);
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

		if (waiRole != null) {
			if (waiRole.isLiteralText()==false) {
				component.setValueExpression(Properties.WAI_ROLE, waiRole);

			} else {
				component.setWaiRole(waiRole.getExpressionString());
			}
		}

		if (mouseOutListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
		}

		if (mouseOverListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MOUSE_OVER_LISTENER_TYPE, mouseOverListeners);
		}

		if (unlockedClientAttributeNames != null) {
			if (unlockedClientAttributeNames.isLiteralText()==false) {
				component.setValueExpression(Properties.UNLOCKED_CLIENT_ATTRIBUTE_NAMES, unlockedClientAttributeNames);

			} else {
				component.setUnlockedClientAttributeNames(unlockedClientAttributeNames.getExpressionString());
			}
		}

		if (initListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.INIT_LISTENER_TYPE, initListeners);
		}

		if (propertyChangeListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.PROPERTY_CHANGE_LISTENER_TYPE, propertyChangeListeners);
		}

		if (hiddenMode != null) {
			if (hiddenMode.isLiteralText()==false) {
				component.setValueExpression(Properties.HIDDEN_MODE, hiddenMode);

			} else {
				component.setHiddenMode(hiddenMode.getExpressionString());
			}
		}

		if (first != null) {
			if (first.isLiteralText()==false) {
				component.setValueExpression(Properties.FIRST, first);

			} else {
				component.setFirst(getInt(first.getExpressionString()));
			}
		}

		if (margins != null) {
			if (margins.isLiteralText()==false) {
				throw new javax.faces.FacesException("Attribute 'margins' does not accept binding !");
			}
				component.setMargins(margins.getExpressionString());
		}

		if (rows != null) {
			if (rows.isLiteralText()==false) {
				component.setValueExpression(Properties.ROWS, rows);

			} else {
				component.setRows(getInt(rows.getExpressionString()));
			}
		}

		if (var != null) {
			if (var.isLiteralText()==false) {
				component.setValueExpression(Properties.VAR, var);

			} else {
				component.setVar(var.getExpressionString());
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
		marginBottom = null;
		marginLeft = null;
		marginRight = null;
		marginTop = null;
		resetListeners = null;
		keyUpListeners = null;
		keyDownListeners = null;
		keyPressListeners = null;
		x = null;
		y = null;
		userEventListeners = null;
		helpMessage = null;
		helpURL = null;
		toolTipText = null;
		styleClass = null;
		lookId = null;
		width = null;
		height = null;
		blurListeners = null;
		focusListeners = null;
		backgroundColor = null;
		foregroundColor = null;
		visible = null;
		errorListeners = null;
		sortManager = null;
		waiRole = null;
		mouseOutListeners = null;
		mouseOverListeners = null;
		unlockedClientAttributeNames = null;
		initListeners = null;
		propertyChangeListeners = null;
		hiddenMode = null;
		first = null;
		margins = null;
		rows = null;
		var = null;
		value = null;

		super.release();
	}

}
