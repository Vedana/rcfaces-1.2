package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IRadioGroupCapability;
import javax.el.ValueExpression;
import org.rcfaces.core.component.ExpandableItemComponent;
import org.rcfaces.core.component.capability.IDraggableCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.converter.DragDropEffectsConverter;
import org.rcfaces.core.component.capability.IDroppableCapability;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.TreeComponent;
import java.util.HashSet;
import org.rcfaces.core.internal.tools.TreeTools;
import org.rcfaces.core.internal.converter.DragDropTypesConverter;
import org.rcfaces.core.component.capability.IInputTypeCapability;
import org.rcfaces.core.component.capability.IDragAndDropEffects;
import org.rcfaces.core.internal.converter.InputTypeConverter;
import org.rcfaces.core.component.capability.IMenuPopupIdCapability;
import org.apache.commons.logging.Log;

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

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ExpandableItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"menuPopupId","dragTypes","styleClass","dropTypes","inputType","dropEffects","droppable","groupName","draggable","dragEffects"}));
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

	public int getDragEffects() {
		return getDragEffects(null);
	}

	/**
	 * See {@link #getDragEffects() getDragEffects()} for more details
	 */
	public int getDragEffects(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.DRAG_EFFECTS,IDragAndDropEffects.UNKNOWN_DND_EFFECT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "dragEffects" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDragEffectsSetted() {
		return engine.isPropertySetted(Properties.DRAG_EFFECTS);
	}

	public void setDragEffects(int dragEffects) {
		engine.setProperty(Properties.DRAG_EFFECTS, dragEffects);
	}

	public java.lang.String[] getDragTypes() {
		return getDragTypes(null);
	}

	/**
	 * See {@link #getDragTypes() getDragTypes()} for more details
	 */
	public java.lang.String[] getDragTypes(javax.faces.context.FacesContext facesContext) {
		return (java.lang.String[])engine.getProperty(Properties.DRAG_TYPES, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "dragTypes" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDragTypesSetted() {
		return engine.isPropertySetted(Properties.DRAG_TYPES);
	}

	public void setDragTypes(java.lang.String[] dragTypes) {
		engine.setProperty(Properties.DRAG_TYPES, dragTypes);
	}

	public boolean isDraggable() {
		return isDraggable(null);
	}

	/**
	 * See {@link #isDraggable() isDraggable()} for more details
	 */
	public boolean isDraggable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DRAGGABLE, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "draggable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDraggableSetted() {
		return engine.isPropertySetted(Properties.DRAGGABLE);
	}

	public void setDraggable(boolean draggable) {
		engine.setProperty(Properties.DRAGGABLE, draggable);
	}

	public int getDropEffects() {
		return getDropEffects(null);
	}

	/**
	 * See {@link #getDropEffects() getDropEffects()} for more details
	 */
	public int getDropEffects(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.DROP_EFFECTS,IDragAndDropEffects.UNKNOWN_DND_EFFECT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "dropEffects" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDropEffectsSetted() {
		return engine.isPropertySetted(Properties.DROP_EFFECTS);
	}

	public void setDropEffects(int dropEffects) {
		engine.setProperty(Properties.DROP_EFFECTS, dropEffects);
	}

	public java.lang.String[] getDropTypes() {
		return getDropTypes(null);
	}

	/**
	 * See {@link #getDropTypes() getDropTypes()} for more details
	 */
	public java.lang.String[] getDropTypes(javax.faces.context.FacesContext facesContext) {
		return (java.lang.String[])engine.getProperty(Properties.DROP_TYPES, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "dropTypes" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDropTypesSetted() {
		return engine.isPropertySetted(Properties.DROP_TYPES);
	}

	public void setDropTypes(java.lang.String[] dropTypes) {
		engine.setProperty(Properties.DROP_TYPES, dropTypes);
	}

	public boolean isDroppable() {
		return isDroppable(null);
	}

	/**
	 * See {@link #isDroppable() isDroppable()} for more details
	 */
	public boolean isDroppable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DROPPABLE, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "droppable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDroppableSetted() {
		return engine.isPropertySetted(Properties.DROPPABLE);
	}

	public void setDroppable(boolean droppable) {
		engine.setProperty(Properties.DROPPABLE, droppable);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
