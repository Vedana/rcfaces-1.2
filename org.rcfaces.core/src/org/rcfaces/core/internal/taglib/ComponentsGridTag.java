package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.ComponentsGridComponent;
import javax.faces.application.Application;

public class ComponentsGridTag extends AbstractDataTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ComponentsGridTag.class);

	private String selectionListeners;
	private String selectable;
	private String selectionCardinality;
	private String selectedValues;
	private String doubleClickListeners;
	private String required;
	private String border;
	private String rowStyleClass;
	private String horizontalScrollPosition;
	private String verticalScrollPosition;
	private String preference;
	private String paged;
	private String headerVisible;
	private String rowCountVar;
	private String rowIndexVar;
	private String rowValue;
	private String rowValueConverter;
	private String clientSelectionFullState;
	public String getComponentType() {
		return ComponentsGridComponent.COMPONENT_TYPE;
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

	public final String getRowStyleClass() {
		return rowStyleClass;
	}

	public final void setRowStyleClass(String rowStyleClass) {
		this.rowStyleClass = rowStyleClass;
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

	public final String getHeaderVisible() {
		return headerVisible;
	}

	public final void setHeaderVisible(String headerVisible) {
		this.headerVisible = headerVisible;
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

	public final String getRowValue() {
		return rowValue;
	}

	public final void setRowValue(String rowValue) {
		this.rowValue = rowValue;
	}

	public final String getRowValueConverter() {
		return rowValueConverter;
	}

	public final void setRowValueConverter(String rowValueConverter) {
		this.rowValueConverter = rowValueConverter;
	}

	public final String getClientSelectionFullState() {
		return clientSelectionFullState;
	}

	public final void setClientSelectionFullState(String clientSelectionFullState) {
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
			LOG.debug("  required='"+required+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  rowStyleClass='"+rowStyleClass+"'");
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

		if (doubleClickListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.DOUBLE_CLICK_LISTENER_TYPE, doubleClickListeners);
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

		if (headerVisible != null) {
			if (isValueReference(headerVisible)) {
				ValueBinding vb = application.createValueBinding(headerVisible);
				component.setValueBinding(Properties.HEADER_VISIBLE, vb);

			} else {
				component.setHeaderVisible(getBool(headerVisible));
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

		if (rowValue != null) {
			if (isValueReference(rowValue)) {
				ValueBinding vb = application.createValueBinding(rowValue);
				component.setValueBinding(Properties.ROW_VALUE, vb);

			} else {
				throw new javax.faces.FacesException("Attribute 'rowValue' accept only a binding expression !");
			}
		}

		if (rowValueConverter != null) {
			if (isValueReference(rowValueConverter)) {
				ValueBinding vb = application.createValueBinding(rowValueConverter);
				component.setValueBinding(Properties.ROW_VALUE_CONVERTER, vb);

			} else {
				component.setRowValueConverter(rowValueConverter);
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
	}

	public void release() {
		selectionListeners = null;
		selectable = null;
		selectionCardinality = null;
		selectedValues = null;
		doubleClickListeners = null;
		required = null;
		border = null;
		rowStyleClass = null;
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
