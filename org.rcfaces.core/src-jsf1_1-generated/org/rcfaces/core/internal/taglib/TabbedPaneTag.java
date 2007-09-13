package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.TabbedPaneComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class TabbedPaneTag extends CardBoxTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TabbedPaneTag.class);

	private String closeListeners;
	private String closable;
	private String showValue;
	public String getComponentType() {
		return TabbedPaneComponent.COMPONENT_TYPE;
	}

	public final String getCloseListener() {
		return closeListeners;
	}

	public final void setCloseListener(String closeListeners) {
		this.closeListeners = closeListeners;
	}

	public final String getClosable() {
		return closable;
	}

	public final void setClosable(String closable) {
		this.closable = closable;
	}

	public final String getShowValue() {
		return showValue;
	}

	public final void setShowValue(String showValue) {
		this.showValue = showValue;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (TabbedPaneComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  closable='"+closable+"'");
			LOG.debug("  showValue='"+showValue+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof TabbedPaneComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TabbedPaneComponent'.");
		}

		TabbedPaneComponent component = (TabbedPaneComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (closeListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.CLOSE_LISTENER_TYPE, closeListeners);
		}

		if (closable != null) {
			if (isValueReference(closable)) {
				ValueBinding vb = application.createValueBinding(closable);
				component.setValueBinding(Properties.CLOSABLE, vb);

			} else {
				component.setClosable(getBool(closable));
			}
		}

		if (showValue != null) {
			if (isValueReference(showValue)) {
				ValueBinding vb = application.createValueBinding(showValue);
				component.setValueBinding(Properties.SHOW_VALUE, vb);

			} else {
				component.setShowValue(showValue);
			}
		}
	}

	public void release() {
		closeListeners = null;
		closable = null;
		showValue = null;

		super.release();
	}

}
