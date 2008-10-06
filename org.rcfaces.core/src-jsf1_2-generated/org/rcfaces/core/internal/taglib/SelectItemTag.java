package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.rcfaces.core.component.SelectItemComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class SelectItemTag extends AbstractItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(SelectItemTag.class);

	private ValueExpression toolTipText;
	public String getComponentType() {
		return SelectItemComponent.COMPONENT_TYPE;
	}

	public final void setToolTipText(ValueExpression toolTipText) {
		this.toolTipText = toolTipText;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (SelectItemComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  toolTipText='"+toolTipText+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof SelectItemComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'SelectItemComponent'.");
		}

		SelectItemComponent component = (SelectItemComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (toolTipText != null) {
			if (toolTipText.isLiteralText()==false) {
				component.setValueExpression(Properties.TOOL_TIP_TEXT, toolTipText);

			} else {
				component.setToolTipText(toolTipText.getExpressionString());
			}
		}
	}

	public void release() {
		toolTipText = null;

		super.release();
	}

}
