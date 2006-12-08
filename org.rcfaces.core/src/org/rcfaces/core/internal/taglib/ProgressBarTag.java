package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ProgressBarComponent;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ProgressBarTag extends AbstractOutputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ProgressBarTag.class);

	private String minimum;
	private String maximum;
	private String indeterminate;
	public String getComponentType() {
		return ProgressBarComponent.COMPONENT_TYPE;
	}

	public final String getMinimum() {
		return minimum;
	}

	public final void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public final String getMaximum() {
		return maximum;
	}

	public final void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public final String getIndeterminate() {
		return indeterminate;
	}

	public final void setIndeterminate(String indeterminate) {
		this.indeterminate = indeterminate;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ProgressBarComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  minimum='"+minimum+"'");
			LOG.debug("  maximum='"+maximum+"'");
			LOG.debug("  indeterminate='"+indeterminate+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ProgressBarComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ProgressBarComponent'.");
		}

		ProgressBarComponent component = (ProgressBarComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (minimum != null) {
			if (isValueReference(minimum)) {
				ValueBinding vb = application.createValueBinding(minimum);
				component.setMinimum(vb);
			} else {
				component.setMinimum(getDouble(minimum));
			}
		}

		if (maximum != null) {
			if (isValueReference(maximum)) {
				ValueBinding vb = application.createValueBinding(maximum);
				component.setMaximum(vb);
			} else {
				component.setMaximum(getDouble(maximum));
			}
		}

		if (indeterminate != null) {
			if (isValueReference(indeterminate)) {
				ValueBinding vb = application.createValueBinding(indeterminate);
				component.setIndeterminate(vb);
			} else {
				component.setIndeterminate(getBool(indeterminate));
			}
		}
	}

	public void release() {
		minimum = null;
		maximum = null;
		indeterminate = null;

		super.release();
	}

}
