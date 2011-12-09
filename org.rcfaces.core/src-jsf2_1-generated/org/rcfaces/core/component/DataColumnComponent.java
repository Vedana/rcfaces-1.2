package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.component.capability.ICellToolTipTextCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IStatesImageCapability;
import org.rcfaces.core.component.capability.IUserEventCapability;
import org.rcfaces.core.component.capability.IMenuPopupIdCapability;
import org.rcfaces.core.component.capability.IImageSizeCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IResizableCapability;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.ICellStyleClassCapability;
import org.rcfaces.core.internal.capability.ICellToolTipTextSettings;
import org.rcfaces.core.component.capability.IToolTipCapability;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.capability.ICellStyleClassSettings;
import java.util.Set;
import org.rcfaces.core.component.capability.IAutoFilterCapability;
import org.rcfaces.core.component.capability.IInitEventCapability;
import org.rcfaces.core.internal.component.CameliaValueColumnComponent;
import org.rcfaces.core.internal.converter.OrderConverter;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import java.util.Collection;
import org.rcfaces.core.component.capability.ICellImageCapability;
import org.rcfaces.core.component.capability.IOrderCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import java.lang.String;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.ISortComparatorCapability;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import javax.faces.convert.Converter;
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
import org.rcfaces.core.internal.capability.ICellImageSettings;
import org.rcfaces.core.component.capability.IVerticalAlignmentCapability;
import org.rcfaces.core.component.capability.ITextCapability;

/**
 * Specify a column.
 */
