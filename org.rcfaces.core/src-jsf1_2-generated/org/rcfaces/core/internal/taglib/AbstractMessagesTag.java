package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.AbstractMessagesComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public abstract class AbstractMessagesTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AbstractMessagesTag.class);

	private ValueExpression visible;
	private ValueExpression width;
	private ValueExpression height;
	private ValueExpression helpMessage;
	private ValueExpression helpURL;
	private ValueExpression toolTipText;
	private ValueExpression mouseOutListeners;
	private ValueExpression mouseOverListeners;
	private ValueExpression unlockedClientAttributeNames;
	private ValueExpression lookId;
	private ValueExpression x;
	private ValueExpression y;
	private ValueExpression errorListeners;
	private ValueExpression partialRendering;
	private ValueExpression marginBottom;
	private ValueExpression marginLeft;
	private ValueExpression marginRight;
	private ValueExpression marginTop;
	private ValueExpression backgroundColor;
	private ValueExpression foregroundColor;
	private ValueExpression styleClass;
	private ValueExpression userEventListeners;
	private ValueExpression hiddenMode;
	private ValueExpression ariaLevel;
	private ValueExpression waiRole;
	private ValueExpression propertyChangeListeners;
	private ValueExpression initListeners;
	private ValueExpression showDetail;
	private ValueExpression margins;
	private ValueExpression globalOnly;
	private ValueExpression showSummary;
	public final void setVisible(ValueExpression visible) {
		this.visible = visible;
	}

	public final void setWidth(ValueExpression width) {
		this.width = width;
	}

	public final void setHeight(ValueExpression height) {
		this.height = height;
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

	public final void setMouseOutListener(ValueExpression mouseOutListeners) {
		this.mouseOutListeners = mouseOutListeners;
	}

	public final void setMouseOverListener(ValueExpression mouseOverListeners) {
		this.mouseOverListeners = mouseOverListeners;
	}

	public final void setUnlockedClientAttributeNames(ValueExpression unlockedClientAttributeNames) {
		this.unlockedClientAttributeNames = unlockedClientAttributeNames;
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

	public final void setErrorListener(ValueExpression errorListeners) {
		this.errorListeners = errorListeners;
	}

	public final void setPartialRendering(ValueExpression partialRendering) {
		this.partialRendering = partialRendering;
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

	public final void setBackgroundColor(ValueExpression backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public final void setForegroundColor(ValueExpression foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public final void setStyleClass(ValueExpression styleClass) {
		this.styleClass = styleClass;
	}

	public final void setUserEventListener(ValueExpression userEventListeners) {
		this.userEventListeners = userEventListeners;
	}

	public final void setHiddenMode(ValueExpression hiddenMode) {
		this.hiddenMode = hiddenMode;
	}

	public final void setAriaLevel(ValueExpression ariaLevel) {
		this.ariaLevel = ariaLevel;
	}

	public final void setWaiRole(ValueExpression waiRole) {
		this.waiRole = waiRole;
	}

	public final void setPropertyChangeListener(ValueExpression propertyChangeListeners) {
		this.propertyChangeListeners = propertyChangeListeners;
	}

	public final void setInitListener(ValueExpression initListeners) {
		this.initListeners = initListeners;
	}

	public final void setShowDetail(ValueExpression showDetail) {
		this.showDetail = showDetail;
	}

	public final void setMargins(ValueExpression margins) {
		this.margins = margins;
	}

	public final void setGlobalOnly(ValueExpression globalOnly) {
		this.globalOnly = globalOnly;
	}

	public final void setShowSummary(ValueExpression showSummary) {
		this.showSummary = showSummary;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  width='"+width+"'");
			LOG.debug("  height='"+height+"'");
			LOG.debug("  helpMessage='"+helpMessage+"'");
			LOG.debug("  helpURL='"+helpURL+"'");
			LOG.debug("  toolTipText='"+toolTipText+"'");
			LOG.debug("  unlockedClientAttributeNames='"+unlockedClientAttributeNames+"'");
			LOG.debug("  lookId='"+lookId+"'");
			LOG.debug("  x='"+x+"'");
			LOG.debug("  y='"+y+"'");
			LOG.debug("  partialRendering='"+partialRendering+"'");
			LOG.debug("  marginBottom='"+marginBottom+"'");
			LOG.debug("  marginLeft='"+marginLeft+"'");
			LOG.debug("  marginRight='"+marginRight+"'");
			LOG.debug("  marginTop='"+marginTop+"'");
			LOG.debug("  backgroundColor='"+backgroundColor+"'");
			LOG.debug("  foregroundColor='"+foregroundColor+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  ariaLevel='"+ariaLevel+"'");
			LOG.debug("  waiRole='"+waiRole+"'");
			LOG.debug("  showDetail='"+showDetail+"'");
			LOG.debug("  margins='"+margins+"'");
			LOG.debug("  globalOnly='"+globalOnly+"'");
			LOG.debug("  showSummary='"+showSummary+"'");
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

		if (errorListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.ERROR_LISTENER_TYPE, errorListeners);
		}

		if (partialRendering != null) {
			if (partialRendering.isLiteralText()==false) {
				component.setValueExpression(Properties.PARTIAL_RENDERING, partialRendering);

			} else {
				component.setPartialRendering(getBool(partialRendering.getExpressionString()));
			}
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

		if (styleClass != null) {
			if (styleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.STYLE_CLASS, styleClass);

			} else {
				component.setStyleClass(styleClass.getExpressionString());
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

		if (propertyChangeListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.PROPERTY_CHANGE_LISTENER_TYPE, propertyChangeListeners);
		}

		if (initListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.INIT_LISTENER_TYPE, initListeners);
		}

		if (showDetail != null) {
			if (showDetail.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_DETAIL, showDetail);

			} else {
				component.setShowDetail(getBool(showDetail.getExpressionString()));
			}
		}

		if (margins != null) {
			if (margins.isLiteralText()==false) {
				throw new javax.faces.FacesException("Attribute 'margins' does not accept binding !");
			}
				component.setMargins(margins.getExpressionString());
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
	}

	public void release() {
		visible = null;
		width = null;
		height = null;
		helpMessage = null;
		helpURL = null;
		toolTipText = null;
		mouseOutListeners = null;
		mouseOverListeners = null;
		unlockedClientAttributeNames = null;
		lookId = null;
		x = null;
		y = null;
		errorListeners = null;
		partialRendering = null;
		marginBottom = null;
		marginLeft = null;
		marginRight = null;
		marginTop = null;
		backgroundColor = null;
		foregroundColor = null;
		styleClass = null;
		userEventListeners = null;
		hiddenMode = null;
		ariaLevel = null;
		waiRole = null;
		propertyChangeListeners = null;
		initListeners = null;
		showDetail = null;
		margins = null;
		globalOnly = null;
		showSummary = null;

		super.release();
	}

}
