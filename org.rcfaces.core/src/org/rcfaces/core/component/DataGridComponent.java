package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.ICheckCardinalityCapability;
import org.rcfaces.core.component.capability.ICheckEventCapability;
import org.rcfaces.core.component.capability.ICheckableCapability;
import org.rcfaces.core.component.capability.ICheckedValuesCapability;
import org.rcfaces.core.component.capability.IClientCheckFullStateCapability;
import org.rcfaces.core.component.capability.IClientSelectionFullStateCapability;
import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.capability.IOrderedChildrenCapability;
import org.rcfaces.core.component.capability.IPagedCapability;
import org.rcfaces.core.component.capability.IPreferenceCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.IRowStyleClassCapability;
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.component.capability.ISelectableCapability;
import org.rcfaces.core.component.capability.ISelectionCardinalityCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.ISelectionValuesCapability;
import org.rcfaces.core.component.capability.IShowValueCapability;
import org.rcfaces.core.component.capability.ISortedChildrenCapability;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.component.iterator.IDataColumnIterator;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.capability.ISortedComponentsCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.CardinalityConverter;
import org.rcfaces.core.internal.tools.CheckTools;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.tools.GridTools;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.internal.tools.OrderTools;
import org.rcfaces.core.internal.tools.SelectionTools;
import org.rcfaces.core.internal.tools.SortTools;
import org.rcfaces.core.lang.provider.ICursorProvider;
import org.rcfaces.core.model.ISortedComponent;

