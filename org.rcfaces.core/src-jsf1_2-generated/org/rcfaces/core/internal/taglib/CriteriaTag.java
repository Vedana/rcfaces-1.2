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
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.component.CriteriaComponent;
import javax.faces.context.FacesContext;

public class CriteriaTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CriteriaTag.class);

	private ValueExpression value;
	private ValueExpression converter;
	public String getComponentType() {
		return CriteriaComponent.COMPONENT_TYPE;
	}

	public final void setValue(ValueExpression value) {
		this.value = value;
	}

	public final void setConverter(ValueExpression converter) {
		this.converter = converter;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CriteriaComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  value='"+value+"'");
			LOG.debug("  converter='"+converter+"'");
		}
		if ((uiComponent instanceof CriteriaComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CriteriaComponent'.");
		}

		super.setProperties(uiComponent);

		CriteriaComponent component = (CriteriaComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (value != null) {
			if (value.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE, value);

			} else {
				component.setValue(value.getExpressionString());
			}
		}

		if (converter != null) {
			if (converter.isLiteralText()==false) {
				component.setValueExpression(Properties.CONVERTER, converter);

			} else {
				component.setConverter(converter.getExpressionString());
			}
		}
	}

	public void release() {
		value = null;
		converter = null;

		super.release();
	}

}
