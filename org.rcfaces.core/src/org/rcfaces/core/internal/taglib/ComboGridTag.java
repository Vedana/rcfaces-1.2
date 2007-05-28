package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.ComboGridComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ComboGridTag extends AbstractGridTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ComboGridTag.class);

	private String selectionListeners;
	private String disabled;
	private String required;
	private String readOnly;
	private String border;
	private String rowStyleClass;
	private String manyResultsMessage;
	private String message;
	private String oneResultMessage;
	private String zeroResultMessage;
	private String horizontalScrollPosition;
	private String verticalScrollPosition;
	private String filterProperties;
	private String paged;
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
	private String gridStyleClass;
	private String gridLookId;
	public String getComponentType() {
		return ComboGridComponent.COMPONENT_TYPE;
	}

	public final String getSelectionListener() {
		return selectionListeners;
	}

	public final void setSelectionListener(String selectionListeners) {
		this.selectionListeners = selectionListeners;
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

	public final String getSuggestionDelayMs() {
		return suggestionDelayMs;
	}

	public final void setSuggestionDelayMs(String suggestionDelayMs) {
		this.suggestionDelayMs = suggestionDelayMs;
	}

	public final String getSuggestionMinChars() {
		return suggestionMinChars;
	}

	public final void setSuggestionMinChars(String suggestionMinChars) {
		this.suggestionMinChars = suggestionMinChars;
	}

	public final String getRowCountVar() {
		return rowCountVar;
	}

	public final void setRowCountVar(String rowCountVar) {
		this.rowCountVar = rowCountVar;
	}

	public final String getRowIndexVar() {
		return rowIndexVar;
	}

	public final void setRowIndexVar(String rowIndexVar) {
		this.rowIndexVar = rowIndexVar;
	}

	public final String getValueColumnId() {
		return valueColumnId;
	}

	public final void setValueColumnId(String valueColumnId) {
		this.valueColumnId = valueColumnId;
	}

	public final String getLabelColumnId() {
		return labelColumnId;
	}

	public final void setLabelColumnId(String labelColumnId) {
		this.labelColumnId = labelColumnId;
	}

	public final String getSelectedValue() {
		return selectedValue;
	}

	public final void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public final String getPopupWidth() {
		return popupWidth;
	}

	public final void setPopupWidth(String popupWidth) {
		this.popupWidth = popupWidth;
	}

	public final String getPopupHeight() {
		return popupHeight;
	}

	public final void setPopupHeight(String popupHeight) {
		this.popupHeight = popupHeight;
	}

	public final String getValueFormat() {
		return valueFormat;
	}

	public final void setValueFormat(String valueFormat) {
		this.valueFormat = valueFormat;
	}

	public final String getPagerStyleClass() {
		return pagerStyleClass;
	}

	public final void setPagerStyleClass(String pagerStyleClass) {
		this.pagerStyleClass = pagerStyleClass;
	}

	public final String getPagerLookId() {
		return pagerLookId;
	}

	public final void setPagerLookId(String pagerLookId) {
		this.pagerLookId = pagerLookId;
	}

	public final String getGridStyleClass() {
		return gridStyleClass;
	}

	public final void setGridStyleClass(String gridStyleClass) {
		this.gridStyleClass = gridStyleClass;
	}

	public final String getGridLookId() {
		return gridLookId;
	}

	public final void setGridLookId(String gridLookId) {
		this.gridLookId = gridLookId;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ComboGridComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  disabled='"+disabled+"'");
			LOG.debug("  required='"+required+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  rowStyleClass='"+rowStyleClass+"'");
			LOG.debug("  manyResultsMessage='"+manyResultsMessage+"'");
			LOG.debug("  message='"+message+"'");
			LOG.debug("  oneResultMessage='"+oneResultMessage+"'");
			LOG.debug("  zeroResultMessage='"+zeroResultMessage+"'");
			LOG.debug("  horizontalScrollPosition='"+horizontalScrollPosition+"'");
			LOG.debug("  verticalScrollPosition='"+verticalScrollPosition+"'");
			LOG.debug("  filterProperties='"+filterProperties+"'");
			LOG.debug("  paged='"+paged+"'");
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
		Application application = facesContext.getApplication();

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
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
				ValueBinding vb = application.createValueBinding(filterProperties);
				component.setValueBinding(Properties.FILTER_PROPERTIES, vb);
		}

		if (paged != null) {
			if (isValueReference(paged)) {
				ValueBinding vb = application.createValueBinding(paged);
				component.setValueBinding(Properties.PAGED, vb);

			} else {
				component.setPaged(getBool(paged));
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
	}

	public void release() {
		selectionListeners = null;
		disabled = null;
		required = null;
		readOnly = null;
		border = null;
		rowStyleClass = null;
		manyResultsMessage = null;
		message = null;
		oneResultMessage = null;
		zeroResultMessage = null;
		horizontalScrollPosition = null;
		verticalScrollPosition = null;
		filterProperties = null;
		paged = null;
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
