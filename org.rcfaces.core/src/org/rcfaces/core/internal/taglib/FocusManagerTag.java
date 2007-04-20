package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.FocusManagerComponent;

public class FocusManagerTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(FocusManagerTag.class);

	private String focusId;
	public String getComponentType() {
		return FocusManagerComponent.COMPONENT_TYPE;
	}

	public final String getFocusId() {
		return focusId;
	}

	public final void setFocusId(String focusId) {
		this.focusId = focusId;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (FocusManagerComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  focusId='"+focusId+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof FocusManagerComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'FocusManagerComponent'.");
		}

		FocusManagerComponent component = (FocusManagerComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (focusId != null) {
			if (isValueReference(focusId)) {
				ValueBinding vb = application.createValueBinding(focusId);
				component.setFocusId(vb);
			} else {
				component.setFocusId(focusId);
			}
		}
	}

	public void release() {
		focusId = null;

		super.release();
	}

}
