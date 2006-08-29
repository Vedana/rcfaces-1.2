package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.ButtonComponent;

import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class ButtonTag extends AbstractCommandTag {

private static final Log LOG=LogFactory.getLog(ButtonTag.class);
	private String text;
	private String selectionListeners;
	private String readOnly;
	private String action;
	private String actionListeners;
	public String getComponentType() {
		return ButtonComponent.COMPONENT_TYPE;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
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

	public final void setAction(String action) {
		this.action=action;
	}

	public final String getAction() {
		return action;
	}

	public final void setActionListener(String listeners) {
		this.actionListeners = listeners;
	}

	public final String getActionListener() {
		return actionListeners;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ButtonComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ButtonComponent'.");
		}

		ButtonComponent component = (ButtonComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);

				component.setText(vb);
			} else {
				component.setText(text);
			}
		}

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

		if (action != null) {
			parseAction(application, component, SELECTION_LISTENER_TYPE, action, true);
		}

		if (actionListeners != null) {
			parseActionListener(application, component, SELECTION_LISTENER_TYPE, actionListeners, true);
		}
	}

	public void release() {
		text = null;
		selectionListeners = null;
		readOnly = null;
		action = null;
		actionListeners = null;

		super.release();
	}

}
