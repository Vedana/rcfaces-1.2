package org.rcfaces.core.component;

import org.rcfaces.core.internal.tools.CollectionTools.IComponentValueType;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.converter.DragDropEffectsConverter;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.ISelectableCapability;
import org.rcfaces.core.internal.capability.ISortedComponentsCapability;
import org.rcfaces.core.internal.tools.SelectionTools;
import org.rcfaces.core.component.capability.IKeySearchColumnIdCapability;
import org.rcfaces.core.component.capability.IHeaderVisibilityCapability;
import org.rcfaces.core.component.capability.ILoadEventCapability;
import org.rcfaces.core.internal.tools.CollectionTools.IComponentValueTypeCapability;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.ISelectionCardinalityCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.capability.IDraggableGridComponent;
import org.rcfaces.core.component.capability.IDragEventCapability;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.ISortedChildrenCapability;
import org.rcfaces.core.component.capability.IDroppableCapability;
import org.rcfaces.core.internal.converter.CheckCardinalityConverter;
import org.rcfaces.core.internal.capability.IAdditionalInformationRangeComponent;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.tools.CheckTools;
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.core.internal.tools.AdditionalInformationTools;
import org.rcfaces.core.component.capability.ICheckedValuesCapability;
import org.rcfaces.core.internal.tools.OrderTools;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.component.capability.IEmptyDataMessageCapability;
import org.rcfaces.core.component.capability.IClientSelectionFullStateCapability;
import org.rcfaces.core.component.AbstractDataComponent;
import org.rcfaces.core.component.capability.IDraggableCapability;
import org.rcfaces.core.component.capability.IShowValueCapability;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.capability.IPreferencesSettings;
import org.rcfaces.core.internal.capability.ICheckRangeComponent;
import org.rcfaces.core.component.capability.IDropCompleteEventCapability;
import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.IFilterCapability;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IOrderedChildrenCapability;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.component.capability.IClientAdditionalInformationFullStateCapability;
import org.rcfaces.core.component.capability.ICheckCardinalityCapability;
import org.rcfaces.core.internal.capability.ISelectionRangeComponent;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IClientCheckFullStateCapability;
import org.rcfaces.core.lang.provider.ICursorProvider;
import org.rcfaces.core.component.capability.IPagedCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.ISelectedValuesCapability;
import org.rcfaces.core.internal.tools.GridTools;
import org.rcfaces.core.component.capability.IDragAndDropEffects;
import org.rcfaces.core.component.capability.ICheckableCapability;
import org.rcfaces.core.internal.converter.FilterPropertiesConverter;
import org.rcfaces.core.component.capability.IRowStyleClassCapability;
import org.rcfaces.core.internal.converter.SelectionCardinalityConverter;
import org.rcfaces.core.internal.tools.SortTools;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.converter.DragDropTypesConverter;
import org.rcfaces.core.component.capability.IAdditionalInformationCardinalityCapability;
import java.util.Set;
import org.rcfaces.core.component.iterator.IDataColumnIterator;
import org.rcfaces.core.internal.capability.IDroppableGridComponent;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import java.util.Collection;
import org.rcfaces.core.component.capability.IAdditionalInformationEventCapability;
import org.rcfaces.core.component.DataColumnComponent;
import org.rcfaces.core.component.iterator.IAdditionalInformationIterator;
import org.rcfaces.core.component.capability.IDropEventCapability;
import java.lang.String;
import org.rcfaces.core.internal.converter.ClientFullStateConverter;
import org.rcfaces.core.component.capability.IAdditionalInformationValuesCapability;
import org.rcfaces.core.component.capability.ICheckEventCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.rcfaces.core.internal.converter.AdditionalInformationCardinalityConverter;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.internal.tools.CollectionTools;

/**
 * <p>The dataGrid Component is a grid component. It can be compared to the grid found in the list part of the modern file explorer. It allows sorts, resizing, contextual menus ...</p>
 * <p>The dataGrid Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; images</li>
 * <li>Margin &amp; border</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * <li>Contextual actions in cell, row or table scope</li>
 * <li>...</li>
 * </ul>
 * </p>
 */
