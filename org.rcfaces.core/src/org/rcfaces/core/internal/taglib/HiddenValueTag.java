package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.HiddenValueComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class HiddenValueTag extends CameliaTag {


	private static final Log LOG=LogFactory.getLog(HiddenValueTag.class);

	private String propertyChangeListeners;
	private String immediate;
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
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof HiddenValueComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'HiddenValueComponent'.");
		}

		HiddenValueComponent component = (HiddenValueComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (propertyChangeListeners != null) {
			parseActionListener(application, component, PROPERTY_CHANGE_LISTENER_TYPE, propertyChangeListeners);
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

		if (converter != null) {
			if (isValueReference(converter)) {
				ValueBinding vb = application.createValueBinding(converter);
				component.setConverter(vb);
			} else {
				component.setConverter(converter);
			}
		}
	}

	public void release() {
		propertyChangeListeners = null;
		immediate = null;
		value = null;
		converter = null;

		super.release();
	}

}
