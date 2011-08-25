package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ProgressBarComponent;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ProgressBarTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ProgressBarTag.class);

	private ValueExpression minimum;
	private ValueExpression maximum;
	private ValueExpression indeterminate;
	public String getComponentType() {
		return ProgressBarComponent.COMPONENT_TYPE;
	}

	public void setMinimum(ValueExpression minimum) {
		this.minimum = minimum;
	}

	public void setMaximum(ValueExpression maximum) {
		this.maximum = maximum;
	}

	public void setIndeterminate(ValueExpression indeterminate) {
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
		if ((uiComponent instanceof ProgressBarComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ProgressBarComponent'.");
		}

		super.setProperties(uiComponent);

		ProgressBarComponent component = (ProgressBarComponent) uiComponent;
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

		if (indeterminate != null) {
			if (indeterminate.isLiteralText()==false) {
				component.setValueExpression(Properties.INDETERMINATE, indeterminate);

			} else {
				component.setIndeterminate(getBool(indeterminate.getExpressionString()));
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
