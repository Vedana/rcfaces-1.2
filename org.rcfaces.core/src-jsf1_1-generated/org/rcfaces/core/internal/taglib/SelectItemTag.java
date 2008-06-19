package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.SelectItemComponent;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class SelectItemTag extends AbstractItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(SelectItemTag.class);

	private String toolTipText;
	public String getComponentType() {
		return SelectItemComponent.COMPONENT_TYPE;
	}

	public final String getToolTipText() {
		return toolTipText;
	}

	public final void setToolTipText(String toolTipText) {
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
		Application application = facesContext.getApplication();

		if (toolTipText != null) {
			if (isValueReference(toolTipText)) {
				ValueBinding vb = application.createValueBinding(toolTipText);
				component.setValueBinding(Properties.TOOL_TIP_TEXT, vb);

			} else {
				component.setToolTipText(toolTipText);
			}
		}
	}

	public void release() {
		toolTipText = null;

		super.release();
	}

}
