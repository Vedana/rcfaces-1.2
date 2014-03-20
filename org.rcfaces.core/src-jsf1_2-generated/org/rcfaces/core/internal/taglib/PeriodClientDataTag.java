package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.component.PeriodClientDataComponent;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class PeriodClientDataTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(PeriodClientDataTag.class);

	private ValueExpression name;
	private ValueExpression value;
	public String getComponentType() {
		return PeriodClientDataComponent.COMPONENT_TYPE;
	}

	public void setName(ValueExpression name) {
		this.name = name;
	}

	public void setValue(ValueExpression value) {
		this.value = value;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (PeriodClientDataComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  name='"+name+"'");
			LOG.debug("  value='"+value+"'");
		}
		if ((uiComponent instanceof PeriodClientDataComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'PeriodClientDataComponent'.");
		}

		super.setProperties(uiComponent);

		PeriodClientDataComponent component = (PeriodClientDataComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (name != null) {
			if (name.isLiteralText()==false) {
				component.setValueExpression(Properties.NAME, name);

			} else {
				component.setName(name.getExpressionString());
			}
		}

		if (value != null) {
			if (value.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE, value);

			} else {
				component.setValue(value.getExpressionString());
			}
		}
	}

	public void release() {
		name = null;
		value = null;

		super.release();
	}

}
