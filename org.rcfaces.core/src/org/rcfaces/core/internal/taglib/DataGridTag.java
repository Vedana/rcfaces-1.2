package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.DataGridComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class DataGridTag extends AbstractGridTag implements Tag {


	private static final Log LOG=LogFactory.getLog(DataGridTag.class);

	private String selectionListeners;
	private String selectable;
	private String selectionCardinality;
	private String checkListeners;
	private String checkable;
	private String checkCardinality;
	private String doubleClickListeners;
	private String required;
	private String border;
	private String readOnly;
	private String disabled;
	private String horizontalScrollPosition;
	private String verticalScrollPosition;
	private String filterProperties;
	private String preference;
	private String headerVisible;
	private String selectedValues;
	private String checkedValues;
	private String sortedColumnIds;
	private String columnsOrder;
	private String rowValueColumnId;
	private String rowCountVar;
	private String rowIndexVar;
	private String clientSelectionFullState;
	private String clientCheckFullState;
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

	public final String getDoubleClickListener() {
		return doubleClickListeners;
	}

	public final void setDoubleClickListener(String doubleClickListeners) {
		this.doubleClickListeners = doubleClickListeners;
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

	public final String getPreference() {
		return preference;
	}

	public final void setPreference(String preference) {
		this.preference = preference;
	}

	public final String getHeaderVisible() {
		return headerVisible;
	}

	public final void setHeaderVisible(String headerVisible) {
		this.headerVisible = headerVisible;
	}

	public final String getSelectedValues() {
		return selectedValues;
	}

	public final void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}

	public final String getCheckedValues() {
		return checkedValues;
	}

	public final void setCheckedValues(String checkedValues) {
		this.checkedValues = checkedValues;
	}

	public final String getSortedColumnIds() {
		return sortedColumnIds;
	}

	public final void setSortedColumnIds(String sortedColumnIds) {
		this.sortedColumnIds = sortedColumnIds;
	}

	public final String getColumnsOrder() {
		return columnsOrder;
	}

	public final void setColumnsOrder(String columnsOrder) {
		this.columnsOrder = columnsOrder;
	}

	public final String getRowValueColumnId() {
		return rowValueColumnId;
	}

	public final void setRowValueColumnId(String rowValueColumnId) {
		this.rowValueColumnId = rowValueColumnId;
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

	public final void setAction(String action) {
		this.action=action;
	}

	public final String getAction() {
		return action;
	}

	public final void setActionListener(String listeners) {
		this.actionListeners = listeners;
	}

	public final String getActionListener() {
		return actionListeners;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (DataGridComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  selectable='"+selectable+"'");
			LOG.debug("  selectionCardinality='"+selectionCardinality+"'");
			LOG.debug("  checkable='"+checkable+"'");
			LOG.debug("  checkCardinality='"+checkCardinality+"'");
			LOG.debug("  required='"+required+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  disabled='"+disabled+"'");
			LOG.debug("  horizontalScrollPosition='"+horizontalScrollPosition+"'");
			LOG.debug("  verticalScrollPosition='"+verticalScrollPosition+"'");
			LOG.debug("  filterProperties='"+filterProperties+"'");
			LOG.debug("  preference='"+preference+"'");
			LOG.debug("  headerVisible='"+headerVisible+"'");
			LOG.debug("  selectedValues='"+selectedValues+"'");
			LOG.debug("  checkedValues='"+checkedValues+"'");
			LOG.debug("  sortedColumnIds='"+sortedColumnIds+"'");
			LOG.debug("  columnsOrder='"+columnsOrder+"'");
			LOG.debug("  rowValueColumnId='"+rowValueColumnId+"'");
			LOG.debug("  rowCountVar='"+rowCountVar+"'");
			LOG.debug("  rowIndexVar='"+rowIndexVar+"'");
			LOG.debug("  clientSelectionFullState='"+clientSelectionFullState+"'");
			LOG.debug("  clientCheckFullState='"+clientCheckFullState+"'");
			LOG.debug("  action='"+action+"'");
			LOG.debug("  actionListeners='"+actionListeners+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof DataGridComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'DataGridComponent'.");
		}

		DataGridComponent component = (DataGridComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (selectionListeners != null) {
			parseActionListener(application, component, SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (selectable != null) {
			if (isValueReference(selectable)) {
				ValueBinding vb = application.createValueBinding(selectable);

				component.setSelectable(vb);
			} else {
				component.setSelectable(getBool(selectable));
			}
		}

		if (selectionCardinality != null) {
			if (isValueReference(selectionCardinality)) {
				ValueBinding vb = application.createValueBinding(selectionCardinality);

				component.setSelectionCardinality(vb);
			} else {
				component.setSelectionCardinality(selectionCardinality);
			}
		}

		if (checkListeners != null) {
			parseActionListener(application, component, CHECK_LISTENER_TYPE, checkListeners);
		}

		if (checkable != null) {
			if (isValueReference(checkable)) {
				ValueBinding vb = application.createValueBinding(checkable);

				component.setCheckable(vb);
			} else {
				component.setCheckable(getBool(checkable));
			}
		}

		if (checkCardinality != null) {
			if (isValueReference(checkCardinality)) {
				ValueBinding vb = application.createValueBinding(checkCardinality);

				component.setCheckCardinality(vb);
			} else {
				component.setCheckCardinality(checkCardinality);
			}
		}

		if (doubleClickListeners != null) {
			parseActionListener(application, component, DOUBLE_CLICK_LISTENER_TYPE, doubleClickListeners);
		}

		if (required != null) {
			if (isValueReference(required)) {
				ValueBinding vb = application.createValueBinding(required);

				component.setRequired(vb);
			} else {
				component.setRequired(getBool(required));
			}
		}

		if (border != null) {
			if (isValueReference(border)) {
				ValueBinding vb = application.createValueBinding(border);

				component.setBorder(vb);
			} else {
				component.setBorder(getBool(border));
			}
		}

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);

				component.setReadOnly(vb);
			} else {
				component.setReadOnly(getBool(readOnly));
			}
		}

		if (disabled != null) {
			if (isValueReference(disabled)) {
				ValueBinding vb = application.createValueBinding(disabled);

				component.setDisabled(vb);
			} else {
				component.setDisabled(getBool(disabled));
			}
		}

		if (horizontalScrollPosition != null) {
			if (isValueReference(horizontalScrollPosition)) {
				ValueBinding vb = application.createValueBinding(horizontalScrollPosition);

				component.setHorizontalScrollPosition(vb);
			} else {
				component.setHorizontalScrollPosition(horizontalScrollPosition);
			}
		}

		if (verticalScrollPosition != null) {
			if (isValueReference(verticalScrollPosition)) {
				ValueBinding vb = application.createValueBinding(verticalScrollPosition);

				component.setVerticalScrollPosition(vb);
			} else {
				component.setVerticalScrollPosition(verticalScrollPosition);
			}
		}

		if (filterProperties != null) {
				ValueBinding vb = application.createValueBinding(filterProperties);

				component.setFilterProperties(vb);
		}

		if (preference != null) {
				ValueBinding vb = application.createValueBinding(preference);

				component.setPreference(vb);
		}

		if (headerVisible != null) {
			if (isValueReference(headerVisible)) {
				ValueBinding vb = application.createValueBinding(headerVisible);
				component.setHeaderVisible(vb);
			} else {
				component.setHeaderVisible(getBool(headerVisible));
			}
		}

		if (selectedValues != null) {
			if (isValueReference(selectedValues)) {
				ValueBinding vb = application.createValueBinding(selectedValues);
				component.setSelectedValues(vb);
			} else {
				component.setSelectedValues(selectedValues);
			}
		}

		if (checkedValues != null) {
			if (isValueReference(checkedValues)) {
				ValueBinding vb = application.createValueBinding(checkedValues);
				component.setCheckedValues(vb);
			} else {
				component.setCheckedValues(checkedValues);
			}
		}

		if (sortedColumnIds != null) {
			if (isValueReference(sortedColumnIds)) {
				ValueBinding vb = application.createValueBinding(sortedColumnIds);
				component.setSortedColumnIds(vb);
			} else {
				component.setSortedColumnIds(sortedColumnIds);
			}
		}

		if (columnsOrder != null) {
			if (isValueReference(columnsOrder)) {
				ValueBinding vb = application.createValueBinding(columnsOrder);
				component.setColumnsOrder(vb);
			} else {
				component.setColumnsOrder(columnsOrder);
			}
		}

		if (rowValueColumnId != null) {
			if (isValueReference(rowValueColumnId)) {
				ValueBinding vb = application.createValueBinding(rowValueColumnId);
				component.setRowValueColumnId(vb);
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

		if (clientSelectionFullState != null) {
			if (isValueReference(clientSelectionFullState)) {
				ValueBinding vb = application.createValueBinding(clientSelectionFullState);
				component.setClientSelectionFullState(vb);
			} else {
				component.setClientSelectionFullState(getBool(clientSelectionFullState));
			}
		}

		if (clientCheckFullState != null) {
			if (isValueReference(clientCheckFullState)) {
				ValueBinding vb = application.createValueBinding(clientCheckFullState);
				component.setClientCheckFullState(vb);
			} else {
				component.setClientCheckFullState(getBool(clientCheckFullState));
			}
		}

		if (action != null) {
			parseAction(application, component, SELECTION_LISTENER_TYPE, action, true);
		}

		if (actionListeners != null) {
			parseActionListener(application, component, SELECTION_LISTENER_TYPE, actionListeners, true);
		}
	}

	public void release() {
		selectionListeners = null;
		selectable = null;
		selectionCardinality = null;
		checkListeners = null;
		checkable = null;
		checkCardinality = null;
		doubleClickListeners = null;
		required = null;
		border = null;
		readOnly = null;
		disabled = null;
		horizontalScrollPosition = null;
		verticalScrollPosition = null;
		filterProperties = null;
		preference = null;
		headerVisible = null;
		selectedValues = null;
		checkedValues = null;
		sortedColumnIds = null;
		columnsOrder = null;
		rowValueColumnId = null;
		rowCountVar = null;
		rowIndexVar = null;
		clientSelectionFullState = null;
		clientCheckFullState = null;
		action = null;
		actionListeners = null;

		super.release();
	}

}
