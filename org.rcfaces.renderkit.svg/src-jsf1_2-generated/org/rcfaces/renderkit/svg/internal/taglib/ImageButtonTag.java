package org.rcfaces.renderkit.svg.internal.taglib;

import javax.faces.component.UIComponent;
import org.rcfaces.renderkit.svg.component.Properties;
import org.apache.commons.logging.LogFactory;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;
import javax.faces.application.Application;
import org.rcfaces.renderkit.svg.component.ImageButtonComponent;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.internal.tools.ListenersTools1_2;

public class ImageButtonTag extends ImageTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ImageButtonTag.class);

	private ValueExpression disabled;
	private ValueExpression accessKey;
	private ValueExpression tabIndex;
	private ValueExpression blurListeners;
	private ValueExpression focusListeners;
	private ValueExpression keyUpListeners;
	private ValueExpression keyDownListeners;
	private ValueExpression keyPressListeners;
	private ValueExpression immediate;
	private ValueExpression validationListeners;
	private ValueExpression selectionListeners;
	private ValueExpression focusStyleClass;
	public String getComponentType() {
		return ImageButtonComponent.COMPONENT_TYPE;
	}

	public void setDisabled(ValueExpression disabled) {
		this.disabled = disabled;
	}

	public void setAccessKey(ValueExpression accessKey) {
		this.accessKey = accessKey;
	}

	public void setTabIndex(ValueExpression tabIndex) {
		this.tabIndex = tabIndex;
	}

	public void setBlurListener(ValueExpression blurListeners) {
		this.blurListeners = blurListeners;
	}

	public void setFocusListener(ValueExpression focusListeners) {
		this.focusListeners = focusListeners;
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

	public void setImmediate(ValueExpression immediate) {
		this.immediate = immediate;
	}

	public void setValidationListener(ValueExpression validationListeners) {
		this.validationListeners = validationListeners;
	}

	public void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public void setFocusStyleClass(ValueExpression focusStyleClass) {
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

		if (disabled != null) {
			if (disabled.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLED, disabled);

			} else {
				component.setDisabled(getBool(disabled.getExpressionString()));
			}
		}

		if (accessKey != null) {
			if (accessKey.isLiteralText()==false) {
				component.setValueExpression(Properties.ACCESS_KEY, accessKey);

			} else {
				component.setAccessKey(accessKey.getExpressionString());
			}
		}

		if (tabIndex != null) {
			if (tabIndex.isLiteralText()==false) {
				component.setValueExpression(Properties.TAB_INDEX, tabIndex);

			} else {
				component.setTabIndex(getInteger(tabIndex.getExpressionString()));
			}
		}

		if (blurListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.BLUR_LISTENER_TYPE, blurListeners);
		}

		if (focusListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.FOCUS_LISTENER_TYPE, focusListeners);
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

		if (immediate != null) {
			if (immediate.isLiteralText()==false) {
				component.setValueExpression(Properties.IMMEDIATE, immediate);

			} else {
				component.setImmediate(getBool(immediate.getExpressionString()));
			}
		}

		if (validationListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.VALIDATION_LISTENER_TYPE, validationListeners);
		}

		if (selectionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (focusStyleClass != null) {
			if (focusStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.FOCUS_STYLE_CLASS, focusStyleClass);

			} else {
				component.setFocusStyleClass(focusStyleClass.getExpressionString());
			}
		}
	}

	public void release() {
		disabled = null;
		accessKey = null;
		tabIndex = null;
		blurListeners = null;
		focusListeners = null;
		keyUpListeners = null;
		keyDownListeners = null;
		keyPressListeners = null;
		immediate = null;
		validationListeners = null;
		selectionListeners = null;
		focusStyleClass = null;

		super.release();
	}

}
