package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.TabbedPaneComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class TabbedPaneTag extends CardBoxTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TabbedPaneTag.class);

	private String closeListeners;
	private String closable;
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

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (TabbedPaneComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  closable='"+closable+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof TabbedPaneComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TabbedPaneComponent'.");
		}

		TabbedPaneComponent component = (TabbedPaneComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (closeListeners != null) {
			Listeners.parseListener(facesContext, component, Listeners.CLOSE_LISTENER_TYPE, closeListeners);
		}

		if (closable != null) {
			if (isValueReference(closable)) {
				ValueBinding vb = application.createValueBinding(closable);

				component.setClosable(vb);
			} else {
				component.setClosable(getBool(closable));
			}
		}
	}

	public void release() {
		closeListeners = null;
		closable = null;

		super.release();
	}

}
