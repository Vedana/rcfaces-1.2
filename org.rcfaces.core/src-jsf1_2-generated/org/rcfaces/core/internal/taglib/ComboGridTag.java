package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.component.ComboGridComponent;
import javax.faces.context.FacesContext;

public class ComboGridTag extends AbstractGridTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ComboGridTag.class);

	private ValueExpression selectionListeners;
	private ValueExpression additionalInformationListeners;
	private ValueExpression additionalInformationValues;
	private ValueExpression clientAdditionalInformationFullState;
	private ValueExpression additionalInformationCardinality;
	private ValueExpression disabled;
	private ValueExpression required;
	private ValueExpression readOnly;
	private ValueExpression border;
	private ValueExpression rowStyleClass;
	private ValueExpression manyResultsMessage;
	private ValueExpression message;
	private ValueExpression oneResultMessage;
	private ValueExpression zeroResultMessage;
	private ValueExpression maxTextLength;
	private ValueExpression horizontalScrollPosition;
	private ValueExpression verticalScrollPosition;
	private ValueExpression filterProperties;
	private ValueExpression paged;
	private ValueExpression headerVisible;
	private ValueExpression suggestionDelayMs;
	private ValueExpression suggestionMinChars;
	private ValueExpression rowCountVar;
	private ValueExpression rowIndexVar;
	private ValueExpression valueColumnId;
	private ValueExpression labelColumnId;
	private ValueExpression selectedValue;
	private ValueExpression popupWidth;
	private ValueExpression popupHeight;
	private ValueExpression valueFormat;
	private ValueExpression pagerStyleClass;
	private ValueExpression pagerLookId;
	private ValueExpression gridStyleClass;
	private ValueExpression gridLookId;
	public String getComponentType() {
		return ComboGridComponent.COMPONENT_TYPE;
	}

	public final void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final void setAdditionalInformationListener(ValueExpression additionalInformationListeners) {
		this.additionalInformationListeners = additionalInformationListeners;
	}

	public final void setAdditionalInformationValues(ValueExpression additionalInformationValues) {
		this.additionalInformationValues = additionalInformationValues;
	}

	public final void setClientAdditionalInformationFullState(ValueExpression clientAdditionalInformationFullState) {
		this.clientAdditionalInformationFullState = clientAdditionalInformationFullState;
	}

	public final void setAdditionalInformationCardinality(ValueExpression additionalInformationCardinality) {
		this.additionalInformationCardinality = additionalInformationCardinality;
	}

	public final void setDisabled(ValueExpression disabled) {
		this.disabled = disabled;
	}

	public final void setRequired(ValueExpression required) {
		this.required = required;
	}

	public final void setReadOnly(ValueExpression readOnly) {
		this.readOnly = readOnly;
	}

	public final void setBorder(ValueExpression border) {
		this.border = border;
	}

	public final void setRowStyleClass(ValueExpression rowStyleClass) {
		this.rowStyleClass = rowStyleClass;
	}

	public final void setManyResultsMessage(ValueExpression manyResultsMessage) {
		this.manyResultsMessage = manyResultsMessage;
	}

	public final void setMessage(ValueExpression message) {
		this.message = message;
	}

	public final void setOneResultMessage(ValueExpression oneResultMessage) {
		this.oneResultMessage = oneResultMessage;
	}

	public final void setZeroResultMessage(ValueExpression zeroResultMessage) {
		this.zeroResultMessage = zeroResultMessage;
	}

	public final void setMaxTextLength(ValueExpression maxTextLength) {
		this.maxTextLength = maxTextLength;
	}

	public final void setHorizontalScrollPosition(ValueExpression horizontalScrollPosition) {
		this.horizontalScrollPosition = horizontalScrollPosition;
	}

	public final void setVerticalScrollPosition(ValueExpression verticalScrollPosition) {
		this.verticalScrollPosition = verticalScrollPosition;
	}

	public final void setFilterProperties(ValueExpression filterProperties) {
		this.filterProperties = filterProperties;
	}

	public final void setPaged(ValueExpression paged) {
		this.paged = paged;
	}

	public final void setHeaderVisible(ValueExpression headerVisible) {
		this.headerVisible = headerVisible;
	}

	public final void setSuggestionDelayMs(ValueExpression suggestionDelayMs) {
		this.suggestionDelayMs = suggestionDelayMs;
	}

	public final void setSuggestionMinChars(ValueExpression suggestionMinChars) {
		this.suggestionMinChars = suggestionMinChars;
	}

	public final void setRowCountVar(ValueExpression rowCountVar) {
		this.rowCountVar = rowCountVar;
	}

	public final void setRowIndexVar(ValueExpression rowIndexVar) {
		this.rowIndexVar = rowIndexVar;
	}

	public final void setValueColumnId(ValueExpression valueColumnId) {
		this.valueColumnId = valueColumnId;
	}

	public final void setLabelColumnId(ValueExpression labelColumnId) {
		this.labelColumnId = labelColumnId;
	}

	public final void setSelectedValue(ValueExpression selectedValue) {
		this.selectedValue = selectedValue;
	}

	public final void setPopupWidth(ValueExpression popupWidth) {
		this.popupWidth = popupWidth;
	}

	public final void setPopupHeight(ValueExpression popupHeight) {
		this.popupHeight = popupHeight;
	}

	public final void setValueFormat(ValueExpression valueFormat) {
		this.valueFormat = valueFormat;
	}

	public final void setPagerStyleClass(ValueExpression pagerStyleClass) {
		this.pagerStyleClass = pagerStyleClass;
	}

	public final void setPagerLookId(ValueExpression pagerLookId) {
		this.pagerLookId = pagerLookId;
	}

	public final void setGridStyleClass(ValueExpression gridStyleClass) {
		this.gridStyleClass = gridStyleClass;
	}

	public final void setGridLookId(ValueExpression gridLookId) {
		this.gridLookId = gridLookId;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ComboGridComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  additionalInformationValues='"+additionalInformationValues+"'");
			LOG.debug("  clientAdditionalInformationFullState='"+clientAdditionalInformationFullState+"'");
			LOG.debug("  additionalInformationCardinality='"+additionalInformationCardinality+"'");
			LOG.debug("  disabled='"+disabled+"'");
			LOG.debug("  required='"+required+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  rowStyleClass='"+rowStyleClass+"'");
			LOG.debug("  manyResultsMessage='"+manyResultsMessage+"'");
			LOG.debug("  message='"+message+"'");
			LOG.debug("  oneResultMessage='"+oneResultMessage+"'");
			LOG.debug("  zeroResultMessage='"+zeroResultMessage+"'");
			LOG.debug("  maxTextLength='"+maxTextLength+"'");
			LOG.debug("  horizontalScrollPosition='"+horizontalScrollPosition+"'");
			LOG.debug("  verticalScrollPosition='"+verticalScrollPosition+"'");
			LOG.debug("  filterProperties='"+filterProperties+"'");
			LOG.debug("  paged='"+paged+"'");
			LOG.debug("  headerVisible='"+headerVisible+"'");
			LOG.debug("  suggestionDelayMs='"+suggestionDelayMs+"'");
			LOG.debug("  suggestionMinChars='"+suggestionMinChars+"'");
			LOG.debug("  rowCountVar='"+rowCountVar+"'");
			LOG.debug("  rowIndexVar='"+rowIndexVar+"'");
			LOG.debug("  valueColumnId='"+valueColumnId+"'");
			LOG.debug("  labelColumnId='"+labelColumnId+"'");
			LOG.debug("  selectedValue='"+selectedValue+"'");
			LOG.debug("  popupWidth='"+popupWidth+"'");
			LOG.debug("  popupHeight='"+popupHeight+"'");
			LOG.debug("  valueFormat='"+valueFormat+"'");
			LOG.debug("  pagerStyleClass='"+pagerStyleClass+"'");
			LOG.debug("  pagerLookId='"+pagerLookId+"'");
			LOG.debug("  gridStyleClass='"+gridStyleClass+"'");
			LOG.debug("  gridLookId='"+gridLookId+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ComboGridComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ComboGridComponent'.");
		}

		ComboGridComponent component = (ComboGridComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (additionalInformationListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.ADDITIONAL_INFORMATION_LISTENER_TYPE, additionalInformationListeners);
		}

		if (additionalInformationValues != null) {
				component.setValueExpression(Properties.ADDITIONAL_INFORMATION_VALUES, additionalInformationValues);
		}

		if (clientAdditionalInformationFullState != null) {
			if (clientAdditionalInformationFullState.isLiteralText()==false) {
				component.setValueExpression(Properties.CLIENT_ADDITIONAL_INFORMATION_FULL_STATE, clientAdditionalInformationFullState);

			} else {
				component.setClientAdditionalInformationFullState(getBool(clientAdditionalInformationFullState.getExpressionString()));
			}
		}

		if (additionalInformationCardinality != null) {
			if (additionalInformationCardinality.isLiteralText()==false) {
				component.setValueExpression(Properties.ADDITIONAL_INFORMATION_CARDINALITY, additionalInformationCardinality);

			} else {
				component.setAdditionalInformationCardinality(getInt(additionalInformationCardinality.getExpressionString()));
			}
		}

		if (disabled != null) {
			if (disabled.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLED, disabled);

			} else {
				component.setDisabled(getBool(disabled.getExpressionString()));
			}
		}

		if (required != null) {
			if (required.isLiteralText()==false) {
				component.setValueExpression(Properties.REQUIRED, required);

			} else {
				component.setRequired(getBool(required.getExpressionString()));
			}
		}

		if (readOnly != null) {
			if (readOnly.isLiteralText()==false) {
				component.setValueExpression(Properties.READ_ONLY, readOnly);

			} else {
				component.setReadOnly(getBool(readOnly.getExpressionString()));
			}
		}

		if (border != null) {
			if (border.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER, border);

			} else {
				component.setBorder(getBool(border.getExpressionString()));
			}
		}

		if (rowStyleClass != null) {
			if (rowStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.ROW_STYLE_CLASS, rowStyleClass);

			} else {
				component.setRowStyleClass(rowStyleClass.getExpressionString());
			}
		}

		if (manyResultsMessage != null) {
			if (manyResultsMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.MANY_RESULTS_MESSAGE, manyResultsMessage);

			} else {
				component.setManyResultsMessage(manyResultsMessage.getExpressionString());
			}
		}

		if (message != null) {
			if (message.isLiteralText()==false) {
				component.setValueExpression(Properties.MESSAGE, message);

			} else {
				component.setMessage(message.getExpressionString());
			}
		}

		if (oneResultMessage != null) {
			if (oneResultMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.ONE_RESULT_MESSAGE, oneResultMessage);

			} else {
				component.setOneResultMessage(oneResultMessage.getExpressionString());
			}
		}

		if (zeroResultMessage != null) {
			if (zeroResultMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.ZERO_RESULT_MESSAGE, zeroResultMessage);

			} else {
				component.setZeroResultMessage(zeroResultMessage.getExpressionString());
			}
		}

		if (maxTextLength != null) {
			if (maxTextLength.isLiteralText()==false) {
				component.setValueExpression(Properties.MAX_TEXT_LENGTH, maxTextLength);

			} else {
				component.setMaxTextLength(getInt(maxTextLength.getExpressionString()));
			}
		}

		if (horizontalScrollPosition != null) {
			if (horizontalScrollPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.HORIZONTAL_SCROLL_POSITION, horizontalScrollPosition);

			} else {
				component.setHorizontalScrollPosition(getInt(horizontalScrollPosition.getExpressionString()));
			}
		}

		if (verticalScrollPosition != null) {
			if (verticalScrollPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.VERTICAL_SCROLL_POSITION, verticalScrollPosition);

			} else {
				component.setVerticalScrollPosition(getInt(verticalScrollPosition.getExpressionString()));
			}
		}

		if (filterProperties != null) {
				component.setValueExpression(Properties.FILTER_PROPERTIES, filterProperties);
		}

		if (paged != null) {
			if (paged.isLiteralText()==false) {
				component.setValueExpression(Properties.PAGED, paged);

			} else {
				component.setPaged(getBool(paged.getExpressionString()));
			}
		}

		if (headerVisible != null) {
			if (headerVisible.isLiteralText()==false) {
				component.setValueExpression(Properties.HEADER_VISIBLE, headerVisible);

			} else {
				component.setHeaderVisible(getBool(headerVisible.getExpressionString()));
			}
		}

		if (suggestionDelayMs != null) {
			if (suggestionDelayMs.isLiteralText()==false) {
				component.setValueExpression(Properties.SUGGESTION_DELAY_MS, suggestionDelayMs);

			} else {
				component.setSuggestionDelayMs(getInt(suggestionDelayMs.getExpressionString()));
			}
		}

		if (suggestionMinChars != null) {
			if (suggestionMinChars.isLiteralText()==false) {
				component.setValueExpression(Properties.SUGGESTION_MIN_CHARS, suggestionMinChars);

			} else {
				component.setSuggestionMinChars(getInt(suggestionMinChars.getExpressionString()));
			}
		}

		if (rowCountVar != null) {
			if (rowCountVar.isLiteralText()==false) {
				throw new javax.faces.FacesException("Attribute 'rowCountVar' does not accept binding !");
			}
				component.setRowCountVar(rowCountVar.getExpressionString());
		}

		if (rowIndexVar != null) {
			if (rowIndexVar.isLiteralText()==false) {
				throw new javax.faces.FacesException("Attribute 'rowIndexVar' does not accept binding !");
			}
				component.setRowIndexVar(rowIndexVar.getExpressionString());
		}

		if (valueColumnId != null) {
			if (valueColumnId.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE_COLUMN_ID, valueColumnId);

			} else {
				component.setValueColumnId(valueColumnId.getExpressionString());
			}
		}

		if (labelColumnId != null) {
			if (labelColumnId.isLiteralText()==false) {
				component.setValueExpression(Properties.LABEL_COLUMN_ID, labelColumnId);

			} else {
				component.setLabelColumnId(labelColumnId.getExpressionString());
			}
		}

		if (selectedValue != null) {
			if (selectedValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SELECTED_VALUE, selectedValue);

			} else {
				component.setSelectedValue(selectedValue.getExpressionString());
			}
		}

		if (popupWidth != null) {
			if (popupWidth.isLiteralText()==false) {
				component.setValueExpression(Properties.POPUP_WIDTH, popupWidth);

			} else {
				component.setPopupWidth(getInt(popupWidth.getExpressionString()));
			}
		}

		if (popupHeight != null) {
			if (popupHeight.isLiteralText()==false) {
				component.setValueExpression(Properties.POPUP_HEIGHT, popupHeight);

			} else {
				component.setPopupHeight(getInt(popupHeight.getExpressionString()));
			}
		}

		if (valueFormat != null) {
			if (valueFormat.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE_FORMAT, valueFormat);

			} else {
				component.setValueFormat(valueFormat.getExpressionString());
			}
		}

		if (pagerStyleClass != null) {
			if (pagerStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.PAGER_STYLE_CLASS, pagerStyleClass);

			} else {
				component.setPagerStyleClass(pagerStyleClass.getExpressionString());
			}
		}

		if (pagerLookId != null) {
			if (pagerLookId.isLiteralText()==false) {
				component.setValueExpression(Properties.PAGER_LOOK_ID, pagerLookId);

			} else {
				component.setPagerLookId(pagerLookId.getExpressionString());
			}
		}

		if (gridStyleClass != null) {
			if (gridStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.GRID_STYLE_CLASS, gridStyleClass);

			} else {
				component.setGridStyleClass(gridStyleClass.getExpressionString());
			}
		}

		if (gridLookId != null) {
			if (gridLookId.isLiteralText()==false) {
				component.setValueExpression(Properties.GRID_LOOK_ID, gridLookId);

			} else {
				component.setGridLookId(gridLookId.getExpressionString());
			}
		}
	}

	public void release() {
		selectionListeners = null;
		additionalInformationListeners = null;
		additionalInformationValues = null;
		clientAdditionalInformationFullState = null;
		additionalInformationCardinality = null;
		disabled = null;
		required = null;
		readOnly = null;
		border = null;
		rowStyleClass = null;
		manyResultsMessage = null;
		message = null;
		oneResultMessage = null;
		zeroResultMessage = null;
		maxTextLength = null;
		horizontalScrollPosition = null;
		verticalScrollPosition = null;
		filterProperties = null;
		paged = null;
		headerVisible = null;
		suggestionDelayMs = null;
		suggestionMinChars = null;
		rowCountVar = null;
		rowIndexVar = null;
		valueColumnId = null;
		labelColumnId = null;
		selectedValue = null;
		popupWidth = null;
		popupHeight = null;
		valueFormat = null;
		pagerStyleClass = null;
		pagerLookId = null;
		gridStyleClass = null;
		gridLookId = null;

		super.release();
	}

}
