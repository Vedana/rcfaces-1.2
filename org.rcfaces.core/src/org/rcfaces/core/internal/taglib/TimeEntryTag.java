package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.TimeEntryComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class TimeEntryTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TimeEntryTag.class);

	private String required;
	private String autoTab;
	private String valueChangeListeners;
	private String focusStyleClass;
	private String selectionListeners;
	private String readOnly;
	private String autoCompletion;
	private String time;
	private String minTime;
	private String defaultTime;
	private String maxTime;
	private String timeFormat;
	public String getComponentType() {
		return TimeEntryComponent.COMPONENT_TYPE;
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

	public final String getAutoCompletion() {
		return autoCompletion;
	}

	public final void setAutoCompletion(String autoCompletion) {
		this.autoCompletion = autoCompletion;
	}

	public final String getTime() {
		return time;
	}

	public final void setTime(String time) {
		this.time = time;
	}

	public final String getMinTime() {
		return minTime;
	}

	public final void setMinTime(String minTime) {
		this.minTime = minTime;
	}

	public final String getDefaultTime() {
		return defaultTime;
	}

	public final void setDefaultTime(String defaultTime) {
		this.defaultTime = defaultTime;
	}

	public final String getMaxTime() {
		return maxTime;
	}

	public final void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}

	public final String getTimeFormat() {
		return timeFormat;
	}

	public final void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (TimeEntryComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  required='"+required+"'");
			LOG.debug("  autoTab='"+autoTab+"'");
			LOG.debug("  focusStyleClass='"+focusStyleClass+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  autoCompletion='"+autoCompletion+"'");
			LOG.debug("  time='"+time+"'");
			LOG.debug("  minTime='"+minTime+"'");
			LOG.debug("  defaultTime='"+defaultTime+"'");
			LOG.debug("  maxTime='"+maxTime+"'");
			LOG.debug("  timeFormat='"+timeFormat+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof TimeEntryComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TimeEntryComponent'.");
		}

		TimeEntryComponent component = (TimeEntryComponent) uiComponent;
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

		if (autoCompletion != null) {
			if (isValueReference(autoCompletion)) {
				ValueBinding vb = application.createValueBinding(autoCompletion);
				component.setAutoCompletion(vb);
			} else {
				component.setAutoCompletion(getBool(autoCompletion));
			}
		}

		if (time != null) {
			if (isValueReference(time)) {
				ValueBinding vb = application.createValueBinding(time);
				component.setTime(vb);
			} else {
				component.setTime(time);
			}
		}

		if (minTime != null) {
			if (isValueReference(minTime)) {
				ValueBinding vb = application.createValueBinding(minTime);
				component.setMinTime(vb);
			} else {
				component.setMinTime(minTime);
			}
		}

		if (defaultTime != null) {
			if (isValueReference(defaultTime)) {
				ValueBinding vb = application.createValueBinding(defaultTime);
				component.setDefaultTime(vb);
			} else {
				component.setDefaultTime(defaultTime);
			}
		}

		if (maxTime != null) {
			if (isValueReference(maxTime)) {
				ValueBinding vb = application.createValueBinding(maxTime);
				component.setMaxTime(vb);
			} else {
				component.setMaxTime(maxTime);
			}
		}

		if (timeFormat != null) {
			if (isValueReference(timeFormat)) {
				ValueBinding vb = application.createValueBinding(timeFormat);
				component.setTimeFormat(vb);
			} else {
				component.setTimeFormat(timeFormat);
			}
		}
	}

	public void release() {
		required = null;
		autoTab = null;
		valueChangeListeners = null;
		focusStyleClass = null;
		selectionListeners = null;
		readOnly = null;
		autoCompletion = null;
		time = null;
		minTime = null;
		defaultTime = null;
		maxTime = null;
		timeFormat = null;

		super.release();
	}

}
