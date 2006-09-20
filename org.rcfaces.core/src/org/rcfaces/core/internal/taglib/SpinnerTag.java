package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.SpinnerComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class SpinnerTag extends TextEntryTag implements Tag {


	private static final Log LOG=LogFactory.getLog(SpinnerTag.class);

	private String minimum;
	private String maximum;
	private String step;
	private String cycleValue;
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

	public final String getStep() {
		return step;
	}

	public final void setStep(String step) {
		this.step = step;
	}

	public final String getCycleValue() {
		return cycleValue;
	}

	public final void setCycleValue(String cycleValue) {
		this.cycleValue = cycleValue;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (SpinnerComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  minimum='"+minimum+"'");
			LOG.debug("  maximum='"+maximum+"'");
			LOG.debug("  step='"+step+"'");
			LOG.debug("  cycleValue='"+cycleValue+"'");
		}
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

		if (step != null) {
			if (isValueReference(step)) {
				ValueBinding vb = application.createValueBinding(step);
				component.setStep(vb);
			} else {
				component.setStep(getDouble(step));
			}
		}

		if (cycleValue != null) {
			if (isValueReference(cycleValue)) {
				ValueBinding vb = application.createValueBinding(cycleValue);
				component.setCycleValue(vb);
			} else {
				component.setCycleValue(getBool(cycleValue));
			}
		}
	}

	public void release() {
		minimum = null;
		maximum = null;
		step = null;
		cycleValue = null;

		super.release();
	}

}
