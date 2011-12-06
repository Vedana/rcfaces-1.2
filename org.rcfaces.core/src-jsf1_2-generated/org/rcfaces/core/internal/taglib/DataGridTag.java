package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.DataGridComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class DataGridTag extends AbstractDataTag implements Tag {


	private static final Log LOG=LogFactory.getLog(DataGridTag.class);

	private ValueExpression dragListeners;
	private ValueExpression dragEffects;
	private ValueExpression dragTypes;
	private ValueExpression draggable;
	private ValueExpression dropListeners;
	private ValueExpression dropCompleteListeners;
	private ValueExpression dropEffects;
	private ValueExpression dropTypes;
	private ValueExpression droppable;
	private ValueExpression selectionListeners;
	private ValueExpression selectable;
	private ValueExpression selectionCardinality;
	private ValueExpression selectedValues;
	private ValueExpression checkListeners;
	private ValueExpression checkable;
	private ValueExpression checkCardinality;
	private ValueExpression checkedValues;
	private ValueExpression additionalInformationListeners;
	private ValueExpression additionalInformationValues;
	private ValueExpression clientAdditionalInformationFullState;
	private ValueExpression additionalInformationCardinality;
	private ValueExpression doubleClickListeners;
	private ValueExpression loadListeners;
	private ValueExpression required;
	private ValueExpression border;
	private ValueExpression rowStyleClass;
	private ValueExpression emptyDataMessage;
	private ValueExpression readOnly;
	private ValueExpression disabled;
	private ValueExpression horizontalScrollPosition;
	private ValueExpression verticalScrollPosition;
	private ValueExpression filterProperties;
	private ValueExpression showValue;
	private ValueExpression keySearchColumnId;
	private ValueExpression preferences;
	private ValueExpression paged;
	private ValueExpression fullCriteriaCount;
	private ValueExpression clientSelectionFullState;
	private ValueExpression clientCheckFullState;
	private ValueExpression headerVisible;
	private ValueExpression rowToolTipId;
	private ValueExpression cursorValue;
	private ValueExpression rowDragTypes;
	private ValueExpression rowDragEffects;
	private ValueExpression rowDropTypes;
	private ValueExpression rowDropEffects;
	private ValueExpression selectedCriteriaColumns;
	private ValueExpression rowValueColumnId;
	private ValueExpression rowLabelColumnId;
	private ValueExpression rowCountVar;
	private ValueExpression rowIndexVar;
	private ValueExpression cellTextWrap;
	private ValueExpression bodyDroppable;
	private ValueExpression actionListeners;
	private ValueExpression action;
	public String getComponentType() {
		return DataGridComponent.COMPONENT_TYPE;
	}

	public void setDragListener(ValueExpression dragListeners) {
		this.dragListeners = dragListeners;
	}

	public void setDragEffects(ValueExpression dragEffects) {
		this.dragEffects = dragEffects;
	}

	public void setDragTypes(ValueExpression dragTypes) {
		this.dragTypes = dragTypes;
	}

	public void setDraggable(ValueExpression draggable) {
		this.draggable = draggable;
	}

	public void setDropListener(ValueExpression dropListeners) {
		this.dropListeners = dropListeners;
	}

	public void setDropCompleteListener(ValueExpression dropCompleteListeners) {
		this.dropCompleteListeners = dropCompleteListeners;
	}

	public void setDropEffects(ValueExpression dropEffects) {
		this.dropEffects = dropEffects;
	}

	public void setDropTypes(ValueExpression dropTypes) {
		this.dropTypes = dropTypes;
	}

	public void setDroppable(ValueExpression droppable) {
		this.droppable = droppable;
	}

	public void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public void setSelectable(ValueExpression selectable) {
		this.selectable = selectable;
	}

	public void setSelectionCardinality(ValueExpression selectionCardinality) {
		this.selectionCardinality = selectionCardinality;
	}

	public void setSelectedValues(ValueExpression selectedValues) {
		this.selectedValues = selectedValues;
	}

	public void setCheckListener(ValueExpression checkListeners) {
		this.checkListeners = checkListeners;
	}

	public void setCheckable(ValueExpression checkable) {
		this.checkable = checkable;
	}

	public void setCheckCardinality(ValueExpression checkCardinality) {
		this.checkCardinality = checkCardinality;
	}

	public void setCheckedValues(ValueExpression checkedValues) {
		this.checkedValues = checkedValues;
	}

	public void setAdditionalInformationListener(ValueExpression additionalInformationListeners) {
		this.additionalInformationListeners = additionalInformationListeners;
	}

	public void setAdditionalInformationValues(ValueExpression additionalInformationValues) {
		this.additionalInformationValues = additionalInformationValues;
	}

	public void setClientAdditionalInformationFullState(ValueExpression clientAdditionalInformationFullState) {
		this.clientAdditionalInformationFullState = clientAdditionalInformationFullState;
	}

	public void setAdditionalInformationCardinality(ValueExpression additionalInformationCardinality) {
		this.additionalInformationCardinality = additionalInformationCardinality;
	}

	public void setDoubleClickListener(ValueExpression doubleClickListeners) {
		this.doubleClickListeners = doubleClickListeners;
	}

	public void setLoadListener(ValueExpression loadListeners) {
		this.loadListeners = loadListeners;
	}

	public void setRequired(ValueExpression required) {
		this.required = required;
	}

	public void setBorder(ValueExpression border) {
		this.border = border;
	}

	public void setRowStyleClass(ValueExpression rowStyleClass) {
		this.rowStyleClass = rowStyleClass;
	}

	public void setEmptyDataMessage(ValueExpression emptyDataMessage) {
		this.emptyDataMessage = emptyDataMessage;
	}

	public void setReadOnly(ValueExpression readOnly) {
		this.readOnly = readOnly;
	}

	public void setDisabled(ValueExpression disabled) {
		this.disabled = disabled;
	}

	public void setHorizontalScrollPosition(ValueExpression horizontalScrollPosition) {
		this.horizontalScrollPosition = horizontalScrollPosition;
	}

	public void setVerticalScrollPosition(ValueExpression verticalScrollPosition) {
		this.verticalScrollPosition = verticalScrollPosition;
	}

	public void setFilterProperties(ValueExpression filterProperties) {
		this.filterProperties = filterProperties;
	}

	public void setShowValue(ValueExpression showValue) {
		this.showValue = showValue;
	}

	public void setKeySearchColumnId(ValueExpression keySearchColumnId) {
		this.keySearchColumnId = keySearchColumnId;
	}

	public void setPreferences(ValueExpression preferences) {
		this.preferences = preferences;
	}

	public void setPaged(ValueExpression paged) {
		this.paged = paged;
	}

	public void setFullCriteriaCount(ValueExpression fullCriteriaCount) {
		this.fullCriteriaCount = fullCriteriaCount;
	}

	public void setClientSelectionFullState(ValueExpression clientSelectionFullState) {
		this.clientSelectionFullState = clientSelectionFullState;
	}

	public void setClientCheckFullState(ValueExpression clientCheckFullState) {
		this.clientCheckFullState = clientCheckFullState;
	}

	public void setHeaderVisible(ValueExpression headerVisible) {
		this.headerVisible = headerVisible;
	}

	public void setRowToolTipId(ValueExpression rowToolTipId) {
		this.rowToolTipId = rowToolTipId;
	}

	public void setCursorValue(ValueExpression cursorValue) {
		this.cursorValue = cursorValue;
	}

	public void setRowDragTypes(ValueExpression rowDragTypes) {
		this.rowDragTypes = rowDragTypes;
	}

	public void setRowDragEffects(ValueExpression rowDragEffects) {
		this.rowDragEffects = rowDragEffects;
	}

	public void setRowDropTypes(ValueExpression rowDropTypes) {
		this.rowDropTypes = rowDropTypes;
	}

	public void setRowDropEffects(ValueExpression rowDropEffects) {
		this.rowDropEffects = rowDropEffects;
	}

	public void setSelectedCriteriaColumns(ValueExpression selectedCriteriaColumns) {
		this.selectedCriteriaColumns = selectedCriteriaColumns;
	}

	public void setRowValueColumnId(ValueExpression rowValueColumnId) {
		this.rowValueColumnId = rowValueColumnId;
	}

	public void setRowLabelColumnId(ValueExpression rowLabelColumnId) {
		this.rowLabelColumnId = rowLabelColumnId;
	}

	public void setRowCountVar(ValueExpression rowCountVar) {
		this.rowCountVar = rowCountVar;
	}

	public void setRowIndexVar(ValueExpression rowIndexVar) {
		this.rowIndexVar = rowIndexVar;
	}

	public void setCellTextWrap(ValueExpression cellTextWrap) {
		this.cellTextWrap = cellTextWrap;
	}

	public void setBodyDroppable(ValueExpression bodyDroppable) {
		this.bodyDroppable = bodyDroppable;
	}

	public final void setAction(ValueExpression action) {
		this.action=action;
	}

	public final void setActionListener(ValueExpression listeners) {
		this.actionListeners = listeners;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (DataGridComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  dragEffects='"+dragEffects+"'");
			LOG.debug("  dragTypes='"+dragTypes+"'");
			LOG.debug("  draggable='"+draggable+"'");
			LOG.debug("  dropEffects='"+dropEffects+"'");
			LOG.debug("  dropTypes='"+dropTypes+"'");
			LOG.debug("  droppable='"+droppable+"'");
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
			LOG.debug("  emptyDataMessage='"+emptyDataMessage+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  disabled='"+disabled+"'");
			LOG.debug("  horizontalScrollPosition='"+horizontalScrollPosition+"'");
			LOG.debug("  verticalScrollPosition='"+verticalScrollPosition+"'");
			LOG.debug("  filterProperties='"+filterProperties+"'");
			LOG.debug("  showValue='"+showValue+"'");
			LOG.debug("  keySearchColumnId='"+keySearchColumnId+"'");
			LOG.debug("  preferences='"+preferences+"'");
			LOG.debug("  paged='"+paged+"'");
			LOG.debug("  fullCriteriaCount='"+fullCriteriaCount+"'");
			LOG.debug("  clientSelectionFullState='"+clientSelectionFullState+"'");
			LOG.debug("  clientCheckFullState='"+clientCheckFullState+"'");
			LOG.debug("  headerVisible='"+headerVisible+"'");
			LOG.debug("  rowToolTipId='"+rowToolTipId+"'");
			LOG.debug("  cursorValue='"+cursorValue+"'");
			LOG.debug("  rowDragTypes='"+rowDragTypes+"'");
			LOG.debug("  rowDragEffects='"+rowDragEffects+"'");
			LOG.debug("  rowDropTypes='"+rowDropTypes+"'");
			LOG.debug("  rowDropEffects='"+rowDropEffects+"'");
			LOG.debug("  selectedCriteriaColumns='"+selectedCriteriaColumns+"'");
			LOG.debug("  rowValueColumnId='"+rowValueColumnId+"'");
			LOG.debug("  rowLabelColumnId='"+rowLabelColumnId+"'");
			LOG.debug("  rowCountVar='"+rowCountVar+"'");
			LOG.debug("  rowIndexVar='"+rowIndexVar+"'");
			LOG.debug("  cellTextWrap='"+cellTextWrap+"'");
			LOG.debug("  bodyDroppable='"+bodyDroppable+"'");
			LOG.debug("  action='"+action+"'");
			LOG.debug("  actionListeners='"+actionListeners+"'");
		}
		if ((uiComponent instanceof DataGridComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'DataGridComponent'.");
		}

		super.setProperties(uiComponent);

		DataGridComponent component = (DataGridComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (dragListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.DRAG_LISTENER_TYPE, dragListeners);
		}

		if (dragEffects != null) {
			if (dragEffects.isLiteralText()==false) {
				component.setValueExpression(Properties.DRAG_EFFECTS, dragEffects);

			} else {
				component.setDragEffects(dragEffects.getExpressionString());
			}
		}

		if (dragTypes != null) {
			if (dragTypes.isLiteralText()==false) {
				component.setValueExpression(Properties.DRAG_TYPES, dragTypes);

			} else {
				component.setDragTypes(dragTypes.getExpressionString());
			}
		}

		if (draggable != null) {
			if (draggable.isLiteralText()==false) {
				component.setValueExpression(Properties.DRAGGABLE, draggable);

			} else {
				component.setDraggable(getBool(draggable.getExpressionString()));
			}
		}

		if (dropListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.DROP_LISTENER_TYPE, dropListeners);
		}

		if (dropCompleteListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.DROP_COMPLETE_LISTENER_TYPE, dropCompleteListeners);
		}

		if (dropEffects != null) {
			if (dropEffects.isLiteralText()==false) {
				component.setValueExpression(Properties.DROP_EFFECTS, dropEffects);

			} else {
				component.setDropEffects(dropEffects.getExpressionString());
			}
		}

		if (dropTypes != null) {
			if (dropTypes.isLiteralText()==false) {
				component.setValueExpression(Properties.DROP_TYPES, dropTypes);

			} else {
				component.setDropTypes(dropTypes.getExpressionString());
			}
		}

		if (droppable != null) {
			if (droppable.isLiteralText()==false) {
				component.setValueExpression(Properties.DROPPABLE, droppable);

			} else {
				component.setDroppable(getBool(droppable.getExpressionString()));
			}
		}

		if (selectionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (selectable != null) {
			if (selectable.isLiteralText()==false) {
				component.setValueExpression(Properties.SELECTABLE, selectable);

			} else {
				component.setSelectable(getBool(selectable.getExpressionString()));
			}
		}

		if (selectionCardinality != null) {
			if (selectionCardinality.isLiteralText()==false) {
				component.setValueExpression(Properties.SELECTION_CARDINALITY, selectionCardinality);

			} else {
				component.setSelectionCardinality(selectionCardinality.getExpressionString());
			}
		}

		if (selectedValues != null) {
				component.setValueExpression(Properties.SELECTED_VALUES, selectedValues);
		}

		if (checkListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.CHECK_LISTENER_TYPE, checkListeners);
		}

		if (checkable != null) {
			if (checkable.isLiteralText()==false) {
				component.setValueExpression(Properties.CHECKABLE, checkable);

			} else {
				component.setCheckable(getBool(checkable.getExpressionString()));
			}
		}

		if (checkCardinality != null) {
			if (checkCardinality.isLiteralText()==false) {
				component.setValueExpression(Properties.CHECK_CARDINALITY, checkCardinality);

			} else {
				component.setCheckCardinality(checkCardinality.getExpressionString());
			}
		}

		if (checkedValues != null) {
				component.setValueExpression(Properties.CHECKED_VALUES, checkedValues);
		}

		if (additionalInformationListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.ADDITIONAL_INFORMATION_LISTENER_TYPE, additionalInformationListeners);
		}

		if (additionalInformationValues != null) {
				component.setValueExpression(Properties.ADDITIONAL_INFORMATION_VALUES, additionalInformationValues);
		}

		if (clientAdditionalInformationFullState != null) {
			if (clientAdditionalInformationFullState.isLiteralText()==false) {
				component.setValueExpression(Properties.CLIENT_ADDITIONAL_INFORMATION_FULL_STATE, clientAdditionalInformationFullState);

			} else {
				component.setClientAdditionalInformationFullState(clientAdditionalInformationFullState.getExpressionString());
			}
		}

		if (additionalInformationCardinality != null) {
			if (additionalInformationCardinality.isLiteralText()==false) {
				component.setValueExpression(Properties.ADDITIONAL_INFORMATION_CARDINALITY, additionalInformationCardinality);

			} else {
				component.setAdditionalInformationCardinality(getInt(additionalInformationCardinality.getExpressionString()));
			}
		}

		if (doubleClickListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.DOUBLE_CLICK_LISTENER_TYPE, doubleClickListeners);
		}

		if (loadListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.LOAD_LISTENER_TYPE, loadListeners);
		}

		if (required != null) {
			if (required.isLiteralText()==false) {
				component.setValueExpression(Properties.REQUIRED, required);

			} else {
				component.setRequired(getBool(required.getExpressionString()));
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

		if (emptyDataMessage != null) {
			if (emptyDataMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.EMPTY_DATA_MESSAGE, emptyDataMessage);

			} else {
				component.setEmptyDataMessage(emptyDataMessage.getExpressionString());
			}
		}

		if (readOnly != null) {
			if (readOnly.isLiteralText()==false) {
				component.setValueExpression(Properties.READ_ONLY, readOnly);

			} else {
				component.setReadOnly(getBool(readOnly.getExpressionString()));
			}
		}

		if (disabled != null) {
			if (disabled.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLED, disabled);

			} else {
				component.setDisabled(getBool(disabled.getExpressionString()));
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
			if (filterProperties.isLiteralText()==false) {
				component.setValueExpression(Properties.FILTER_PROPERTIES, filterProperties);

			} else {
				component.setFilterProperties(filterProperties.getExpressionString());
			}
		}

		if (showValue != null) {
			if (showValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_VALUE, showValue);

			} else {
				component.setShowValue(showValue.getExpressionString());
			}
		}

		if (keySearchColumnId != null) {
			if (keySearchColumnId.isLiteralText()==false) {
				component.setValueExpression(Properties.KEY_SEARCH_COLUMN_ID, keySearchColumnId);

			} else {
				component.setKeySearchColumnId(keySearchColumnId.getExpressionString());
			}
		}

		if (preferences != null) {
				component.setValueExpression(Properties.PREFERENCES, preferences);
		}

		if (paged != null) {
			if (paged.isLiteralText()==false) {
				component.setValueExpression(Properties.PAGED, paged);

			} else {
				component.setPaged(getBool(paged.getExpressionString()));
			}
		}

		if (fullCriteriaCount != null) {
			if (fullCriteriaCount.isLiteralText()==false) {
				component.setValueExpression(Properties.FULL_CRITERIA_COUNT, fullCriteriaCount);

			} else {
				component.setFullCriteriaCount(getBool(fullCriteriaCount.getExpressionString()));
			}
		}

		if (clientSelectionFullState != null) {
			if (clientSelectionFullState.isLiteralText()==false) {
				component.setValueExpression(Properties.CLIENT_SELECTION_FULL_STATE, clientSelectionFullState);

			} else {
				component.setClientSelectionFullState(clientSelectionFullState.getExpressionString());
			}
		}

		if (clientCheckFullState != null) {
			if (clientCheckFullState.isLiteralText()==false) {
				component.setValueExpression(Properties.CLIENT_CHECK_FULL_STATE, clientCheckFullState);

			} else {
				component.setClientCheckFullState(clientCheckFullState.getExpressionString());
			}
		}

		if (headerVisible != null) {
			if (headerVisible.isLiteralText()==false) {
				component.setValueExpression(Properties.HEADER_VISIBLE, headerVisible);

			} else {
				component.setHeaderVisible(getBool(headerVisible.getExpressionString()));
			}
		}

		if (rowToolTipId != null) {
			if (rowToolTipId.isLiteralText()==false) {
				component.setValueExpression(Properties.ROW_TOOL_TIP_ID, rowToolTipId);

			} else {
				component.setRowToolTipId(rowToolTipId.getExpressionString());
			}
		}

		if (cursorValue != null) {
			if (cursorValue.isLiteralText()==false) {
				component.setValueExpression(Properties.CURSOR_VALUE, cursorValue);

			} else {
				component.setCursorValue(cursorValue.getExpressionString());
			}
		}

		if (rowDragTypes != null) {
			if (rowDragTypes.isLiteralText()==false) {
				component.setValueExpression(Properties.ROW_DRAG_TYPES, rowDragTypes);

			} else {
				component.setRowDragTypes(rowDragTypes.getExpressionString());
			}
		}

		if (rowDragEffects != null) {
			if (rowDragEffects.isLiteralText()==false) {
				component.setValueExpression(Properties.ROW_DRAG_EFFECTS, rowDragEffects);

			} else {
				component.setRowDragEffects(rowDragEffects.getExpressionString());
			}
		}

		if (rowDropTypes != null) {
			if (rowDropTypes.isLiteralText()==false) {
				component.setValueExpression(Properties.ROW_DROP_TYPES, rowDropTypes);

			} else {
				component.setRowDropTypes(rowDropTypes.getExpressionString());
			}
		}

		if (rowDropEffects != null) {
			if (rowDropEffects.isLiteralText()==false) {
				component.setValueExpression(Properties.ROW_DROP_EFFECTS, rowDropEffects);

			} else {
				component.setRowDropEffects(rowDropEffects.getExpressionString());
			}
		}

		if (selectedCriteriaColumns != null) {
			if (selectedCriteriaColumns.isLiteralText()==false) {
				component.setValueExpression(Properties.SELECTED_CRITERIA_COLUMNS, selectedCriteriaColumns);

			} else {
				component.setSelectedCriteriaColumns(selectedCriteriaColumns.getExpressionString());
			}
		}

		if (rowValueColumnId != null) {
			if (rowValueColumnId.isLiteralText()==false) {
				component.setValueExpression(Properties.ROW_VALUE_COLUMN_ID, rowValueColumnId);

			} else {
				component.setRowValueColumnId(rowValueColumnId.getExpressionString());
			}
		}

		if (rowLabelColumnId != null) {
			if (rowLabelColumnId.isLiteralText()==false) {
				component.setValueExpression(Properties.ROW_LABEL_COLUMN_ID, rowLabelColumnId);

			} else {
				component.setRowLabelColumnId(rowLabelColumnId.getExpressionString());
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

		if (cellTextWrap != null) {
			if (cellTextWrap.isLiteralText()==false) {
				component.setValueExpression(Properties.CELL_TEXT_WRAP, cellTextWrap);

			} else {
				component.setCellTextWrap(getBool(cellTextWrap.getExpressionString()));
			}
		}

		if (bodyDroppable != null) {
			if (bodyDroppable.isLiteralText()==false) {
				component.setValueExpression(Properties.BODY_DROPPABLE, bodyDroppable);

			} else {
				component.setBodyDroppable(getBool(bodyDroppable.getExpressionString()));
			}
		}

		if (action != null) {
			ListenersTools1_2.parseAction(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, action);
		}

		if (actionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, actionListeners, true);
		}
	}

	public void release() {
		dragListeners = null;
		dragEffects = null;
		dragTypes = null;
		draggable = null;
		dropListeners = null;
		dropCompleteListeners = null;
		dropEffects = null;
		dropTypes = null;
		droppable = null;
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
		emptyDataMessage = null;
		readOnly = null;
		disabled = null;
		horizontalScrollPosition = null;
		verticalScrollPosition = null;
		filterProperties = null;
		showValue = null;
		keySearchColumnId = null;
		preferences = null;
		paged = null;
		fullCriteriaCount = null;
		clientSelectionFullState = null;
		clientCheckFullState = null;
		headerVisible = null;
		rowToolTipId = null;
		cursorValue = null;
		rowDragTypes = null;
		rowDragEffects = null;
		rowDropTypes = null;
		rowDropEffects = null;
		selectedCriteriaColumns = null;
		rowValueColumnId = null;
		rowLabelColumnId = null;
		rowCountVar = null;
		rowIndexVar = null;
		cellTextWrap = null;
		bodyDroppable = null;
		action = null;
		actionListeners = null;

		super.release();
	}

}
