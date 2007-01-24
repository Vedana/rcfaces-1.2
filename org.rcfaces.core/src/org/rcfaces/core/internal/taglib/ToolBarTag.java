package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ToolBarComponent;
import org.rcfaces.core.internal.tools.ListenersTools;

public class ToolBarTag extends AbstractBasicTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ToolBarTag.class);

	private String initListeners;
	public String getComponentType() {
		return ToolBarComponent.COMPONENT_TYPE;
	}

	public final String getInitListener() {
		return initListeners;
	}

	public final void setInitListener(String initListeners) {
		this.initListeners = initListeners;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ToolBarComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ToolBarComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ToolBarComponent'.");
		}

		ToolBarComponent component = (ToolBarComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (initListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.INIT_LISTENER_TYPE, initListeners);
		}
	}

	public void release() {
		initListeners = null;

		super.release();
	}

}
