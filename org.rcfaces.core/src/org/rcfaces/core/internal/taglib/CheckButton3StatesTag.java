package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.CheckButton3StatesComponent;

import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class CheckButton3StatesTag extends AbstractInputTag {

private static final Log LOG=LogFactory.getLog(CheckButton3StatesTag.class);
	private String text;
	private String textPosition;
	private String selectionListeners;
	private String readOnly;
	private String selectedState;
	public String getComponentType() {
		return CheckButton3StatesComponent.COMPONENT_TYPE;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getTextPosition() {
		return textPosition;
	}

	public final void setTextPosition(String textPosition) {
		this.textPosition = textPosition;
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

	public final String getSelectedState() {
		return selectedState;
	}

	public final void setSelectedState(String selectedState) {
		this.selectedState = selectedState;
	}

	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if ((uiComponent instanceof CheckButton3StatesComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CheckButton3StatesComponent'.");
		}

		CheckButton3StatesComponent component = (CheckButton3StatesComponent) uiComponent;
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

		if (textPosition != null) {
			if (isValueReference(textPosition)) {
				ValueBinding vb = application.createValueBinding(textPosition);

				component.setTextPosition(vb);
			} else {
				component.setTextPosition(textPosition);
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

		if (selectedState != null) {
			if (isValueReference(selectedState)) {
				ValueBinding vb = application.createValueBinding(selectedState);

				component.setSelectedState(vb);
			} else {
				component.setSelectedState(selectedState);
			}
		}
	}

	public void release() {
		text = null;
		textPosition = null;
		selectionListeners = null;
		readOnly = null;
		selectedState = null;

		super.release();
	}

}
