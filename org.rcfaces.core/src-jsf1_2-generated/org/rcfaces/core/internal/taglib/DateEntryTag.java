package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.component.DateEntryComponent;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

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
	private ValueExpression dateFormat;
	private ValueExpression showCalendarOnFocus;
	private ValueExpression autoCompletion;
	private ValueExpression defaultDate;
	private ValueExpression actionListeners;
	private ValueExpression action;
	public String getComponentType() {
		return DateEntryComponent.COMPONENT_TYPE;
	}

	public void setRequired(ValueExpression required) {
		this.required = required;
	}

	public void setAutoTab(ValueExpression autoTab) {
		this.autoTab = autoTab;
	}

	public void setValueChangeListener(ValueExpression valueChangeListeners) {
		this.valueChangeListeners = valueChangeListeners;
	}

	public void setFocusStyleClass(ValueExpression focusStyleClass) {
		this.focusStyleClass = focusStyleClass;
	}

	public void setErrorStyleClass(ValueExpression errorStyleClass) {
		this.errorStyleClass = errorStyleClass;
	}

	public void setFatalStyleClass(ValueExpression fatalStyleClass) {
		this.fatalStyleClass = fatalStyleClass;
	}

	public void setInfoStyleClass(ValueExpression infoStyleClass) {
		this.infoStyleClass = infoStyleClass;
	}

	public void setWarnStyleClass(ValueExpression warnStyleClass) {
		this.warnStyleClass = warnStyleClass;
	}

	public void setDateFormat(ValueExpression dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setShowCalendarOnFocus(ValueExpression showCalendarOnFocus) {
		this.showCalendarOnFocus = showCalendarOnFocus;
	}

	public void setAutoCompletion(ValueExpression autoCompletion) {
		this.autoCompletion = autoCompletion;
	}

	public void setDefaultDate(ValueExpression defaultDate) {
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
			LOG.debug("  dateFormat='"+dateFormat+"'");
			LOG.debug("  showCalendarOnFocus='"+showCalendarOnFocus+"'");
			LOG.debug("  autoCompletion='"+autoCompletion+"'");
			LOG.debug("  defaultDate='"+defaultDate+"'");
			LOG.debug("  action='"+action+"'");
			LOG.debug("  actionListeners='"+actionListeners+"'");
		}
		if ((uiComponent instanceof DateEntryComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'DateEntryComponent'.");
		}

		super.setProperties(uiComponent);

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

		if (dateFormat != null) {
			if (dateFormat.isLiteralText()==false) {
				component.setValueExpression(Properties.DATE_FORMAT, dateFormat);

			} else {
				component.setDateFormat(dateFormat.getExpressionString());
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
		dateFormat = null;
		showCalendarOnFocus = null;
		autoCompletion = null;
		defaultDate = null;
		action = null;
		actionListeners = null;

		super.release();
	}

}
