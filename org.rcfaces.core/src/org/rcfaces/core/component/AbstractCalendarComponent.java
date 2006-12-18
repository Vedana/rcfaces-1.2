package org.rcfaces.core.component;

import org.rcfaces.core.internal.converter.WeekDaysConverter;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IClientDatesStrategyCapability;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.ILocalizedAttributesCapability;
import javax.faces.FacesException;
import java.util.Date;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.internal.converter.TwoDigitYearConverter;
import java.util.HashSet;
import java.util.Locale;
import org.rcfaces.core.internal.converter.ClientDatesStrategyConverter;
import org.rcfaces.core.internal.converter.LocaleConverter;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.internal.tools.CalendarTools;
import org.rcfaces.core.internal.converter.DateConverter;
import org.rcfaces.core.component.capability.IReadOnlyCapability;

public abstract class AbstractCalendarComponent extends AbstractInputComponent implements 
	ISelectionEventCapability,
	IReadOnlyCapability,
	ILocalizedAttributesCapability,
	IClientDatesStrategyCapability {

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","maxDate","clientDatesStrategy","readOnly","disabledWeekDays","attributesLocale","twoDigitYearStart","minDate"}));
	}


	public final void setValue(Object value) {


				if (value instanceof String) {
					value=CalendarTools.parseValue(this, (String)value);
				}
				
				super.setValue(value);
			
	}

	public final Date getDate() {


				Object value=getValue();
				if (value==null || (value instanceof Date)) {
					return (Date)value;
				}

				throw new FacesException("Value of AbstractCalendar is not a date ! ("+value+")");
			
	}

	public final void setDate(Date date) {


				setValue(date);
			
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

	/**
	 * See {@link #isReadOnly() isReadOnly()} for more details
	 */
	public final boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.READ_ONLY, false, facesContext);
	}

	public final void setReadOnly(boolean readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	/**
	 * See {@link #setReadOnly(boolean) setReadOnly(boolean)} for more details
	 */
	public final void setReadOnly(ValueBinding readOnly) {
		engine.setProperty(Properties.READ_ONLY, readOnly);
	}

	public final java.util.Locale getAttributesLocale() {
		return getAttributesLocale(null);
	}

	/**
	 * See {@link #getAttributesLocale() getAttributesLocale()} for more details
	 */
	public final java.util.Locale getAttributesLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)engine.getProperty(Properties.ATTRIBUTES_LOCALE, facesContext);
	}

	public final void setAttributesLocale(java.util.Locale attributesLocale) {
		engine.setProperty(Properties.ATTRIBUTES_LOCALE, attributesLocale);
	}

	/**
	 * See {@link #setAttributesLocale(java.util.Locale) setAttributesLocale(java.util.Locale)} for more details
	 */
	public final void setAttributesLocale(ValueBinding attributesLocale) {
		engine.setProperty(Properties.ATTRIBUTES_LOCALE, attributesLocale);
	}

	public final int getClientDatesStrategy() {
		return getClientDatesStrategy(null);
	}

	/**
	 * See {@link #getClientDatesStrategy() getClientDatesStrategy()} for more details
	 */
	public final int getClientDatesStrategy(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.CLIENT_DATES_STRATEGY,0, facesContext);
	}

	public final void setClientDatesStrategy(int clientDatesStrategy) {
		engine.setProperty(Properties.CLIENT_DATES_STRATEGY, clientDatesStrategy);
	}

	/**
	 * See {@link #setClientDatesStrategy(int) setClientDatesStrategy(int)} for more details
	 */
	public final void setClientDatesStrategy(ValueBinding clientDatesStrategy) {
		engine.setProperty(Properties.CLIENT_DATES_STRATEGY, clientDatesStrategy);
	}

	/**
	 * Returns a string value specifying the year considered as base for selecting a date when the associated dateEntry's value's year has only two digits.
	 * @return pivot year
	 */
	public final Date getTwoDigitYearStart() {
		return getTwoDigitYearStart(null);
	}

	/**
	 * Returns a string value specifying the year considered as base for selecting a date when the associated dateEntry's value's year has only two digits.
	 * @return pivot year
	 */
	public final Date getTwoDigitYearStart(javax.faces.context.FacesContext facesContext) {
		return (Date)engine.getValue(Properties.TWO_DIGIT_YEAR_START, facesContext);
	}

	/**
	 * Sets a string value specifying the year considered as base for selecting a date when the associated dateEntry's value's year has only two digits.
	 * @param twoDigitYearStart pivot year
	 */
	public final void setTwoDigitYearStart(Date twoDigitYearStart) {
		engine.setProperty(Properties.TWO_DIGIT_YEAR_START, twoDigitYearStart);
	}

	/**
	 * Sets a string value specifying the year considered as base for selecting a date when the associated dateEntry's value's year has only two digits.
	 * @param twoDigitYearStart pivot year
	 */
	public final void setTwoDigitYearStart(ValueBinding twoDigitYearStart) {
		engine.setProperty(Properties.TWO_DIGIT_YEAR_START, twoDigitYearStart);
	}

	/**
	 * Returns <code>true</code> if the attribute "twoDigitYearStart" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTwoDigitYearStartSetted() {
		return engine.isPropertySetted(Properties.TWO_DIGIT_YEAR_START);
	}

	/**
	 * Returns a date value indicating the minimum acceptable date for the component. the first accepted value is minDate plus one day.
	 * @return min date
	 */
	public final java.util.Date getMinDate() {
		return getMinDate(null);
	}

	/**
	 * Returns a date value indicating the minimum acceptable date for the component. the first accepted value is minDate plus one day.
	 * @return min date
	 */
	public final java.util.Date getMinDate(javax.faces.context.FacesContext facesContext) {
		return (java.util.Date)engine.getValue(Properties.MIN_DATE, facesContext);
	}

	/**
	 * Sets a date value indicating the minimum acceptable date for the component. the first accepted value is minDate plus one day.
	 * @param minDate min date
	 */
	public final void setMinDate(java.util.Date minDate) {
		engine.setProperty(Properties.MIN_DATE, minDate);
	}

	/**
	 * Sets a date value indicating the minimum acceptable date for the component. the first accepted value is minDate plus one day.
	 * @param minDate min date
	 */
	public final void setMinDate(ValueBinding minDate) {
		engine.setProperty(Properties.MIN_DATE, minDate);
	}

	/**
	 * Returns <code>true</code> if the attribute "minDate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMinDateSetted() {
		return engine.isPropertySetted(Properties.MIN_DATE);
	}

	/**
	 * Returns a date value indicating the maximum acceptable date for the component. The last accepted value is maxDate minus one day.
	 * @return max date
	 */
	public final java.util.Date getMaxDate() {
		return getMaxDate(null);
	}

	/**
	 * Returns a date value indicating the maximum acceptable date for the component. The last accepted value is maxDate minus one day.
	 * @return max date
	 */
	public final java.util.Date getMaxDate(javax.faces.context.FacesContext facesContext) {
		return (java.util.Date)engine.getValue(Properties.MAX_DATE, facesContext);
	}

	/**
	 * Sets a date value indicating the maximum acceptable date for the component. The last accepted value is maxDate minus one day.
	 * @param maxDate max date
	 */
	public final void setMaxDate(java.util.Date maxDate) {
		engine.setProperty(Properties.MAX_DATE, maxDate);
	}

	/**
	 * Sets a date value indicating the maximum acceptable date for the component. The last accepted value is maxDate minus one day.
	 * @param maxDate max date
	 */
	public final void setMaxDate(ValueBinding maxDate) {
		engine.setProperty(Properties.MAX_DATE, maxDate);
	}

	/**
	 * Returns <code>true</code> if the attribute "maxDate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMaxDateSetted() {
		return engine.isPropertySetted(Properties.MAX_DATE);
	}

	/**
	 * Returns a list of disabled week days (depending on the locale specified for the component).
	 * @return list of days
	 */
	public final int getDisabledWeekDays() {
		return getDisabledWeekDays(null);
	}

	/**
	 * Returns a list of disabled week days (depending on the locale specified for the component).
	 * @return list of days
	 */
	public final int getDisabledWeekDays(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.DISABLED_WEEK_DAYS, 0, facesContext);
	}

	/**
	 * Sets a list of disabled week days (depending on the locale specified for the component).
	 * @param disabledWeekDays list of days
	 */
	public final void setDisabledWeekDays(int disabledWeekDays) {
		engine.setProperty(Properties.DISABLED_WEEK_DAYS, disabledWeekDays);
	}

	/**
	 * Sets a list of disabled week days (depending on the locale specified for the component).
	 * @param disabledWeekDays list of days
	 */
	public final void setDisabledWeekDays(ValueBinding disabledWeekDays) {
		engine.setProperty(Properties.DISABLED_WEEK_DAYS, disabledWeekDays);
	}

	/**
	 * Returns <code>true</code> if the attribute "disabledWeekDays" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledWeekDaysSetted() {
		return engine.isPropertySetted(Properties.DISABLED_WEEK_DAYS);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
