package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.component.ComponentsGridComponent;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ComponentsGridTag extends AbstractDataTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ComponentsGridTag.class);

	private ValueExpression selectionListeners;
	private ValueExpression selectable;
	private ValueExpression selectionCardinality;
	private ValueExpression selectedValues;
	private ValueExpression clientSelectionFullState;
	private ValueExpression additionalInformationListeners;
	private ValueExpression additionalInformationValues;
	private ValueExpression clientAdditionalInformationFullState;
	private ValueExpression additionalInformationCardinality;
	private ValueExpression doubleClickListeners;
	private ValueExpression loadListeners;
	private ValueExpression required;
	private ValueExpression border;
	private ValueExpression rowStyleClass;
	private ValueExpression showValue;
	private ValueExpression emptyDataMessage;
	private ValueExpression horizontalScrollPosition;
	private ValueExpression verticalScrollPosition;
	private ValueExpression preferences;
	private ValueExpression paged;
	private ValueExpression rowToolTipId;
	private ValueExpression headerVisible;
	private ValueExpression rowCountVar;
	private ValueExpression rowIndexVar;
	private ValueExpression rowValue;
	private ValueExpression rowValueConverter;
	private ValueExpression cellTextWrap;
	public String getComponentType() {
		return ComponentsGridComponent.COMPONENT_TYPE;
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

	public void setClientSelectionFullState(ValueExpression clientSelectionFullState) {
		this.clientSelectionFullState = clientSelectionFullState;
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

	public void setShowValue(ValueExpression showValue) {
		this.showValue = showValue;
	}

	public void setEmptyDataMessage(ValueExpression emptyDataMessage) {
		this.emptyDataMessage = emptyDataMessage;
	}

	public void setHorizontalScrollPosition(ValueExpression horizontalScrollPosition) {
		this.horizontalScrollPosition = horizontalScrollPosition;
	}

	public void setVerticalScrollPosition(ValueExpression verticalScrollPosition) {
		this.verticalScrollPosition = verticalScrollPosition;
	}

	public void setPreferences(ValueExpression preferences) {
		this.preferences = preferences;
	}

	public void setPaged(ValueExpression paged) {
		this.paged = paged;
	}

	public void setRowToolTipId(ValueExpression rowToolTipId) {
		this.rowToolTipId = rowToolTipId;
	}

	public void setHeaderVisible(ValueExpression headerVisible) {
		this.headerVisible = headerVisible;
	}

	public void setRowCountVar(ValueExpression rowCountVar) {
		this.rowCountVar = rowCountVar;
	}

	public void setRowIndexVar(ValueExpression rowIndexVar) {
		this.rowIndexVar = rowIndexVar;
	}

	public void setRowValue(ValueExpression rowValue) {
		this.rowValue = rowValue;
	}

	public void setRowValueConverter(ValueExpression rowValueConverter) {
		this.rowValueConverter = rowValueConverter;
	}

	public void setCellTextWrap(ValueExpression cellTextWrap) {
		this.cellTextWrap = cellTextWrap;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ComponentsGridComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  selectable='"+selectable+"'");
			LOG.debug("  selectionCardinality='"+selectionCardinality+"'");
			LOG.debug("  selectedValues='"+selectedValues+"'");
			LOG.debug("  clientSelectionFullState='"+clientSelectionFullState+"'");
			LOG.debug("  additionalInformationValues='"+additionalInformationValues+"'");
			LOG.debug("  clientAdditionalInformationFullState='"+clientAdditionalInformationFullState+"'");
			LOG.debug("  additionalInformationCardinality='"+additionalInformationCardinality+"'");
			LOG.debug("  required='"+required+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  rowStyleClass='"+rowStyleClass+"'");
			LOG.debug("  showValue='"+showValue+"'");
			LOG.debug("  emptyDataMessage='"+emptyDataMessage+"'");
			LOG.debug("  horizontalScrollPosition='"+horizontalScrollPosition+"'");
			LOG.debug("  verticalScrollPosition='"+verticalScrollPosition+"'");
			LOG.debug("  preferences='"+preferences+"'");
			LOG.debug("  paged='"+paged+"'");
			LOG.debug("  rowToolTipId='"+rowToolTipId+"'");
			LOG.debug("  headerVisible='"+headerVisible+"'");
			LOG.debug("  rowCountVar='"+rowCountVar+"'");
			LOG.debug("  rowIndexVar='"+rowIndexVar+"'");
			LOG.debug("  rowValue='"+rowValue+"'");
			LOG.debug("  rowValueConverter='"+rowValueConverter+"'");
			LOG.debug("  cellTextWrap='"+cellTextWrap+"'");
		}
		if ((uiComponent instanceof ComponentsGridComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ComponentsGridComponent'.");
		}

		super.setProperties(uiComponent);

		ComponentsGridComponent component = (ComponentsGridComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

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

		if (clientSelectionFullState != null) {
			if (clientSelectionFullState.isLiteralText()==false) {
				component.setValueExpression(Properties.CLIENT_SELECTION_FULL_STATE, clientSelectionFullState);

			} else {
				component.setClientSelectionFullState(clientSelectionFullState.getExpressionString());
			}
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

		if (showValue != null) {
			if (showValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_VALUE, showValue);

			} else {
				component.setShowValue(showValue.getExpressionString());
			}
		}

		if (emptyDataMessage != null) {
			if (emptyDataMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.EMPTY_DATA_MESSAGE, emptyDataMessage);

			} else {
				component.setEmptyDataMessage(emptyDataMessage.getExpressionString());
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

		if (rowToolTipId != null) {
			if (rowToolTipId.isLiteralText()==false) {
				component.setValueExpression(Properties.ROW_TOOL_TIP_ID, rowToolTipId);

			} else {
				component.setRowToolTipId(rowToolTipId.getExpressionString());
			}
		}

		if (headerVisible != null) {
			if (headerVisible.isLiteralText()==false) {
				component.setValueExpression(Properties.HEADER_VISIBLE, headerVisible);

			} else {
				component.setHeaderVisible(getBool(headerVisible.getExpressionString()));
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

		if (rowValue != null) {
			if (rowValue.isLiteralText()==false) {
				component.setValueExpression(Properties.ROW_VALUE, rowValue);

			} else {
				throw new javax.faces.FacesException("Attribute 'rowValue' accept only a binding expression !");
			}
		}

		if (rowValueConverter != null) {
			if (rowValueConverter.isLiteralText()==false) {
				component.setValueExpression(Properties.ROW_VALUE_CONVERTER, rowValueConverter);

			} else {
				component.setRowValueConverter(rowValueConverter.getExpressionString());
			}
		}

		if (cellTextWrap != null) {
			if (cellTextWrap.isLiteralText()==false) {
				component.setValueExpression(Properties.CELL_TEXT_WRAP, cellTextWrap);

			} else {
				component.setCellTextWrap(getBool(cellTextWrap.getExpressionString()));
			}
		}
	}

	public void release() {
		selectionListeners = null;
		selectable = null;
		selectionCardinality = null;
		selectedValues = null;
		clientSelectionFullState = null;
		additionalInformationListeners = null;
		additionalInformationValues = null;
		clientAdditionalInformationFullState = null;
		additionalInformationCardinality = null;
		doubleClickListeners = null;
		loadListeners = null;
		required = null;
		border = null;
		rowStyleClass = null;
		showValue = null;
		emptyDataMessage = null;
		horizontalScrollPosition = null;
		verticalScrollPosition = null;
		preferences = null;
		paged = null;
		rowToolTipId = null;
		headerVisible = null;
		rowCountVar = null;
		rowIndexVar = null;
		rowValue = null;
		rowValueConverter = null;
		cellTextWrap = null;

		super.release();
	}

}
