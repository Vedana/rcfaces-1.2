package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.component.TimeEntryComponent;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
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
	private String errorStyleClass;
	private String fatalStyleClass;
	private String infoStyleClass;
	private String warnStyleClass;
	private String alternateText;
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

	public final String getAlternateText() {
		return alternateText;
	}

	public final void setAlternateText(String alternateText) {
		this.alternateText = alternateText;
	}

	public final void setAutoCompletion(String autoCompletion) {
		this.autoCompletion = autoCompletion;
	}

	public final void setTime(String time) {
		this.time = time;
	}

	public final void setMinTime(String minTime) {
		this.minTime = minTime;
	}

	public final void setDefaultTime(String defaultTime) {
		this.defaultTime = defaultTime;
	}

	public final void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}

	public final void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public final void setHourStep(String hourStep) {
		this.hourStep = hourStep;
	}

	public final void setMinuteStep(String minuteStep) {
		this.minuteStep = minuteStep;
	}

	public final void setSecondStep(String secondStep) {
		this.secondStep = secondStep;
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
			LOG.debug("  errorStyleClass='"+errorStyleClass+"'");
			LOG.debug("  fatalStyleClass='"+fatalStyleClass+"'");
			LOG.debug("  infoStyleClass='"+infoStyleClass+"'");
			LOG.debug("  warnStyleClass='"+warnStyleClass+"'");
			LOG.debug("  alternateText='"+alternateText+"'");
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
		if ((uiComponent instanceof TimeEntryComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TimeEntryComponent'.");
		}

		super.setProperties(uiComponent);

		TimeEntryComponent component = (TimeEntryComponent) uiComponent;
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

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (readOnly != null) {
			if (isValueReference(readOnly)) {
				ValueBinding vb = application.createValueBinding(readOnly);
				component.setValueBinding(Properties.READ_ONLY, vb);

			} else {
				component.setReadOnly(getBool(readOnly));
			}
		}

		if (literalLocale != null) {
			if (isValueReference(literalLocale)) {
				ValueBinding vb = application.createValueBinding(literalLocale);
				component.setValueBinding(Properties.LITERAL_LOCALE, vb);

			} else {
				component.setLiteralLocale(literalLocale);
			}
		}

		if (literalTimeZone != null) {
			if (isValueReference(literalTimeZone)) {
				ValueBinding vb = application.createValueBinding(literalTimeZone);
				component.setValueBinding(Properties.LITERAL_TIME_ZONE, vb);

			} else {
				component.setLiteralTimeZone(literalTimeZone);
			}
		}

		if (componentLocale != null) {
			if (isValueReference(componentLocale)) {
				ValueBinding vb = application.createValueBinding(componentLocale);
				component.setValueBinding(Properties.COMPONENT_LOCALE, vb);

			} else {
				component.setComponentLocale(componentLocale);
			}
		}

		if (componentTimeZone != null) {
			if (isValueReference(componentTimeZone)) {
				ValueBinding vb = application.createValueBinding(componentTimeZone);
				component.setValueBinding(Properties.COMPONENT_TIME_ZONE, vb);

			} else {
				component.setComponentTimeZone(componentTimeZone);
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

		if (alternateText != null) {
			if (isValueReference(alternateText)) {
				ValueBinding vb = application.createValueBinding(alternateText);
				component.setValueBinding(Properties.ALTERNATE_TEXT, vb);

			} else {
				component.setAlternateText(alternateText);
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

		if (time != null) {
			if (isValueReference(time)) {
				ValueBinding vb = application.createValueBinding(time);
				component.setValueBinding(Properties.VALUE, vb);

			} else {
				component.setTime(time);
			}
		}

		if (minTime != null) {
			if (isValueReference(minTime)) {
				ValueBinding vb = application.createValueBinding(minTime);
				component.setValueBinding(Properties.MIN_TIME, vb);

			} else {
				component.setMinTime(minTime);
			}
		}

		if (defaultTime != null) {
			if (isValueReference(defaultTime)) {
				ValueBinding vb = application.createValueBinding(defaultTime);
				component.setValueBinding(Properties.DEFAULT_TIME, vb);

			} else {
				component.setDefaultTime(defaultTime);
			}
		}

		if (maxTime != null) {
			if (isValueReference(maxTime)) {
				ValueBinding vb = application.createValueBinding(maxTime);
				component.setValueBinding(Properties.MAX_TIME, vb);

			} else {
				component.setMaxTime(maxTime);
			}
		}

		if (timeFormat != null) {
			if (isValueReference(timeFormat)) {
				ValueBinding vb = application.createValueBinding(timeFormat);
				component.setValueBinding(Properties.TIME_FORMAT, vb);

			} else {
				component.setTimeFormat(timeFormat);
			}
		}

		if (hourStep != null) {
			if (isValueReference(hourStep)) {
				ValueBinding vb = application.createValueBinding(hourStep);
				component.setValueBinding(Properties.HOUR_STEP, vb);

			} else {
				component.setHourStep(hourStep);
			}
		}

		if (minuteStep != null) {
			if (isValueReference(minuteStep)) {
				ValueBinding vb = application.createValueBinding(minuteStep);
				component.setValueBinding(Properties.MINUTE_STEP, vb);

			} else {
				component.setMinuteStep(minuteStep);
			}
		}

		if (secondStep != null) {
			if (isValueReference(secondStep)) {
				ValueBinding vb = application.createValueBinding(secondStep);
				component.setValueBinding(Properties.SECOND_STEP, vb);

			} else {
				component.setSecondStep(secondStep);
			}
		}

		if (millisStep != null) {
			if (isValueReference(millisStep)) {
				ValueBinding vb = application.createValueBinding(millisStep);
				component.setValueBinding(Properties.MILLIS_STEP, vb);

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
		errorStyleClass = null;
		fatalStyleClass = null;
		infoStyleClass = null;
		warnStyleClass = null;
		alternateText = null;
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
