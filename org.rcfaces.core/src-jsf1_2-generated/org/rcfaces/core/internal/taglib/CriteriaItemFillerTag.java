package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.CriteriaItemFillerComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class CriteriaItemFillerTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CriteriaItemFillerTag.class);

	private ValueExpression logicalFilter;
	private ValueExpression itemValue;
	public String getComponentType() {
		return CriteriaItemFillerComponent.COMPONENT_TYPE;
	}

	public void setLogicalFilter(ValueExpression logicalFilter) {
		this.logicalFilter = logicalFilter;
	}

	public void setItemValue(ValueExpression itemValue) {
		this.itemValue = itemValue;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CriteriaItemFillerComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  logicalFilter='"+logicalFilter+"'");
			LOG.debug("  itemValue='"+itemValue+"'");
		}
		if ((uiComponent instanceof CriteriaItemFillerComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CriteriaItemFillerComponent'.");
		}

		super.setProperties(uiComponent);

		CriteriaItemFillerComponent component = (CriteriaItemFillerComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (logicalFilter != null) {
			if (logicalFilter.isLiteralText()==false) {
				component.setValueExpression(Properties.LOGICAL_FILTER, logicalFilter);

			} else {
				component.setLogicalFilter(logicalFilter.getExpressionString());
			}
		}

		if (itemValue != null) {
			if (itemValue.isLiteralText()==false) {
				component.setValueExpression(Properties.ITEM_VALUE, itemValue);

			} else {
				component.setItemValue(itemValue.getExpressionString());
			}
		}
	}

	public void release() {
		logicalFilter = null;
		itemValue = null;

		super.release();
	}

}
