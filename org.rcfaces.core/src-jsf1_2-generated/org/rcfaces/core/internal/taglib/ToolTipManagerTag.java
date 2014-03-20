package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ToolTipManagerComponent;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ToolTipManagerTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ToolTipManagerTag.class);

	private ValueExpression showDelayMs;
	private ValueExpression neighbourThresholdMs;
	public String getComponentType() {
		return ToolTipManagerComponent.COMPONENT_TYPE;
	}

	public void setShowDelayMs(ValueExpression showDelayMs) {
		this.showDelayMs = showDelayMs;
	}

	public void setNeighbourThresholdMs(ValueExpression neighbourThresholdMs) {
		this.neighbourThresholdMs = neighbourThresholdMs;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ToolTipManagerComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  showDelayMs='"+showDelayMs+"'");
			LOG.debug("  neighbourThresholdMs='"+neighbourThresholdMs+"'");
		}
		if ((uiComponent instanceof ToolTipManagerComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ToolTipManagerComponent'.");
		}

		super.setProperties(uiComponent);

		ToolTipManagerComponent component = (ToolTipManagerComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (showDelayMs != null) {
			if (showDelayMs.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_DELAY_MS, showDelayMs);

			} else {
				component.setShowDelayMs(getInt(showDelayMs.getExpressionString()));
			}
		}

		if (neighbourThresholdMs != null) {
			if (neighbourThresholdMs.isLiteralText()==false) {
				component.setValueExpression(Properties.NEIGHBOUR_THRESHOLD_MS, neighbourThresholdMs);

			} else {
				component.setNeighbourThresholdMs(getInt(neighbourThresholdMs.getExpressionString()));
			}
		}
	}

	public void release() {
		showDelayMs = null;
		neighbourThresholdMs = null;

		super.release();
	}

}
