package org.rcfaces.core.internal.component;

import java.util.Date;
import java.util.Locale;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IClientDatesStrategyCapability;
import org.rcfaces.core.component.capability.ILocalizedAttributesCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.internal.converter.ClientDatesStrategyConverter;
import org.rcfaces.core.internal.converter.DateConverter;
import org.rcfaces.core.internal.converter.LocaleConverter;
import org.rcfaces.core.internal.converter.TwoDigitYearConverter;
import org.rcfaces.core.internal.converter.WeekDaysConverter;
import org.rcfaces.core.internal.tools.CalendarTools;

public abstract class AbstractCalendarComponent extends AbstractInputComponent implements 
	ISelectionEventCapability,
	IReadOnlyCapability,
	ILocalizedAttributesCapability,
	IClientDatesStrategyCapability {



	public final void setValue(Object value) {


				if (value instanceof String) {
					value=CalendarTools.parseValue(this, (String)value);
				}
				
				super.setValue(value);
			
	}

	public final void setTwoDigitYearStart(String date) {


			setTwoDigitYearStart((Date)TwoDigitYearConverter.SINGLETON.getAsObject(null, this, date));
		
	}

	public final void setClientDatesStrategy(String strategy) {


			setClientDatesStrategy(((Integer)ClientDatesStrategyConverter.SINGLETON.getAsObject(null, this, strategy)).intValue());
		
	}

	public final void setMinDate(String date) {


			setMinDate((Date)DateConverter.SINGLETON.getAsObject(null, this, date));
		
	}

	public final void setMaxDate(String date) {


			setMaxDate((Date)DateConverter.SINGLETON.getAsObject(null, this, date));
		
	}

	public final void setAttributesLocale(String locale) {


		setAttributesLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
	}

	public final void setDisabledWeekDays(String disabledWeekDays) {


		setDisabledWeekDays(((Integer)WeekDaysConverter.SINGLETON.getAsObject(null, this, disabledWeekDays)).intValue());
		
	}

	public final void addSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
	}

	public final boolean isReadOnly() {
		return isReadOnly(null);
	}

	public final boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.READ_ONLY, false, facesContext);
	}

	public final void setReadOnly(boolean readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	public final void setReadOnly(ValueBinding readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	public final java.util.Locale getAttributesLocale() {
		return getAttributesLocale(null);
	}

	public final java.util.Locale getAttributesLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)engine.getProperty(Properties.ATTRIBUTES_LOCALE, facesContext);
	}

	public final void setAttributesLocale(java.util.Locale attributesLocale) {
		engine.setProperty(Properties.ATTRIBUTES_LOCALE, attributesLocale);
	}

	public final void setAttributesLocale(ValueBinding attributesLocale) {
		engine.setProperty(Properties.ATTRIBUTES_LOCALE, attributesLocale);
	}

	public final int getClientDatesStrategy() {
		return getClientDatesStrategy(null);
	}

	public final int getClientDatesStrategy(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.CLIENT_DATES_STRATEGY,0, facesContext);
	}

	public final void setClientDatesStrategy(int clientDatesStrategy) {
		engine.setProperty(Properties.CLIENT_DATES_STRATEGY, clientDatesStrategy);
	}

	public final void setClientDatesStrategy(ValueBinding clientDatesStrategy) {
		engine.setProperty(Properties.CLIENT_DATES_STRATEGY, clientDatesStrategy);
	}

	public final Date getTwoDigitYearStart() {
		return getTwoDigitYearStart(null);
	}

	public final Date getTwoDigitYearStart(javax.faces.context.FacesContext facesContext) {
		return (Date)engine.getValue(Properties.TWO_DIGIT_YEAR_START, facesContext);
	}

	public final void setTwoDigitYearStart(Date twoDigitYearStart) {
		engine.setProperty(Properties.TWO_DIGIT_YEAR_START, twoDigitYearStart);
	}

	public final void setTwoDigitYearStart(ValueBinding twoDigitYearStart) {
		engine.setProperty(Properties.TWO_DIGIT_YEAR_START, twoDigitYearStart);
	}

	public final boolean isTwoDigitYearStartSetted() {
		return engine.isPropertySetted(Properties.TWO_DIGIT_YEAR_START);
	}

	public final java.util.Date getMinDate() {
		return getMinDate(null);
	}

	public final java.util.Date getMinDate(javax.faces.context.FacesContext facesContext) {
		return (java.util.Date)engine.getValue(Properties.MIN_DATE, facesContext);
	}

	public final void setMinDate(java.util.Date minDate) {
		engine.setProperty(Properties.MIN_DATE, minDate);
	}

	public final void setMinDate(ValueBinding minDate) {
		engine.setProperty(Properties.MIN_DATE, minDate);
	}

	public final boolean isMinDateSetted() {
		return engine.isPropertySetted(Properties.MIN_DATE);
	}

	public final java.util.Date getMaxDate() {
		return getMaxDate(null);
	}

	public final java.util.Date getMaxDate(javax.faces.context.FacesContext facesContext) {
		return (java.util.Date)engine.getValue(Properties.MAX_DATE, facesContext);
	}

	public final void setMaxDate(java.util.Date maxDate) {
		engine.setProperty(Properties.MAX_DATE, maxDate);
	}

	public final void setMaxDate(ValueBinding maxDate) {
		engine.setProperty(Properties.MAX_DATE, maxDate);
	}

	public final boolean isMaxDateSetted() {
		return engine.isPropertySetted(Properties.MAX_DATE);
	}

	public final int getDisabledWeekDays() {
		return getDisabledWeekDays(null);
	}

	public final int getDisabledWeekDays(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.DISABLED_WEEK_DAYS, 0, facesContext);
	}

	public final void setDisabledWeekDays(int disabledWeekDays) {
		engine.setProperty(Properties.DISABLED_WEEK_DAYS, disabledWeekDays);
	}

	public final void setDisabledWeekDays(ValueBinding disabledWeekDays) {
		engine.setProperty(Properties.DISABLED_WEEK_DAYS, disabledWeekDays);
	}

	public final boolean isDisabledWeekDaysSetted() {
		return engine.isPropertySetted(Properties.DISABLED_WEEK_DAYS);
	}

	public void release() {
		super.release();
	}
}
