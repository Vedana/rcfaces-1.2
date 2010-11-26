package org.rcfaces.core.component;

import org.rcfaces.core.internal.tools.CollectionTools.IComponentValueType;
import org.rcfaces.core.internal.converter.DragDropEffectsConverter;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UISelectItem;
import org.rcfaces.core.component.capability.IOverStyleClassCapability;
import org.rcfaces.core.component.capability.ISelectableCapability;
import org.rcfaces.core.internal.tools.SelectionTools;
import org.rcfaces.core.component.capability.ILoadEventCapability;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.internal.tools.CollectionTools.IComponentValueTypeCapability;
import org.rcfaces.core.component.capability.IPreSelectionEventCapability;
import org.rcfaces.core.component.capability.ISelectionCardinalityCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.IDragEventCapability;
import org.rcfaces.core.internal.util.ComponentIterators;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.IExpandEventCapability;
import org.rcfaces.core.internal.capability.ICheckComponent;
import org.rcfaces.core.component.capability.IDroppableCapability;
import org.rcfaces.core.internal.capability.ISelectionComponent;
import org.rcfaces.core.internal.converter.CheckCardinalityConverter;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.tools.CheckTools;
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.ICheckedValuesCapability;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.component.capability.IDraggableCapability;
import org.rcfaces.core.component.capability.IClientSelectionFullStateCapability;
import org.rcfaces.core.component.capability.IShowValueCapability;
import org.rcfaces.core.component.capability.IDropCompleteEventCapability;
import org.rcfaces.core.component.capability.IFilterCapability;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.component.capability.ICheckCardinalityCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IClientCheckFullStateCapability;
import org.rcfaces.core.internal.tools.TreeTools;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.ISelectedValuesCapability;
import org.rcfaces.core.component.capability.IDragAndDropEffects;
import org.rcfaces.core.component.capability.IExpandableCapability;
import org.rcfaces.core.component.capability.ICheckableCapability;
import org.rcfaces.core.internal.converter.FilterPropertiesConverter;
import org.rcfaces.core.internal.converter.SelectionCardinalityConverter;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.tools.ExpansionTools;
import org.rcfaces.core.internal.converter.DragDropTypesConverter;
import java.util.Set;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.capability.IExpandedValuesCapability;
import org.rcfaces.core.component.capability.IDropEventCapability;
import java.lang.String;
import org.rcfaces.core.internal.converter.ClientFullStateConverter;
import org.rcfaces.core.component.capability.ICheckEventCapability;
import org.rcfaces.core.component.capability.IPreloadedLevelDepthCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.component.AbstractInputComponent;

