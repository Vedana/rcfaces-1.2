package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.ICheckCardinalityCapability;
import org.rcfaces.core.component.capability.ICheckEventCapability;
import org.rcfaces.core.component.capability.ICheckableCapability;
import org.rcfaces.core.component.capability.ICheckedValuesCapability;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.capability.IPreloadedLevelDepthCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.component.capability.ISelectableCapability;
import org.rcfaces.core.component.capability.ISelectionCardinalityCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.IShowValueCapability;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.CardinalityConverter;
import org.rcfaces.core.internal.tools.CheckTools;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.internal.tools.TreeTools;
import org.rcfaces.core.internal.util.ComponentIterators;

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
	IShowValueCapability,
	ICheckableCapability,
	ICheckCardinalityCapability,
	ICheckEventCapability,
	ICheckedValuesCapability,
	ISelectableCapability,
	ISelectionCardinalityCapability,
	ISelectionEventCapability,
	IPreloadedLevelDepthCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.tree";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","horizontalScrollPosition","doubleClickListener","hideRootExpandSign","selectable","showValue","defaultExpandedLeafImageURL","expansionValues","checkable","checkedValues","defaultSelectedImageURL","defaultLeafImageURL","checkCardinality","border","defaultExpandedImageURL","defaultDisabledLeafImageURL","verticalScrollPosition","defaultDisabledImageURL","defaultSelectedLeafImageURL","expansionUseValue","defaultImageURL","required","cursorValue","clientCheckFullState","clientSelectionFullState","checkListener","preloadedLevelDepth","userExpandable","selectionCardinality","readOnly","selectedValues"}));
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

	public final void setExpanded(Object value, boolean expanded) {


				setExpanded(null, value, expanded);
			
	}

	public final boolean isExpanded(FacesContext context, Object value) {


				return TreeTools.isExpanded(context, this, value);
			
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

	public final void addDoubleClickListener(org.rcfaces.core.event.IDoubleClickListener listener) {
		addFacesListener(listener);
	}

	public final void removeDoubleClickListener(org.rcfaces.core.event.IDoubleClickListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listDoubleClickListeners() {
		return getFacesListeners(org.rcfaces.core.event.IDoubleClickListener.class);
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

	/**
	 * See {@link #setHorizontalScrollPosition(int) setHorizontalScrollPosition(int)} for more details
	 */
	public void setHorizontalScrollPosition(ValueBinding horizontalScrollPosition) {
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

	/**
	 * See {@link #setVerticalScrollPosition(int) setVerticalScrollPosition(int)} for more details
	 */
	public void setVerticalScrollPosition(ValueBinding verticalScrollPosition) {
		engine.setProperty(Properties.VERTICAL_SCROLL_POSITION, verticalScrollPosition);
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

	/**
	 * See {@link #setBorder(boolean) setBorder(boolean)} for more details
	 */
	public void setBorder(ValueBinding border) {
		engine.setProperty(Properties.BORDER, border);
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

	/**
	 * See {@link #setReadOnly(boolean) setReadOnly(boolean)} for more details
	 */
	public void setReadOnly(ValueBinding readOnly) {
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

	/**
	 * See {@link #setShowValue(Object) setShowValue(Object)} for more details
	 */
	public void setShowValue(ValueBinding showValue) {
		engine.setProperty(Properties.SHOW_VALUE, showValue);
	}

	public boolean isCheckable() {
		return isCheckable(null);
	}

	/**
	 * See {@link #isCheckable() isCheckable()} for more details
	 */
	public boolean isCheckable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CHECKABLE, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "checkable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCheckableSetted() {
		return engine.isPropertySetted(Properties.CHECKABLE);
	}

	public void setCheckable(boolean checkable) {
		engine.setProperty(Properties.CHECKABLE, checkable);
	}

	/**
	 * See {@link #setCheckable(boolean) setCheckable(boolean)} for more details
	 */
	public void setCheckable(ValueBinding checkable) {
		engine.setProperty(Properties.CHECKABLE, checkable);
	}

	public int getCheckCardinality() {
		return getCheckCardinality(null);
	}

	/**
	 * See {@link #getCheckCardinality() getCheckCardinality()} for more details
	 */
	public int getCheckCardinality(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.CHECK_CARDINALITY,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "checkCardinality" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCheckCardinalitySetted() {
		return engine.isPropertySetted(Properties.CHECK_CARDINALITY);
	}

	public void setCheckCardinality(int checkCardinality) {
		engine.setProperty(Properties.CHECK_CARDINALITY, checkCardinality);
	}

	/**
	 * See {@link #setCheckCardinality(int) setCheckCardinality(int)} for more details
	 */
	public void setCheckCardinality(ValueBinding checkCardinality) {
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

	public java.lang.Object getCheckedValues() {
		return getCheckedValues(null);
	}

	/**
	 * See {@link #getCheckedValues() getCheckedValues()} for more details
	 */
	public java.lang.Object getCheckedValues(javax.faces.context.FacesContext facesContext) {
		return engine.getProperty(Properties.CHECKED_VALUES, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "checkedValues" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCheckedValuesSetted() {
		return engine.isPropertySetted(Properties.CHECKED_VALUES);
	}

	public void setCheckedValues(java.lang.Object checkedValues) {
		engine.setProperty(Properties.CHECKED_VALUES, checkedValues);
	}

	/**
	 * See {@link #setCheckedValues(Object) setCheckedValues(Object)} for more details
	 */
	public void setCheckedValues(ValueBinding checkedValues) {
		engine.setProperty(Properties.CHECKED_VALUES, checkedValues);
	}

	/**
	 * Return the type of the property represented by the {@link ValueBinding}, relative to the specified {@link javax.faces.context.FacesContext}.
	 */
	public Class getCheckedValuesType(javax.faces.context.FacesContext facesContext) {
		ValueBinding valueBinding=engine.getValueBindingProperty(Properties.CHECKED_VALUES);
		if (valueBinding==null) {
			return null;
		}
		if (facesContext==null) {
			facesContext=javax.faces.context.FacesContext.getCurrentInstance();
		}
		return valueBinding.getType(facesContext);
	}

	public final int getCheckedValuesCount() {


			return CheckTools.getCount(getCheckedValues());
		
	}

	public final Object getFirstCheckedValue() {


			return CheckTools.getFirst(getCheckedValues());
		
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

	/**
	 * See {@link #setSelectable(boolean) setSelectable(boolean)} for more details
	 */
	public void setSelectable(ValueBinding selectable) {
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

	/**
	 * See {@link #setSelectionCardinality(int) setSelectionCardinality(int)} for more details
	 */
	public void setSelectionCardinality(ValueBinding selectionCardinality) {
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

	public int getPreloadedLevelDepth() {
		return getPreloadedLevelDepth(null);
	}

	/**
	 * See {@link #getPreloadedLevelDepth() getPreloadedLevelDepth()} for more details
	 */
	public int getPreloadedLevelDepth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.PRELOADED_LEVEL_DEPTH,-1, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "preloadedLevelDepth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isPreloadedLevelDepthSetted() {
		return engine.isPropertySetted(Properties.PRELOADED_LEVEL_DEPTH);
	}

	public void setPreloadedLevelDepth(int preloadedLevelDepth) {
		engine.setProperty(Properties.PRELOADED_LEVEL_DEPTH, preloadedLevelDepth);
	}

	/**
	 * See {@link #setPreloadedLevelDepth(int) setPreloadedLevelDepth(int)} for more details
	 */
	public void setPreloadedLevelDepth(ValueBinding preloadedLevelDepth) {
		engine.setProperty(Properties.PRELOADED_LEVEL_DEPTH, preloadedLevelDepth);
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
