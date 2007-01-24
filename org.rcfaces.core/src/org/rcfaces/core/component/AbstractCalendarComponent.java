package org.rcfaces.core.component;

import org.rcfaces.core.internal.converter.WeekDaysConverter;
import org.rcfaces.core.internal.component.Properties;
import java.util.TimeZone;
import java.util.Arrays;
import org.rcfaces.core.component.capability.ILiteralLocaleCapability;
import org.rcfaces.core.internal.converter.LiteralTwoDigitYearConverter;
import org.rcfaces.core.component.AbstractInputComponent;
import org.rcfaces.core.component.capability.IComponentTimeZoneCapability;
import org.rcfaces.core.component.capability.ILiteralTimeZoneCapability;
import org.rcfaces.core.component.capability.IClientDatesStrategyCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import javax.faces.el.ValueBinding;
import javax.faces.FacesException;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.internal.converter.ClientDatesStrategyConverter;
import java.util.Locale;
import org.rcfaces.core.model.IAdaptable;
import org.rcfaces.core.internal.converter.LocaleConverter;
import org.rcfaces.core.internal.converter.TimeZoneConverter;
import org.rcfaces.core.internal.converter.LiteralDateConverter;
import org.rcfaces.core.component.capability.IComponentLocaleCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;

/**
 * Technical component, used as a basis for building new RCFaces components.
 */
