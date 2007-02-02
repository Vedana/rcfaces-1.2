package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.capability.ICheckCardinalityCapability;
import java.util.Arrays;
import org.rcfaces.core.internal.tools.GridTools;
import org.rcfaces.core.component.capability.ICheckableCapability;
import org.rcfaces.core.internal.converter.CardinalityConverter;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.component.capability.ICheckEventCapability;
import org.rcfaces.core.component.capability.ISelectionCardinalityCapability;
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.AbstractGridComponent;
import java.lang.String;
import org.rcfaces.core.component.capability.IDisabledCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import javax.faces.el.ValueBinding;
import java.util.Set;
import org.rcfaces.core.component.capability.IPreferenceCapability;
import java.util.HashSet;
import org.rcfaces.core.component.capability.ISelectableCapability;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.component.iterator.IDataColumnIterator;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.core.component.DataColumnComponent;
import org.rcfaces.core.component.capability.IReadOnlyCapability;

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
public class DataGridComponent extends AbstractGridComponent implements 
	ISelectionEventCapability,
	ISelectableCapability,
	ISelectionCardinalityCapability,
	ICheckEventCapability,
	ICheckableCapability,
	ICheckCardinalityCapability,
	IDoubleClickEventCapability,
	IRequiredCapability,
	IBorderCapability,
	IReadOnlyCapability,
	IDisabledCapability,
	IMenuCapability,
	IScrollableCapability,
	IFilterCapability,
	IPreferenceCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.dataGrid";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractGridComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","rowValueColumnId","horizontalScrollPosition","doubleClickListener","sortedColumnIds","rowIndexVar","selectable","filterProperties","checkable","checkedValues","preference","border","checkCardinality","verticalScrollPosition","paged","columnsOrder","required","disabled","cursorValue","clientCheckFullState","headerVisible","rowCountVar","clientSelectionFullState","checkListener","readOnly","selectionCardinality","selectedValues"}));
	}

	public DataGridComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public DataGridComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final IDataColumnIterator listColumns() {


		return GridTools.listColumns(this);
		
	}

	public final void setSelectionCardinality(String cardinality) {


			setSelectionCardinality(((Integer)CardinalityConverter.SINGLETON.getAsObject(null, this, cardinality)).intValue());
		
	}

	public final void setCheckCardinality(String cardinality) {


			setCheckCardinality(((Integer)CardinalityConverter.SINGLETON.getAsObject(null, this, cardinality)).intValue());
		
	}

	public final int getSelectedRowsCount() {


				return GridTools.getCount(getSelectedValues());
			
	}

	public final int getCheckedRowsCount() {


				return GridTools.getCount(getCheckedValues());
			
	}

	public final Object getFirstSelectedRow() {


				return GridTools.getFirst(getDataModel(null), getSelectedValues());
			
	}

	public final Object getFirstCheckedRow() {


				return GridTools.getFirst(getDataModel(null), getCheckedValues());
			
	}

	public final boolean setSortedColumn(DataColumnComponent dataColumn) {


				return GridTools.setSortedColumn(this, dataColumn);
			
	}

	public final boolean setSortedColumns(DataColumnComponent[] dataColumns) {


				return GridTools.setSortedColumns(this, dataColumns);
			
	}

	public final DataColumnComponent getFirstSortedColumn() {


				return getFirstSortedColumn(null);
			
	}

	public final DataColumnComponent getFirstSortedColumn(FacesContext context) {


				return GridTools.getFirstSortedColumn(context, this);
			
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


				GridTools.select(this, rowValue);
			
	}

	/**
	 * Selects the item at the given zero-relative index in the receiver. If the item at the index was already selected, it remains selected. Indices that are out of range are ignored.
	 * @param index the index of the item to select
	 */
	public final void select(int index) {


				GridTools.select(this, index);
			
	}

	/**
	 * Selects the items at the given zero-relative indices in the receiver. The current selection is not cleared before the new items are selected.
	 * If the item at a given index is not selected, it is selected. If the item at a given index was already selected, it remains selected. Indices that are out of range and duplicate indices are ignored. If the receiver is single-select and multiple indices are specified, then all indices are ignored.
	 * @param indices the array of indices for the items to select
	 */
	public final void select(int[] indices) {


				GridTools.select(this, indices);
			
	}

	/**
	 * Selects the items in the range specified by the given zero-relative indices in the receiver. The range of indices is inclusive. The current selection is not cleared before the new items are selected.
	 * If an item in the given range is not selected, it is selected. If an item in the given range was already selected, it remains selected. Indices that are out of range are ignored and no items will be selected if start is greater than end. If the receiver is single-select and there is more than one item in the given range, then all indices are ignored.
	 * @param start the start of the range
	 * @param end the end of the range
	 */
	public final void select(int start, int end) {


				GridTools.select(this, start, end);
			
	}

	/**
	 * Selects all of the items in the receiver.
	 * If the receiver is single-select, do nothing.
	 */
	public final void selectAll() {


				GridTools.selectAll(this);
			
	}

	public final void deselect(Object rowValue) {


				GridTools.deselect(this, rowValue);
			
	}

	/**
	 * Deselects the item at the given zero-relative index in the receiver. If the item at the index was already deselected, it remains deselected. Indices that are out of range are ignored.
	 * @param index the index of the item to deselect
	 */
	public final void deselect(int index) {


				GridTools.deselect(this, index);
			
	}

	/**
	 * Deselects the items at the given zero-relative indices in the receiver. If the item at the given zero-relative index in the receiver is selected, it is deselected. If the item at the index was not selected, it remains deselected. Indices that are out of range and duplicate indices are ignored.
	 * @param indices the array of indices for the items to deselect
	 */
	public final void deselect(int[] indices) {


				GridTools.deselect(this, indices);
			
	}

	/**
	 * Deselects the items at the given zero-relative indices in the receiver. If the item at the given zero-relative index in the receiver is selected, it is deselected. If the item at the index was not selected, it remains deselected. The range of the indices is inclusive. Indices that are out of range are ignored.
	 * @param start the start index of the items to deselect
	 * @param end the end index of the items to deselect
	 */
	public final void deselect(int start, int end) {


				GridTools.deselect(this, start, end);
			
	}

	/**
	 * Deselects all selected items in the receiver.
	 */
	public final void deselectAll() {


				GridTools.deselectAll(this);
			
	}

	public final Object getCursorValue(FacesContext facesContext) {


				Object cursorValue=engine.getValue(Properties.CURSOR_VALUE, facesContext);
				if (cursorValue!=null) {
					return cursorValue;
				}
				
				Object value=getValue();
				cursorValue=ComponentTools.getCursorValue(value, this, facesContext);
								
				return cursorValue;				
			
	}

	public final Object getSelectedValues(FacesContext facesContext) {


				Object selectedValues=engine.getValue(Properties.SELECTED_VALUES, facesContext);
				if (selectedValues!=null) {
					return selectedValues;
				}
				
				Object value=getValue();
				selectedValues=ComponentTools.getSelectedValues(value, this, facesContext);
								
				return selectedValues;				
			
	}

	public final Object getCheckValues(FacesContext facesContext) {


				Object checkedValues=engine.getValue(Properties.CHECKED_VALUES, facesContext);
				if (checkedValues!=null) {
					return checkedValues;
				}
				
				Object value=getValue();
				checkedValues=ComponentTools.getCheckedValues(value, this, facesContext);
								
				return checkedValues;				
			
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

	public final boolean isSelectable() {
		return isSelectable(null);
	}

	/**
	 * See {@link #isSelectable() isSelectable()} for more details
	 */
	public final boolean isSelectable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SELECTABLE, false, facesContext);
	}

	public final void setSelectable(boolean selectable) {
		engine.setProperty(Properties.SELECTABLE, selectable);
	}

	/**
	 * See {@link #setSelectable(boolean) setSelectable(boolean)} for more details
	 */
	public final void setSelectable(ValueBinding selectable) {
		engine.setProperty(Properties.SELECTABLE, selectable);
	}

	public final int getSelectionCardinality() {
		return getSelectionCardinality(null);
	}

	/**
	 * See {@link #getSelectionCardinality() getSelectionCardinality()} for more details
	 */
	public final int getSelectionCardinality(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.SELECTION_CARDINALITY,0, facesContext);
	}

	public final void setSelectionCardinality(int selectionCardinality) {
		engine.setProperty(Properties.SELECTION_CARDINALITY, selectionCardinality);
	}

	/**
	 * See {@link #setSelectionCardinality(int) setSelectionCardinality(int)} for more details
	 */
	public final void setSelectionCardinality(ValueBinding selectionCardinality) {
		engine.setProperty(Properties.SELECTION_CARDINALITY, selectionCardinality);
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

	public final boolean isCheckable() {
		return isCheckable(null);
	}

	/**
	 * See {@link #isCheckable() isCheckable()} for more details
	 */
	public final boolean isCheckable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CHECKABLE, false, facesContext);
	}

	public final void setCheckable(boolean checkable) {
		engine.setProperty(Properties.CHECKABLE, checkable);
	}

	/**
	 * See {@link #setCheckable(boolean) setCheckable(boolean)} for more details
	 */
	public final void setCheckable(ValueBinding checkable) {
		engine.setProperty(Properties.CHECKABLE, checkable);
	}

	public final int getCheckCardinality() {
		return getCheckCardinality(null);
	}

	/**
	 * See {@link #getCheckCardinality() getCheckCardinality()} for more details
	 */
	public final int getCheckCardinality(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.CHECK_CARDINALITY,0, facesContext);
	}

	public final void setCheckCardinality(int checkCardinality) {
		engine.setProperty(Properties.CHECK_CARDINALITY, checkCardinality);
	}

	/**
	 * See {@link #setCheckCardinality(int) setCheckCardinality(int)} for more details
	 */
	public final void setCheckCardinality(ValueBinding checkCardinality) {
		engine.setProperty(Properties.CHECK_CARDINALITY, checkCardinality);
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

	public final boolean isRequired() {
		return isRequired(null);
	}

	/**
	 * See {@link #isRequired() isRequired()} for more details
	 */
	public final boolean isRequired(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.REQUIRED, false, facesContext);
	}

	public final void setRequired(boolean required) {
		engine.setProperty(Properties.REQUIRED, required);
	}

	/**
	 * See {@link #setRequired(boolean) setRequired(boolean)} for more details
	 */
	public final void setRequired(ValueBinding required) {
		engine.setProperty(Properties.REQUIRED, required);
	}

	public final boolean isBorder() {
		return isBorder(null);
	}

	/**
	 * See {@link #isBorder() isBorder()} for more details
	 */
	public final boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BORDER, true, facesContext);
	}

	public final void setBorder(boolean border) {
		engine.setProperty(Properties.BORDER, border);
	}

	/**
	 * See {@link #setBorder(boolean) setBorder(boolean)} for more details
	 */
	public final void setBorder(ValueBinding border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public final boolean isReadOnly() {
		return isReadOnly(null);
	}

	/**
	 * See {@link #isReadOnly() isReadOnly()} for more details
	 */
	public final boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.READ_ONLY, false, facesContext);
	}

	public final void setReadOnly(boolean readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	/**
	 * See {@link #setReadOnly(boolean) setReadOnly(boolean)} for more details
	 */
	public final void setReadOnly(ValueBinding readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	public final boolean isDisabled() {
		return isDisabled(null);
	}

	/**
	 * See {@link #isDisabled() isDisabled()} for more details
	 */
	public final boolean isDisabled(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DISABLED, false, facesContext);
	}

	public final void setDisabled(boolean disabled) {
		engine.setProperty(Properties.DISABLED, disabled);
	}

	/**
	 * See {@link #setDisabled(boolean) setDisabled(boolean)} for more details
	 */
	public final void setDisabled(ValueBinding disabled) {
		engine.setProperty(Properties.DISABLED, disabled);
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

	public final int getHorizontalScrollPosition() {
		return getHorizontalScrollPosition(null);
	}

	/**
	 * See {@link #getHorizontalScrollPosition() getHorizontalScrollPosition()} for more details
	 */
	public final int getHorizontalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.HORIZONTAL_SCROLL_POSITION,0, facesContext);
	}

	public final void setHorizontalScrollPosition(int horizontalScrollPosition) {
		engine.setProperty(Properties.HORIZONTAL_SCROLL_POSITION, horizontalScrollPosition);
	}

	/**
	 * See {@link #setHorizontalScrollPosition(int) setHorizontalScrollPosition(int)} for more details
	 */
	public final void setHorizontalScrollPosition(ValueBinding horizontalScrollPosition) {
		engine.setProperty(Properties.HORIZONTAL_SCROLL_POSITION, horizontalScrollPosition);
	}

	public final int getVerticalScrollPosition() {
		return getVerticalScrollPosition(null);
	}

	/**
	 * See {@link #getVerticalScrollPosition() getVerticalScrollPosition()} for more details
	 */
	public final int getVerticalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.VERTICAL_SCROLL_POSITION,0, facesContext);
	}

	public final void setVerticalScrollPosition(int verticalScrollPosition) {
		engine.setProperty(Properties.VERTICAL_SCROLL_POSITION, verticalScrollPosition);
	}

	/**
	 * See {@link #setVerticalScrollPosition(int) setVerticalScrollPosition(int)} for more details
	 */
	public final void setVerticalScrollPosition(ValueBinding verticalScrollPosition) {
		engine.setProperty(Properties.VERTICAL_SCROLL_POSITION, verticalScrollPosition);
	}

	public final org.rcfaces.core.model.IFilterProperties getFilterProperties() {
		return getFilterProperties(null);
	}

	/**
	 * See {@link #getFilterProperties() getFilterProperties()} for more details
	 */
	public final org.rcfaces.core.model.IFilterProperties getFilterProperties(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.model.IFilterProperties)engine.getProperty(Properties.FILTER_PROPERTIES, facesContext);
	}

	public final void setFilterProperties(org.rcfaces.core.model.IFilterProperties filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	/**
	 * See {@link #setFilterProperties(org.rcfaces.core.model.IFilterProperties) setFilterProperties(org.rcfaces.core.model.IFilterProperties)} for more details
	 */
	public final void setFilterProperties(ValueBinding filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	public final org.rcfaces.core.preference.IComponentPreference getPreference() {
		return getPreference(null);
	}

	/**
	 * See {@link #getPreference() getPreference()} for more details
	 */
	public final org.rcfaces.core.preference.IComponentPreference getPreference(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.preference.IComponentPreference)engine.getProperty(Properties.PREFERENCE, facesContext);
	}

	public final void setPreference(org.rcfaces.core.preference.IComponentPreference preference) {
		engine.setProperty(Properties.PREFERENCE, preference);
	}

	/**
	 * See {@link #setPreference(org.rcfaces.core.preference.IComponentPreference) setPreference(org.rcfaces.core.preference.IComponentPreference)} for more details
	 */
	public final void setPreference(ValueBinding preference) {
		engine.setProperty(Properties.PREFERENCE, preference);
	}

	/**
	 * Returns a boolean value indicating wether the header should be visible.
	 * @return true if the header is visible
	 */
	public final boolean isHeaderVisible() {
		return isHeaderVisible(null);
	}

	/**
	 * Returns a boolean value indicating wether the header should be visible.
	 * @return true if the header is visible
	 */
	public final boolean isHeaderVisible(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.HEADER_VISIBLE, true, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the header should be visible.
	 * @param headerVisible true if the header should be visible
	 */
	public final void setHeaderVisible(boolean headerVisible) {
		engine.setProperty(Properties.HEADER_VISIBLE, headerVisible);
	}

	/**
	 * Sets a boolean value indicating wether the header should be visible.
	 * @param headerVisible true if the header should be visible
	 */
	public final void setHeaderVisible(ValueBinding headerVisible) {
		engine.setProperty(Properties.HEADER_VISIBLE, headerVisible);
	}

	/**
	 * Returns <code>true</code> if the attribute "headerVisible" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHeaderVisibleSetted() {
		return engine.isPropertySetted(Properties.HEADER_VISIBLE);
	}

	/**
	 * Returns a boolean value specifying wether the results should be displayed by page (thus letting the user choose what page to display via the pager).
	 * It is ignored if the attribute "rows" is undefined.
	 * The default value is true.
	 * If "rows" is defined and "paged"'s value is set to false, pages are downloaded automatically when the last displayed row is selected.
	 * @return true if display by page
	 */
	public final boolean isPaged() {
		return isPaged(null);
	}

	/**
	 * Returns a boolean value specifying wether the results should be displayed by page (thus letting the user choose what page to display via the pager).
	 * It is ignored if the attribute "rows" is undefined.
	 * The default value is true.
	 * If "rows" is defined and "paged"'s value is set to false, pages are downloaded automatically when the last displayed row is selected.
	 * @return true if display by page
	 */
	public final boolean isPaged(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.PAGED, true, facesContext);
	}

	/**
	 * Sets a boolean value specifying wether the results should be displayed by page (thus letting the user choose what page to display via the pager).
	 * It is ignored if the attribute "rows" is undefined.
	 * The default value is true.
	 * If "rows" is defined and "paged"'s value is set to false, pages are downloaded automatically when the last displayed row is selected.
	 * @param paged true if display by page
	 */
	public final void setPaged(boolean paged) {
		engine.setProperty(Properties.PAGED, paged);
	}

	/**
	 * Sets a boolean value specifying wether the results should be displayed by page (thus letting the user choose what page to display via the pager).
	 * It is ignored if the attribute "rows" is undefined.
	 * The default value is true.
	 * If "rows" is defined and "paged"'s value is set to false, pages are downloaded automatically when the last displayed row is selected.
	 * @param paged true if display by page
	 */
	public final void setPaged(ValueBinding paged) {
		engine.setProperty(Properties.PAGED, paged);
	}

	/**
	 * Returns <code>true</code> if the attribute "paged" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isPagedSetted() {
		return engine.isPropertySetted(Properties.PAGED);
	}

	/**
	 * Returns a table of the values associated with selected nodes for the component. (Binding only)
	 * @return table of values
	 */
	public final Object getSelectedValues() {
		return getSelectedValues(null);
	}

	/**
	 * Sets a table of the values associated with selected nodes for the component. (Binding only)
	 * @param selectedValues table of values
	 */
	public final void setSelectedValues(Object selectedValues) {
		engine.setValue(Properties.SELECTED_VALUES, selectedValues);
	}

	/**
	 * Sets a table of the values associated with selected nodes for the component. (Binding only)
	 * @param selectedValues table of values
	 */
	public final void setSelectedValues(ValueBinding selectedValues) {
		engine.setValueBinding(Properties.SELECTED_VALUES, selectedValues);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectedValues" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSelectedValuesSetted() {
		return engine.isPropertySetted(Properties.SELECTED_VALUES);
	}

	public final Object getCheckedValues() {
		return getCheckedValues(null);
	}

	public final Object getCheckedValues(javax.faces.context.FacesContext facesContext) {
		return engine.getValue(Properties.CHECKED_VALUES, facesContext);
	}

	public final void setCheckedValues(Object checkedValues) {
		engine.setValue(Properties.CHECKED_VALUES, checkedValues);
	}

	public final void setCheckedValues(ValueBinding checkedValues) {
		engine.setValueBinding(Properties.CHECKED_VALUES, checkedValues);
	}

	/**
	 * Returns <code>true</code> if the attribute "checkedValues" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCheckedValuesSetted() {
		return engine.isPropertySetted(Properties.CHECKED_VALUES);
	}

	/**
	 * Returns a string value giving the ordered list of the sorted column ids. read-only
	 * @return ordered list of the sorted column ids
	 */
	public final String getSortedColumnIds() {
		return getSortedColumnIds(null);
	}

	/**
	 * Returns a string value giving the ordered list of the sorted column ids. read-only
	 * @return ordered list of the sorted column ids
	 */
	public final String getSortedColumnIds(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SORTED_COLUMN_IDS, facesContext);
	}

	/**
	 * read-only
	 */
	public final void setSortedColumnIds(String sortedColumnIds) {
		engine.setProperty(Properties.SORTED_COLUMN_IDS, sortedColumnIds);
	}

	/**
	 * read-only
	 */
	public final void setSortedColumnIds(ValueBinding sortedColumnIds) {
		engine.setProperty(Properties.SORTED_COLUMN_IDS, sortedColumnIds);
	}

	/**
	 * Returns <code>true</code> if the attribute "sortedColumnIds" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSortedColumnIdsSetted() {
		return engine.isPropertySetted(Properties.SORTED_COLUMN_IDS);
	}

	/**
	 * Returns a list of the columns' id which represents their order from left to right.
	 * @return ordered list of columns' id
	 */
	public final String getColumnsOrder() {
		return getColumnsOrder(null);
	}

	/**
	 * Returns a list of the columns' id which represents their order from left to right.
	 * @return ordered list of columns' id
	 */
	public final String getColumnsOrder(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.COLUMNS_ORDER, facesContext);
	}

	/**
	 * Sets a list of the columns' id which represents their order from left to right.
	 * @param columnsOrder ordered list of columns' id
	 */
	public final void setColumnsOrder(String columnsOrder) {
		engine.setProperty(Properties.COLUMNS_ORDER, columnsOrder);
	}

	/**
	 * Sets a list of the columns' id which represents their order from left to right.
	 * @param columnsOrder ordered list of columns' id
	 */
	public final void setColumnsOrder(ValueBinding columnsOrder) {
		engine.setProperty(Properties.COLUMNS_ORDER, columnsOrder);
	}

	/**
	 * Returns <code>true</code> if the attribute "columnsOrder" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isColumnsOrderSetted() {
		return engine.isPropertySetted(Properties.COLUMNS_ORDER);
	}

	/**
	 * Returns the id for the column containing the key for the row.
	 * @return column id
	 */
	public final String getRowValueColumnId() {
		return getRowValueColumnId(null);
	}

	/**
	 * Returns the id for the column containing the key for the row.
	 * @return column id
	 */
	public final String getRowValueColumnId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ROW_VALUE_COLUMN_ID, facesContext);
	}

	/**
	 * Sets the id for the column containing the key for the row.
	 * @param rowValueColumnId column id
	 */
	public final void setRowValueColumnId(String rowValueColumnId) {
		engine.setProperty(Properties.ROW_VALUE_COLUMN_ID, rowValueColumnId);
	}

	/**
	 * Sets the id for the column containing the key for the row.
	 * @param rowValueColumnId column id
	 */
	public final void setRowValueColumnId(ValueBinding rowValueColumnId) {
		engine.setProperty(Properties.ROW_VALUE_COLUMN_ID, rowValueColumnId);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowValueColumnId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRowValueColumnIdSetted() {
		return engine.isPropertySetted(Properties.ROW_VALUE_COLUMN_ID);
	}

	public final Object getCursorValue() {
		return getCursorValue(null);
	}

	public final void setCursorValue(Object cursorValue) {
		engine.setValue(Properties.CURSOR_VALUE, cursorValue);
	}

	public final void setCursorValue(ValueBinding cursorValue) {
		engine.setValueBinding(Properties.CURSOR_VALUE, cursorValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "cursorValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCursorValueSetted() {
		return engine.isPropertySetted(Properties.CURSOR_VALUE);
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
	public final void setRowCountVar(ValueBinding rowCountVar) {
		engine.setProperty(Properties.ROW_COUNT_VAR, rowCountVar);
	}

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
	public final void setRowIndexVar(ValueBinding rowIndexVar) {
		engine.setProperty(Properties.ROW_INDEX_VAR, rowIndexVar);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowIndexVar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRowIndexVarSetted() {
		return engine.isPropertySetted(Properties.ROW_INDEX_VAR);
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
	public final void setClientSelectionFullState(ValueBinding clientSelectionFullState) {
		engine.setProperty(Properties.CLIENT_SELECTION_FULL_STATE, clientSelectionFullState);
	}

	/**
	 * Returns <code>true</code> if the attribute "clientSelectionFullState" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isClientSelectionFullStateSetted() {
		return engine.isPropertySetted(Properties.CLIENT_SELECTION_FULL_STATE);
	}

	/**
	 * Returns a boolean value indicating wether the client should know about the component's full state even if only a part of the data is present (AJAX).
	 * @return boolean
	 */
	public final boolean isClientCheckFullState() {
		return isClientCheckFullState(null);
	}

	/**
	 * Returns a boolean value indicating wether the client should know about the component's full state even if only a part of the data is present (AJAX).
	 * @return boolean
	 */
	public final boolean isClientCheckFullState(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CLIENT_CHECK_FULL_STATE, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the client should know about the component's full state even if only a part of the data is present (AJAX).
	 * @param clientCheckFullState boolean
	 */
	public final void setClientCheckFullState(boolean clientCheckFullState) {
		engine.setProperty(Properties.CLIENT_CHECK_FULL_STATE, clientCheckFullState);
	}

	/**
	 * Sets a boolean value indicating wether the client should know about the component's full state even if only a part of the data is present (AJAX).
	 * @param clientCheckFullState boolean
	 */
	public final void setClientCheckFullState(ValueBinding clientCheckFullState) {
		engine.setProperty(Properties.CLIENT_CHECK_FULL_STATE, clientCheckFullState);
	}

	/**
	 * Returns <code>true</code> if the attribute "clientCheckFullState" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isClientCheckFullStateSetted() {
		return engine.isPropertySetted(Properties.CLIENT_CHECK_FULL_STATE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
