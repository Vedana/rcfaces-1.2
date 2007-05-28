package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import java.util.Arrays;
import org.rcfaces.core.internal.tools.SortTools;
import org.rcfaces.core.internal.tools.GridTools;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.component.capability.IPagerMessageCapability;
import org.rcfaces.core.component.capability.IRowStyleClassCapability;
import org.rcfaces.core.internal.tools.OrderTools;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.AbstractGridComponent;
import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.ISortedChildrenCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import javax.faces.el.ValueBinding;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IOrderedChildrenCapability;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.core.component.capability.IPagedCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.ComboColumnComponent;

public class ComboGridComponent extends AbstractGridComponent implements 
	ISelectionEventCapability,
	IDisabledCapability,
	IRequiredCapability,
	IReadOnlyCapability,
	IBorderCapability,
	IRowStyleClassCapability,
	IPagerMessageCapability,
	IScrollableCapability,
	IFilterCapability,
	IPagedCapability,
	IOrderedChildrenCapability,
	IGridComponent,
	ISortedChildrenCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.comboGrid";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractGridComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","popupHeight","suggestionDelayMs","horizontalScrollPosition","message","pagerStyleClass","rowIndexVar","valueColumnId","pagerLookId","filterProperties","oneResultMessage","zeroResultMessage","border","valueFormat","gridStyleClass","suggestionMinChars","labelColumnId","gridLookId","verticalScrollPosition","paged","required","disabled","selectedValue","rowStyleClass","rowCountVar","manyResultsMessage","popupWidth","readOnly"}));
	}

	public ComboGridComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ComboGridComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setSortedChildren(UIComponent[] components) {


				SortTools.setSortedChildren(null, this, engine, ComboColumnComponent.class, components);
			
	}

	public final UIComponent[] getSortedChildren() {


				return SortTools.getSortedChildren(null, this, engine, ComboColumnComponent.class);
			
	}

	public final UIComponent[] getOrderedChildren() {


				return OrderTools.getOrderedChildren(null, this, engine, ComboColumnComponent.class);
			
	}

	public final void setOrderedChildren(UIComponent[] components) {


				OrderTools.setOrderedChildren(null, this, engine, ComboColumnComponent.class, components);
			
	}

	public final IColumnIterator listColumns() {


			return GridTools.listColumns(this, ComboColumnComponent.class);
			
	}

	public final ComboColumnComponent[] getSortedColumns() {


				return (ComboColumnComponent[])getSortedChildren();
			
	}

	public final ComboColumnComponent getFirstSortedColumn() {


				return (ComboColumnComponent)SortTools.getFirstSortedChild(null, this, engine, ComboColumnComponent.class );
			
	}

	public final void setSortedColumn(ComboColumnComponent comboColumn) {


				SortTools.setSortedChildren(null, this, engine, ComboColumnComponent.class, new ComboColumnComponent[] { comboColumn });
			
	}

	public final void setSortedColumns(ComboColumnComponent[] componentsColumns) {


				setSortedChildren(componentsColumns);
			
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
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledSetted() {
		return engine.isPropertySetted(Properties.DISABLED);
	}

	public void setDisabled(boolean disabled) {
		engine.setProperty(Properties.DISABLED, disabled);
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

	public java.lang.String getManyResultsMessage() {
		return getManyResultsMessage(null);
	}

	/**
	 * See {@link #getManyResultsMessage() getManyResultsMessage()} for more details
	 */
	public java.lang.String getManyResultsMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MANY_RESULTS_MESSAGE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "manyResultsMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isManyResultsMessageSetted() {
		return engine.isPropertySetted(Properties.MANY_RESULTS_MESSAGE);
	}

	public void setManyResultsMessage(java.lang.String manyResultsMessage) {
		engine.setProperty(Properties.MANY_RESULTS_MESSAGE, manyResultsMessage);
	}

	public java.lang.String getMessage() {
		return getMessage(null);
	}

	/**
	 * See {@link #getMessage() getMessage()} for more details
	 */
	public java.lang.String getMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MESSAGE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "message" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMessageSetted() {
		return engine.isPropertySetted(Properties.MESSAGE);
	}

	public void setMessage(java.lang.String message) {
		engine.setProperty(Properties.MESSAGE, message);
	}

	public java.lang.String getOneResultMessage() {
		return getOneResultMessage(null);
	}

	/**
	 * See {@link #getOneResultMessage() getOneResultMessage()} for more details
	 */
	public java.lang.String getOneResultMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ONE_RESULT_MESSAGE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "oneResultMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isOneResultMessageSetted() {
		return engine.isPropertySetted(Properties.ONE_RESULT_MESSAGE);
	}

	public void setOneResultMessage(java.lang.String oneResultMessage) {
		engine.setProperty(Properties.ONE_RESULT_MESSAGE, oneResultMessage);
	}

	public java.lang.String getZeroResultMessage() {
		return getZeroResultMessage(null);
	}

	/**
	 * See {@link #getZeroResultMessage() getZeroResultMessage()} for more details
	 */
	public java.lang.String getZeroResultMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ZERO_RESULT_MESSAGE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "zeroResultMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isZeroResultMessageSetted() {
		return engine.isPropertySetted(Properties.ZERO_RESULT_MESSAGE);
	}

	public void setZeroResultMessage(java.lang.String zeroResultMessage) {
		engine.setProperty(Properties.ZERO_RESULT_MESSAGE, zeroResultMessage);
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
	 * Returns an int value specifying the delay in milliseconds before showing the suggestion list.
	 * @return delay in milliseconds
	 */
	public int getSuggestionDelayMs() {
		return getSuggestionDelayMs(null);
	}

	/**
	 * Returns an int value specifying the delay in milliseconds before showing the suggestion list.
	 * @return delay in milliseconds
	 */
	public int getSuggestionDelayMs(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.SUGGESTION_DELAY_MS, 0, facesContext);
	}

	/**
	 * Sets an int value specifying the delay in milliseconds before showing the suggestion list.
	 * @param suggestionDelayMs delay in milliseconds
	 */
	public void setSuggestionDelayMs(int suggestionDelayMs) {
		engine.setProperty(Properties.SUGGESTION_DELAY_MS, suggestionDelayMs);
	}

	/**
	 * Sets an int value specifying the delay in milliseconds before showing the suggestion list.
	 * @param suggestionDelayMs delay in milliseconds
	 */
	/**
	 * Returns <code>true</code> if the attribute "suggestionDelayMs" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSuggestionDelayMsSetted() {
		return engine.isPropertySetted(Properties.SUGGESTION_DELAY_MS);
	}

	/**
	 * Returns an int value specifying the minimum number of characters before the suggestion is calculated.
	 * @return minimum number of characters
	 */
	public int getSuggestionMinChars() {
		return getSuggestionMinChars(null);
	}

	/**
	 * Returns an int value specifying the minimum number of characters before the suggestion is calculated.
	 * @return minimum number of characters
	 */
	public int getSuggestionMinChars(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.SUGGESTION_MIN_CHARS, 0, facesContext);
	}

	/**
	 * Sets an int value specifying the minimum number of characters before the suggestion is calculated.
	 * @param suggestionMinChars minimum number of characters
	 */
	public void setSuggestionMinChars(int suggestionMinChars) {
		engine.setProperty(Properties.SUGGESTION_MIN_CHARS, suggestionMinChars);
	}

	/**
	 * Sets an int value specifying the minimum number of characters before the suggestion is calculated.
	 * @param suggestionMinChars minimum number of characters
	 */
	/**
	 * Returns <code>true</code> if the attribute "suggestionMinChars" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSuggestionMinCharsSetted() {
		return engine.isPropertySetted(Properties.SUGGESTION_MIN_CHARS);
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
		return engine.getStringProperty(Properties.ROW_COUNT_VAR, facesContext);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the number of rows.
	 * @param rowCountVar variable name
	 */
	public void setRowCountVar(String rowCountVar) {
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
	public boolean isRowCountVarSetted() {
		return engine.isPropertySetted(Properties.ROW_COUNT_VAR);
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
		return engine.getStringProperty(Properties.ROW_INDEX_VAR, facesContext);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the index of the current row.
	 * @param rowIndexVar variable name
	 */
	public void setRowIndexVar(String rowIndexVar) {
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
	public boolean isRowIndexVarSetted() {
		return engine.isPropertySetted(Properties.ROW_INDEX_VAR);
	}

	public String getValueColumnId() {
		return getValueColumnId(null);
	}

	public String getValueColumnId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.VALUE_COLUMN_ID, facesContext);
	}

	public void setValueColumnId(String valueColumnId) {
		engine.setProperty(Properties.VALUE_COLUMN_ID, valueColumnId);
	}

	/**
	 * Returns <code>true</code> if the attribute "valueColumnId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isValueColumnIdSetted() {
		return engine.isPropertySetted(Properties.VALUE_COLUMN_ID);
	}

	public String getLabelColumnId() {
		return getLabelColumnId(null);
	}

	public String getLabelColumnId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.LABEL_COLUMN_ID, facesContext);
	}

	public void setLabelColumnId(String labelColumnId) {
		engine.setProperty(Properties.LABEL_COLUMN_ID, labelColumnId);
	}

	/**
	 * Returns <code>true</code> if the attribute "labelColumnId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isLabelColumnIdSetted() {
		return engine.isPropertySetted(Properties.LABEL_COLUMN_ID);
	}

	public Object getSelectedValue() {
		return getSelectedValue(null);
	}

	public Object getSelectedValue(javax.faces.context.FacesContext facesContext) {
		return engine.getValue(Properties.SELECTED_VALUE, facesContext);
	}

	public void setSelectedValue(Object selectedValue) {
		engine.setValue(Properties.SELECTED_VALUE, selectedValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectedValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSelectedValueSetted() {
		return engine.isPropertySetted(Properties.SELECTED_VALUE);
	}

	public int getPopupWidth() {
		return getPopupWidth(null);
	}

	public int getPopupWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.POPUP_WIDTH, 0, facesContext);
	}

	public void setPopupWidth(int popupWidth) {
		engine.setProperty(Properties.POPUP_WIDTH, popupWidth);
	}

	/**
	 * Returns <code>true</code> if the attribute "popupWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPopupWidthSetted() {
		return engine.isPropertySetted(Properties.POPUP_WIDTH);
	}

	public int getPopupHeight() {
		return getPopupHeight(null);
	}

	public int getPopupHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.POPUP_HEIGHT, 0, facesContext);
	}

	public void setPopupHeight(int popupHeight) {
		engine.setProperty(Properties.POPUP_HEIGHT, popupHeight);
	}

	/**
	 * Returns <code>true</code> if the attribute "popupHeight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPopupHeightSetted() {
		return engine.isPropertySetted(Properties.POPUP_HEIGHT);
	}

	public String getValueFormat() {
		return getValueFormat(null);
	}

	public String getValueFormat(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.VALUE_FORMAT, facesContext);
	}

	public void setValueFormat(String valueFormat) {
		engine.setProperty(Properties.VALUE_FORMAT, valueFormat);
	}

	/**
	 * Returns <code>true</code> if the attribute "valueFormat" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isValueFormatSetted() {
		return engine.isPropertySetted(Properties.VALUE_FORMAT);
	}

	public String getPagerStyleClass() {
		return getPagerStyleClass(null);
	}

	public String getPagerStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.PAGER_STYLE_CLASS, facesContext);
	}

	public void setPagerStyleClass(String pagerStyleClass) {
		engine.setProperty(Properties.PAGER_STYLE_CLASS, pagerStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "pagerStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPagerStyleClassSetted() {
		return engine.isPropertySetted(Properties.PAGER_STYLE_CLASS);
	}

	public String getPagerLookId() {
		return getPagerLookId(null);
	}

	public String getPagerLookId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.PAGER_LOOK_ID, facesContext);
	}

	public void setPagerLookId(String pagerLookId) {
		engine.setProperty(Properties.PAGER_LOOK_ID, pagerLookId);
	}

	/**
	 * Returns <code>true</code> if the attribute "pagerLookId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPagerLookIdSetted() {
		return engine.isPropertySetted(Properties.PAGER_LOOK_ID);
	}

	public String getGridStyleClass() {
		return getGridStyleClass(null);
	}

	public String getGridStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.GRID_STYLE_CLASS, facesContext);
	}

	public void setGridStyleClass(String gridStyleClass) {
		engine.setProperty(Properties.GRID_STYLE_CLASS, gridStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "gridStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isGridStyleClassSetted() {
		return engine.isPropertySetted(Properties.GRID_STYLE_CLASS);
	}

	public String getGridLookId() {
		return getGridLookId(null);
	}

	public String getGridLookId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.GRID_LOOK_ID, facesContext);
	}

	public void setGridLookId(String gridLookId) {
		engine.setProperty(Properties.GRID_LOOK_ID, gridLookId);
	}

	/**
	 * Returns <code>true</code> if the attribute "gridLookId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isGridLookIdSetted() {
		return engine.isPropertySetted(Properties.GRID_LOOK_ID);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
