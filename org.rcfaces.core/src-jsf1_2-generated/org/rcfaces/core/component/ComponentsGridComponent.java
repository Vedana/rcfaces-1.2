package org.rcfaces.core.component;

import org.rcfaces.core.internal.tools.CollectionTools.IComponentValueType;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.ISelectableCapability;
import org.rcfaces.core.internal.capability.ISortedComponentsCapability;
import org.rcfaces.core.internal.tools.SelectionTools;
import org.rcfaces.core.component.capability.ILoadEventCapability;
import org.rcfaces.core.component.capability.IHeaderVisibilityCapability;
import org.rcfaces.core.internal.tools.CollectionTools.IComponentValueTypeCapability;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.ISelectionCardinalityCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.capability.IRowToolTipIdCapability;
import org.rcfaces.core.component.capability.ISortedChildrenCapability;
import org.rcfaces.core.internal.tools.AdditionalInformationTools
			;
import org.rcfaces.core.component.capability.IGridCaptionCapability;
import org.rcfaces.core.internal.capability.IAdditionalInformationRangeComponent;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.model.ISortedComponent;
import java.lang.Object;
import org.rcfaces.core.internal.tools.OrderTools;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.component.capability.IEmptyDataMessageCapability;
import org.rcfaces.core.component.capability.IClientSelectionFullStateCapability;
import org.rcfaces.core.component.AbstractDataComponent;
import org.rcfaces.core.component.capability.IShowValueCapability;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.capability.IPreferencesSettings;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IOrderedChildrenCapability;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.component.capability.IClientAdditionalInformationFullStateCapability;
import org.rcfaces.core.internal.capability.ISelectionRangeComponent;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.IPagedCapability;
import org.rcfaces.core.component.capability.ISelectedValuesCapability;
import org.rcfaces.core.internal.tools.GridTools;
import org.rcfaces.core.component.capability.IRowStyleClassCapability;
import org.rcfaces.core.internal.converter.SelectionCardinalityConverter;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.tools.SortTools;
import org.rcfaces.core.component.capability.IAdditionalInformationCardinalityCapability;
import java.util.Set;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.capability.IAdditionalInformationEventCapability;
import org.rcfaces.core.component.iterator.IAdditionalInformationIterator;
import org.rcfaces.core.component.capability.IScopeColumnIdCapability;
import java.lang.String;
import org.rcfaces.core.internal.converter.ClientFullStateConverter;
import org.rcfaces.core.component.capability.IAdditionalInformationValuesCapability;
import javax.faces.convert.Converter;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.rcfaces.core.internal.converter.AdditionalInformationCardinalityConverter;
import org.rcfaces.core.component.ComponentsColumnComponent;

/**
 * <p>The componentGrid Component is a grid component. It can be compared to the grid found in the list part of the modern file explorer. It allows sorts, resizing, contextual menus ...</p>
 * <p>The componentGrid Component is an evolution of <a href="/comps/dataGridComponent.html">dataGrid component</a>. This component is a component container, it allows to:
 * <ul>
 * <li>ISelectionEventCapability</li>
 * <li>ISelectableCapability</li>
 * <li>ISelectionCardinalityCapability</li>
 * <li>ISelectedValuesCapability</li>
 * <li>IClientSelectionFullStateCapability</li>
 * <li>IAdditionalInformationEventCapability</li>
 * <li>IAdditionalInformationValuesCapability</li>
 * <li>IClientAdditionalInformationFullStateCapability</li>
 * <li>IAdditionalInformationCardinalityCapability</li>
 * <li>IDoubleClickEventCapability</li>
 * <li>ILoadEventCapability</li>
 * <li>IRequiredCapability</li>
 * <li>IBorderCapability</li>
 * <li>IRowStyleClassCapability</li>
 * <li>IShowValueCapability</li>
 * <li>IEmptyDataMessageCapability</li>
 * <li>IMenuCapability</li>
 * <li>IScrollableCapability</li>
 * <li>IPreferencesSettings</li>
 * <li>IPagedCapability</li>
 * <li>IHeaderVisibilityCapability</li>
 * <li>IGridComponent</li>
 * <li>IOrderedChildrenCapability</li>
 * <li>ISortedChildrenCapability</li>
 * <li>IComponentValueTypeCapability</li>
 * <li>ISelectionRangeComponent</li>
 * <li>ISortedComponentsCapability</li>
 * <li>IAdditionalInformationRangeComponent</li>
 * </ul></p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/ComponentsGridComponent.html"> componentsGrid </a> renderer is link to the <a href="/jsdocs/index.html?f_componentsGrid.html" target="_blank">f_componentsGrid</a> javascript class. f_componentsGrid extends f_grid</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <th  width="33%">Style Name</th>
 * <th width="50%">Description</th>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_componentsGrid</td>
 * <td id="ermvsh" width="50%">Defines styles for the wrapper DIV element. Contains f_grid styles.</td>
 * </tr>
 * </tbody>
 * </table>
 */
