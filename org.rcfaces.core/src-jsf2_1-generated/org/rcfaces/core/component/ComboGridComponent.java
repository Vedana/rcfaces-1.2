package org.rcfaces.core.component;

import org.rcfaces.core.internal.tools.CollectionTools.IComponentValueType;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IClientAdditionalInformationFullStateCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IHeaderVisibilityCapability;
import org.rcfaces.core.internal.tools.CollectionTools.IComponentValueTypeCapability;
import org.rcfaces.core.component.capability.IPagedCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.GridTools;
import org.rcfaces.core.component.ComboColumnComponent;
import org.rcfaces.core.component.capability.IRowStyleClassCapability;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.tools.SortTools;
import org.rcfaces.core.component.capability.ISortedChildrenCapability;
import org.rcfaces.core.component.capability.IAdditionalInformationCardinalityCapability;
import java.util.Set;
import org.rcfaces.core.internal.capability.IAdditionalInformationRangeComponent;
import java.util.Collection;
import org.rcfaces.core.component.capability.IAdditionalInformationEventCapability;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.core.internal.tools.AdditionalInformationTools;
import org.rcfaces.core.component.iterator.IAdditionalInformationIterator;
import org.rcfaces.core.internal.tools.OrderTools;
import org.rcfaces.core.component.KeyEntryComponent;
import java.lang.String;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.internal.converter.ClientFullStateConverter;
import org.rcfaces.core.component.capability.IAdditionalInformationValuesCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IFilterCapability;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IOrderedChildrenCapability;
import org.rcfaces.core.internal.tools.CollectionTools;
import org.rcfaces.core.component.capability.IPagerMessageCapability;

