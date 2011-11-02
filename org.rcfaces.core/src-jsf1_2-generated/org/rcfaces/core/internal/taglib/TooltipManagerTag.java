package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.component.TooltipManagerComponent;
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

public class TooltipManagerTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TooltipManagerTag.class);

	private ValueExpression delay;
	public String getComponentType() {
		return TooltipManagerComponent.COMPONENT_TYPE;
	}

	public void setDelay(ValueExpression delay) {
		this.delay = delay;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (TooltipManagerComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  delay='"+delay+"'");
		}
		if ((uiComponent instanceof TooltipManagerComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TooltipManagerComponent'.");
		}

		super.setProperties(uiComponent);

		TooltipManagerComponent component = (TooltipManagerComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (delay != null) {
			if (delay.isLiteralText()==false) {
				component.setValueExpression(Properties.DELAY, delay);

			} else {
				component.setDelay(getInt(delay.getExpressionString()));
			}
		}
	}

	public void release() {
		delay = null;

		super.release();
	}

}
