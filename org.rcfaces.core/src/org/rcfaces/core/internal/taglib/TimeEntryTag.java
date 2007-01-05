package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.TimeEntryComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
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
	private String literalLocale;
	private String literalTimeZone;
	private String componentLocale;
	private String componentTimeZone;
	private String autoCompletion;
	private String time;
	private String minTime;
	private String defaultTime;
	private String maxTime;
	private String timeFormat;
	private String hourStep;
	private String minuteStep;
	private String secondStep;
	private String millisStep;
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

	public final String getLiteralLocale() {
		return literalLocale;
	}

	public final void setLiteralLocale(String literalLocale) {
		this.literalLocale = literalLocale;
	}

	public final String getLiteralTimeZone() {
		return literalTimeZone;
	}

	public final void setLiteralTimeZone(String literalTimeZone) {
		this.literalTimeZone = literalTimeZone;
	}

	public final String getComponentLocale() {
		return componentLocale;
	}

	public final void setComponentLocale(String componentLocale) {
		this.componentLocale = componentLocale;
	}

	public final String getComponentTimeZone() {
		return componentTimeZone;
	}

	public final void setComponentTimeZone(String componentTimeZone) {
		this.componentTimeZone = componentTimeZone;
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

	public final String getHourStep() {
		return hourStep;
	}

	public final void setHourStep(String hourStep) {
		this.hourStep = hourStep;
	}

	public final String getMinuteStep() {
		return minuteStep;
	}

	public final void setMinuteStep(String minuteStep) {
		this.minuteStep = minuteStep;
	}

	public final String getSecondStep() {
		return secondStep;
	}

	public final void setSecondStep(String secondStep) {
		this.secondStep = secondStep;
	}

	public final String getMillisStep() {
		return millisStep;
	}

	public final void setMillisStep(String millisStep) {
		this.millisStep = millisStep;
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
			LOG.debug("  literalLocale='"+literalLocale+"'");
			LOG.debug("  literalTimeZone='"+literalTimeZone+"'");
			LOG.debug("  componentLocale='"+componentLocale+"'");
			LOG.debug("  componentTimeZone='"+componentTimeZone+"'");
			LOG.debug("  autoCompletion='"+autoCompletion+"'");
			LOG.debug("  time='"+time+"'");
			LOG.debug("  minTime='"+minTime+"'");
			LOG.debug("  defaultTime='"+defaultTime+"'");
			LOG.debug("  maxTime='"+maxTime+"'");
			LOG.debug("  timeFormat='"+timeFormat+"'");
			LOG.debug("  hourStep='"+hourStep+"'");
			LOG.debug("  minuteStep='"+minuteStep+"'");
			LOG.debug("  secondStep='"+secondStep+"'");
			LOG.debug("  millisStep='"+millisStep+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof TimeEntryComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
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
			ListenersTools.parseListener(facesContext, component, ListenersTools.VALUE_CHANGE_LISTENER_TYPE, valueChangeListeners);
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
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);

				component.setReadOnly(vb);
			} else {
				component.setReadOnly(getBool(readOnly));
			}
		}

		if (literalLocale != null) {
			if (isValueReference(literalLocale)) {
				ValueBinding vb = application.createValueBinding(literalLocale);

				component.setLiteralLocale(vb);
			} else {
				component.setLiteralLocale(literalLocale);
			}
		}

		if (literalTimeZone != null) {
			if (isValueReference(literalTimeZone)) {
				ValueBinding vb = application.createValueBinding(literalTimeZone);

				component.setLiteralTimeZone(vb);
			} else {
				component.setLiteralTimeZone(literalTimeZone);
			}
		}

		if (componentLocale != null) {
			if (isValueReference(componentLocale)) {
				ValueBinding vb = application.createValueBinding(componentLocale);

				component.setComponentLocale(vb);
			} else {
				component.setComponentLocale(componentLocale);
			}
		}

		if (componentTimeZone != null) {
			if (isValueReference(componentTimeZone)) {
				ValueBinding vb = application.createValueBinding(componentTimeZone);

				component.setComponentTimeZone(vb);
			} else {
				component.setComponentTimeZone(componentTimeZone);
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

		if (hourStep != null) {
			if (isValueReference(hourStep)) {
				ValueBinding vb = application.createValueBinding(hourStep);
				component.setHourStep(vb);
			} else {
				component.setHourStep(hourStep);
			}
		}

		if (minuteStep != null) {
			if (isValueReference(minuteStep)) {
				ValueBinding vb = application.createValueBinding(minuteStep);
				component.setMinuteStep(vb);
			} else {
				component.setMinuteStep(minuteStep);
			}
		}

		if (secondStep != null) {
			if (isValueReference(secondStep)) {
				ValueBinding vb = application.createValueBinding(secondStep);
				component.setSecondStep(vb);
			} else {
				component.setSecondStep(secondStep);
			}
		}

		if (millisStep != null) {
			if (isValueReference(millisStep)) {
				ValueBinding vb = application.createValueBinding(millisStep);
				component.setMillisStep(vb);
			} else {
				component.setMillisStep(millisStep);
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
		literalLocale = null;
		literalTimeZone = null;
		componentLocale = null;
		componentTimeZone = null;
		autoCompletion = null;
		time = null;
		minTime = null;
		defaultTime = null;
		maxTime = null;
		timeFormat = null;
		hourStep = null;
		minuteStep = null;
		secondStep = null;
		millisStep = null;

		super.release();
	}

}
