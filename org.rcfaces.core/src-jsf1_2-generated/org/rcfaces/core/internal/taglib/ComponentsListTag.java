package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ComponentsListComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ComponentsListTag extends AbstractDataTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ComponentsListTag.class);

	private ValueExpression border;
	private ValueExpression borderType;
	private ValueExpression horizontalScrollPosition;
	private ValueExpression verticalScrollPosition;
	private ValueExpression showValue;
	private ValueExpression headingZone;
	private ValueExpression headingLevel;
	private ValueExpression rowCountVar;
	private ValueExpression rowIndexVar;
	private ValueExpression columnNumber;
	private ValueExpression rowStyleClass;
	private ValueExpression columnStyleClass;
	public String getComponentType() {
		return ComponentsListComponent.COMPONENT_TYPE;
	}

	public void setBorder(ValueExpression border) {
		this.border = border;
	}

	public void setBorderType(ValueExpression borderType) {
		this.borderType = borderType;
	}

	public void setHorizontalScrollPosition(ValueExpression horizontalScrollPosition) {
		this.horizontalScrollPosition = horizontalScrollPosition;
	}

	public void setVerticalScrollPosition(ValueExpression verticalScrollPosition) {
		this.verticalScrollPosition = verticalScrollPosition;
	}

	public void setShowValue(ValueExpression showValue) {
		this.showValue = showValue;
	}

	public void setHeadingZone(ValueExpression headingZone) {
		this.headingZone = headingZone;
	}

	public void setHeadingLevel(ValueExpression headingLevel) {
		this.headingLevel = headingLevel;
	}

	public void setRowCountVar(ValueExpression rowCountVar) {
		this.rowCountVar = rowCountVar;
	}

	public void setRowIndexVar(ValueExpression rowIndexVar) {
		this.rowIndexVar = rowIndexVar;
	}

	public void setColumnNumber(ValueExpression columnNumber) {
		this.columnNumber = columnNumber;
	}

	public void setRowStyleClass(ValueExpression rowStyleClass) {
		this.rowStyleClass = rowStyleClass;
	}

	public void setColumnStyleClass(ValueExpression columnStyleClass) {
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
			LOG.debug("  headingZone='"+headingZone+"'");
			LOG.debug("  headingLevel='"+headingLevel+"'");
			LOG.debug("  rowCountVar='"+rowCountVar+"'");
			LOG.debug("  rowIndexVar='"+rowIndexVar+"'");
			LOG.debug("  columnNumber='"+columnNumber+"'");
			LOG.debug("  rowStyleClass='"+rowStyleClass+"'");
			LOG.debug("  columnStyleClass='"+columnStyleClass+"'");
		}
		if ((uiComponent instanceof ComponentsListComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ComponentsListComponent'.");
		}

		super.setProperties(uiComponent);

		ComponentsListComponent component = (ComponentsListComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (border != null) {
			if (border.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER, border);

			} else {
				component.setBorder(getBool(border.getExpressionString()));
			}
		}

		if (borderType != null) {
			if (borderType.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER_TYPE, borderType);

			} else {
				component.setBorderType(borderType.getExpressionString());
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

		if (showValue != null) {
			if (showValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_VALUE, showValue);

			} else {
				component.setShowValue(showValue.getExpressionString());
			}
		}

		if (headingZone != null) {
			if (headingZone.isLiteralText()==false) {
				component.setValueExpression(Properties.HEADING_ZONE, headingZone);

			} else {
				component.setHeadingZone(getBool(headingZone.getExpressionString()));
			}
		}

		if (headingLevel != null) {
			if (headingLevel.isLiteralText()==false) {
				component.setValueExpression(Properties.HEADING_LEVEL, headingLevel);

			} else {
				component.setHeadingLevel(headingLevel.getExpressionString());
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

		if (columnNumber != null) {
			if (columnNumber.isLiteralText()==false) {
				component.setValueExpression(Properties.COLUMN_NUMBER, columnNumber);

			} else {
				component.setColumnNumber(getInt(columnNumber.getExpressionString()));
			}
		}

		if (rowStyleClass != null) {
			if (rowStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.ROW_STYLE_CLASS, rowStyleClass);

			} else {
				component.setRowStyleClass(rowStyleClass.getExpressionString());
			}
		}

		if (columnStyleClass != null) {
			if (columnStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.COLUMN_STYLE_CLASS, columnStyleClass);

			} else {
				component.setColumnStyleClass(columnStyleClass.getExpressionString());
			}
		}
	}

	public void release() {
		border = null;
		borderType = null;
		horizontalScrollPosition = null;
		verticalScrollPosition = null;
		showValue = null;
		headingZone = null;
		headingLevel = null;
		rowCountVar = null;
		rowIndexVar = null;
		columnNumber = null;
		rowStyleClass = null;
		columnStyleClass = null;

		super.release();
	}

}
