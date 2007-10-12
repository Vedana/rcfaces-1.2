package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.component.DateEntryComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class DateEntryTag extends AbstractCalendarTag implements Tag {


	private static final Log LOG=LogFactory.getLog(DateEntryTag.class);

	private ValueExpression required;
	private ValueExpression autoTab;
	private ValueExpression valueChangeListeners;
	private ValueExpression focusStyleClass;
	private ValueExpression errorStyleClass;
	private ValueExpression fatalStyleClass;
	private ValueExpression infoStyleClass;
	private ValueExpression warnStyleClass;
	private ValueExpression showCalendarOnFocus;
	private ValueExpression autoCompletion;
	private ValueExpression dateFormat;
	private ValueExpression defaultDate;
	private ValueExpression actionListeners;
	private ValueExpression action;
	public String getComponentType() {
		return DateEntryComponent.COMPONENT_TYPE;
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

	public final void setShowCalendarOnFocus(ValueExpression showCalendarOnFocus) {
		this.showCalendarOnFocus = showCalendarOnFocus;
	}

	public final void setAutoCompletion(ValueExpression autoCompletion) {
		this.autoCompletion = autoCompletion;
	}

	public final void setDateFormat(ValueExpression dateFormat) {
		this.dateFormat = dateFormat;
	}

	public final void setDefaultDate(ValueExpression defaultDate) {
		this.defaultDate = defaultDate;
	}

	public final void setAction(ValueExpression action) {
		this.action=action;
	}

	public final void setActionListener(ValueExpression listeners) {
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
			LOG.debug("  dateFormat='"+dateFormat+"'");
			LOG.debug("  defaultDate='"+defaultDate+"'");
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
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.VALUE_CHANGE_LISTENER_TYPE, valueChangeListeners);
		}

		if (focusStyleClass != null) {
			if (focusStyleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.FOCUS_STYLE_CLASS, focusStyleClass);

			} else {
				component.setFocusStyleClass(focusStyleClass.getExpressionString());
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

		if (showCalendarOnFocus != null) {
			if (showCalendarOnFocus.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_CALENDAR_ON_FOCUS, showCalendarOnFocus);

			} else {
				component.setShowCalendarOnFocus(getBool(showCalendarOnFocus.getExpressionString()));
			}
		}

		if (autoCompletion != null) {
			if (autoCompletion.isLiteralText()==false) {
				component.setValueExpression(Properties.AUTO_COMPLETION, autoCompletion);

			} else {
				component.setAutoCompletion(getBool(autoCompletion.getExpressionString()));
			}
		}

		if (dateFormat != null) {
			if (dateFormat.isLiteralText()==false) {
				component.setValueExpression(Properties.DATE_FORMAT, dateFormat);

			} else {
				component.setDateFormat(dateFormat.getExpressionString());
			}
		}

		if (defaultDate != null) {
			if (defaultDate.isLiteralText()==false) {
				component.setValueExpression(Properties.DEFAULT_DATE, defaultDate);

			} else {
				component.setDefaultDate(defaultDate.getExpressionString());
			}
		}

		if (action != null) {
			ListenersTools1_2.parseAction(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, action);
		}

		if (actionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, actionListeners, true);
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
		dateFormat = null;
		defaultDate = null;
		action = null;
		actionListeners = null;

		super.release();
	}

}
