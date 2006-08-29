package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.TreeNodeComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class TreeNodeTag extends ExpandableItemTag {

private static final Log LOG=LogFactory.getLog(TreeNodeTag.class);
	private String groupName;
	private String inputType;
	public String getComponentType() {
		return TreeNodeComponent.COMPONENT_TYPE;
	}

	public final String getGroupName() {
		return groupName;
	}

	public final void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public final String getInputType() {
		return inputType;
	}

	public final void setInputType(String inputType) {
		this.inputType = inputType;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof TreeNodeComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TreeNodeComponent'.");
		}

		TreeNodeComponent component = (TreeNodeComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (groupName != null) {
			if (isValueReference(groupName)) {
				ValueBinding vb = application.createValueBinding(groupName);

				component.setGroupName(vb);
			} else {
				component.setGroupName(groupName);
			}
		}

		if (inputType != null) {
			if (isValueReference(inputType)) {
				ValueBinding vb = application.createValueBinding(inputType);
				component.setInputType(vb);
			} else {
				component.setInputType(inputType);
			}
		}
	}

	public void release() {
		groupName = null;
		inputType = null;

		super.release();
	}

}
