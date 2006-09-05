package org.rcfaces.core.internal.taglib;

import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.DateEntryComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class DateEntryTag extends AbstractCalendarTag {


	private static final Log LOG=LogFactory.getLog(DateEntryTag.class);

	private String required;
	private String autoTab;
	private String valueChangeListeners;
	private String focusStyleClass;
	private String showCalendarOnFocus;
	private String autoCompletion;
	private String dateFormat;
	private String defaultDate;
	private String action;
	private String actionListeners;
	public String getComponentType() {
		return DateEntryComponent.COMPONENT_TYPE;
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

	public final String getShowCalendarOnFocus() {
		return showCalendarOnFocus;
	}

	public final void setShowCalendarOnFocus(String showCalendarOnFocus) {
		this.showCalendarOnFocus = showCalendarOnFocus;
	}

	public final String getAutoCompletion() {
		return autoCompletion;
	}

	public final void setAutoCompletion(String autoCompletion) {
		this.autoCompletion = autoCompletion;
	}

	public final String getDateFormat() {
		return dateFormat;
	}

	public final void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public final String getDefaultDate() {
		return defaultDate;
	}

	public final void setDefaultDate(String defaultDate) {
		this.defaultDate = defaultDate;
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
			if (DateEntryComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  required='"+required+"'");
			LOG.debug("  autoTab='"+autoTab+"'");
			LOG.debug("  focusStyleClass='"+focusStyleClass+"'");
			LOG.debug("  showCalendarOnFocus='"+showCalendarOnFocus+"'");
			LOG.debug("  autoCompletion='"+autoCompletion+"'");
			LOG.debug("  dateFormat='"+dateFormat+"'");
			LOG.debug("  defaultDate='"+defaultDate+"'");
			LOG.debug("  action='"+action+"'");
			LOG.debug("  actionListeners='"+actionListeners+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof DateEntryComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'DateEntryComponent'.");
		}

		DateEntryComponent component = (DateEntryComponent) uiComponent;
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

		if (showCalendarOnFocus != null) {
			if (isValueReference(showCalendarOnFocus)) {
				ValueBinding vb = application.createValueBinding(showCalendarOnFocus);
				component.setShowCalendarOnFocus(vb);
			} else {
				component.setShowCalendarOnFocus(getBool(showCalendarOnFocus));
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

		if (dateFormat != null) {
			if (isValueReference(dateFormat)) {
				ValueBinding vb = application.createValueBinding(dateFormat);
				component.setDateFormat(vb);
			} else {
				component.setDateFormat(dateFormat);
			}
		}

		if (defaultDate != null) {
			if (isValueReference(defaultDate)) {
				ValueBinding vb = application.createValueBinding(defaultDate);
				component.setDefaultDate(vb);
			} else {
				component.setDefaultDate(defaultDate);
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
		valueChangeListeners = null;
		focusStyleClass = null;
		showCalendarOnFocus = null;
		autoCompletion = null;
		dateFormat = null;
		defaultDate = null;
		action = null;
		actionListeners = null;

		super.release();
	}

}
