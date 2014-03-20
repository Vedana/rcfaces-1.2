package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.CriteriaListComponent;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class CriteriaListTag extends AbstractBasicTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CriteriaListTag.class);

	private ValueExpression forValue;
	private ValueExpression criteriaFormat;
	private ValueExpression noCriteriaMessage;
	public String getComponentType() {
		return CriteriaListComponent.COMPONENT_TYPE;
	}

	public void setFor(ValueExpression forValue) {
		this.forValue = forValue;
	}

	public void setCriteriaFormat(ValueExpression criteriaFormat) {
		this.criteriaFormat = criteriaFormat;
	}

	public void setNoCriteriaMessage(ValueExpression noCriteriaMessage) {
		this.noCriteriaMessage = noCriteriaMessage;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CriteriaListComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  forValue='"+forValue+"'");
			LOG.debug("  criteriaFormat='"+criteriaFormat+"'");
			LOG.debug("  noCriteriaMessage='"+noCriteriaMessage+"'");
		}
		if ((uiComponent instanceof CriteriaListComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CriteriaListComponent'.");
		}

		super.setProperties(uiComponent);

		CriteriaListComponent component = (CriteriaListComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (forValue != null) {
			if (forValue.isLiteralText()==false) {
				component.setValueExpression(Properties.FOR, forValue);

			} else {
				component.setFor(forValue.getExpressionString());
			}
		}

		if (criteriaFormat != null) {
			if (criteriaFormat.isLiteralText()==false) {
				component.setValueExpression(Properties.CRITERIA_FORMAT, criteriaFormat);

			} else {
				component.setCriteriaFormat(criteriaFormat.getExpressionString());
			}
		}

		if (noCriteriaMessage != null) {
			if (noCriteriaMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.NO_CRITERIA_MESSAGE, noCriteriaMessage);

			} else {
				component.setNoCriteriaMessage(noCriteriaMessage.getExpressionString());
			}
		}
	}

	public void release() {
		forValue = null;
		criteriaFormat = null;
		noCriteriaMessage = null;

		super.release();
	}

}
