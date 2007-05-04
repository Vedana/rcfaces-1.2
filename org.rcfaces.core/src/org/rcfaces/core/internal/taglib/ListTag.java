package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.ListComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ListTag extends ComboTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ListTag.class);

	private String multipleSelect;
	private String doubleClickListeners;
	private String rowNumber;
	public String getComponentType() {
		return ListComponent.COMPONENT_TYPE;
	}

	public final String getMultipleSelect() {
		return multipleSelect;
	}

	public final void setMultipleSelect(String multipleSelect) {
		this.multipleSelect = multipleSelect;
	}

	public final String getDoubleClickListener() {
		return doubleClickListeners;
	}

	public final void setDoubleClickListener(String doubleClickListeners) {
		this.doubleClickListeners = doubleClickListeners;
	}

	public final String getRowNumber() {
		return rowNumber;
	}

	public final void setRowNumber(String rowNumber) {
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
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ListComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ListComponent'.");
		}

		ListComponent component = (ListComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (multipleSelect != null) {
			if (isValueReference(multipleSelect)) {
				ValueBinding vb = application.createValueBinding(multipleSelect);

				component.setMultipleSelect(vb);
			} else {
				component.setMultipleSelect(getBool(multipleSelect));
			}
		}

		if (doubleClickListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.DOUBLE_CLICK_LISTENER_TYPE, doubleClickListeners);
		}

		if (rowNumber != null) {
			if (isValueReference(rowNumber)) {
				ValueBinding vb = application.createValueBinding(rowNumber);
				component.setRowNumber(vb);
			} else {
				component.setRowNumber(getInt(rowNumber));
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
