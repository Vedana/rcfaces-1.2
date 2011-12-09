package org.rcfaces.core.component;

import org.rcfaces.core.internal.converter.DragDropEffectsConverter;
import org.rcfaces.core.component.ExpandableItemComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IInputTypeCapability;
import org.rcfaces.core.component.capability.IRadioGroupCapability;
import org.rcfaces.core.component.capability.IMenuPopupIdCapability;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.TreeTools;
import org.rcfaces.core.component.capability.IDraggableCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.TreeComponent;
import org.rcfaces.core.component.capability.IDragAndDropEffects;
import javax.el.ValueExpression;
import org.rcfaces.core.internal.converter.InputTypeConverter;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.IDroppableCapability;
import org.rcfaces.core.internal.converter.DragDropTypesConverter;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import java.util.Set;
import java.util.Arrays;
import java.util.Collection;

/**
 * A node belonging to a tree.
 */
public class TreeNodeComponent extends ExpandableItemComponent implements 
	IRadioGroupCapability,
	IStyleClassCapability,
	IMenuPopupIdCapability,
	IInputTypeCapability,
	IDraggableCapability,
	IDroppableCapability {

	private static final Log LOG = LogFactory.getLog(TreeNodeComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.treeNode";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=ExpandableItemComponent.BEHAVIOR_EVENT_NAMES;

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

	public void setDragEffects(String dragEffects) {


			setDragEffects(((Integer)DragDropEffectsConverter.SINGLETON.getAsObject(null, this, dragEffects)).intValue());
		
	}

	public void setDragTypes(String dragTypes) {


			setDragTypes((String[])DragDropTypesConverter.SINGLETON.getAsObject(null, this, dragTypes));
		
	}

	public void setDropEffects(String dropEffects) {


			setDropEffects(((Integer)DragDropEffectsConverter.SINGLETON.getAsObject(null, this, dropEffects)).intValue());
		
	}

	public void setDropTypes(String dropTypes) {


			setDropTypes((String[])DragDropTypesConverter.SINGLETON.getAsObject(null, this, dropTypes));
		
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
		return (String)getStateHelper().eval(Properties.GROUP_NAME);
	}

	/**
	 * Returns <code>true</code> if the attribute "groupName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isGroupNameSetted() {
		return getStateHelper().get(Properties.GROUP_NAME)!=null;
	}

	public void setGroupName(java.lang.String groupName) {
		getStateHelper().put(Properties.GROUP_NAME, groupName);
	}

	public java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	/**
	 * See {@link #getStyleClass() getStyleClass()} for more details
	 */
	public java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "styleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isStyleClassSetted() {
		return getStateHelper().get(Properties.STYLE_CLASS)!=null;
	}

	public void setStyleClass(java.lang.String styleClass) {
		getStateHelper().put(Properties.STYLE_CLASS, styleClass);
	}

	public java.lang.String getMenuPopupId() {
		return getMenuPopupId(null);
	}

	/**
	 * See {@link #getMenuPopupId() getMenuPopupId()} for more details
	 */
	public java.lang.String getMenuPopupId(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.MENU_POPUP_ID);
	}

	/**
	 * Returns <code>true</code> if the attribute "menuPopupId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMenuPopupIdSetted() {
		return getStateHelper().get(Properties.MENU_POPUP_ID)!=null;
	}

	public void setMenuPopupId(java.lang.String menuPopupId) {
		getStateHelper().put(Properties.MENU_POPUP_ID, menuPopupId);
	}

	public int getInputType() {
		return getInputType(null);
	}

	/**
	 * See {@link #getInputType() getInputType()} for more details
	 */
	public int getInputType(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.INPUT_TYPE, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "inputType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isInputTypeSetted() {
		return getStateHelper().get(Properties.INPUT_TYPE)!=null;
	}

	public void setInputType(int inputType) {
		getStateHelper().put(Properties.INPUT_TYPE, inputType);
	}

	public int getDragEffects() {
		return getDragEffects(null);
	}

	/**
	 * See {@link #getDragEffects() getDragEffects()} for more details
	 */
	public int getDragEffects(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.DRAG_EFFECTS, IDragAndDropEffects.UNKNOWN_DND_EFFECT);
	}

	/**
	 * Returns <code>true</code> if the attribute "dragEffects" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDragEffectsSetted() {
		return getStateHelper().get(Properties.DRAG_EFFECTS)!=null;
	}

	public void setDragEffects(int dragEffects) {
		getStateHelper().put(Properties.DRAG_EFFECTS, dragEffects);
	}

	public java.lang.String[] getDragTypes() {
		return getDragTypes(null);
	}

	/**
	 * See {@link #getDragTypes() getDragTypes()} for more details
	 */
	public java.lang.String[] getDragTypes(javax.faces.context.FacesContext facesContext) {
		return (java.lang.String[])getStateHelper().eval(Properties.DRAG_TYPES);
	}

	/**
	 * Returns <code>true</code> if the attribute "dragTypes" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDragTypesSetted() {
		return getStateHelper().get(Properties.DRAG_TYPES)!=null;
	}

	public void setDragTypes(java.lang.String[] dragTypes) {
		getStateHelper().put(Properties.DRAG_TYPES, dragTypes);
	}

	public boolean isDraggable() {
		return isDraggable(null);
	}

	/**
	 * See {@link #isDraggable() isDraggable()} for more details
	 */
	public boolean isDraggable(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.DRAGGABLE, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "draggable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDraggableSetted() {
		return getStateHelper().get(Properties.DRAGGABLE)!=null;
	}

	public void setDraggable(boolean draggable) {
		getStateHelper().put(Properties.DRAGGABLE, draggable);
	}

	public int getDropEffects() {
		return getDropEffects(null);
	}

	/**
	 * See {@link #getDropEffects() getDropEffects()} for more details
	 */
	public int getDropEffects(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.DROP_EFFECTS, IDragAndDropEffects.UNKNOWN_DND_EFFECT);
	}

	/**
	 * Returns <code>true</code> if the attribute "dropEffects" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDropEffectsSetted() {
		return getStateHelper().get(Properties.DROP_EFFECTS)!=null;
	}

	public void setDropEffects(int dropEffects) {
		getStateHelper().put(Properties.DROP_EFFECTS, dropEffects);
	}

	public java.lang.String[] getDropTypes() {
		return getDropTypes(null);
	}

	/**
	 * See {@link #getDropTypes() getDropTypes()} for more details
	 */
	public java.lang.String[] getDropTypes(javax.faces.context.FacesContext facesContext) {
		return (java.lang.String[])getStateHelper().eval(Properties.DROP_TYPES);
	}

	/**
	 * Returns <code>true</code> if the attribute "dropTypes" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDropTypesSetted() {
		return getStateHelper().get(Properties.DROP_TYPES)!=null;
	}

	public void setDropTypes(java.lang.String[] dropTypes) {
		getStateHelper().put(Properties.DROP_TYPES, dropTypes);
	}

	public boolean isDroppable() {
		return isDroppable(null);
	}

	/**
	 * See {@link #isDroppable() isDroppable()} for more details
	 */
	public boolean isDroppable(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.DROPPABLE, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "droppable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDroppableSetted() {
		return getStateHelper().get(Properties.DROPPABLE)!=null;
	}

	public void setDroppable(boolean droppable) {
		getStateHelper().put(Properties.DROPPABLE, droppable);
	}

}