public class ComboGridComponent extends KeyEntryComponent implements 
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
	ISortedChildrenCapability,
	IComponentValueTypeCapability,
	IAdditionalInformationRangeComponent {

	private static final Log LOG = LogFactory.getLog(ComboGridComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.comboGrid";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=KeyEntryComponent.BEHAVIOR_EVENT_NAMES;

	public ComboGridComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ComboGridComponent(String componentId) {
		this();
		setId(componentId);
	}

	public UIComponent[] getSortedChildren() {


				return SortTools.getSortedChildren(null, this, getComponentEngine(), ComboColumnComponent.class);
			
	}

	public void setSortedChildren(UIComponent[] components) {


				SortTools.setSortedChildren(null, this, getComponentEngine(), ComboColumnComponent.class, components);
			
	}

	public void setOrderedChildren(UIComponent[] components) {


				OrderTools.setOrderedChildren(null, this, getComponentEngine(), ComboColumnComponent.class, components);
			
	}

	public UIComponent[] getOrderedChildren() {


				return OrderTools.getOrderedChildren(null, this, getComponentEngine(), ComboColumnComponent.class);
			
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


				return getStateHelper().eval(Properties.ADDITIONAL_INFORMATION_VALUES, facesContext);
			
	}

	public Object[] listAdditionalInformationValues() {


				return AdditionalInformationTools.listValues(getAdditionalInformationValues(), getValue());
			
	}

	public ComboColumnComponent[] getSortedColumns() {


				return (ComboColumnComponent[])getSortedChildren();
			
	}

	public ComboColumnComponent getFirstSortedColumn() {


				return (ComboColumnComponent)SortTools.getFirstSortedChild(null, this, getComponentEngine(), ComboColumnComponent.class );
			
	}

	public void setSortedColumn(ComboColumnComponent comboColumn) {


				SortTools.setSortedChildren(null, this, getComponentEngine(), ComboColumnComponent.class, new ComboColumnComponent[] { comboColumn });
			
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

	public java.lang.String getManyResultsMessage() {
		return getManyResultsMessage(null);
	}

	/**
	 * See {@link #getManyResultsMessage() getManyResultsMessage()} for more details
	 */
	public java.lang.String getManyResultsMessage(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.MANY_RESULTS_MESSAGE);
	}

	/**
	 * Returns <code>true</code> if the attribute "manyResultsMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isManyResultsMessageSetted() {
		return getStateHelper().get(Properties.MANY_RESULTS_MESSAGE)!=null;
	}

	public void setManyResultsMessage(java.lang.String manyResultsMessage) {
		getStateHelper().put(Properties.MANY_RESULTS_MESSAGE, manyResultsMessage);
	}

	public java.lang.String getMessage() {
		return getMessage(null);
	}

	/**
	 * See {@link #getMessage() getMessage()} for more details
	 */
	public java.lang.String getMessage(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.MESSAGE);
	}

	/**
	 * Returns <code>true</code> if the attribute "message" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMessageSetted() {
		return getStateHelper().get(Properties.MESSAGE)!=null;
	}

	public void setMessage(java.lang.String message) {
		getStateHelper().put(Properties.MESSAGE, message);
	}

	public java.lang.String getOneResultMessage() {
		return getOneResultMessage(null);
	}

	/**
	 * See {@link #getOneResultMessage() getOneResultMessage()} for more details
	 */
	public java.lang.String getOneResultMessage(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ONE_RESULT_MESSAGE);
	}

	/**
	 * Returns <code>true</code> if the attribute "oneResultMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isOneResultMessageSetted() {
		return getStateHelper().get(Properties.ONE_RESULT_MESSAGE)!=null;
	}

	public void setOneResultMessage(java.lang.String oneResultMessage) {
		getStateHelper().put(Properties.ONE_RESULT_MESSAGE, oneResultMessage);
	}

	public java.lang.String getZeroResultMessage() {
		return getZeroResultMessage(null);
	}

	/**
	 * See {@link #getZeroResultMessage() getZeroResultMessage()} for more details
	 */
	public java.lang.String getZeroResultMessage(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ZERO_RESULT_MESSAGE);
	}

	/**
	 * Returns <code>true</code> if the attribute "zeroResultMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isZeroResultMessageSetted() {
		return getStateHelper().get(Properties.ZERO_RESULT_MESSAGE)!=null;
	}

	public void setZeroResultMessage(java.lang.String zeroResultMessage) {
		getStateHelper().put(Properties.ZERO_RESULT_MESSAGE, zeroResultMessage);
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

	public int getPopupWidth() {
		return getPopupWidth(null);
	}

	public int getPopupWidth(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.POPUP_WIDTH, 0);
	}

	public void setPopupWidth(int popupWidth) {
		 getStateHelper().put(Properties.POPUP_WIDTH, popupWidth);
	}

	/**
	 * Returns <code>true</code> if the attribute "popupWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPopupWidthSetted() {
		return getStateHelper().get(Properties.POPUP_WIDTH)!=null;
	}

	public int getPopupHeight() {
		return getPopupHeight(null);
	}

	public int getPopupHeight(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.POPUP_HEIGHT, 0);
	}

	public void setPopupHeight(int popupHeight) {
		 getStateHelper().put(Properties.POPUP_HEIGHT, popupHeight);
	}

	/**
	 * Returns <code>true</code> if the attribute "popupHeight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPopupHeightSetted() {
		return getStateHelper().get(Properties.POPUP_HEIGHT)!=null;
	}

	public String getPagerStyleClass() {
		return getPagerStyleClass(null);
	}

	public String getPagerStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.PAGER_STYLE_CLASS);
	}

	public void setPagerStyleClass(String pagerStyleClass) {
		 getStateHelper().put(Properties.PAGER_STYLE_CLASS, pagerStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "pagerStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPagerStyleClassSetted() {
		return getStateHelper().get(Properties.PAGER_STYLE_CLASS)!=null;
	}

	public String getPagerLookId() {
		return getPagerLookId(null);
	}

	public String getPagerLookId(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.PAGER_LOOK_ID);
	}

	public void setPagerLookId(String pagerLookId) {
		 getStateHelper().put(Properties.PAGER_LOOK_ID, pagerLookId);
	}

	/**
	 * Returns <code>true</code> if the attribute "pagerLookId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPagerLookIdSetted() {
		return getStateHelper().get(Properties.PAGER_LOOK_ID)!=null;
	}

	public String getPopupStyleClass() {
		return getPopupStyleClass(null);
	}

	public String getPopupStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.POPUP_STYLE_CLASS);
	}

	public void setPopupStyleClass(String popupStyleClass) {
		 getStateHelper().put(Properties.POPUP_STYLE_CLASS, popupStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "popupStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isPopupStyleClassSetted() {
		return getStateHelper().get(Properties.POPUP_STYLE_CLASS)!=null;
	}

	public String getGridStyleClass() {
		return getGridStyleClass(null);
	}

	public String getGridStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.GRID_STYLE_CLASS);
	}

	public void setGridStyleClass(String gridStyleClass) {
		 getStateHelper().put(Properties.GRID_STYLE_CLASS, gridStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "gridStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isGridStyleClassSetted() {
		return getStateHelper().get(Properties.GRID_STYLE_CLASS)!=null;
	}

	public String getGridLookId() {
		return getGridLookId(null);
	}

	public String getGridLookId(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.GRID_LOOK_ID);
	}

	public void setGridLookId(String gridLookId) {
		 getStateHelper().put(Properties.GRID_LOOK_ID, gridLookId);
	}

	/**
	 * Returns <code>true</code> if the attribute "gridLookId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isGridLookIdSetted() {
		return getStateHelper().get(Properties.GRID_LOOK_ID)!=null;
	}

	public boolean isSearchFieldVisible() {
		return isSearchFieldVisible(null);
	}

	public boolean isSearchFieldVisible(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.SEARCH_FIELD_VISIBLE, true);
	}

	public void setSearchFieldVisible(boolean searchFieldVisible) {
		 getStateHelper().put(Properties.SEARCH_FIELD_VISIBLE, searchFieldVisible);
	}

	/**
	 * Returns <code>true</code> if the attribute "searchFieldVisible" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSearchFieldVisibleSetted() {
		return getStateHelper().get(Properties.SEARCH_FIELD_VISIBLE)!=null;
	}

}
