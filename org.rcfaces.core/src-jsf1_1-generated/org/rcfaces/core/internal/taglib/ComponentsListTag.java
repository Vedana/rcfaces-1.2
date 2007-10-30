package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ComponentsListComponent;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ComponentsListTag extends AbstractDataTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ComponentsListTag.class);

	private String border;
	private String borderType;
	private String horizontalScrollPosition;
	private String verticalScrollPosition;
	private String showValue;
	private String rowCountVar;
	private String rowIndexVar;
	private String columnNumber;
	private String rowStyleClass;
	private String columnStyleClass;
	public String getComponentType() {
		return ComponentsListComponent.COMPONENT_TYPE;
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

	public final String getShowValue() {
		return showValue;
	}

	public final void setShowValue(String showValue) {
		this.showValue = showValue;
	}

	public final void setRowCountVar(String rowCountVar) {
		this.rowCountVar = rowCountVar;
	}

	public final void setRowIndexVar(String rowIndexVar) {
		this.rowIndexVar = rowIndexVar;
	}

	public final void setColumnNumber(String columnNumber) {
		this.columnNumber = columnNumber;
	}

	public final void setRowStyleClass(String rowStyleClass) {
		this.rowStyleClass = rowStyleClass;
	}

	public final void setColumnStyleClass(String columnStyleClass) {
		this.columnStyleClass = columnStyleClass;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ComponentsListComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  border='"+border+"'");
			LOG.debug("  borderType='"+borderType+"'");
			LOG.debug("  horizontalScrollPosition='"+horizontalScrollPosition+"'");
			LOG.debug("  verticalScrollPosition='"+verticalScrollPosition+"'");
			LOG.debug("  showValue='"+showValue+"'");
			LOG.debug("  rowCountVar='"+rowCountVar+"'");
			LOG.debug("  rowIndexVar='"+rowIndexVar+"'");
			LOG.debug("  columnNumber='"+columnNumber+"'");
			LOG.debug("  rowStyleClass='"+rowStyleClass+"'");
			LOG.debug("  columnStyleClass='"+columnStyleClass+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ComponentsListComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ComponentsListComponent'.");
		}

		ComponentsListComponent component = (ComponentsListComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (border != null) {
			if (isValueReference(border)) {
				ValueBinding vb = application.createValueBinding(border);
				component.setValueBinding(Properties.BORDER, vb);

			} else {
				component.setBorder(getBool(border));
			}
		}

		if (borderType != null) {
			if (isValueReference(borderType)) {
				ValueBinding vb = application.createValueBinding(borderType);
				component.setValueBinding(Properties.BORDER_TYPE, vb);

			} else {
				component.setBorderType(borderType);
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

		if (showValue != null) {
			if (isValueReference(showValue)) {
				ValueBinding vb = application.createValueBinding(showValue);
				component.setValueBinding(Properties.SHOW_VALUE, vb);

			} else {
				component.setShowValue(showValue);
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
				component.setValueBinding(Properties.COLUMN_NUMBER, vb);

			} else {
				component.setColumnNumber(getInt(columnNumber));
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

		if (columnStyleClass != null) {
			if (isValueReference(columnStyleClass)) {
				ValueBinding vb = application.createValueBinding(columnStyleClass);
				component.setValueBinding(Properties.COLUMN_STYLE_CLASS, vb);

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
		showValue = null;
		rowCountVar = null;
		rowIndexVar = null;
		columnNumber = null;
		rowStyleClass = null;
		columnStyleClass = null;

		super.release();
	}

}
