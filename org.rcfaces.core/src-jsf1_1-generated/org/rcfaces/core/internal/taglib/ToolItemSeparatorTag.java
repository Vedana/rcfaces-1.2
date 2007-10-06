package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.component.ToolItemSeparatorComponent;
import javax.faces.context.FacesContext;

public class ToolItemSeparatorTag extends AbstractSeparatorTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ToolItemSeparatorTag.class);

	private String alternateText;
	public String getComponentType() {
		return ToolItemSeparatorComponent.COMPONENT_TYPE;
	}

	public final String getAlternateText() {
		return alternateText;
	}

	public final void setAlternateText(String alternateText) {
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
		Application application = facesContext.getApplication();

		if (alternateText != null) {
			if (isValueReference(alternateText)) {
				ValueBinding vb = application.createValueBinding(alternateText);
				component.setValueBinding(Properties.ALTERNATE_TEXT, vb);

			} else {
				component.setAlternateText(alternateText);
			}
		}
	}

	public void release() {
		alternateText = null;

		super.release();
	}

}