public class ComponentsGridComponent extends AbstractDataComponent implements 
	ISelectionEventCapability,
	ISelectableCapability,
	ISelectionCardinalityCapability,
	ISelectedValuesCapability,
	IClientSelectionFullStateCapability,
	IAdditionalInformationEventCapability,
	IAdditionalInformationValuesCapability,
	IClientAdditionalInformationFullStateCapability,
	IAdditionalInformationCardinalityCapability,
	IDoubleClickEventCapability,
	ILoadEventCapability,
	IRequiredCapability,
	IBorderCapability,
	IRowStyleClassCapability,
	IShowValueCapability,
	IEmptyDataMessageCapability,
	IMenuCapability,
	IScrollableCapability,
	IPreferencesSettings,
	IPagedCapability,
	IRowToolTipIdCapability,
	IHeaderVisibilityCapability,
	IScopeColumnIdCapability,
	IGridCaptionCapability,
	IGridComponent,
	IOrderedChildrenCapability,
	ISortedChildrenCapability,
	IComponentValueTypeCapability,
	ISelectionRangeComponent,
	ISortedComponentsCapability,
	IAdditionalInformationRangeComponent {

	private static final Log LOG = LogFactory.getLog(ComponentsGridComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.componentsGrid";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractDataComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"headerVisible","summary","selectionCardinality","clientAdditionalInformationFullState","scopeColumnId","cellTextWrap","emptyDataMessage","loadListener","selectionListener","paged","additionalInformationValues","showValue","additionalInformationListener","verticalScrollPosition","required","border","clientSelectionFullState","preferences","doubleClickListener","caption","selectedValues","horizontalScrollPosition","rowCountVar","rowStyleClass","rowToolTipId","selectable","additionalInformationCardinality","rowValueConverter","rowIndexVar","rowValue"}));
	}

	public ComponentsGridComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ComponentsGridComponent(String componentId) {
		this();
		setId(componentId);
	}

	public UIComponent[] getSortedChildren() {


				return SortTools.getSortedChildren(null, this, engine,
				ComponentsColumnComponent.class);
			
	}

	public void setSortedChildren(UIComponent[] components) {


				SortTools.setSortedChildren(null, this, engine,
				ComponentsColumnComponent.class, components);
			
	}

	public void setOrderedChildren(UIComponent[] components) {


				OrderTools.setOrderedChildren(null, this, engine,
				ComponentsColumnComponent.class, components);
			
	}

	public UIComponent[] getOrderedChildren() {


				return OrderTools.getOrderedChildren(null, this, engine,
				ComponentsColumnComponent.class);
			
	}

	public IComponentValueType getComponentValueType() {


				return GridTools.COMPONENTS_GRID_VALUE_TYPE;
			
	}

	public IColumnIterator listColumns() {


				return GridTools.listColumns(this, ComponentsColumnComponent.class);
			
	}

	public IAdditionalInformationIterator listAdditionalInformations() {


				return AdditionalInformationTools.listAdditionalInformations(this);
			
	}

	public void setRowValueConverter(String converterId) {

			
			setRowValueConverter(converterId, null);
		
	}

	public void setRowValueConverter(String converterId, FacesContext facesContext) {


			Converter converter=ComponentTools.createConverter(facesContext, converterId);

			setRowValueConverter(converter);
		
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


				return SelectionTools.getFirst(getSelectedValues(),
				getValue());
			
	}

	public Object[] listSelectedValues() {


				return SelectionTools.listValues(getSelectedValues(),
				getValue());
			
	}

	public Object getSelectedValues(FacesContext facesContext) {

			
				if (engine.isPropertySetted(Properties.SELECTED_VALUES)) {
					return engine.getValue(Properties.SELECTED_VALUES, facesContext);
				}

				return SelectionTools.getAdaptedValues(getValue(), false);
			
	}

	public void setSelectedValues(Object selectedValues) {


				if (engine.isPropertySetted(Properties.SELECTED_VALUES)==false) {
					if (SelectionTools.setAdaptedValues(getValue(), selectedValues)) {
						return;
					}
				}
								
				engine.setValue(Properties.SELECTED_VALUES, selectedValues);
			
	}

	public void setAdditionalInformationCardinality(String cardinality) {


			setAdditionalInformationCardinality(((Integer)AdditionalInformationCardinalityConverter.SINGLETON.getAsObject(null, this, cardinality)).intValue());
		
	}

	public void setClientAdditionalInformationFullState(String state) {


			setClientAdditionalInformationFullState(((Integer)ClientFullStateConverter.SINGLETON.getAsObject(null, this, state)).intValue());
		
	}

	public int getAdditionalInformationValuesCount() {


				return
				AdditionalInformationTools.getCount(getAdditionalInformationValues());
			
	}

	public Object getFirstAdditionalInformationValue() {


				return
				AdditionalInformationTools.getFirst(getAdditionalInformationValues(),
				null);
			
	}

	public Object getAdditionalInformationValues(FacesContext facesContext) {


				return
				engine.getValue(Properties.ADDITIONAL_INFORMATION_VALUES,
				facesContext);
			
	}

	public Object[] listAdditionalInformationValues() {


				return
				AdditionalInformationTools.listValues(getAdditionalInformationValues(),
				getValue());
			
	}

	public ComponentsColumnComponent[] getSortedColumns() {


				return (ComponentsColumnComponent[])getSortedChildren();
			
	}

	public ComponentsColumnComponent getFirstSortedColumn() {


				return
				(ComponentsColumnComponent)SortTools.getFirstSortedChild(null, this,
				engine, ComponentsColumnComponent.class );
			
	}

	public void setSortedColumn(ComponentsColumnComponent componentsColumn) {


				SortTools.setSortedChildren(null, this, engine,
				ComponentsColumnComponent.class, new ComponentsColumnComponent[] {
				componentsColumn });
			
	}

	public void setSortedColumns(ComponentsColumnComponent[] componentsColumns) {


				setSortedChildren(componentsColumns);
			
	}

	public ISortedComponent[] listSortedComponents() {


				return listSortedComponents(null);
			
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

	public java.lang.String getEmptyDataMessage() {
		return getEmptyDataMessage(null);
	}

	/**
	 * See {@link #getEmptyDataMessage() getEmptyDataMessage()} for more details
	 */
	public java.lang.String getEmptyDataMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.EMPTY_DATA_MESSAGE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "emptyDataMessage" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isEmptyDataMessageSetted() {
		return engine.isPropertySetted(Properties.EMPTY_DATA_MESSAGE);
	}

	public void setEmptyDataMessage(java.lang.String emptyDataMessage) {
		engine.setProperty(Properties.EMPTY_DATA_MESSAGE, emptyDataMessage);
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

	public org.rcfaces.core.preference.IComponentPreferences getPreferences() {
		return getPreferences(null);
	}

	/**
	 * See {@link #getPreferences() getPreferences()} for more details
	 */
	public org.rcfaces.core.preference.IComponentPreferences getPreferences(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.preference.IComponentPreferences)engine.getProperty(Properties.PREFERENCES, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "preferences" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isPreferencesSetted() {
		return engine.isPropertySetted(Properties.PREFERENCES);
	}

	public void setPreferences(org.rcfaces.core.preference.IComponentPreferences preferences) {
		engine.setProperty(Properties.PREFERENCES, preferences);
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

	public java.lang.String getRowToolTipId() {
		return getRowToolTipId(null);
	}

	/**
	 * See {@link #getRowToolTipId() getRowToolTipId()} for more details
	 */
	public java.lang.String getRowToolTipId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ROW_TOOL_TIP_ID, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowToolTipId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRowToolTipIdSetted() {
		return engine.isPropertySetted(Properties.ROW_TOOL_TIP_ID);
	}

	public void setRowToolTipId(java.lang.String rowToolTipId) {
		engine.setProperty(Properties.ROW_TOOL_TIP_ID, rowToolTipId);
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

	public java.lang.String getScopeColumnId() {
		return getScopeColumnId(null);
	}

	/**
	 * See {@link #getScopeColumnId() getScopeColumnId()} for more details
	 */
	public java.lang.String getScopeColumnId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SCOPE_COLUMN_ID, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "scopeColumnId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isScopeColumnIdSetted() {
		return engine.isPropertySetted(Properties.SCOPE_COLUMN_ID);
	}

	public void setScopeColumnId(java.lang.String scopeColumnId) {
		engine.setProperty(Properties.SCOPE_COLUMN_ID, scopeColumnId);
	}

	public java.lang.String getSummary() {
		return getSummary(null);
	}

	/**
	 * See {@link #getSummary() getSummary()} for more details
	 */
	public java.lang.String getSummary(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SUMMARY, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "summary" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSummarySetted() {
		return engine.isPropertySetted(Properties.SUMMARY);
	}

	public void setSummary(java.lang.String summary) {
		engine.setProperty(Properties.SUMMARY, summary);
	}

	public java.lang.String getCaption() {
		return getCaption(null);
	}

	/**
	 * See {@link #getCaption() getCaption()} for more details
	 */
	public java.lang.String getCaption(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CAPTION, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "caption" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCaptionSetted() {
		return engine.isPropertySetted(Properties.CAPTION);
	}

	public void setCaption(java.lang.String caption) {
		engine.setProperty(Properties.CAPTION, caption);
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
		String s = engine.getStringProperty(Properties.ROW_COUNT_VAR, facesContext);
		return s;
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
		String s = engine.getStringProperty(Properties.ROW_INDEX_VAR, facesContext);
		return s;
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

	public String getRowValue() {
		return getRowValue(null);
	}

	public String getRowValue(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.ROW_VALUE, facesContext);
		return s;
	}

	public void setRowValue(String rowValue) {
		engine.setProperty(Properties.ROW_VALUE, rowValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowValueSetted() {
		return engine.isPropertySetted(Properties.ROW_VALUE);
	}

	public javax.faces.convert.Converter getRowValueConverter() {
		return getRowValueConverter(null);
	}

	public javax.faces.convert.Converter getRowValueConverter(javax.faces.context.FacesContext facesContext) {
		return (javax.faces.convert.Converter)engine.getValue(Properties.ROW_VALUE_CONVERTER, facesContext);
	}

	public void setRowValueConverter(javax.faces.convert.Converter rowValueConverter) {
		engine.setProperty(Properties.ROW_VALUE_CONVERTER, rowValueConverter);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowValueConverter" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowValueConverterSetted() {
		return engine.isPropertySetted(Properties.ROW_VALUE_CONVERTER);
	}

	public boolean isCellTextWrap() {
		return isCellTextWrap(null);
	}

	public boolean isCellTextWrap(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CELL_TEXT_WRAP, false, facesContext);
	}

	public void setCellTextWrap(boolean cellTextWrap) {
		engine.setProperty(Properties.CELL_TEXT_WRAP, cellTextWrap);
	}

	/**
	 * Returns <code>true</code> if the attribute "cellTextWrap" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isCellTextWrapSetted() {
		return engine.isPropertySetted(Properties.CELL_TEXT_WRAP);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
