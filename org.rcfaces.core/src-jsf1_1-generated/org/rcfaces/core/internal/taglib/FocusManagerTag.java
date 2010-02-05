package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.component.FocusManagerComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class FocusManagerTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(FocusManagerTag.class);

	private String focusId;
	private String setFocusIfMessage;
	public String getComponentType() {
		return FocusManagerComponent.COMPONENT_TYPE;
	}

	public final void setFocusId(String focusId) {
		this.focusId = focusId;
	}

	public final void setSetFocusIfMessage(String setFocusIfMessage) {
		this.setFocusIfMessage = setFocusIfMessage;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (FocusManagerComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  focusId='"+focusId+"'");
			LOG.debug("  setFocusIfMessage='"+setFocusIfMessage+"'");
		}
		if ((uiComponent instanceof FocusManagerComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'FocusManagerComponent'.");
		}

		super.setProperties(uiComponent);

		FocusManagerComponent component = (FocusManagerComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (focusId != null) {
			if (isValueReference(focusId)) {
				ValueBinding vb = application.createValueBinding(focusId);
				component.setValueBinding(Properties.FOCUS_ID, vb);

			} else {
				component.setFocusId(focusId);
			}
		}

		if (setFocusIfMessage != null) {
			if (isValueReference(setFocusIfMessage)) {
				ValueBinding vb = application.createValueBinding(setFocusIfMessage);
				component.setValueBinding(Properties.SET_FOCUS_IF_MESSAGE, vb);

			} else {
				component.setSetFocusIfMessage(getBool(setFocusIfMessage));
			}
		}
	}

	public void release() {
		focusId = null;
		setFocusIfMessage = null;

		super.release();
	}

}
