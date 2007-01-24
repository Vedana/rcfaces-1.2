package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import org.rcfaces.core.component.AbstractCalendarComponent;
import javax.faces.application.Application;

public abstract class AbstractCalendarTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(AbstractCalendarTag.class);

	private String selectionListeners;
	private String readOnly;
	private String literalLocale;
	private String literalTimeZone;
	private String componentLocale;
	private String componentTimeZone;
	private String clientDatesStrategy;
	private String twoDigitYearStart;
	private String minDate;
	private String maxDate;
	private String disabledWeekDays;
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

	public final String getClientDatesStrategy() {
		return clientDatesStrategy;
	}

	public final void setClientDatesStrategy(String clientDatesStrategy) {
		this.clientDatesStrategy = clientDatesStrategy;
	}

	public final String getTwoDigitYearStart() {
		return twoDigitYearStart;
	}

	public final void setTwoDigitYearStart(String twoDigitYearStart) {
		this.twoDigitYearStart = twoDigitYearStart;
	}

	public final String getMinDate() {
		return minDate;
	}

	public final void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	public final String getMaxDate() {
		return maxDate;
	}

	public final void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public final String getDisabledWeekDays() {
		return disabledWeekDays;
	}

	public final void setDisabledWeekDays(String disabledWeekDays) {
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
		Application application = facesContext.getApplication();

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

		if (clientDatesStrategy != null) {
			if (isValueReference(clientDatesStrategy)) {
				ValueBinding vb = application.createValueBinding(clientDatesStrategy);

				component.setClientDatesStrategy(vb);
			} else {
				component.setClientDatesStrategy(clientDatesStrategy);
			}
		}

		if (twoDigitYearStart != null) {
			if (isValueReference(twoDigitYearStart)) {
				ValueBinding vb = application.createValueBinding(twoDigitYearStart);
				component.setTwoDigitYearStart(vb);
			} else {
				component.setTwoDigitYearStart(twoDigitYearStart);
			}
		}

		if (minDate != null) {
			if (isValueReference(minDate)) {
				ValueBinding vb = application.createValueBinding(minDate);
				component.setMinDate(vb);
			} else {
				component.setMinDate(minDate);
			}
		}

		if (maxDate != null) {
			if (isValueReference(maxDate)) {
				ValueBinding vb = application.createValueBinding(maxDate);
				component.setMaxDate(vb);
			} else {
				component.setMaxDate(maxDate);
			}
		}

		if (disabledWeekDays != null) {
			if (isValueReference(disabledWeekDays)) {
				ValueBinding vb = application.createValueBinding(disabledWeekDays);
				component.setDisabledWeekDays(vb);
			} else {
				component.setDisabledWeekDays(disabledWeekDays);
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
		disabledWeekDays = null;

		super.release();
	}

}