public abstract class AbstractCalendarComponent extends AbstractInputComponent implements 
	ISelectionEventCapability,
	IReadOnlyCapability,
	ILiteralLocaleCapability,
	ILiteralTimeZoneCapability,
	IComponentLocaleCapability,
	IComponentTimeZoneCapability,
	IClientDatesStrategyCapability {

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractInputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"selectionListener","literalTimeZone","maxDate","literalLocale","componentLocale","clientDatesStrategy","componentTimeZone","readOnly","disabledWeekDays","twoDigitYearStart","minDate"}));
	}


	public final Date getDate() {


				Object value=getValue();

				if (value==null) {
					return null;
				}			

				if (value instanceof Date) {
					return (Date)value;
				}
								
				if (value instanceof String) {
					return (Date)LiteralDateConverter.SINGLETON.getAsObject(null, this, (String)value);
				}				

				if (value instanceof IAdaptable) {
					Date adapted=(Date)((IAdaptable)value).getAdapter(Date.class, this);
					if (adapted!=null) {
						return adapted;
					}
				}


				throw new FacesException("Value of AbstractCalendar is not a date ! ("+value+")");
			
	}

	public final void setDate(Date date) {


				setValue(date);
			
	}

	public final void setTwoDigitYearStart(String date) {


			engine.setProperty(Properties.TWO_DIGIT_YEAR_START, date);
		
	}

	public final Date getTwoDigitYearStart(FacesContext facesContext) {


			Object value=engine.getProperty(Properties.TWO_DIGIT_YEAR_START, facesContext);
			if (value instanceof String) {
				value=LiteralTwoDigitYearConverter.SINGLETON.getAsObject(facesContext, this, (String)value);
			}
			
			return (Date)value;
		
	}

	public final void setClientDatesStrategy(String strategy) {


			setClientDatesStrategy(((Integer)ClientDatesStrategyConverter.SINGLETON.getAsObject(null, this, strategy)).intValue());
		
	}

	public final void setMinDate(String date) {


			engine.setProperty(Properties.MIN_DATE, date);
		
	}

	public final Date getMinDate(FacesContext facesContext) {


			Object value=engine.getProperty(Properties.MIN_DATE, facesContext);
			if (value instanceof String) {
				value=LiteralDateConverter.SINGLETON.getAsObject(facesContext, this, (String)value);
			}
			
			return (Date)value;
		
	}

	public final void setMaxDate(String date) {


			engine.setProperty(Properties.MAX_DATE, date);
		
	}

	public final Date getMaxDate(FacesContext facesContext) {


			Object value=engine.getProperty(Properties.MAX_DATE, facesContext);
			if (value instanceof String) {
				value=LiteralDateConverter.SINGLETON.getAsObject(facesContext, this, (String)value);
			}
			
			return (Date)value;
		
	}

	public final void setLiteralLocale(String locale) {


		setLiteralLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
	}

	public final void setComponentLocale(String locale) {


		setComponentLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
	}

	public final void setLiteralTimeZone(String timeZone) {


		setLiteralTimeZone((TimeZone)TimeZoneConverter.SINGLETON.getAsObject(null, this, timeZone));
		
	}

	public final void setComponentTimeZone(String timeZone) {


		setComponentTimeZone((TimeZone)TimeZoneConverter.SINGLETON.getAsObject(null, this, timeZone));
		
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

	public final java.util.Locale getLiteralLocale() {
		return getLiteralLocale(null);
	}

	/**
	 * See {@link #getLiteralLocale() getLiteralLocale()} for more details
	 */
	public final java.util.Locale getLiteralLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)engine.getProperty(Properties.LITERAL_LOCALE, facesContext);
	}

	public final void setLiteralLocale(java.util.Locale literalLocale) {
		engine.setProperty(Properties.LITERAL_LOCALE, literalLocale);
	}

	/**
	 * See {@link #setLiteralLocale(java.util.Locale) setLiteralLocale(java.util.Locale)} for more details
	 */
	public final void setLiteralLocale(ValueBinding literalLocale) {
		engine.setProperty(Properties.LITERAL_LOCALE, literalLocale);
	}

	public final java.util.TimeZone getLiteralTimeZone() {
		return getLiteralTimeZone(null);
	}

	/**
	 * See {@link #getLiteralTimeZone() getLiteralTimeZone()} for more details
	 */
	public final java.util.TimeZone getLiteralTimeZone(javax.faces.context.FacesContext facesContext) {
		return (java.util.TimeZone)engine.getProperty(Properties.LITERAL_TIME_ZONE, facesContext);
	}

	public final void setLiteralTimeZone(java.util.TimeZone literalTimeZone) {
		engine.setProperty(Properties.LITERAL_TIME_ZONE, literalTimeZone);
	}

	/**
	 * See {@link #setLiteralTimeZone(java.util.TimeZone) setLiteralTimeZone(java.util.TimeZone)} for more details
	 */
	public final void setLiteralTimeZone(ValueBinding literalTimeZone) {
		engine.setProperty(Properties.LITERAL_TIME_ZONE, literalTimeZone);
	}

	public final java.util.Locale getComponentLocale() {
		return getComponentLocale(null);
	}

	/**
	 * See {@link #getComponentLocale() getComponentLocale()} for more details
	 */
	public final java.util.Locale getComponentLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)engine.getProperty(Properties.COMPONENT_LOCALE, facesContext);
	}

	public final void setComponentLocale(java.util.Locale componentLocale) {
		engine.setProperty(Properties.COMPONENT_LOCALE, componentLocale);
	}

	/**
	 * See {@link #setComponentLocale(java.util.Locale) setComponentLocale(java.util.Locale)} for more details
	 */
	public final void setComponentLocale(ValueBinding componentLocale) {
		engine.setProperty(Properties.COMPONENT_LOCALE, componentLocale);
	}

	public final java.util.TimeZone getComponentTimeZone() {
		return getComponentTimeZone(null);
	}

	/**
	 * See {@link #getComponentTimeZone() getComponentTimeZone()} for more details
	 */
	public final java.util.TimeZone getComponentTimeZone(javax.faces.context.FacesContext facesContext) {
		return (java.util.TimeZone)engine.getProperty(Properties.COMPONENT_TIME_ZONE, facesContext);
	}

	public final void setComponentTimeZone(java.util.TimeZone componentTimeZone) {
		engine.setProperty(Properties.COMPONENT_TIME_ZONE, componentTimeZone);
	}

	/**
	 * See {@link #setComponentTimeZone(java.util.TimeZone) setComponentTimeZone(java.util.TimeZone)} for more details
	 */
	public final void setComponentTimeZone(ValueBinding componentTimeZone) {
		engine.setProperty(Properties.COMPONENT_TIME_ZONE, componentTimeZone);
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
