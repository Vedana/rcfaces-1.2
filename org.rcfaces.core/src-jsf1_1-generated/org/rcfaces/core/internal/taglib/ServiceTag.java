package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.ServiceComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ServiceTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ServiceTag.class);

	private String propertyChangeListeners;
	private String serviceEventListeners;
	private String filterProperties;
	private String componentLocale;
	private String componentTimeZone;
	private String serviceId;
	private String enableViewState;
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

	public final String getComponentLocale() {
		return componentLocale;
	}

	public final void setComponentLocale(String componentLocale) {
		this.componentLocale = componentLocale;
	}

	public final String getComponentTimeZone() {
		return componentTimeZone;
	}

	public final void setComponentTimeZone(String componentTimeZone) {
		this.componentTimeZone = componentTimeZone;
	}

	public final void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public final void setEnableViewState(String enableViewState) {
		this.enableViewState = enableViewState;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ServiceComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  filterProperties='"+filterProperties+"'");
			LOG.debug("  componentLocale='"+componentLocale+"'");
			LOG.debug("  componentTimeZone='"+componentTimeZone+"'");
			LOG.debug("  serviceId='"+serviceId+"'");
			LOG.debug("  enableViewState='"+enableViewState+"'");
		}
		if ((uiComponent instanceof ServiceComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ServiceComponent'.");
		}

		super.setProperties(uiComponent);

		ServiceComponent component = (ServiceComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (propertyChangeListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.PROPERTY_CHANGE_LISTENER_TYPE, propertyChangeListeners);
		}

		if (serviceEventListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SERVICE_EVENT_LISTENER_TYPE, serviceEventListeners);
		}

		if (filterProperties != null) {
			if (isValueReference(filterProperties)) {
				ValueBinding vb = application.createValueBinding(filterProperties);
				component.setValueBinding(Properties.FILTER_PROPERTIES, vb);

			} else {
				component.setFilterProperties(filterProperties);
			}
		}

		if (componentLocale != null) {
			if (isValueReference(componentLocale)) {
				ValueBinding vb = application.createValueBinding(componentLocale);
				component.setValueBinding(Properties.COMPONENT_LOCALE, vb);

			} else {
				component.setComponentLocale(componentLocale);
			}
		}

		if (componentTimeZone != null) {
			if (isValueReference(componentTimeZone)) {
				ValueBinding vb = application.createValueBinding(componentTimeZone);
				component.setValueBinding(Properties.COMPONENT_TIME_ZONE, vb);

			} else {
				component.setComponentTimeZone(componentTimeZone);
			}
		}

		if (serviceId != null) {
			if (isValueReference(serviceId)) {
				ValueBinding vb = application.createValueBinding(serviceId);
				component.setValueBinding(Properties.SERVICE_ID, vb);

			} else {
				component.setServiceId(serviceId);
			}
		}

		if (enableViewState != null) {
			if (isValueReference(enableViewState)) {
				ValueBinding vb = application.createValueBinding(enableViewState);
				component.setValueBinding(Properties.ENABLE_VIEW_STATE, vb);

			} else {
				component.setEnableViewState(getBool(enableViewState));
			}
		}
	}

	public void release() {
		propertyChangeListeners = null;
		serviceEventListeners = null;
		filterProperties = null;
		componentLocale = null;
		componentTimeZone = null;
		serviceId = null;
		enableViewState = null;

		super.release();
	}

}
