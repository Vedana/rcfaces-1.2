package org.rcfaces.core.component;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IRadioGroupCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.TreeTools;

public class TreeNodeComponent extends ExpandableItemComponent implements 
	IRadioGroupCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.treeNode";


	public TreeNodeComponent() {
		setRendererType(null);
	}

	public TreeNodeComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setExpanded(boolean expanded) {


				setExpanded(null, expanded);
			
	}

	public final void setExpanded(FacesContext context, boolean expanded) {


				getTree().setExpanded(context, getItemValue(), expanded);
			
	}

	public final boolean isExpanded() {


				return isExpanded(null);
			
	}

	public final boolean isExpanded(FacesContext context) {


				return getTree().isExpanded(context, getItemValue());
			
	}

	public final TreeComponent getTree() {


			return TreeTools.getTree(this);
			
	}

	public final java.lang.String getGroupName() {
		return getGroupName(null);
	}

	public final java.lang.String getGroupName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.GROUP_NAME, facesContext);
	}

	public final void setGroupName(java.lang.String groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	public final void setGroupName(ValueBinding groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	public final String getInputType() {
		return getInputType(null);
	}

	public final String getInputType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.INPUT_TYPE, facesContext);
	}

	public final void setInputType(String inputType) {
		engine.setProperty(Properties.INPUT_TYPE, inputType);
	}

	public final void setInputType(ValueBinding inputType) {
		engine.setProperty(Properties.INPUT_TYPE, inputType);
	}

	public final boolean isInputTypeSetted() {
		return engine.isPropertySetted(Properties.INPUT_TYPE);
	}

	public void release() {
		super.release();
	}
}
