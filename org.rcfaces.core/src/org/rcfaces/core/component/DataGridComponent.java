package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.capability.ICheckCardinalityCapability;
import java.util.Arrays;
import org.rcfaces.core.internal.tools.GridTools;
import org.rcfaces.core.component.capability.ICheckableCapability;
import org.rcfaces.core.internal.converter.CardinalityConverter;
import org.rcfaces.core.component.capability.IFilterCapability;
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
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","rowValueColumnId","horizontalScrollPosition","doubleClickListener","sortedColumnIds","rowIndexVar","selectable","filterProperties","checkable","checkedValues","preference","border","checkCardinality","verticalScrollPosition","paged","columnsOrder","required","disabled","clientCheckFullState","headerVisible","rowCountVar","clientSelectionFullState","checkListener","readOnly","selectionCardinality","selectedValues"}));
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


			setSelectionCardinality(((Integer)CardinalityConverter.SINGLETON.getAsObject(null, null, cardinality)).intValue());
		
	}

	public final void setCheckCardinality(String cardinality) {


			setCheckCardinality(((Integer)CardinalityConverter.SINGLETON.getAsObject(null, null, cardinality)).intValue());
		
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

	public final boolean isSelectable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SELECTABLE, false, facesContext);
	}

	public final void setSelectable(boolean selectable) {
		engine.setProperty(Properties.SELECTABLE, selectable);
	}

	public final void setSelectable(ValueBinding selectable) {
		engine.setProperty(Properties.SELECTABLE, selectable);
	}

	public final int getSelectionCardinality() {
		return getSelectionCardinality(null);
	}

	public final int getSelectionCardinality(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.SELECTION_CARDINALITY,0, facesContext);
	}

	public final void setSelectionCardinality(int selectionCardinality) {
		engine.setProperty(Properties.SELECTION_CARDINALITY, selectionCardinality);
	}

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

	public final boolean isCheckable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CHECKABLE, false, facesContext);
	}

	public final void setCheckable(boolean checkable) {
		engine.setProperty(Properties.CHECKABLE, checkable);
	}

	public final void setCheckable(ValueBinding checkable) {
		engine.setProperty(Properties.CHECKABLE, checkable);
	}

	public final int getCheckCardinality() {
		return getCheckCardinality(null);
	}

	public final int getCheckCardinality(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.CHECK_CARDINALITY,0, facesContext);
	}

	public final void setCheckCardinality(int checkCardinality) {
		engine.setProperty(Properties.CHECK_CARDINALITY, checkCardinality);
	}

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

	public final boolean isRequired(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.REQUIRED, false, facesContext);
	}

	public final void setRequired(boolean required) {
		engine.setProperty(Properties.REQUIRED, required);
	}

	public final void setRequired(ValueBinding required) {
		engine.setProperty(Properties.REQUIRED, required);
	}

	public final boolean isBorder() {
		return isBorder(null);
	}

	public final boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BORDER, true, facesContext);
	}

	public final void setBorder(boolean border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public final void setBorder(ValueBinding border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public final boolean isReadOnly() {
		return isReadOnly(null);
	}

	public final boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.READ_ONLY, false, facesContext);
	}

	public final void setReadOnly(boolean readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	public final void setReadOnly(ValueBinding readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	public final boolean isDisabled() {
		return isDisabled(null);
	}

	public final boolean isDisabled(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DISABLED, false, facesContext);
	}

	public final void setDisabled(boolean disabled) {
		engine.setProperty(Properties.DISABLED, disabled);
	}

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

	public final java.lang.String getHorizontalScrollPosition() {
		return getHorizontalScrollPosition(null);
	}

	public final java.lang.String getHorizontalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HORIZONTAL_SCROLL_POSITION, facesContext);
	}

	public final void setHorizontalScrollPosition(java.lang.String horizontalScrollPosition) {
		engine.setProperty(Properties.HORIZONTAL_SCROLL_POSITION, horizontalScrollPosition);
	}

	public final void setHorizontalScrollPosition(ValueBinding horizontalScrollPosition) {
		engine.setProperty(Properties.HORIZONTAL_SCROLL_POSITION, horizontalScrollPosition);
	}

	public final java.lang.String getVerticalScrollPosition() {
		return getVerticalScrollPosition(null);
	}

	public final java.lang.String getVerticalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.VERTICAL_SCROLL_POSITION, facesContext);
	}

	public final void setVerticalScrollPosition(java.lang.String verticalScrollPosition) {
		engine.setProperty(Properties.VERTICAL_SCROLL_POSITION, verticalScrollPosition);
	}

	public final void setVerticalScrollPosition(ValueBinding verticalScrollPosition) {
		engine.setProperty(Properties.VERTICAL_SCROLL_POSITION, verticalScrollPosition);
	}

	public final org.rcfaces.core.model.IFilterProperties getFilterProperties() {
		return getFilterProperties(null);
	}

	public final org.rcfaces.core.model.IFilterProperties getFilterProperties(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.model.IFilterProperties)engine.getProperty(Properties.FILTER_PROPERTIES, facesContext);
	}

	public final void setFilterProperties(org.rcfaces.core.model.IFilterProperties filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	public final void setFilterProperties(ValueBinding filterProperties) {
		engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
	}

	public final org.rcfaces.core.preference.IComponentPreference getPreference() {
		return getPreference(null);
	}

	public final org.rcfaces.core.preference.IComponentPreference getPreference(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.preference.IComponentPreference)engine.getProperty(Properties.PREFERENCE, facesContext);
	}

	public final void setPreference(org.rcfaces.core.preference.IComponentPreference preference) {
		engine.setProperty(Properties.PREFERENCE, preference);
	}

	public final void setPreference(ValueBinding preference) {
		engine.setProperty(Properties.PREFERENCE, preference);
	}

	public final boolean isHeaderVisible() {
		return isHeaderVisible(null);
	}

	public final boolean isHeaderVisible(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.HEADER_VISIBLE, true, facesContext);
	}

	public final void setHeaderVisible(boolean headerVisible) {
		engine.setProperty(Properties.HEADER_VISIBLE, headerVisible);
	}

	public final void setHeaderVisible(ValueBinding headerVisible) {
		engine.setProperty(Properties.HEADER_VISIBLE, headerVisible);
	}

	public final boolean isHeaderVisibleSetted() {
		return engine.isPropertySetted(Properties.HEADER_VISIBLE);
	}

	public final boolean isPaged() {
		return isPaged(null);
	}

	public final boolean isPaged(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.PAGED, true, facesContext);
	}

	public final void setPaged(boolean paged) {
		engine.setProperty(Properties.PAGED, paged);
	}

	public final void setPaged(ValueBinding paged) {
		engine.setProperty(Properties.PAGED, paged);
	}

	public final boolean isPagedSetted() {
		return engine.isPropertySetted(Properties.PAGED);
	}

	public final Object getSelectedValues() {
		return getSelectedValues(null);
	}

	public final Object getSelectedValues(javax.faces.context.FacesContext facesContext) {
		return engine.getValue(Properties.SELECTED_VALUES, facesContext);
	}

	public final void setSelectedValues(Object selectedValues) {
		engine.setValue(Properties.SELECTED_VALUES, selectedValues);
	}

	public final void setSelectedValues(ValueBinding selectedValues) {
		engine.setValueBinding(Properties.SELECTED_VALUES, selectedValues);
	}

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

	public final boolean isCheckedValuesSetted() {
		return engine.isPropertySetted(Properties.CHECKED_VALUES);
	}

	public final String getSortedColumnIds() {
		return getSortedColumnIds(null);
	}

	public final String getSortedColumnIds(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SORTED_COLUMN_IDS, facesContext);
	}

	public final void setSortedColumnIds(String sortedColumnIds) {
		engine.setProperty(Properties.SORTED_COLUMN_IDS, sortedColumnIds);
	}

	public final void setSortedColumnIds(ValueBinding sortedColumnIds) {
		engine.setProperty(Properties.SORTED_COLUMN_IDS, sortedColumnIds);
	}

	public final boolean isSortedColumnIdsSetted() {
		return engine.isPropertySetted(Properties.SORTED_COLUMN_IDS);
	}

	public final String getColumnsOrder() {
		return getColumnsOrder(null);
	}

	public final String getColumnsOrder(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.COLUMNS_ORDER, facesContext);
	}

	public final void setColumnsOrder(String columnsOrder) {
		engine.setProperty(Properties.COLUMNS_ORDER, columnsOrder);
	}

	public final void setColumnsOrder(ValueBinding columnsOrder) {
		engine.setProperty(Properties.COLUMNS_ORDER, columnsOrder);
	}

	public final boolean isColumnsOrderSetted() {
		return engine.isPropertySetted(Properties.COLUMNS_ORDER);
	}

	public final String getRowValueColumnId() {
		return getRowValueColumnId(null);
	}

	public final String getRowValueColumnId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ROW_VALUE_COLUMN_ID, facesContext);
	}

	public final void setRowValueColumnId(String rowValueColumnId) {
		engine.setProperty(Properties.ROW_VALUE_COLUMN_ID, rowValueColumnId);
	}

	public final void setRowValueColumnId(ValueBinding rowValueColumnId) {
		engine.setProperty(Properties.ROW_VALUE_COLUMN_ID, rowValueColumnId);
	}

	public final boolean isRowValueColumnIdSetted() {
		return engine.isPropertySetted(Properties.ROW_VALUE_COLUMN_ID);
	}

	public final String getRowCountVar() {
		return getRowCountVar(null);
	}

	public final String getRowCountVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ROW_COUNT_VAR, facesContext);
	}

	public final void setRowCountVar(String rowCountVar) {
		engine.setProperty(Properties.ROW_COUNT_VAR, rowCountVar);
	}

	public final void setRowCountVar(ValueBinding rowCountVar) {
		engine.setProperty(Properties.ROW_COUNT_VAR, rowCountVar);
	}

	public final boolean isRowCountVarSetted() {
		return engine.isPropertySetted(Properties.ROW_COUNT_VAR);
	}

	public final String getRowIndexVar() {
		return getRowIndexVar(null);
	}

	public final String getRowIndexVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ROW_INDEX_VAR, facesContext);
	}

	public final void setRowIndexVar(String rowIndexVar) {
		engine.setProperty(Properties.ROW_INDEX_VAR, rowIndexVar);
	}

	public final void setRowIndexVar(ValueBinding rowIndexVar) {
		engine.setProperty(Properties.ROW_INDEX_VAR, rowIndexVar);
	}

	public final boolean isRowIndexVarSetted() {
		return engine.isPropertySetted(Properties.ROW_INDEX_VAR);
	}

	public final boolean isClientSelectionFullState() {
		return isClientSelectionFullState(null);
	}

	public final boolean isClientSelectionFullState(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CLIENT_SELECTION_FULL_STATE, false, facesContext);
	}

	public final void setClientSelectionFullState(boolean clientSelectionFullState) {
		engine.setProperty(Properties.CLIENT_SELECTION_FULL_STATE, clientSelectionFullState);
	}

	public final void setClientSelectionFullState(ValueBinding clientSelectionFullState) {
		engine.setProperty(Properties.CLIENT_SELECTION_FULL_STATE, clientSelectionFullState);
	}

	public final boolean isClientSelectionFullStateSetted() {
		return engine.isPropertySetted(Properties.CLIENT_SELECTION_FULL_STATE);
	}

	public final boolean isClientCheckFullState() {
		return isClientCheckFullState(null);
	}

	public final boolean isClientCheckFullState(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CLIENT_CHECK_FULL_STATE, false, facesContext);
	}

	public final void setClientCheckFullState(boolean clientCheckFullState) {
		engine.setProperty(Properties.CLIENT_CHECK_FULL_STATE, clientCheckFullState);
	}

	public final void setClientCheckFullState(ValueBinding clientCheckFullState) {
		engine.setProperty(Properties.CLIENT_CHECK_FULL_STATE, clientCheckFullState);
	}

	public final boolean isClientCheckFullStateSetted() {
		return engine.isPropertySetted(Properties.CLIENT_CHECK_FULL_STATE);
	}

	public void release() {
		super.release();
	}
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
