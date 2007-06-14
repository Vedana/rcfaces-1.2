package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.TextEditorImageButtonComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class TextEditorImageButtonTag extends ImageButtonTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TextEditorImageButtonTag.class);

	private String forValue;
	private String groupName;
	private String type;
	public String getComponentType() {
		return TextEditorImageButtonComponent.COMPONENT_TYPE;
	}

	public final String getFor() {
		return forValue;
	}

	public final void setFor(String forValue) {
		this.forValue = forValue;
	}

	public final String getGroupName() {
		return groupName;
	}

	public final void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public final String getType() {
		return type;
	}

	public final void setType(String type) {
		this.type = type;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (TextEditorImageButtonComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  forValue='"+forValue+"'");
			LOG.debug("  groupName='"+groupName+"'");
			LOG.debug("  type='"+type+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof TextEditorImageButtonComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TextEditorImageButtonComponent'.");
		}

		TextEditorImageButtonComponent component = (TextEditorImageButtonComponent) uiComponent;
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

		if (groupName != null) {
			if (isValueReference(groupName)) {
				ValueBinding vb = application.createValueBinding(groupName);
				component.setValueBinding(Properties.GROUP_NAME, vb);

			} else {
				component.setGroupName(groupName);
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
		groupName = null;
		type = null;

		super.release();
	}

}
