package org.rcfaces.core.component;

import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IAlignmentCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.IOrderCapability;
import org.rcfaces.core.component.capability.IResizableCapability;
import org.rcfaces.core.component.capability.ISortComparatorCapability;
import org.rcfaces.core.component.capability.ISortEventCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.IToolTipCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.CameliaColumnComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import org.rcfaces.core.internal.converter.OrderConverter;

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
	ValueHolder {

	public static final String COMPONENT_TYPE="org.rcfaces.core.dataColumn";


	public DataColumnComponent() {
		setRendererType(null);
	}

	public DataColumnComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final Converter getConverter() {


		return getConverter(null);
		
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


		engine.setConverter(converter);
		
	}

	public final void setConverter(String converterId) {


		engine.setConverterId(converterId);
		
	}

	public final void setConverter(ValueBinding converter) {


			engine.setConverterId(converter);
		
	}

	public final Object getValue(FacesContext context) {


				return engine.getValue(Properties.VALUE, context);
			
	}

	public final void setValue(ValueBinding value) {


				engine.setValueBinding(Properties.VALUE, value);
			
	}

	public final void setHiddenMode(String hiddenMode) {


			setHiddenMode(((Integer)HiddenModeConverter.SINGLETON.getAsObject(null, null, hiddenMode)).intValue());
		
	}

	public final void setAscending(String order) {


			setAscending(((Boolean)OrderConverter.SINGLETON.getAsObject(null, null, order)).booleanValue());
		
	}

	public final java.lang.Boolean getVisible() {
		return getVisible(null);
	}

	public final java.lang.Boolean getVisible(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.VISIBLE, facesContext);
	}

	public final void setVisible(java.lang.Boolean visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public final void setVisible(ValueBinding visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public final int getHiddenMode() {
		return getHiddenMode(null);
	}

	public final int getHiddenMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.HIDDEN_MODE,0, facesContext);
	}

	public final void setHiddenMode(int hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public final void setHiddenMode(ValueBinding hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public final java.lang.String getText() {
		return getText(null);
	}

	public final java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT, facesContext);
	}

	public final void setText(java.lang.String text) {
		engine.setProperty(Properties.TEXT, text);
	}

	public final void setText(ValueBinding text) {
		engine.setProperty(Properties.TEXT, text);
	}

	public final java.lang.String getToolTipText() {
		return getToolTipText(null);
	}

	public final java.lang.String getToolTipText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TOOL_TIP_TEXT, facesContext);
	}

	public final void setToolTipText(java.lang.String toolTipText) {
		engine.setProperty(Properties.TOOL_TIP_TEXT, toolTipText);
	}

	public final void setToolTipText(ValueBinding toolTipText) {
		engine.setProperty(Properties.TOOL_TIP_TEXT, toolTipText);
	}

	public final java.lang.String getAlignment() {
		return getAlignment(null);
	}

	public final java.lang.String getAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ALIGNMENT, facesContext);
	}

	public final void setAlignment(java.lang.String alignment) {
		engine.setProperty(Properties.ALIGNMENT, alignment);
	}

	public final void setAlignment(ValueBinding alignment) {
		engine.setProperty(Properties.ALIGNMENT, alignment);
	}

	public final java.lang.String getForegroundColor() {
		return getForegroundColor(null);
	}

	public final java.lang.String getForegroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOREGROUND_COLOR, facesContext);
	}

	public final void setForegroundColor(java.lang.String foregroundColor) {
		engine.setProperty(Properties.FOREGROUND_COLOR, foregroundColor);
	}

	public final void setForegroundColor(ValueBinding foregroundColor) {
		engine.setProperty(Properties.FOREGROUND_COLOR, foregroundColor);
	}

	public final java.lang.String getBackgroundColor() {
		return getBackgroundColor(null);
	}

	public final java.lang.String getBackgroundColor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BACKGROUND_COLOR, facesContext);
	}

	public final void setBackgroundColor(java.lang.String backgroundColor) {
		engine.setProperty(Properties.BACKGROUND_COLOR, backgroundColor);
	}

	public final void setBackgroundColor(ValueBinding backgroundColor) {
		engine.setProperty(Properties.BACKGROUND_COLOR, backgroundColor);
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

	public final java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STYLE_CLASS, facesContext);
	}

	public final void setStyleClass(java.lang.String styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	public final void setStyleClass(ValueBinding styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	public final boolean isAscending() {
		return isAscending(null);
	}

	public final boolean isAscending(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.ASCENDING, false, facesContext);
	}

	public final void setAscending(boolean ascending) {
		engine.setProperty(Properties.ASCENDING, ascending);
	}

	public final void setAscending(ValueBinding ascending) {
		engine.setProperty(Properties.ASCENDING, ascending);
	}

	public final java.util.Comparator getSortComparator() {
		return getSortComparator(null);
	}

	public final java.util.Comparator getSortComparator(javax.faces.context.FacesContext facesContext) {
		return (java.util.Comparator)engine.getProperty(Properties.SORT_COMPARATOR, facesContext);
	}

	public final void setSortComparator(java.util.Comparator sortComparator) {
		engine.setProperty(Properties.SORT_COMPARATOR, sortComparator);
	}

	public final void setSortComparator(ValueBinding sortComparator) {
		engine.setProperty(Properties.SORT_COMPARATOR, sortComparator);
	}

	public final boolean isResizable() {
		return isResizable(null);
	}

	public final boolean isResizable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.RESIZABLE, false, facesContext);
	}

	public final void setResizable(boolean resizable) {
		engine.setProperty(Properties.RESIZABLE, resizable);
	}

	public final void setResizable(ValueBinding resizable) {
		engine.setProperty(Properties.RESIZABLE, resizable);
	}

	public final String getWidth() {
		return getWidth(null);
	}

	public final String getWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WIDTH, facesContext);
	}

	public final void setWidth(String width) {
		engine.setProperty(Properties.WIDTH, width);
	}

	public final void setWidth(ValueBinding width) {
		engine.setProperty(Properties.WIDTH, width);
	}

	public final boolean isWidthSetted() {
		return engine.isPropertySetted(Properties.WIDTH);
	}

	public final int getMaxWidth() {
		return getMaxWidth(null);
	}

	public final int getMaxWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.MAX_WIDTH, 0, facesContext);
	}

	public final void setMaxWidth(int maxWidth) {
		engine.setProperty(Properties.MAX_WIDTH, maxWidth);
	}

	public final void setMaxWidth(ValueBinding maxWidth) {
		engine.setProperty(Properties.MAX_WIDTH, maxWidth);
	}

	public final boolean isMaxWidthSetted() {
		return engine.isPropertySetted(Properties.MAX_WIDTH);
	}

	public final int getMinWidth() {
		return getMinWidth(null);
	}

	public final int getMinWidth(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.MIN_WIDTH, 0, facesContext);
	}

	public final void setMinWidth(int minWidth) {
		engine.setProperty(Properties.MIN_WIDTH, minWidth);
	}

	public final void setMinWidth(ValueBinding minWidth) {
		engine.setProperty(Properties.MIN_WIDTH, minWidth);
	}

	public final boolean isMinWidthSetted() {
		return engine.isPropertySetted(Properties.MIN_WIDTH);
	}

	public final String getVerticalAlign() {
		return getVerticalAlign(null);
	}

	public final String getVerticalAlign(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.VERTICAL_ALIGN, facesContext);
	}

	public final void setVerticalAlign(String verticalAlign) {
		engine.setProperty(Properties.VERTICAL_ALIGN, verticalAlign);
	}

	public final void setVerticalAlign(ValueBinding verticalAlign) {
		engine.setProperty(Properties.VERTICAL_ALIGN, verticalAlign);
	}

	public final boolean isVerticalAlignSetted() {
		return engine.isPropertySetted(Properties.VERTICAL_ALIGN);
	}

	public final String getDefaultCellImageURL() {
		return getDefaultCellImageURL(null);
	}

	public final String getDefaultCellImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DEFAULT_CELL_IMAGE_URL, facesContext);
	}

	public final void setDefaultCellImageURL(String defaultCellImageURL) {
		engine.setProperty(Properties.DEFAULT_CELL_IMAGE_URL, defaultCellImageURL);
	}

	public final void setDefaultCellImageURL(ValueBinding defaultCellImageURL) {
		engine.setProperty(Properties.DEFAULT_CELL_IMAGE_URL, defaultCellImageURL);
	}

	public final boolean isDefaultCellImageURLSetted() {
		return engine.isPropertySetted(Properties.DEFAULT_CELL_IMAGE_URL);
	}

	public final String getCellImageURL() {
		return getCellImageURL(null);
	}

	public final String getCellImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CELL_IMAGE_URL, facesContext);
	}

	public final void setCellImageURL(String cellImageURL) {
		engine.setProperty(Properties.CELL_IMAGE_URL, cellImageURL);
	}

	public final void setCellImageURL(ValueBinding cellImageURL) {
		engine.setProperty(Properties.CELL_IMAGE_URL, cellImageURL);
	}

	public final boolean isCellImageURLSetted() {
		return engine.isPropertySetted(Properties.CELL_IMAGE_URL);
	}

	public final String getCellStyleClass() {
		return getCellStyleClass(null);
	}

	public final String getCellStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CELL_STYLE_CLASS, facesContext);
	}

	public final void setCellStyleClass(String cellStyleClass) {
		engine.setProperty(Properties.CELL_STYLE_CLASS, cellStyleClass);
	}

	public final void setCellStyleClass(ValueBinding cellStyleClass) {
		engine.setProperty(Properties.CELL_STYLE_CLASS, cellStyleClass);
	}

	public final boolean isCellStyleClassSetted() {
		return engine.isPropertySetted(Properties.CELL_STYLE_CLASS);
	}

	public final boolean isAutoFilter() {
		return isAutoFilter(null);
	}

	public final boolean isAutoFilter(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.AUTO_FILTER, false, facesContext);
	}

	public final void setAutoFilter(boolean autoFilter) {
		engine.setProperty(Properties.AUTO_FILTER, autoFilter);
	}

	public final void setAutoFilter(ValueBinding autoFilter) {
		engine.setProperty(Properties.AUTO_FILTER, autoFilter);
	}

	public final boolean isAutoFilterSetted() {
		return engine.isPropertySetted(Properties.AUTO_FILTER);
	}

	public void release() {
		super.release();
	}
}
