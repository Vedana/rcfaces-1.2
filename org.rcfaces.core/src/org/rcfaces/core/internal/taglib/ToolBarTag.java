package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.ToolBarComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ToolBarTag extends AbstractInputTag {

private static final Log LOG=LogFactory.getLog(ToolBarTag.class);
	private String selectionListeners;
	private String readOnly;
	public String getComponentType() {
		return ToolBarComponent.COMPONENT_TYPE;
	}

	public final String getSelectionListener() {
		return selectionListeners;
	}

	public final void setSelectionListener(String selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final String getReadOnly() {
		return readOnly;
	}

	public final void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ToolBarComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ToolBarComponent'.");
		}

		ToolBarComponent component = (ToolBarComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (selectionListeners != null) {
			parseActionListener(application, component, SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);

				component.setReadOnly(vb);
			} else {
				component.setReadOnly(getBool(readOnly));
			}
		}
	}

	public void release() {
		selectionListeners = null;
		readOnly = null;

		super.release();
	}

}
