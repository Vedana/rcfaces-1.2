package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.CriteriaItemComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class CriteriaItemTag extends SelectItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CriteriaItemTag.class);

	private ValueExpression logicalFilter;
	private ValueExpression startingValue;
	public String getComponentType() {
		return CriteriaItemComponent.COMPONENT_TYPE;
	}

	public void setLogicalFilter(ValueExpression logicalFilter) {
		this.logicalFilter = logicalFilter;
	}

	public void setStartingValue(ValueExpression startingValue) {
		this.startingValue = startingValue;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CriteriaItemComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  logicalFilter='"+logicalFilter+"'");
			LOG.debug("  startingValue='"+startingValue+"'");
		}
		if ((uiComponent instanceof CriteriaItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CriteriaItemComponent'.");
		}

		super.setProperties(uiComponent);

		CriteriaItemComponent component = (CriteriaItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (logicalFilter != null) {
			if (logicalFilter.isLiteralText()==false) {
				component.setValueExpression(Properties.LOGICAL_FILTER, logicalFilter);

			} else {
				component.setLogicalFilter(logicalFilter.getExpressionString());
			}
		}

		if (startingValue != null) {
			if (startingValue.isLiteralText()==false) {
				component.setValueExpression(Properties.STARTING_VALUE, startingValue);

			} else {
				component.setStartingValue(startingValue.getExpressionString());
			}
		}
	}

	public void release() {
		logicalFilter = null;
		startingValue = null;

		super.release();
	}

}
