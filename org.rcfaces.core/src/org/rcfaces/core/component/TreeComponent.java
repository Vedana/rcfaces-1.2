package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.ICheckCardinalityCapability;
import org.rcfaces.core.component.capability.ISelectableCapability;
import org.rcfaces.core.component.capability.ICheckableCapability;
import org.rcfaces.core.internal.tools.TreeTools;
import org.rcfaces.core.internal.converter.CardinalityConverter;
import org.rcfaces.core.component.capability.ISelectionCardinalityCapability;
import org.rcfaces.core.component.capability.ICheckEventCapability;
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.internal.util.ComponentIterators;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.internal.tools.MenuTools;
import javax.faces.component.UISelectItem;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;

public class TreeComponent extends AbstractInputComponent implements 
	IDoubleClickEventCapability,
	IRequiredCapability,
	IScrollableCapability,
	IBorderCapability,
	IReadOnlyCapability,
	IMenuCapability,
	ICheckableCapability,
	ICheckCardinalityCapability,
	ICheckEventCapability,
	ISelectableCapability,
	ISelectionCardinalityCapability,
	ISelectionEventCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.tree";


	public TreeComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TreeComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setSelectionCardinality(String cardinality) {


			setSelectionCardinality(((Integer)CardinalityConverter.SINGLETON.getAsObject(null, null, cardinality)).intValue());
		
	}

	public final void setCheckCardinality(String cardinality) {


			setCheckCardinality(((Integer)CardinalityConverter.SINGLETON.getAsObject(null, null, cardinality)).intValue());
		
	}

	public final void removeAllTreeNodes() {


			ComponentIterators.removeAll(this, UISelectItem.class);
			
	}

	public final void collapseAll() {


				collapseAll(null);
			
	}

	public final void collapseAll(FacesContext context) {


				TreeTools.collapseAll(context, this);
			
	}

	public final void expandAll() {


				expandAll(null);
			
	}

	public final void expandAll(FacesContext context) {


				TreeTools.expandAll(context, this);
			
	}

	public final void setExpanded(FacesContext context, Object value, boolean expanded) {


				TreeTools.setExpanded(context, this, value, expanded);
			
	}

	public final boolean isExpanded(FacesContext context, Object value) {


				return TreeTools.isExpanded(context, this, value);
			
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

	public final IMenuComponent getMenu(String menuId) {


		return MenuTools.getMenu(this, menuId);
		
	}

	public final IMenuComponent getMenu() {


		return MenuTools.getMenu(this);
		
	}

	public final IMenuIterator listMenus() {


		return MenuTools.listMenus(this);
		
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

	public final void addCheckListener(org.rcfaces.core.event.ICheckListener listener) {
		addFacesListener(listener);
	}

	public final void removeCheckListener(org.rcfaces.core.event.ICheckListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listCheckListeners() {
		return getFacesListeners(org.rcfaces.core.event.ICheckListener.class);
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

	public final void addSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
	}

	public final String getDefaultImageURL() {
		return getDefaultImageURL(null);
	}

	public final String getDefaultImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_IMAGE_URL, facesContext);
	}

	public final void setDefaultImageURL(String defaultImageURL) {
		engine.setProperty(Properties.DEFAULT_IMAGE_URL, defaultImageURL);
	}

	public final void setDefaultImageURL(ValueBinding defaultImageURL) {
		engine.setProperty(Properties.DEFAULT_IMAGE_URL, defaultImageURL);
	}

	public final boolean isDefaultImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_IMAGE_URL);
	}

	public final String getDefaultSelectedImageURL() {
		return getDefaultSelectedImageURL(null);
	}

	public final String getDefaultSelectedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_SELECTED_IMAGE_URL, facesContext);
	}

	public final void setDefaultSelectedImageURL(String defaultSelectedImageURL) {
		engine.setProperty(Properties.DEFAULT_SELECTED_IMAGE_URL, defaultSelectedImageURL);
	}

	public final void setDefaultSelectedImageURL(ValueBinding defaultSelectedImageURL) {
		engine.setProperty(Properties.DEFAULT_SELECTED_IMAGE_URL, defaultSelectedImageURL);
	}

	public final boolean isDefaultSelectedImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_SELECTED_IMAGE_URL);
	}

	public final String getDefaultExpandedImageURL() {
		return getDefaultExpandedImageURL(null);
	}

	public final String getDefaultExpandedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_EXPANDED_IMAGE_URL, facesContext);
	}

	public final void setDefaultExpandedImageURL(String defaultExpandedImageURL) {
		engine.setProperty(Properties.DEFAULT_EXPANDED_IMAGE_URL, defaultExpandedImageURL);
	}

	public final void setDefaultExpandedImageURL(ValueBinding defaultExpandedImageURL) {
		engine.setProperty(Properties.DEFAULT_EXPANDED_IMAGE_URL, defaultExpandedImageURL);
	}

	public final boolean isDefaultExpandedImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_EXPANDED_IMAGE_URL);
	}

	public final String getDefaultDisabledImageURL() {
		return getDefaultDisabledImageURL(null);
	}

	public final String getDefaultDisabledImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_DISABLED_IMAGE_URL, facesContext);
	}

	public final void setDefaultDisabledImageURL(String defaultDisabledImageURL) {
		engine.setProperty(Properties.DEFAULT_DISABLED_IMAGE_URL, defaultDisabledImageURL);
	}

	public final void setDefaultDisabledImageURL(ValueBinding defaultDisabledImageURL) {
		engine.setProperty(Properties.DEFAULT_DISABLED_IMAGE_URL, defaultDisabledImageURL);
	}

	public final boolean isDefaultDisabledImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_DISABLED_IMAGE_URL);
	}

	public final String getDefaultLeafImageURL() {
		return getDefaultLeafImageURL(null);
	}

	public final String getDefaultLeafImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_LEAF_IMAGE_URL, facesContext);
	}

	public final void setDefaultLeafImageURL(String defaultLeafImageURL) {
		engine.setProperty(Properties.DEFAULT_LEAF_IMAGE_URL, defaultLeafImageURL);
	}

	public final void setDefaultLeafImageURL(ValueBinding defaultLeafImageURL) {
		engine.setProperty(Properties.DEFAULT_LEAF_IMAGE_URL, defaultLeafImageURL);
	}

	public final boolean isDefaultLeafImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_LEAF_IMAGE_URL);
	}

	public final String getDefaultSelectedLeafImageURL() {
		return getDefaultSelectedLeafImageURL(null);
	}

	public final String getDefaultSelectedLeafImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_SELECTED_LEAF_IMAGE_URL, facesContext);
	}

	public final void setDefaultSelectedLeafImageURL(String defaultSelectedLeafImageURL) {
		engine.setProperty(Properties.DEFAULT_SELECTED_LEAF_IMAGE_URL, defaultSelectedLeafImageURL);
	}

	public final void setDefaultSelectedLeafImageURL(ValueBinding defaultSelectedLeafImageURL) {
		engine.setProperty(Properties.DEFAULT_SELECTED_LEAF_IMAGE_URL, defaultSelectedLeafImageURL);
	}

	public final boolean isDefaultSelectedLeafImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_SELECTED_LEAF_IMAGE_URL);
	}

	public final String getDefaultExpandedLeafImageURL() {
		return getDefaultExpandedLeafImageURL(null);
	}

	public final String getDefaultExpandedLeafImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_EXPANDED_LEAF_IMAGE_URL, facesContext);
	}

	public final void setDefaultExpandedLeafImageURL(String defaultExpandedLeafImageURL) {
		engine.setProperty(Properties.DEFAULT_EXPANDED_LEAF_IMAGE_URL, defaultExpandedLeafImageURL);
	}

	public final void setDefaultExpandedLeafImageURL(ValueBinding defaultExpandedLeafImageURL) {
		engine.setProperty(Properties.DEFAULT_EXPANDED_LEAF_IMAGE_URL, defaultExpandedLeafImageURL);
	}

	public final boolean isDefaultExpandedLeafImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_EXPANDED_LEAF_IMAGE_URL);
	}

	public final String getDefaultDisabledLeafImageURL() {
		return getDefaultDisabledLeafImageURL(null);
	}

	public final String getDefaultDisabledLeafImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_DISABLED_LEAF_IMAGE_URL, facesContext);
	}

	public final void setDefaultDisabledLeafImageURL(String defaultDisabledLeafImageURL) {
		engine.setProperty(Properties.DEFAULT_DISABLED_LEAF_IMAGE_URL, defaultDisabledLeafImageURL);
	}

	public final void setDefaultDisabledLeafImageURL(ValueBinding defaultDisabledLeafImageURL) {
		engine.setProperty(Properties.DEFAULT_DISABLED_LEAF_IMAGE_URL, defaultDisabledLeafImageURL);
	}

	public final boolean isDefaultDisabledLeafImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_DISABLED_LEAF_IMAGE_URL);
	}

	public final boolean isUserExpandable() {
		return isUserExpandable(null);
	}

	public final boolean isUserExpandable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.USER_EXPANDABLE, true, facesContext);
	}

	public final void setUserExpandable(boolean userExpandable) {
		engine.setProperty(Properties.USER_EXPANDABLE, userExpandable);
	}

	public final void setUserExpandable(ValueBinding userExpandable) {
		engine.setProperty(Properties.USER_EXPANDABLE, userExpandable);
	}

	public final boolean isUserExpandableSetted() {
		return engine.isPropertySetted(Properties.USER_EXPANDABLE);
	}

	public final boolean isHideRootExpandSign() {
		return isHideRootExpandSign(null);
	}

	public final boolean isHideRootExpandSign(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.HIDE_ROOT_EXPAND_SIGN, false, facesContext);
	}

	public final void setHideRootExpandSign(boolean hideRootExpandSign) {
		engine.setProperty(Properties.HIDE_ROOT_EXPAND_SIGN, hideRootExpandSign);
	}

	public final void setHideRootExpandSign(ValueBinding hideRootExpandSign) {
		engine.setProperty(Properties.HIDE_ROOT_EXPAND_SIGN, hideRootExpandSign);
	}

	public final boolean isHideRootExpandSignSetted() {
		return engine.isPropertySetted(Properties.HIDE_ROOT_EXPAND_SIGN);
	}

	public final int getPreloadedLevelDepth() {
		return getPreloadedLevelDepth(null);
	}

	public final int getPreloadedLevelDepth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.PRELOADED_LEVEL_DEPTH, 0, facesContext);
	}

	public final void setPreloadedLevelDepth(int preloadedLevelDepth) {
		engine.setProperty(Properties.PRELOADED_LEVEL_DEPTH, preloadedLevelDepth);
	}

	public final void setPreloadedLevelDepth(ValueBinding preloadedLevelDepth) {
		engine.setProperty(Properties.PRELOADED_LEVEL_DEPTH, preloadedLevelDepth);
	}

	public final boolean isPreloadedLevelDepthSetted() {
		return engine.isPropertySetted(Properties.PRELOADED_LEVEL_DEPTH);
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

	public final Object[] getExpansionValues() {
		return getExpansionValues(null);
	}

	public final Object[] getExpansionValues(javax.faces.context.FacesContext facesContext) {
		return (Object[])engine.getValue(Properties.EXPANSION_VALUES, facesContext);
	}

	public final void setExpansionValues(Object[] expansionValues) {
		engine.setProperty(Properties.EXPANSION_VALUES, expansionValues);
	}

	public final void setExpansionValues(ValueBinding expansionValues) {
		engine.setValueBinding(Properties.EXPANSION_VALUES, expansionValues);
	}

	public final boolean isExpansionValuesSetted() {
		return engine.isPropertySetted(Properties.EXPANSION_VALUES);
	}

	public final boolean isExpansionUseValue() {
		return isExpansionUseValue(null);
	}

	public final boolean isExpansionUseValue(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.EXPANSION_USE_VALUE, false, facesContext);
	}

	public final void setExpansionUseValue(boolean expansionUseValue) {
		engine.setProperty(Properties.EXPANSION_USE_VALUE, expansionUseValue);
	}

	public final void setExpansionUseValue(ValueBinding expansionUseValue) {
		engine.setProperty(Properties.EXPANSION_USE_VALUE, expansionUseValue);
	}

	public final boolean isExpansionUseValueSetted() {
		return engine.isPropertySetted(Properties.EXPANSION_USE_VALUE);
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
}