public class DataGridComponent extends AbstractDataComponent implements 
	ISelectionEventCapability,
	ISelectableCapability,
	ISelectionCardinalityCapability,
	ISelectedValuesCapability,
	IDragEventCapability,
	IDraggableCapability,
	IDropEventCapability,
	IDropCompleteEventCapability,
	IDroppableCapability,
	ICheckEventCapability,
	ICheckableCapability,
	ICheckCardinalityCapability,
	ICheckedValuesCapability,
	IAdditionalInformationEventCapability,
	IAdditionalInformationValuesCapability,
	IClientAdditionalInformationFullStateCapability,
	IAdditionalInformationCardinalityCapability,
	IDoubleClickEventCapability,
	ILoadEventCapability,
	IRequiredCapability,
	IBorderCapability,
	IRowStyleClassCapability,
	IEmptyDataMessageCapability,
	IReadOnlyCapability,
	IDisabledCapability,
	IMenuCapability,
	IScrollableCapability,
	IFilterCapability,
	IShowValueCapability,
	IKeySearchColumnIdCapability,
	IPreferencesSettings,
	IPagedCapability,
	IClientSelectionFullStateCapability,
	IClientCheckFullStateCapability,
	IHeaderVisibilityCapability,
	ICursorProvider,
	IGridComponent,
	IDroppableGridComponent,
	IOrderedChildrenCapability,
	ISortedChildrenCapability,
	IComponentValueTypeCapability,
	ISelectionRangeComponent,
	ICheckRangeComponent,
	ISortedComponentsCapability,
	IAdditionalInformationRangeComponent,
	IDraggableGridComponent {

	private static final Log LOG = LogFactory.getLog(DataGridComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.dataGrid";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractDataComponent.BEHAVIOR_EVENT_NAMES;

	public DataGridComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public DataGridComponent(String componentId) {
		this();
		setId(componentId);
	}

	public UIComponent[] getSortedChildren() {


				return SortTools.getSortedChildren(null, this, getComponentEngine(), DataColumnComponent.class);
			
	}

	public void setSortedChildren(UIComponent[] components) {


				SortTools.setSortedChildren(null, this, getComponentEngine(), DataColumnComponent.class, components);
			
	}

	public void setOrderedChildren(UIComponent[] components) {


				OrderTools.setOrderedChildren(null, this, getComponentEngine(), DataColumnComponent.class, components);
			
	}

	public UIComponent[] getOrderedChildren() {


				return OrderTools.getOrderedChildren(null, this, getComponentEngine(), DataColumnComponent.class);
			
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

	public void setRowDragTypes(String dragTypes) {


			setRowDragTypes((String[])DragDropTypesConverter.SINGLETON.getAsObject(null, this, dragTypes));
		
	}

	public void setRowDragEffects(String dragEffects) {


			setRowDragEffects(((Integer)DragDropEffectsConverter.SINGLETON.getAsObject(null, this, dragEffects)).intValue());
		
	}

	public void setRowDropTypes(String dropTypes) {


			setRowDropTypes((String[])DragDropTypesConverter.SINGLETON.getAsObject(null, this, dropTypes));
		
	}

	public void setRowDropEffects(String dropEffects) {


			setRowDropEffects(((Integer)DragDropEffectsConverter.SINGLETON.getAsObject(null, this, dropEffects)).intValue());
		
	}

	public IComponentValueType getComponentValueType() {


				return GridTools.DATA_GRID_VALUE_TYPE;
			
	}

	public IColumnIterator listColumns() {


				return GridTools.listColumns(this, org.rcfaces.core.component.DataColumnComponent.class);
			
	}

	public IDataColumnIterator listDataColumns() {


				return GridTools.listDataColumns(this);
			
	}

	public IAdditionalInformationIterator listAdditionalInformations() {


			return AdditionalInformationTools.listAdditionalInformations(this);
			
	}

	public void setSelectionCardinality(String cardinality) {


			setSelectionCardinality(((Integer)SelectionCardinalityConverter.SINGLETON.getAsObject(null, this, cardinality)).intValue());
		
	}

	public void setClientSelectionFullState(String state) {


			setClientSelectionFullState(((Integer)ClientFullStateConverter.SINGLETON.getAsObject(null, this, state)).intValue());
		
	}

	public int getSelectedValuesCount() {


				return SelectionTools.getCount(getSelectedValues());
			
	}

	public Object getFirstSelectedValue() {


				return SelectionTools.getFirst(getSelectedValues(), getValue());
			
	}

	public Object[] listSelectedValues() {


				return SelectionTools.listValues(getSelectedValues(), getValue());
			
	}

	public Object getSelectedValues(FacesContext facesContext) {

			
				if (isPropertySetted(Properties.SELECTED_VALUES)) {
					return getStateHelper().eval(Properties.SELECTED_VALUES, facesContext);
				}

				return SelectionTools.getAdaptedValues(getValue(), false);
			
	}

	public void setSelectedValues(Object selectedValues) {


				if (isPropertySetted(Properties.SELECTED_VALUES)==false) {
					if (SelectionTools.setAdaptedValues(getValue(), selectedValues)) {
						return;
					}
				}
								
				getStateHelper().put(Properties.SELECTED_VALUES, selectedValues);
			
	}

	public void setCheckCardinality(String cardinality) {


			setCheckCardinality(((Integer)CheckCardinalityConverter.SINGLETON.getAsObject(null, this, cardinality)).intValue());
		
	}

	public void setClientCheckFullState(String state) {


			setClientCheckFullState(((Integer)ClientFullStateConverter.SINGLETON.getAsObject(null, this, state)).intValue());
		
	}

	public int getCheckedValuesCount() {


				return CheckTools.getCount(getCheckedValues());
			
	}

	public Object getFirstCheckedValue() {


				return CheckTools.getFirst(getCheckedValues(), getValue());
			
	}

	public Object[] listCheckedValues() {


				return CheckTools.listValues(getCheckedValues(), getValue());
			
	}

	public Object getCheckedValues(FacesContext facesContext) {


				if (isPropertySetted(Properties.CHECKED_VALUES)) {
					return getStateHelper().eval(Properties.CHECKED_VALUES, facesContext);
				}

				return CheckTools.getAdaptedValues(getValue(), false);
			
	}

	public void setCheckedValues(Object checkedValues) {


				if (isPropertySetted(Properties.CHECKED_VALUES)==false) {
					if (CheckTools.setAdaptedValues(getValue(), checkedValues)) {
						return;
					}
				}
								
				getStateHelper().put(Properties.CHECKED_VALUES, checkedValues);
			
	}

	public void setAdditionalInformationCardinality(String cardinality) {


			setAdditionalInformationCardinality(((Integer)AdditionalInformationCardinalityConverter.SINGLETON.getAsObject(null, this, cardinality)).intValue());
		
	}

	public void setClientAdditionalInformationFullState(String state) {


			setClientAdditionalInformationFullState(((Integer)ClientFullStateConverter.SINGLETON.getAsObject(null, this, state)).intValue());
		
	}

	public int getAdditionalInformationValuesCount() {


				return AdditionalInformationTools.getCount(getAdditionalInformationValues());
			
	}

	public Object getFirstAdditionalInformationValue() {


				return AdditionalInformationTools.getFirst(getAdditionalInformationValues(), null);
			
	}

	public Object getAdditionalInformationValues(FacesContext facesContext) {


				return getStateHelper().eval(Properties.ADDITIONAL_INFORMATION_VALUES, facesContext);
			
	}

	public Object[] listAdditionalInformationValues() {


				return AdditionalInformationTools.listValues(getAdditionalInformationValues(), getValue());
			
	}

	public DataColumnComponent[] getSortedColumns() {


				return (DataColumnComponent[])getSortedChildren();
			
	}

	public DataColumnComponent getFirstSortedColumn() {


				return (DataColumnComponent)SortTools.getFirstSortedChild(null, this, getComponentEngine(), DataColumnComponent.class );
			
	}

	public void setSortedColumn(DataColumnComponent dataColumn) {


				SortTools.setSortedChildren(null, this, getComponentEngine(), DataColumnComponent.class, new DataColumnComponent[] { dataColumn });
			
	}

	public void setSortedColumns(DataColumnComponent[] dataColumns) {


				setSortedChildren(dataColumns);
			
	}

	public ISortedComponent[] listSortedComponents(FacesContext context) {


				return GridTools.listSortedComponents(context, this);
			
	}

	public void select(Object rowValue) {


				SelectionTools.select(null, this, rowValue);
			
	}

	public void select(int index) {


				SelectionTools.select(null, this, index);
			
	}

	public void select(int[] indices) {


				SelectionTools.select(null, this, indices);
			
	}

	public void select(int start, int end) {


				SelectionTools.select(null, this, start, end);
			
	}

	public void selectAll() {


				SelectionTools.selectAll(null, this);
			
	}

	public void deselect(Object rowValue) {


				SelectionTools.deselect(null, this, rowValue);
			
	}

	public void deselect(int index) {


				SelectionTools.deselect(null, this, index);
			
	}

	public void deselect(int[] indices) {


				SelectionTools.deselect(null, this, indices);
			
	}

	public void deselect(int start, int end) {


				SelectionTools.deselect(null, this, start, end);
			
	}

	public void deselectAll() {


				SelectionTools.deselectAll(null, this);
			
	}

	public void check(Object rowValue) {


				CheckTools.check(null, this, rowValue);
			
	}

	public void check(int index) {


				CheckTools.check(null, this, index);
			
	}

	public void check(int[] indices) {


				CheckTools.check(null, this, indices);
			
	}

	public void check(int start, int end) {


				CheckTools.check(null, this, start, end);
			
	}

	public void checkAll() {


				CheckTools.checkAll(null, this);
			
	}

	public void uncheck(Object rowValue) {


				CheckTools.uncheck(null, this, rowValue);
			
	}

	public void uncheck(int index) {


				CheckTools.uncheck(null, this, index);
			
	}

	public void uncheck(int[] indices) {


				CheckTools.uncheck(null, this, indices);
			
	}

	public void uncheck(int start, int end) {


				CheckTools.uncheck(null, this, start, end);
			
	}

	public void uncheckAll() {


				CheckTools.uncheckAll(null, this);
			
	}

	public void showAdditionalInformation(Object rowValue) {


				AdditionalInformationTools.show(null, this, rowValue);
			
	}

	public void showAdditionalInformation(int index) {


				AdditionalInformationTools.show(null, this, index);
			
	}

	public void showAdditionalInformation(int[] indexes) {


				AdditionalInformationTools.show(null, this, indexes);
			
	}

	public void showAllAdditionalInformations() {


				AdditionalInformationTools.showAll(null, this);
			
	}

	public void hideAdditionalInformation(Object rowValue) {


				AdditionalInformationTools.hide(null, this, rowValue);
			
	}

	public void hideAdditionalInformation(int index) {


				AdditionalInformationTools.hide(null, this, index);
			
	}

	public void hideAdditionalInformation(int[] indexes) {


				AdditionalInformationTools.hide(null, this, indexes);
			
	}

	public void hideAllAdditionalInformations() {


				AdditionalInformationTools.hideAll(null, this);
			
	}

	public Object getCursorValue(FacesContext facesContext) {


				Object cursorValue=getStateHelper().eval(Properties.CURSOR_VALUE, facesContext);
				if (cursorValue!=null) {
					return cursorValue;
				}
				
				return ComponentTools.getCursorValue(getValue(), this, facesContext);
			
	}

	public void setFilterProperties(String properties) {


			IFilterProperties filterProperties=(IFilterProperties)FilterPropertiesConverter.SINGLETON.getAsObject(null, this, properties);
			
			setFilterProperties(filterProperties);
		
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

	public boolean isSelectable() {
		return isSelectable(null);
	}

	/**
	 * See {@link #isSelectable() isSelectable()} for more details
	 */
	public boolean isSelectable(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.SELECTABLE, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSelectableSetted() {
		return getStateHelper().get(Properties.SELECTABLE)!=null;
	}

	public void setSelectable(boolean selectable) {
		getStateHelper().put(Properties.SELECTABLE, selectable);
	}

	public int getSelectionCardinality() {
		return getSelectionCardinality(null);
	}

	/**
	 * See {@link #getSelectionCardinality() getSelectionCardinality()} for more details
	 */
	public int getSelectionCardinality(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.SELECTION_CARDINALITY, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectionCardinality" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSelectionCardinalitySetted() {
		return getStateHelper().get(Properties.SELECTION_CARDINALITY)!=null;
	}

	public void setSelectionCardinality(int selectionCardinality) {
		getStateHelper().put(Properties.SELECTION_CARDINALITY, selectionCardinality);
	}

	public java.lang.Object getSelectedValues() {
		return getSelectedValues(null);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectedValues" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSelectedValuesSetted() {
		return getStateHelper().get(Properties.SELECTED_VALUES)!=null;
	}

	/**
	 * Return the type of the property represented by the {@link ValueExpression}, relative to the specified {@link javax.faces.context.FacesContext}.
	 */
	public Class getSelectedValuesType(javax.faces.context.FacesContext facesContext) {
		Object valueExpression=getStateHelper().get(Properties.SELECTED_VALUES);
		if ((valueExpression instanceof ValueExpression)==false) {
			return null;
		}
		if (facesContext==null) {
			facesContext=javax.faces.context.FacesContext.getCurrentInstance();
		}
		return ((ValueExpression)valueExpression).getType(facesContext.getELContext());
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

	public final void addCheckListener(org.rcfaces.core.event.ICheckListener listener) {
		addFacesListener(listener);
	}

	public final void removeCheckListener(org.rcfaces.core.event.ICheckListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listCheckListeners() {
		return getFacesListeners(org.rcfaces.core.event.ICheckListener.class);
	}

	public boolean isCheckable() {
		return isCheckable(null);
	}

	/**
	 * See {@link #isCheckable() isCheckable()} for more details
	 */
	public boolean isCheckable(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.CHECKABLE, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "checkable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCheckableSetted() {
		return getStateHelper().get(Properties.CHECKABLE)!=null;
	}

	public void setCheckable(boolean checkable) {
		getStateHelper().put(Properties.CHECKABLE, checkable);
	}

	public int getCheckCardinality() {
		return getCheckCardinality(null);
	}

	/**
	 * See {@link #getCheckCardinality() getCheckCardinality()} for more details
	 */
	public int getCheckCardinality(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.CHECK_CARDINALITY, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "checkCardinality" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCheckCardinalitySetted() {
		return getStateHelper().get(Properties.CHECK_CARDINALITY)!=null;
	}

	public void setCheckCardinality(int checkCardinality) {
		getStateHelper().put(Properties.CHECK_CARDINALITY, checkCardinality);
	}

	public java.lang.Object getCheckedValues() {
		return getCheckedValues(null);
	}

	/**
	 * Returns <code>true</code> if the attribute "checkedValues" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCheckedValuesSetted() {
		return getStateHelper().get(Properties.CHECKED_VALUES)!=null;
	}

	/**
	 * Return the type of the property represented by the {@link ValueExpression}, relative to the specified {@link javax.faces.context.FacesContext}.
	 */
	public Class getCheckedValuesType(javax.faces.context.FacesContext facesContext) {
		Object valueExpression=getStateHelper().get(Properties.CHECKED_VALUES);
		if ((valueExpression instanceof ValueExpression)==false) {
			return null;
		}
		if (facesContext==null) {
			facesContext=javax.faces.context.FacesContext.getCurrentInstance();
		}
		return ((ValueExpression)valueExpression).getType(facesContext.getELContext());
	}

	public final void addAdditionalInformationListener(org.rcfaces.core.event.IAdditionalInformationListener listener) {
		addFacesListener(listener);
	}

	public final void removeAdditionalInformationListener(org.rcfaces.core.event.IAdditionalInformationListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listAdditionalInformationListeners() {
		return getFacesListeners(org.rcfaces.core.event.IAdditionalInformationListener.class);
	}

	public java.lang.Object getAdditionalInformationValues() {
		return getAdditionalInformationValues(null);
	}

	/**
	 * Returns <code>true</code> if the attribute "additionalInformationValues" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAdditionalInformationValuesSetted() {
		return getStateHelper().get(Properties.ADDITIONAL_INFORMATION_VALUES)!=null;
	}

	public void setAdditionalInformationValues(java.lang.Object additionalInformationValues) {
		getStateHelper().put(Properties.ADDITIONAL_INFORMATION_VALUES, additionalInformationValues);
	}

	/**
	 * Return the type of the property represented by the {@link ValueExpression}, relative to the specified {@link javax.faces.context.FacesContext}.
	 */
	public Class getAdditionalInformationValuesType(javax.faces.context.FacesContext facesContext) {
		Object valueExpression=getStateHelper().get(Properties.ADDITIONAL_INFORMATION_VALUES);
		if ((valueExpression instanceof ValueExpression)==false) {
			return null;
		}
		if (facesContext==null) {
			facesContext=javax.faces.context.FacesContext.getCurrentInstance();
		}
		return ((ValueExpression)valueExpression).getType(facesContext.getELContext());
	}

	public int getClientAdditionalInformationFullState() {
		return getClientAdditionalInformationFullState(null);
	}

	/**
	 * See {@link #getClientAdditionalInformationFullState() getClientAdditionalInformationFullState()} for more details
	 */
	public int getClientAdditionalInformationFullState(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.CLIENT_ADDITIONAL_INFORMATION_FULL_STATE, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "clientAdditionalInformationFullState" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isClientAdditionalInformationFullStateSetted() {
		return getStateHelper().get(Properties.CLIENT_ADDITIONAL_INFORMATION_FULL_STATE)!=null;
	}

	public void setClientAdditionalInformationFullState(int clientAdditionalInformationFullState) {
		getStateHelper().put(Properties.CLIENT_ADDITIONAL_INFORMATION_FULL_STATE, clientAdditionalInformationFullState);
	}

	public int getAdditionalInformationCardinality() {
		return getAdditionalInformationCardinality(null);
	}

	/**
	 * See {@link #getAdditionalInformationCardinality() getAdditionalInformationCardinality()} for more details
	 */
	public int getAdditionalInformationCardinality(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.ADDITIONAL_INFORMATION_CARDINALITY, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "additionalInformationCardinality" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAdditionalInformationCardinalitySetted() {
		return getStateHelper().get(Properties.ADDITIONAL_INFORMATION_CARDINALITY)!=null;
	}

	public void setAdditionalInformationCardinality(int additionalInformationCardinality) {
		getStateHelper().put(Properties.ADDITIONAL_INFORMATION_CARDINALITY, additionalInformationCardinality);
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

	public final void addLoadListener(org.rcfaces.core.event.ILoadListener listener) {
		addFacesListener(listener);
	}

	public final void removeLoadListener(org.rcfaces.core.event.ILoadListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listLoadListeners() {
		return getFacesListeners(org.rcfaces.core.event.ILoadListener.class);
	}

	public boolean isRequired() {
		return isRequired(null);
	}

	/**
	 * See {@link #isRequired() isRequired()} for more details
	 */
	public boolean isRequired(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.REQUIRED, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "required" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRequiredSetted() {
		return getStateHelper().get(Properties.REQUIRED)!=null;
	}

	public void setRequired(boolean required) {
		getStateHelper().put(Properties.REQUIRED, required);
	}

	public boolean isBorder() {
		return isBorder(null);
	}

	/**
	 * See {@link #isBorder() isBorder()} for more details
	 */
	public boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.BORDER, true);
	}

	/**
	 * Returns <code>true</code> if the attribute "border" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderSetted() {
		return getStateHelper().get(Properties.BORDER)!=null;
	}

	public void setBorder(boolean border) {
		getStateHelper().put(Properties.BORDER, border);
	}

	public java.lang.String getRowStyleClass() {
		return getRowStyleClass(null);
	}

	/**
	 * See {@link #getRowStyleClass() getRowStyleClass()} for more details
	 */
	public java.lang.String getRowStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ROW_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRowStyleClassSetted() {
		return getStateHelper().get(Properties.ROW_STYLE_CLASS)!=null;
	}

	public void setRowStyleClass(java.lang.String rowStyleClass) {
		getStateHelper().put(Properties.ROW_STYLE_CLASS, rowStyleClass);
	}

	public java.lang.String getEmptyDataMessage() {
		return getEmptyDataMessage(null);
	}

	/**
	 * See {@link #getEmptyDataMessage() getEmptyDataMessage()} for more details
	 */
	public java.lang.String getEmptyDataMessage(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.EMPTY_DATA_MESSAGE);
	}

	/**
	 * Returns <code>true</code> if the attribute "emptyDataMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isEmptyDataMessageSetted() {
		return getStateHelper().get(Properties.EMPTY_DATA_MESSAGE)!=null;
	}

	public void setEmptyDataMessage(java.lang.String emptyDataMessage) {
		getStateHelper().put(Properties.EMPTY_DATA_MESSAGE, emptyDataMessage);
	}

	public boolean isReadOnly() {
		return isReadOnly(null);
	}

	/**
	 * See {@link #isReadOnly() isReadOnly()} for more details
	 */
	public boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.READ_ONLY, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "readOnly" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isReadOnlySetted() {
		return getStateHelper().get(Properties.READ_ONLY)!=null;
	}

	public void setReadOnly(boolean readOnly) {
		getStateHelper().put(Properties.READ_ONLY, readOnly);
	}

	public boolean isDisabled() {
		return isDisabled(null);
	}

	/**
	 * See {@link #isDisabled() isDisabled()} for more details
	 */
	public boolean isDisabled(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.DISABLED, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "disabled" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledSetted() {
		return getStateHelper().get(Properties.DISABLED)!=null;
	}

	public void setDisabled(boolean disabled) {
		getStateHelper().put(Properties.DISABLED, disabled);
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

	public int getHorizontalScrollPosition() {
		return getHorizontalScrollPosition(null);
	}

	/**
	 * See {@link #getHorizontalScrollPosition() getHorizontalScrollPosition()} for more details
	 */
	public int getHorizontalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.HORIZONTAL_SCROLL_POSITION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "horizontalScrollPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHorizontalScrollPositionSetted() {
		return getStateHelper().get(Properties.HORIZONTAL_SCROLL_POSITION)!=null;
	}

	public void setHorizontalScrollPosition(int horizontalScrollPosition) {
		getStateHelper().put(Properties.HORIZONTAL_SCROLL_POSITION, horizontalScrollPosition);
	}

	public int getVerticalScrollPosition() {
		return getVerticalScrollPosition(null);
	}

	/**
	 * See {@link #getVerticalScrollPosition() getVerticalScrollPosition()} for more details
	 */
	public int getVerticalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.VERTICAL_SCROLL_POSITION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalScrollPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalScrollPositionSetted() {
		return getStateHelper().get(Properties.VERTICAL_SCROLL_POSITION)!=null;
	}

	public void setVerticalScrollPosition(int verticalScrollPosition) {
		getStateHelper().put(Properties.VERTICAL_SCROLL_POSITION, verticalScrollPosition);
	}

	public org.rcfaces.core.model.IFilterProperties getFilterProperties() {
		return getFilterProperties(null);
	}

	/**
	 * See {@link #getFilterProperties() getFilterProperties()} for more details
	 */
	public org.rcfaces.core.model.IFilterProperties getFilterProperties(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.model.IFilterProperties)getStateHelper().eval(Properties.FILTER_PROPERTIES);
	}

	/**
	 * Returns <code>true</code> if the attribute "filterProperties" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFilterPropertiesSetted() {
		return getStateHelper().get(Properties.FILTER_PROPERTIES)!=null;
	}

	public void setFilterProperties(org.rcfaces.core.model.IFilterProperties filterProperties) {
		getStateHelper().put(Properties.FILTER_PROPERTIES, filterProperties);
	}

	public java.lang.Object getShowValue() {
		return getShowValue(null);
	}

	/**
	 * See {@link #getShowValue() getShowValue()} for more details
	 */
	public java.lang.Object getShowValue(javax.faces.context.FacesContext facesContext) {
		return getStateHelper().eval(Properties.SHOW_VALUE);
	}

	/**
	 * Returns <code>true</code> if the attribute "showValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isShowValueSetted() {
		return getStateHelper().get(Properties.SHOW_VALUE)!=null;
	}

	public void setShowValue(java.lang.Object showValue) {
		getStateHelper().put(Properties.SHOW_VALUE, showValue);
	}

	public java.lang.String getKeySearchColumnId() {
		return getKeySearchColumnId(null);
	}

	/**
	 * See {@link #getKeySearchColumnId() getKeySearchColumnId()} for more details
	 */
	public java.lang.String getKeySearchColumnId(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.KEY_SEARCH_COLUMN_ID);
	}

	/**
	 * Returns <code>true</code> if the attribute "keySearchColumnId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isKeySearchColumnIdSetted() {
		return getStateHelper().get(Properties.KEY_SEARCH_COLUMN_ID)!=null;
	}

	public void setKeySearchColumnId(java.lang.String keySearchColumnId) {
		getStateHelper().put(Properties.KEY_SEARCH_COLUMN_ID, keySearchColumnId);
	}

	public org.rcfaces.core.preference.IComponentPreferences getPreferences() {
		return getPreferences(null);
	}

	/**
	 * See {@link #getPreferences() getPreferences()} for more details
	 */
	public org.rcfaces.core.preference.IComponentPreferences getPreferences(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.preference.IComponentPreferences)getStateHelper().eval(Properties.PREFERENCES);
	}

	/**
	 * Returns <code>true</code> if the attribute "preferences" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isPreferencesSetted() {
		return getStateHelper().get(Properties.PREFERENCES)!=null;
	}

	public void setPreferences(org.rcfaces.core.preference.IComponentPreferences preferences) {
		getStateHelper().put(Properties.PREFERENCES, preferences);
	}

	public boolean isPaged() {
		return isPaged(null);
	}

	/**
	 * See {@link #isPaged() isPaged()} for more details
	 */
	public boolean isPaged(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.PAGED, false);
	}

	public boolean isPagedSetted() {


			return isPropertySetted(Properties.PAGED);
		
	}

	public void setPaged(boolean paged) {
		getStateHelper().put(Properties.PAGED, paged);
	}

	public int getClientSelectionFullState() {
		return getClientSelectionFullState(null);
	}

	/**
	 * See {@link #getClientSelectionFullState() getClientSelectionFullState()} for more details
	 */
	public int getClientSelectionFullState(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.CLIENT_SELECTION_FULL_STATE, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "clientSelectionFullState" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isClientSelectionFullStateSetted() {
		return getStateHelper().get(Properties.CLIENT_SELECTION_FULL_STATE)!=null;
	}

	public void setClientSelectionFullState(int clientSelectionFullState) {
		getStateHelper().put(Properties.CLIENT_SELECTION_FULL_STATE, clientSelectionFullState);
	}

	public int getClientCheckFullState() {
		return getClientCheckFullState(null);
	}

	/**
	 * See {@link #getClientCheckFullState() getClientCheckFullState()} for more details
	 */
	public int getClientCheckFullState(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.CLIENT_CHECK_FULL_STATE, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "clientCheckFullState" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isClientCheckFullStateSetted() {
		return getStateHelper().get(Properties.CLIENT_CHECK_FULL_STATE)!=null;
	}

	public void setClientCheckFullState(int clientCheckFullState) {
		getStateHelper().put(Properties.CLIENT_CHECK_FULL_STATE, clientCheckFullState);
	}

	public boolean isHeaderVisible() {
		return isHeaderVisible(null);
	}

	/**
	 * See {@link #isHeaderVisible() isHeaderVisible()} for more details
	 */
	public boolean isHeaderVisible(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.HEADER_VISIBLE, true);
	}

	/**
	 * Returns <code>true</code> if the attribute "headerVisible" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHeaderVisibleSetted() {
		return getStateHelper().get(Properties.HEADER_VISIBLE)!=null;
	}

	public void setHeaderVisible(boolean headerVisible) {
		getStateHelper().put(Properties.HEADER_VISIBLE, headerVisible);
	}

	public java.lang.Object getCursorValue() {
		return getCursorValue(null);
	}

	/**
	 * Returns <code>true</code> if the attribute "cursorValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCursorValueSetted() {
		return getStateHelper().get(Properties.CURSOR_VALUE)!=null;
	}

	public void setCursorValue(java.lang.Object cursorValue) {
		getStateHelper().put(Properties.CURSOR_VALUE, cursorValue);
	}

	public String[] getRowDragTypes() {
		return getRowDragTypes(null);
	}

	public String[] getRowDragTypes(javax.faces.context.FacesContext facesContext) {
		return (String[])getStateHelper().eval(Properties.ROW_DRAG_TYPES);
	}

	public void setRowDragTypes(String[] rowDragTypes) {
		 getStateHelper().put(Properties.ROW_DRAG_TYPES, rowDragTypes);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowDragTypes" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowDragTypesSetted() {
		return getStateHelper().get(Properties.ROW_DRAG_TYPES)!=null;
	}

	public int getRowDragEffects() {
		return getRowDragEffects(null);
	}

	public int getRowDragEffects(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.ROW_DRAG_EFFECTS, 0);
	}

	public void setRowDragEffects(int rowDragEffects) {
		 getStateHelper().put(Properties.ROW_DRAG_EFFECTS, rowDragEffects);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowDragEffects" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowDragEffectsSetted() {
		return getStateHelper().get(Properties.ROW_DRAG_EFFECTS)!=null;
	}

	public String[] getRowDropTypes() {
		return getRowDropTypes(null);
	}

	public String[] getRowDropTypes(javax.faces.context.FacesContext facesContext) {
		return (String[])getStateHelper().eval(Properties.ROW_DROP_TYPES);
	}

	public void setRowDropTypes(String[] rowDropTypes) {
		 getStateHelper().put(Properties.ROW_DROP_TYPES, rowDropTypes);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowDropTypes" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowDropTypesSetted() {
		return getStateHelper().get(Properties.ROW_DROP_TYPES)!=null;
	}

	public int getRowDropEffects() {
		return getRowDropEffects(null);
	}

	public int getRowDropEffects(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.ROW_DROP_EFFECTS, 0);
	}

	public void setRowDropEffects(int rowDropEffects) {
		 getStateHelper().put(Properties.ROW_DROP_EFFECTS, rowDropEffects);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowDropEffects" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowDropEffectsSetted() {
		return getStateHelper().get(Properties.ROW_DROP_EFFECTS)!=null;
	}

	/**
	 * Returns the id for the column containing the key for the row.
	 * @return column id
	 */
	public String getRowValueColumnId() {
		return getRowValueColumnId(null);
	}

	/**
	 * Returns the id for the column containing the key for the row.
	 * @return column id
	 */
	public String getRowValueColumnId(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ROW_VALUE_COLUMN_ID);
	}

	/**
	 * Sets the id for the column containing the key for the row.
	 * @param rowValueColumnId column id
	 */
	public void setRowValueColumnId(String rowValueColumnId) {
		 getStateHelper().put(Properties.ROW_VALUE_COLUMN_ID, rowValueColumnId);
	}

	/**
	 * Sets the id for the column containing the key for the row.
	 * @param rowValueColumnId column id
	 */
	/**
	 * Returns <code>true</code> if the attribute "rowValueColumnId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowValueColumnIdSetted() {
		return getStateHelper().get(Properties.ROW_VALUE_COLUMN_ID)!=null;
	}

	public String getRowLabelColumnId() {
		return getRowLabelColumnId(null);
	}

	public String getRowLabelColumnId(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ROW_LABEL_COLUMN_ID);
	}

	public void setRowLabelColumnId(String rowLabelColumnId) {
		 getStateHelper().put(Properties.ROW_LABEL_COLUMN_ID, rowLabelColumnId);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowLabelColumnId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowLabelColumnIdSetted() {
		return getStateHelper().get(Properties.ROW_LABEL_COLUMN_ID)!=null;
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the number of rows.
	 * @return variable name
	 */
	public String getRowCountVar() {
		return getRowCountVar(null);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the number of rows.
	 * @return variable name
	 */
	public String getRowCountVar(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ROW_COUNT_VAR);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the number of rows.
	 * @param rowCountVar variable name
	 */
	public void setRowCountVar(String rowCountVar) {
		 getStateHelper().put(Properties.ROW_COUNT_VAR, rowCountVar);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the number of rows.
	 * @param rowCountVar variable name
	 */
	/**
	 * Returns <code>true</code> if the attribute "rowCountVar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowCountVarSetted() {
		return getStateHelper().get(Properties.ROW_COUNT_VAR)!=null;
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the index of the current row.
	 * @return variable name
	 */
	public String getRowIndexVar() {
		return getRowIndexVar(null);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the index of the current row.
	 * @return variable name
	 */
	public String getRowIndexVar(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ROW_INDEX_VAR);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the index of the current row.
	 * @param rowIndexVar variable name
	 */
	public void setRowIndexVar(String rowIndexVar) {
		 getStateHelper().put(Properties.ROW_INDEX_VAR, rowIndexVar);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the index of the current row.
	 * @param rowIndexVar variable name
	 */
	/**
	 * Returns <code>true</code> if the attribute "rowIndexVar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowIndexVarSetted() {
		return getStateHelper().get(Properties.ROW_INDEX_VAR)!=null;
	}

	public boolean isCellTextWrap() {
		return isCellTextWrap(null);
	}

	public boolean isCellTextWrap(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.CELL_TEXT_WRAP, false);
	}

	public void setCellTextWrap(boolean cellTextWrap) {
		 getStateHelper().put(Properties.CELL_TEXT_WRAP, cellTextWrap);
	}

	/**
	 * Returns <code>true</code> if the attribute "cellTextWrap" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isCellTextWrapSetted() {
		return getStateHelper().get(Properties.CELL_TEXT_WRAP)!=null;
	}

	public boolean isBodyDroppable() {
		return isBodyDroppable(null);
	}

	public boolean isBodyDroppable(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.BODY_DROPPABLE, false);
	}

	public void setBodyDroppable(boolean bodyDroppable) {
		 getStateHelper().put(Properties.BODY_DROPPABLE, bodyDroppable);
	}

	/**
	 * Returns <code>true</code> if the attribute "bodyDroppable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isBodyDroppableSetted() {
		return getStateHelper().get(Properties.BODY_DROPPABLE)!=null;
	}

}
