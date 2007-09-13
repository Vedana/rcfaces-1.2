package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.ComponentsColumnComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ComponentsColumnTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ComponentsColumnTag.class);

	private String visible;
	private String hiddenMode;
	private String text;
	private String textDirection;
	private String toolTipText;
	private String alignment;
	private String backgroundColor;
	private String foregroundColor;
	private String sortListeners;
	private String styleClass;
	private String ascending;
	private String sortComparator;
	private String resizable;
	private String disabledImageURL;
	private String hoverImageURL;
	private String selectedImageURL;
	private String imageURL;
	private String imageHeight;
	private String imageWidth;
	private String menuPopupId;
	private String maxWidth;
	private String minWidth;
	private String width;
	private String verticalAlignment;
	private String cellStyleClass;
	private String defaultCellStyleClass;
	private String cellDefaultToolTipText;
	private String cellToolTipText;
	public String getComponentType() {
		return ComponentsColumnComponent.COMPONENT_TYPE;
	}

	public final String getVisible() {
		return visible;
	}

	public final void setVisible(String visible) {
		this.visible = visible;
	}

	public final String getHiddenMode() {
		return hiddenMode;
	}

	public final void setHiddenMode(String hiddenMode) {
		this.hiddenMode = hiddenMode;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getTextDirection() {
		return textDirection;
	}

	public final void setTextDirection(String textDirection) {
		this.textDirection = textDirection;
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

	public final String getDisabledImageURL() {
		return disabledImageURL;
	}

	public final void setDisabledImageURL(String disabledImageURL) {
		this.disabledImageURL = disabledImageURL;
	}

	public final String getHoverImageURL() {
		return hoverImageURL;
	}

	public final void setHoverImageURL(String hoverImageURL) {
		this.hoverImageURL = hoverImageURL;
	}

	public final String getSelectedImageURL() {
		return selectedImageURL;
	}

	public final void setSelectedImageURL(String selectedImageURL) {
		this.selectedImageURL = selectedImageURL;
	}

	public final String getImageURL() {
		return imageURL;
	}

	public final void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public final String getImageHeight() {
		return imageHeight;
	}

	public final void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}

	public final String getImageWidth() {
		return imageWidth;
	}

	public final void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	public final String getMenuPopupId() {
		return menuPopupId;
	}

	public final void setMenuPopupId(String menuPopupId) {
		this.menuPopupId = menuPopupId;
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

	public final String getWidth() {
		return width;
	}

	public final void setWidth(String width) {
		this.width = width;
	}

	public final String getVerticalAlignment() {
		return verticalAlignment;
	}

	public final void setVerticalAlignment(String verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public final String getCellStyleClass() {
		return cellStyleClass;
	}

	public final void setCellStyleClass(String cellStyleClass) {
		this.cellStyleClass = cellStyleClass;
	}

	public final String getDefaultCellStyleClass() {
		return defaultCellStyleClass;
	}

	public final void setDefaultCellStyleClass(String defaultCellStyleClass) {
		this.defaultCellStyleClass = defaultCellStyleClass;
	}

	public final String getCellDefaultToolTipText() {
		return cellDefaultToolTipText;
	}

	public final void setCellDefaultToolTipText(String cellDefaultToolTipText) {
		this.cellDefaultToolTipText = cellDefaultToolTipText;
	}

	public final String getCellToolTipText() {
		return cellToolTipText;
	}

	public final void setCellToolTipText(String cellToolTipText) {
		this.cellToolTipText = cellToolTipText;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ComponentsColumnComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  visible='"+visible+"'");
			LOG.debug("  hiddenMode='"+hiddenMode+"'");
			LOG.debug("  text='"+text+"'");
			LOG.debug("  textDirection='"+textDirection+"'");
			LOG.debug("  toolTipText='"+toolTipText+"'");
			LOG.debug("  alignment='"+alignment+"'");
			LOG.debug("  backgroundColor='"+backgroundColor+"'");
			LOG.debug("  foregroundColor='"+foregroundColor+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  ascending='"+ascending+"'");
			LOG.debug("  sortComparator='"+sortComparator+"'");
			LOG.debug("  resizable='"+resizable+"'");
			LOG.debug("  disabledImageURL='"+disabledImageURL+"'");
			LOG.debug("  hoverImageURL='"+hoverImageURL+"'");
			LOG.debug("  selectedImageURL='"+selectedImageURL+"'");
			LOG.debug("  imageURL='"+imageURL+"'");
			LOG.debug("  imageHeight='"+imageHeight+"'");
			LOG.debug("  imageWidth='"+imageWidth+"'");
			LOG.debug("  menuPopupId='"+menuPopupId+"'");
			LOG.debug("  maxWidth='"+maxWidth+"'");
			LOG.debug("  minWidth='"+minWidth+"'");
			LOG.debug("  width='"+width+"'");
			LOG.debug("  verticalAlignment='"+verticalAlignment+"'");
			LOG.debug("  cellStyleClass='"+cellStyleClass+"'");
			LOG.debug("  defaultCellStyleClass='"+defaultCellStyleClass+"'");
			LOG.debug("  cellDefaultToolTipText='"+cellDefaultToolTipText+"'");
			LOG.debug("  cellToolTipText='"+cellToolTipText+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ComponentsColumnComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ComponentsColumnComponent'.");
		}

		ComponentsColumnComponent component = (ComponentsColumnComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (visible != null) {
			if (isValueReference(visible)) {
				ValueBinding vb = application.createValueBinding(visible);
				component.setValueBinding(Properties.VISIBLE, vb);

			} else {
				component.setVisible(getBool(visible));
			}
		}

		if (hiddenMode != null) {
			if (isValueReference(hiddenMode)) {
				ValueBinding vb = application.createValueBinding(hiddenMode);
				component.setValueBinding(Properties.HIDDEN_MODE, vb);

			} else {
				component.setHiddenMode(hiddenMode);
			}
		}

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);
				component.setValueBinding(Properties.TEXT, vb);

			} else {
				component.setText(text);
			}
		}

		if (textDirection != null) {
			if (isValueReference(textDirection)) {
				ValueBinding vb = application.createValueBinding(textDirection);
				component.setValueBinding(Properties.TEXT_DIRECTION, vb);

			} else {
				component.setTextDirection(getInt(textDirection));
			}
		}

		if (toolTipText != null) {
			if (isValueReference(toolTipText)) {
				ValueBinding vb = application.createValueBinding(toolTipText);
				component.setValueBinding(Properties.TOOL_TIP_TEXT, vb);

			} else {
				component.setToolTipText(toolTipText);
			}
		}

		if (alignment != null) {
			if (isValueReference(alignment)) {
				ValueBinding vb = application.createValueBinding(alignment);
				component.setValueBinding(Properties.ALIGNMENT, vb);

			} else {
				component.setAlignment(alignment);
			}
		}

		if (backgroundColor != null) {
			if (isValueReference(backgroundColor)) {
				ValueBinding vb = application.createValueBinding(backgroundColor);
				component.setValueBinding(Properties.BACKGROUND_COLOR, vb);

			} else {
				component.setBackgroundColor(backgroundColor);
			}
		}

		if (foregroundColor != null) {
			if (isValueReference(foregroundColor)) {
				ValueBinding vb = application.createValueBinding(foregroundColor);
				component.setValueBinding(Properties.FOREGROUND_COLOR, vb);

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
				component.setValueBinding(Properties.STYLE_CLASS, vb);

			} else {
				component.setStyleClass(styleClass);
			}
		}

		if (ascending != null) {
			if (isValueReference(ascending)) {
				ValueBinding vb = application.createValueBinding(ascending);
				component.setValueBinding(Properties.ASCENDING, vb);

			} else {
				component.setAscending(getBool(ascending));
			}
		}

		if (sortComparator != null) {
				ValueBinding vb = application.createValueBinding(sortComparator);
				component.setValueBinding(Properties.SORT_COMPARATOR, vb);
		}

		if (resizable != null) {
			if (isValueReference(resizable)) {
				ValueBinding vb = application.createValueBinding(resizable);
				component.setValueBinding(Properties.RESIZABLE, vb);

			} else {
				component.setResizable(getBool(resizable));
			}
		}

		if (disabledImageURL != null) {
			if (isValueReference(disabledImageURL)) {
				ValueBinding vb = application.createValueBinding(disabledImageURL);
				component.setValueBinding(Properties.DISABLED_IMAGE_URL, vb);

			} else {
				component.setDisabledImageURL(disabledImageURL);
			}
		}

		if (hoverImageURL != null) {
			if (isValueReference(hoverImageURL)) {
				ValueBinding vb = application.createValueBinding(hoverImageURL);
				component.setValueBinding(Properties.HOVER_IMAGE_URL, vb);

			} else {
				component.setHoverImageURL(hoverImageURL);
			}
		}

		if (selectedImageURL != null) {
			if (isValueReference(selectedImageURL)) {
				ValueBinding vb = application.createValueBinding(selectedImageURL);
				component.setValueBinding(Properties.SELECTED_IMAGE_URL, vb);

			} else {
				component.setSelectedImageURL(selectedImageURL);
			}
		}

		if (imageURL != null) {
			if (isValueReference(imageURL)) {
				ValueBinding vb = application.createValueBinding(imageURL);
				component.setValueBinding(Properties.IMAGE_URL, vb);

			} else {
				component.setImageURL(imageURL);
			}
		}

		if (imageHeight != null) {
			if (isValueReference(imageHeight)) {
				ValueBinding vb = application.createValueBinding(imageHeight);
				component.setValueBinding(Properties.IMAGE_HEIGHT, vb);

			} else {
				component.setImageHeight(getInt(imageHeight));
			}
		}

		if (imageWidth != null) {
			if (isValueReference(imageWidth)) {
				ValueBinding vb = application.createValueBinding(imageWidth);
				component.setValueBinding(Properties.IMAGE_WIDTH, vb);

			} else {
				component.setImageWidth(getInt(imageWidth));
			}
		}

		if (menuPopupId != null) {
			if (isValueReference(menuPopupId)) {
				ValueBinding vb = application.createValueBinding(menuPopupId);
				component.setValueBinding(Properties.MENU_POPUP_ID, vb);

			} else {
				component.setMenuPopupId(menuPopupId);
			}
		}

		if (maxWidth != null) {
			if (isValueReference(maxWidth)) {
				ValueBinding vb = application.createValueBinding(maxWidth);
				component.setValueBinding(Properties.MAX_WIDTH, vb);

			} else {
				component.setMaxWidth(getInt(maxWidth));
			}
		}

		if (minWidth != null) {
			if (isValueReference(minWidth)) {
				ValueBinding vb = application.createValueBinding(minWidth);
				component.setValueBinding(Properties.MIN_WIDTH, vb);

			} else {
				component.setMinWidth(getInt(minWidth));
			}
		}

		if (width != null) {
			if (isValueReference(width)) {
				ValueBinding vb = application.createValueBinding(width);
				component.setValueBinding(Properties.WIDTH, vb);

			} else {
				component.setWidth(width);
			}
		}

		if (verticalAlignment != null) {
			if (isValueReference(verticalAlignment)) {
				ValueBinding vb = application.createValueBinding(verticalAlignment);
				component.setValueBinding(Properties.VERTICAL_ALIGNMENT, vb);

			} else {
				component.setVerticalAlignment(verticalAlignment);
			}
		}

		if (cellStyleClass != null) {
			if (isValueReference(cellStyleClass)) {
				ValueBinding vb = application.createValueBinding(cellStyleClass);
				component.setValueBinding(Properties.CELL_STYLE_CLASS, vb);

			} else {
				component.setCellStyleClass(cellStyleClass);
			}
		}

		if (defaultCellStyleClass != null) {
			if (isValueReference(defaultCellStyleClass)) {
				ValueBinding vb = application.createValueBinding(defaultCellStyleClass);
				component.setValueBinding(Properties.DEFAULT_CELL_STYLE_CLASS, vb);

			} else {
				component.setDefaultCellStyleClass(defaultCellStyleClass);
			}
		}

		if (cellDefaultToolTipText != null) {
			if (isValueReference(cellDefaultToolTipText)) {
				ValueBinding vb = application.createValueBinding(cellDefaultToolTipText);
				component.setValueBinding(Properties.CELL_DEFAULT_TOOL_TIP_TEXT, vb);

			} else {
				component.setCellDefaultToolTipText(cellDefaultToolTipText);
			}
		}

		if (cellToolTipText != null) {
			if (isValueReference(cellToolTipText)) {
				ValueBinding vb = application.createValueBinding(cellToolTipText);
				component.setValueBinding(Properties.CELL_TOOL_TIP_TEXT, vb);

			} else {
				component.setCellToolTipText(cellToolTipText);
			}
		}
	}

	public void release() {
		visible = null;
		hiddenMode = null;
		text = null;
		textDirection = null;
		toolTipText = null;
		alignment = null;
		backgroundColor = null;
		foregroundColor = null;
		sortListeners = null;
		styleClass = null;
		ascending = null;
		sortComparator = null;
		resizable = null;
		disabledImageURL = null;
		hoverImageURL = null;
		selectedImageURL = null;
		imageURL = null;
		imageHeight = null;
		imageWidth = null;
		menuPopupId = null;
		maxWidth = null;
		minWidth = null;
		width = null;
		verticalAlignment = null;
		cellStyleClass = null;
		defaultCellStyleClass = null;
		cellDefaultToolTipText = null;
		cellToolTipText = null;

		super.release();
	}

}
