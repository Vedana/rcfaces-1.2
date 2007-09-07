package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.TimeEntryComponent;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class TimeEntryTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TimeEntryTag.class);

	private ValueExpression required;
	private ValueExpression autoTab;
	private ValueExpression valueChangeListeners;
	private ValueExpression focusStyleClass;
	private ValueExpression selectionListeners;
	private ValueExpression readOnly;
	private ValueExpression literalLocale;
	private ValueExpression literalTimeZone;
	private ValueExpression componentLocale;
	private ValueExpression componentTimeZone;
	private ValueExpression errorStyleClass;
	private ValueExpression fatalStyleClass;
	private ValueExpression infoStyleClass;
	private ValueExpression warnStyleClass;
	private ValueExpression alternateText;
	private ValueExpression autoCompletion;
	private ValueExpression time;
	private ValueExpression minTime;
	private ValueExpression defaultTime;
	private ValueExpression maxTime;
	private ValueExpression timeFormat;
	private ValueExpression hourStep;
	private ValueExpression minuteStep;
	private ValueExpression secondStep;
	private ValueExpression millisStep;
	public String getComponentType() {
		return TimeEntryComponent.COMPONENT_TYPE;
	}

	public final void setRequired(ValueExpression required) {
		this.required = required;
	}

	public final void setAutoTab(ValueExpression autoTab) {
		this.autoTab = autoTab;
	}

	public final void setValueChangeListener(ValueExpression valueChangeListeners) {
		this.valueChangeListeners = valueChangeListeners;
	}

	public final void setFocusStyleClass(ValueExpression focusStyleClass) {
		this.focusStyleClass = focusStyleClass;
	}

	public final void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final void setReadOnly(ValueExpression readOnly) {
		this.readOnly = readOnly;
	}

	public final void setLiteralLocale(ValueExpression literalLocale) {
		this.literalLocale = literalLocale;
	}

	public final void setLiteralTimeZone(ValueExpression literalTimeZone) {
		this.literalTimeZone = literalTimeZone;
	}

	public final void setComponentLocale(ValueExpression componentLocale) {
		this.componentLocale = componentLocale;
	}

	public final void setComponentTimeZone(ValueExpression componentTimeZone) {
		this.componentTimeZone = componentTimeZone;
	}

	public final void setErrorStyleClass(ValueExpression errorStyleClass) {
		this.errorStyleClass = errorStyleClass;
	}

	public final void setFatalStyleClass(ValueExpression fatalStyleClass) {
		this.fatalStyleClass = fatalStyleClass;
	}

	public final void setInfoStyleClass(ValueExpression infoStyleClass) {
		this.infoStyleClass = infoStyleClass;
	}

	public final void setWarnStyleClass(ValueExpression warnStyleClass) {
		this.warnStyleClass = warnStyleClass;
	}

	public final void setAlternateText(ValueExpression alternateText) {
		this.alternateText = alternateText;
	}

	public final void setAutoCompletion(ValueExpression autoCompletion) {
		this.autoCompletion = autoCompletion;
	}

	public final void setTime(ValueExpression time) {
		this.time = time;
	}

	public final void setMinTime(ValueExpression minTime) {
		this.minTime = minTime;
	}

	public final void setDefaultTime(ValueExpression defaultTime) {
		this.defaultTime = defaultTime;
	}

	public final void setMaxTime(ValueExpression maxTime) {
		this.maxTime = maxTime;
	}

	public final void setTimeFormat(ValueExpression timeFormat) {
		this.timeFormat = timeFormat;
	}

	public final void setHourStep(ValueExpression hourStep) {
		this.hourStep = hourStep;
	}

	public final void setMinuteStep(ValueExpression minuteStep) {
		this.minuteStep = minuteStep;
	}

	public final void setSecondStep(ValueExpression secondStep) {
		this.secondStep = secondStep;
	}

	public final void setMillisStep(ValueExpression millisStep) {
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
		super.setProperties(uiComponent);

		if ((uiComponent instanceof TimeEntryComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TimeEntryComponent'.");
		}

		TimeEntryComponent component = (TimeEntryComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (required != null) {
			if (required.isLiteralText()==false) {
				component.setValueExpression(Properties.REQUIRED, required);

			} else {
				component.setRequired(getBool(required.getExpressionString()));
			}
		}

		if (autoTab != null) {
			if (autoTab.isLiteralText()==false) {
				component.setValueExpression(Properties.AUTO_TAB, autoTab);

			} else {
				component.setAutoTab(getBool(autoTab.getExpressionString()));
			}
		}

		if (valueChangeListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.VALUE_CHANGE_LISTENER_TYPE, valueChangeListeners);
		}

		if (focusStyleClass != null) {
			if (focusStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.FOCUS_STYLE_CLASS, focusStyleClass);

			} else {
				component.setFocusStyleClass(focusStyleClass.getExpressionString());
			}
		}

		if (selectionListeners != null) {
			ListenersTools.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (readOnly != null) {
			if (readOnly.isLiteralText()==false) {
				component.setValueExpression(Properties.READ_ONLY, readOnly);

			} else {
				component.setReadOnly(getBool(readOnly.getExpressionString()));
			}
		}

		if (literalLocale != null) {
			if (literalLocale.isLiteralText()==false) {
				component.setValueExpression(Properties.LITERAL_LOCALE, literalLocale);

			} else {
				component.setLiteralLocale(literalLocale.getExpressionString());
			}
		}

		if (literalTimeZone != null) {
			if (literalTimeZone.isLiteralText()==false) {
				component.setValueExpression(Properties.LITERAL_TIME_ZONE, literalTimeZone);

			} else {
				component.setLiteralTimeZone(literalTimeZone.getExpressionString());
			}
		}

		if (componentLocale != null) {
			if (componentLocale.isLiteralText()==false) {
				component.setValueExpression(Properties.COMPONENT_LOCALE, componentLocale);

			} else {
				component.setComponentLocale(componentLocale.getExpressionString());
			}
		}

		if (componentTimeZone != null) {
			if (componentTimeZone.isLiteralText()==false) {
				component.setValueExpression(Properties.COMPONENT_TIME_ZONE, componentTimeZone);

			} else {
				component.setComponentTimeZone(componentTimeZone.getExpressionString());
			}
		}

		if (errorStyleClass != null) {
			if (errorStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.ERROR_STYLE_CLASS, errorStyleClass);

			} else {
				component.setErrorStyleClass(errorStyleClass.getExpressionString());
			}
		}

		if (fatalStyleClass != null) {
			if (fatalStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.FATAL_STYLE_CLASS, fatalStyleClass);

			} else {
				component.setFatalStyleClass(fatalStyleClass.getExpressionString());
			}
		}

		if (infoStyleClass != null) {
			if (infoStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.INFO_STYLE_CLASS, infoStyleClass);

			} else {
				component.setInfoStyleClass(infoStyleClass.getExpressionString());
			}
		}

		if (warnStyleClass != null) {
			if (warnStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.WARN_STYLE_CLASS, warnStyleClass);

			} else {
				component.setWarnStyleClass(warnStyleClass.getExpressionString());
			}
		}

		if (alternateText != null) {
			if (alternateText.isLiteralText()==false) {
				component.setValueExpression(Properties.ALTERNATE_TEXT, alternateText);

			} else {
				component.setAlternateText(alternateText.getExpressionString());
			}
		}

		if (autoCompletion != null) {
			if (autoCompletion.isLiteralText()==false) {
				component.setValueExpression(Properties.AUTO_COMPLETION, autoCompletion);

			} else {
				component.setAutoCompletion(getBool(autoCompletion.getExpressionString()));
			}
		}

		if (time != null) {
			if (time.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE, time);

			} else {
				component.setTime(time.getExpressionString());
			}
		}

		if (minTime != null) {
			if (minTime.isLiteralText()==false) {
				component.setValueExpression(Properties.MIN_TIME, minTime);

			} else {
				component.setMinTime(minTime.getExpressionString());
			}
		}

		if (defaultTime != null) {
			if (defaultTime.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_TIME, defaultTime);

			} else {
				component.setDefaultTime(defaultTime.getExpressionString());
			}
		}

		if (maxTime != null) {
			if (maxTime.isLiteralText()==false) {
				component.setValueExpression(Properties.MAX_TIME, maxTime);

			} else {
				component.setMaxTime(maxTime.getExpressionString());
			}
		}

		if (timeFormat != null) {
			if (timeFormat.isLiteralText()==false) {
				component.setValueExpression(Properties.TIME_FORMAT, timeFormat);

			} else {
				component.setTimeFormat(timeFormat.getExpressionString());
			}
		}

		if (hourStep != null) {
			if (hourStep.isLiteralText()==false) {
				component.setValueExpression(Properties.HOUR_STEP, hourStep);

			} else {
				component.setHourStep(hourStep.getExpressionString());
			}
		}

		if (minuteStep != null) {
			if (minuteStep.isLiteralText()==false) {
				component.setValueExpression(Properties.MINUTE_STEP, minuteStep);

			} else {
				component.setMinuteStep(minuteStep.getExpressionString());
			}
		}

		if (secondStep != null) {
			if (secondStep.isLiteralText()==false) {
				component.setValueExpression(Properties.SECOND_STEP, secondStep);

			} else {
				component.setSecondStep(secondStep.getExpressionString());
			}
		}

		if (millisStep != null) {
			if (millisStep.isLiteralText()==false) {
				component.setValueExpression(Properties.MILLIS_STEP, millisStep);

			} else {
				component.setMillisStep(millisStep.getExpressionString());
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
