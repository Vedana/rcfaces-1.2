package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.ComboColumnComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ComboColumnTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ComboColumnTag.class);

	private ValueExpression visible;
	private ValueExpression hiddenMode;
	private ValueExpression text;
	private ValueExpression textDirection;
	private ValueExpression toolTipText;
	private ValueExpression alignment;
	private ValueExpression backgroundColor;
	private ValueExpression foregroundColor;
	private ValueExpression sortListeners;
	private ValueExpression styleClass;
	private ValueExpression ascending;
	private ValueExpression sortComparator;
	private ValueExpression resizable;
	private ValueExpression disabledImageURL;
	private ValueExpression hoverImageURL;
	private ValueExpression selectedImageURL;
	private ValueExpression imageURL;
	private ValueExpression imageHeight;
	private ValueExpression imageWidth;
	private ValueExpression maxWidth;
	private ValueExpression minWidth;
	private ValueExpression width;
	private ValueExpression verticalAlignment;
	private ValueExpression autoFilter;
	private ValueExpression cellImageURL;
	private ValueExpression defaultCellImageURL;
	private ValueExpression cellStyleClass;
	private ValueExpression defaultCellStyleClass;
	private ValueExpression cellDefaultToolTipText;
	private ValueExpression cellToolTipText;
	private ValueExpression value;
	private ValueExpression converter;

	public String getComponentType() {
		return ComboColumnComponent.COMPONENT_TYPE;
	}

	public final void setVisible(ValueExpression visible) {
		this.visible = visible;
	}

	public final void setHiddenMode(ValueExpression hiddenMode) {
		this.hiddenMode = hiddenMode;
	}

	public final void setText(ValueExpression text) {
		this.text = text;
	}

	public final void setTextDirection(ValueExpression textDirection) {
		this.textDirection = textDirection;
	}

	public final void setToolTipText(ValueExpression toolTipText) {
		this.toolTipText = toolTipText;
	}

	public final void setAlignment(ValueExpression alignment) {
		this.alignment = alignment;
	}

	public final void setBackgroundColor(ValueExpression backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public final void setForegroundColor(ValueExpression foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public final void setSortListener(ValueExpression sortListeners) {
		this.sortListeners = sortListeners;
	}

	public final void setStyleClass(ValueExpression styleClass) {
		this.styleClass = styleClass;
	}

	public final void setAscending(ValueExpression ascending) {
		this.ascending = ascending;
	}

	public final void setSortComparator(ValueExpression sortComparator) {
		this.sortComparator = sortComparator;
	}

	public final void setResizable(ValueExpression resizable) {
		this.resizable = resizable;
	}

	public final void setDisabledImageURL(ValueExpression disabledImageURL) {
		this.disabledImageURL = disabledImageURL;
	}

	public final void setHoverImageURL(ValueExpression hoverImageURL) {
		this.hoverImageURL = hoverImageURL;
	}

	public final void setSelectedImageURL(ValueExpression selectedImageURL) {
		this.selectedImageURL = selectedImageURL;
	}

	public final void setImageURL(ValueExpression imageURL) {
		this.imageURL = imageURL;
	}

	public final void setImageHeight(ValueExpression imageHeight) {
		this.imageHeight = imageHeight;
	}

	public final void setImageWidth(ValueExpression imageWidth) {
		this.imageWidth = imageWidth;
	}

	public final void setMaxWidth(ValueExpression maxWidth) {
		this.maxWidth = maxWidth;
	}

	public final void setMinWidth(ValueExpression minWidth) {
		this.minWidth = minWidth;
	}

	public final void setWidth(ValueExpression width) {
		this.width = width;
	}

	public final void setVerticalAlignment(ValueExpression verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public final void setAutoFilter(ValueExpression autoFilter) {
		this.autoFilter = autoFilter;
	}

	public final void setCellImageURL(ValueExpression cellImageURL) {
		this.cellImageURL = cellImageURL;
	}

	public final void setDefaultCellImageURL(ValueExpression defaultCellImageURL) {
		this.defaultCellImageURL = defaultCellImageURL;
	}

	public final void setCellStyleClass(ValueExpression cellStyleClass) {
		this.cellStyleClass = cellStyleClass;
	}

	public final void setDefaultCellStyleClass(ValueExpression defaultCellStyleClass) {
		this.defaultCellStyleClass = defaultCellStyleClass;
	}

	public final void setCellDefaultToolTipText(ValueExpression cellDefaultToolTipText) {
		this.cellDefaultToolTipText = cellDefaultToolTipText;
	}

	public final void setCellToolTipText(ValueExpression cellToolTipText) {
		this.cellToolTipText = cellToolTipText;
	}

	public final void setValue(ValueExpression value) {
		this.value = value;
	}

	public final void setConverter(ValueExpression converter) {
		this.converter=converter;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ComboColumnComponent.COMPONENT_TYPE==getComponentType()) {
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
			LOG.debug("  maxWidth='"+maxWidth+"'");
			LOG.debug("  minWidth='"+minWidth+"'");
			LOG.debug("  width='"+width+"'");
			LOG.debug("  verticalAlignment='"+verticalAlignment+"'");
			LOG.debug("  autoFilter='"+autoFilter+"'");
			LOG.debug("  cellImageURL='"+cellImageURL+"'");
			LOG.debug("  defaultCellImageURL='"+defaultCellImageURL+"'");
			LOG.debug("  cellStyleClass='"+cellStyleClass+"'");
			LOG.debug("  defaultCellStyleClass='"+defaultCellStyleClass+"'");
			LOG.debug("  cellDefaultToolTipText='"+cellDefaultToolTipText+"'");
			LOG.debug("  cellToolTipText='"+cellToolTipText+"'");
			LOG.debug("  value='"+value+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ComboColumnComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ComboColumnComponent'.");
		}

		ComboColumnComponent component = (ComboColumnComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (visible != null) {
			if (visible.isLiteralText()==false) {
				component.setValueExpression(Properties.VISIBLE, visible);

			} else {
				component.setVisible(getBool(visible.getExpressionString()));
			}
		}

		if (hiddenMode != null) {
			if (hiddenMode.isLiteralText()==false) {
				component.setValueExpression(Properties.HIDDEN_MODE, hiddenMode);

			} else {
				component.setHiddenMode(hiddenMode.getExpressionString());
			}
		}

		if (text != null) {
			if (text.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT, text);

			} else {
				component.setText(text.getExpressionString());
			}
		}

		if (textDirection != null) {
			if (textDirection.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_DIRECTION, textDirection);

			} else {
				component.setTextDirection(getInt(textDirection.getExpressionString()));
			}
		}

		if (toolTipText != null) {
			if (toolTipText.isLiteralText()==false) {
				component.setValueExpression(Properties.TOOL_TIP_TEXT, toolTipText);

			} else {
				component.setToolTipText(toolTipText.getExpressionString());
			}
		}

		if (alignment != null) {
			if (alignment.isLiteralText()==false) {
				component.setValueExpression(Properties.ALIGNMENT, alignment);

			} else {
				component.setAlignment(alignment.getExpressionString());
			}
		}

		if (backgroundColor != null) {
			if (backgroundColor.isLiteralText()==false) {
				component.setValueExpression(Properties.BACKGROUND_COLOR, backgroundColor);

			} else {
				component.setBackgroundColor(backgroundColor.getExpressionString());
			}
		}

		if (foregroundColor != null) {
			if (foregroundColor.isLiteralText()==false) {
				component.setValueExpression(Properties.FOREGROUND_COLOR, foregroundColor);

			} else {
				component.setForegroundColor(foregroundColor.getExpressionString());
			}
		}

		if (sortListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SORT_LISTENER_TYPE, sortListeners);
		}

		if (styleClass != null) {
			if (styleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.STYLE_CLASS, styleClass);

			} else {
				component.setStyleClass(styleClass.getExpressionString());
			}
		}

		if (ascending != null) {
			if (ascending.isLiteralText()==false) {
				component.setValueExpression(Properties.ASCENDING, ascending);

			} else {
				component.setAscending(getBool(ascending.getExpressionString()));
			}
		}

		if (sortComparator != null) {
				component.setValueExpression(Properties.SORT_COMPARATOR, sortComparator);
		}

		if (resizable != null) {
			if (resizable.isLiteralText()==false) {
				component.setValueExpression(Properties.RESIZABLE, resizable);

			} else {
				component.setResizable(getBool(resizable.getExpressionString()));
			}
		}

		if (disabledImageURL != null) {
			if (disabledImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLED_IMAGE_URL, disabledImageURL);

			} else {
				component.setDisabledImageURL(disabledImageURL.getExpressionString());
			}
		}

		if (hoverImageURL != null) {
			if (hoverImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.HOVER_IMAGE_URL, hoverImageURL);

			} else {
				component.setHoverImageURL(hoverImageURL.getExpressionString());
			}
		}

		if (selectedImageURL != null) {
			if (selectedImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.SELECTED_IMAGE_URL, selectedImageURL);

			} else {
				component.setSelectedImageURL(selectedImageURL.getExpressionString());
			}
		}

		if (imageURL != null) {
			if (imageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_URL, imageURL);

			} else {
				component.setImageURL(imageURL.getExpressionString());
			}
		}

		if (imageHeight != null) {
			if (imageHeight.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_HEIGHT, imageHeight);

			} else {
				component.setImageHeight(getInt(imageHeight.getExpressionString()));
			}
		}

		if (imageWidth != null) {
			if (imageWidth.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_WIDTH, imageWidth);

			} else {
				component.setImageWidth(getInt(imageWidth.getExpressionString()));
			}
		}

		if (maxWidth != null) {
			if (maxWidth.isLiteralText()==false) {
				component.setValueExpression(Properties.MAX_WIDTH, maxWidth);

			} else {
				component.setMaxWidth(getInt(maxWidth.getExpressionString()));
			}
		}

		if (minWidth != null) {
			if (minWidth.isLiteralText()==false) {
				component.setValueExpression(Properties.MIN_WIDTH, minWidth);

			} else {
				component.setMinWidth(getInt(minWidth.getExpressionString()));
			}
		}

		if (width != null) {
			if (width.isLiteralText()==false) {
				component.setValueExpression(Properties.WIDTH, width);

			} else {
				component.setWidth(width.getExpressionString());
			}
		}

		if (verticalAlignment != null) {
			if (verticalAlignment.isLiteralText()==false) {
				component.setValueExpression(Properties.VERTICAL_ALIGNMENT, verticalAlignment);

			} else {
				component.setVerticalAlignment(verticalAlignment.getExpressionString());
			}
		}

		if (autoFilter != null) {
			if (autoFilter.isLiteralText()==false) {
				component.setValueExpression(Properties.AUTO_FILTER, autoFilter);

			} else {
				component.setAutoFilter(getBool(autoFilter.getExpressionString()));
			}
		}

		if (cellImageURL != null) {
			if (cellImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.CELL_IMAGE_URL, cellImageURL);

			} else {
				component.setCellImageURL(cellImageURL.getExpressionString());
			}
		}

		if (defaultCellImageURL != null) {
			if (defaultCellImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_CELL_IMAGE_URL, defaultCellImageURL);

			} else {
				component.setDefaultCellImageURL(defaultCellImageURL.getExpressionString());
			}
		}

		if (cellStyleClass != null) {
			if (cellStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.CELL_STYLE_CLASS, cellStyleClass);

			} else {
				component.setCellStyleClass(cellStyleClass.getExpressionString());
			}
		}

		if (defaultCellStyleClass != null) {
			if (defaultCellStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_CELL_STYLE_CLASS, defaultCellStyleClass);

			} else {
				component.setDefaultCellStyleClass(defaultCellStyleClass.getExpressionString());
			}
		}

		if (cellDefaultToolTipText != null) {
			if (cellDefaultToolTipText.isLiteralText()==false) {
				component.setValueExpression(Properties.CELL_DEFAULT_TOOL_TIP_TEXT, cellDefaultToolTipText);

			} else {
				component.setCellDefaultToolTipText(cellDefaultToolTipText.getExpressionString());
			}
		}

		if (cellToolTipText != null) {
			if (cellToolTipText.isLiteralText()==false) {
				component.setValueExpression(Properties.CELL_TOOL_TIP_TEXT, cellToolTipText);

			} else {
				component.setCellToolTipText(cellToolTipText.getExpressionString());
			}
		}

		if (value != null) {
			if (value.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE, value);

			} else {
				component.setValue(value.getExpressionString());
			}
		}
	if (converter != null) {
		if (converter.isLiteralText()==false) {
			component.setValueExpression(Properties.CONVERTER, converter);
		} else {
			component.setConverter(application.createConverter(converter.getExpressionString()));
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
		maxWidth = null;
		minWidth = null;
		width = null;
		verticalAlignment = null;
		autoFilter = null;
		cellImageURL = null;
		defaultCellImageURL = null;
		cellStyleClass = null;
		defaultCellStyleClass = null;
		cellDefaultToolTipText = null;
		cellToolTipText = null;
		value = null;
		converter = null;

		super.release();
	}

}
