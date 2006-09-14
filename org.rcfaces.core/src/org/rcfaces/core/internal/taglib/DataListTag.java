package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.DataListComponent;

public class DataListTag extends AbstractDataTag implements Tag {


	private static final Log LOG=LogFactory.getLog(DataListTag.class);

	private String border;
	private String borderType;
	private String horizontalScrollPosition;
	private String verticalScrollPosition;
	private String rows;
	private String first;
	private String value;
	private String var;
	private String rowCountVar;
	private String rowIndexVar;
	private String columnNumber;
	private String rowStyleClass;
	private String columnStyleClass;
	public String getComponentType() {
		return DataListComponent.COMPONENT_TYPE;
	}

	public final String getBorder() {
		return border;
	}

	public final void setBorder(String border) {
		this.border = border;
	}

	public final String getBorderType() {
		return borderType;
	}

	public final void setBorderType(String borderType) {
		this.borderType = borderType;
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

	public final String getRows() {
		return rows;
	}

	public final void setRows(String rows) {
		this.rows = rows;
	}

	public final String getFirst() {
		return first;
	}

	public final void setFirst(String first) {
		this.first = first;
	}

	public final String getValue() {
		return value;
	}

	public final void setValue(String value) {
		this.value = value;
	}

	public final String getVar() {
		return var;
	}

	public final void setVar(String var) {
		this.var = var;
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

	public final String getColumnNumber() {
		return columnNumber;
	}

	public final void setColumnNumber(String columnNumber) {
		this.columnNumber = columnNumber;
	}

	public final String getRowStyleClass() {
		return rowStyleClass;
	}

	public final void setRowStyleClass(String rowStyleClass) {
		this.rowStyleClass = rowStyleClass;
	}

	public final String getColumnStyleClass() {
		return columnStyleClass;
	}

	public final void setColumnStyleClass(String columnStyleClass) {
		this.columnStyleClass = columnStyleClass;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (DataListComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  border='"+border+"'");
			LOG.debug("  borderType='"+borderType+"'");
			LOG.debug("  horizontalScrollPosition='"+horizontalScrollPosition+"'");
			LOG.debug("  verticalScrollPosition='"+verticalScrollPosition+"'");
			LOG.debug("  rows='"+rows+"'");
			LOG.debug("  first='"+first+"'");
			LOG.debug("  value='"+value+"'");
			LOG.debug("  var='"+var+"'");
			LOG.debug("  rowCountVar='"+rowCountVar+"'");
			LOG.debug("  rowIndexVar='"+rowIndexVar+"'");
			LOG.debug("  columnNumber='"+columnNumber+"'");
			LOG.debug("  rowStyleClass='"+rowStyleClass+"'");
			LOG.debug("  columnStyleClass='"+columnStyleClass+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof DataListComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'DataListComponent'.");
		}

		DataListComponent component = (DataListComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (border != null) {
			if (isValueReference(border)) {
				ValueBinding vb = application.createValueBinding(border);

				component.setBorder(vb);
			} else {
				component.setBorder(getBool(border));
			}
		}

		if (borderType != null) {
			if (isValueReference(borderType)) {
				ValueBinding vb = application.createValueBinding(borderType);

				component.setBorderType(vb);
			} else {
				component.setBorderType(borderType);
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

		if (rows != null) {
			if (isValueReference(rows)) {
				ValueBinding vb = application.createValueBinding(rows);
				component.setRows(vb);
			} else {
				component.setRows(getInt(rows));
			}
		}

		if (first != null) {
			if (isValueReference(first)) {
				ValueBinding vb = application.createValueBinding(first);
				component.setFirst(vb);
			} else {
				component.setFirst(getInt(first));
			}
		}

		if (value != null) {
			if (isValueReference(value)) {
				ValueBinding vb = application.createValueBinding(value);
				component.setValue(vb);
			} else {
				component.setValue(value);
			}
		}

		if (var != null) {
			if (isValueReference(var)) {
				ValueBinding vb = application.createValueBinding(var);
				component.setVar(vb);
			} else {
				component.setVar(var);
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

		if (columnNumber != null) {
			if (isValueReference(columnNumber)) {
				ValueBinding vb = application.createValueBinding(columnNumber);
				component.setColumnNumber(vb);
			} else {
				component.setColumnNumber(getInt(columnNumber));
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

		if (columnStyleClass != null) {
			if (isValueReference(columnStyleClass)) {
				ValueBinding vb = application.createValueBinding(columnStyleClass);
				component.setColumnStyleClass(vb);
			} else {
				component.setColumnStyleClass(columnStyleClass);
			}
		}
	}

	public void release() {
		border = null;
		borderType = null;
		horizontalScrollPosition = null;
		verticalScrollPosition = null;
		rows = null;
		first = null;
		value = null;
		var = null;
		rowCountVar = null;
		rowIndexVar = null;
		columnNumber = null;
		rowStyleClass = null;
		columnStyleClass = null;

		super.release();
	}

}
