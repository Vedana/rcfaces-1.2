package org.rcfaces.core.internal.taglib;

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

public class ComboGridTag extends AbstractDataTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ComboGridTag.class);

	private String required;
	private String border;
	private String rowStyleClass;
	private String horizontalScrollPosition;
	private String verticalScrollPosition;
	private String paged;
	private String rowCountVar;
	private String rowIndexVar;
	private String valueColumnId;
	private String labelColumnId;
	private String selectedValue;
	private String selectionValueConverter;
	public String getComponentType() {
		return ComboGridComponent.COMPONENT_TYPE;
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

	public final String getPaged() {
		return paged;
	}

	public final void setPaged(String paged) {
		this.paged = paged;
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

	public final String getSelectionValueConverter() {
		return selectionValueConverter;
	}

	public final void setSelectionValueConverter(String selectionValueConverter) {
		this.selectionValueConverter = selectionValueConverter;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ComboGridComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  required='"+required+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  rowStyleClass='"+rowStyleClass+"'");
			LOG.debug("  horizontalScrollPosition='"+horizontalScrollPosition+"'");
			LOG.debug("  verticalScrollPosition='"+verticalScrollPosition+"'");
			LOG.debug("  paged='"+paged+"'");
			LOG.debug("  rowCountVar='"+rowCountVar+"'");
			LOG.debug("  rowIndexVar='"+rowIndexVar+"'");
			LOG.debug("  valueColumnId='"+valueColumnId+"'");
			LOG.debug("  labelColumnId='"+labelColumnId+"'");
			LOG.debug("  selectedValue='"+selectedValue+"'");
			LOG.debug("  selectionValueConverter='"+selectionValueConverter+"'");
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

		if (rowStyleClass != null) {
			if (isValueReference(rowStyleClass)) {
				ValueBinding vb = application.createValueBinding(rowStyleClass);

				component.setRowStyleClass(vb);
			} else {
				component.setRowStyleClass(rowStyleClass);
			}
		}

		if (horizontalScrollPosition != null) {
			if (isValueReference(horizontalScrollPosition)) {
				ValueBinding vb = application.createValueBinding(horizontalScrollPosition);

				component.setHorizontalScrollPosition(vb);
			} else {
				component.setHorizontalScrollPosition(getInt(horizontalScrollPosition));
			}
		}

		if (verticalScrollPosition != null) {
			if (isValueReference(verticalScrollPosition)) {
				ValueBinding vb = application.createValueBinding(verticalScrollPosition);

				component.setVerticalScrollPosition(vb);
			} else {
				component.setVerticalScrollPosition(getInt(verticalScrollPosition));
			}
		}

		if (paged != null) {
			if (isValueReference(paged)) {
				ValueBinding vb = application.createValueBinding(paged);

				component.setPaged(vb);
			} else {
				component.setPaged(getBool(paged));
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
				component.setValueColumnId(vb);
			} else {
				component.setValueColumnId(valueColumnId);
			}
		}

		if (labelColumnId != null) {
			if (isValueReference(labelColumnId)) {
				ValueBinding vb = application.createValueBinding(labelColumnId);
				component.setLabelColumnId(vb);
			} else {
				component.setLabelColumnId(labelColumnId);
			}
		}

		if (selectedValue != null) {
			if (isValueReference(selectedValue)) {
				ValueBinding vb = application.createValueBinding(selectedValue);
				component.setSelectedValue(vb);
			} else {
				component.setSelectedValue(selectedValue);
			}
		}

		if (selectionValueConverter != null) {
			if (isValueReference(selectionValueConverter)) {
				ValueBinding vb = application.createValueBinding(selectionValueConverter);
				component.setSelectionValueConverter(vb);
			} else {
				component.setSelectionValueConverter(selectionValueConverter);
			}
		}
	}

	public void release() {
		required = null;
		border = null;
		rowStyleClass = null;
		horizontalScrollPosition = null;
		verticalScrollPosition = null;
		paged = null;
		rowCountVar = null;
		rowIndexVar = null;
		valueColumnId = null;
		labelColumnId = null;
		selectedValue = null;
		selectionValueConverter = null;

		super.release();
	}

}
