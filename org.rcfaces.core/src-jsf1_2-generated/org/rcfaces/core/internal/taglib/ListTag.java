package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ListComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ListTag extends ComboTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ListTag.class);

	private ValueExpression multipleSelect;
	private ValueExpression doubleClickListeners;
	private ValueExpression rowNumber;
	public String getComponentType() {
		return ListComponent.COMPONENT_TYPE;
	}

	public final void setMultipleSelect(ValueExpression multipleSelect) {
		this.multipleSelect = multipleSelect;
	}

	public final void setDoubleClickListener(ValueExpression doubleClickListeners) {
		this.doubleClickListeners = doubleClickListeners;
	}

	public final void setRowNumber(ValueExpression rowNumber) {
		this.rowNumber = rowNumber;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ListComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  multipleSelect='"+multipleSelect+"'");
			LOG.debug("  rowNumber='"+rowNumber+"'");
		}
		if ((uiComponent instanceof ListComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ListComponent'.");
		}

		super.setProperties(uiComponent);

		ListComponent component = (ListComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (multipleSelect != null) {
			if (multipleSelect.isLiteralText()==false) {
				component.setValueExpression(Properties.MULTIPLE_SELECT, multipleSelect);

			} else {
				component.setMultipleSelect(getBool(multipleSelect.getExpressionString()));
			}
		}

		if (doubleClickListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.DOUBLE_CLICK_LISTENER_TYPE, doubleClickListeners);
		}

		if (rowNumber != null) {
			if (rowNumber.isLiteralText()==false) {
				component.setValueExpression(Properties.ROW_NUMBER, rowNumber);

			} else {
				component.setRowNumber(getInt(rowNumber.getExpressionString()));
			}
		}
	}

	public void release() {
		multipleSelect = null;
		doubleClickListeners = null;
		rowNumber = null;

		super.release();
	}

}
