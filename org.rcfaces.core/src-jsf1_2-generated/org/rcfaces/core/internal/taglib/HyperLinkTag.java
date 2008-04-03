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
import org.rcfaces.core.component.HyperLinkComponent;
import javax.faces.context.FacesContext;

public class HyperLinkTag extends ButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(HyperLinkTag.class);

	private ValueExpression converter;
	public String getComponentType() {
		return HyperLinkComponent.COMPONENT_TYPE;
	}

	public final void setConverter(ValueExpression converter) {
		this.converter = converter;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (HyperLinkComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  converter='"+converter+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof HyperLinkComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'HyperLinkComponent'.");
		}

		HyperLinkComponent component = (HyperLinkComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (converter != null) {
			if (converter.isLiteralText()==false) {
				component.setValueExpression(Properties.CONVERTER, converter);

			} else {
				component.setConverter(converter.getExpressionString());
			}
		}
	}

	public void release() {
		converter = null;

		super.release();
	}

}
