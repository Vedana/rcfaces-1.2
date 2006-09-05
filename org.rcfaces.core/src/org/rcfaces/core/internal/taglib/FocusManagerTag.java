package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.FocusManagerComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class FocusManagerTag extends CameliaTag {


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