/**
 * <p>The tree Component shows informations in an arborescent view.
 * It can be compared to the tree found in most modern file explorer.
 * It allows contextual menus ...</p>
 * <p>The tree Component has the following capabilities :
 * <ul>
 * <li>IDoubleClickEventCapability</li>
 * <li>IRequiredCapability</li>
 * <li>IScrollableCapability</li>
 * <li>IBorderCapability</li>
 * <li>IReadOnlyCapability</li>
 * <li>IMenuCapability</li>
 * <li>IShowValueCapability</li>
 * <li>IOverStyleClassCapability</li>
 * <li>IFilterCapability</li>
 * <li>ILoadEventCapability</li>
 * <li>IExpandEventCapability</li>
 * <li>IPreSelectionEventCapability</li>
 * <li>IDragEventCapability</li>
 * <li>IDraggableCapability</li>
 * <li>IDropEventCapability</li>
 * <li>IDropCompleteEventCapability</li>
 * <li>IDroppableCapability</li>
 * <li>ICheckableCapability</li>
 * <li>ICheckCardinalityCapability</li>
 * <li>ICheckEventCapability</li>
 * <li>ICheckedValuesCapability</li>
 * <li>IClientCheckFullStateCapability</li>
 * <li>ISelectableCapability</li>
 * <li><li>ISelectionCardinalityCapability</li>
 * <li>ISelectionEventCapability</li>
 * <li>ISelectedValuesCapability</li>
 * <li>IClientSelectionFullStateCapability</li>
 * <li>IPreloadedLevelDepthCapability</li>
 * <li>IExpandableCapability</li>
 * <li>IExpandedValuesCapability</li>
 * <li>ICheckComponent</li>
 * <li>ISelectionComponent</li>
 * <li>IComponentValueTypeCapability</li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/TreeComponent.html">tree</a> renderer is linked to the <a href="/jsdoc/index.html?f_tree.html">f_tree</a> javascript class. f_tree extends f_component, fa_readOnly, fa_disabled, fa_immediate, fa_subMenu, fa_selectionManager<String[]>, fa_checkManager, fa_itemClientDatas, fa_scrollPositions, fa_overStyleClass, fa_filterProperties, fa_droppable, fa_draggable, fa_autoScroll, fa_autoOpen</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <td bgcolor="#eeeeee"  width="33%">Style Name</td>
 * <td bgcolor="#eeeeee" " width="50%">Description</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_tree</td>
 * <td width="50%">Defines styles for the wrapper DIV element</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_tree_body</td>
 * <td width="50%">Defines styles for the wrapper UL element</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_tree_parent</td>
 * <td width="50%">Defines styles for the wrapper LI element of the parent nodes</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_tree_depth</td>
 * <td width="50%">Defines styles for the DIV element of the depth node</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_tree_node</td>
 * <td width="50%">Defines styles for the DIV element of the node</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class TreeComponent extends AbstractInputComponent implements 
	IDoubleClickEventCapability,
	IRequiredCapability,
	IScrollableCapability,
	IBorderCapability,
	IReadOnlyCapability,
	IMenuCapability,
	IShowValueCapability,
	IOverStyleClassCapability,
	IFilterCapability,
	ILoadEventCapability,
	IExpandEventCapability,
	IPreSelectionEventCapability,
	IDragEventCapability,
	IDraggableCapability,
	IDropEventCapability,
	IDropCompleteEventCapability,
	IDroppableCapability,
	ICheckableCapability,
	ICheckCardinalityCapability,
	ICheckEventCapability,
	ICheckedValuesCapability,
	IClientCheckFullStateCapability,
	ISelectableCapability,
	ISelectionCardinalityCapability,
	ISelectionEventCapability,
	ISelectedValuesCapability,
	IClientSelectionFullStateCapability,
	IPreloadedLevelDepthCapability,
	IExpandableCapability,
	IExpandedValuesCapability,
	ICheckComponent,
	ISelectionComponent,
	IComponentValueTypeCapability {

	private static final Log LOG = LogFactory.getLog(TreeComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.tree";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"checkListener","preloadedLevelDepth","selectionCardinality","droppable","defaultDisabledLeafImageURL","dragListener","dragTypes","checkable","checkCardinality","dropListener","dropEffects","overStyleClass","loadListener","checkedValues","selectionListener","defaultLeafImageURL","showValue","verticalScrollPosition","defaultSelectedLeafImageURL","cursorValue","border","required","clientSelectionFullState","preSelectionListener","bodyDroppable","defaultCollapsedImageURL","defaultExpandedImageURL","filterProperties","defaultDisabledImageURL","defaultImageURL","dragEffects","clientCheckFullState","expandListener","doubleClickListener","selectedValues","horizontalScrollPosition","dropCompleteListener","dropTypes","expansionUseValue","readOnly","selectable","expandedValues","hideRootExpandSign","defaultSelectedImageURL","defaultExpandedLeafImageURL","draggable","expandable"}));
	}
	protected static final String CAMELIA_VALUE_ALIAS="value";

	public TreeComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TreeComponent(String componentId) {
		this();
		setId(componentId);
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

	public void setClientSelectionFullState(String state) {


			setClientSelectionFullState(((Integer)ClientFullStateConverter.SINGLETON.getAsObject(null, this, state)).intValue());
		
	}

	public void setClientCheckFullState(String state) {


			setClientCheckFullState(((Integer)ClientFullStateConverter.SINGLETON.getAsObject(null, this, state)).intValue());
		
	}

	public void setFilterProperties(String properties) {


			IFilterProperties filterProperties=(IFilterProperties)FilterPropertiesConverter.SINGLETON.getAsObject(null, this, properties);
			
			setFilterProperties(filterProperties);
		
	}

	public IComponentValueType getComponentValueType() {


				return TreeTools.TREE_VALUE_TYPE;
			
	}

	public void setSelectionCardinality(String cardinality) {


			setSelectionCardinality(((Integer)SelectionCardinalityConverter.SINGLETON.getAsObject(null, this, cardinality)).intValue());
		
	}

	public void setCheckCardinality(String cardinality) {


			setCheckCardinality(((Integer)CheckCardinalityConverter.SINGLETON.getAsObject(null, this, cardinality)).intValue());
		
	}

	public void removeAllTreeNodes() {


			ComponentIterators.removeAll(this, UISelectItem.class);
			
	}

	public void select(Object rowValue) {


				SelectionTools.select(null, this, rowValue);
			
	}

	public void selectAll() {


				SelectionTools.selectAll(null, this);
			
	}

	public void deselect(Object rowValue) {


				SelectionTools.deselect(null, this, rowValue);
			
	}

	public void deselectAll() {


				SelectionTools.deselectAll(null, this);
			
	}

	public void check(Object rowValue) {


				CheckTools.check(null, this, rowValue);
			
	}

	public void checkAll() {


				CheckTools.checkAll(null, this);
			
	}

	public void uncheck(Object rowValue) {


				CheckTools.uncheck(null, this, rowValue);
			
	}

	public void uncheckAll() {


				CheckTools.uncheckAll(null, this);
			
	}

	public void collapseAll() {


				collapseAll(null);
			
	}

	public void collapseAll(FacesContext context) {


				ExpansionTools.collapseAll(context, this);
			
	}

	public void expandAll() {


				expandAll(null);
			
	}

	public void expandAll(FacesContext context) {


				ExpansionTools.expandAll(context, this);
			
	}

	public void setExpanded(FacesContext context, Object value, boolean expanded) {


				TreeTools.setExpanded(context, this, value, expanded);
			
	}

	public void expand(Object value) {


				ExpansionTools.expand(null, this, value);
			
	}

	public void collapse(Object value) {


				ExpansionTools.collapse(null, this, value);
			
	}

	public void setExpanded(Object value, boolean expanded) {


				setExpanded(null, value, expanded);
			
	}

	public boolean isExpanded(FacesContext context, Object value) {


				return TreeTools.isExpanded(context, this, value);
			
	}

	public Object getCursorValue(FacesContext facesContext) {


				if (engine.isPropertySetted(Properties.CURSOR_VALUE)) {
					return engine.getValue(Properties.CURSOR_VALUE, facesContext);
				}
				
				Object cursorValue=ComponentTools.getCursorValue(getValue(), this, facesContext);
								
				return cursorValue;				
			
	}

	public Object getSelectedValues(FacesContext facesContext) {

			
				if (engine.isPropertySetted(Properties.SELECTED_VALUES)) {
					return engine.getValue(Properties.SELECTED_VALUES, facesContext);
				}

				boolean mainValue=isSelectable(facesContext) && isCheckable(facesContext)==false;

				return SelectionTools.getAdaptedValues(getValue(), mainValue);
			
	}

	public void setSelectedValues(Object selectedValues) {


				if (engine.isPropertySetted(Properties.SELECTED_VALUES)==false) {
					if (SelectionTools.setAdaptedValues(getValue(), selectedValues)) {
						return;
					}

					boolean mainValue=isSelectable() && isCheckable()==false;	
					if (mainValue) {						
						setValue(selectedValues);
						return;
					}
				}
								
				engine.setValue(Properties.SELECTED_VALUES, selectedValues);
			
	}

	public Object getCheckedValues(FacesContext facesContext) {


				if (engine.isPropertySetted(Properties.CHECKED_VALUES)) {
					return engine.getValue(Properties.CHECKED_VALUES, facesContext);
				}

				boolean mainValue=isCheckable(facesContext);

				return CheckTools.getAdaptedValues(getValue(), mainValue);
			
	}

	public void setCheckedValues(Object checkedValues) {


				if (engine.isPropertySetted(Properties.CHECKED_VALUES)==false) {
					if (CheckTools.setAdaptedValues(getValue(), checkedValues)) {
						return;
					}

					boolean mainValue=isCheckable();						
					if (mainValue) {
						setValue(checkedValues);
						return;
					}
				}
								
				engine.setValue(Properties.CHECKED_VALUES, checkedValues);
			
	}

	public Object getExpandedValues(FacesContext facesContext) {


				if (engine.isPropertySetted(Properties.EXPANDED_VALUES)) {
					return engine.getValue(Properties.EXPANDED_VALUES, facesContext);
				}

				boolean mainValue=this.isExpandable(facesContext) && this.isSelectable(facesContext)==false && this.isCheckable(facesContext)==false;

				return ExpansionTools.getAdaptedValues(getValue(), mainValue);
			
	}

	public void setExpandedValues(Object expandedValues) {


				if (engine.isPropertySetted(Properties.EXPANDED_VALUES)==false) {
					if (ExpansionTools.setAdaptedValues(getValue(), expandedValues)) {
						return;
					}

					boolean mainValue=this.isExpandable() && this.isSelectable()==false && this.isCheckable()==false;					
					if (mainValue) {	
						setValue(expandedValues);
						return;
					}
				}
								
				engine.setValue(Properties.EXPANDED_VALUES, expandedValues);
			
	}

	public final void addDoubleClickListener(org.rcfaces.core.event.IDoubleClickListener listener) {
		addFacesListener(listener);
	}

	public final void removeDoubleClickListener(org.rcfaces.core.event.IDoubleClickListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listDoubleClickListeners() {
		return getFacesListeners(org.rcfaces.core.event.IDoubleClickListener.class);
	}

	public int getHorizontalScrollPosition() {
		return getHorizontalScrollPosition(null);
	}

	/**
	 * See {@link #getHorizontalScrollPosition() getHorizontalScrollPosition()} for more details
	 */
	public int getHorizontalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.HORIZONTAL_SCROLL_POSITION,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "horizontalScrollPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHorizontalScrollPositionSetted() {
		return engine.isPropertySetted(Properties.HORIZONTAL_SCROLL_POSITION);
	}

	public void setHorizontalScrollPosition(int horizontalScrollPosition) {
		engine.setProperty(Properties.HORIZONTAL_SCROLL_POSITION, horizontalScrollPosition);
	}

	public int getVerticalScrollPosition() {
		return getVerticalScrollPosition(null);
	}

	/**
	 * See {@link #getVerticalScrollPosition() getVerticalScrollPosition()} for more details
	 */
	public int getVerticalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.VERTICAL_SCROLL_POSITION,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalScrollPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalScrollPositionSetted() {
		return engine.isPropertySetted(Properties.VERTICAL_SCROLL_POSITION);
	}

	public void setVerticalScrollPosition(int verticalScrollPosition) {
		engine.setProperty(Properties.VERTICAL_SCROLL_POSITION, verticalScrollPosition);
	}

	public boolean isBorder() {
		return isBorder(null);
	}

	/**
	 * See {@link #isBorder() isBorder()} for more details
	 */
	public boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BORDER, true, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "border" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderSetted() {
		return engine.isPropertySetted(Properties.BORDER);
	}

	public void setBorder(boolean border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public boolean isReadOnly() {
		return isReadOnly(null);
	}

	/**
	 * See {@link #isReadOnly() isReadOnly()} for more details
	 */
	public boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.READ_ONLY, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "readOnly" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isReadOnlySetted() {
		return engine.isPropertySetted(Properties.READ_ONLY);
	}

	public void setReadOnly(boolean readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	public IMenuComponent getMenu() {


		return MenuTools.getMenu(this);
		
	}

	public IMenuComponent getMenu(String menuId) {


		return MenuTools.getMenu(this, menuId);
		
	}

	public IMenuIterator listMenus() {


		return MenuTools.listMenus(this);
		
	}

	public java.lang.Object getShowValue() {
		return getShowValue(null);
	}

	/**
	 * See {@link #getShowValue() getShowValue()} for more details
	 */
	public java.lang.Object getShowValue(javax.faces.context.FacesContext facesContext) {
		return engine.getProperty(Properties.SHOW_VALUE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "showValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isShowValueSetted() {
		return engine.isPropertySetted(Properties.SHOW_VALUE);
	}

	public void setShowValue(java.lang.Object showValue) {
		engine.setProperty(Properties.SHOW_VALUE, showValue);
	}

	public java.lang.String getOverStyleClass() {
		return getOverStyleClass(null);
	}

	/**
	 * See {@link #getOverStyleClass() getOverStyleClass()} for more details
	 */
	public java.lang.String getOverStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.OVER_STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "overStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isOverStyleClassSetted() {
		return engine.isPropertySetted(Properties.OVER_STYLE_CLASS);
	}

	public void setOverStyleClass(java.lang.String overStyleClass) {
		engine.setProperty(Properties.OVER_STYLE_CLASS, overStyleClass);
	}

	public org.rcfaces.core.model.IFilterProperties getFilterProperties() {
		return getFilterProperties(null);
	}

	/**
	 * See {@link #getFilterProperties() getFilterProperties()} for more details
	 */
	public org.rcfaces.core.model.IFilterProperties getFilterProperties(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.model.IFilterProperties)engine.getProperty(Properties.FILTER_PROPERTIES, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "filterProperties" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFilterPropertiesSetted() {
		return engine.isPropertySetted(Properties.FILTER_PROPERTIES);
	}

	public void setFilterProperties(org.rcfaces.core.model.IFilterProperties filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	public final void addLoadListener(org.rcfaces.core.event.ILoadListener listener) {
		addFacesListener(listener);
	}

	public final void removeLoadListener(org.rcfaces.core.event.ILoadListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listLoadListeners() {
		return getFacesListeners(org.rcfaces.core.event.ILoadListener.class);
	}

	public final void addExpandListener(org.rcfaces.core.event.IExpandListener listener) {
		addFacesListener(listener);
	}

	public final void removeExpandListener(org.rcfaces.core.event.IExpandListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listExpandListeners() {
		return getFacesListeners(org.rcfaces.core.event.IExpandListener.class);
	}

	public final void addPreSelectionListener(org.rcfaces.core.event.IPreSelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removePreSelectionListener(org.rcfaces.core.event.IPreSelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listPreSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.IPreSelectionListener.class);
	}

	public final void addDragListener(org.rcfaces.core.event.IDragListener listener) {
		addFacesListener(listener);
	}

	public final void removeDragListener(org.rcfaces.core.event.IDragListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listDragListeners() {
		return getFacesListeners(org.rcfaces.core.event.IDragListener.class);
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

	public final void addDropListener(org.rcfaces.core.event.IDropListener listener) {
		addFacesListener(listener);
	}

	public final void removeDropListener(org.rcfaces.core.event.IDropListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listDropListeners() {
		return getFacesListeners(org.rcfaces.core.event.IDropListener.class);
	}

	public final void addDropCompleteListener(org.rcfaces.core.event.IDropCompleteListener listener) {
		addFacesListener(listener);
	}

	public final void removeDropCompleteListener(org.rcfaces.core.event.IDropCompleteListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listDropCompleteListeners() {
		return getFacesListeners(org.rcfaces.core.event.IDropCompleteListener.class);
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

	public boolean isCheckable() {
		return isCheckable(null);
	}

	/**
	 * See {@link #isCheckable() isCheckable()} for more details
	 */
	public boolean isCheckable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CHECKABLE, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "checkable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCheckableSetted() {
		return engine.isPropertySetted(Properties.CHECKABLE);
	}

	public void setCheckable(boolean checkable) {
		engine.setProperty(Properties.CHECKABLE, checkable);
	}

	public int getCheckCardinality() {
		return getCheckCardinality(null);
	}

	/**
	 * See {@link #getCheckCardinality() getCheckCardinality()} for more details
	 */
	public int getCheckCardinality(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.CHECK_CARDINALITY,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "checkCardinality" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCheckCardinalitySetted() {
		return engine.isPropertySetted(Properties.CHECK_CARDINALITY);
	}

	public void setCheckCardinality(int checkCardinality) {
		engine.setProperty(Properties.CHECK_CARDINALITY, checkCardinality);
	}

	public final void addCheckListener(org.rcfaces.core.event.ICheckListener listener) {
		addFacesListener(listener);
	}

	public final void removeCheckListener(org.rcfaces.core.event.ICheckListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listCheckListeners() {
		return getFacesListeners(org.rcfaces.core.event.ICheckListener.class);
	}

	public java.lang.Object getCheckedValues() {
		return getCheckedValues(null);
	}

	/**
	 * Returns <code>true</code> if the attribute "checkedValues" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCheckedValuesSetted() {
		return engine.isPropertySetted(Properties.CHECKED_VALUES);
	}

	/**
	 * Return the type of the property represented by the {@link ValueExpression}, relative to the specified {@link javax.faces.context.FacesContext}.
	 */
	public Class getCheckedValuesType(javax.faces.context.FacesContext facesContext) {
		ValueExpression valueExpression=engine.getValueExpressionProperty(Properties.CHECKED_VALUES);
		if (valueExpression==null) {
			return null;
		}
		if (facesContext==null) {
			facesContext=javax.faces.context.FacesContext.getCurrentInstance();
		}
		return valueExpression.getType(facesContext.getELContext());
	}

	public Object getFirstCheckedValue() {


			return CheckTools.getFirst(getCheckedValues(), getValue());
		
	}

	public int getCheckedValuesCount() {


			return CheckTools.getCount(getCheckedValues());
		
	}

	public Object[] listCheckedValues() {


			return CheckTools.listValues(getCheckedValues(), getValue());
		
	}

	public int getClientCheckFullState() {
		return getClientCheckFullState(null);
	}

	/**
	 * See {@link #getClientCheckFullState() getClientCheckFullState()} for more details
	 */
	public int getClientCheckFullState(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.CLIENT_CHECK_FULL_STATE,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "clientCheckFullState" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isClientCheckFullStateSetted() {
		return engine.isPropertySetted(Properties.CLIENT_CHECK_FULL_STATE);
	}

	public void setClientCheckFullState(int clientCheckFullState) {
		engine.setProperty(Properties.CLIENT_CHECK_FULL_STATE, clientCheckFullState);
	}

	public boolean isSelectable() {
		return isSelectable(null);
	}

	/**
	 * See {@link #isSelectable() isSelectable()} for more details
	 */
	public boolean isSelectable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SELECTABLE, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSelectableSetted() {
		return engine.isPropertySetted(Properties.SELECTABLE);
	}

	public void setSelectable(boolean selectable) {
		engine.setProperty(Properties.SELECTABLE, selectable);
	}

	public int getSelectionCardinality() {
		return getSelectionCardinality(null);
	}

	/**
	 * See {@link #getSelectionCardinality() getSelectionCardinality()} for more details
	 */
	public int getSelectionCardinality(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.SELECTION_CARDINALITY,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectionCardinality" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSelectionCardinalitySetted() {
		return engine.isPropertySetted(Properties.SELECTION_CARDINALITY);
	}

	public void setSelectionCardinality(int selectionCardinality) {
		engine.setProperty(Properties.SELECTION_CARDINALITY, selectionCardinality);
	}

	public final void addSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
	}

	public java.lang.Object getSelectedValues() {
		return getSelectedValues(null);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectedValues" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSelectedValuesSetted() {
		return engine.isPropertySetted(Properties.SELECTED_VALUES);
	}

	/**
	 * Return the type of the property represented by the {@link ValueExpression}, relative to the specified {@link javax.faces.context.FacesContext}.
	 */
	public Class getSelectedValuesType(javax.faces.context.FacesContext facesContext) {
		ValueExpression valueExpression=engine.getValueExpressionProperty(Properties.SELECTED_VALUES);
		if (valueExpression==null) {
			return null;
		}
		if (facesContext==null) {
			facesContext=javax.faces.context.FacesContext.getCurrentInstance();
		}
		return valueExpression.getType(facesContext.getELContext());
	}

	public Object getFirstSelectedValue() {


			return SelectionTools.getFirst(getSelectedValues(), getValue());
		
	}

	public int getSelectedValuesCount() {


			return SelectionTools.getCount(getSelectedValues());
		
	}

	public Object[] listSelectedValues() {


			return SelectionTools.listValues(getSelectedValues(), getValue());
		
	}

	public int getClientSelectionFullState() {
		return getClientSelectionFullState(null);
	}

	/**
	 * See {@link #getClientSelectionFullState() getClientSelectionFullState()} for more details
	 */
	public int getClientSelectionFullState(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.CLIENT_SELECTION_FULL_STATE,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "clientSelectionFullState" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isClientSelectionFullStateSetted() {
		return engine.isPropertySetted(Properties.CLIENT_SELECTION_FULL_STATE);
	}

	public void setClientSelectionFullState(int clientSelectionFullState) {
		engine.setProperty(Properties.CLIENT_SELECTION_FULL_STATE, clientSelectionFullState);
	}

	public int getPreloadedLevelDepth() {
		return getPreloadedLevelDepth(null);
	}

	/**
	 * See {@link #getPreloadedLevelDepth() getPreloadedLevelDepth()} for more details
	 */
	public int getPreloadedLevelDepth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.PRELOADED_LEVEL_DEPTH,-1, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "preloadedLevelDepth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isPreloadedLevelDepthSetted() {
		return engine.isPropertySetted(Properties.PRELOADED_LEVEL_DEPTH);
	}

	public void setPreloadedLevelDepth(int preloadedLevelDepth) {
		engine.setProperty(Properties.PRELOADED_LEVEL_DEPTH, preloadedLevelDepth);
	}

	public boolean isExpandable() {
		return isExpandable(null);
	}

	/**
	 * See {@link #isExpandable() isExpandable()} for more details
	 */
	public boolean isExpandable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.EXPANDABLE, true, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "expandable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isExpandableSetted() {
		return engine.isPropertySetted(Properties.EXPANDABLE);
	}

	public void setExpandable(boolean expandable) {
		engine.setProperty(Properties.EXPANDABLE, expandable);
	}

	public java.lang.Object getExpandedValues() {
		return getExpandedValues(null);
	}

	/**
	 * Returns <code>true</code> if the attribute "expandedValues" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isExpandedValuesSetted() {
		return engine.isPropertySetted(Properties.EXPANDED_VALUES);
	}

	/**
	 * Return the type of the property represented by the {@link ValueExpression}, relative to the specified {@link javax.faces.context.FacesContext}.
	 */
	public Class getExpandedValuesType(javax.faces.context.FacesContext facesContext) {
		ValueExpression valueExpression=engine.getValueExpressionProperty(Properties.EXPANDED_VALUES);
		if (valueExpression==null) {
			return null;
		}
		if (facesContext==null) {
			facesContext=javax.faces.context.FacesContext.getCurrentInstance();
		}
		return valueExpression.getType(facesContext.getELContext());
	}

	public Object[] listExpandedValues() {


			return ExpansionTools.listValues(this, getExpandedValues(), getValue());
		
	}

	public int getExpandedValuesCount() {


			return ExpansionTools.getCount(this, getExpandedValues());
		
	}

	/**
	 * Returns an url string pointing to the default image.
	 * @return image url
	 */
	public String getDefaultImageURL() {
		return getDefaultImageURL(null);
	}

	/**
	 * Returns an url string pointing to the default image.
	 * @return image url
	 */
	public String getDefaultImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the default image.
	 * @param defaultImageURL image url
	 */
	public void setDefaultImageURL(String defaultImageURL) {
		engine.setProperty(Properties.DEFAULT_IMAGE_URL, defaultImageURL);
	}

	/**
	 * Sets an url string pointing to the default image.
	 * @param defaultImageURL image url
	 */
	/**
	 * Returns <code>true</code> if the attribute "defaultImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_IMAGE_URL);
	}

	/**
	 * Returns an url string pointing to the default image for the selected state.
	 * @return selected image url
	 */
	public String getDefaultSelectedImageURL() {
		return getDefaultSelectedImageURL(null);
	}

	/**
	 * Returns an url string pointing to the default image for the selected state.
	 * @return selected image url
	 */
	public String getDefaultSelectedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_SELECTED_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the default image for the selected state.
	 * @param defaultSelectedImageURL selected image url
	 */
	public void setDefaultSelectedImageURL(String defaultSelectedImageURL) {
		engine.setProperty(Properties.DEFAULT_SELECTED_IMAGE_URL, defaultSelectedImageURL);
	}

	/**
	 * Sets an url string pointing to the default image for the selected state.
	 * @param defaultSelectedImageURL selected image url
	 */
	/**
	 * Returns <code>true</code> if the attribute "defaultSelectedImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultSelectedImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_SELECTED_IMAGE_URL);
	}

	/**
	 * Returns an url string pointing to the default image for an expanded node.
	 * @return expanded image url
	 */
	public String getDefaultExpandedImageURL() {
		return getDefaultExpandedImageURL(null);
	}

	/**
	 * Returns an url string pointing to the default image for an expanded node.
	 * @return expanded image url
	 */
	public String getDefaultExpandedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_EXPANDED_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the default image for an expanded node.
	 * @param defaultExpandedImageURL expanded image url
	 */
	public void setDefaultExpandedImageURL(String defaultExpandedImageURL) {
		engine.setProperty(Properties.DEFAULT_EXPANDED_IMAGE_URL, defaultExpandedImageURL);
	}

	/**
	 * Sets an url string pointing to the default image for an expanded node.
	 * @param defaultExpandedImageURL expanded image url
	 */
	/**
	 * Returns <code>true</code> if the attribute "defaultExpandedImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultExpandedImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_EXPANDED_IMAGE_URL);
	}

	public String getDefaultCollapsedImageURL() {
		return getDefaultCollapsedImageURL(null);
	}

	public String getDefaultCollapsedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_COLLAPSED_IMAGE_URL, facesContext);
	}

	public void setDefaultCollapsedImageURL(String defaultCollapsedImageURL) {
		engine.setProperty(Properties.DEFAULT_COLLAPSED_IMAGE_URL, defaultCollapsedImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultCollapsedImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultCollapsedImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_COLLAPSED_IMAGE_URL);
	}

	/**
	 * Returns an url string pointing to the default image for the disabled state.
	 * @return disabled image url
	 */
	public String getDefaultDisabledImageURL() {
		return getDefaultDisabledImageURL(null);
	}

	/**
	 * Returns an url string pointing to the default image for the disabled state.
	 * @return disabled image url
	 */
	public String getDefaultDisabledImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_DISABLED_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the default image for the disabled state.
	 * @param defaultDisabledImageURL disabled image url
	 */
	public void setDefaultDisabledImageURL(String defaultDisabledImageURL) {
		engine.setProperty(Properties.DEFAULT_DISABLED_IMAGE_URL, defaultDisabledImageURL);
	}

	/**
	 * Sets an url string pointing to the default image for the disabled state.
	 * @param defaultDisabledImageURL disabled image url
	 */
	/**
	 * Returns <code>true</code> if the attribute "defaultDisabledImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultDisabledImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_DISABLED_IMAGE_URL);
	}

	/**
	 * Returns an url string pointing to the default image for a leaf.
	 * @return leaf image url
	 */
	public String getDefaultLeafImageURL() {
		return getDefaultLeafImageURL(null);
	}

	/**
	 * Returns an url string pointing to the default image for a leaf.
	 * @return leaf image url
	 */
	public String getDefaultLeafImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_LEAF_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the default image for a leaf.
	 * @param defaultLeafImageURL leaf image url
	 */
	public void setDefaultLeafImageURL(String defaultLeafImageURL) {
		engine.setProperty(Properties.DEFAULT_LEAF_IMAGE_URL, defaultLeafImageURL);
	}

	/**
	 * Sets an url string pointing to the default image for a leaf.
	 * @param defaultLeafImageURL leaf image url
	 */
	/**
	 * Returns <code>true</code> if the attribute "defaultLeafImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultLeafImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_LEAF_IMAGE_URL);
	}

	public String getDefaultSelectedLeafImageURL() {
		return getDefaultSelectedLeafImageURL(null);
	}

	public String getDefaultSelectedLeafImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_SELECTED_LEAF_IMAGE_URL, facesContext);
	}

	public void setDefaultSelectedLeafImageURL(String defaultSelectedLeafImageURL) {
		engine.setProperty(Properties.DEFAULT_SELECTED_LEAF_IMAGE_URL, defaultSelectedLeafImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultSelectedLeafImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultSelectedLeafImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_SELECTED_LEAF_IMAGE_URL);
	}

	public String getDefaultExpandedLeafImageURL() {
		return getDefaultExpandedLeafImageURL(null);
	}

	public String getDefaultExpandedLeafImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_EXPANDED_LEAF_IMAGE_URL, facesContext);
	}

	public void setDefaultExpandedLeafImageURL(String defaultExpandedLeafImageURL) {
		engine.setProperty(Properties.DEFAULT_EXPANDED_LEAF_IMAGE_URL, defaultExpandedLeafImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultExpandedLeafImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultExpandedLeafImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_EXPANDED_LEAF_IMAGE_URL);
	}

	public String getDefaultDisabledLeafImageURL() {
		return getDefaultDisabledLeafImageURL(null);
	}

	public String getDefaultDisabledLeafImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_DISABLED_LEAF_IMAGE_URL, facesContext);
	}

	public void setDefaultDisabledLeafImageURL(String defaultDisabledLeafImageURL) {
		engine.setProperty(Properties.DEFAULT_DISABLED_LEAF_IMAGE_URL, defaultDisabledLeafImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultDisabledLeafImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDefaultDisabledLeafImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_DISABLED_LEAF_IMAGE_URL);
	}

	/**
	 * Returns a boolean value indicating wether the expand sign should be visible for the topmost node.
	 * @return true if the head node's expand sign is hidden
	 */
	public boolean isHideRootExpandSign() {
		return isHideRootExpandSign(null);
	}

	/**
	 * Returns a boolean value indicating wether the expand sign should be visible for the topmost node.
	 * @return true if the head node's expand sign is hidden
	 */
	public boolean isHideRootExpandSign(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.HIDE_ROOT_EXPAND_SIGN, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the expand sign should be visible for the topmost node.
	 * @param hideRootExpandSign true if the head node's expand sign is to be hidden
	 */
	public void setHideRootExpandSign(boolean hideRootExpandSign) {
		engine.setProperty(Properties.HIDE_ROOT_EXPAND_SIGN, hideRootExpandSign);
	}

	/**
	 * Sets a boolean value indicating wether the expand sign should be visible for the topmost node.
	 * @param hideRootExpandSign true if the head node's expand sign is to be hidden
	 */
	/**
	 * Returns <code>true</code> if the attribute "hideRootExpandSign" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isHideRootExpandSignSetted() {
		return engine.isPropertySetted(Properties.HIDE_ROOT_EXPAND_SIGN);
	}

	public boolean isBodyDroppable() {
		return isBodyDroppable(null);
	}

	public boolean isBodyDroppable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BODY_DROPPABLE, false, facesContext);
	}

	public void setBodyDroppable(boolean bodyDroppable) {
		engine.setProperty(Properties.BODY_DROPPABLE, bodyDroppable);
	}

	/**
	 * Returns <code>true</code> if the attribute "bodyDroppable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isBodyDroppableSetted() {
		return engine.isPropertySetted(Properties.BODY_DROPPABLE);
	}

	public Object getCursorValue() {
		return getCursorValue(null);
	}

	public void setCursorValue(Object cursorValue) {
		engine.setValue(Properties.CURSOR_VALUE, cursorValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "cursorValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isCursorValueSetted() {
		return engine.isPropertySetted(Properties.CURSOR_VALUE);
	}

	/**
	 * Returns a boolean value that indicates wether a node is identified by it's value or by a complete path.
	 * @return true if node identification use node value
	 */
	public boolean isExpansionUseValue() {
		return isExpansionUseValue(null);
	}

	/**
	 * Returns a boolean value that indicates wether a node is identified by it's value or by a complete path.
	 * @return true if node identification use node value
	 */
	public boolean isExpansionUseValue(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.EXPANSION_USE_VALUE, false, facesContext);
	}

	/**
	 * Sets a boolean value that indicates wether a node is identified by it's value or by a complete path.
	 * @param expansionUseValue true if node identification should use node value
	 */
	public void setExpansionUseValue(boolean expansionUseValue) {
		engine.setProperty(Properties.EXPANSION_USE_VALUE, expansionUseValue);
	}

	/**
	 * Sets a boolean value that indicates wether a node is identified by it's value or by a complete path.
	 * @param expansionUseValue true if node identification should use node value
	 */
	/**
	 * Returns <code>true</code> if the attribute "expansionUseValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isExpansionUseValueSetted() {
		return engine.isPropertySetted(Properties.EXPANSION_USE_VALUE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	protected String getCameliaValueAlias() {
		return CAMELIA_VALUE_ALIAS;
	}
}
