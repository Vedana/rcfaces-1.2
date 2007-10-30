package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.SpinnerComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class SpinnerTag extends TextEntryTag implements Tag {


	private static final Log LOG=LogFactory.getLog(SpinnerTag.class);

	private String minimum;
	private String maximum;
	private String cycleValue;
	private String step;
	public String getComponentType() {
		return SpinnerComponent.COMPONENT_TYPE;
	}

	public final void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public final void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public final void setCycleValue(String cycleValue) {
		this.cycleValue = cycleValue;
	}

	public final void setStep(String step) {
		this.step = step;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (SpinnerComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  minimum='"+minimum+"'");
			LOG.debug("  maximum='"+maximum+"'");
			LOG.debug("  cycleValue='"+cycleValue+"'");
			LOG.debug("  step='"+step+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof SpinnerComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'SpinnerComponent'.");
		}

		SpinnerComponent component = (SpinnerComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (minimum != null) {
			if (isValueReference(minimum)) {
				ValueBinding vb = application.createValueBinding(minimum);
				component.setValueBinding(Properties.MINIMUM, vb);

			} else {
				component.setMinimum(getDouble(minimum));
			}
		}

		if (maximum != null) {
			if (isValueReference(maximum)) {
				ValueBinding vb = application.createValueBinding(maximum);
				component.setValueBinding(Properties.MAXIMUM, vb);

			} else {
				component.setMaximum(getDouble(maximum));
			}
		}

		if (cycleValue != null) {
			if (isValueReference(cycleValue)) {
				ValueBinding vb = application.createValueBinding(cycleValue);
				component.setValueBinding(Properties.CYCLE_VALUE, vb);

			} else {
				component.setCycleValue(getBool(cycleValue));
			}
		}

		if (step != null) {
			if (isValueReference(step)) {
				ValueBinding vb = application.createValueBinding(step);
				component.setValueBinding(Properties.STEP, vb);

			} else {
				component.setStep(step);
			}
		}
	}

	public void release() {
		minimum = null;
		maximum = null;
		cycleValue = null;
		step = null;

		super.release();
	}

}
