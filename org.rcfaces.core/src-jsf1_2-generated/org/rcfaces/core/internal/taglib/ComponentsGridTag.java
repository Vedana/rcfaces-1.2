package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.ComponentsGridComponent;
import javax.faces.application.Application;

public class ComponentsGridTag extends AbstractDataTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ComponentsGridTag.class);

	private ValueExpression selectionListeners;
	private ValueExpression selectable;
	private ValueExpression selectionCardinality;
	private ValueExpression selectedValues;
	private ValueExpression additionalInformationListeners;
	private ValueExpression additionalInformationValues;
	private ValueExpression clientAdditionalInformationFullState;
	private ValueExpression additionalInformationCardinality;
	private ValueExpression doubleClickListeners;
	private ValueExpression required;
	private ValueExpression border;
	private ValueExpression rowStyleClass;
	private ValueExpression showValue;
	private ValueExpression horizontalScrollPosition;
	private ValueExpression verticalScrollPosition;
	private ValueExpression preference;
	private ValueExpression paged;
	private ValueExpression headerVisible;
	private ValueExpression rowCountVar;
	private ValueExpression rowIndexVar;
	private ValueExpression rowValue;
	private ValueExpression rowValueConverter;
	private ValueExpression clientSelectionFullState;
	public String getComponentType() {
		return ComponentsGridComponent.COMPONENT_TYPE;
	}

	public final void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final void setSelectable(ValueExpression selectable) {
		this.selectable = selectable;
	}

	public final void setSelectionCardinality(ValueExpression selectionCardinality) {
		this.selectionCardinality = selectionCardinality;
	}

	public final void setSelectedValues(ValueExpression selectedValues) {
		this.selectedValues = selectedValues;
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

	public final void setDoubleClickListener(ValueExpression doubleClickListeners) {
		this.doubleClickListeners = doubleClickListeners;
	}

	public final void setRequired(ValueExpression required) {
		this.required = required;
	}

	public final void setBorder(ValueExpression border) {
		this.border = border;
	}

	public final void setRowStyleClass(ValueExpression rowStyleClass) {
		this.rowStyleClass = rowStyleClass;
	}

	public final void setShowValue(ValueExpression showValue) {
		this.showValue = showValue;
	}

	public final void setHorizontalScrollPosition(ValueExpression horizontalScrollPosition) {
		this.horizontalScrollPosition = horizontalScrollPosition;
	}

	public final void setVerticalScrollPosition(ValueExpression verticalScrollPosition) {
		this.verticalScrollPosition = verticalScrollPosition;
	}

	public final void setPreference(ValueExpression preference) {
		this.preference = preference;
	}

	public final void setPaged(ValueExpression paged) {
		this.paged = paged;
	}

	public final void setHeaderVisible(ValueExpression headerVisible) {
		this.headerVisible = headerVisible;
	}

	public final void setRowCountVar(ValueExpression rowCountVar) {
		this.rowCountVar = rowCountVar;
	}

	public final void setRowIndexVar(ValueExpression rowIndexVar) {
		this.rowIndexVar = rowIndexVar;
	}

	public final void setRowValue(ValueExpression rowValue) {
		this.rowValue = rowValue;
	}

	public final void setRowValueConverter(ValueExpression rowValueConverter) {
		this.rowValueConverter = rowValueConverter;
	}

	public final void setClientSelectionFullState(ValueExpression clientSelectionFullState) {
		this.clientSelectionFullState = clientSelectionFullState;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ComponentsGridComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  selectable='"+selectable+"'");
			LOG.debug("  selectionCardinality='"+selectionCardinality+"'");
			LOG.debug("  selectedValues='"+selectedValues+"'");
			LOG.debug("  additionalInformationValues='"+additionalInformationValues+"'");
			LOG.debug("  clientAdditionalInformationFullState='"+clientAdditionalInformationFullState+"'");
			LOG.debug("  additionalInformationCardinality='"+additionalInformationCardinality+"'");
			LOG.debug("  required='"+required+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  rowStyleClass='"+rowStyleClass+"'");
			LOG.debug("  showValue='"+showValue+"'");
			LOG.debug("  horizontalScrollPosition='"+horizontalScrollPosition+"'");
			LOG.debug("  verticalScrollPosition='"+verticalScrollPosition+"'");
			LOG.debug("  preference='"+preference+"'");
			LOG.debug("  paged='"+paged+"'");
			LOG.debug("  headerVisible='"+headerVisible+"'");
			LOG.debug("  rowCountVar='"+rowCountVar+"'");
			LOG.debug("  rowIndexVar='"+rowIndexVar+"'");
			LOG.debug("  rowValue='"+rowValue+"'");
			LOG.debug("  rowValueConverter='"+rowValueConverter+"'");
			LOG.debug("  clientSelectionFullState='"+clientSelectionFullState+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ComponentsGridComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ComponentsGridComponent'.");
		}

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

		if (doubleClickListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.DOUBLE_CLICK_LISTENER_TYPE, doubleClickListeners);
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

		if (preference != null) {
				component.setValueExpression(Properties.PREFERENCE, preference);
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

		if (clientSelectionFullState != null) {
			if (clientSelectionFullState.isLiteralText()==false) {
				component.setValueExpression(Properties.CLIENT_SELECTION_FULL_STATE, clientSelectionFullState);

			} else {
				component.setClientSelectionFullState(getBool(clientSelectionFullState.getExpressionString()));
			}
		}
	}

	public void release() {
		selectionListeners = null;
		selectable = null;
		selectionCardinality = null;
		selectedValues = null;
		additionalInformationListeners = null;
		additionalInformationValues = null;
		clientAdditionalInformationFullState = null;
		additionalInformationCardinality = null;
		doubleClickListeners = null;
		required = null;
		border = null;
		rowStyleClass = null;
		showValue = null;
		horizontalScrollPosition = null;
		verticalScrollPosition = null;
		preference = null;
		paged = null;
		headerVisible = null;
		rowCountVar = null;
		rowIndexVar = null;
		rowValue = null;
		rowValueConverter = null;
		clientSelectionFullState = null;

		super.release();
	}

}
