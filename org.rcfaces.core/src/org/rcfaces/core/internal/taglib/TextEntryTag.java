package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.TextEntryComponent;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class TextEntryTag extends AbstractInputTag {

private static final Log LOG=LogFactory.getLog(TextEntryTag.class);
	private String required;
	private String autoTab;
	private String text;
	private String emptyMessage;
	private String readOnly;
	private String valueChangeListeners;
	private String focusStyleClass;
	private String clientValidator;
	private String selectionListeners;
	private String maxTextLength;
	private String columnNumber;
	private String autoCompletion;
	private String action;
	private String actionListeners;
	public String getComponentType() {
		return TextEntryComponent.COMPONENT_TYPE;
	}

	public final String getRequired() {
		return required;
	}

	public final void setRequired(String required) {
		this.required = required;
	}

	public final String getAutoTab() {
		return autoTab;
	}

	public final void setAutoTab(String autoTab) {
		this.autoTab = autoTab;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getEmptyMessage() {
		return emptyMessage;
	}

	public final void setEmptyMessage(String emptyMessage) {
		this.emptyMessage = emptyMessage;
	}

	public final String getReadOnly() {
		return readOnly;
	}

	public final void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public final String getValueChangeListener() {
		return valueChangeListeners;
	}

	public final void setValueChangeListener(String valueChangeListeners) {
		this.valueChangeListeners = valueChangeListeners;
	}

	public final String getFocusStyleClass() {
		return focusStyleClass;
	}

	public final void setFocusStyleClass(String focusStyleClass) {
		this.focusStyleClass = focusStyleClass;
	}

	public final String getClientValidator() {
		return clientValidator;
	}

	public final void setClientValidator(String clientValidator) {
		this.clientValidator = clientValidator;
	}

	public final String getSelectionListener() {
		return selectionListeners;
	}

	public final void setSelectionListener(String selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final String getMaxTextLength() {
		return maxTextLength;
	}

	public final void setMaxTextLength(String maxTextLength) {
		this.maxTextLength = maxTextLength;
	}

	public final String getColumnNumber() {
		return columnNumber;
	}

	public final void setColumnNumber(String columnNumber) {
		this.columnNumber = columnNumber;
	}

	public final String getAutoCompletion() {
		return autoCompletion;
	}

	public final void setAutoCompletion(String autoCompletion) {
		this.autoCompletion = autoCompletion;
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

		if ((uiComponent instanceof TextEntryComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TextEntryComponent'.");
		}

		TextEntryComponent component = (TextEntryComponent) uiComponent;
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

		if (autoTab != null) {
			if (isValueReference(autoTab)) {
				ValueBinding vb = application.createValueBinding(autoTab);

				component.setAutoTab(vb);
			} else {
				component.setAutoTab(getBool(autoTab));
			}
		}

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);

				component.setText(vb);
			} else {
				component.setText(text);
			}
		}

		if (emptyMessage != null) {
			if (isValueReference(emptyMessage)) {
				ValueBinding vb = application.createValueBinding(emptyMessage);

				component.setEmptyMessage(vb);
			} else {
				component.setEmptyMessage(emptyMessage);
			}
		}

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);

				component.setReadOnly(vb);
			} else {
				component.setReadOnly(getBool(readOnly));
			}
		}

		if (valueChangeListeners != null) {
			parseActionListener(application, component, VALUE_CHANGE_LISTENER_TYPE, valueChangeListeners);
		}

		if (focusStyleClass != null) {
			if (isValueReference(focusStyleClass)) {
				ValueBinding vb = application.createValueBinding(focusStyleClass);

				component.setFocusStyleClass(vb);
			} else {
				component.setFocusStyleClass(focusStyleClass);
			}
		}

		if (clientValidator != null) {
			if (isValueReference(clientValidator)) {
				ValueBinding vb = application.createValueBinding(clientValidator);

				component.setClientValidator(vb);
			} else {
				component.setClientValidator(clientValidator);
			}
		}

		if (selectionListeners != null) {
			parseActionListener(application, component, SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (maxTextLength != null) {
			if (isValueReference(maxTextLength)) {
				ValueBinding vb = application.createValueBinding(maxTextLength);
				component.setMaxTextLength(vb);
			} else {
				component.setMaxTextLength(getInt(maxTextLength));
			}
		}

		if (columnNumber != null) {
			if (isValueReference(columnNumber)) {
				ValueBinding vb = application.createValueBinding(columnNumber);
				component.setColumnNumber(vb);
			} else {
				component.setColumnNumber(getInt(columnNumber));
			}
		}

		if (autoCompletion != null) {
			if (isValueReference(autoCompletion)) {
				ValueBinding vb = application.createValueBinding(autoCompletion);
				component.setAutoCompletion(vb);
			} else {
				component.setAutoCompletion(getBool(autoCompletion));
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
		required = null;
		autoTab = null;
		text = null;
		emptyMessage = null;
		readOnly = null;
		valueChangeListeners = null;
		focusStyleClass = null;
		clientValidator = null;
		selectionListeners = null;
		maxTextLength = null;
		columnNumber = null;
		autoCompletion = null;
		action = null;
		actionListeners = null;

		super.release();
	}

}
