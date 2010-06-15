package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IAdditionalInformationCardinalityCapability;
import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import org.rcfaces.core.internal.tools.SortTools;
import org.rcfaces.core.internal.tools.GridTools;
import org.rcfaces.core.internal.capability.IAdditionalInformationRangeComponent;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.component.capability.IPagerMessageCapability;
import org.rcfaces.core.component.KeyEntryComponent;
import org.rcfaces.core.component.capability.IClientAdditionalInformationFullStateCapability;
import org.rcfaces.core.component.capability.IRowStyleClassCapability;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.internal.tools.OrderTools;
import org.rcfaces.core.internal.tools.AdditionalInformationTools;
import org.rcfaces.core.internal.tools.CollectionTools;
import java.lang.String;
import org.rcfaces.core.component.capability.IAdditionalInformationEventCapability;
import org.rcfaces.core.internal.converter.ClientFullStateConverter;
import javax.el.ValueExpression;
import org.rcfaces.core.internal.tools.CollectionTools.IComponentValueTypeCapability;
import org.rcfaces.core.component.capability.ISortedChildrenCapability;
import org.rcfaces.core.component.capability.IAdditionalInformationValuesCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IOrderedChildrenCapability;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.tools.CollectionTools.IComponentValueType;
import org.rcfaces.core.component.capability.IHeaderVisibilityCapability;
import org.rcfaces.core.component.iterator.IAdditionalInformationIterator;
import org.apache.commons.logging.Log;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.core.component.capability.IPagedCapability;
import org.rcfaces.core.component.ComboColumnComponent;

