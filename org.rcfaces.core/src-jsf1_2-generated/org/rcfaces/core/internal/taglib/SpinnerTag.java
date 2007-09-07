package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.SpinnerComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class SpinnerTag extends TextEntryTag implements Tag {


	private static final Log LOG=LogFactory.getLog(SpinnerTag.class);

	private ValueExpression minimum;
	private ValueExpression maximum;
	private ValueExpression cycleValue;
	private ValueExpression step;
	public String getComponentType() {
		return SpinnerComponent.COMPONENT_TYPE;
	}

	public final void setMinimum(ValueExpression minimum) {
		this.minimum = minimum;
	}

	public final void setMaximum(ValueExpression maximum) {
		this.maximum = maximum;
	}

	public final void setCycleValue(ValueExpression cycleValue) {
		this.cycleValue = cycleValue;
	}

	public final void setStep(ValueExpression step) {
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

		if (minimum != null) {
			if (minimum.isLiteralText()==false) {
				component.setValueExpression(Properties.MINIMUM, minimum);

			} else {
				component.setMinimum(getDouble(minimum.getExpressionString()));
			}
		}

		if (maximum != null) {
			if (maximum.isLiteralText()==false) {
				component.setValueExpression(Properties.MAXIMUM, maximum);

			} else {
				component.setMaximum(getDouble(maximum.getExpressionString()));
			}
		}

		if (cycleValue != null) {
			if (cycleValue.isLiteralText()==false) {
				component.setValueExpression(Properties.CYCLE_VALUE, cycleValue);

			} else {
				component.setCycleValue(getBool(cycleValue.getExpressionString()));
			}
		}

		if (step != null) {
			if (step.isLiteralText()==false) {
				component.setValueExpression(Properties.STEP, step);

			} else {
				component.setStep(step.getExpressionString());
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
