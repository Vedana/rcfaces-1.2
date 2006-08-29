package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ServiceComponent;

import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ServiceTag extends CameliaTag {

private static final Log LOG=LogFactory.getLog(ServiceTag.class);
	private String propertyChangeListeners;
	private String serviceEventListeners;
	private String filterProperties;
	private String serviceId;
	public String getComponentType() {
		return ServiceComponent.COMPONENT_TYPE;
	}

	public final String getPropertyChangeListener() {
		return propertyChangeListeners;
	}

	public final void setPropertyChangeListener(String propertyChangeListeners) {
		this.propertyChangeListeners = propertyChangeListeners;
	}

	public final String getServiceEventListener() {
		return serviceEventListeners;
	}

	public final void setServiceEventListener(String serviceEventListeners) {
		this.serviceEventListeners = serviceEventListeners;
	}

	public final String getFilterProperties() {
		return filterProperties;
	}

	public final void setFilterProperties(String filterProperties) {
		this.filterProperties = filterProperties;
	}

	public final String getServiceId() {
		return serviceId;
	}

	public final void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ServiceComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ServiceComponent'.");
		}

		ServiceComponent component = (ServiceComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (propertyChangeListeners != null) {
			parseActionListener(application, component, PROPERTY_CHANGE_LISTENER_TYPE, propertyChangeListeners);
		}

		if (serviceEventListeners != null) {
			parseActionListener(application, component, SERVICE_EVENT_LISTENER_TYPE, serviceEventListeners);
		}

		if (filterProperties != null) {
				ValueBinding vb = application.createValueBinding(filterProperties);

				component.setFilterProperties(vb);
		}

		if (serviceId != null) {
			if (isValueReference(serviceId)) {
				ValueBinding vb = application.createValueBinding(serviceId);
				component.setServiceId(vb);
			} else {
				component.setServiceId(serviceId);
			}
		}
	}

	public void release() {
		propertyChangeListeners = null;
		serviceEventListeners = null;
		filterProperties = null;
		serviceId = null;

		super.release();
	}

}