public class ComboGridComponent extends KeyEntryComponent implements 
	ISelectionEventCapability,
	IAdditionalInformationEventCapability,
	IAdditionalInformationValuesCapability,
	IClientAdditionalInformationFullStateCapability,
	IAdditionalInformationCardinalityCapability,
	IRowStyleClassCapability,
	IPagerMessageCapability,
	IFilterCapability,
	IPagedCapability,
	IHeaderVisibilityCapability,
	IOrderedChildrenCapability,
	IAdditionalInformationRangeComponent,
	IComponentValueTypeCapability,
	ISortedChildrenCapability {

	private static final Log LOG = LogFactory.getLog(ComboGridComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.comboGrid";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(KeyEntryComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"popupHeight","selectionListener","paged","searchFieldVisible","pagerStyleClass","clientAdditionalInformationFullState","message","additionalInformationCardinality","rowStyleClass","headerVisible","additionalInformationValues","pagerLookId","manyResultsMessage","oneResultMessage","filterProperties","popupWidth","zeroResultMessage","additionalInformationListener","gridStyleClass","popupStyleClass","gridLookId"}));
	}

	public ComboGridComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ComboGridComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setSortedChildren(UIComponent[] components) {


				SortTools.setSortedChildren(null, this, engine, ComboColumnComponent.class, components);
			
	}

	public UIComponent[] getSortedChildren() {


				return SortTools.getSortedChildren(null, this, engine, ComboColumnComponent.class);
			
	}

	public UIComponent[] getOrderedChildren() {


				return OrderTools.getOrderedChildren(null, this, engine, ComboColumnComponent.class);
			
	}

	public void setOrderedChildren(UIComponent[] components) {


				OrderTools.setOrderedChildren(null, this, engine, ComboColumnComponent.class, components);
			
	}

	public IComponentValueType getComponentValueType() {


				return GridTools.COMBO_GRID_VALUE_TYPE;
			
	}

	public IColumnIterator listColumns() {


			return GridTools.listColumns(this, ComboColumnComponent.class);
			
	}

	public IAdditionalInformationIterator listAdditionalInformations() {


			return AdditionalInformationTools.listAdditionalInformations(this);
			
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


				return engine.getValue(Properties.ADDITIONAL_INFORMATION_VALUES, facesContext);
			
	}

	public Object[] listAdditionalInformationValues() {


				return AdditionalInformationTools.listValues(getAdditionalInformationValues(), getValue());
			
	}

	public ComboColumnComponent[] getSortedColumns() {


				return (ComboColumnComponent[])getSortedChildren();
			
	}

	public ComboColumnComponent getFirstSortedColumn() {


				return (ComboColumnComponent)SortTools.getFirstSortedChild(null, this, engine, ComboColumnComponent.class );
			
	}

	public void setSortedColumn(ComboColumnComponent comboColumn) {


				SortTools.setSortedChildren(null, this, engine, ComboColumnComponent.class, new ComboColumnComponent[] { comboColumn });
			
	}

	public void setSortedColumns(ComboColumnComponent[] componentsColumns) {


				setSortedChildren(componentsColumns);
			
	}

	public ISortedComponent[] listSortedComponents() {


				return listSortedComponents(null);
			
	}

	public ISortedComponent[] listSortedComponents(FacesContext context) {


				return GridTools.listSortedComponents(context, this);
			
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

	public final void addSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
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
		return engine.isPropertySetted(Properties.ADDITIONAL_INFORMATION_VALUES);
	}

	public void setAdditionalInformationValues(java.lang.Object additionalInformationValues) {
		engine.setProperty(Properties.ADDITIONAL_INFORMATION_VALUES, additionalInformationValues);
	}

	/**
	 * Return the type of the property represented by the {@link ValueExpression}, relative to the specified {@link javax.faces.context.FacesContext}.
	 */
	public Class getAdditionalInformationValuesType(javax.faces.context.FacesContext facesContext) {
		ValueExpression valueExpression=engine.getValueExpressionProperty(Properties.ADDITIONAL_INFORMATION_VALUES);
		if (valueExpression==null) {
			return null;
		}
		if (facesContext==null) {
			facesContext=javax.faces.context.FacesContext.getCurrentInstance();
		}
		return valueExpression.getType(facesContext.getELContext());
	}

	public int getClientAdditionalInformationFullState() {
		return getClientAdditionalInformationFullState(null);
	}

	/**
	 * See {@link #getClientAdditionalInformationFullState() getClientAdditionalInformationFullState()} for more details
	 */
	public int getClientAdditionalInformationFullState(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.CLIENT_ADDITIONAL_INFORMATION_FULL_STATE,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "clientAdditionalInformationFullState" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isClientAdditionalInformationFullStateSetted() {
		return engine.isPropertySetted(Properties.CLIENT_ADDITIONAL_INFORMATION_FULL_STATE);
	}

	public void setClientAdditionalInformationFullState(int clientAdditionalInformationFullState) {
		engine.setProperty(Properties.CLIENT_ADDITIONAL_INFORMATION_FULL_STATE, clientAdditionalInformationFullState);
	}

	public int getAdditionalInformationCardinality() {
		return getAdditionalInformationCardinality(null);
	}

	/**
	 * See {@link #getAdditionalInformationCardinality() getAdditionalInformationCardinality()} for more details
	 */
	public int getAdditionalInformationCardinality(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.ADDITIONAL_INFORMATION_CARDINALITY,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "additionalInformationCardinality" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAdditionalInformationCardinalitySetted() {
		return engine.isPropertySetted(Properties.ADDITIONAL_INFORMATION_CARDINALITY);
	}

	public void setAdditionalInformationCardinality(int additionalInformationCardinality) {
		engine.setProperty(Properties.ADDITIONAL_INFORMATION_CARDINALITY, additionalInformationCardinality);
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

	public boolean isPaged() {
		return isPaged(null);
	}

	/**
	 * See {@link #isPaged() isPaged()} for more details
	 */
	public boolean isPaged(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.PAGED, false, facesContext);
	}

	public boolean isPagedSetted() {


			return engine.isPropertySetted(Properties.PAGED);
		
	}

	public void setPaged(boolean paged) {
		engine.setProperty(Properties.PAGED, paged);
	}

	public boolean isHeaderVisible() {
		return isHeaderVisible(null);
	}

	/**
	 * See {@link #isHeaderVisible() isHeaderVisible()} for more details
	 */
	public boolean isHeaderVisible(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.HEADER_VISIBLE, true, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "headerVisible" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHeaderVisibleSetted() {
		return engine.isPropertySetted(Properties.HEADER_VISIBLE);
	}

	public void setHeaderVisible(boolean headerVisible) {
		engine.setProperty(Properties.HEADER_VISIBLE, headerVisible);
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

	public String getPopupStyleClass() {
		return getPopupStyleClass(null);
	}

	public String getPopupStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.POPUP_STYLE_CLASS, facesContext);
	}

	public void setPopupStyleClass(String popupStyleClass) {
		engine.setProperty(Properties.POPUP_STYLE_CLASS, popupStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "popupStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPopupStyleClassSetted() {
		return engine.isPropertySetted(Properties.POPUP_STYLE_CLASS);
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

	public boolean isSearchFieldVisible() {
		return isSearchFieldVisible(null);
	}

	public boolean isSearchFieldVisible(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.SEARCH_FIELD_VISIBLE, true, facesContext);
	}

	public void setSearchFieldVisible(boolean searchFieldVisible) {
		engine.setProperty(Properties.SEARCH_FIELD_VISIBLE, searchFieldVisible);
	}

	/**
	 * Returns <code>true</code> if the attribute "searchFieldVisible" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSearchFieldVisibleSetted() {
		return engine.isPropertySetted(Properties.SEARCH_FIELD_VISIBLE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
