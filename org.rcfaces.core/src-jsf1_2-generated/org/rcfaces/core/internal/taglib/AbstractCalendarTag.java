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
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.AbstractCalendarComponent;
import javax.faces.application.Application;

public abstract class AbstractCalendarTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AbstractCalendarTag.class);

	private ValueExpression selectionListeners;
	private ValueExpression readOnly;
	private ValueExpression literalLocale;
	private ValueExpression literalTimeZone;
	private ValueExpression componentLocale;
	private ValueExpression componentTimeZone;
	private ValueExpression clientDatesStrategy;
	private ValueExpression twoDigitYearStart;
	private ValueExpression minDate;
	private ValueExpression maxDate;
	private ValueExpression cursorDate;
	private ValueExpression disabledWeekDays;
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

	public final void setClientDatesStrategy(ValueExpression clientDatesStrategy) {
		this.clientDatesStrategy = clientDatesStrategy;
	}

	public final void setTwoDigitYearStart(ValueExpression twoDigitYearStart) {
		this.twoDigitYearStart = twoDigitYearStart;
	}

	public final void setMinDate(ValueExpression minDate) {
		this.minDate = minDate;
	}

	public final void setMaxDate(ValueExpression maxDate) {
		this.maxDate = maxDate;
	}

	public final void setCursorDate(ValueExpression cursorDate) {
		this.cursorDate = cursorDate;
	}

	public final void setDisabledWeekDays(ValueExpression disabledWeekDays) {
		this.disabledWeekDays = disabledWeekDays;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  literalLocale='"+literalLocale+"'");
			LOG.debug("  literalTimeZone='"+literalTimeZone+"'");
			LOG.debug("  componentLocale='"+componentLocale+"'");
			LOG.debug("  componentTimeZone='"+componentTimeZone+"'");
			LOG.debug("  clientDatesStrategy='"+clientDatesStrategy+"'");
			LOG.debug("  twoDigitYearStart='"+twoDigitYearStart+"'");
			LOG.debug("  minDate='"+minDate+"'");
			LOG.debug("  maxDate='"+maxDate+"'");
			LOG.debug("  cursorDate='"+cursorDate+"'");
			LOG.debug("  disabledWeekDays='"+disabledWeekDays+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof AbstractCalendarComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'AbstractCalendarComponent'.");
		}

		AbstractCalendarComponent component = (AbstractCalendarComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (selectionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
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

		if (clientDatesStrategy != null) {
			if (clientDatesStrategy.isLiteralText()==false) {
				component.setValueExpression(Properties.CLIENT_DATES_STRATEGY, clientDatesStrategy);

			} else {
				component.setClientDatesStrategy(clientDatesStrategy.getExpressionString());
			}
		}

		if (twoDigitYearStart != null) {
			if (twoDigitYearStart.isLiteralText()==false) {
				component.setValueExpression(Properties.TWO_DIGIT_YEAR_START, twoDigitYearStart);

			} else {
				component.setTwoDigitYearStart(twoDigitYearStart.getExpressionString());
			}
		}

		if (minDate != null) {
			if (minDate.isLiteralText()==false) {
				component.setValueExpression(Properties.MIN_DATE, minDate);

			} else {
				component.setMinDate(minDate.getExpressionString());
			}
		}

		if (maxDate != null) {
			if (maxDate.isLiteralText()==false) {
				component.setValueExpression(Properties.MAX_DATE, maxDate);

			} else {
				component.setMaxDate(maxDate.getExpressionString());
			}
		}

		if (cursorDate != null) {
			if (cursorDate.isLiteralText()==false) {
				component.setValueExpression(Properties.CURSOR_DATE, cursorDate);

			} else {
				component.setCursorDate(cursorDate.getExpressionString());
			}
		}

		if (disabledWeekDays != null) {
			if (disabledWeekDays.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLED_WEEK_DAYS, disabledWeekDays);

			} else {
				component.setDisabledWeekDays(disabledWeekDays.getExpressionString());
			}
		}
	}

	public void release() {
		selectionListeners = null;
		readOnly = null;
		literalLocale = null;
		literalTimeZone = null;
		componentLocale = null;
		componentTimeZone = null;
		clientDatesStrategy = null;
		twoDigitYearStart = null;
		minDate = null;
		maxDate = null;
		cursorDate = null;
		disabledWeekDays = null;

		super.release();
	}

}
