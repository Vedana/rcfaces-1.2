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
import org.rcfaces.core.component.HelpButtonComponent;
import javax.faces.context.FacesContext;

public class HelpButtonTag extends ImageButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(HelpButtonTag.class);

	private ValueExpression forValue;
	public String getComponentType() {
		return HelpButtonComponent.COMPONENT_TYPE;
	}

	public final void setFor(ValueExpression forValue) {
		this.forValue = forValue;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (HelpButtonComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  forValue='"+forValue+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof HelpButtonComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'HelpButtonComponent'.");
		}

		HelpButtonComponent component = (HelpButtonComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (forValue != null) {
			if (forValue.isLiteralText()==false) {
				component.setValueExpression(Properties.FOR, forValue);

			} else {
				component.setFor(forValue.getExpressionString());
			}
		}
	}

	public void release() {
		forValue = null;

		super.release();
	}

}
