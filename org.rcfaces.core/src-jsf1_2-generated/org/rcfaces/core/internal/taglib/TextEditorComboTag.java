package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.TextEditorComboComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class TextEditorComboTag extends ComboTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TextEditorComboTag.class);

	private ValueExpression forValue;
	private ValueExpression type;
	public String getComponentType() {
		return TextEditorComboComponent.COMPONENT_TYPE;
	}

	public final void setFor(ValueExpression forValue) {
		this.forValue = forValue;
	}

	public final void setType(ValueExpression type) {
		this.type = type;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (TextEditorComboComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  forValue='"+forValue+"'");
			LOG.debug("  type='"+type+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof TextEditorComboComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TextEditorComboComponent'.");
		}

		TextEditorComboComponent component = (TextEditorComboComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (forValue != null) {
			if (forValue.isLiteralText()==false) {
				component.setValueExpression(Properties.FOR, forValue);

			} else {
				component.setFor(forValue.getExpressionString());
			}
		}

		if (type != null) {
			if (type.isLiteralText()==false) {
				component.setValueExpression(Properties.TYPE, type);

			} else {
				component.setType(type.getExpressionString());
			}
		}
	}

	public void release() {
		forValue = null;
		type = null;

		super.release();
	}

}
