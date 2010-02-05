package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.HiddenValueComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class HiddenValueTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(HiddenValueTag.class);

	private String propertyChangeListeners;
	private String immediate;
	private String valueLocked;
	private String validationListeners;
	private String userEventListeners;
	private String value;
	private String converter;
	public String getComponentType() {
		return HiddenValueComponent.COMPONENT_TYPE;
	}

	public final String getPropertyChangeListener() {
		return propertyChangeListeners;
	}

	public final void setPropertyChangeListener(String propertyChangeListeners) {
		this.propertyChangeListeners = propertyChangeListeners;
	}

	public final String getImmediate() {
		return immediate;
	}

	public final void setImmediate(String immediate) {
		this.immediate = immediate;
	}

	public final String getValueLocked() {
		return valueLocked;
	}

	public final void setValueLocked(String valueLocked) {
		this.valueLocked = valueLocked;
	}

	public final String getValidationListener() {
		return validationListeners;
	}

	public final void setValidationListener(String validationListeners) {
		this.validationListeners = validationListeners;
	}

	public final String getUserEventListener() {
		return userEventListeners;
	}

	public final void setUserEventListener(String userEventListeners) {
		this.userEventListeners = userEventListeners;
	}

	public final String getValue() {
		return value;
	}

	public final void setValue(String value) {
		this.value = value;
	}

	public final String getConverter() {
		return converter;
	}

	public final void setConverter(String converter) {
		this.converter = converter;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (HiddenValueComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  immediate='"+immediate+"'");
			LOG.debug("  valueLocked='"+valueLocked+"'");
		}
		if ((uiComponent instanceof HiddenValueComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'HiddenValueComponent'.");
		}

		super.setProperties(uiComponent);

		HiddenValueComponent component = (HiddenValueComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (propertyChangeListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.PROPERTY_CHANGE_LISTENER_TYPE, propertyChangeListeners);
		}

		if (immediate != null) {
			if (isValueReference(immediate)) {
				ValueBinding vb = application.createValueBinding(immediate);
				component.setValueBinding(Properties.IMMEDIATE, vb);

			} else {
				component.setImmediate(getBool(immediate));
			}
		}

		if (valueLocked != null) {
			if (isValueReference(valueLocked)) {
				ValueBinding vb = application.createValueBinding(valueLocked);
				component.setValueBinding(Properties.VALUE_LOCKED, vb);

			} else {
				component.setValueLocked(getBool(valueLocked));
			}
		}

		if (validationListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.VALIDATION_LISTENER_TYPE, validationListeners);
		}

		if (userEventListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.USER_EVENT_LISTENER_TYPE, userEventListeners);
		}

		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = application.createValueBinding(value);
				component.setValueBinding(Properties.VALUE, vb);

			} else {
				component.setValue(value);
			}
		}

		if (converter != null) {
			if (isValueReference(converter)) {
				ValueBinding vb = application.createValueBinding(converter);
				component.setValueBinding(Properties.CONVERTER, vb);

			} else {
				component.setConverter(converter);
			}
		}
	}

	public void release() {
		propertyChangeListeners = null;
		immediate = null;
		valueLocked = null;
		validationListeners = null;
		userEventListeners = null;
		value = null;
		converter = null;

		super.release();
	}

}