public class DataColumnComponent extends CameliaValueColumnComponent implements 
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
	IAutoFilterCapability,
	ICellImageCapability,
	ICellStyleClassCapability,
	ICellToolTipTextCapability,
	ISelectionEventCapability,
	IDoubleClickEventCapability,
	IUserEventCapability,
	IInitEventCapability,
	IImageAccessorsCapability,
	ValueHolder,
	ICellStyleClassSettings,
	ICellToolTipTextSettings,
	ICellImageSettings {

	private static final Log LOG = LogFactory.getLog(DataColumnComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.dataColumn";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=CameliaValueColumnComponent.BEHAVIOR_EVENT_NAMES;

	public DataColumnComponent() {
		setRendererType(null);
	}

	public DataColumnComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setConverter(Converter converter) {


        	getStateHelper().put("converter", converter);
		
	}

	public Object getValue() {


				return getValue(null);
			
	}

	public Object getLocalValue() {


				return getComponentEngine().getLocalValue(Properties.VALUE);
			
	}

	public Converter getConverter() {


        	return (Converter)getStateHelper().eval("converter", null);
		
	}

	public void setValue(Object value) {


				getStateHelper().put(Properties.VALUE, value);
			
	}

	public IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, getComponentEngine());
		
	}

	public void setConverter(String converterId) {


			 setConverter(null, converterId);
		
	}

	public void setConverter(FacesContext facesContext, String converterId) {


			if (facesContext==null) {
				facesContext=FacesContext.getCurrentInstance();
			}
			Converter converter = facesContext.getApplication().createConverter(converterId);
            this.setConverter(converter);
		
	}

	public Boolean getVisibleState(FacesContext facesContext) {


			if (isPropertySetted(Properties.VISIBLE)==false) {
				return null;
			}
			
			return Boolean.valueOf(isVisible(facesContext));
		
	}

	public Object getValue(FacesContext context) {


				return getStateHelper().eval(Properties.VALUE, context);
			
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
		return (Boolean)getStateHelper().eval(Properties.VISIBLE, true);
	}

	/**
	 * Returns <code>true</code> if the attribute "visible" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVisibleSetted() {
		return getStateHelper().get(Properties.VISIBLE)!=null;
	}

	public void setVisible(boolean visible) {
		getStateHelper().put(Properties.VISIBLE, visible);
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
		return (Integer)getStateHelper().eval(Properties.HIDDEN_MODE, IHiddenModeCapability.DEFAULT_HIDDEN_MODE);
	}

	/**
	 * Returns <code>true</code> if the attribute "hiddenMode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHiddenModeSetted() {
		return getStateHelper().get(Properties.HIDDEN_MODE)!=null;
	}

	public void setHiddenMode(int hiddenMode) {
		getStateHelper().put(Properties.HIDDEN_MODE, hiddenMode);
	}

	public java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.TEXT);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return getStateHelper().get(Properties.TEXT)!=null;
	}

	public void setText(java.lang.String text) {
		getStateHelper().put(Properties.TEXT, text);
	}

	public int getTextDirection() {
		return getTextDirection(null);
	}

	/**
	 * See {@link #getTextDirection() getTextDirection()} for more details
	 */
	public int getTextDirection(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.TEXT_DIRECTION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "textDirection" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextDirectionSetted() {
		return getStateHelper().get(Properties.TEXT_DIRECTION)!=null;
	}

	public void setTextDirection(int textDirection) {
		getStateHelper().put(Properties.TEXT_DIRECTION, textDirection);
	}

	public java.lang.String getToolTipText() {
		return getToolTipText(null);
	}

	/**
	 * See {@link #getToolTipText() getToolTipText()} for more details
	 */
	public java.lang.String getToolTipText(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.TOOL_TIP_TEXT);
	}

	/**
	 * Returns <code>true</code> if the attribute "toolTipText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isToolTipTextSetted() {
		return getStateHelper().get(Properties.TOOL_TIP_TEXT)!=null;
	}

	public void setToolTipText(java.lang.String toolTipText) {
		getStateHelper().put(Properties.TOOL_TIP_TEXT, toolTipText);
	}

	public java.lang.String getAlignment() {
		return getAlignment(null);
	}

	/**
	 * See {@link #getAlignment() getAlignment()} for more details
	 */
	public java.lang.String getAlignment(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ALIGNMENT);
	}

	/**
	 * Returns <code>true</code> if the attribute "alignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAlignmentSetted() {
		return getStateHelper().get(Properties.ALIGNMENT)!=null;
	}

	public void setAlignment(java.lang.String alignment) {
		getStateHelper().put(Properties.ALIGNMENT, alignment);
	}

	public java.lang.String getBackgroundColor() {
		return getBackgroundColor(null);
	}

	/**
	 * See {@link #getBackgroundColor() getBackgroundColor()} for more details
	 */
	public java.lang.String getBackgroundColor(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.BACKGROUND_COLOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "backgroundColor" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBackgroundColorSetted() {
		return getStateHelper().get(Properties.BACKGROUND_COLOR)!=null;
	}

	public void setBackgroundColor(java.lang.String backgroundColor) {
		getStateHelper().put(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	public java.lang.String getForegroundColor() {
		return getForegroundColor(null);
	}

	/**
	 * See {@link #getForegroundColor() getForegroundColor()} for more details
	 */
	public java.lang.String getForegroundColor(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FOREGROUND_COLOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "foregroundColor" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForegroundColorSetted() {
		return getStateHelper().get(Properties.FOREGROUND_COLOR)!=null;
	}

	public void setForegroundColor(java.lang.String foregroundColor) {
		getStateHelper().put(Properties.FOREGROUND_COLOR, foregroundColor);
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
		return (String)getStateHelper().eval(Properties.STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "styleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isStyleClassSetted() {
		return getStateHelper().get(Properties.STYLE_CLASS)!=null;
	}

	public void setStyleClass(java.lang.String styleClass) {
		getStateHelper().put(Properties.STYLE_CLASS, styleClass);
	}

	public boolean isAscending() {
		return isAscending(null);
	}

	/**
	 * See {@link #isAscending() isAscending()} for more details
	 */
	public boolean isAscending(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.ASCENDING, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "ascending" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAscendingSetted() {
		return getStateHelper().get(Properties.ASCENDING)!=null;
	}

	public void setAscending(boolean ascending) {
		getStateHelper().put(Properties.ASCENDING, ascending);
	}

	public java.util.Comparator getSortComparator() {
		return getSortComparator(null);
	}

	/**
	 * See {@link #getSortComparator() getSortComparator()} for more details
	 */
	public java.util.Comparator getSortComparator(javax.faces.context.FacesContext facesContext) {
		return (java.util.Comparator)getStateHelper().eval(Properties.SORT_COMPARATOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "sortComparator" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSortComparatorSetted() {
		return getStateHelper().get(Properties.SORT_COMPARATOR)!=null;
	}

	public void setSortComparator(java.util.Comparator sortComparator) {
		getStateHelper().put(Properties.SORT_COMPARATOR, sortComparator);
	}

	public boolean isResizable() {
		return isResizable(null);
	}

	/**
	 * See {@link #isResizable() isResizable()} for more details
	 */
	public boolean isResizable(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.RESIZABLE, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "resizable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isResizableSetted() {
		return getStateHelper().get(Properties.RESIZABLE)!=null;
	}

	public void setResizable(boolean resizable) {
		getStateHelper().put(Properties.RESIZABLE, resizable);
	}

	public java.lang.String getDisabledImageURL() {
		return getDisabledImageURL(null);
	}

	/**
	 * See {@link #getDisabledImageURL() getDisabledImageURL()} for more details
	 */
	public java.lang.String getDisabledImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.DISABLED_IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "disabledImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledImageURLSetted() {
		return getStateHelper().get(Properties.DISABLED_IMAGE_URL)!=null;
	}

	public void setDisabledImageURL(java.lang.String disabledImageURL) {
		getStateHelper().put(Properties.DISABLED_IMAGE_URL, disabledImageURL);
	}

	public java.lang.String getHoverImageURL() {
		return getHoverImageURL(null);
	}

	/**
	 * See {@link #getHoverImageURL() getHoverImageURL()} for more details
	 */
	public java.lang.String getHoverImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.HOVER_IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "hoverImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHoverImageURLSetted() {
		return getStateHelper().get(Properties.HOVER_IMAGE_URL)!=null;
	}

	public void setHoverImageURL(java.lang.String hoverImageURL) {
		getStateHelper().put(Properties.HOVER_IMAGE_URL, hoverImageURL);
	}

	public java.lang.String getSelectedImageURL() {
		return getSelectedImageURL(null);
	}

	/**
	 * See {@link #getSelectedImageURL() getSelectedImageURL()} for more details
	 */
	public java.lang.String getSelectedImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.SELECTED_IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "selectedImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSelectedImageURLSetted() {
		return getStateHelper().get(Properties.SELECTED_IMAGE_URL)!=null;
	}

	public void setSelectedImageURL(java.lang.String selectedImageURL) {
		getStateHelper().put(Properties.SELECTED_IMAGE_URL, selectedImageURL);
	}

	public java.lang.String getImageURL() {
		return getImageURL(null);
	}

	/**
	 * See {@link #getImageURL() getImageURL()} for more details
	 */
	public java.lang.String getImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageURLSetted() {
		return getStateHelper().get(Properties.IMAGE_URL)!=null;
	}

	public void setImageURL(java.lang.String imageURL) {
		getStateHelper().put(Properties.IMAGE_URL, imageURL);
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
		return (Integer)getStateHelper().eval(Properties.IMAGE_HEIGHT, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageHeight" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageHeightSetted() {
		return getStateHelper().get(Properties.IMAGE_HEIGHT)!=null;
	}

	public void setImageHeight(int imageHeight) {
		getStateHelper().put(Properties.IMAGE_HEIGHT, imageHeight);
	}

	public int getImageWidth() {
		return getImageWidth(null);
	}

	/**
	 * See {@link #getImageWidth() getImageWidth()} for more details
	 */
	public int getImageWidth(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.IMAGE_WIDTH, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "imageWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isImageWidthSetted() {
		return getStateHelper().get(Properties.IMAGE_WIDTH)!=null;
	}

	public void setImageWidth(int imageWidth) {
		getStateHelper().put(Properties.IMAGE_WIDTH, imageWidth);
	}

	public java.lang.String getMenuPopupId() {
		return getMenuPopupId(null);
	}

	/**
	 * See {@link #getMenuPopupId() getMenuPopupId()} for more details
	 */
	public java.lang.String getMenuPopupId(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.MENU_POPUP_ID);
	}

	/**
	 * Returns <code>true</code> if the attribute "menuPopupId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMenuPopupIdSetted() {
		return getStateHelper().get(Properties.MENU_POPUP_ID)!=null;
	}

	public void setMenuPopupId(java.lang.String menuPopupId) {
		getStateHelper().put(Properties.MENU_POPUP_ID, menuPopupId);
	}

	public int getMaxWidth() {
		return getMaxWidth(null);
	}

	/**
	 * See {@link #getMaxWidth() getMaxWidth()} for more details
	 */
	public int getMaxWidth(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.MAX_WIDTH, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "maxWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMaxWidthSetted() {
		return getStateHelper().get(Properties.MAX_WIDTH)!=null;
	}

	public void setMaxWidth(int maxWidth) {
		getStateHelper().put(Properties.MAX_WIDTH, maxWidth);
	}

	public int getMinWidth() {
		return getMinWidth(null);
	}

	/**
	 * See {@link #getMinWidth() getMinWidth()} for more details
	 */
	public int getMinWidth(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.MIN_WIDTH, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "minWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMinWidthSetted() {
		return getStateHelper().get(Properties.MIN_WIDTH)!=null;
	}

	public void setMinWidth(int minWidth) {
		getStateHelper().put(Properties.MIN_WIDTH, minWidth);
	}

	public java.lang.String getWidth() {
		return getWidth(null);
	}

	/**
	 * See {@link #getWidth() getWidth()} for more details
	 */
	public java.lang.String getWidth(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.WIDTH);
	}

	/**
	 * Returns <code>true</code> if the attribute "width" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWidthSetted() {
		return getStateHelper().get(Properties.WIDTH)!=null;
	}

	public void setWidth(java.lang.String width) {
		getStateHelper().put(Properties.WIDTH, width);
	}

	public java.lang.String getVerticalAlignment() {
		return getVerticalAlignment(null);
	}

	/**
	 * See {@link #getVerticalAlignment() getVerticalAlignment()} for more details
	 */
	public java.lang.String getVerticalAlignment(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.VERTICAL_ALIGNMENT);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalAlignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalAlignmentSetted() {
		return getStateHelper().get(Properties.VERTICAL_ALIGNMENT)!=null;
	}

	public void setVerticalAlignment(java.lang.String verticalAlignment) {
		getStateHelper().put(Properties.VERTICAL_ALIGNMENT, verticalAlignment);
	}

	public boolean isAutoFilter() {
		return isAutoFilter(null);
	}

	/**
	 * See {@link #isAutoFilter() isAutoFilter()} for more details
	 */
	public boolean isAutoFilter(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.AUTO_FILTER, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "autoFilter" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAutoFilterSetted() {
		return getStateHelper().get(Properties.AUTO_FILTER)!=null;
	}

	public void setAutoFilter(boolean autoFilter) {
		getStateHelper().put(Properties.AUTO_FILTER, autoFilter);
	}

	public java.lang.String getCellImageURL() {
		return getCellImageURL(null);
	}

	/**
	 * See {@link #getCellImageURL() getCellImageURL()} for more details
	 */
	public java.lang.String getCellImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.CELL_IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "cellImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCellImageURLSetted() {
		return getStateHelper().get(Properties.CELL_IMAGE_URL)!=null;
	}

	public void setCellImageURL(java.lang.String cellImageURL) {
		getStateHelper().put(Properties.CELL_IMAGE_URL, cellImageURL);
	}

	public java.lang.String getDefaultCellImageURL() {
		return getDefaultCellImageURL(null);
	}

	/**
	 * See {@link #getDefaultCellImageURL() getDefaultCellImageURL()} for more details
	 */
	public java.lang.String getDefaultCellImageURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.DEFAULT_CELL_IMAGE_URL);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultCellImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDefaultCellImageURLSetted() {
		return getStateHelper().get(Properties.DEFAULT_CELL_IMAGE_URL)!=null;
	}

	public void setDefaultCellImageURL(java.lang.String defaultCellImageURL) {
		getStateHelper().put(Properties.DEFAULT_CELL_IMAGE_URL, defaultCellImageURL);
	}

	public java.lang.String getCellStyleClass() {
		return getCellStyleClass(null);
	}

	/**
	 * See {@link #getCellStyleClass() getCellStyleClass()} for more details
	 */
	public java.lang.String getCellStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.CELL_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "cellStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCellStyleClassSetted() {
		return getStateHelper().get(Properties.CELL_STYLE_CLASS)!=null;
	}

	public void setCellStyleClass(java.lang.String cellStyleClass) {
		getStateHelper().put(Properties.CELL_STYLE_CLASS, cellStyleClass);
	}

	public java.lang.String getDefaultCellStyleClass() {
		return getDefaultCellStyleClass(null);
	}

	/**
	 * See {@link #getDefaultCellStyleClass() getDefaultCellStyleClass()} for more details
	 */
	public java.lang.String getDefaultCellStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.DEFAULT_CELL_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultCellStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDefaultCellStyleClassSetted() {
		return getStateHelper().get(Properties.DEFAULT_CELL_STYLE_CLASS)!=null;
	}

	public void setDefaultCellStyleClass(java.lang.String defaultCellStyleClass) {
		getStateHelper().put(Properties.DEFAULT_CELL_STYLE_CLASS, defaultCellStyleClass);
	}

	public java.lang.String getCellDefaultToolTipText() {
		return getCellDefaultToolTipText(null);
	}

	/**
	 * See {@link #getCellDefaultToolTipText() getCellDefaultToolTipText()} for more details
	 */
	public java.lang.String getCellDefaultToolTipText(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.CELL_DEFAULT_TOOL_TIP_TEXT);
	}

	/**
	 * Returns <code>true</code> if the attribute "cellDefaultToolTipText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCellDefaultToolTipTextSetted() {
		return getStateHelper().get(Properties.CELL_DEFAULT_TOOL_TIP_TEXT)!=null;
	}

	public void setCellDefaultToolTipText(java.lang.String cellDefaultToolTipText) {
		getStateHelper().put(Properties.CELL_DEFAULT_TOOL_TIP_TEXT, cellDefaultToolTipText);
	}

	public java.lang.String getCellToolTipText() {
		return getCellToolTipText(null);
	}

	/**
	 * See {@link #getCellToolTipText() getCellToolTipText()} for more details
	 */
	public java.lang.String getCellToolTipText(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.CELL_TOOL_TIP_TEXT);
	}

	/**
	 * Returns <code>true</code> if the attribute "cellToolTipText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCellToolTipTextSetted() {
		return getStateHelper().get(Properties.CELL_TOOL_TIP_TEXT)!=null;
	}

	public void setCellToolTipText(java.lang.String cellToolTipText) {
		getStateHelper().put(Properties.CELL_TOOL_TIP_TEXT, cellToolTipText);
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

}
