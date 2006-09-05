package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.TextAreaComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class TextAreaTag extends TextEntryTag {


	private static final Log LOG=LogFactory.getLog(TextAreaTag.class);

	private String rowNumber;
	public String getComponentType() {
		return TextAreaComponent.COMPONENT_TYPE;
	}

	public final String getRowNumber() {
		return rowNumber;
	}

	public final void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (TextAreaComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  rowNumber='"+rowNumber+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof TextAreaComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TextAreaComponent'.");
		}

		TextAreaComponent component = (TextAreaComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

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
		rowNumber = null;

		super.release();
	}

}
