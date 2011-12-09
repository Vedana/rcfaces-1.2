package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.AbstractMessagesComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public abstract class AbstractMessagesTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AbstractMessagesTag.class);

	private ValueExpression marginBottom;
	private ValueExpression marginLeft;
	private ValueExpression marginRight;
	private ValueExpression marginTop;
	private ValueExpression userEventListeners;
	private ValueExpression x;
	private ValueExpression y;
	private ValueExpression helpMessage;
	private ValueExpression helpURL;
	private ValueExpression toolTipText;
	private ValueExpression partialRendering;
	private ValueExpression styleClass;
	private ValueExpression lookId;
	private ValueExpression width;
	private ValueExpression height;
	private ValueExpression backgroundColor;
	private ValueExpression foregroundColor;
	private ValueExpression visible;
	private ValueExpression errorListeners;
	private ValueExpression ariaLabel;
	private ValueExpression ariaLevel;
	private ValueExpression waiRole;
	private ValueExpression mouseOutListeners;
	private ValueExpression mouseOverListeners;
	private ValueExpression bottomPosition;
	private ValueExpression leftPosition;
	private ValueExpression rightPosition;
	private ValueExpression topPosition;
	private ValueExpression unlockedClientAttributeNames;
	private ValueExpression initListeners;
	private ValueExpression propertyChangeListeners;
	private ValueExpression hiddenMode;
	private ValueExpression globalOnly;
	private ValueExpression showSummary;
	private ValueExpression margins;
	private ValueExpression showDetail;
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

	public final void setUserEventListener(ValueExpression userEventListeners) {
		this.userEventListeners = userEventListeners;
	}

	public final void setX(ValueExpression x) {
		this.x = x;
	}

	public final void setY(ValueExpression y) {
		this.y = y;
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

	public final void setPartialRendering(ValueExpression partialRendering) {
		this.partialRendering = partialRendering;
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

	public final void setAriaLabel(ValueExpression ariaLabel) {
		this.ariaLabel = ariaLabel;
	}

	public final void setAriaLevel(ValueExpression ariaLevel) {
		this.ariaLevel = ariaLevel;
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

	public final void setGlobalOnly(ValueExpression globalOnly) {
		this.globalOnly = globalOnly;
	}

	public final void setShowSummary(ValueExpression showSummary) {
		this.showSummary = showSummary;
	}

	public final void setMargins(ValueExpression margins) {
		this.margins = margins;
	}

	public final void setShowDetail(ValueExpression showDetail) {
		this.showDetail = showDetail;
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
			LOG.debug("  partialRendering='"+partialRendering+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  lookId='"+lookId+"'");
			LOG.debug("  width='"+width+"'");
			LOG.debug("  height='"+height+"'");
			LOG.debug("  backgroundColor='"+backgroundColor+"'");
			LOG.debug("  foregroundColor='"+foregroundColor+"'");
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  ariaLabel='"+ariaLabel+"'");
			LOG.debug("  ariaLevel='"+ariaLevel+"'");
			LOG.debug("  waiRole='"+waiRole+"'");
			LOG.debug("  bottomPosition='"+bottomPosition+"'");
			LOG.debug("  leftPosition='"+leftPosition+"'");
			LOG.debug("  rightPosition='"+rightPosition+"'");
			LOG.debug("  topPosition='"+topPosition+"'");
			LOG.debug("  unlockedClientAttributeNames='"+unlockedClientAttributeNames+"'");
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  globalOnly='"+globalOnly+"'");
			LOG.debug("  showSummary='"+showSummary+"'");
			LOG.debug("  margins='"+margins+"'");
			LOG.debug("  showDetail='"+showDetail+"'");
		}
		if ((uiComponent instanceof AbstractMessagesComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractMessagesComponent'.");
		}

		super.setProperties(uiComponent);

		AbstractMessagesComponent component = (AbstractMessagesComponent) uiComponent;
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

		if (mouseOutListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
		}

		if (mouseOverListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MOUSE_OVER_LISTENER_TYPE, mouseOverListeners);
		}

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
			}
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

		if (globalOnly != null) {
			if (globalOnly.isLiteralText()==false) {
				component.setValueExpression(Properties.GLOBAL_ONLY, globalOnly);

			} else {
				component.setGlobalOnly(getBool(globalOnly.getExpressionString()));
			}
		}

		if (showSummary != null) {
			if (showSummary.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_SUMMARY, showSummary);

			} else {
				component.setShowSummary(getBool(showSummary.getExpressionString()));
			}
		}

		if (margins != null) {
			if (margins.isLiteralText()==false) {
				throw new javax.faces.FacesException("Attribute 'margins' does not accept binding !");
			}
				component.setMargins(margins.getExpressionString());
		}

		if (showDetail != null) {
			if (showDetail.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_DETAIL, showDetail);

			} else {
				component.setShowDetail(getBool(showDetail.getExpressionString()));
			}
		}
	}

	public void release() {
		marginBottom = null;
		marginLeft = null;
		marginRight = null;
		marginTop = null;
		userEventListeners = null;
		x = null;
		y = null;
		helpMessage = null;
		helpURL = null;
		toolTipText = null;
		partialRendering = null;
		styleClass = null;
		lookId = null;
		width = null;
		height = null;
		backgroundColor = null;
		foregroundColor = null;
		visible = null;
		errorListeners = null;
		ariaLabel = null;
		ariaLevel = null;
		waiRole = null;
		mouseOutListeners = null;
		mouseOverListeners = null;
		bottomPosition = null;
		leftPosition = null;
		rightPosition = null;
		topPosition = null;
		unlockedClientAttributeNames = null;
		initListeners = null;
		propertyChangeListeners = null;
		hiddenMode = null;
		globalOnly = null;
		showSummary = null;
		margins = null;
		showDetail = null;

		super.release();
	}

}
