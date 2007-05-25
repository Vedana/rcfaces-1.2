package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.TextEntryComponent;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class TextEntryTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TextEntryTag.class);

	private String required;
	private String autoTab;
	private String text;
	private String textDirection;
	private String emptyMessage;
	private String readOnly;
	private String valueChangeListeners;
	private String focusStyleClass;
	private String errorStyleClass;
	private String fatalStyleClass;
	private String infoStyleClass;
	private String warnStyleClass;
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

	public final String getTextDirection() {
		return textDirection;
	}

	public final void setTextDirection(String textDirection) {
		this.textDirection = textDirection;
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

	public final String getErrorStyleClass() {
		return errorStyleClass;
	}

	public final void setErrorStyleClass(String errorStyleClass) {
		this.errorStyleClass = errorStyleClass;
	}

	public final String getFatalStyleClass() {
		return fatalStyleClass;
	}

	public final void setFatalStyleClass(String fatalStyleClass) {
		this.fatalStyleClass = fatalStyleClass;
	}

	public final String getInfoStyleClass() {
		return infoStyleClass;
	}

	public final void setInfoStyleClass(String infoStyleClass) {
		this.infoStyleClass = infoStyleClass;
	}

	public final String getWarnStyleClass() {
		return warnStyleClass;
	}

	public final void setWarnStyleClass(String warnStyleClass) {
		this.warnStyleClass = warnStyleClass;
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
		if (LOG.isDebugEnabled()) {
			if (TextEntryComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  required='"+required+"'");
			LOG.debug("  autoTab='"+autoTab+"'");
			LOG.debug("  text='"+text+"'");
			LOG.debug("  textDirection='"+textDirection+"'");
			LOG.debug("  emptyMessage='"+emptyMessage+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  focusStyleClass='"+focusStyleClass+"'");
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  clientValidator='"+clientValidator+"'");
			LOG.debug("  maxTextLength='"+maxTextLength+"'");
			LOG.debug("  columnNumber='"+columnNumber+"'");
			LOG.debug("  autoCompletion='"+autoCompletion+"'");
			LOG.debug("  action='"+action+"'");
			LOG.debug("  actionListeners='"+actionListeners+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof TextEntryComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TextEntryComponent'.");
		}

		TextEntryComponent component = (TextEntryComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (required != null) {
			if (isValueReference(required)) {
				ValueBinding vb = application.createValueBinding(required);
				component.setValueBinding(Properties.REQUIRED, vb);

			} else {
				component.setRequired(getBool(required));
			}
		}

		if (autoTab != null) {
			if (isValueReference(autoTab)) {
				ValueBinding vb = application.createValueBinding(autoTab);
				component.setValueBinding(Properties.AUTO_TAB, vb);

			} else {
				component.setAutoTab(getBool(autoTab));
			}
		}

		if (text != null) {
			if (isValueReference(text)) {
				ValueBinding vb = application.createValueBinding(text);
				component.setValueBinding(Properties.TEXT, vb);

			} else {
				component.setText(text);
			}
		}

		if (textDirection != null) {
			if (isValueReference(textDirection)) {
				ValueBinding vb = application.createValueBinding(textDirection);
				component.setValueBinding(Properties.TEXT_DIRECTION, vb);

			} else {
				component.setTextDirection(getInt(textDirection));
			}
		}

		if (emptyMessage != null) {
			if (isValueReference(emptyMessage)) {
				ValueBinding vb = application.createValueBinding(emptyMessage);
				component.setValueBinding(Properties.EMPTY_MESSAGE, vb);

			} else {
				component.setEmptyMessage(emptyMessage);
			}
		}

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);
				component.setValueBinding(Properties.READ_ONLY, vb);

			} else {
				component.setReadOnly(getBool(readOnly));
			}
		}

		if (valueChangeListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.VALUE_CHANGE_LISTENER_TYPE, valueChangeListeners);
		}

		if (focusStyleClass != null) {
			if (isValueReference(focusStyleClass)) {
				ValueBinding vb = application.createValueBinding(focusStyleClass);
				component.setValueBinding(Properties.FOCUS_STYLE_CLASS, vb);

			} else {
				component.setFocusStyleClass(focusStyleClass);
			}
		}

		if (errorStyleClass != null) {
			if (isValueReference(errorStyleClass)) {
				ValueBinding vb = application.createValueBinding(errorStyleClass);
				component.setValueBinding(Properties.ERROR_STYLE_CLASS, vb);

			} else {
				component.setErrorStyleClass(errorStyleClass);
			}
		}

		if (fatalStyleClass != null) {
			if (isValueReference(fatalStyleClass)) {
				ValueBinding vb = application.createValueBinding(fatalStyleClass);
				component.setValueBinding(Properties.FATAL_STYLE_CLASS, vb);

			} else {
				component.setFatalStyleClass(fatalStyleClass);
			}
		}

		if (infoStyleClass != null) {
			if (isValueReference(infoStyleClass)) {
				ValueBinding vb = application.createValueBinding(infoStyleClass);
				component.setValueBinding(Properties.INFO_STYLE_CLASS, vb);

			} else {
				component.setInfoStyleClass(infoStyleClass);
			}
		}

		if (warnStyleClass != null) {
			if (isValueReference(warnStyleClass)) {
				ValueBinding vb = application.createValueBinding(warnStyleClass);
				component.setValueBinding(Properties.WARN_STYLE_CLASS, vb);

			} else {
				component.setWarnStyleClass(warnStyleClass);
			}
		}

		if (clientValidator != null) {
			if (isValueReference(clientValidator)) {
				ValueBinding vb = application.createValueBinding(clientValidator);
				component.setValueBinding(Properties.CLIENT_VALIDATOR, vb);

			} else {
				component.setClientValidator(clientValidator);
			}
		}

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (maxTextLength != null) {
			if (isValueReference(maxTextLength)) {
				ValueBinding vb = application.createValueBinding(maxTextLength);
				component.setValueBinding(Properties.MAX_TEXT_LENGTH, vb);

			} else {
				component.setMaxTextLength(getInt(maxTextLength));
			}
		}

		if (columnNumber != null) {
			if (isValueReference(columnNumber)) {
				ValueBinding vb = application.createValueBinding(columnNumber);
				component.setValueBinding(Properties.COLUMN_NUMBER, vb);

			} else {
				component.setColumnNumber(getInt(columnNumber));
			}
		}

		if (autoCompletion != null) {
			if (isValueReference(autoCompletion)) {
				ValueBinding vb = application.createValueBinding(autoCompletion);
				component.setValueBinding(Properties.AUTO_COMPLETION, vb);

			} else {
				component.setAutoCompletion(getBool(autoCompletion));
			}
		}

		if (action != null) {
			ListenersTools.parseAction(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, action);
		}

		if (actionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, actionListeners, true);
		}
	}

	public void release() {
		required = null;
		autoTab = null;
		text = null;
		textDirection = null;
		emptyMessage = null;
		readOnly = null;
		valueChangeListeners = null;
		focusStyleClass = null;
		errorStyleClass = null;
		fatalStyleClass = null;
		infoStyleClass = null;
		warnStyleClass = null;
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
