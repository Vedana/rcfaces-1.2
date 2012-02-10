package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.HeadingComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class HeadingTag extends TextTag implements Tag {


	private static final Log LOG=LogFactory.getLog(HeadingTag.class);

	private ValueExpression level;
	public String getComponentType() {
		return HeadingComponent.COMPONENT_TYPE;
	}

	public void setLevel(ValueExpression level) {
		this.level = level;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (HeadingComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  level='"+level+"'");
		}
		if ((uiComponent instanceof HeadingComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'HeadingComponent'.");
		}

		super.setProperties(uiComponent);

		HeadingComponent component = (HeadingComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (level != null) {
			if (level.isLiteralText()==false) {
				component.setValueExpression(Properties.LEVEL, level);

			} else {
				component.setLevel(getInt(level.getExpressionString()));
			}
		}
	}

	public void release() {
		level = null;

		super.release();
	}

}
