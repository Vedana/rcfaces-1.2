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
import org.rcfaces.core.component.ProgressIndicatorComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ProgressIndicatorTag extends AbstractBasicTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ProgressIndicatorTag.class);

	private String indeterminate;
	public String getComponentType() {
		return ProgressIndicatorComponent.COMPONENT_TYPE;
	}

	public final void setIndeterminate(String indeterminate) {
		this.indeterminate = indeterminate;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ProgressIndicatorComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  indeterminate='"+indeterminate+"'");
		}
		if ((uiComponent instanceof ProgressIndicatorComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ProgressIndicatorComponent'.");
		}

		super.setProperties(uiComponent);

		ProgressIndicatorComponent component = (ProgressIndicatorComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (indeterminate != null) {
			if (isValueReference(indeterminate)) {
				ValueBinding vb = application.createValueBinding(indeterminate);
				component.setValueBinding(Properties.INDETERMINATE, vb);

			} else {
				component.setIndeterminate(getBool(indeterminate));
			}
		}
	}

	public void release() {
		indeterminate = null;

		super.release();
	}

}
