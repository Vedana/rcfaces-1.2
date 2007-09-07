package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.component.ToolItemSeparatorComponent;
import javax.faces.context.FacesContext;

public class ToolItemSeparatorTag extends AbstractSeparatorTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ToolItemSeparatorTag.class);

	private ValueExpression alternateText;
	public String getComponentType() {
		return ToolItemSeparatorComponent.COMPONENT_TYPE;
	}

	public final void setAlternateText(ValueExpression alternateText) {
		this.alternateText = alternateText;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ToolItemSeparatorComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  alternateText='"+alternateText+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ToolItemSeparatorComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ToolItemSeparatorComponent'.");
		}

		ToolItemSeparatorComponent component = (ToolItemSeparatorComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (alternateText != null) {
			if (alternateText.isLiteralText()==false) {
				component.setValueExpression(Properties.ALTERNATE_TEXT, alternateText);

			} else {
				component.setAlternateText(alternateText.getExpressionString());
			}
		}
	}

	public void release() {
		alternateText = null;

		super.release();
	}

}
