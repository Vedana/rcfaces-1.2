package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.component.AbstractMessagesComponent;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public abstract class AbstractMessagesTag extends CameliaTag {


	private static final Log LOG=LogFactory.getLog(AbstractMessagesTag.class);

	private String height;
	private String width;
	private String visible;
	private String hiddenMode;
	private String mouseOutListeners;
	private String mouseOverListeners;
	private String helpMessage;
	private String helpURL;
	private String toolTipText;
	private String y;
	private String x;
	private String lookId;
	private String marginRight;
	private String marginLeft;
	private String marginTop;
	private String marginBottom;
	private String foregroundColor;
	private String backgroundColor;
	private String styleClass;
	private String userEventListeners;
	private String propertyChangeListeners;
	private String initListeners;
	private String globalOnly;
	private String showSummary;
	private String margins;
	private String showDetail;
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

	public final String getY() {
		return y;
	}

	public final void setY(String y) {
		this.y = y;
	}

	public final String getX() {
		return x;
	}

	public final void setX(String x) {
		this.x = x;
	}

	public final String getLookId() {
		return lookId;
	}

	public final void setLookId(String lookId) {
		this.lookId = lookId;
	}

	public final String getMarginRight() {
		return marginRight;
	}

	public final void setMarginRight(String marginRight) {
		this.marginRight = marginRight;
	}

	public final String getMarginLeft() {
		return marginLeft;
	}

	public final void setMarginLeft(String marginLeft) {
		this.marginLeft = marginLeft;
	}

	public final String getMarginTop() {
		return marginTop;
	}

	public final void setMarginTop(String marginTop) {
		this.marginTop = marginTop;
	}

	public final String getMarginBottom() {
		return marginBottom;
	}

	public final void setMarginBottom(String marginBottom) {
		this.marginBottom = marginBottom;
	}

	public final String getForegroundColor() {
		return foregroundColor;
	}

	public final void setForegroundColor(String foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public final String getBackgroundColor() {
		return backgroundColor;
	}

	public final void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
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

	public final String getInitListener() {
		return initListeners;
	}

	public final void setInitListener(String initListeners) {
		this.initListeners = initListeners;
	}

	public final String getGlobalOnly() {
		return globalOnly;
	}

	public final void setGlobalOnly(String globalOnly) {
		this.globalOnly = globalOnly;
	}

	public final String getShowSummary() {
		return showSummary;
	}

	public final void setShowSummary(String showSummary) {
		this.showSummary = showSummary;
	}

	public final String getMargins() {
		return margins;
	}

	public final void setMargins(String margins) {
		this.margins = margins;
	}

	public final String getShowDetail() {
		return showDetail;
	}

	public final void setShowDetail(String showDetail) {
		this.showDetail = showDetail;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  height='"+height+"'");
			LOG.debug("  width='"+width+"'");
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  helpMessage='"+helpMessage+"'");
			LOG.debug("  helpURL='"+helpURL+"'");
			LOG.debug("  toolTipText='"+toolTipText+"'");
			LOG.debug("  y='"+y+"'");
			LOG.debug("  x='"+x+"'");
			LOG.debug("  lookId='"+lookId+"'");
			LOG.debug("  marginRight='"+marginRight+"'");
			LOG.debug("  marginLeft='"+marginLeft+"'");
			LOG.debug("  marginTop='"+marginTop+"'");
			LOG.debug("  marginBottom='"+marginBottom+"'");
			LOG.debug("  foregroundColor='"+foregroundColor+"'");
			LOG.debug("  backgroundColor='"+backgroundColor+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  globalOnly='"+globalOnly+"'");
			LOG.debug("  showSummary='"+showSummary+"'");
			LOG.debug("  margins='"+margins+"'");
			LOG.debug("  showDetail='"+showDetail+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof AbstractMessagesComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractMessagesComponent'.");
		}

		AbstractMessagesComponent component = (AbstractMessagesComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

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

		if (visible != null) {
			if (isValueReference(visible)) {
				ValueBinding vb = application.createValueBinding(visible);

				component.setVisible(vb);
			} else {
				component.setVisible(getBoolean(visible));
			}
		}

		if (hiddenMode != null) {
			if (isValueReference(hiddenMode)) {
				ValueBinding vb = application.createValueBinding(hiddenMode);

				component.setHiddenMode(vb);
			} else {
				component.setHiddenMode(hiddenMode);
			}
		}

		if (mouseOutListeners != null) {
			parseActionListener(application, component, MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
		}

		if (mouseOverListeners != null) {
			parseActionListener(application, component, MOUSE_OVER_LISTENER_TYPE, mouseOverListeners);
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

		if (y != null) {
			if (isValueReference(y)) {
				ValueBinding vb = application.createValueBinding(y);

				component.setY(vb);
			} else {
				component.setY(y);
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

		if (lookId != null) {
			if (isValueReference(lookId)) {
				ValueBinding vb = application.createValueBinding(lookId);

				component.setLookId(vb);
			} else {
				component.setLookId(lookId);
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

		if (marginLeft != null) {
			if (isValueReference(marginLeft)) {
				ValueBinding vb = application.createValueBinding(marginLeft);

				component.setMarginLeft(vb);
			} else {
				component.setMarginLeft(marginLeft);
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

		if (marginBottom != null) {
			if (isValueReference(marginBottom)) {
				ValueBinding vb = application.createValueBinding(marginBottom);

				component.setMarginBottom(vb);
			} else {
				component.setMarginBottom(marginBottom);
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

		if (backgroundColor != null) {
			if (isValueReference(backgroundColor)) {
				ValueBinding vb = application.createValueBinding(backgroundColor);

				component.setBackgroundColor(vb);
			} else {
				component.setBackgroundColor(backgroundColor);
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
			parseActionListener(application, component, USER_EVENT_LISTENER_TYPE, userEventListeners);
		}

		if (propertyChangeListeners != null) {
			parseActionListener(application, component, PROPERTY_CHANGE_LISTENER_TYPE, propertyChangeListeners);
		}

		if (initListeners != null) {
			parseActionListener(application, component, INIT_LISTENER_TYPE, initListeners);
		}

		if (globalOnly != null) {
			if (isValueReference(globalOnly)) {
				ValueBinding vb = application.createValueBinding(globalOnly);
				component.setGlobalOnly(vb);
			} else {
				component.setGlobalOnly(getBool(globalOnly));
			}
		}

		if (showSummary != null) {
			if (isValueReference(showSummary)) {
				ValueBinding vb = application.createValueBinding(showSummary);
				component.setShowSummary(vb);
			} else {
				component.setShowSummary(getBool(showSummary));
			}
		}

		if (margins != null) {
			if (isValueReference(margins)) {
				throw new javax.faces.FacesException("Attribute 'margins' does not accept binding !");
			}
				component.setMargins(margins);
		}

		if (showDetail != null) {
			if (isValueReference(showDetail)) {
				ValueBinding vb = application.createValueBinding(showDetail);
				component.setShowDetail(vb);
			} else {
				component.setShowDetail(getBool(showDetail));
			}
		}
	}

	public void release() {
		height = null;
		width = null;
		visible = null;
		hiddenMode = null;
		mouseOutListeners = null;
		mouseOverListeners = null;
		helpMessage = null;
		helpURL = null;
		toolTipText = null;
		y = null;
		x = null;
		lookId = null;
		marginRight = null;
		marginLeft = null;
		marginTop = null;
		marginBottom = null;
		foregroundColor = null;
		backgroundColor = null;
		styleClass = null;
		userEventListeners = null;
		propertyChangeListeners = null;
		initListeners = null;
		globalOnly = null;
		showSummary = null;
		margins = null;
		showDetail = null;

		super.release();
	}

}
