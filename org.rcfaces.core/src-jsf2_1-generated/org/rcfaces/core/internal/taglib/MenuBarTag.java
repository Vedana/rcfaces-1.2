package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.MenuBarComponent;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class MenuBarTag extends AbstractMenuTag implements Tag {


	private static final Log LOG=LogFactory.getLog(MenuBarTag.class);

	private ValueExpression actionListeners;
	private ValueExpression action;
	public String getComponentType() {
		return MenuBarComponent.COMPONENT_TYPE;
	}

	public final void setAction(ValueExpression action) {
		this.action=action;
	}

	public final void setActionListener(ValueExpression listeners) {
		this.actionListeners = listeners;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (MenuBarComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  action='"+action+"'");
			LOG.debug("  actionListeners='"+actionListeners+"'");
		}
		if ((uiComponent instanceof MenuBarComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'MenuBarComponent'.");
		}

		super.setProperties(uiComponent);

		MenuBarComponent component = (MenuBarComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (action != null) {
			ListenersTools1_2.parseAction(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, action);
		}

		if (actionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, actionListeners, true);
		}
	}

	public void release() {
		action = null;
		actionListeners = null;

		super.release();
	}

}
