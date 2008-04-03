package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.ComponentsGridComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ComponentsGridTag extends AbstractDataTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ComponentsGridTag.class);

	private String selectionListeners;
	private String selectable;
	private String selectionCardinality;
	private String selectedValues;
	private String clientSelectionFullState;
	private String additionalInformationListeners;
	private String additionalInformationValues;
	private String clientAdditionalInformationFullState;
	private String additionalInformationCardinality;
	private String doubleClickListeners;
	private String loadListeners;
	private String required;
	private String border;
	private String rowStyleClass;
	private String showValue;
	private String horizontalScrollPosition;
	private String verticalScrollPosition;
	private String preferences;
	private String paged;
	private String headerVisible;
	private String rowCountVar;
	private String rowIndexVar;
	private String rowValue;
	private String rowValueConverter;
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

	public final String getClientSelectionFullState() {
		return clientSelectionFullState;
	}

	public final void setClientSelectionFullState(String clientSelectionFullState) {
		this.clientSelectionFullState = clientSelectionFullState;
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

	public final String getShowValue() {
		return showValue;
	}

	public final void setShowValue(String showValue) {
		this.showValue = showValue;
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

	public final String getPreferences() {
		return preferences;
	}

	public final void setPreferences(String preferences) {
		this.preferences = preferences;
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

	public final void setRowCountVar(String rowCountVar) {
		this.rowCountVar = rowCountVar;
	}

	public final void setRowIndexVar(String rowIndexVar) {
		this.rowIndexVar = rowIndexVar;
	}

	public final void setRowValue(String rowValue) {
		this.rowValue = rowValue;
	}

	public final void setRowValueConverter(String rowValueConverter) {
		this.rowValueConverter = rowValueConverter;
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
			LOG.debug("  horizontalScrollPosition='"+horizontalScrollPosition+"'");
			LOG.debug("  verticalScrollPosition='"+verticalScrollPosition+"'");
			LOG.debug("  preferences='"+preferences+"'");
			LOG.debug("  paged='"+paged+"'");
			LOG.debug("  headerVisible='"+headerVisible+"'");
			LOG.debug("  rowCountVar='"+rowCountVar+"'");
			LOG.debug("  rowIndexVar='"+rowIndexVar+"'");
			LOG.debug("  rowValue='"+rowValue+"'");
			LOG.debug("  rowValueConverter='"+rowValueConverter+"'");
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

		if (clientSelectionFullState != null) {
			if (isValueReference(clientSelectionFullState)) {
				ValueBinding vb = application.createValueBinding(clientSelectionFullState);
				component.setValueBinding(Properties.CLIENT_SELECTION_FULL_STATE, vb);

			} else {
				component.setClientSelectionFullState(clientSelectionFullState);
			}
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

		if (showValue != null) {
			if (isValueReference(showValue)) {
				ValueBinding vb = application.createValueBinding(showValue);
				component.setValueBinding(Properties.SHOW_VALUE, vb);

			} else {
				component.setShowValue(showValue);
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

		if (preferences != null) {
				ValueBinding vb = application.createValueBinding(preferences);
				component.setValueBinding(Properties.PREFERENCES, vb);
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
		horizontalScrollPosition = null;
		verticalScrollPosition = null;
		preferences = null;
		paged = null;
		headerVisible = null;
		rowCountVar = null;
		rowIndexVar = null;
		rowValue = null;
		rowValueConverter = null;

		super.release();
	}

}
