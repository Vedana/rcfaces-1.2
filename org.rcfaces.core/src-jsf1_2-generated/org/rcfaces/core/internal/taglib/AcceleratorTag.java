package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.rcfaces.core.component.AcceleratorComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class AcceleratorTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AcceleratorTag.class);

	private ValueExpression keyPressListeners;
	private ValueExpression validationListeners;
	private ValueExpression forValue;
	private ValueExpression forItemValue;
	private ValueExpression keyBinding;
	private ValueExpression ignoreEditableComponent;
	private ValueExpression actionListeners;
	private ValueExpression action;
	private ValueExpression immediate;
	private ValueExpression value;
	public String getComponentType() {
		return AcceleratorComponent.COMPONENT_TYPE;
	}

	public final void setKeyPressListener(ValueExpression keyPressListeners) {
		this.keyPressListeners = keyPressListeners;
	}

	public final void setValidationListener(ValueExpression validationListeners) {
		this.validationListeners = validationListeners;
	}

	public final void setFor(ValueExpression forValue) {
		this.forValue = forValue;
	}

	public final void setForItemValue(ValueExpression forItemValue) {
		this.forItemValue = forItemValue;
	}

	public final void setKeyBinding(ValueExpression keyBinding) {
		this.keyBinding = keyBinding;
	}

	public final void setIgnoreEditableComponent(ValueExpression ignoreEditableComponent) {
		this.ignoreEditableComponent = ignoreEditableComponent;
	}

	public final void setAction(ValueExpression action) {
		this.action=action;
	}

	public final void setActionListener(ValueExpression listeners) {
		this.actionListeners = listeners;
	}

	public final void setImmediate(ValueExpression immediate) {
		this.immediate = immediate;
	}

	public final void setValue(ValueExpression value) {
		this.value = value;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (AcceleratorComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  forValue='"+forValue+"'");
			LOG.debug("  forItemValue='"+forItemValue+"'");
			LOG.debug("  keyBinding='"+keyBinding+"'");
			LOG.debug("  ignoreEditableComponent='"+ignoreEditableComponent+"'");
			LOG.debug("  action='"+action+"'");
			LOG.debug("  actionListeners='"+actionListeners+"'");
		}
		if ((uiComponent instanceof AcceleratorComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AcceleratorComponent'.");
		}

		super.setProperties(uiComponent);

		AcceleratorComponent component = (AcceleratorComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (keyPressListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.KEY_PRESS_LISTENER_TYPE, keyPressListeners);
		}

		if (validationListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.VALIDATION_LISTENER_TYPE, validationListeners);
		}

		if (forValue != null) {
			if (forValue.isLiteralText()==false) {
				component.setValueExpression(Properties.FOR, forValue);

			} else {
				component.setFor(forValue.getExpressionString());
			}
		}

		if (forItemValue != null) {
			if (forItemValue.isLiteralText()==false) {
				component.setValueExpression(Properties.FOR_ITEM_VALUE, forItemValue);

			} else {
				component.setForItemValue(forItemValue.getExpressionString());
			}
		}

		if (keyBinding != null) {
			if (keyBinding.isLiteralText()==false) {
				component.setValueExpression(Properties.KEY_BINDING, keyBinding);

			} else {
				component.setKeyBinding(keyBinding.getExpressionString());
			}
		}

		if (ignoreEditableComponent != null) {
			if (ignoreEditableComponent.isLiteralText()==false) {
				component.setValueExpression(Properties.IGNORE_EDITABLE_COMPONENT, ignoreEditableComponent);

			} else {
				component.setIgnoreEditableComponent(getBool(ignoreEditableComponent.getExpressionString()));
			}
		}

		if (action != null) {
			ListenersTools1_2.parseAction(facesContext, component, ListenersTools.KEY_PRESS_LISTENER_TYPE, action);
		}

		if (actionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.KEY_PRESS_LISTENER_TYPE, actionListeners, true);
		}

		if (immediate != null) {
			if (immediate.isLiteralText()==false) {
				component.setValueExpression(Properties.IMMEDIATE, immediate);

			} else {
				component.setImmediate(getBool(immediate.getExpressionString()));
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
		keyPressListeners = null;
		validationListeners = null;
		forValue = null;
		forItemValue = null;
		keyBinding = null;
		ignoreEditableComponent = null;
		action = null;
		actionListeners = null;
		immediate = null;
		value = null;

		super.release();
	}

}
