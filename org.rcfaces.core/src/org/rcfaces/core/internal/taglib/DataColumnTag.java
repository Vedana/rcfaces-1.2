package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.component.DataColumnComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class DataColumnTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(DataColumnTag.class);

	private String hiddenMode;
	private String visible;
	private String text;
	private String toolTipText;
	private String alignment;
	private String backgroundColor;
	private String foregroundColor;
	private String sortListeners;
	private String styleClass;
	private String ascending;
	private String sortComparator;
	private String resizable;
	private String width;
	private String maxWidth;
	private String minWidth;
	private String verticalAlign;
	private String value;
	private String defaultCellImageURL;
	private String cellImageURL;
	private String cellStyleClass;
	private String cellToolTipText;
	private String autoFilter;
	private String converter;

	public String getComponentType() {
		return DataColumnComponent.COMPONENT_TYPE;
	}

	public final String getHiddenMode() {
		return hiddenMode;
	}

	public final void setHiddenMode(String hiddenMode) {
		this.hiddenMode = hiddenMode;
	}

	public final String getVisible() {
		return visible;
	}

	public final void setVisible(String visible) {
		this.visible = visible;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getToolTipText() {
		return toolTipText;
	}

	public final void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}

	public final String getAlignment() {
		return alignment;
	}

	public final void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public final String getBackgroundColor() {
		return backgroundColor;
	}

	public final void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public final String getForegroundColor() {
		return foregroundColor;
	}

	public final void setForegroundColor(String foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public final String getSortListener() {
		return sortListeners;
	}

	public final void setSortListener(String sortListeners) {
		this.sortListeners = sortListeners;
	}

	public final String getStyleClass() {
		return styleClass;
	}

	public final void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public final String getAscending() {
		return ascending;
	}

	public final void setAscending(String ascending) {
		this.ascending = ascending;
	}

	public final String getSortComparator() {
		return sortComparator;
	}

	public final void setSortComparator(String sortComparator) {
		this.sortComparator = sortComparator;
	}

	public final String getResizable() {
		return resizable;
	}

	public final void setResizable(String resizable) {
		this.resizable = resizable;
	}

	public final String getWidth() {
		return width;
	}

	public final void setWidth(String width) {
		this.width = width;
	}

	public final String getMaxWidth() {
		return maxWidth;
	}

	public final void setMaxWidth(String maxWidth) {
		this.maxWidth = maxWidth;
	}

	public final String getMinWidth() {
		return minWidth;
	}

	public final void setMinWidth(String minWidth) {
		this.minWidth = minWidth;
	}

	public final String getVerticalAlign() {
		return verticalAlign;
	}

	public final void setVerticalAlign(String verticalAlign) {
		this.verticalAlign = verticalAlign;
	}

	public final String getValue() {
		return value;
	}

	public final void setValue(String value) {
		this.value = value;
	}

	public final String getDefaultCellImageURL() {
		return defaultCellImageURL;
	}

	public final void setDefaultCellImageURL(String defaultCellImageURL) {
		this.defaultCellImageURL = defaultCellImageURL;
	}

	public final String getCellImageURL() {
		return cellImageURL;
	}

	public final void setCellImageURL(String cellImageURL) {
		this.cellImageURL = cellImageURL;
	}

	public final String getCellStyleClass() {
		return cellStyleClass;
	}

	public final void setCellStyleClass(String cellStyleClass) {
		this.cellStyleClass = cellStyleClass;
	}

	public final String getCellToolTipText() {
		return cellToolTipText;
	}

	public final void setCellToolTipText(String cellToolTipText) {
		this.cellToolTipText = cellToolTipText;
	}

	public final String getAutoFilter() {
		return autoFilter;
	}

	public final void setAutoFilter(String autoFilter) {
		this.autoFilter = autoFilter;
	}

	public final void setConverter(String converter) {
		this.converter=converter;
	}

	public final String getConverter() {
		return converter;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (DataColumnComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  text='"+text+"'");
			LOG.debug("  toolTipText='"+toolTipText+"'");
			LOG.debug("  alignment='"+alignment+"'");
			LOG.debug("  backgroundColor='"+backgroundColor+"'");
			LOG.debug("  foregroundColor='"+foregroundColor+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  ascending='"+ascending+"'");
			LOG.debug("  sortComparator='"+sortComparator+"'");
			LOG.debug("  resizable='"+resizable+"'");
			LOG.debug("  width='"+width+"'");
			LOG.debug("  maxWidth='"+maxWidth+"'");
			LOG.debug("  minWidth='"+minWidth+"'");
			LOG.debug("  verticalAlign='"+verticalAlign+"'");
			LOG.debug("  value='"+value+"'");
			LOG.debug("  defaultCellImageURL='"+defaultCellImageURL+"'");
			LOG.debug("  cellImageURL='"+cellImageURL+"'");
			LOG.debug("  cellStyleClass='"+cellStyleClass+"'");
			LOG.debug("  cellToolTipText='"+cellToolTipText+"'");
			LOG.debug("  autoFilter='"+autoFilter+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof DataColumnComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'DataColumnComponent'.");
		}

		DataColumnComponent component = (DataColumnComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (hiddenMode != null) {
			if (isValueReference(hiddenMode)) {
				ValueBinding vb = application.createValueBinding(hiddenMode);

				component.setHiddenMode(vb);
			} else {
				component.setHiddenMode(hiddenMode);
			}
		}

		if (visible != null) {
			if (isValueReference(visible)) {
				ValueBinding vb = application.createValueBinding(visible);

				component.setVisible(vb);
			} else {
				component.setVisible(getBool(visible));
			}
		}

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);

				component.setText(vb);
			} else {
				component.setText(text);
			}
		}

		if (toolTipText != null) {
			if (isValueReference(toolTipText)) {
				ValueBinding vb = application.createValueBinding(toolTipText);

				component.setToolTipText(vb);
			} else {
				component.setToolTipText(toolTipText);
			}
		}

		if (alignment != null) {
			if (isValueReference(alignment)) {
				ValueBinding vb = application.createValueBinding(alignment);

				component.setAlignment(vb);
			} else {
				component.setAlignment(alignment);
			}
		}

		if (backgroundColor != null) {
			if (isValueReference(backgroundColor)) {
				ValueBinding vb = application.createValueBinding(backgroundColor);

				component.setBackgroundColor(vb);
			} else {
				component.setBackgroundColor(backgroundColor);
			}
		}

		if (foregroundColor != null) {
			if (isValueReference(foregroundColor)) {
				ValueBinding vb = application.createValueBinding(foregroundColor);

				component.setForegroundColor(vb);
			} else {
				component.setForegroundColor(foregroundColor);
			}
		}

		if (sortListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SORT_LISTENER_TYPE, sortListeners);
		}

		if (styleClass != null) {
			if (isValueReference(styleClass)) {
				ValueBinding vb = application.createValueBinding(styleClass);

				component.setStyleClass(vb);
			} else {
				component.setStyleClass(styleClass);
			}
		}

		if (ascending != null) {
			if (isValueReference(ascending)) {
				ValueBinding vb = application.createValueBinding(ascending);

				component.setAscending(vb);
			} else {
				component.setAscending(getBool(ascending));
			}
		}

		if (sortComparator != null) {
				ValueBinding vb = application.createValueBinding(sortComparator);

				component.setSortComparator(vb);
		}

		if (resizable != null) {
			if (isValueReference(resizable)) {
				ValueBinding vb = application.createValueBinding(resizable);

				component.setResizable(vb);
			} else {
				component.setResizable(getBool(resizable));
			}
		}

		if (width != null) {
			if (isValueReference(width)) {
				ValueBinding vb = application.createValueBinding(width);
				component.setWidth(vb);
			} else {
				component.setWidth(width);
			}
		}

		if (maxWidth != null) {
			if (isValueReference(maxWidth)) {
				ValueBinding vb = application.createValueBinding(maxWidth);
				component.setMaxWidth(vb);
			} else {
				component.setMaxWidth(getInt(maxWidth));
			}
		}

		if (minWidth != null) {
			if (isValueReference(minWidth)) {
				ValueBinding vb = application.createValueBinding(minWidth);
				component.setMinWidth(vb);
			} else {
				component.setMinWidth(getInt(minWidth));
			}
		}

		if (verticalAlign != null) {
			if (isValueReference(verticalAlign)) {
				ValueBinding vb = application.createValueBinding(verticalAlign);
				component.setVerticalAlign(vb);
			} else {
				component.setVerticalAlign(verticalAlign);
			}
		}

		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = application.createValueBinding(value);
				component.setValue(vb);
			} else {
				component.setValue(value);
			}
		}

		if (defaultCellImageURL != null) {
			if (isValueReference(defaultCellImageURL)) {
				ValueBinding vb = application.createValueBinding(defaultCellImageURL);
				component.setDefaultCellImageURL(vb);
			} else {
				component.setDefaultCellImageURL(defaultCellImageURL);
			}
		}

		if (cellImageURL != null) {
			if (isValueReference(cellImageURL)) {
				ValueBinding vb = application.createValueBinding(cellImageURL);
				component.setCellImageURL(vb);
			} else {
				component.setCellImageURL(cellImageURL);
			}
		}

		if (cellStyleClass != null) {
			if (isValueReference(cellStyleClass)) {
				ValueBinding vb = application.createValueBinding(cellStyleClass);
				component.setCellStyleClass(vb);
			} else {
				component.setCellStyleClass(cellStyleClass);
			}
		}

		if (cellToolTipText != null) {
			if (isValueReference(cellToolTipText)) {
				ValueBinding vb = application.createValueBinding(cellToolTipText);
				component.setCellToolTipText(vb);
			} else {
				component.setCellToolTipText(cellToolTipText);
			}
		}

		if (autoFilter != null) {
			if (isValueReference(autoFilter)) {
				ValueBinding vb = application.createValueBinding(autoFilter);
				component.setAutoFilter(vb);
			} else {
				component.setAutoFilter(getBool(autoFilter));
			}
		}
	if (converter != null) {
		if (isValueReference(converter)) {
			ValueBinding vb = application.createValueBinding(converter);
			component.setConverter(vb);
		} else {
			component.setConverter(application.createConverter(converter));
		}
	}
	}

	public void release() {
		hiddenMode = null;
		visible = null;
		text = null;
		toolTipText = null;
		alignment = null;
		backgroundColor = null;
		foregroundColor = null;
		sortListeners = null;
		styleClass = null;
		ascending = null;
		sortComparator = null;
		resizable = null;
		width = null;
		maxWidth = null;
		minWidth = null;
		verticalAlign = null;
		value = null;
		defaultCellImageURL = null;
		cellImageURL = null;
		cellStyleClass = null;
		cellToolTipText = null;
		autoFilter = null;
		converter = null;

		super.release();
	}

}
