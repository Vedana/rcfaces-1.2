package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.AcceleratorComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class AcceleratorTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AcceleratorTag.class);

	private String keyPressListeners;
	private String forValue;
	private String forItemValue;
	private String keyBinding;
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

	public final String getFor() {
		return forValue;
	}

	public final void setFor(String forValue) {
		this.forValue = forValue;
	}

	public final String getForItemValue() {
		return forItemValue;
	}

	public final void setForItemValue(String forItemValue) {
		this.forItemValue = forItemValue;
	}

	public final String getKeyBinding() {
		return keyBinding;
	}

	public final void setKeyBinding(String keyBinding) {
		this.keyBinding = keyBinding;
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

	public final String getActionListener() {
		return actionListeners;
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
			LOG.debug("  action='"+action+"'");
			LOG.debug("  actionListeners='"+actionListeners+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof AcceleratorComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AcceleratorComponent'.");
		}

		AcceleratorComponent component = (AcceleratorComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (keyPressListeners != null) {
			Listeners.parseListener(facesContext, component, Listeners.KEY_PRESS_LISTENER_TYPE, keyPressListeners);
		}

		if (forValue != null) {
			if (isValueReference(forValue)) {
				ValueBinding vb = application.createValueBinding(forValue);

				component.setFor(vb);
			} else {
				component.setFor(forValue);
			}
		}

		if (forItemValue != null) {
			if (isValueReference(forItemValue)) {
				ValueBinding vb = application.createValueBinding(forItemValue);
				component.setForItemValue(vb);
			} else {
				component.setForItemValue(forItemValue);
			}
		}

		if (keyBinding != null) {
			if (isValueReference(keyBinding)) {
				ValueBinding vb = application.createValueBinding(keyBinding);
				component.setKeyBinding(vb);
			} else {
				component.setKeyBinding(keyBinding);
			}
		}

		if (action != null) {
			Listeners.parseAction(facesContext, component, Listeners.KEY_PRESS_LISTENER_TYPE, action);
		}

		if (actionListeners != null) {
			Listeners.parseListener(facesContext, component, Listeners.KEY_PRESS_LISTENER_TYPE, actionListeners, true);
		}

		if (immediate != null) {
			if (isValueReference(immediate)) {
				ValueBinding vb = application.createValueBinding(immediate);
				component.setImmediate(vb);
			} else {
				component.setImmediate(getBool(immediate));
			}
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
		keyPressListeners = null;
		forValue = null;
		forItemValue = null;
		keyBinding = null;
		action = null;
		actionListeners = null;
		immediate = null;
		value = null;

		super.release();
	}

}
