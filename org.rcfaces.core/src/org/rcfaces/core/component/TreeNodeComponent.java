package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IRadioGroupCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ExpandableItemComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.TreeComponent;
import java.util.HashSet;
import org.rcfaces.core.internal.tools.TreeTools;

/**
 * A node belonging to a tree.
 */
public class TreeNodeComponent extends ExpandableItemComponent implements 
	IRadioGroupCapability {

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

	/**
	 * Experimental:	Returns a string value specifying the type of input to show radio button or check box.
	 * @return radio|check
	 */
	public final String getInputType() {
		return getInputType(null);
	}

	/**
	 * Experimental:	Returns a string value specifying the type of input to show radio button or check box.
	 * @return radio|check
	 */
	public final String getInputType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.INPUT_TYPE, facesContext);
	}

	/**
	 * Experimental:	Sets a string value specifying the type of input to show radio button or check box.
	 * @param inputType radio|check
	 */
	public final void setInputType(String inputType) {
		engine.setProperty(Properties.INPUT_TYPE, inputType);
	}

	/**
	 * Experimental:	Sets a string value specifying the type of input to show radio button or check box.
	 * @param inputType radio|check
	 */
	public final void setInputType(ValueBinding inputType) {
		engine.setProperty(Properties.INPUT_TYPE, inputType);
	}

	/**
	 * Returns <code>true</code> if the attribute "inputType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isInputTypeSetted() {
		return engine.isPropertySetted(Properties.INPUT_TYPE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
