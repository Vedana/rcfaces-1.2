package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.capability.IInputTypeCapability;
import org.rcfaces.core.component.capability.IMenuPopupIdCapability;
import org.rcfaces.core.component.capability.IRadioGroupCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.InputTypeConverter;
import org.rcfaces.core.internal.tools.TreeTools;

/**
 * A node belonging to a tree.
 */
public class TreeNodeComponent extends ExpandableItemComponent implements 
	IRadioGroupCapability,
	IStyleClassCapability,
	IMenuPopupIdCapability,
	IInputTypeCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.treeNode";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ExpandableItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"menuPopupId","styleClass","inputType","groupName"}));
	}

	public TreeNodeComponent() {
		setRendererType(null);
	}

	public TreeNodeComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setInputType(String inputType) {


			setInputType(((Integer)InputTypeConverter.SINGLETON.getAsObject(null, this, inputType)).intValue());
		
	}

	public void setExpanded(boolean expanded) {


				setExpanded(null, expanded);
			
	}

	public void setExpanded(FacesContext context, boolean expanded) {


				getTree().setExpanded(context, getItemValue(), expanded);
			
	}

	public boolean isExpanded() {


				return isExpanded(null);
			
	}

	public boolean isExpanded(FacesContext context) {


				return getTree().isExpanded(context, getItemValue());
			
	}

	public TreeComponent getTree() {


			return TreeTools.getTree(this);
			
	}

	public java.lang.String getGroupName() {
		return getGroupName(null);
	}

	/**
	 * See {@link #getGroupName() getGroupName()} for more details
	 */
	public java.lang.String getGroupName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.GROUP_NAME, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "groupName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isGroupNameSetted() {
		return engine.isPropertySetted(Properties.GROUP_NAME);
	}

	public void setGroupName(java.lang.String groupName) {
		engine.setProperty(Properties.GROUP_NAME, groupName);
	}

	public java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	/**
	 * See {@link #getStyleClass() getStyleClass()} for more details
	 */
	public java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "styleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isStyleClassSetted() {
		return engine.isPropertySetted(Properties.STYLE_CLASS);
	}

	public void setStyleClass(java.lang.String styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	public java.lang.String getMenuPopupId() {
		return getMenuPopupId(null);
	}

	/**
	 * See {@link #getMenuPopupId() getMenuPopupId()} for more details
	 */
	public java.lang.String getMenuPopupId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MENU_POPUP_ID, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "menuPopupId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMenuPopupIdSetted() {
		return engine.isPropertySetted(Properties.MENU_POPUP_ID);
	}

	public void setMenuPopupId(java.lang.String menuPopupId) {
		engine.setProperty(Properties.MENU_POPUP_ID, menuPopupId);
	}

	public int getInputType() {
		return getInputType(null);
	}

	/**
	 * See {@link #getInputType() getInputType()} for more details
	 */
	public int getInputType(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.INPUT_TYPE,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "inputType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isInputTypeSetted() {
		return engine.isPropertySetted(Properties.INPUT_TYPE);
	}

	public void setInputType(int inputType) {
		engine.setProperty(Properties.INPUT_TYPE, inputType);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
