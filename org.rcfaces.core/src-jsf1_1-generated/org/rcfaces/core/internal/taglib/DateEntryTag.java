package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.DateEntryComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class DateEntryTag extends AbstractCalendarTag implements Tag {


	private static final Log LOG=LogFactory.getLog(DateEntryTag.class);

	private String required;
	private String autoTab;
	private String valueChangeListeners;
	private String focusStyleClass;
	private String errorStyleClass;
	private String fatalStyleClass;
	private String infoStyleClass;
	private String warnStyleClass;
	private String showCalendarOnFocus;
	private String autoCompletion;
	private String defaultDate;
	private String dateFormat;
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

	public final void setShowCalendarOnFocus(String showCalendarOnFocus) {
		this.showCalendarOnFocus = showCalendarOnFocus;
	}

	public final void setAutoCompletion(String autoCompletion) {
		this.autoCompletion = autoCompletion;
	}

	public final void setDefaultDate(String defaultDate) {
		this.defaultDate = defaultDate;
	}

	public final void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
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

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (DateEntryComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  required='"+required+"'");
			LOG.debug("  autoTab='"+autoTab+"'");
			LOG.debug("  focusStyleClass='"+focusStyleClass+"'");
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  showCalendarOnFocus='"+showCalendarOnFocus+"'");
			LOG.debug("  autoCompletion='"+autoCompletion+"'");
			LOG.debug("  defaultDate='"+defaultDate+"'");
			LOG.debug("  dateFormat='"+dateFormat+"'");
			LOG.debug("  action='"+action+"'");
			LOG.debug("  actionListeners='"+actionListeners+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof DateEntryComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'DateEntryComponent'.");
		}

		DateEntryComponent component = (DateEntryComponent) uiComponent;
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

		if (showCalendarOnFocus != null) {
			if (isValueReference(showCalendarOnFocus)) {
				ValueBinding vb = application.createValueBinding(showCalendarOnFocus);
				component.setValueBinding(Properties.SHOW_CALENDAR_ON_FOCUS, vb);

			} else {
				component.setShowCalendarOnFocus(getBool(showCalendarOnFocus));
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

		if (defaultDate != null) {
			if (isValueReference(defaultDate)) {
				ValueBinding vb = application.createValueBinding(defaultDate);
				component.setValueBinding(Properties.DEFAULT_DATE, vb);

			} else {
				component.setDefaultDate(defaultDate);
			}
		}

		if (dateFormat != null) {
			if (isValueReference(dateFormat)) {
				ValueBinding vb = application.createValueBinding(dateFormat);
				component.setValueBinding(Properties.DATE_FORMAT, vb);

			} else {
				component.setDateFormat(dateFormat);
			}
		}

		if (action != null) {
			ListenersTools1_1.parseAction(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, action);
		}

		if (actionListeners != null) {
			ListenersTools1_1.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, actionListeners, true);
		}
	}

	public void release() {
		required = null;
		autoTab = null;
		valueChangeListeners = null;
		focusStyleClass = null;
		errorStyleClass = null;
		fatalStyleClass = null;
		infoStyleClass = null;
		warnStyleClass = null;
		showCalendarOnFocus = null;
		autoCompletion = null;
		defaultDate = null;
		dateFormat = null;
		action = null;
		actionListeners = null;

		super.release();
	}

}
