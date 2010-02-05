package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.ComboGridComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ComboGridTag extends AbstractGridTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ComboGridTag.class);

	private String selectionListeners;
	private String additionalInformationListeners;
	private String additionalInformationValues;
	private String clientAdditionalInformationFullState;
	private String additionalInformationCardinality;
	private String emptyMessage;
	private String emptyDataMessage;
	private String disabled;
	private String required;
	private String readOnly;
	private String border;
	private String rowStyleClass;
	private String manyResultsMessage;
	private String message;
	private String oneResultMessage;
	private String zeroResultMessage;
	private String maxTextLength;
	private String editable;
	private String horizontalScrollPosition;
	private String verticalScrollPosition;
	private String filterProperties;
	private String paged;
	private String headerVisible;
	private String suggestionDelayMs;
	private String suggestionMinChars;
	private String rowCountVar;
	private String rowIndexVar;
	private String valueColumnId;
	private String labelColumnId;
	private String selectedValue;
	private String popupWidth;
	private String popupHeight;
	private String valueFormat;
	private String pagerStyleClass;
	private String pagerLookId;
	private String popupStyleClass;
	private String gridStyleClass;
	private String gridLookId;
	private String searchFieldVisible;
	public String getComponentType() {
		return ComboGridComponent.COMPONENT_TYPE;
	}

	public final String getSelectionListener() {
		return selectionListeners;
	}

	public final void setSelectionListener(String selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final String getAdditionalInformationListener() {
		return additionalInformationListeners;
	}

	public final void setAdditionalInformationListener(String additionalInformationListeners) {
		this.additionalInformationListeners = additionalInformationListeners;
	}

	public final String getAdditionalInformationValues() {
		return additionalInformationValues;
	}

	public final void setAdditionalInformationValues(String additionalInformationValues) {
		this.additionalInformationValues = additionalInformationValues;
	}

	public final String getClientAdditionalInformationFullState() {
		return clientAdditionalInformationFullState;
	}

	public final void setClientAdditionalInformationFullState(String clientAdditionalInformationFullState) {
		this.clientAdditionalInformationFullState = clientAdditionalInformationFullState;
	}

	public final String getAdditionalInformationCardinality() {
		return additionalInformationCardinality;
	}

	public final void setAdditionalInformationCardinality(String additionalInformationCardinality) {
		this.additionalInformationCardinality = additionalInformationCardinality;
	}

	public final String getEmptyMessage() {
		return emptyMessage;
	}

	public final void setEmptyMessage(String emptyMessage) {
		this.emptyMessage = emptyMessage;
	}

	public final String getEmptyDataMessage() {
		return emptyDataMessage;
	}

	public final void setEmptyDataMessage(String emptyDataMessage) {
		this.emptyDataMessage = emptyDataMessage;
	}

	public final String getDisabled() {
		return disabled;
	}

	public final void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public final String getRequired() {
		return required;
	}

	public final void setRequired(String required) {
		this.required = required;
	}

	public final String getReadOnly() {
		return readOnly;
	}

	public final void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public final String getBorder() {
		return border;
	}

	public final void setBorder(String border) {
		this.border = border;
	}

	public final String getRowStyleClass() {
		return rowStyleClass;
	}

	public final void setRowStyleClass(String rowStyleClass) {
		this.rowStyleClass = rowStyleClass;
	}

	public final String getManyResultsMessage() {
		return manyResultsMessage;
	}

	public final void setManyResultsMessage(String manyResultsMessage) {
		this.manyResultsMessage = manyResultsMessage;
	}

	public final String getMessage() {
		return message;
	}

	public final void setMessage(String message) {
		this.message = message;
	}

	public final String getOneResultMessage() {
		return oneResultMessage;
	}

	public final void setOneResultMessage(String oneResultMessage) {
		this.oneResultMessage = oneResultMessage;
	}

	public final String getZeroResultMessage() {
		return zeroResultMessage;
	}

	public final void setZeroResultMessage(String zeroResultMessage) {
		this.zeroResultMessage = zeroResultMessage;
	}

	public final String getMaxTextLength() {
		return maxTextLength;
	}

	public final void setMaxTextLength(String maxTextLength) {
		this.maxTextLength = maxTextLength;
	}

	public final String getEditable() {
		return editable;
	}

	public final void setEditable(String editable) {
		this.editable = editable;
	}

	public final String getHorizontalScrollPosition() {
		return horizontalScrollPosition;
	}

	public final void setHorizontalScrollPosition(String horizontalScrollPosition) {
		this.horizontalScrollPosition = horizontalScrollPosition;
	}

	public final String getVerticalScrollPosition() {
		return verticalScrollPosition;
	}

	public final void setVerticalScrollPosition(String verticalScrollPosition) {
		this.verticalScrollPosition = verticalScrollPosition;
	}

	public final String getFilterProperties() {
		return filterProperties;
	}

	public final void setFilterProperties(String filterProperties) {
		this.filterProperties = filterProperties;
	}

	public final String getPaged() {
		return paged;
	}

	public final void setPaged(String paged) {
		this.paged = paged;
	}

	public final String getHeaderVisible() {
		return headerVisible;
	}

	public final void setHeaderVisible(String headerVisible) {
		this.headerVisible = headerVisible;
	}

	public final void setSuggestionDelayMs(String suggestionDelayMs) {
		this.suggestionDelayMs = suggestionDelayMs;
	}

	public final void setSuggestionMinChars(String suggestionMinChars) {
		this.suggestionMinChars = suggestionMinChars;
	}

	public final void setRowCountVar(String rowCountVar) {
		this.rowCountVar = rowCountVar;
	}

	public final void setRowIndexVar(String rowIndexVar) {
		this.rowIndexVar = rowIndexVar;
	}

	public final void setValueColumnId(String valueColumnId) {
		this.valueColumnId = valueColumnId;
	}

	public final void setLabelColumnId(String labelColumnId) {
		this.labelColumnId = labelColumnId;
	}

	public final void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public final void setPopupWidth(String popupWidth) {
		this.popupWidth = popupWidth;
	}

	public final void setPopupHeight(String popupHeight) {
		this.popupHeight = popupHeight;
	}

	public final void setValueFormat(String valueFormat) {
		this.valueFormat = valueFormat;
	}

	public final void setPagerStyleClass(String pagerStyleClass) {
		this.pagerStyleClass = pagerStyleClass;
	}

	public final void setPagerLookId(String pagerLookId) {
		this.pagerLookId = pagerLookId;
	}

	public final void setPopupStyleClass(String popupStyleClass) {
		this.popupStyleClass = popupStyleClass;
	}

	public final void setGridStyleClass(String gridStyleClass) {
		this.gridStyleClass = gridStyleClass;
	}

	public final void setGridLookId(String gridLookId) {
		this.gridLookId = gridLookId;
	}

	public final void setSearchFieldVisible(String searchFieldVisible) {
		this.searchFieldVisible = searchFieldVisible;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ComboGridComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  additionalInformationValues='"+additionalInformationValues+"'");
			LOG.debug("  clientAdditionalInformationFullState='"+clientAdditionalInformationFullState+"'");
			LOG.debug("  additionalInformationCardinality='"+additionalInformationCardinality+"'");
			LOG.debug("  emptyMessage='"+emptyMessage+"'");
			LOG.debug("  emptyDataMessage='"+emptyDataMessage+"'");
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
			LOG.debug("  editable='"+editable+"'");
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
			LOG.debug("  popupStyleClass='"+popupStyleClass+"'");
			LOG.debug("  gridStyleClass='"+gridStyleClass+"'");
			LOG.debug("  gridLookId='"+gridLookId+"'");
			LOG.debug("  searchFieldVisible='"+searchFieldVisible+"'");
		}
		if ((uiComponent instanceof ComboGridComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ComboGridComponent'.");
		}

		super.setProperties(uiComponent);

		ComboGridComponent component = (ComboGridComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (additionalInformationListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.ADDITIONAL_INFORMATION_LISTENER_TYPE, additionalInformationListeners);
		}

		if (additionalInformationValues != null) {
				ValueBinding vb = application.createValueBinding(additionalInformationValues);
				component.setValueBinding(Properties.ADDITIONAL_INFORMATION_VALUES, vb);
		}

		if (clientAdditionalInformationFullState != null) {
			if (isValueReference(clientAdditionalInformationFullState)) {
				ValueBinding vb = application.createValueBinding(clientAdditionalInformationFullState);
				component.setValueBinding(Properties.CLIENT_ADDITIONAL_INFORMATION_FULL_STATE, vb);

			} else {
				component.setClientAdditionalInformationFullState(clientAdditionalInformationFullState);
			}
		}

		if (additionalInformationCardinality != null) {
			if (isValueReference(additionalInformationCardinality)) {
				ValueBinding vb = application.createValueBinding(additionalInformationCardinality);
				component.setValueBinding(Properties.ADDITIONAL_INFORMATION_CARDINALITY, vb);

			} else {
				component.setAdditionalInformationCardinality(getInt(additionalInformationCardinality));
			}
		}

		if (emptyMessage != null) {
			if (isValueReference(emptyMessage)) {
				ValueBinding vb = application.createValueBinding(emptyMessage);
				component.setValueBinding(Properties.EMPTY_MESSAGE, vb);

			} else {
				component.setEmptyMessage(emptyMessage);
			}
		}

		if (emptyDataMessage != null) {
			if (isValueReference(emptyDataMessage)) {
				ValueBinding vb = application.createValueBinding(emptyDataMessage);
				component.setValueBinding(Properties.EMPTY_DATA_MESSAGE, vb);

			} else {
				component.setEmptyDataMessage(emptyDataMessage);
			}
		}

		if (disabled != null) {
			if (isValueReference(disabled)) {
				ValueBinding vb = application.createValueBinding(disabled);
				component.setValueBinding(Properties.DISABLED, vb);

			} else {
				component.setDisabled(getBool(disabled));
			}
		}

		if (required != null) {
			if (isValueReference(required)) {
				ValueBinding vb = application.createValueBinding(required);
				component.setValueBinding(Properties.REQUIRED, vb);

			} else {
				component.setRequired(getBool(required));
			}
		}

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);
				component.setValueBinding(Properties.READ_ONLY, vb);

			} else {
				component.setReadOnly(getBool(readOnly));
			}
		}

		if (border != null) {
			if (isValueReference(border)) {
				ValueBinding vb = application.createValueBinding(border);
				component.setValueBinding(Properties.BORDER, vb);

			} else {
				component.setBorder(getBool(border));
			}
		}

		if (rowStyleClass != null) {
			if (isValueReference(rowStyleClass)) {
				ValueBinding vb = application.createValueBinding(rowStyleClass);
				component.setValueBinding(Properties.ROW_STYLE_CLASS, vb);

			} else {
				component.setRowStyleClass(rowStyleClass);
			}
		}

		if (manyResultsMessage != null) {
			if (isValueReference(manyResultsMessage)) {
				ValueBinding vb = application.createValueBinding(manyResultsMessage);
				component.setValueBinding(Properties.MANY_RESULTS_MESSAGE, vb);

			} else {
				component.setManyResultsMessage(manyResultsMessage);
			}
		}

		if (message != null) {
			if (isValueReference(message)) {
				ValueBinding vb = application.createValueBinding(message);
				component.setValueBinding(Properties.MESSAGE, vb);

			} else {
				component.setMessage(message);
			}
		}

		if (oneResultMessage != null) {
			if (isValueReference(oneResultMessage)) {
				ValueBinding vb = application.createValueBinding(oneResultMessage);
				component.setValueBinding(Properties.ONE_RESULT_MESSAGE, vb);

			} else {
				component.setOneResultMessage(oneResultMessage);
			}
		}

		if (zeroResultMessage != null) {
			if (isValueReference(zeroResultMessage)) {
				ValueBinding vb = application.createValueBinding(zeroResultMessage);
				component.setValueBinding(Properties.ZERO_RESULT_MESSAGE, vb);

			} else {
				component.setZeroResultMessage(zeroResultMessage);
			}
		}

		if (maxTextLength != null) {
			if (isValueReference(maxTextLength)) {
				ValueBinding vb = application.createValueBinding(maxTextLength);
				component.setValueBinding(Properties.MAX_TEXT_LENGTH, vb);

			} else {
				component.setMaxTextLength(getInt(maxTextLength));
			}
		}

		if (editable != null) {
			if (isValueReference(editable)) {
				ValueBinding vb = application.createValueBinding(editable);
				component.setValueBinding(Properties.EDITABLE, vb);

			} else {
				component.setEditable(getBool(editable));
			}
		}

		if (horizontalScrollPosition != null) {
			if (isValueReference(horizontalScrollPosition)) {
				ValueBinding vb = application.createValueBinding(horizontalScrollPosition);
				component.setValueBinding(Properties.HORIZONTAL_SCROLL_POSITION, vb);

			} else {
				component.setHorizontalScrollPosition(getInt(horizontalScrollPosition));
			}
		}

		if (verticalScrollPosition != null) {
			if (isValueReference(verticalScrollPosition)) {
				ValueBinding vb = application.createValueBinding(verticalScrollPosition);
				component.setValueBinding(Properties.VERTICAL_SCROLL_POSITION, vb);

			} else {
				component.setVerticalScrollPosition(getInt(verticalScrollPosition));
			}
		}

		if (filterProperties != null) {
			if (isValueReference(filterProperties)) {
				ValueBinding vb = application.createValueBinding(filterProperties);
				component.setValueBinding(Properties.FILTER_PROPERTIES, vb);

			} else {
				component.setFilterProperties(filterProperties);
			}
		}

		if (paged != null) {
			if (isValueReference(paged)) {
				ValueBinding vb = application.createValueBinding(paged);
				component.setValueBinding(Properties.PAGED, vb);

			} else {
				component.setPaged(getBool(paged));
			}
		}

		if (headerVisible != null) {
			if (isValueReference(headerVisible)) {
				ValueBinding vb = application.createValueBinding(headerVisible);
				component.setValueBinding(Properties.HEADER_VISIBLE, vb);

			} else {
				component.setHeaderVisible(getBool(headerVisible));
			}
		}

		if (suggestionDelayMs != null) {
			if (isValueReference(suggestionDelayMs)) {
				ValueBinding vb = application.createValueBinding(suggestionDelayMs);
				component.setValueBinding(Properties.SUGGESTION_DELAY_MS, vb);

			} else {
				component.setSuggestionDelayMs(getInt(suggestionDelayMs));
			}
		}

		if (suggestionMinChars != null) {
			if (isValueReference(suggestionMinChars)) {
				ValueBinding vb = application.createValueBinding(suggestionMinChars);
				component.setValueBinding(Properties.SUGGESTION_MIN_CHARS, vb);

			} else {
				component.setSuggestionMinChars(getInt(suggestionMinChars));
			}
		}

		if (rowCountVar != null) {
			if (isValueReference(rowCountVar)) {
				throw new javax.faces.FacesException("Attribute 'rowCountVar' does not accept binding !");
			}
				component.setRowCountVar(rowCountVar);
		}

		if (rowIndexVar != null) {
			if (isValueReference(rowIndexVar)) {
				throw new javax.faces.FacesException("Attribute 'rowIndexVar' does not accept binding !");
			}
				component.setRowIndexVar(rowIndexVar);
		}

		if (valueColumnId != null) {
			if (isValueReference(valueColumnId)) {
				ValueBinding vb = application.createValueBinding(valueColumnId);
				component.setValueBinding(Properties.VALUE_COLUMN_ID, vb);

			} else {
				component.setValueColumnId(valueColumnId);
			}
		}

		if (labelColumnId != null) {
			if (isValueReference(labelColumnId)) {
				ValueBinding vb = application.createValueBinding(labelColumnId);
				component.setValueBinding(Properties.LABEL_COLUMN_ID, vb);

			} else {
				component.setLabelColumnId(labelColumnId);
			}
		}

		if (selectedValue != null) {
			if (isValueReference(selectedValue)) {
				ValueBinding vb = application.createValueBinding(selectedValue);
				component.setValueBinding(Properties.SELECTED_VALUE, vb);

			} else {
				component.setSelectedValue(selectedValue);
			}
		}

		if (popupWidth != null) {
			if (isValueReference(popupWidth)) {
				ValueBinding vb = application.createValueBinding(popupWidth);
				component.setValueBinding(Properties.POPUP_WIDTH, vb);

			} else {
				component.setPopupWidth(getInt(popupWidth));
			}
		}

		if (popupHeight != null) {
			if (isValueReference(popupHeight)) {
				ValueBinding vb = application.createValueBinding(popupHeight);
				component.setValueBinding(Properties.POPUP_HEIGHT, vb);

			} else {
				component.setPopupHeight(getInt(popupHeight));
			}
		}

		if (valueFormat != null) {
			if (isValueReference(valueFormat)) {
				ValueBinding vb = application.createValueBinding(valueFormat);
				component.setValueBinding(Properties.VALUE_FORMAT, vb);

			} else {
				component.setValueFormat(valueFormat);
			}
		}

		if (pagerStyleClass != null) {
			if (isValueReference(pagerStyleClass)) {
				ValueBinding vb = application.createValueBinding(pagerStyleClass);
				component.setValueBinding(Properties.PAGER_STYLE_CLASS, vb);

			} else {
				component.setPagerStyleClass(pagerStyleClass);
			}
		}

		if (pagerLookId != null) {
			if (isValueReference(pagerLookId)) {
				ValueBinding vb = application.createValueBinding(pagerLookId);
				component.setValueBinding(Properties.PAGER_LOOK_ID, vb);

			} else {
				component.setPagerLookId(pagerLookId);
			}
		}

		if (popupStyleClass != null) {
			if (isValueReference(popupStyleClass)) {
				ValueBinding vb = application.createValueBinding(popupStyleClass);
				component.setValueBinding(Properties.POPUP_STYLE_CLASS, vb);

			} else {
				component.setPopupStyleClass(popupStyleClass);
			}
		}

		if (gridStyleClass != null) {
			if (isValueReference(gridStyleClass)) {
				ValueBinding vb = application.createValueBinding(gridStyleClass);
				component.setValueBinding(Properties.GRID_STYLE_CLASS, vb);

			} else {
				component.setGridStyleClass(gridStyleClass);
			}
		}

		if (gridLookId != null) {
			if (isValueReference(gridLookId)) {
				ValueBinding vb = application.createValueBinding(gridLookId);
				component.setValueBinding(Properties.GRID_LOOK_ID, vb);

			} else {
				component.setGridLookId(gridLookId);
			}
		}

		if (searchFieldVisible != null) {
			if (isValueReference(searchFieldVisible)) {
				ValueBinding vb = application.createValueBinding(searchFieldVisible);
				component.setValueBinding(Properties.SEARCH_FIELD_VISIBLE, vb);

			} else {
				component.setSearchFieldVisible(getBool(searchFieldVisible));
			}
		}
	}

	public void release() {
		selectionListeners = null;
		additionalInformationListeners = null;
		additionalInformationValues = null;
		clientAdditionalInformationFullState = null;
		additionalInformationCardinality = null;
		emptyMessage = null;
		emptyDataMessage = null;
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
		editable = null;
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
		popupStyleClass = null;
		gridStyleClass = null;
		gridLookId = null;
		searchFieldVisible = null;

		super.release();
	}

}
