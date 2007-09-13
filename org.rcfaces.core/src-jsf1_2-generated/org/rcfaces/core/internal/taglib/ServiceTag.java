package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.rcfaces.core.component.ServiceComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ServiceTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ServiceTag.class);

	private ValueExpression propertyChangeListeners;
	private ValueExpression serviceEventListeners;
	private ValueExpression filterProperties;
	private ValueExpression componentLocale;
	private ValueExpression componentTimeZone;
	private ValueExpression serviceId;
	private ValueExpression enableViewState;
	public String getComponentType() {
		return ServiceComponent.COMPONENT_TYPE;
	}

	public final void setPropertyChangeListener(ValueExpression propertyChangeListeners) {
		this.propertyChangeListeners = propertyChangeListeners;
	}

	public final void setServiceEventListener(ValueExpression serviceEventListeners) {
		this.serviceEventListeners = serviceEventListeners;
	}

	public final void setFilterProperties(ValueExpression filterProperties) {
		this.filterProperties = filterProperties;
	}

	public final void setComponentLocale(ValueExpression componentLocale) {
		this.componentLocale = componentLocale;
	}

	public final void setComponentTimeZone(ValueExpression componentTimeZone) {
		this.componentTimeZone = componentTimeZone;
	}

	public final void setServiceId(ValueExpression serviceId) {
		this.serviceId = serviceId;
	}

	public final void setEnableViewState(ValueExpression enableViewState) {
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
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ServiceComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ServiceComponent'.");
		}

		ServiceComponent component = (ServiceComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (propertyChangeListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.PROPERTY_CHANGE_LISTENER_TYPE, propertyChangeListeners);
		}

		if (serviceEventListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SERVICE_EVENT_LISTENER_TYPE, serviceEventListeners);
		}

		if (filterProperties != null) {
				component.setValueExpression(Properties.FILTER_PROPERTIES, filterProperties);
		}

		if (componentLocale != null) {
			if (componentLocale.isLiteralText()==false) {
				component.setValueExpression(Properties.COMPONENT_LOCALE, componentLocale);

			} else {
				component.setComponentLocale(componentLocale.getExpressionString());
			}
		}

		if (componentTimeZone != null) {
			if (componentTimeZone.isLiteralText()==false) {
				component.setValueExpression(Properties.COMPONENT_TIME_ZONE, componentTimeZone);

			} else {
				component.setComponentTimeZone(componentTimeZone.getExpressionString());
			}
		}

		if (serviceId != null) {
			if (serviceId.isLiteralText()==false) {
				component.setValueExpression(Properties.SERVICE_ID, serviceId);

			} else {
				component.setServiceId(serviceId.getExpressionString());
			}
		}

		if (enableViewState != null) {
			if (enableViewState.isLiteralText()==false) {
				component.setValueExpression(Properties.ENABLE_VIEW_STATE, enableViewState);

			} else {
				component.setEnableViewState(getBool(enableViewState.getExpressionString()));
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
