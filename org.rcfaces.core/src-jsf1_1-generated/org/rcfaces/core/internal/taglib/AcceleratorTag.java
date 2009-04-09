package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.AcceleratorComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class AcceleratorTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AcceleratorTag.class);

	private String keyPressListeners;
	private String validationListeners;
	private String forValue;
	private String forItemValue;
	private String keyBinding;
	private String ignoreEditableComponent;
	private String action;
	private String actionListeners;
	private String immediate;
	private String value;
	public String getComponentType() {
		return AcceleratorComponent.COMPONENT_TYPE;
	}

	public final String getKeyPressListener() {
		return keyPressListeners;
	}

	public final void setKeyPressListener(String keyPressListeners) {
		this.keyPressListeners = keyPressListeners;
	}

	public final String getValidationListener() {
		return validationListeners;
	}

	public final void setValidationListener(String validationListeners) {
		this.validationListeners = validationListeners;
	}

	public final String getFor() {
		return forValue;
	}

	public final void setFor(String forValue) {
		this.forValue = forValue;
	}

	public final void setForItemValue(String forItemValue) {
		this.forItemValue = forItemValue;
	}

	public final void setKeyBinding(String keyBinding) {
		this.keyBinding = keyBinding;
	}

	public final void setIgnoreEditableComponent(String ignoreEditableComponent) {
		this.ignoreEditableComponent = ignoreEditableComponent;
	}

	public final void setAction(String action) {
		this.action=action;
	}

	public final String getAction() {
		return action;
	}

	public final void setActionListener(String listeners) {
		this.actionListeners = listeners;
	}

	public final String getImmediate() {
		return immediate;
	}

	public final void setImmediate(String immediate) {
		this.immediate = immediate;
	}

	public final String getValue() {
		return value;
	}

	public final void setValue(String value) {
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
		super.setProperties(uiComponent);

		if ((uiComponent instanceof AcceleratorComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AcceleratorComponent'.");
		}

		AcceleratorComponent component = (AcceleratorComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (keyPressListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.KEY_PRESS_LISTENER_TYPE, keyPressListeners);
		}

		if (validationListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.VALIDATION_LISTENER_TYPE, validationListeners);
		}

		if (forValue != null) {
			if (isValueReference(forValue)) {
				ValueBinding vb = application.createValueBinding(forValue);
				component.setValueBinding(Properties.FOR, vb);

			} else {
				component.setFor(forValue);
			}
		}

		if (forItemValue != null) {
			if (isValueReference(forItemValue)) {
				ValueBinding vb = application.createValueBinding(forItemValue);
				component.setValueBinding(Properties.FOR_ITEM_VALUE, vb);

			} else {
				component.setForItemValue(forItemValue);
			}
		}

		if (keyBinding != null) {
			if (isValueReference(keyBinding)) {
				ValueBinding vb = application.createValueBinding(keyBinding);
				component.setValueBinding(Properties.KEY_BINDING, vb);

			} else {
				component.setKeyBinding(keyBinding);
			}
		}

		if (ignoreEditableComponent != null) {
			if (isValueReference(ignoreEditableComponent)) {
				ValueBinding vb = application.createValueBinding(ignoreEditableComponent);
				component.setValueBinding(Properties.IGNORE_EDITABLE_COMPONENT, vb);

			} else {
				component.setIgnoreEditableComponent(getBool(ignoreEditableComponent));
			}
		}

		if (action != null) {
			ListenersTools1_1.parseAction(facesContext, component, ListenersTools.KEY_PRESS_LISTENER_TYPE, action);
		}

		if (actionListeners != null) {
			ListenersTools1_1.parseListener(facesContext, component, ListenersTools.KEY_PRESS_LISTENER_TYPE, actionListeners, true);
		}

		if (immediate != null) {
			if (isValueReference(immediate)) {
				ValueBinding vb = application.createValueBinding(immediate);
				component.setValueBinding(Properties.IMMEDIATE, vb);

			} else {
				component.setImmediate(getBool(immediate));
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