/**
 * <p>
 * The dataGrid Component is a grid component. It can be compared to the grid
 * found in the list part of the modern file explorer. It allows sorts,
 * resizing, contextual menus ...
 * </p>
 * <p>
 * The dataGrid Component has the following capabilities :
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
        ISelectionEventCapability, ISelectableCapability,
        ISelectionCardinalityCapability, ISelectionValuesCapability,
        ICheckEventCapability, ICheckableCapability,
        ICheckCardinalityCapability, ICheckedValuesCapability,
        IDoubleClickEventCapability, IRequiredCapability, IBorderCapability,
        IRowStyleClassCapability, IReadOnlyCapability, IDisabledCapability,
        IMenuCapability, IScrollableCapability, IFilterCapability,
        IShowValueCapability, IPreferenceCapability, IPagedCapability,
        IClientSelectionFullStateCapability, IClientCheckFullStateCapability,
        ICursorProvider, IOrderedChildrenCapability,
        ISortedComponentsCapability, IGridComponent, ISortedChildrenCapability {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.dataGrid";

    protected static final Set CAMELIA_ATTRIBUTES = new HashSet(
            AbstractGridComponent.CAMELIA_ATTRIBUTES);
    static {
        CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {
                "selectionListener", "rowValueColumnId",
                "horizontalScrollPosition", "doubleClickListener",
                "rowIndexVar", "selectable", "showValue", "filterProperties",
                "checkable", "checkedValues", "preference", "border",
                "checkCardinality", "verticalScrollPosition", "paged",
                "required", "disabled", "cursorValue", "clientCheckFullState",
                "rowStyleClass", "headerVisible", "rowCountVar",
                "clientSelectionFullState", "checkListener", "readOnly",
                "selectionCardinality", "selectedValues" }));
    }

    public DataGridComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public DataGridComponent(String componentId) {
        this();
        setId(componentId);
    }

    public final void setSortedChildren(UIComponent[] components) {

        SortTools.setSortedChildren(null, this, engine,
                DataColumnComponent.class, components);

    }

    public final UIComponent[] getSortedChildren() {

        return SortTools.getSortedChildren(null, this, engine,
                DataColumnComponent.class);

    }

    public final UIComponent[] getOrderedChildren() {

        return OrderTools.getOrderedChildren(null, this, engine,
                DataColumnComponent.class);

    }

    public final void setOrderedChildren(UIComponent[] components) {

        OrderTools.setOrderedChildren(null, this, engine,
                DataColumnComponent.class, components);

    }

    public final IColumnIterator listColumns() {

        return GridTools.listColumns(this,
                org.rcfaces.core.component.DataColumnComponent.class);

    }

    public final IDataColumnIterator listDataColumns() {

        return GridTools.listDataColumns(this);

    }

    public final void setSelectionCardinality(String cardinality) {

        setSelectionCardinality(((Integer) CardinalityConverter.SINGLETON
                .getAsObject(null, this, cardinality)).intValue());

    }

    public final void setCheckCardinality(String cardinality) {

        setCheckCardinality(((Integer) CardinalityConverter.SINGLETON
                .getAsObject(null, this, cardinality)).intValue());

    }

    public final int getSelectedValuesCount() {

        return SelectionTools.getCount(getSelectedValues());

    }

    public final Object getFirstSelectedValue() {

        return SelectionTools.getFirst(getSelectedValues(), null);

    }

    public final Object[] listSelectedValues() {

        return SelectionTools.listValues(getSelectedValues(), getValue());

    }

    public final int getCheckedValuesCount() {

        return CheckTools.getCount(getCheckedValues());

    }

    public final Object getFirstCheckedValue() {

        return CheckTools.getFirst(getCheckedValues(), null);

    }

    public final Object[] listCheckedValues() {

        return CheckTools.listValues(getCheckedValues(), getValue());

    }

    public final DataColumnComponent[] getSortedColumns() {

        return (DataColumnComponent[]) getSortedChildren();

    }

    public final DataColumnComponent getFirstSortedColumn() {

        return (DataColumnComponent) SortTools.getFirstSortedChild(null, this,
                engine, DataColumnComponent.class);

    }

    public final void setSortedColumn(DataColumnComponent dataColumn) {

        SortTools.setSortedChildren(null, this, engine,
                DataColumnComponent.class,
                new DataColumnComponent[] { dataColumn });

    }

    public final void setSortedColumns(DataColumnComponent[] dataColumns) {

        setSortedChildren(dataColumns);

    }

    public final ISortedComponent[] listSortedComponents(FacesContext context) {

        return GridTools.listSortedComponents(context, this);

    }

    /**
     * 
     */
    public final void select(Object rowValue) {

        GridTools.select(null, this, rowValue);

    }

    /**
     * Selects the item at the given zero-relative index in the receiver. If the
     * item at the index was already selected, it remains selected. Indices that
     * are out of range are ignored.
     * 
     * @param index
     *            the index of the item to select
     */
    public final void select(int index) {

        GridTools.select(null, this, index);

    }

    /**
     * Selects the items at the given zero-relative indices in the receiver. The
     * current selection is not cleared before the new items are selected. If
     * the item at a given index is not selected, it is selected. If the item at
     * a given index was already selected, it remains selected. Indices that are
     * out of range and duplicate indices are ignored. If the receiver is
     * single-select and multiple indices are specified, then all indices are
     * ignored.
     * 
     * @param indices
     *            the array of indices for the items to select
     */
    public final void select(int[] indices) {

        GridTools.select(null, this, indices);

    }

    /**
     * Selects the items in the range specified by the given zero-relative
     * indices in the receiver. The range of indices is inclusive. The current
     * selection is not cleared before the new items are selected. If an item in
     * the given range is not selected, it is selected. If an item in the given
     * range was already selected, it remains selected. Indices that are out of
     * range are ignored and no items will be selected if start is greater than
     * end. If the receiver is single-select and there is more than one item in
     * the given range, then all indices are ignored.
     * 
     * @param start
     *            the start of the range
     * @param end
     *            the end of the range
     */
    public final void select(int start, int end) {

        GridTools.select(null, this, start, end);

    }

    /**
     * Selects all of the items in the receiver. If the receiver is
     * single-select, do nothing.
     */
    public final void selectAll() {

        GridTools.selectAll(null, this);

    }

    public final void deselect(Object rowValue) {

        GridTools.deselect(null, this, rowValue);

    }

    /**
     * Deselects the item at the given zero-relative index in the receiver. If
     * the item at the index was already deselected, it remains deselected.
     * Indices that are out of range are ignored.
     * 
     * @param index
     *            the index of the item to deselect
     */
    public final void deselect(int index) {

        GridTools.deselect(null, this, index);

    }

    /**
     * Deselects the items at the given zero-relative indices in the receiver.
     * If the item at the given zero-relative index in the receiver is selected,
     * it is deselected. If the item at the index was not selected, it remains
     * deselected. Indices that are out of range and duplicate indices are
     * ignored.
     * 
     * @param indices
     *            the array of indices for the items to deselect
     */
    public final void deselect(int[] indices) {

        GridTools.deselect(null, this, indices);

    }

    /**
     * Deselects the items at the given zero-relative indices in the receiver.
     * If the item at the given zero-relative index in the receiver is selected,
     * it is deselected. If the item at the index was not selected, it remains
     * deselected. The range of the indices is inclusive. Indices that are out
     * of range are ignored.
     * 
     * @param start
     *            the start index of the items to deselect
     * @param end
     *            the end index of the items to deselect
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

    public final Object getCursorValue(FacesContext facesContext) {

        Object cursorValue = engine.getValue(Properties.CURSOR_VALUE,
                facesContext);
        if (cursorValue != null) {
            return cursorValue;
        }

        return ComponentTools.getCursorValue(getValue(), this, facesContext);

    }

    public final Object getSelectedValues(FacesContext facesContext) {

        if (engine.isPropertySetted(Properties.SELECTED_VALUES)) {
            return engine.getValue(Properties.SELECTED_VALUES, facesContext);
        }

        Object values = SelectionTools.adaptValue(getValue(), null);
        return values;
        /*
         * if (values!=null) { return values; }
         * 
         * if (this.isRowValueColumnIdSetted()) { values=new Object[0];
         *  } else { values=new ArrayIndexesModel(); }
         * 
         * setSelectedValues(values);
         * 
         * return values;
         */

    }

    public final Object getCheckedValues(FacesContext facesContext) {

        if (engine.isPropertySetted(Properties.CHECKED_VALUES)) {
            return engine.getValue(Properties.CHECKED_VALUES, facesContext);
        }

        Object values = CheckTools.adaptValue(getValue(), null);
        return values;
        /*
         * if (values!=null) { return values; }
         * 
         * if (this.isRowValueColumnIdSetted()) { values=new Object[0];
         *  } else { values=new ArrayIndexesModel(); }
         * 
         * setCheckedValues(values);
         * 
         * return values;
         */

    }

    public final void addSelectionListener(
            org.rcfaces.core.event.ISelectionListener listener) {
        addFacesListener(listener);
    }

    public final void removeSelectionListener(
            org.rcfaces.core.event.ISelectionListener listener) {
        removeFacesListener(listener);
    }

    public final javax.faces.event.FacesListener[] listSelectionListeners() {
        return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
    }

    public boolean isSelectable() {
        return isSelectable(null);
    }

    /**
     * See {@link #isSelectable() isSelectable()} for more details
     */
    public boolean isSelectable(javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.SELECTABLE, false,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "selectable" is set.
     * 
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
     * See {@link #getSelectionCardinality() getSelectionCardinality()} for more
     * details
     */
    public int getSelectionCardinality(
            javax.faces.context.FacesContext facesContext) {
        return engine.getIntProperty(Properties.SELECTION_CARDINALITY, 0,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "selectionCardinality" is
     * set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isSelectionCardinalitySetted() {
        return engine.isPropertySetted(Properties.SELECTION_CARDINALITY);
    }

    public void setSelectionCardinality(int selectionCardinality) {
        engine.setProperty(Properties.SELECTION_CARDINALITY,
                selectionCardinality);
    }

    public java.lang.Object getSelectedValues() {
        return getSelectedValues(null);
    }

    /**
     * Returns <code>true</code> if the attribute "selectedValues" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isSelectedValuesSetted() {
        return engine.isPropertySetted(Properties.SELECTED_VALUES);
    }

    public void setSelectedValues(java.lang.Object selectedValues) {
        engine.setProperty(Properties.SELECTED_VALUES, selectedValues);
    }

    /**
     * Return the type of the property represented by the {@link ValueBinding},
     * relative to the specified {@link javax.faces.context.FacesContext}.
     */
    public Class getSelectedValuesType(
            javax.faces.context.FacesContext facesContext) {
        ValueBinding valueBinding = engine
                .getValueBindingProperty(Properties.SELECTED_VALUES);
        if (valueBinding == null) {
            return null;
        }
        if (facesContext == null) {
            facesContext = javax.faces.context.FacesContext
                    .getCurrentInstance();
        }
        return valueBinding.getType(facesContext);
    }

    public final void addCheckListener(
            org.rcfaces.core.event.ICheckListener listener) {
        addFacesListener(listener);
    }

    public final void removeCheckListener(
            org.rcfaces.core.event.ICheckListener listener) {
        removeFacesListener(listener);
    }

    public final javax.faces.event.FacesListener[] listCheckListeners() {
        return getFacesListeners(org.rcfaces.core.event.ICheckListener.class);
    }

    public boolean isCheckable() {
        return isCheckable(null);
    }

    /**
     * See {@link #isCheckable() isCheckable()} for more details
     */
    public boolean isCheckable(javax.faces.context.FacesContext facesContext) {
        return engine
                .getBoolProperty(Properties.CHECKABLE, false, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "checkable" is set.
     * 
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
        return engine.getIntProperty(Properties.CHECK_CARDINALITY, 0,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "checkCardinality" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isCheckCardinalitySetted() {
        return engine.isPropertySetted(Properties.CHECK_CARDINALITY);
    }

    public void setCheckCardinality(int checkCardinality) {
        engine.setProperty(Properties.CHECK_CARDINALITY, checkCardinality);
    }

    public java.lang.Object getCheckedValues() {
        return getCheckedValues(null);
    }

    /**
     * Returns <code>true</code> if the attribute "checkedValues" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isCheckedValuesSetted() {
        return engine.isPropertySetted(Properties.CHECKED_VALUES);
    }

    public void setCheckedValues(java.lang.Object checkedValues) {
        engine.setProperty(Properties.CHECKED_VALUES, checkedValues);
    }

    /**
     * Return the type of the property represented by the {@link ValueBinding},
     * relative to the specified {@link javax.faces.context.FacesContext}.
     */
    public Class getCheckedValuesType(
            javax.faces.context.FacesContext facesContext) {
        ValueBinding valueBinding = engine
                .getValueBindingProperty(Properties.CHECKED_VALUES);
        if (valueBinding == null) {
            return null;
        }
        if (facesContext == null) {
            facesContext = javax.faces.context.FacesContext
                    .getCurrentInstance();
        }
        return valueBinding.getType(facesContext);
    }

    public final void addDoubleClickListener(
            org.rcfaces.core.event.IDoubleClickListener listener) {
        addFacesListener(listener);
    }

    public final void removeDoubleClickListener(
            org.rcfaces.core.event.IDoubleClickListener listener) {
        removeFacesListener(listener);
    }

    public final javax.faces.event.FacesListener[] listDoubleClickListeners() {
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
     * 
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
     * 
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
    public java.lang.String getRowStyleClass(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.ROW_STYLE_CLASS,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "rowStyleClass" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isRowStyleClassSetted() {
        return engine.isPropertySetted(Properties.ROW_STYLE_CLASS);
    }

    public void setRowStyleClass(java.lang.String rowStyleClass) {
        engine.setProperty(Properties.ROW_STYLE_CLASS, rowStyleClass);
    }

    public boolean isReadOnly() {
        return isReadOnly(null);
    }

    /**
     * See {@link #isReadOnly() isReadOnly()} for more details
     */
    public boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
        return engine
                .getBoolProperty(Properties.READ_ONLY, false, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "readOnly" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isReadOnlySetted() {
        return engine.isPropertySetted(Properties.READ_ONLY);
    }

    public void setReadOnly(boolean readOnly) {
        engine.setProperty(Properties.READ_ONLY, readOnly);
    }

    public boolean isDisabled() {
        return isDisabled(null);
    }

    /**
     * See {@link #isDisabled() isDisabled()} for more details
     */
    public boolean isDisabled(javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.DISABLED, false, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "disabled" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isDisabledSetted() {
        return engine.isPropertySetted(Properties.DISABLED);
    }

    public void setDisabled(boolean disabled) {
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

    public int getHorizontalScrollPosition() {
        return getHorizontalScrollPosition(null);
    }

    /**
     * See {@link #getHorizontalScrollPosition() getHorizontalScrollPosition()}
     * for more details
     */
    public int getHorizontalScrollPosition(
            javax.faces.context.FacesContext facesContext) {
        return engine.getIntProperty(Properties.HORIZONTAL_SCROLL_POSITION, 0,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "horizontalScrollPosition"
     * is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isHorizontalScrollPositionSetted() {
        return engine.isPropertySetted(Properties.HORIZONTAL_SCROLL_POSITION);
    }

    public void setHorizontalScrollPosition(int horizontalScrollPosition) {
        engine.setProperty(Properties.HORIZONTAL_SCROLL_POSITION,
                horizontalScrollPosition);
    }

    public int getVerticalScrollPosition() {
        return getVerticalScrollPosition(null);
    }

    /**
     * See {@link #getVerticalScrollPosition() getVerticalScrollPosition()} for
     * more details
     */
    public int getVerticalScrollPosition(
            javax.faces.context.FacesContext facesContext) {
        return engine.getIntProperty(Properties.VERTICAL_SCROLL_POSITION, 0,
                facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "verticalScrollPosition" is
     * set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isVerticalScrollPositionSetted() {
        return engine.isPropertySetted(Properties.VERTICAL_SCROLL_POSITION);
    }

    public void setVerticalScrollPosition(int verticalScrollPosition) {
        engine.setProperty(Properties.VERTICAL_SCROLL_POSITION,
                verticalScrollPosition);
    }

    public org.rcfaces.core.model.IFilterProperties getFilterProperties() {
        return getFilterProperties(null);
    }

    /**
     * See {@link #getFilterProperties() getFilterProperties()} for more details
     */
    public org.rcfaces.core.model.IFilterProperties getFilterProperties(
            javax.faces.context.FacesContext facesContext) {
        return (org.rcfaces.core.model.IFilterProperties) engine.getProperty(
                Properties.FILTER_PROPERTIES, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "filterProperties" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isFilterPropertiesSetted() {
        return engine.isPropertySetted(Properties.FILTER_PROPERTIES);
    }

    public void setFilterProperties(
            org.rcfaces.core.model.IFilterProperties filterProperties) {
        engine.setProperty(Properties.FILTER_PROPERTIES, filterProperties);
    }

    public java.lang.Object getShowValue() {
        return getShowValue(null);
    }

    /**
     * See {@link #getShowValue() getShowValue()} for more details
     */
    public java.lang.Object getShowValue(
            javax.faces.context.FacesContext facesContext) {
        return engine.getProperty(Properties.SHOW_VALUE, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "showValue" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isShowValueSetted() {
        return engine.isPropertySetted(Properties.SHOW_VALUE);
    }

    public void setShowValue(java.lang.Object showValue) {
        engine.setProperty(Properties.SHOW_VALUE, showValue);
    }

    public org.rcfaces.core.preference.IComponentPreference getPreference() {
        return getPreference(null);
    }

    /**
     * See {@link #getPreference() getPreference()} for more details
     */
    public org.rcfaces.core.preference.IComponentPreference getPreference(
            javax.faces.context.FacesContext facesContext) {
        return (org.rcfaces.core.preference.IComponentPreference) engine
                .getProperty(Properties.PREFERENCE, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "preference" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isPreferenceSetted() {
        return engine.isPropertySetted(Properties.PREFERENCE);
    }

    public void setPreference(
            org.rcfaces.core.preference.IComponentPreference preference) {
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

    public boolean isClientSelectionFullState() {
        return isClientSelectionFullState(null);
    }

    /**
     * See {@link #isClientSelectionFullState() isClientSelectionFullState()}
     * for more details
     */
    public boolean isClientSelectionFullState(
            javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.CLIENT_SELECTION_FULL_STATE,
                false, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "clientSelectionFullState"
     * is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isClientSelectionFullStateSetted() {
        return engine.isPropertySetted(Properties.CLIENT_SELECTION_FULL_STATE);
    }

    public void setClientSelectionFullState(boolean clientSelectionFullState) {
        engine.setProperty(Properties.CLIENT_SELECTION_FULL_STATE,
                clientSelectionFullState);
    }

    public boolean isClientCheckFullState() {
        return isClientCheckFullState(null);
    }

    /**
     * See {@link #isClientCheckFullState() isClientCheckFullState()} for more
     * details
     */
    public boolean isClientCheckFullState(
            javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.CLIENT_CHECK_FULL_STATE,
                false, facesContext);
    }

    /**
     * Returns <code>true</code> if the attribute "clientCheckFullState" is
     * set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isClientCheckFullStateSetted() {
        return engine.isPropertySetted(Properties.CLIENT_CHECK_FULL_STATE);
    }

    public void setClientCheckFullState(boolean clientCheckFullState) {
        engine.setProperty(Properties.CLIENT_CHECK_FULL_STATE,
                clientCheckFullState);
    }

    public java.lang.Object getCursorValue() {
        return getCursorValue(null);
    }

    /**
     * Returns <code>true</code> if the attribute "cursorValue" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isCursorValueSetted() {
        return engine.isPropertySetted(Properties.CURSOR_VALUE);
    }

    public void setCursorValue(java.lang.Object cursorValue) {
        engine.setProperty(Properties.CURSOR_VALUE, cursorValue);
    }

    /**
     * Returns a boolean value indicating wether the header should be visible.
     * 
     * @return true if the header is visible
     */
    public final boolean isHeaderVisible() {
        return isHeaderVisible(null);
    }

    /**
     * Returns a boolean value indicating wether the header should be visible.
     * 
     * @return true if the header is visible
     */
    public final boolean isHeaderVisible(
            javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.HEADER_VISIBLE, true,
                facesContext);
    }

    /**
     * Sets a boolean value indicating wether the header should be visible.
     * 
     * @param headerVisible
     *            true if the header should be visible
     */
    public final void setHeaderVisible(boolean headerVisible) {
        engine.setProperty(Properties.HEADER_VISIBLE, headerVisible);
    }

    /**
     * Sets a boolean value indicating wether the header should be visible.
     * 
     * @param headerVisible
     *            true if the header should be visible
     */
    /**
     * Returns <code>true</code> if the attribute "headerVisible" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isHeaderVisibleSetted() {
        return engine.isPropertySetted(Properties.HEADER_VISIBLE);
    }

    /**
     * Returns the id for the column containing the key for the row.
     * 
     * @return column id
     */
    public final String getRowValueColumnId() {
        return getRowValueColumnId(null);
    }

    /**
     * Returns the id for the column containing the key for the row.
     * 
     * @return column id
     */
    public final String getRowValueColumnId(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.ROW_VALUE_COLUMN_ID,
                facesContext);
    }

    /**
     * Sets the id for the column containing the key for the row.
     * 
     * @param rowValueColumnId
     *            column id
     */
    public final void setRowValueColumnId(String rowValueColumnId) {
        engine.setProperty(Properties.ROW_VALUE_COLUMN_ID, rowValueColumnId);
    }

    /**
     * Sets the id for the column containing the key for the row.
     * 
     * @param rowValueColumnId
     *            column id
     */
    /**
     * Returns <code>true</code> if the attribute "rowValueColumnId" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isRowValueColumnIdSetted() {
        return engine.isPropertySetted(Properties.ROW_VALUE_COLUMN_ID);
    }

    /**
     * Returns a string value specifying the name of the variable receiving the
     * number of rows.
     * 
     * @return variable name
     */
    public final String getRowCountVar() {
        return getRowCountVar(null);
    }

    /**
     * Returns a string value specifying the name of the variable receiving the
     * number of rows.
     * 
     * @return variable name
     */
    public final String getRowCountVar(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.ROW_COUNT_VAR, facesContext);
    }

    /**
     * Sets a string value specifying the name of the variable receiving the
     * number of rows.
     * 
     * @param rowCountVar
     *            variable name
     */
    public final void setRowCountVar(String rowCountVar) {
        engine.setProperty(Properties.ROW_COUNT_VAR, rowCountVar);
    }

    /**
     * Sets a string value specifying the name of the variable receiving the
     * number of rows.
     * 
     * @param rowCountVar
     *            variable name
     */
    /**
     * Returns <code>true</code> if the attribute "rowCountVar" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isRowCountVarSetted() {
        return engine.isPropertySetted(Properties.ROW_COUNT_VAR);
    }

    /**
     * Returns a string value specifying the name of the variable receiving the
     * index of the current row.
     * 
     * @return variable name
     */
    public final String getRowIndexVar() {
        return getRowIndexVar(null);
    }

    /**
     * Returns a string value specifying the name of the variable receiving the
     * index of the current row.
     * 
     * @return variable name
     */
    public final String getRowIndexVar(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.ROW_INDEX_VAR, facesContext);
    }

    /**
     * Sets a string value specifying the name of the variable receiving the
     * index of the current row.
     * 
     * @param rowIndexVar
     *            variable name
     */
    public final void setRowIndexVar(String rowIndexVar) {
        engine.setProperty(Properties.ROW_INDEX_VAR, rowIndexVar);
    }

    /**
     * Sets a string value specifying the name of the variable receiving the
     * index of the current row.
     * 
     * @param rowIndexVar
     *            variable name
     */
    /**
     * Returns <code>true</code> if the attribute "rowIndexVar" is set.
     * 
     * @return <code>true</code> if the attribute is set.
     */
    public final boolean isRowIndexVarSetted() {
        return engine.isPropertySetted(Properties.ROW_INDEX_VAR);
    }

    protected Set getCameliaFields() {
        return CAMELIA_ATTRIBUTES;
    }
}
