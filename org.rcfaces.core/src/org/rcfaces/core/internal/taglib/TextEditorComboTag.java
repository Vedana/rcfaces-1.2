package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.TextEditorComboComponent;
import javax.faces.application.Application;

public class TextEditorComboTag extends ComboTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TextEditorComboTag.class);

	private String forValue;
	private String type;
	public String getComponentType() {
		return TextEditorComboComponent.COMPONENT_TYPE;
	}

	public final String getFor() {
		return forValue;
	}

	public final void setFor(String forValue) {
		this.forValue = forValue;
	}

	public final String getType() {
		return type;
	}

	public final void setType(String type) {
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
		Application application = facesContext.getApplication();

		if (forValue != null) {
			if (isValueReference(forValue)) {
				ValueBinding vb = application.createValueBinding(forValue);
				component.setValueBinding(Properties.FOR, vb);

			} else {
				component.setFor(forValue);
			}
		}

		if (type != null) {
			if (isValueReference(type)) {
				ValueBinding vb = application.createValueBinding(type);
				component.setValueBinding(Properties.TYPE, vb);

			} else {
				component.setType(type);
			}
		}
	}

	public void release() {
		forValue = null;
		type = null;

		super.release();
	}

}
