package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.SpinnerComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class SpinnerTag extends TextEntryTag {

private static final Log LOG=LogFactory.getLog(SpinnerTag.class);
	private String minimum;
	private String maximum;
	public String getComponentType() {
		return SpinnerComponent.COMPONENT_TYPE;
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

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof SpinnerComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'SpinnerComponent'.");
		}

		SpinnerComponent component = (SpinnerComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (minimum != null) {
			if (isValueReference(minimum)) {
				ValueBinding vb = application.createValueBinding(minimum);
				component.setMinimum(vb);
			} else {
				component.setMinimum(getInt(minimum));
			}
		}

		if (maximum != null) {
			if (isValueReference(maximum)) {
				ValueBinding vb = application.createValueBinding(maximum);
				component.setMaximum(vb);
			} else {
				component.setMaximum(getInt(maximum));
			}
		}
	}

	public void release() {
		minimum = null;
		maximum = null;

		super.release();
	}

}
