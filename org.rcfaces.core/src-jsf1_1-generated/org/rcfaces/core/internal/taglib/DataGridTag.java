package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.DataGridComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class DataGridTag extends AbstractDataTag implements Tag {


	private static final Log LOG=LogFactory.getLog(DataGridTag.class);

	private String selectionListeners;
	private String selectable;
	private String selectionCardinality;
	private String selectedValues;
	private String checkListeners;
	private String checkable;
	private String checkCardinality;
	private String checkedValues;
	private String additionalInformationListeners;
	private String additionalInformationValues;
	private String clientAdditionalInformationFullState;
	private String additionalInformationCardinality;
	private String doubleClickListeners;
	private String loadListeners;
	private String required;
	private String border;
	private String rowStyleClass;
	private String readOnly;
	private String disabled;
	private String horizontalScrollPosition;
	private String verticalScrollPosition;
	private String filterProperties;
	private String showValue;
	private String preference;
	private String paged;
	private String clientSelectionFullState;
	private String clientCheckFullState;
	private String headerVisible;
	private String cursorValue;
	private String rowValueColumnId;
	private String rowCountVar;
	private String rowIndexVar;
	private String action;
	private String actionListeners;
	public String getComponentType() {
		return DataGridComponent.COMPONENT_TYPE;
	}

	public final String getSelectionListener() {
		return selectionListeners;
	}

	public final void setSelectionListener(String selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final String getSelectable() {
		return selectable;
	}

	public final void setSelectable(String selectable) {
		this.selectable = selectable;
	}

	public final String getSelectionCardinality() {
		return selectionCardinality;
	}

	public final void setSelectionCardinality(String selectionCardinality) {
		this.selectionCardinality = selectionCardinality;
	}

	public final String getSelectedValues() {
		return selectedValues;
	}

	public final void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}

	public final String getCheckListener() {
		return checkListeners;
	}

	public final void setCheckListener(String checkListeners) {
		this.checkListeners = checkListeners;
	}

	public final String getCheckable() {
		return checkable;
	}

	public final void setCheckable(String checkable) {
		this.checkable = checkable;
	}

	public final String getCheckCardinality() {
		return checkCardinality;
	}

	public final void setCheckCardinality(String checkCardinality) {
		this.checkCardinality = checkCardinality;
	}

	public final String getCheckedValues() {
		return checkedValues;
	}

	public final void setCheckedValues(String checkedValues) {
		this.checkedValues = checkedValues;
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

	public final String getDoubleClickListener() {
		return doubleClickListeners;
	}

	public final void setDoubleClickListener(String doubleClickListeners) {
		this.doubleClickListeners = doubleClickListeners;
	}

	public final String getLoadListener() {
		return loadListeners;
	}

	public final void setLoadListener(String loadListeners) {
		this.loadListeners = loadListeners;
	}

	public final String getRequired() {
		return required;
	}

	public final void setRequired(String required) {
		this.required = required;
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

	public final String getReadOnly() {
		return readOnly;
	}

	public final void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public final String getDisabled() {
		return disabled;
	}

	public final void setDisabled(String disabled) {
		this.disabled = disabled;
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

	public final String getShowValue() {
		return showValue;
	}

	public final void setShowValue(String showValue) {
		this.showValue = showValue;
	}

	public final String getPreference() {
		return preference;
	}

	public final void setPreference(String preference) {
		this.preference = preference;
	}

	public final String getPaged() {
		return paged;
	}

	public final void setPaged(String paged) {
		this.paged = paged;
	}

	public final String getClientSelectionFullState() {
		return clientSelectionFullState;
	}

	public final void setClientSelectionFullState(String clientSelectionFullState) {
		this.clientSelectionFullState = clientSelectionFullState;
	}

	public final String getClientCheckFullState() {
		return clientCheckFullState;
	}

	public final void setClientCheckFullState(String clientCheckFullState) {
		this.clientCheckFullState = clientCheckFullState;
	}

	public final String getHeaderVisible() {
		return headerVisible;
	}

	public final void setHeaderVisible(String headerVisible) {
		this.headerVisible = headerVisible;
	}

	public final String getCursorValue() {
		return cursorValue;
	}

	public final void setCursorValue(String cursorValue) {
		this.cursorValue = cursorValue;
	}

	public final void setRowValueColumnId(String rowValueColumnId) {
		this.rowValueColumnId = rowValueColumnId;
	}

	public final void setRowCountVar(String rowCountVar) {
		this.rowCountVar = rowCountVar;
	}

	public final void setRowIndexVar(String rowIndexVar) {
		this.rowIndexVar = rowIndexVar;
	}

	public final void setAction(String action) {
		this.action=action;
	}

	public final String getAction() {
		return action;
	}

	public final void setActionListener(String listeners) {
		this.actionListeners = listeners;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (DataGridComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  selectable='"+selectable+"'");
			LOG.debug("  selectionCardinality='"+selectionCardinality+"'");
			LOG.debug("  selectedValues='"+selectedValues+"'");
			LOG.debug("  checkable='"+checkable+"'");
			LOG.debug("  checkCardinality='"+checkCardinality+"'");
			LOG.debug("  checkedValues='"+checkedValues+"'");
			LOG.debug("  additionalInformationValues='"+additionalInformationValues+"'");
			LOG.debug("  clientAdditionalInformationFullState='"+clientAdditionalInformationFullState+"'");
			LOG.debug("  additionalInformationCardinality='"+additionalInformationCardinality+"'");
			LOG.debug("  required='"+required+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  rowStyleClass='"+rowStyleClass+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  disabled='"+disabled+"'");
			LOG.debug("  horizontalScrollPosition='"+horizontalScrollPosition+"'");
			LOG.debug("  verticalScrollPosition='"+verticalScrollPosition+"'");
			LOG.debug("  filterProperties='"+filterProperties+"'");
			LOG.debug("  showValue='"+showValue+"'");
			LOG.debug("  preference='"+preference+"'");
			LOG.debug("  paged='"+paged+"'");
			LOG.debug("  clientSelectionFullState='"+clientSelectionFullState+"'");
			LOG.debug("  clientCheckFullState='"+clientCheckFullState+"'");
			LOG.debug("  headerVisible='"+headerVisible+"'");
			LOG.debug("  cursorValue='"+cursorValue+"'");
			LOG.debug("  rowValueColumnId='"+rowValueColumnId+"'");
			LOG.debug("  rowCountVar='"+rowCountVar+"'");
			LOG.debug("  rowIndexVar='"+rowIndexVar+"'");
			LOG.debug("  action='"+action+"'");
			LOG.debug("  actionListeners='"+actionListeners+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof DataGridComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'DataGridComponent'.");
		}

		DataGridComponent component = (DataGridComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (selectable != null) {
			if (isValueReference(selectable)) {
				ValueBinding vb = application.createValueBinding(selectable);
				component.setValueBinding(Properties.SELECTABLE, vb);

			} else {
				component.setSelectable(getBool(selectable));
			}
		}

		if (selectionCardinality != null) {
			if (isValueReference(selectionCardinality)) {
				ValueBinding vb = application.createValueBinding(selectionCardinality);
				component.setValueBinding(Properties.SELECTION_CARDINALITY, vb);

			} else {
				component.setSelectionCardinality(selectionCardinality);
			}
		}

		if (selectedValues != null) {
				ValueBinding vb = application.createValueBinding(selectedValues);
				component.setValueBinding(Properties.SELECTED_VALUES, vb);
		}

		if (checkListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.CHECK_LISTENER_TYPE, checkListeners);
		}

		if (checkable != null) {
			if (isValueReference(checkable)) {
				ValueBinding vb = application.createValueBinding(checkable);
				component.setValueBinding(Properties.CHECKABLE, vb);

			} else {
				component.setCheckable(getBool(checkable));
			}
		}

		if (checkCardinality != null) {
			if (isValueReference(checkCardinality)) {
				ValueBinding vb = application.createValueBinding(checkCardinality);
				component.setValueBinding(Properties.CHECK_CARDINALITY, vb);

			} else {
				component.setCheckCardinality(checkCardinality);
			}
		}

		if (checkedValues != null) {
				ValueBinding vb = application.createValueBinding(checkedValues);
				component.setValueBinding(Properties.CHECKED_VALUES, vb);
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
				component.setClientAdditionalInformationFullState(getBool(clientAdditionalInformationFullState));
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

		if (doubleClickListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.DOUBLE_CLICK_LISTENER_TYPE, doubleClickListeners);
		}

		if (loadListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.LOAD_LISTENER_TYPE, loadListeners);
		}

		if (required != null) {
			if (isValueReference(required)) {
				ValueBinding vb = application.createValueBinding(required);
				component.setValueBinding(Properties.REQUIRED, vb);

			} else {
				component.setRequired(getBool(required));
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

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);
				component.setValueBinding(Properties.READ_ONLY, vb);

			} else {
				component.setReadOnly(getBool(readOnly));
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

		if (showValue != null) {
			if (isValueReference(showValue)) {
				ValueBinding vb = application.createValueBinding(showValue);
				component.setValueBinding(Properties.SHOW_VALUE, vb);

			} else {
				component.setShowValue(showValue);
			}
		}

		if (preference != null) {
				ValueBinding vb = application.createValueBinding(preference);
				component.setValueBinding(Properties.PREFERENCE, vb);
		}

		if (paged != null) {
			if (isValueReference(paged)) {
				ValueBinding vb = application.createValueBinding(paged);
				component.setValueBinding(Properties.PAGED, vb);

			} else {
				component.setPaged(getBool(paged));
			}
		}

		if (clientSelectionFullState != null) {
			if (isValueReference(clientSelectionFullState)) {
				ValueBinding vb = application.createValueBinding(clientSelectionFullState);
				component.setValueBinding(Properties.CLIENT_SELECTION_FULL_STATE, vb);

			} else {
				component.setClientSelectionFullState(getBool(clientSelectionFullState));
			}
		}

		if (clientCheckFullState != null) {
			if (isValueReference(clientCheckFullState)) {
				ValueBinding vb = application.createValueBinding(clientCheckFullState);
				component.setValueBinding(Properties.CLIENT_CHECK_FULL_STATE, vb);

			} else {
				component.setClientCheckFullState(getBool(clientCheckFullState));
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

		if (cursorValue != null) {
			if (isValueReference(cursorValue)) {
				ValueBinding vb = application.createValueBinding(cursorValue);
				component.setValueBinding(Properties.CURSOR_VALUE, vb);

			} else {
				component.setCursorValue(cursorValue);
			}
		}

		if (rowValueColumnId != null) {
			if (isValueReference(rowValueColumnId)) {
				ValueBinding vb = application.createValueBinding(rowValueColumnId);
				component.setValueBinding(Properties.ROW_VALUE_COLUMN_ID, vb);

			} else {
				component.setRowValueColumnId(rowValueColumnId);
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

		if (action != null) {
			ListenersTools1_1.parseAction(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, action);
		}

		if (actionListeners != null) {
			ListenersTools1_1.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, actionListeners, true);
		}
	}

	public void release() {
		selectionListeners = null;
		selectable = null;
		selectionCardinality = null;
		selectedValues = null;
		checkListeners = null;
		checkable = null;
		checkCardinality = null;
		checkedValues = null;
		additionalInformationListeners = null;
		additionalInformationValues = null;
		clientAdditionalInformationFullState = null;
		additionalInformationCardinality = null;
		doubleClickListeners = null;
		loadListeners = null;
		required = null;
		border = null;
		rowStyleClass = null;
		readOnly = null;
		disabled = null;
		horizontalScrollPosition = null;
		verticalScrollPosition = null;
		filterProperties = null;
		showValue = null;
		preference = null;
		paged = null;
		clientSelectionFullState = null;
		clientCheckFullState = null;
		headerVisible = null;
		cursorValue = null;
		rowValueColumnId = null;
		rowCountVar = null;
		rowIndexVar = null;
		action = null;
		actionListeners = null;

		super.release();
	}

}
