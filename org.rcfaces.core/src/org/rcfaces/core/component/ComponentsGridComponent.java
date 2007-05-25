package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ComponentsColumnComponent;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.internal.tools.SortTools;
import java.util.Arrays;
import org.rcfaces.core.internal.tools.GridTools;
import org.rcfaces.core.internal.converter.CardinalityConverter;
import org.rcfaces.core.component.capability.ISelectionValuesCapability;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.component.capability.ISelectionCardinalityCapability;
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.capability.IRowStyleClassCapability;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.internal.tools.OrderTools;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.component.AbstractDataComponent;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import java.lang.String;
import javax.faces.convert.Converter;
import org.rcfaces.core.component.capability.ISortedChildrenCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.SelectionTools;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import javax.faces.el.ValueBinding;
import java.util.Set;
import org.rcfaces.core.component.capability.IPreferenceCapability;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IOrderedChildrenCapability;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.capability.ISelectableCapability;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.core.component.capability.IPagedCapability;

/**
 * Reserved for future use
 */
public class ComponentsGridComponent extends AbstractDataComponent implements 
	ISelectionEventCapability,
	ISelectableCapability,
	ISelectionCardinalityCapability,
	ISelectionValuesCapability,
	IDoubleClickEventCapability,
	IRequiredCapability,
	IBorderCapability,
	IRowStyleClassCapability,
	IMenuCapability,
	IScrollableCapability,
	IPreferenceCapability,
	IPagedCapability,
	IOrderedChildrenCapability,
	IGridComponent,
	ISortedChildrenCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.componentsGrid";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractDataComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"rowValueConverter","selectionListener","verticalScrollPosition","paged","required","horizontalScrollPosition","doubleClickListener","rowStyleClass","rowIndexVar","selectable","rowCountVar","clientSelectionFullState","preference","border","selectionCardinality","rowValue","selectedValues"}));
	}

	public ComponentsGridComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ComponentsGridComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setSortedChildren(UIComponent[] components) {


				SortTools.setSortedChildren(null, this, engine, ComponentsColumnComponent.class, components);
			
	}

	public final UIComponent[] getSortedChildren() {


				return SortTools.getSortedChildren(null, this, engine, ComponentsColumnComponent.class);
			
	}

	public final UIComponent[] getOrderedChildren() {


				return OrderTools.getOrderedChildren(null, this, engine, ComponentsColumnComponent.class);
			
	}

	public final void setOrderedChildren(UIComponent[] components) {


				OrderTools.setOrderedChildren(null, this, engine, ComponentsColumnComponent.class, components);
			
	}

	public final IColumnIterator listColumns() {


			return GridTools.listColumns(this, ComponentsColumnComponent.class);
			
	}

	public final void setRowValueConverter(String converterId) {

			
			setRowValueConverter(converterId, null);
		
	}

	public final void setRowValueConverter(String converterId, FacesContext facesContext) {


			Converter converter=ComponentTools.createConverter(facesContext, converterId);

			setRowValueConverter(converter);
		
	}

	public final void setSelectionCardinality(String cardinality) {


			setSelectionCardinality(((Integer)CardinalityConverter.SINGLETON.getAsObject(null, this, cardinality)).intValue());
		
	}

	public final int getSelectedValuesCount() {


				return SelectionTools.getCount(getSelectedValues());
			
	}

	public final Object getFirstSelectedValue() {


				return SelectionTools.getFirst(getSelectedValues(), null);
			
	}

	public final Object getSelectedValues(FacesContext facesContext) {


				if (engine.isPropertySetted(Properties.SELECTED_VALUES)) {
					return engine.getValue(Properties.SELECTED_VALUES, facesContext);
				}

				return SelectionTools.adaptValue(getValue(), null);
				/*
				if (values!=null) {
					return values;
				}
				
				if (this.isRowValueColumnIdSetted()) {
					values=new Object[0];
					
				} else {
					values=new ArrayIndexesModel();
				}
				
				setSelectedValues(values);
				
				return values;
				*/
			
	}

	public final Object[] listSelectedValues() {


				return SelectionTools.listValues(getSelectedValues(), getValue());
			
	}

	public final ComponentsColumnComponent[] getSortedColumns() {


				return (ComponentsColumnComponent[])getSortedChildren();
			
	}

	public final ComponentsColumnComponent getFirstSortedColumn() {


				return (ComponentsColumnComponent)SortTools.getFirstSortedChild(null, this, engine, ComponentsColumnComponent.class );
			
	}

	public final void setSortedColumn(ComponentsColumnComponent componentsColumn) {


				SortTools.setSortedChildren(null, this, engine, ComponentsColumnComponent.class, new ComponentsColumnComponent[] { componentsColumn });
			
	}

	public final void setSortedColumns(ComponentsColumnComponent[] componentsColumns) {


				setSortedChildren(componentsColumns);
			
	}

	public final ISortedComponent[] listSortedComponents() {


				return listSortedComponents(null);
			
	}

	public final ISortedComponent[] listSortedComponents(FacesContext context) {


				return GridTools.listSortedComponents(context, this);
			
	}

	/**

	 */
	public final void select(Object rowValue) {


				GridTools.select(null, this, rowValue);
			
	}

	/**
	 * Selects the item at the given zero-relative index in the receiver. If the item at the index was already selected, it remains selected. Indices that are out of range are ignored.
	 * @param index the index of the item to select
	 */
	public final void select(int index) {


				GridTools.select(null, this, index);
			
	}

	/**
	 * Selects the items at the given zero-relative indices in the receiver. The current selection is not cleared before the new items are selected.
	 * If the item at a given index is not selected, it is selected. If the item at a given index was already selected, it remains selected. Indices that are out of range and duplicate indices are ignored. If the receiver is single-select and multiple indices are specified, then all indices are ignored.
	 * @param indices the array of indices for the items to select
	 */
	public final void select(int[] indices) {


				GridTools.select(null, this, indices);
			
	}

	/**
	 * Selects the items in the range specified by the given zero-relative indices in the receiver. The range of indices is inclusive. The current selection is not cleared before the new items are selected.
	 * If an item in the given range is not selected, it is selected. If an item in the given range was already selected, it remains selected. Indices that are out of range are ignored and no items will be selected if start is greater than end. If the receiver is single-select and there is more than one item in the given range, then all indices are ignored.
	 * @param start the start of the range
	 * @param end the end of the range
	 */
	public final void select(int start, int end) {


				GridTools.select(null, this, start, end);
			
	}

	/**
	 * Selects all of the items in the receiver.
	 * If the receiver is single-select, do nothing.
	 */
	public final void selectAll() {


				GridTools.selectAll(null, this);
			
	}

	public final void deselect(Object rowValue) {


				GridTools.deselect(null, this, rowValue);
			
	}

	/**
	 * Deselects the item at the given zero-relative index in the receiver. If the item at the index was already deselected, it remains deselected. Indices that are out of range are ignored.
	 * @param index the index of the item to deselect
	 */
	public final void deselect(int index) {


				GridTools.deselect(null, this, index);
			
	}

	/**
	 * Deselects the items at the given zero-relative indices in the receiver. If the item at the given zero-relative index in the receiver is selected, it is deselected. If the item at the index was not selected, it remains deselected. Indices that are out of range and duplicate indices are ignored.
	 * @param indices the array of indices for the items to deselect
	 */
	public final void deselect(int[] indices) {


				GridTools.deselect(null, this, indices);
			
	}

	/**
	 * Deselects the items at the given zero-relative indices in the receiver. If the item at the given zero-relative index in the receiver is selected, it is deselected. If the item at the index was not selected, it remains deselected. The range of the indices is inclusive. Indices that are out of range are ignored.
	 * @param start the start index of the items to deselect
	 * @param end the end index of the items to deselect
	 */
	public final void deselect(int start, int end) {


				GridTools.deselect(null, this, start, end);
			
	}

	/**
	 * Deselects all selected items in the receiver.
	 */
	public final void deselectAll() {


				GridTools.deselectAll(null, this);
			
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

	public void setSelectedValues(java.lang.Object selectedValues) {
		engine.setProperty(Properties.SELECTED_VALUES, selectedValues);
	}

	/**
	 * Return the type of the property represented by the {@link ValueBinding}, relative to the specified {@link javax.faces.context.FacesContext}.
	 */
	public Class getSelectedValuesType(javax.faces.context.FacesContext facesContext) {
		ValueBinding valueBinding=engine.getValueBindingProperty(Properties.SELECTED_VALUES);
		if (valueBinding==null) {
			return null;
		}
		if (facesContext==null) {
			facesContext=javax.faces.context.FacesContext.getCurrentInstance();
		}
		return valueBinding.getType(facesContext);
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

	public boolean isRequired() {
		return isRequired(null);
	}

	/**
	 * See {@link #isRequired() isRequired()} for more details
	 */
	public boolean isRequired(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.REQUIRED, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "required" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRequiredSetted() {
		return engine.isPropertySetted(Properties.REQUIRED);
	}

	public void setRequired(boolean required) {
		engine.setProperty(Properties.REQUIRED, required);
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

	public java.lang.String getRowStyleClass() {
		return getRowStyleClass(null);
	}

	/**
	 * See {@link #getRowStyleClass() getRowStyleClass()} for more details
	 */
	public java.lang.String getRowStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ROW_STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRowStyleClassSetted() {
		return engine.isPropertySetted(Properties.ROW_STYLE_CLASS);
	}

	public void setRowStyleClass(java.lang.String rowStyleClass) {
		engine.setProperty(Properties.ROW_STYLE_CLASS, rowStyleClass);
	}

	public final IMenuComponent getMenu(String menuId) {


		return MenuTools.getMenu(this, menuId);
		
	}

	public final IMenuComponent getMenu() {


		return MenuTools.getMenu(this);
		
	}

	public final IMenuIterator listMenus() {


		return MenuTools.listMenus(this);
		
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

	public org.rcfaces.core.preference.IComponentPreference getPreference() {
		return getPreference(null);
	}

	/**
	 * See {@link #getPreference() getPreference()} for more details
	 */
	public org.rcfaces.core.preference.IComponentPreference getPreference(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.preference.IComponentPreference)engine.getProperty(Properties.PREFERENCE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "preference" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isPreferenceSetted() {
		return engine.isPropertySetted(Properties.PREFERENCE);
	}

	public void setPreference(org.rcfaces.core.preference.IComponentPreference preference) {
		engine.setProperty(Properties.PREFERENCE, preference);
	}

	public boolean isPaged() {
		return isPaged(null);
	}

	/**
	 * See {@link #isPaged() isPaged()} for more details
	 */
	public boolean isPaged(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.PAGED, false, facesContext);
	}

	public final boolean isPagedSetted() {


			return engine.isPropertySetted(Properties.PAGED);
		
	}

	public void setPaged(boolean paged) {
		engine.setProperty(Properties.PAGED, paged);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the number of rows.
	 * @return variable name
	 */
	public final String getRowCountVar() {
		return getRowCountVar(null);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the number of rows.
	 * @return variable name
	 */
	public final String getRowCountVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ROW_COUNT_VAR, facesContext);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the number of rows.
	 * @param rowCountVar variable name
	 */
	public final void setRowCountVar(String rowCountVar) {
		engine.setProperty(Properties.ROW_COUNT_VAR, rowCountVar);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the number of rows.
	 * @param rowCountVar variable name
	 */
	/**
	 * Returns <code>true</code> if the attribute "rowCountVar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRowCountVarSetted() {
		return engine.isPropertySetted(Properties.ROW_COUNT_VAR);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the index of the current row.
	 * @return variable name
	 */
	public final String getRowIndexVar() {
		return getRowIndexVar(null);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the index of the current row.
	 * @return variable name
	 */
	public final String getRowIndexVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ROW_INDEX_VAR, facesContext);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the index of the current row.
	 * @param rowIndexVar variable name
	 */
	public final void setRowIndexVar(String rowIndexVar) {
		engine.setProperty(Properties.ROW_INDEX_VAR, rowIndexVar);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the index of the current row.
	 * @param rowIndexVar variable name
	 */
	/**
	 * Returns <code>true</code> if the attribute "rowIndexVar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRowIndexVarSetted() {
		return engine.isPropertySetted(Properties.ROW_INDEX_VAR);
	}

	public final String getRowValue() {
		return getRowValue(null);
	}

	public final String getRowValue(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ROW_VALUE, facesContext);
	}

	public final void setRowValue(String rowValue) {
		engine.setProperty(Properties.ROW_VALUE, rowValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRowValueSetted() {
		return engine.isPropertySetted(Properties.ROW_VALUE);
	}

	public final javax.faces.convert.Converter getRowValueConverter() {
		return getRowValueConverter(null);
	}

	public final javax.faces.convert.Converter getRowValueConverter(javax.faces.context.FacesContext facesContext) {
		return (javax.faces.convert.Converter)engine.getValue(Properties.ROW_VALUE_CONVERTER, facesContext);
	}

	public final void setRowValueConverter(javax.faces.convert.Converter rowValueConverter) {
		engine.setProperty(Properties.ROW_VALUE_CONVERTER, rowValueConverter);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowValueConverter" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRowValueConverterSetted() {
		return engine.isPropertySetted(Properties.ROW_VALUE_CONVERTER);
	}

	/**
	 * Returns a boolean value indicating wether the client should know about the component's full state even if only a part of the data is present (AJAX).
	 * @return boolean
	 */
	public final boolean isClientSelectionFullState() {
		return isClientSelectionFullState(null);
	}

	/**
	 * Returns a boolean value indicating wether the client should know about the component's full state even if only a part of the data is present (AJAX).
	 * @return boolean
	 */
	public final boolean isClientSelectionFullState(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CLIENT_SELECTION_FULL_STATE, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the client should know about the component's full state even if only a part of the data is present (AJAX).
	 * @param clientSelectionFullState boolean
	 */
	public final void setClientSelectionFullState(boolean clientSelectionFullState) {
		engine.setProperty(Properties.CLIENT_SELECTION_FULL_STATE, clientSelectionFullState);
	}

	/**
	 * Sets a boolean value indicating wether the client should know about the component's full state even if only a part of the data is present (AJAX).
	 * @param clientSelectionFullState boolean
	 */
	/**
	 * Returns <code>true</code> if the attribute "clientSelectionFullState" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isClientSelectionFullStateSetted() {
		return engine.isPropertySetted(Properties.CLIENT_SELECTION_FULL_STATE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
