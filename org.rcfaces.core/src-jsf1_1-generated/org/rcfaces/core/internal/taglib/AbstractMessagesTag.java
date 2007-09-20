package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.AbstractMessagesComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public abstract class AbstractMessagesTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AbstractMessagesTag.class);

	private String marginBottom;
	private String marginLeft;
	private String marginRight;
	private String marginTop;
	private String userEventListeners;
	private String x;
	private String y;
	private String helpMessage;
	private String helpURL;
	private String toolTipText;
	private String styleClass;
	private String lookId;
	private String width;
	private String height;
	private String backgroundColor;
	private String foregroundColor;
	private String visible;
	private String errorListeners;
	private String waiRole;
	private String mouseOutListeners;
	private String mouseOverListeners;
	private String initListeners;
	private String propertyChangeListeners;
	private String hiddenMode;
	private String showSummary;
	private String globalOnly;
	private String showDetail;
	private String margins;
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

	public final String getUserEventListener() {
		return userEventListeners;
	}

	public final void setUserEventListener(String userEventListeners) {
		this.userEventListeners = userEventListeners;
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

	public final String getStyleClass() {
		return styleClass;
	}

	public final void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public final String getLookId() {
		return lookId;
	}

	public final void setLookId(String lookId) {
		this.lookId = lookId;
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

	public final String getInitListener() {
		return initListeners;
	}

	public final void setInitListener(String initListeners) {
		this.initListeners = initListeners;
	}

	public final String getPropertyChangeListener() {
		return propertyChangeListeners;
	}

	public final void setPropertyChangeListener(String propertyChangeListeners) {
		this.propertyChangeListeners = propertyChangeListeners;
	}

	public final String getHiddenMode() {
		return hiddenMode;
	}

	public final void setHiddenMode(String hiddenMode) {
		this.hiddenMode = hiddenMode;
	}

	public final void setShowSummary(String showSummary) {
		this.showSummary = showSummary;
	}

	public final void setGlobalOnly(String globalOnly) {
		this.globalOnly = globalOnly;
	}

	public final void setShowDetail(String showDetail) {
		this.showDetail = showDetail;
	}

	public final void setMargins(String margins) {
		this.margins = margins;
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
			LOG.debug("  waiRole='"+waiRole+"'");
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  showSummary='"+showSummary+"'");
			LOG.debug("  globalOnly='"+globalOnly+"'");
			LOG.debug("  showDetail='"+showDetail+"'");
			LOG.debug("  margins='"+margins+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof AbstractMessagesComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractMessagesComponent'.");
		}

		AbstractMessagesComponent component = (AbstractMessagesComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

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

		if (userEventListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.USER_EVENT_LISTENER_TYPE, userEventListeners);
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

		if (styleClass != null) {
			if (isValueReference(styleClass)) {
				ValueBinding vb = application.createValueBinding(styleClass);
				component.setValueBinding(Properties.STYLE_CLASS, vb);

			} else {
				component.setStyleClass(styleClass);
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

		if (backgroundColor != null) {
			if (isValueReference(backgroundColor)) {
				ValueBinding vb = application.createValueBinding(backgroundColor);
				component.setValueBinding(Properties.BACKGROUND_COLOR, vb);

			} else {
				component.setBackgroundColor(backgroundColor);
			}
		}

		if (foregroundColor != null) {
			if (isValueReference(foregroundColor)) {
				ValueBinding vb = application.createValueBinding(foregroundColor);
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

		if (errorListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.ERROR_LISTENER_TYPE, errorListeners);
		}

		if (waiRole != null) {
			if (isValueReference(waiRole)) {
				ValueBinding vb = application.createValueBinding(waiRole);
				component.setValueBinding(Properties.WAI_ROLE, vb);

			} else {
				component.setWaiRole(waiRole);
			}
		}

		if (mouseOutListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
		}

		if (mouseOverListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.MOUSE_OVER_LISTENER_TYPE, mouseOverListeners);
		}

		if (initListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.INIT_LISTENER_TYPE, initListeners);
		}

		if (propertyChangeListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.PROPERTY_CHANGE_LISTENER_TYPE, propertyChangeListeners);
		}

		if (hiddenMode != null) {
			if (isValueReference(hiddenMode)) {
				ValueBinding vb = application.createValueBinding(hiddenMode);
				component.setValueBinding(Properties.HIDDEN_MODE, vb);

			} else {
				component.setHiddenMode(hiddenMode);
			}
		}

		if (showSummary != null) {
			if (isValueReference(showSummary)) {
				ValueBinding vb = application.createValueBinding(showSummary);
				component.setValueBinding(Properties.SHOW_SUMMARY, vb);

			} else {
				component.setShowSummary(getBool(showSummary));
			}
		}

		if (globalOnly != null) {
			if (isValueReference(globalOnly)) {
				ValueBinding vb = application.createValueBinding(globalOnly);
				component.setValueBinding(Properties.GLOBAL_ONLY, vb);

			} else {
				component.setGlobalOnly(getBool(globalOnly));
			}
		}

		if (showDetail != null) {
			if (isValueReference(showDetail)) {
				ValueBinding vb = application.createValueBinding(showDetail);
				component.setValueBinding(Properties.SHOW_DETAIL, vb);

			} else {
				component.setShowDetail(getBool(showDetail));
			}
		}

		if (margins != null) {
			if (isValueReference(margins)) {
				throw new javax.faces.FacesException("Attribute 'margins' does not accept binding !");
			}
				component.setMargins(margins);
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
		styleClass = null;
		lookId = null;
		width = null;
		height = null;
		backgroundColor = null;
		foregroundColor = null;
		visible = null;
		errorListeners = null;
		waiRole = null;
		mouseOutListeners = null;
		mouseOverListeners = null;
		initListeners = null;
		propertyChangeListeners = null;
		hiddenMode = null;
		showSummary = null;
		globalOnly = null;
		showDetail = null;
		margins = null;

		super.release();
	}

}
