package org.rcfaces.renderkit.svg.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.renderkit.svg.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.renderkit.svg.component.SVGDataColumnComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class SVGDataColumnTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(SVGDataColumnTag.class);

	private ValueExpression value;
	private ValueExpression converter;

	public String getComponentType() {
		return SVGDataColumnComponent.COMPONENT_TYPE;
	}

	public void setValue(ValueExpression value) {
		this.value = value;
	}

	public final void setConverter(ValueExpression converter) {
		this.converter=converter;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (SVGDataColumnComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  value='"+value+"'");
		}
		if ((uiComponent instanceof SVGDataColumnComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'SVGDataColumnComponent'.");
		}

		super.setProperties(uiComponent);

		SVGDataColumnComponent component = (SVGDataColumnComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

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
			component.setConverter(application.createConverter(converter.getExpressionString()));
		}
	}
	}

	public void release() {
		value = null;
		converter = null;

		super.release();
	}

}
