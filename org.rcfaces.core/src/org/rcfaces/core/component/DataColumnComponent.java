package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IAlignmentCapability;
import org.rcfaces.core.component.capability.IAutoFilterCapability;
import org.rcfaces.core.component.capability.ICellImageCapability;
import org.rcfaces.core.component.capability.ICellStyleClassCapability;
import org.rcfaces.core.component.capability.ICellToolTipTextCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import org.rcfaces.core.component.capability.IImageSizeCapability;
import org.rcfaces.core.component.capability.IMenuPopupIdCapability;
import org.rcfaces.core.component.capability.IOrderCapability;
import org.rcfaces.core.component.capability.IResizableCapability;
import org.rcfaces.core.component.capability.ISortComparatorCapability;
import org.rcfaces.core.component.capability.ISortEventCapability;
import org.rcfaces.core.component.capability.IStatesImageCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.IToolTipCapability;
import org.rcfaces.core.component.capability.IVerticalAlignmentCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.component.capability.IWidthRangeCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.internal.capability.ICellImageSettings;
import org.rcfaces.core.internal.capability.ICellStyleClassSettings;
import org.rcfaces.core.internal.capability.ICellToolTipTextSettings;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import org.rcfaces.core.internal.component.CameliaValueColumnComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import org.rcfaces.core.internal.converter.OrderConverter;
import org.rcfaces.core.internal.tools.ImageAccessorTools;

/**
 * Specify a column.
 */
