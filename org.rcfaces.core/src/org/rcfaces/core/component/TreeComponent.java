package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IMenuCapability;
import java.util.Arrays;
import org.rcfaces.core.component.capability.ICheckCardinalityCapability;
import org.rcfaces.core.component.capability.ICheckableCapability;
import org.rcfaces.core.internal.converter.CardinalityConverter;
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.component.capability.ICheckEventCapability;
import org.rcfaces.core.component.capability.ISelectionCardinalityCapability;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.component.IMenuComponent;
import javax.faces.component.UISelectItem;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import java.lang.String;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.ISelectableCapability;
import org.rcfaces.core.internal.tools.TreeTools;
import org.rcfaces.core.internal.util.ComponentIterators;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.component.capability.IReadOnlyCapability;

/**
 * <p>The tree Component shows informations in an arborescent view.
 * It can be compared to the tree found in most modern file explorer.
 * It allows contextual menus ...</p>
 * <p>The tree Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; images</li>
 * <li>Margin &amp; border</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * <li>Contextual actions in node or tree scope</li>
 * </ul>
 * </p>
 */
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

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","horizontalScrollPosition","doubleClickListener","hideRootExpandSign","selectable","defaultExpandedLeafImageURL","expansionValues","checkable","defaultSelectedImageURL","checkedValues","defaultLeafImageURL","checkCardinality","border","defaultExpandedImageURL","defaultDisabledLeafImageURL","verticalScrollPosition","defaultDisabledImageURL","defaultSelectedLeafImageURL","expansionUseValue","defaultImageURL","required","cursorValue","clientCheckFullState","clientSelectionFullState","checkListener","preloadedLevelDepth","userExpandable","selectionCardinality","readOnly","selectedValues"}));
	}
	protected static final String CAMELIA_VALUE_ALIAS="value";

	public TreeComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TreeComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setSelectionCardinality(String cardinality) {


			setSelectionCardinality(((Integer)CardinalityConverter.SINGLETON.getAsObject(null, this, cardinality)).intValue());
		
	}

	public final void setCheckCardinality(String cardinality) {


			setCheckCardinality(((Integer)CardinalityConverter.SINGLETON.getAsObject(null, this, cardinality)).intValue());
		
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

	public final void addSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
	}

	/**
	 * Returns an url string pointing to the default image.
	 * @return image url
	 */
	public final String getDefaultImageURL() {
		return getDefaultImageURL(null);
	}

	/**
	 * Returns an url string pointing to the default image.
	 * @return image url
	 */
	public final String getDefaultImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the default image.
	 * @param defaultImageURL image url
	 */
	public final void setDefaultImageURL(String defaultImageURL) {
		engine.setProperty(Properties.DEFAULT_IMAGE_URL, defaultImageURL);
	}

	/**
	 * Sets an url string pointing to the default image.
	 * @param defaultImageURL image url
	 */
	public final void setDefaultImageURL(ValueBinding defaultImageURL) {
		engine.setProperty(Properties.DEFAULT_IMAGE_URL, defaultImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDefaultImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_IMAGE_URL);
	}

	/**
	 * Returns an url string pointing to the default image for the selected state.
	 * @return selected image url
	 */
	public final String getDefaultSelectedImageURL() {
		return getDefaultSelectedImageURL(null);
	}

	/**
	 * Returns an url string pointing to the default image for the selected state.
	 * @return selected image url
	 */
	public final String getDefaultSelectedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_SELECTED_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the default image for the selected state.
	 * @param defaultSelectedImageURL selected image url
	 */
	public final void setDefaultSelectedImageURL(String defaultSelectedImageURL) {
		engine.setProperty(Properties.DEFAULT_SELECTED_IMAGE_URL, defaultSelectedImageURL);
	}

	/**
	 * Sets an url string pointing to the default image for the selected state.
	 * @param defaultSelectedImageURL selected image url
	 */
	public final void setDefaultSelectedImageURL(ValueBinding defaultSelectedImageURL) {
		engine.setProperty(Properties.DEFAULT_SELECTED_IMAGE_URL, defaultSelectedImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultSelectedImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDefaultSelectedImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_SELECTED_IMAGE_URL);
	}

	/**
	 * Returns an url string pointing to the default image for an expanded node.
	 * @return expanded image url
	 */
	public final String getDefaultExpandedImageURL() {
		return getDefaultExpandedImageURL(null);
	}

	/**
	 * Returns an url string pointing to the default image for an expanded node.
	 * @return expanded image url
	 */
	public final String getDefaultExpandedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_EXPANDED_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the default image for an expanded node.
	 * @param defaultExpandedImageURL expanded image url
	 */
	public final void setDefaultExpandedImageURL(String defaultExpandedImageURL) {
		engine.setProperty(Properties.DEFAULT_EXPANDED_IMAGE_URL, defaultExpandedImageURL);
	}

	/**
	 * Sets an url string pointing to the default image for an expanded node.
	 * @param defaultExpandedImageURL expanded image url
	 */
	public final void setDefaultExpandedImageURL(ValueBinding defaultExpandedImageURL) {
		engine.setProperty(Properties.DEFAULT_EXPANDED_IMAGE_URL, defaultExpandedImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultExpandedImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDefaultExpandedImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_EXPANDED_IMAGE_URL);
	}

	/**
	 * Returns an url string pointing to the default image for the disabled state.
	 * @return disabled image url
	 */
	public final String getDefaultDisabledImageURL() {
		return getDefaultDisabledImageURL(null);
	}

	/**
	 * Returns an url string pointing to the default image for the disabled state.
	 * @return disabled image url
	 */
	public final String getDefaultDisabledImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_DISABLED_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the default image for the disabled state.
	 * @param defaultDisabledImageURL disabled image url
	 */
	public final void setDefaultDisabledImageURL(String defaultDisabledImageURL) {
		engine.setProperty(Properties.DEFAULT_DISABLED_IMAGE_URL, defaultDisabledImageURL);
	}

	/**
	 * Sets an url string pointing to the default image for the disabled state.
	 * @param defaultDisabledImageURL disabled image url
	 */
	public final void setDefaultDisabledImageURL(ValueBinding defaultDisabledImageURL) {
		engine.setProperty(Properties.DEFAULT_DISABLED_IMAGE_URL, defaultDisabledImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultDisabledImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDefaultDisabledImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_DISABLED_IMAGE_URL);
	}

	/**
	 * Returns an url string pointing to the default image for a leaf.
	 * @return leaf image url
	 */
	public final String getDefaultLeafImageURL() {
		return getDefaultLeafImageURL(null);
	}

	/**
	 * Returns an url string pointing to the default image for a leaf.
	 * @return leaf image url
	 */
	public final String getDefaultLeafImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_LEAF_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the default image for a leaf.
	 * @param defaultLeafImageURL leaf image url
	 */
	public final void setDefaultLeafImageURL(String defaultLeafImageURL) {
		engine.setProperty(Properties.DEFAULT_LEAF_IMAGE_URL, defaultLeafImageURL);
	}

	/**
	 * Sets an url string pointing to the default image for a leaf.
	 * @param defaultLeafImageURL leaf image url
	 */
	public final void setDefaultLeafImageURL(ValueBinding defaultLeafImageURL) {
		engine.setProperty(Properties.DEFAULT_LEAF_IMAGE_URL, defaultLeafImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultLeafImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
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

	/**
	 * Returns <code>true</code> if the attribute "defaultSelectedLeafImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
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

	/**
	 * Returns <code>true</code> if the attribute "defaultExpandedLeafImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
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

	/**
	 * Returns <code>true</code> if the attribute "defaultDisabledLeafImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDefaultDisabledLeafImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_DISABLED_LEAF_IMAGE_URL);
	}

	/**
	 * Returns a boolean value indicating wether the user can expand the component.
	 * @return true if the user can expand the component
	 */
	public final boolean isUserExpandable() {
		return isUserExpandable(null);
	}

	/**
	 * Returns a boolean value indicating wether the user can expand the component.
	 * @return true if the user can expand the component
	 */
	public final boolean isUserExpandable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.USER_EXPANDABLE, true, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the user can expand the component.
	 * @param userExpandable true if the user can expand the component
	 */
	public final void setUserExpandable(boolean userExpandable) {
		engine.setProperty(Properties.USER_EXPANDABLE, userExpandable);
	}

	/**
	 * Sets a boolean value indicating wether the user can expand the component.
	 * @param userExpandable true if the user can expand the component
	 */
	public final void setUserExpandable(ValueBinding userExpandable) {
		engine.setProperty(Properties.USER_EXPANDABLE, userExpandable);
	}

	/**
	 * Returns <code>true</code> if the attribute "userExpandable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isUserExpandableSetted() {
		return engine.isPropertySetted(Properties.USER_EXPANDABLE);
	}

	/**
	 * Returns a boolean value indicating wether the expand sign should be visible for the topmost node.
	 * @return true if the head node's expand sign is hidden
	 */
	public final boolean isHideRootExpandSign() {
		return isHideRootExpandSign(null);
	}

	/**
	 * Returns a boolean value indicating wether the expand sign should be visible for the topmost node.
	 * @return true if the head node's expand sign is hidden
	 */
	public final boolean isHideRootExpandSign(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.HIDE_ROOT_EXPAND_SIGN, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the expand sign should be visible for the topmost node.
	 * @param hideRootExpandSign true if the head node's expand sign is to be hidden
	 */
	public final void setHideRootExpandSign(boolean hideRootExpandSign) {
		engine.setProperty(Properties.HIDE_ROOT_EXPAND_SIGN, hideRootExpandSign);
	}

	/**
	 * Sets a boolean value indicating wether the expand sign should be visible for the topmost node.
	 * @param hideRootExpandSign true if the head node's expand sign is to be hidden
	 */
	public final void setHideRootExpandSign(ValueBinding hideRootExpandSign) {
		engine.setProperty(Properties.HIDE_ROOT_EXPAND_SIGN, hideRootExpandSign);
	}

	/**
	 * Returns <code>true</code> if the attribute "hideRootExpandSign" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHideRootExpandSignSetted() {
		return engine.isPropertySetted(Properties.HIDE_ROOT_EXPAND_SIGN);
	}

	/**
	 * Returns an int value specifying the number of levels that should be preloaded when a node is displayed.
	 * @return number of preloaded levels
	 */
	public final int getPreloadedLevelDepth() {
		return getPreloadedLevelDepth(null);
	}

	/**
	 * Returns an int value specifying the number of levels that should be preloaded when a node is displayed.
	 * @return number of preloaded levels
	 */
	public final int getPreloadedLevelDepth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.PRELOADED_LEVEL_DEPTH, 0, facesContext);
	}

	/**
	 * Sets an int value specifying the number of levels that should be preloaded when a node is displayed.
	 * @param preloadedLevelDepth number of levels to preload
	 */
	public final void setPreloadedLevelDepth(int preloadedLevelDepth) {
		engine.setProperty(Properties.PRELOADED_LEVEL_DEPTH, preloadedLevelDepth);
	}

	/**
	 * Sets an int value specifying the number of levels that should be preloaded when a node is displayed.
	 * @param preloadedLevelDepth number of levels to preload
	 */
	public final void setPreloadedLevelDepth(ValueBinding preloadedLevelDepth) {
		engine.setProperty(Properties.PRELOADED_LEVEL_DEPTH, preloadedLevelDepth);
	}

	/**
	 * Returns <code>true</code> if the attribute "preloadedLevelDepth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isPreloadedLevelDepthSetted() {
		return engine.isPropertySetted(Properties.PRELOADED_LEVEL_DEPTH);
	}

	/**
	 * Returns a table of the values associated with selected nodes for the component. (Binding only)
	 * @return table of values
	 */
	public final Object getSelectedValues() {
		return getSelectedValues(null);
	}

	/**
	 * Returns a table of the values associated with selected nodes for the component. (Binding only)
	 * @return table of values
	 */
	public final Object getSelectedValues(javax.faces.context.FacesContext facesContext) {
		return engine.getValue(Properties.SELECTED_VALUES, facesContext);
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

	/**
	 * Returns <code>true</code> if the attribute "expansionValues" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isExpansionValuesSetted() {
		return engine.isPropertySetted(Properties.EXPANSION_VALUES);
	}

	public final Object getCursorValue() {
		return getCursorValue(null);
	}

	public final Object getCursorValue(javax.faces.context.FacesContext facesContext) {
		return engine.getValue(Properties.CURSOR_VALUE, facesContext);
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
	 * Returns a boolean value that indicates wether a node is identified by it's value or by a complete path.
	 * @return true if node identification use node value
	 */
	public final boolean isExpansionUseValue() {
		return isExpansionUseValue(null);
	}

	/**
	 * Returns a boolean value that indicates wether a node is identified by it's value or by a complete path.
	 * @return true if node identification use node value
	 */
	public final boolean isExpansionUseValue(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.EXPANSION_USE_VALUE, false, facesContext);
	}

	/**
	 * Sets a boolean value that indicates wether a node is identified by it's value or by a complete path.
	 * @param expansionUseValue true if node identification should use node value
	 */
	public final void setExpansionUseValue(boolean expansionUseValue) {
		engine.setProperty(Properties.EXPANSION_USE_VALUE, expansionUseValue);
	}

	/**
	 * Sets a boolean value that indicates wether a node is identified by it's value or by a complete path.
	 * @param expansionUseValue true if node identification should use node value
	 */
	public final void setExpansionUseValue(ValueBinding expansionUseValue) {
		engine.setProperty(Properties.EXPANSION_USE_VALUE, expansionUseValue);
	}

	/**
	 * Returns <code>true</code> if the attribute "expansionUseValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isExpansionUseValueSetted() {
		return engine.isPropertySetted(Properties.EXPANSION_USE_VALUE);
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

	protected String getCameliaValueAlias() {
		return CAMELIA_VALUE_ALIAS;
	}
}
