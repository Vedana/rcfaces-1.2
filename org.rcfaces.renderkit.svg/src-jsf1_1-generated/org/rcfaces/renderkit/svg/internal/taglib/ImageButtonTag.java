package org.rcfaces.renderkit.svg.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.renderkit.svg.component.ImageButtonComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;

public class ImageButtonTag extends ImageTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ImageButtonTag.class);

	private String disabled;
	private String accessKey;
	private String tabIndex;
	private String blurListeners;
	private String focusListeners;
	private String keyPressListeners;
	private String keyDownListeners;
	private String keyUpListeners;
	private String immediate;
	private String validationListeners;
	private String selectionListeners;
	private String focusStyleClass;
	public String getComponentType() {
		return ImageButtonComponent.COMPONENT_TYPE;
	}

	public final String getDisabled() {
		return disabled;
	}

	public final void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public final String getAccessKey() {
		return accessKey;
	}

	public final void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public final String getTabIndex() {
		return tabIndex;
	}

	public final void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
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

	public final String getImmediate() {
		return immediate;
	}

	public final void setImmediate(String immediate) {
		this.immediate = immediate;
	}

	public final String getValidationListener() {
		return validationListeners;
	}

	public final void setValidationListener(String validationListeners) {
		this.validationListeners = validationListeners;
	}

	public final String getSelectionListener() {
		return selectionListeners;
	}

	public final void setSelectionListener(String selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final String getFocusStyleClass() {
		return focusStyleClass;
	}

	public final void setFocusStyleClass(String focusStyleClass) {
		this.focusStyleClass = focusStyleClass;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ImageButtonComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  disabled='"+disabled+"'");
			LOG.debug("  accessKey='"+accessKey+"'");
			LOG.debug("  tabIndex='"+tabIndex+"'");
			LOG.debug("  immediate='"+immediate+"'");
			LOG.debug("  focusStyleClass='"+focusStyleClass+"'");
			LOG.debug("  immediate='"+immediate+"'");
		}
		if ((uiComponent instanceof ImageButtonComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ImageButtonComponent'.");
		}

		super.setProperties(uiComponent);

		ImageButtonComponent component = (ImageButtonComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (disabled != null) {
			if (isValueReference(disabled)) {
				ValueBinding vb = application.createValueBinding(disabled);
				component.setValueBinding(Properties.DISABLED, vb);

			} else {
				component.setDisabled(getBool(disabled));
			}
		}

		if (accessKey != null) {
			if (isValueReference(accessKey)) {
				ValueBinding vb = application.createValueBinding(accessKey);
				component.setValueBinding(Properties.ACCESS_KEY, vb);

			} else {
				component.setAccessKey(accessKey);
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

		if (blurListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.BLUR_LISTENER_TYPE, blurListeners);
		}

		if (focusListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.FOCUS_LISTENER_TYPE, focusListeners);
		}

		if (keyPressListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.KEY_PRESS_LISTENER_TYPE, keyPressListeners);
		}

		if (keyDownListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.KEY_DOWN_LISTENER_TYPE, keyDownListeners);
		}

		if (keyUpListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.KEY_UP_LISTENER_TYPE, keyUpListeners);
		}

		if (immediate != null) {
			if (isValueReference(immediate)) {
				ValueBinding vb = application.createValueBinding(immediate);
				component.setValueBinding(Properties.IMMEDIATE, vb);

			} else {
				component.setImmediate(getBool(immediate));
			}
		}

		if (validationListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.VALIDATION_LISTENER_TYPE, validationListeners);
		}

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (focusStyleClass != null) {
			if (isValueReference(focusStyleClass)) {
				ValueBinding vb = application.createValueBinding(focusStyleClass);
				component.setValueBinding(Properties.FOCUS_STYLE_CLASS, vb);

			} else {
				component.setFocusStyleClass(focusStyleClass);
			}
		}
	}

	public void release() {
		disabled = null;
		accessKey = null;
		tabIndex = null;
		blurListeners = null;
		focusListeners = null;
		keyPressListeners = null;
		keyDownListeners = null;
		keyUpListeners = null;
		immediate = null;
		validationListeners = null;
		selectionListeners = null;
		focusStyleClass = null;

		super.release();
	}

}
