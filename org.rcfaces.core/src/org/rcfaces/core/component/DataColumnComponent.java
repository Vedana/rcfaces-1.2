package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IOrderCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.component.capability.ISortComparatorCapability;
import org.rcfaces.core.internal.tools.ImageAccessorTools;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import java.util.Arrays;
import org.rcfaces.core.internal.component.CameliaColumnComponent;
import javax.faces.component.ValueHolder;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.capability.IImageCapability;
import org.rcfaces.core.component.capability.IResizableCapability;
import java.lang.String;
import org.rcfaces.core.component.capability.IAlignmentCapability;
import javax.faces.convert.Converter;
import org.rcfaces.core.component.capability.ISortEventCapability;
import org.rcfaces.core.internal.converter.OrderConverter;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.IToolTipCapability;
import java.util.Set;
import java.util.HashSet;

/**
 * Specify a column.
 */
public class DataColumnComponent extends CameliaColumnComponent implements 
	IVisibilityCapability,
	ITextCapability,
	IToolTipCapability,
	IAlignmentCapability,
	IForegroundBackgroundColorCapability,
	ISortEventCapability,
	IStyleClassCapability,
	IOrderCapability,
	ISortComparatorCapability,
	IResizableCapability,
	IImageCapability,
	ValueHolder {

	public static final String COMPONENT_TYPE="org.rcfaces.core.dataColumn";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaColumnComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"defaultCellImageURL","value","maxWidth","width","imageURL","toolTipText","alignment","ascending","hiddenMode","cellImageURL","foregroundColor","cellToolTipText","minWidth","styleClass","text","sortListener","resizable","verticalAlign","sortComparator","cellStyleClass","visible","backgroundColor","autoFilter"}));
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

	public final IContentAccessors getImageAccessors(FacesContext facesContext) {


			return ImageAccessorTools.createImageAccessors(facesContext, this, engine);
		
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

	public final int getHiddenMode() {
		return getHiddenMode(null);
	}

	/**
	 * See {@link #getHiddenMode() getHiddenMode()} for more details
	 */
	public final int getHiddenMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.HIDDEN_MODE,0, facesContext);
	}

	public final void setHiddenMode(int hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	/**
	 * See {@link #setHiddenMode(int) setHiddenMode(int)} for more details
	 */
	public final void setHiddenMode(ValueBinding hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public final boolean isVisible() {
		return isVisible(null);
	}

	/**
	 * See {@link #isVisible() isVisible()} for more details
	 */
	public final boolean isVisible(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.VISIBLE, true, facesContext);
	}

	public final void setVisible(boolean visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	/**
	 * See {@link #setVisible(boolean) setVisible(boolean)} for more details
	 */
	public final void setVisible(ValueBinding visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public final Boolean getVisibleState() {


			return getVisibleState(null);
		
	}

	public final java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public final java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT, facesContext);
	}

	public final void setText(java.lang.String text) {
		engine.setProperty(Properties.TEXT, text);
	}

	/**
	 * See {@link #setText(String) setText(String)} for more details
	 */
	public final void setText(ValueBinding text) {
		engine.setProperty(Properties.TEXT, text);
	}

	public final java.lang.String getToolTipText() {
		return getToolTipText(null);
	}

	/**
	 * See {@link #getToolTipText() getToolTipText()} for more details
	 */
	public final java.lang.String getToolTipText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TOOL_TIP_TEXT, facesContext);
	}

	public final void setToolTipText(java.lang.String toolTipText) {
		engine.setProperty(Properties.TOOL_TIP_TEXT, toolTipText);
	}

	/**
	 * See {@link #setToolTipText(String) setToolTipText(String)} for more details
	 */
	public final void setToolTipText(ValueBinding toolTipText) {
		engine.setProperty(Properties.TOOL_TIP_TEXT, toolTipText);
	}

	public final java.lang.String getAlignment() {
		return getAlignment(null);
	}

	/**
	 * See {@link #getAlignment() getAlignment()} for more details
	 */
	public final java.lang.String getAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ALIGNMENT, facesContext);
	}

	public final void setAlignment(java.lang.String alignment) {
		engine.setProperty(Properties.ALIGNMENT, alignment);
	}

	/**
	 * See {@link #setAlignment(String) setAlignment(String)} for more details
	 */
	public final void setAlignment(ValueBinding alignment) {
		engine.setProperty(Properties.ALIGNMENT, alignment);
	}

	public final java.lang.String getBackgroundColor() {
		return getBackgroundColor(null);
	}

	/**
	 * See {@link #getBackgroundColor() getBackgroundColor()} for more details
	 */
	public final java.lang.String getBackgroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_COLOR, facesContext);
	}

	public final void setBackgroundColor(java.lang.String backgroundColor) {
		engine.setProperty(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	/**
	 * See {@link #setBackgroundColor(String) setBackgroundColor(String)} for more details
	 */
	public final void setBackgroundColor(ValueBinding backgroundColor) {
		engine.setProperty(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	public final java.lang.String getForegroundColor() {
		return getForegroundColor(null);
	}

	/**
	 * See {@link #getForegroundColor() getForegroundColor()} for more details
	 */
	public final java.lang.String getForegroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOREGROUND_COLOR, facesContext);
	}

	public final void setForegroundColor(java.lang.String foregroundColor) {
		engine.setProperty(Properties.FOREGROUND_COLOR, foregroundColor);
	}

	/**
	 * See {@link #setForegroundColor(String) setForegroundColor(String)} for more details
	 */
	public final void setForegroundColor(ValueBinding foregroundColor) {
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

	public final java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	/**
	 * See {@link #getStyleClass() getStyleClass()} for more details
	 */
	public final java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STYLE_CLASS, facesContext);
	}

	public final void setStyleClass(java.lang.String styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	/**
	 * See {@link #setStyleClass(String) setStyleClass(String)} for more details
	 */
	public final void setStyleClass(ValueBinding styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	public final boolean isAscending() {
		return isAscending(null);
	}

	/**
	 * See {@link #isAscending() isAscending()} for more details
	 */
	public final boolean isAscending(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.ASCENDING, false, facesContext);
	}

	public final void setAscending(boolean ascending) {
		engine.setProperty(Properties.ASCENDING, ascending);
	}

	/**
	 * See {@link #setAscending(boolean) setAscending(boolean)} for more details
	 */
	public final void setAscending(ValueBinding ascending) {
		engine.setProperty(Properties.ASCENDING, ascending);
	}

	public final java.util.Comparator getSortComparator() {
		return getSortComparator(null);
	}

	/**
	 * See {@link #getSortComparator() getSortComparator()} for more details
	 */
	public final java.util.Comparator getSortComparator(javax.faces.context.FacesContext facesContext) {
		return (java.util.Comparator)engine.getProperty(Properties.SORT_COMPARATOR, facesContext);
	}

	public final void setSortComparator(java.util.Comparator sortComparator) {
		engine.setProperty(Properties.SORT_COMPARATOR, sortComparator);
	}

	/**
	 * See {@link #setSortComparator(java.util.Comparator) setSortComparator(java.util.Comparator)} for more details
	 */
	public final void setSortComparator(ValueBinding sortComparator) {
		engine.setProperty(Properties.SORT_COMPARATOR, sortComparator);
	}

	public final boolean isResizable() {
		return isResizable(null);
	}

	/**
	 * See {@link #isResizable() isResizable()} for more details
	 */
	public final boolean isResizable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.RESIZABLE, false, facesContext);
	}

	public final void setResizable(boolean resizable) {
		engine.setProperty(Properties.RESIZABLE, resizable);
	}

	/**
	 * See {@link #setResizable(boolean) setResizable(boolean)} for more details
	 */
	public final void setResizable(ValueBinding resizable) {
		engine.setProperty(Properties.RESIZABLE, resizable);
	}

	public final java.lang.String getImageURL() {
		return getImageURL(null);
	}

	/**
	 * See {@link #getImageURL() getImageURL()} for more details
	 */
	public final java.lang.String getImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.IMAGE_URL, facesContext);
	}

	public final void setImageURL(java.lang.String imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
	}

	/**
	 * See {@link #setImageURL(String) setImageURL(String)} for more details
	 */
	public final void setImageURL(ValueBinding imageURL) {
		engine.setProperty(Properties.IMAGE_URL, imageURL);
	}

	public final IContentAccessors getImageAccessors() {


			return getImageAccessors(null);
		
	}

	/**
	 * Returns a string value (as specified by CSS) for the width of the component.
	 * @return width
	 */
	public final String getWidth() {
		return getWidth(null);
	}

	/**
	 * Returns a string value (as specified by CSS) for the width of the component.
	 * @return width
	 */
	public final String getWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WIDTH, facesContext);
	}

	/**
	 * Sets a string value (as specified by CSS) for the width of the component.
	 * @param width width
	 */
	public final void setWidth(String width) {
		engine.setProperty(Properties.WIDTH, width);
	}

	/**
	 * Sets a string value (as specified by CSS) for the width of the component.
	 * @param width width
	 */
	public final void setWidth(ValueBinding width) {
		engine.setProperty(Properties.WIDTH, width);
	}

	/**
	 * Returns <code>true</code> if the attribute "width" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isWidthSetted() {
		return engine.isPropertySetted(Properties.WIDTH);
	}

	/**
	 * Returns an int value specifying the maximum width in pixels (if resizeable).
	 * @return max width
	 */
	public final int getMaxWidth() {
		return getMaxWidth(null);
	}

	/**
	 * Returns an int value specifying the maximum width in pixels (if resizeable).
	 * @return max width
	 */
	public final int getMaxWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.MAX_WIDTH, 0, facesContext);
	}

	/**
	 * Sets an int value specifying the maximum width in pixels (if resizeable).
	 * @param maxWidth max width
	 */
	public final void setMaxWidth(int maxWidth) {
		engine.setProperty(Properties.MAX_WIDTH, maxWidth);
	}

	/**
	 * Sets an int value specifying the maximum width in pixels (if resizeable).
	 * @param maxWidth max width
	 */
	public final void setMaxWidth(ValueBinding maxWidth) {
		engine.setProperty(Properties.MAX_WIDTH, maxWidth);
	}

	/**
	 * Returns <code>true</code> if the attribute "maxWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMaxWidthSetted() {
		return engine.isPropertySetted(Properties.MAX_WIDTH);
	}

	/**
	 * Returns an int value specifying the minimum width in pixels (if resizeable).
	 * @return min width
	 */
	public final int getMinWidth() {
		return getMinWidth(null);
	}

	/**
	 * Returns an int value specifying the minimum width in pixels (if resizeable).
	 * @return min width
	 */
	public final int getMinWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.MIN_WIDTH, 0, facesContext);
	}

	/**
	 * Sets an int value specifying the minimum width in pixels (if resizeable).
	 * @param minWidth min width
	 */
	public final void setMinWidth(int minWidth) {
		engine.setProperty(Properties.MIN_WIDTH, minWidth);
	}

	/**
	 * Sets an int value specifying the minimum width in pixels (if resizeable).
	 * @param minWidth min width
	 */
	public final void setMinWidth(ValueBinding minWidth) {
		engine.setProperty(Properties.MIN_WIDTH, minWidth);
	}

	/**
	 * Returns <code>true</code> if the attribute "minWidth" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMinWidthSetted() {
		return engine.isPropertySetted(Properties.MIN_WIDTH);
	}

	/**
	 * Returns a string that represent the vertical alignement of the data in the component.
	 * @return vertical alignement : top|center|bottom
	 */
	public final String getVerticalAlign() {
		return getVerticalAlign(null);
	}

	/**
	 * Returns a string that represent the vertical alignement of the data in the component.
	 * @return vertical alignement : top|center|bottom
	 */
	public final String getVerticalAlign(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.VERTICAL_ALIGN, facesContext);
	}

	/**
	 * Sets a string that represent the vertical alignement of the data in the component.
	 * @param verticalAlign vertical alignement : top|center|bottom
	 */
	public final void setVerticalAlign(String verticalAlign) {
		engine.setProperty(Properties.VERTICAL_ALIGN, verticalAlign);
	}

	/**
	 * Sets a string that represent the vertical alignement of the data in the component.
	 * @param verticalAlign vertical alignement : top|center|bottom
	 */
	public final void setVerticalAlign(ValueBinding verticalAlign) {
		engine.setProperty(Properties.VERTICAL_ALIGN, verticalAlign);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalAlign" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalAlignSetted() {
		return engine.isPropertySetted(Properties.VERTICAL_ALIGN);
	}

	/**
	 * Returns an url string pointing to the default image.
	 * @return image url
	 */
	public final String getDefaultCellImageURL() {
		return getDefaultCellImageURL(null);
	}

	/**
	 * Returns an url string pointing to the default image.
	 * @return image url
	 */
	public final String getDefaultCellImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_CELL_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the default image.
	 * @param defaultCellImageURL image url
	 */
	public final void setDefaultCellImageURL(String defaultCellImageURL) {
		engine.setProperty(Properties.DEFAULT_CELL_IMAGE_URL, defaultCellImageURL);
	}

	/**
	 * Sets an url string pointing to the default image.
	 * @param defaultCellImageURL image url
	 */
	public final void setDefaultCellImageURL(ValueBinding defaultCellImageURL) {
		engine.setProperty(Properties.DEFAULT_CELL_IMAGE_URL, defaultCellImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "defaultCellImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDefaultCellImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_CELL_IMAGE_URL);
	}

	/**
	 * Returns an url string pointing to the image.
	 * @return image url
	 */
	public final String getCellImageURL() {
		return getCellImageURL(null);
	}

	/**
	 * Returns an url string pointing to the image.
	 * @return image url
	 */
	public final String getCellImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CELL_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the image.
	 * @param cellImageURL image url for the cell
	 */
	public final void setCellImageURL(String cellImageURL) {
		engine.setProperty(Properties.CELL_IMAGE_URL, cellImageURL);
	}

	/**
	 * Sets an url string pointing to the image.
	 * @param cellImageURL image url for the cell
	 */
	public final void setCellImageURL(ValueBinding cellImageURL) {
		engine.setProperty(Properties.CELL_IMAGE_URL, cellImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "cellImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCellImageURLSetted() {
		return engine.isPropertySetted(Properties.CELL_IMAGE_URL);
	}

	/**
	 * Returns a space-separated list of CSS style class(es) to be applied when this cell is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @return list of CSS style classes
	 */
	public final String getCellStyleClass() {
		return getCellStyleClass(null);
	}

	/**
	 * Returns a space-separated list of CSS style class(es) to be applied when this cell is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @return list of CSS style classes
	 */
	public final String getCellStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CELL_STYLE_CLASS, facesContext);
	}

	/**
	 * Sets a space-separated list of CSS style class(es) to be applied when this cell is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @param cellStyleClass list of CSS style classes
	 */
	public final void setCellStyleClass(String cellStyleClass) {
		engine.setProperty(Properties.CELL_STYLE_CLASS, cellStyleClass);
	}

	/**
	 * Sets a space-separated list of CSS style class(es) to be applied when this cell is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @param cellStyleClass list of CSS style classes
	 */
	public final void setCellStyleClass(ValueBinding cellStyleClass) {
		engine.setProperty(Properties.CELL_STYLE_CLASS, cellStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "cellStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCellStyleClassSetted() {
		return engine.isPropertySetted(Properties.CELL_STYLE_CLASS);
	}

	/**
	 * Returns a string value containing the text that will appear when the pointer hover the component.
	 * @return tool tip text
	 */
	public final String getCellToolTipText() {
		return getCellToolTipText(null);
	}

	/**
	 * Returns a string value containing the text that will appear when the pointer hover the component.
	 * @return tool tip text
	 */
	public final String getCellToolTipText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CELL_TOOL_TIP_TEXT, facesContext);
	}

	/**
	 * Sets a string value containing the text that will appear when the pointer hover the component.
	 * @param cellToolTipText tool tip text
	 */
	public final void setCellToolTipText(String cellToolTipText) {
		engine.setProperty(Properties.CELL_TOOL_TIP_TEXT, cellToolTipText);
	}

	/**
	 * Sets a string value containing the text that will appear when the pointer hover the component.
	 * @param cellToolTipText tool tip text
	 */
	public final void setCellToolTipText(ValueBinding cellToolTipText) {
		engine.setProperty(Properties.CELL_TOOL_TIP_TEXT, cellToolTipText);
	}

	/**
	 * Returns <code>true</code> if the attribute "cellToolTipText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCellToolTipTextSetted() {
		return engine.isPropertySetted(Properties.CELL_TOOL_TIP_TEXT);
	}

	/**
	 * Returns a boolean value indicating if the component should apply filter automatically.
	 * @return true if the component should apply filter
	 */
	public final boolean isAutoFilter() {
		return isAutoFilter(null);
	}

	/**
	 * Returns a boolean value indicating if the component should apply filter automatically.
	 * @return true if the component should apply filter
	 */
	public final boolean isAutoFilter(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.AUTO_FILTER, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating if the component should apply filter automatically.
	 * @param autoFilter true if the component should apply filter
	 */
	public final void setAutoFilter(boolean autoFilter) {
		engine.setProperty(Properties.AUTO_FILTER, autoFilter);
	}

	/**
	 * Sets a boolean value indicating if the component should apply filter automatically.
	 * @param autoFilter true if the component should apply filter
	 */
	public final void setAutoFilter(ValueBinding autoFilter) {
		engine.setProperty(Properties.AUTO_FILTER, autoFilter);
	}

	/**
	 * Returns <code>true</code> if the attribute "autoFilter" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAutoFilterSetted() {
		return engine.isPropertySetted(Properties.AUTO_FILTER);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
