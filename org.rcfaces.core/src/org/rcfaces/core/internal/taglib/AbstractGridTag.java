package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.AbstractGridComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public abstract class AbstractGridTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AbstractGridTag.class);

	private String hiddenMode;
	private String visible;
	private String height;
	private String width;
	private String helpMessage;
	private String helpURL;
	private String toolTipText;
	private String mouseOutListeners;
	private String mouseOverListeners;
	private String valueLocked;
	private String lookId;
	private String blurListeners;
	private String focusListeners;
	private String x;
	private String y;
	private String marginBottom;
	private String marginLeft;
	private String marginRight;
	private String marginTop;
	private String backgroundColor;
	private String foregroundColor;
	private String keyPressListeners;
	private String keyDownListeners;
	private String keyUpListeners;
	private String resetListeners;
	private String styleClass;
	private String userEventListeners;
	private String propertyChangeListeners;
	private String initListeners;
	private String var;
	private String first;
	private String rows;
	private String margins;
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

	public final String getValueLocked() {
		return valueLocked;
	}

	public final void setValueLocked(String valueLocked) {
		this.valueLocked = valueLocked;
	}

	public final String getLookId() {
		return lookId;
	}

	public final void setLookId(String lookId) {
		this.lookId = lookId;
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

	public final String getResetListener() {
		return resetListeners;
	}

	public final void setResetListener(String resetListeners) {
		this.resetListeners = resetListeners;
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

	public final String getVar() {
		return var;
	}

	public final void setVar(String var) {
		this.var = var;
	}

	public final String getFirst() {
		return first;
	}

	public final void setFirst(String first) {
		this.first = first;
	}

	public final String getRows() {
		return rows;
	}

	public final void setRows(String rows) {
		this.rows = rows;
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

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  height='"+height+"'");
			LOG.debug("  width='"+width+"'");
			LOG.debug("  helpMessage='"+helpMessage+"'");
			LOG.debug("  helpURL='"+helpURL+"'");
			LOG.debug("  toolTipText='"+toolTipText+"'");
			LOG.debug("  valueLocked='"+valueLocked+"'");
			LOG.debug("  lookId='"+lookId+"'");
			LOG.debug("  x='"+x+"'");
			LOG.debug("  y='"+y+"'");
			LOG.debug("  marginBottom='"+marginBottom+"'");
			LOG.debug("  marginLeft='"+marginLeft+"'");
			LOG.debug("  marginRight='"+marginRight+"'");
			LOG.debug("  marginTop='"+marginTop+"'");
			LOG.debug("  backgroundColor='"+backgroundColor+"'");
			LOG.debug("  foregroundColor='"+foregroundColor+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  var='"+var+"'");
			LOG.debug("  first='"+first+"'");
			LOG.debug("  rows='"+rows+"'");
			LOG.debug("  margins='"+margins+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof AbstractGridComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractGridComponent'.");
		}

		AbstractGridComponent component = (AbstractGridComponent) uiComponent;
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
			parseActionListener(application, component, MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
		}

		if (mouseOverListeners != null) {
			parseActionListener(application, component, MOUSE_OVER_LISTENER_TYPE, mouseOverListeners);
		}

		if (valueLocked != null) {
			if (isValueReference(valueLocked)) {
				ValueBinding vb = application.createValueBinding(valueLocked);

				component.setValueLocked(vb);
			} else {
				component.setValueLocked(getBool(valueLocked));
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

		if (blurListeners != null) {
			parseActionListener(application, component, BLUR_LISTENER_TYPE, blurListeners);
		}

		if (focusListeners != null) {
			parseActionListener(application, component, FOCUS_LISTENER_TYPE, focusListeners);
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

		if (keyPressListeners != null) {
			parseActionListener(application, component, KEY_PRESS_LISTENER_TYPE, keyPressListeners);
		}

		if (keyDownListeners != null) {
			parseActionListener(application, component, KEY_DOWN_LISTENER_TYPE, keyDownListeners);
		}

		if (keyUpListeners != null) {
			parseActionListener(application, component, KEY_UP_LISTENER_TYPE, keyUpListeners);
		}

		if (resetListeners != null) {
			parseActionListener(application, component, RESET_LISTENER_TYPE, resetListeners);
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

		if (var != null) {
			if (isValueReference(var)) {
				ValueBinding vb = application.createValueBinding(var);
				component.setVar(vb);
			} else {
				component.setVar(var);
			}
		}

		if (first != null) {
			if (isValueReference(first)) {
				ValueBinding vb = application.createValueBinding(first);
				component.setFirst(vb);
			} else {
				component.setFirst(getInt(first));
			}
		}

		if (rows != null) {
			if (isValueReference(rows)) {
				ValueBinding vb = application.createValueBinding(rows);
				component.setRows(vb);
			} else {
				component.setRows(getInt(rows));
			}
		}

		if (margins != null) {
			if (isValueReference(margins)) {
				throw new javax.faces.FacesException("Attribute 'margins' does not accept binding !");
			}
				component.setMargins(margins);
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
		valueLocked = null;
		lookId = null;
		blurListeners = null;
		focusListeners = null;
		x = null;
		y = null;
		marginBottom = null;
		marginLeft = null;
		marginRight = null;
		marginTop = null;
		backgroundColor = null;
		foregroundColor = null;
		keyPressListeners = null;
		keyDownListeners = null;
		keyUpListeners = null;
		resetListeners = null;
		styleClass = null;
		userEventListeners = null;
		propertyChangeListeners = null;
		initListeners = null;
		var = null;
		first = null;
		rows = null;
		margins = null;
		value = null;

		super.release();
	}

}
