package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.component.capability.ICellToolTipTextCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IStatesImageCapability;
import org.rcfaces.core.component.capability.IUserEventCapability;
import org.rcfaces.core.component.capability.IMenuPopupIdCapability;
import org.rcfaces.core.component.capability.IImageSizeCapability;
import org.rcfaces.core.component.capability.IResizableCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.ICellStyleClassCapability;
import org.rcfaces.core.internal.capability.ICellToolTipTextSettings;
import org.rcfaces.core.component.capability.IToolTipCapability;
import org.rcfaces.core.internal.capability.ICellStyleClassSettings;
import java.util.Set;
import org.rcfaces.core.component.capability.IInitEventCapability;
import org.rcfaces.core.internal.converter.OrderConverter;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.capability.IOrderCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import java.lang.String;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.ISortComparatorCapability;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import org.rcfaces.core.internal.component.CameliaColumnComponent;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import org.rcfaces.core.component.capability.IAlignmentCapability;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.ISortEventCapability;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IWidthRangeCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import java.util.Arrays;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import org.rcfaces.core.component.capability.IVerticalAlignmentCapability;
import org.rcfaces.core.component.capability.ITextCapability;

public class ComponentsColumnComponent extends CameliaColumnComponent implements 
	IVisibilityCapability,
	IHiddenModeCapability,
	ITextCapability,
	ITextDirectionCapability,
	IToolTipCapability,
	IAlignmentCapability,
	IForegroundBackgroundColorCapability,
	ISortEventCapability,
	IStyleClassCapability,
	IOrderCapability,
	ISortComparatorCapability,
	IResizableCapability,
	IStatesImageCapability,
	IImageSizeCapability,
	IMenuPopupIdCapability,
	IWidthRangeCapability,
	IVerticalAlignmentCapability,
	ICellStyleClassCapability,
	ICellToolTipTextCapability,
	ISelectionEventCapability,
	IDoubleClickEventCapability,
	IUserEventCapability,
	IInitEventCapability,
	IImageAccessorsCapability,
	ICellStyleClassSettings,
	ICellToolTipTextSettings {

	public static final String COMPONENT_TYPE="org.rcfaces.core.componentsColumn";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaColumnComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"disabledImageURL","alignment","menuPopupId","visible","backgroundColor","minWidth","sortComparator","cellStyleClass","selectedImageURL","selectionListener","hiddenMode","defaultCellStyleClass","maxWidth","resizable","ascending","foregroundColor","imageHeight","text","cellToolTipText","userEventListener","styleClass","hoverImageURL","width","doubleClickListener","cellDefaultToolTipText","initListener","textDirection","verticalAlignment","sortListener","toolTipText","imageURL","imageWidth"}));
	}

	public ComponentsColumnComponent() {
		setRendererType(null);
	}

	public ComponentsColumnComponent(String componentId) {
		this();
		setId(componentId);
	}

	public IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, engine);
		
	}

	public Boolean getVisibleState(FacesContext facesContext) {


			if (engine.isPropertySetted(Properties.VISIBLE)==false) {
				return null;
			}
			
			return Boolean.valueOf(isVisible(facesContext));
		
	}

	public void setHiddenMode(String hiddenMode) {


			setHiddenMode(((Integer)HiddenModeConverter.SINGLETON.getAsObject(null, this, hiddenMode)).intValue());
		
	}

	public void setAscending(String order) {


			setAscending(((Boolean)OrderConverter.SINGLETON.getAsObject(null, this, order)).booleanValue());
		
	}

	public boolean isVisible() {
		return isVisible(null);
	}

	/**
	 * See {@link #isVisible() isVisible()} for more details
	 */
	public boolean isVisible(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.VISIBLE, true, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "visible" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVisibleSetted() {
		return engine.isPropertySetted(Properties.VISIBLE);
	}

	public void setVisible(boolean visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public Boolean getVisibleState() {


			return getVisibleState(null);
		
	}

	public int getHiddenMode() {
		return getHiddenMode(null);
	}

	/**
	 * See {@link #getHiddenMode() getHiddenMode()} for more details
	 */
	public int getHiddenMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.HIDDEN_MODE,IHiddenModeCapability.DEFAULT_HIDDEN_MODE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "hiddenMode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHiddenModeSetted() {
		return engine.isPropertySetted(Properties.HIDDEN_MODE);
	}

	public void setHiddenMode(int hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return engine.isPropertySetted(Properties.TEXT);
	}

	public void setText(java.lang.String text) {
		engine.setProperty(Properties.TEXT, text);
	}

	public int getTextDirection() {
		return getTextDirection(null);
	}

	/**
	 * See {@link #getTextDirection() getTextDirection()} for more details
	 */
	public int getTextDirection(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.TEXT_DIRECTION,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "textDirection" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextDirectionSetted() {
		return engine.isPropertySetted(Properties.TEXT_DIRECTION);
	}

	public void setTextDirection(int textDirection) {
		engine.setProperty(Properties.TEXT_DIRECTION, textDirection);
	}

	public java.lang.String getToolTipText() {
		return getToolTipText(null);
	}

	/**
	 * See {@link #getToolTipText() getToolTipText()} for more details
	 */
	public java.lang.String getToolTipText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TOOL_TIP_TEXT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "toolTipText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isToolTipTextSetted() {
		return engine.isPropertySetted(Properties.TOOL_TIP_TEXT);
	}

	public void setToolTipText(java.lang.String toolTipText) {
		engine.setProperty(Properties.TOOL_TIP_TEXT, toolTipText);
	}

	public java.lang.String getAlignment() {
		return getAlignment(null);
	}

	/**
	 * See {@link #getAlignment() getAlignment()} for more details
	 */
	public java.lang.String getAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ALIGNMENT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "alignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAlignmentSetted() {
		return engine.isPropertySetted(Properties.ALIGNMENT);
	}

	public void setAlignment(java.lang.String alignment) {
		engine.setProperty(Properties.ALIGNMENT, alignment);
	}

	public java.lang.String getBackgroundColor() {
		return getBackgroundColor(null);
	}

	/**
	 * See {@link #getBackgroundColor() getBackgroundColor()} for more details
	 */
	public java.lang.String getBackgroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_COLOR, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundColor" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundColorSetted() {
		return engine.isPropertySetted(Properties.BACKGROUND_COLOR);
	}

	public void setBackgroundColor(java.lang.String backgroundColor) {
		engine.setProperty(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	public java.lang.String getForegroundColor() {
		return getForegroundColor(null);
	}

	/**
	 * See {@link #getForegroundColor() getForegroundColor()} for more details
	 */
	public java.lang.String getForegroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOREGROUND_COLOR, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "foregroundColor" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForegroundColorSetted() {
		return engine.isPropertySetted(Properties.FOREGROUND_COLOR);
	}

	public void setForegroundColor(java.lang.String foregroundColor) {
		engine.setProperty(Properties.FOREGROUND_COLOR, foregroundColor);
	}

	public final void addSortListener(org.rcfaces.core.event.ISortListener listener) {
		addFacesListener(listener);
	}

	public final void removeSortListener(org.rcfaces.core.event.ISortListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSortListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISortListener.class);
	}

	public java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	/**
	 * See {@link #getStyleClass() getStyleClass()} for more details
	 */
	public java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "styleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isStyleClassSetted() {
		return engine.isPropertySetted(Properties.STYLE_CLASS);
	}

	public void setStyleClass(java.lang.String styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	public boolean isAscending() {
		return isAscending(null);
	}

	/**
	 * See {@link #isAscending() isAscending()} for more details
	 */
	public boolean isAscending(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.ASCENDING, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "ascending" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAscendingSetted() {
		return engine.isPropertySetted(Properties.ASCENDING);
	}

	public void setAscending(boolean ascending) {
		engine.setProperty(Properties.ASCENDING, ascending);
	}

	public java.util.Comparator getSortComparator() {
		return getSortComparator(null);
	}

	/**
	 * See {@link #getSortComparator() getSortComparator()} for more details
	 */
	public java.util.Comparator getSortComparator(javax.faces.context.FacesContext facesContext) {
		return (java.util.Comparator)engine.getProperty(Properties.SORT_COMPARATOR, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "sortComparator" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSortComparatorSetted() {
		return engine.isPropertySetted(Properties.SORT_COMPARATOR);
	}

	public void setSortComparator(java.util.Comparator sortComparator) {
		engine.setProperty(Properties.SORT_COMPARATOR, sortComparator);
	}

	public boolean isResizable() {
		return isResizable(null);
	}

	/**
	 * See {@link #isResizable() isResizable()} for more details
	 */
	public boolean isResizable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.RESIZABLE, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "resizable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isResizableSetted() {
		return engine.isPropertySetted(Properties.RESIZABLE);
	}

	public void setResizable(boolean resizable) {
		engine.setProperty(Properties.RESIZABLE, resizable);
	}

	public java.lang.String getDisabledImageURL() {
		return getDisabledImageURL(null);
	}

	/**
	 * See {@link #getDisabledImageURL() getDisabledImageURL()} for more details
	 */
	public java.lang.String getDisabledImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DISABLED_IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "disabledImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledImageURLSetted() {
		return engine.isPropertySetted(Properties.DISABLED_IMAGE_URL);
	}

	public void setDisabledImageURL(java.lang.String disabledImageURL) {
		engine.setProperty(Properties.DISABLED_IMAGE_URL, disabledImageURL);
	}

	public java.lang.String getHoverImageURL() {
		return getHoverImageURL(null);
	}

	/**
	 * See {@link #getHoverImageURL() getHoverImageURL()} for more details
	 */
	public java.lang.String getHoverImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HOVER_IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "hoverImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHoverImageURLSetted() {
		return engine.isPropertySetted(Properties.HOVER_IMAGE_URL);
	}

	public void setHoverImageURL(java.lang.String hoverImageURL) {
		engine.setProperty(Properties.HOVER_IMAGE_URL, hoverImageURL);
	}

	public java.lang.String getSelectedImageURL() {
		return getSelectedImageURL(null);
	}

	/**
	 * See {@link #getSelectedImageURL() getSelectedImageURL()} for more details
	 */
	public java.lang.String getSelectedImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SELECTED_IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectedImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSelectedImageURLSetted() {
		return engine.isPropertySetted(Properties.SELECTED_IMAGE_URL);
	}

	public void setSelectedImageURL(java.lang.String selectedImageURL) {
		engine.setProperty(Properties.SELECTED_IMAGE_URL, selectedImageURL);
	}

	public java.lang.String getImageURL() {
		return getImageURL(null);
	}

	/**
	 * See {@link #getImageURL() getImageURL()} for more details
	 */
	public java.lang.String getImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageURLSetted() {
		return engine.isPropertySetted(Properties.IMAGE_URL);
	}

	public void setImageURL(java.lang.String imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
	}

	public IContentAccessors getImageAccessors() {


			return getImageAccessors(null);
		
	}

	public int getImageHeight() {
		return getImageHeight(null);
	}

	/**
	 * See {@link #getImageHeight() getImageHeight()} for more details
	 */
	public int getImageHeight(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.IMAGE_HEIGHT,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageHeight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageHeightSetted() {
		return engine.isPropertySetted(Properties.IMAGE_HEIGHT);
	}

	public void setImageHeight(int imageHeight) {
		engine.setProperty(Properties.IMAGE_HEIGHT, imageHeight);
	}

	public int getImageWidth() {
		return getImageWidth(null);
	}

	/**
	 * See {@link #getImageWidth() getImageWidth()} for more details
	 */
	public int getImageWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.IMAGE_WIDTH,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageWidthSetted() {
		return engine.isPropertySetted(Properties.IMAGE_WIDTH);
	}

	public void setImageWidth(int imageWidth) {
		engine.setProperty(Properties.IMAGE_WIDTH, imageWidth);
	}

	public java.lang.String getMenuPopupId() {
		return getMenuPopupId(null);
	}

	/**
	 * See {@link #getMenuPopupId() getMenuPopupId()} for more details
	 */
	public java.lang.String getMenuPopupId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MENU_POPUP_ID, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "menuPopupId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMenuPopupIdSetted() {
		return engine.isPropertySetted(Properties.MENU_POPUP_ID);
	}

	public void setMenuPopupId(java.lang.String menuPopupId) {
		engine.setProperty(Properties.MENU_POPUP_ID, menuPopupId);
	}

	public int getMaxWidth() {
		return getMaxWidth(null);
	}

	/**
	 * See {@link #getMaxWidth() getMaxWidth()} for more details
	 */
	public int getMaxWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.MAX_WIDTH,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "maxWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMaxWidthSetted() {
		return engine.isPropertySetted(Properties.MAX_WIDTH);
	}

	public void setMaxWidth(int maxWidth) {
		engine.setProperty(Properties.MAX_WIDTH, maxWidth);
	}

	public int getMinWidth() {
		return getMinWidth(null);
	}

	/**
	 * See {@link #getMinWidth() getMinWidth()} for more details
	 */
	public int getMinWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.MIN_WIDTH,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "minWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMinWidthSetted() {
		return engine.isPropertySetted(Properties.MIN_WIDTH);
	}

	public void setMinWidth(int minWidth) {
		engine.setProperty(Properties.MIN_WIDTH, minWidth);
	}

	public java.lang.String getWidth() {
		return getWidth(null);
	}

	/**
	 * See {@link #getWidth() getWidth()} for more details
	 */
	public java.lang.String getWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WIDTH, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "width" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWidthSetted() {
		return engine.isPropertySetted(Properties.WIDTH);
	}

	public void setWidth(java.lang.String width) {
		engine.setProperty(Properties.WIDTH, width);
	}

	public java.lang.String getVerticalAlignment() {
		return getVerticalAlignment(null);
	}

	/**
	 * See {@link #getVerticalAlignment() getVerticalAlignment()} for more details
	 */
	public java.lang.String getVerticalAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.VERTICAL_ALIGNMENT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalAlignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalAlignmentSetted() {
		return engine.isPropertySetted(Properties.VERTICAL_ALIGNMENT);
	}

	public void setVerticalAlignment(java.lang.String verticalAlignment) {
		engine.setProperty(Properties.VERTICAL_ALIGNMENT, verticalAlignment);
	}

	public java.lang.String getCellStyleClass() {
		return getCellStyleClass(null);
	}

	/**
	 * See {@link #getCellStyleClass() getCellStyleClass()} for more details
	 */
	public java.lang.String getCellStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CELL_STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "cellStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCellStyleClassSetted() {
		return engine.isPropertySetted(Properties.CELL_STYLE_CLASS);
	}

	public void setCellStyleClass(java.lang.String cellStyleClass) {
		engine.setProperty(Properties.CELL_STYLE_CLASS, cellStyleClass);
	}

	public java.lang.String getDefaultCellStyleClass() {
		return getDefaultCellStyleClass(null);
	}

	/**
	 * See {@link #getDefaultCellStyleClass() getDefaultCellStyleClass()} for more details
	 */
	public java.lang.String getDefaultCellStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_CELL_STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultCellStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDefaultCellStyleClassSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_CELL_STYLE_CLASS);
	}

	public void setDefaultCellStyleClass(java.lang.String defaultCellStyleClass) {
		engine.setProperty(Properties.DEFAULT_CELL_STYLE_CLASS, defaultCellStyleClass);
	}

	public java.lang.String getCellDefaultToolTipText() {
		return getCellDefaultToolTipText(null);
	}

	/**
	 * See {@link #getCellDefaultToolTipText() getCellDefaultToolTipText()} for more details
	 */
	public java.lang.String getCellDefaultToolTipText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CELL_DEFAULT_TOOL_TIP_TEXT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "cellDefaultToolTipText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCellDefaultToolTipTextSetted() {
		return engine.isPropertySetted(Properties.CELL_DEFAULT_TOOL_TIP_TEXT);
	}

	public void setCellDefaultToolTipText(java.lang.String cellDefaultToolTipText) {
		engine.setProperty(Properties.CELL_DEFAULT_TOOL_TIP_TEXT, cellDefaultToolTipText);
	}

	public java.lang.String getCellToolTipText() {
		return getCellToolTipText(null);
	}

	/**
	 * See {@link #getCellToolTipText() getCellToolTipText()} for more details
	 */
	public java.lang.String getCellToolTipText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CELL_TOOL_TIP_TEXT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "cellToolTipText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCellToolTipTextSetted() {
		return engine.isPropertySetted(Properties.CELL_TOOL_TIP_TEXT);
	}

	public void setCellToolTipText(java.lang.String cellToolTipText) {
		engine.setProperty(Properties.CELL_TOOL_TIP_TEXT, cellToolTipText);
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

	public final void addDoubleClickListener(org.rcfaces.core.event.IDoubleClickListener listener) {
		addFacesListener(listener);
	}

	public final void removeDoubleClickListener(org.rcfaces.core.event.IDoubleClickListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listDoubleClickListeners() {
		return getFacesListeners(org.rcfaces.core.event.IDoubleClickListener.class);
	}

	public final void addUserEventListener(org.rcfaces.core.event.IUserEventListener listener) {
		addFacesListener(listener);
	}

	public final void removeUserEventListener(org.rcfaces.core.event.IUserEventListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listUserEventListeners() {
		return getFacesListeners(org.rcfaces.core.event.IUserEventListener.class);
	}

	public final void addInitListener(org.rcfaces.core.event.IInitListener listener) {
		addFacesListener(listener);
	}

	public final void removeInitListener(org.rcfaces.core.event.IInitListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listInitListeners() {
		return getFacesListeners(org.rcfaces.core.event.IInitListener.class);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
