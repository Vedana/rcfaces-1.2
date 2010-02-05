package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.component.ViewErrorListenerComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ViewErrorListenerTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ViewErrorListenerTag.class);

	private ValueExpression propertyChangeListeners;
	private ValueExpression errorListeners;
	public String getComponentType() {
		return ViewErrorListenerComponent.COMPONENT_TYPE;
	}

	public final void setPropertyChangeListener(ValueExpression propertyChangeListeners) {
		this.propertyChangeListeners = propertyChangeListeners;
	}

	public final void setErrorListener(ValueExpression errorListeners) {
		this.errorListeners = errorListeners;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ViewErrorListenerComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
		}
		if ((uiComponent instanceof ViewErrorListenerComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ViewErrorListenerComponent'.");
		}

		super.setProperties(uiComponent);

		ViewErrorListenerComponent component = (ViewErrorListenerComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (propertyChangeListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.PROPERTY_CHANGE_LISTENER_TYPE, propertyChangeListeners);
		}

		if (errorListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.ERROR_LISTENER_TYPE, errorListeners);
		}
	}

	public void release() {
		propertyChangeListeners = null;
		errorListeners = null;

		super.release();
	}

}
