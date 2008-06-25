package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.component.AbstractDataComponent;
import javax.faces.context.FacesContext;

public abstract class AbstractDataTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AbstractDataTag.class);

	private String marginBottom;
	private String marginLeft;
	private String marginRight;
	private String marginTop;
	private String resetListeners;
	private String keyUpListeners;
	private String keyDownListeners;
	private String keyPressListeners;
	private String x;
	private String y;
	private String userEventListeners;
	private String helpMessage;
	private String helpURL;
	private String toolTipText;
	private String styleClass;
	private String lookId;
	private String width;
	private String height;
	private String blurListeners;
	private String focusListeners;
	private String backgroundColor;
	private String foregroundColor;
	private String visible;
	private String errorListeners;
	private String waiRole;
	private String sortManager;
	private String mouseOutListeners;
	private String mouseOverListeners;
	private String unlockedClientAttributeNames;
	private String tabIndex;
	private String initListeners;
	private String propertyChangeListeners;
	private String hiddenMode;
	private String var;
	private String rows;
	private String first;
	private String value;
	private String margins;
	private String saveCompleteState;
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

	public final String getResetListener() {
		return resetListeners;
	}

	public final void setResetListener(String resetListeners) {
		this.resetListeners = resetListeners;
	}

	public final String getKeyUpListener() {
		return keyUpListeners;
	}

	public final void setKeyUpListener(String keyUpListeners) {
		this.keyUpListeners = keyUpListeners;
	}

	public final String getKeyDownListener() {
		return keyDownListeners;
	}

	public final void setKeyDownListener(String keyDownListeners) {
		this.keyDownListeners = keyDownListeners;
	}

	public final String getKeyPressListener() {
		return keyPressListeners;
	}

	public final void setKeyPressListener(String keyPressListeners) {
		this.keyPressListeners = keyPressListeners;
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

	public final String getUserEventListener() {
		return userEventListeners;
	}

	public final void setUserEventListener(String userEventListeners) {
		this.userEventListeners = userEventListeners;
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

	public final String getSortManager() {
		return sortManager;
	}

	public final void setSortManager(String sortManager) {
		this.sortManager = sortManager;
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

	public final void setVar(String var) {
		this.var = var;
	}

	public final void setRows(String rows) {
		this.rows = rows;
	}

	public final void setFirst(String first) {
		this.first = first;
	}

	public final void setValue(String value) {
		this.value = value;
	}

	public final void setMargins(String margins) {
		this.margins = margins;
	}

	public final String getSaveCompleteState() {
		return saveCompleteState;
	}

	public final void setSaveCompleteState(String saveCompleteState) {
		this.saveCompleteState = saveCompleteState;
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
			LOG.debug("  sortManager='"+sortManager+"'");
			LOG.debug("  unlockedClientAttributeNames='"+unlockedClientAttributeNames+"'");
			LOG.debug("  tabIndex='"+tabIndex+"'");
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  var='"+var+"'");
			LOG.debug("  rows='"+rows+"'");
			LOG.debug("  first='"+first+"'");
			LOG.debug("  value='"+value+"'");
			LOG.debug("  margins='"+margins+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof AbstractDataComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractDataComponent'.");
		}

		AbstractDataComponent component = (AbstractDataComponent) uiComponent;
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

		if (resetListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.RESET_LISTENER_TYPE, resetListeners);
		}

		if (keyUpListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.KEY_UP_LISTENER_TYPE, keyUpListeners);
		}

		if (keyDownListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.KEY_DOWN_LISTENER_TYPE, keyDownListeners);
		}

		if (keyPressListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.KEY_PRESS_LISTENER_TYPE, keyPressListeners);
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

		if (userEventListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.USER_EVENT_LISTENER_TYPE, userEventListeners);
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

		if (blurListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.BLUR_LISTENER_TYPE, blurListeners);
		}

		if (focusListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.FOCUS_LISTENER_TYPE, focusListeners);
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

		if (sortManager != null) {
			if (isValueReference(sortManager)) {
				ValueBinding vb = application.createValueBinding(sortManager);
				component.setValueBinding(Properties.SORT_MANAGER, vb);

			} else {
				component.setSortManager(sortManager);
			}
		}

		if (mouseOutListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
		}

		if (mouseOverListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.MOUSE_OVER_LISTENER_TYPE, mouseOverListeners);
		}

		if (unlockedClientAttributeNames != null) {
			if (isValueReference(unlockedClientAttributeNames)) {
				ValueBinding vb = application.createValueBinding(unlockedClientAttributeNames);
				component.setValueBinding(Properties.UNLOCKED_CLIENT_ATTRIBUTE_NAMES, vb);

			} else {
				component.setUnlockedClientAttributeNames(unlockedClientAttributeNames);
			}
		}

		if (tabIndex != null) {
			if (isValueReference(tabIndex)) {
				ValueBinding vb = application.createValueBinding(tabIndex);
				component.setValueBinding(Properties.TAB_INDEX, vb);

			} else {
				component.setTabIndex(getInteger(tabIndex));
			}
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

		if (var != null) {
			if (isValueReference(var)) {
				ValueBinding vb = application.createValueBinding(var);
				component.setValueBinding(Properties.VAR, vb);

			} else {
				component.setVar(var);
			}
		}

		if (rows != null) {
			if (isValueReference(rows)) {
				ValueBinding vb = application.createValueBinding(rows);
				component.setValueBinding(Properties.ROWS, vb);

			} else {
				component.setRows(getInt(rows));
			}
		}

		if (first != null) {
			if (isValueReference(first)) {
				ValueBinding vb = application.createValueBinding(first);
				component.setValueBinding(Properties.FIRST, vb);

			} else {
				component.setFirst(getInt(first));
			}
		}

		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = application.createValueBinding(value);
				component.setValueBinding(Properties.VALUE, vb);

			} else {
				component.setValue(value);
			}
		}

		if (margins != null) {
			if (isValueReference(margins)) {
				throw new javax.faces.FacesException("Attribute 'margins' does not accept binding !");
			}
				component.setMargins(margins);
		}

		if (saveCompleteState != null) {
			if (isValueReference(saveCompleteState)) {
				ValueBinding vb = application.createValueBinding(saveCompleteState);
				component.setValueBinding(Properties.SAVE_COMPLETE_STATE, vb);

			} else {
				component.setSaveCompleteState(getBool(saveCompleteState));
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
		waiRole = null;
		sortManager = null;
		mouseOutListeners = null;
		mouseOverListeners = null;
		unlockedClientAttributeNames = null;
		tabIndex = null;
		initListeners = null;
		propertyChangeListeners = null;
		hiddenMode = null;
		var = null;
		rows = null;
		first = null;
		value = null;
		margins = null;
		saveCompleteState = null;

		super.release();
	}

}
