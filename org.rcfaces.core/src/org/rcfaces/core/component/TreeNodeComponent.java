package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IRadioGroupCapability;
import org.rcfaces.core.component.ExpandableItemComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.TreeComponent;
import org.rcfaces.core.component.capability.IInputTypeCapability;
import org.rcfaces.core.internal.tools.TreeTools;
import org.rcfaces.core.internal.converter.InputTypeConverter;

/**
 * A node belonging to a tree.
 */
public class TreeNodeComponent extends ExpandableItemComponent implements 
	IRadioGroupCapability,
	IInputTypeCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.treeNode";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ExpandableItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"inputType","groupName"}));
	}

	public TreeNodeComponent() {
		setRendererType(null);
	}

	public TreeNodeComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setInputType(String inputType) {


			setInputType(((Integer)InputTypeConverter.SINGLETON.getAsObject(null, this, inputType)).intValue());
		
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

	/**
	 * See {@link #getGroupName() getGroupName()} for more details
	 */
	public final java.lang.String getGroupName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.GROUP_NAME, facesContext);
	}

	public final void setGroupName(java.lang.String groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	/**
	 * See {@link #setGroupName(String) setGroupName(String)} for more details
	 */
	public final void setGroupName(ValueBinding groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	public final int getInputType() {
		return getInputType(null);
	}

	/**
	 * See {@link #getInputType() getInputType()} for more details
	 */
	public final int getInputType(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.INPUT_TYPE,0, facesContext);
	}

	public final void setInputType(int inputType) {
		engine.setProperty(Properties.INPUT_TYPE, inputType);
	}

	/**
	 * See {@link #setInputType(int) setInputType(int)} for more details
	 */
	public final void setInputType(ValueBinding inputType) {
		engine.setProperty(Properties.INPUT_TYPE, inputType);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