public class DataColumnComponent extends CameliaValueColumnComponent implements 
	IVisibilityCapability,
	IHiddenModeCapability,
	ITextCapability,
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
	ICellToolTipTextSettings,
	ICellImageSettings,
	IImageAccessorsCapability,
	ICellStyleClassSettings,
	ValueHolder {

	public static final String COMPONENT_TYPE="org.rcfaces.core.dataColumn";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaValueColumnComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"defaultCellImageURL","imageURL","width","verticalAlignment","defaultCellStyleClass","alignment","hiddenMode","cellImageURL","foregroundColor","menuPopupId","styleClass","sortListener","sortComparator","selectedImageURL","value","hoverImageURL","imageHeight","maxWidth","disabledImageURL","ascending","toolTipText","cellToolTipText","minWidth","resizable","text","imageWidth","cellStyleClass","cellDefaultToolTipText","visible","backgroundColor","autoFilter"}));
	}

	public DataColumnComponent() {
		setRendererType(null);
	}

	public DataColumnComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final Converter getConverter() {


        	return (Converter)engine.getProperty("converter", null);
		
	}

	public final Object getValue() {


				return getValue(null);
			
	}

	public final void setValue(Object value) {


				if (value instanceof javax.faces.el.ValueBinding) {
					setValue((javax.faces.el.ValueBinding)value);
					return;
				}
				engine.setValue(Properties.VALUE, value);
			
	}

	public final Object getLocalValue() {


				return engine.getLocalValue(Properties.VALUE);
			
	}

	public final void setConverter(Converter converter) {


        	engine.setProperty("converter", converter);
		
	}

	public final IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, engine);
		
	}

	public final void setConverter(String converterId) {


			 setConverter(null, converterId);
		
	}

	public final void setConverter(FacesContext facesContext, String converterId) {


			if (facesContext==null) {
				facesContext=FacesContext.getCurrentInstance();
			}
			Converter converter = facesContext.getApplication().createConverter(converterId);
            this.setConverter(converter);
		
	}

	public final void setConverter(ValueBinding valueBinding) {


              this.setValueBinding("converter", valueBinding);
		
	}

	public final Boolean getVisibleState(FacesContext facesContext) {


			if (engine.isPropertySetted(Properties.VISIBLE)==false) {
				return null;
			}
			
			return Boolean.valueOf(isVisible(facesContext));
		
	}

	public final Object getValue(FacesContext context) {


				return engine.getValue(Properties.VALUE, context);
			
	}

	public final void setValue(ValueBinding value) {


				engine.setValueBinding(Properties.VALUE, value);
			
	}

	public final void setHiddenMode(String hiddenMode) {


			setHiddenMode(((Integer)HiddenModeConverter.SINGLETON.getAsObject(null, this, hiddenMode)).intValue());
		
	}

	public final void setAscending(String order) {


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

	/**
	 * See {@link #setVisible(boolean) setVisible(boolean)} for more details
	 */
	public void setVisible(ValueBinding visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public final Boolean getVisibleState() {


			return getVisibleState(null);
		
	}

	public int getHiddenMode() {
		return getHiddenMode(null);
	}

	/**
	 * See {@link #getHiddenMode() getHiddenMode()} for more details
	 */
	public int getHiddenMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.HIDDEN_MODE,0, facesContext);
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

	/**
	 * See {@link #setHiddenMode(int) setHiddenMode(int)} for more details
	 */
	public void setHiddenMode(ValueBinding hiddenMode) {
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

	/**
	 * See {@link #setText(String) setText(String)} for more details
	 */
	public void setText(ValueBinding text) {
		engine.setProperty(Properties.TEXT, text);
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

	/**
	 * See {@link #setToolTipText(String) setToolTipText(String)} for more details
	 */
	public void setToolTipText(ValueBinding toolTipText) {
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

	/**
	 * See {@link #setAlignment(String) setAlignment(String)} for more details
	 */
	public void setAlignment(ValueBinding alignment) {
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

	/**
	 * See {@link #setBackgroundColor(String) setBackgroundColor(String)} for more details
	 */
	public void setBackgroundColor(ValueBinding backgroundColor) {
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

	/**
	 * See {@link #setForegroundColor(String) setForegroundColor(String)} for more details
	 */
	public void setForegroundColor(ValueBinding foregroundColor) {
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

	/**
	 * See {@link #setStyleClass(String) setStyleClass(String)} for more details
	 */
	public void setStyleClass(ValueBinding styleClass) {
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

	/**
	 * See {@link #setAscending(boolean) setAscending(boolean)} for more details
	 */
	public void setAscending(ValueBinding ascending) {
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

	/**
	 * See {@link #setSortComparator(java.util.Comparator) setSortComparator(java.util.Comparator)} for more details
	 */
	public void setSortComparator(ValueBinding sortComparator) {
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

	/**
	 * See {@link #setResizable(boolean) setResizable(boolean)} for more details
	 */
	public void setResizable(ValueBinding resizable) {
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

	/**
	 * See {@link #setDisabledImageURL(String) setDisabledImageURL(String)} for more details
	 */
	public void setDisabledImageURL(ValueBinding disabledImageURL) {
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

	/**
	 * See {@link #setHoverImageURL(String) setHoverImageURL(String)} for more details
	 */
	public void setHoverImageURL(ValueBinding hoverImageURL) {
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

	/**
	 * See {@link #setSelectedImageURL(String) setSelectedImageURL(String)} for more details
	 */
	public void setSelectedImageURL(ValueBinding selectedImageURL) {
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

	/**
	 * See {@link #setImageURL(String) setImageURL(String)} for more details
	 */
	public void setImageURL(ValueBinding imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
	}

	public final IContentAccessors getImageAccessors() {


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

	/**
	 * See {@link #setImageHeight(int) setImageHeight(int)} for more details
	 */
	public void setImageHeight(ValueBinding imageHeight) {
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

	/**
	 * See {@link #setImageWidth(int) setImageWidth(int)} for more details
	 */
	public void setImageWidth(ValueBinding imageWidth) {
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

	/**
	 * See {@link #setMenuPopupId(String) setMenuPopupId(String)} for more details
	 */
	public void setMenuPopupId(ValueBinding menuPopupId) {
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

	/**
	 * See {@link #setMaxWidth(int) setMaxWidth(int)} for more details
	 */
	public void setMaxWidth(ValueBinding maxWidth) {
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

	/**
	 * See {@link #setMinWidth(int) setMinWidth(int)} for more details
	 */
	public void setMinWidth(ValueBinding minWidth) {
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

	/**
	 * See {@link #setWidth(String) setWidth(String)} for more details
	 */
	public void setWidth(ValueBinding width) {
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

	/**
	 * See {@link #setVerticalAlignment(String) setVerticalAlignment(String)} for more details
	 */
	public void setVerticalAlignment(ValueBinding verticalAlignment) {
		engine.setProperty(Properties.VERTICAL_ALIGNMENT, verticalAlignment);
	}

	public boolean isAutoFilter() {
		return isAutoFilter(null);
	}

	/**
	 * See {@link #isAutoFilter() isAutoFilter()} for more details
	 */
	public boolean isAutoFilter(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.AUTO_FILTER, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "autoFilter" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAutoFilterSetted() {
		return engine.isPropertySetted(Properties.AUTO_FILTER);
	}

	public void setAutoFilter(boolean autoFilter) {
		engine.setProperty(Properties.AUTO_FILTER, autoFilter);
	}

	/**
	 * See {@link #setAutoFilter(boolean) setAutoFilter(boolean)} for more details
	 */
	public void setAutoFilter(ValueBinding autoFilter) {
		engine.setProperty(Properties.AUTO_FILTER, autoFilter);
	}

	public java.lang.String getCellImageURL() {
		return getCellImageURL(null);
	}

	/**
	 * See {@link #getCellImageURL() getCellImageURL()} for more details
	 */
	public java.lang.String getCellImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CELL_IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "cellImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCellImageURLSetted() {
		return engine.isPropertySetted(Properties.CELL_IMAGE_URL);
	}

	public void setCellImageURL(java.lang.String cellImageURL) {
		engine.setProperty(Properties.CELL_IMAGE_URL, cellImageURL);
	}

	/**
	 * See {@link #setCellImageURL(String) setCellImageURL(String)} for more details
	 */
	public void setCellImageURL(ValueBinding cellImageURL) {
		engine.setProperty(Properties.CELL_IMAGE_URL, cellImageURL);
	}

	public java.lang.String getDefaultCellImageURL() {
		return getDefaultCellImageURL(null);
	}

	/**
	 * See {@link #getDefaultCellImageURL() getDefaultCellImageURL()} for more details
	 */
	public java.lang.String getDefaultCellImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_CELL_IMAGE_URL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultCellImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDefaultCellImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_CELL_IMAGE_URL);
	}

	public void setDefaultCellImageURL(java.lang.String defaultCellImageURL) {
		engine.setProperty(Properties.DEFAULT_CELL_IMAGE_URL, defaultCellImageURL);
	}

	/**
	 * See {@link #setDefaultCellImageURL(String) setDefaultCellImageURL(String)} for more details
	 */
	public void setDefaultCellImageURL(ValueBinding defaultCellImageURL) {
		engine.setProperty(Properties.DEFAULT_CELL_IMAGE_URL, defaultCellImageURL);
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

	/**
	 * See {@link #setCellStyleClass(String) setCellStyleClass(String)} for more details
	 */
	public void setCellStyleClass(ValueBinding cellStyleClass) {
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

	/**
	 * See {@link #setDefaultCellStyleClass(String) setDefaultCellStyleClass(String)} for more details
	 */
	public void setDefaultCellStyleClass(ValueBinding defaultCellStyleClass) {
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

	/**
	 * See {@link #setCellDefaultToolTipText(String) setCellDefaultToolTipText(String)} for more details
	 */
	public void setCellDefaultToolTipText(ValueBinding cellDefaultToolTipText) {
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

	/**
	 * See {@link #setCellToolTipText(String) setCellToolTipText(String)} for more details
	 */
	public void setCellToolTipText(ValueBinding cellToolTipText) {
		engine.setProperty(Properties.CELL_TOOL_TIP_TEXT, cellToolTipText);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
